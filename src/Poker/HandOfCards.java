// Harry Gleeson - 14455822 - COMP30050 - Assignment 3
package Poker;

import static java.lang.Math.pow;
public class HandOfCards {
	private int handCapacity = 5;
	private PlayingCard[] hand = new PlayingCard[handCapacity];
	private int[] royalFlushGameValue = new int[]{14, 13, 12, 11, 10}; //To allow to see if game values correspond to that of a royal flush
	private int[] wrappedStraightGameValue = new int[]{14, 5, 4, 3, 2}; //To allow for comparison of special straight case A,2,3,4,5
	private DeckOfCards deck = new DeckOfCards();
	private PlayingCard temp;
	static final int HIGH_HAND_DEFAULT = 0;
	static final int ONE_PAIR_DEFAULT = 10000000;
	static final int TWO_PAIR_DEFAULT = 20000000;
	static final int THREE_OF_A_KIND_DEFAULT = 30000000;
	static final int STRAIGHT_DEFAULT = 40000000;
	static final int FLUSH_DEFAULT = 50000000;
	static final int FULL_HOUSE_DEFAULT = 60000000;
	static final int FOUR_OF_A_KIND_DEFAULT = 70000000;
	static final int STRAIGHT_FLUSH_DEFAULT = 80000000;
	static final int ROYAL_FLUSH_DEFAULT = 90000000;
	
	
	public HandOfCards(DeckOfCards deck){ //Deals the hand of cards and sorts them 
		this.deck = deck;
		for(int i=0; i<handCapacity; i++){
			hand[i] =deck.dealNext();
		}
		sort();
	}
	
	public DeckOfCards ReturnDeck(){  //Public method to return deck
		return deck;
	}
	
	public String handString(){ //Returns the toString representations of the cards in the hand
		String handString="";
		for(int i=0; i<handCapacity; i++){
			handString+=hand[i].toString()+" ,";
		}
		return handString;
	}
	
	private boolean sameSuit(){ //Tests if all of the cards in the hand are of the same set, made its own method to remove duplicate code in isRoyalFlush(), isStraightFlush() and isStraight()
		int i=0;
		while (i < handCapacity-1){ 
			if (hand[i].getSuit() == hand[i+1].getSuit()){
				i++;
			}
			else
				return false;
		}
		return true;
	}
	
	private boolean isSequential(){  //Determines if hands in card are sequence of 5 consecutive game values, made its own method to remove duplicate code in isStraightFlush() and isStraight()
		int i=0, j=1, n=0;
		while(j<handCapacity&&i<handCapacity){
			if (hand[n].getGameValue()-j == hand[j].getGameValue()){ //Checks if cards in hand are consecutive in their game values
				j++;
			}
			else if(hand[i].getGameValue() == wrappedStraightGameValue[i]){  //Checks for case of straight A,2,3,4,5 where game values are not sequential
				i++;
			} 
			else
				return false;
		}
		return true;
	}
	
	private void sort(){ //Uses a Bubble sort to sort the cards in descending order according to their game value. 
		for (int i = 0; i < handCapacity-1; i++){
			for(int j=1;  j < handCapacity-i;  j++ ){
                if ( hand[j-1].getGameValue() < hand[j].getGameValue()){
                        temp = hand[j-1];               
                        hand[j-1] = hand[j];
                        hand[j] = temp;
                } 
			}
		}
	}
	
	//****For all of the following methods to determine what hand you have, they will check that the hand is not belonging to a stronger hand
	
	public boolean isRoyalFlush(){  //Checks first if all cards in hand are of same set, if they are then checks if the game values are the same as those in a royal flush
		boolean set = sameSuit(); //Uses sameSuit method to determine if all cards in the hand are in the same suit
		if (!set){
			return false;
		}
		else{
			int i=0;
			while (i < handCapacity){ //If all cards are of the same suit, the game values of each card is compared to the array containing the game values of a royal flush 
				if (hand[i].getGameValue() == royalFlushGameValue[i]){
					i++;
				}
				else
					return false;
			}
		return true;
		}
	}
	
	public boolean isStraightFlush(){  //This method checks if the cards are all of the same suit and are sequential, if one or more of these is not the case then it returns false
		boolean set = sameSuit();
		boolean sequential = isSequential();
		if(isRoyalFlush()||!set||!sequential){
			return false;
		}
		return true;			
	}
	
	public boolean isFourOfAKind(){ //Compares each card in the sorted hand to the card 3 indexes away from it. As the array is sorted, cards with a common face value will be beside each other so if a cards face value is equal to that of the cards 3 away from it, there are 4 cards of the same value in the hand 
		if(isRoyalFlush()||isStraightFlush()){
			return false;
		}
		else{
			int u=0;
			while(u+3<handCapacity){
				if(hand[u].getFaceValue() == hand[u+3].getFaceValue()){ //Compares card's face value to face value of card 3 indexes away from it in the hand 
					return true;
				}
				u++;
			}
		}
		return false;
	}
		
	public boolean isFullHouse(){ //Checks if either the first two and last 3 or first 3 and last 2 cards are the same. If they are, the hand is a full house
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()){
			return false;
		}	
		else{
			int u=0;
			while(u+4<handCapacity){
				if((hand[u].getFaceValue() == hand[u+1].getFaceValue()&&hand[u+1].getFaceValue()==hand[u+2].getFaceValue())&&hand[u+3].getFaceValue()==hand[u+4].getFaceValue()||(hand[u].getFaceValue() == hand[u+1].getFaceValue()&&hand[u+2].getFaceValue()==hand[u+3].getFaceValue())&&hand[u+3].getFaceValue()==hand[u+4].getFaceValue()){ //Array is sorted already so cards with same face value automatically beside each other. Meaning if the first and fourth or second and fifth values are the same there are 4 of a kind.
					return true;
				}
				u++;
			}
		}
		return false;
	}
	
	public boolean isFlush(){ //Checks that all cards in hand are of same suit. If they are all of same set and its not a stronger hand, return true 
		boolean set = sameSuit();
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||!set){
			return false;
		}
		return true;
	}
	
	public boolean isStraight(){ //Checks that all cards in hand are of sequential. If they are sequential and its not a stronger hand, return true 
		boolean sequential = isSequential();
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||!sequential){
			return false;
		}
		return true;
	}
	
	public boolean isThreeOfAKind(){ //Compares each card in the sorted hand to the card 2 indexes away from it. As the array is sorted, cards with a common face value will be beside each other so if a cards face value is equal to that of the cards 2 away from it, there are 3 cards of the same value in the hand
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()){
			return false;
		}
		else{
			int u=0;
			while(u+2<handCapacity){
				if(hand[u].getFaceValue() == hand[u+2].getFaceValue()){ //Compares card's face value to face value of card 2 indexes away from it in the hand
					return true;
				}
				u++;
			}
		}	
		return false;	
	}
			
	public boolean isTwoPair(){ //Checks if any of the cards are the same value as the card after them in the hand, if any are, it counts the pair. If the number of pairs in the hand is 2, it returns true as the hand contains 2 pairs
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()){
			return false;
		}
		else{
			int u=0, pairCounter=0;
			while(u+1<handCapacity){
				if(hand[u].getFaceValue() == hand[u+1].getFaceValue()){
					pairCounter++;
					}
					u++;
			}	
			if(pairCounter==2){
				return true;
			}
			else
				return false;
		}
	}
	
	public boolean isOnePair(){ //As it cannot return true to any stronger hand, the method only needs to check if any of the cards in the hand have the same value to the one next to it
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()||isTwoPair()){
			return false;
		}
		else{
			int u=0;
			while(u+1<handCapacity){
				if(hand[u].getFaceValue() == hand[u+1].getFaceValue()){
					return true;
				}
				u++;
			}
		return false;
		}
	}	
	
	public boolean isHighHand(){ //If none of the other hands return true meaning the hand belongs to no stringer hand, isHighHand() returns true
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()||isTwoPair()||isOnePair()){	
			return false;
		}
		return true;
	}
	
	public int getGameValue(){
		int gameValue=0;
		if(isRoyalFlush()){
			gameValue = HandOfCards.ROYAL_FLUSH_DEFAULT;
		}
		else if(isStraightFlush()){//Formula =  HandOfCards.STRAIGHT_FLUSH_DEFAULT+hand[1].getGameValue
			gameValue = HandOfCards.STRAIGHT_FLUSH_DEFAULT+hand[1].getGameValue();//Adds second card game value because of the case with A,2,3,4,5 if first card compared would give misleading result
		}
		else if(isFourOfAKind()){//If 2 hands have 4 of a kind, they are separated by the non four of a kind card in the hand. Formula = HandOfCards.FOUR_OF_A_KIND_DEFAULT+(game value of four of a kind)^3+(game value of non four of a kind card)
			if(hand[2].getGameValue()==hand[0].getGameValue()){//This checks the location of the non four of a kind card, if this is true, the non matching card is the last card in the hand so the game value of it is added to the game value to separate in case 2 hands have the same 4 of a kind
				gameValue = (int) (HandOfCards.FOUR_OF_A_KIND_DEFAULT+pow(hand[2].getGameValue(), 3)+hand[4].getGameValue());//The second card in the hand is guaranteed to be part of the four of a kind so its value can separate 2 fourOfAKind hands, then the value of the other card is added on to determine a winner in the case of 2 identical 4 of a kinds
			}
			else//In this case, the non four of a kind card is the first card in the hand and its game value is added to the four of a kind's value cubed
				gameValue = (int) (HandOfCards.FOUR_OF_A_KIND_DEFAULT+pow(hand[2].getGameValue(), 3)+hand[0].getGameValue());
		}
		else if(isFullHouse()){//To separate 2 full houses, the full house with the higher three of a kind wins. The third card in the hand, hand[2] is guaranteed to be in the 3 of a kind. Formula = HandOfCards.FULL_HOUSE_DEFAULT+ (face value of three of a kind)^4+(face value of pair)
			if(hand[2].getGameValue()==hand[1].getGameValue()){//This checks the location of the pair, if this is true, the pair is the last 2 cards in the hand so the game value of them is added to the game value to separate in case 2 hands have the same 3 of a kind in their full houses
				gameValue = (int) (HandOfCards.FULL_HOUSE_DEFAULT+pow(hand[2].getGameValue(), 4)+hand[3].getGameValue());
			}
			else
				gameValue = (int) (HandOfCards.FULL_HOUSE_DEFAULT+pow(hand[2].getGameValue(), 4)+hand[1].getGameValue());
		}
		else if(isFlush()){//To separate 2 flushes look at highest card in both flushes. If both have same high card, move to next highest. Formula = HandOfCards.FLUSH_DEFAULT+hand[0].getGameValue^5 + hand[1].getGameValue^4...
			int j=5;
			gameValue = HandOfCards.FLUSH_DEFAULT;
			for(int i=0; i<handCapacity; i++){
				gameValue += (int) pow(hand[i].getGameValue(), j);
				j--;
			}
		}
		else if(isStraight()){//Formula = HandOfCards.STRAIGHT_DEFAULT+hand[1].getGameValue
			gameValue = (int) (HandOfCards.STRAIGHT_DEFAULT+hand[1].getGameValue());//The highest card in the straight wins in the case of 2 straights. I have used the second highest card in the hand to separate 2 straights instead of the first card as in the case of A,2,3,4,5, the result would be incorrect. So the game value of the second card in the hand is added on, if 2 hands have the same second card in a straight, the pot is split
		}
		else if(isThreeOfAKind()){//Formula = HandOfCards.THREE_OF_A_KIND_DEFAULT + (game value of three of a kind)^3 + (game value of of higher non-three of a kind card)^2 + game value of lower non-three of a kind card
			gameValue = (int) (HandOfCards.THREE_OF_A_KIND_DEFAULT+pow(hand[2].getGameValue(), 3));//To separate three of a kind, the hand with the higher three wins. The third card in the hand is guaranteed to be part of the three of a kind so its value is added.
			if(hand[2].getGameValue()!=hand[3].getGameValue()){//Means that the non-three of a kind cards are in hand[3] and hand[4]
				gameValue+=pow(hand[3].getGameValue(), 2)+hand[4].getGameValue();
			}
			else if(hand[1].getGameValue()!=hand[2].getGameValue()){//Means that the non-three of a kind cards are in hand[0] and hand[1]
				gameValue+=pow(hand[0].getGameValue(), 2)+hand[1].getGameValue();
			}
			else//Otherwise the non three of a kind cards are in hand[0] and hand[4]
				gameValue+=pow(hand[0].getGameValue(), 2)+hand[4].getGameValue();
		}
		else if(isTwoPair()){//Formula = HandOfCards.TWO_PAIR_DEFAULT + (higher pair game value)^3 + (lower pair game value)^2 + non-pair card game value
			gameValue = (int) (HandOfCards.TWO_PAIR_DEFAULT+pow(hand[1].getGameValue(),3)+pow(hand[3].getGameValue(),2));//To separate two of a kind, the hand with the higher high pair wins. The second card in the hand is guaranteed to be part of the high pair due to the deck being sorted so its value is added, and the fourth card in the hand is guaranteed to be part of the lower of the 2 pairs. Then if the 2 sets of 2 pairs are the same, the outlier card's value is added. The face value of the highest pair is weighted highest, followed by the lower pair then the outlier
			if(hand[0].getGameValue()!=hand[1].getGameValue()){//Means non pair card is hand[0]
				gameValue+=hand[0].getGameValue();
			}
			else if(hand[1].getGameValue()!=hand[2].getGameValue()&&hand[2].getGameValue()!=hand[3].getGameValue()){//Means non pair card is hand[2]
				gameValue+=hand[2].getGameValue();
			}
			else//Otherwise the non pair card is hand[4]
				gameValue+=hand[4].getGameValue();

		}
		else if(isOnePair()){//Formula = HandOfCards.ONE_PAIR_DEFAULT + (game value of pair * 3)^4 + (game value highest non pair card)^3 + (game value of second lowest non pair card)^2 + game value of lowest non pair card
			int u=0;
			while(u+1<handCapacity){
				if(hand[u].getGameValue() == hand[u+1].getGameValue()){//To separate 2 hands with the same one pair, you look at the highest card outside the pair. These statements weight the cards from the pair being the heaviest weighted to the lowest card outside the hand to enable hands with a common pair to be separated.
					gameValue = HandOfCards.ONE_PAIR_DEFAULT;
					if(u+1==1){
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[2].getGameValue(), 3)+pow(hand[3].getGameValue(), 2)+hand[4].getGameValue());
					}
					else if(u+1==2){
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[0].getGameValue(), 3)+pow(hand[3].getGameValue(), 2)+hand[4].getGameValue());
					}
					else if(u+1==3){
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[0].getGameValue(), 3)+pow(hand[1].getGameValue(), 2)+hand[4].getGameValue());
					}
					else
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[0].getGameValue(), 3)+pow(hand[1].getGameValue(), 2)+hand[2].getGameValue());//Finds the location of the pair in the hand and adds on the game value, the highest pair separates 2 one pairs.
				}
				u++;
			}
		}
		else if(isHighHand()){//In the case two hands have the same high hands, the rest of the hand is gone through to separate them. This weights the hand appropriately from highest card to lowest in order to separate two high hands. Formula = HandOfCards.HIGH_HAND_DEFAULT+hand[0].getGameValue^5 + hand[1].getGameValue^4...
			int j=5;
			gameValue = HandOfCards.HIGH_HAND_DEFAULT;
			for(int i=0; i<handCapacity; i++){
				gameValue += (int) pow(hand[i].getGameValue(), j);
				j--;
			}
		}
		
		return gameValue;
	}
	
	public static void main(String[] args){ //The main method generates a hand of cards, prints out the toString() representation of each card and then the best possible poker hand it belongs to is printed
	//BELOW GENERATES 2 OF THE SAME CATEGORY OF HAND FOR ALL POSSIBLE CATEGORISATIONS OF HANDS AND COMPARES THEM TO TEST THE getGameValue method. Royal flush does not need to be tested as if there are 2 royal flushes the pot will be split automatically.
		boolean achieved = false;
		DeckOfCards CardDeck = new DeckOfCards();
		HandOfCards CardHand = new HandOfCards(CardDeck);
		HandOfCards CardHand1 = new HandOfCards(CardDeck);

		//TESTS 2 STRAIGHT FLUSHES AGAINST EACH OTHER:
		System.out.println("Testing Straight Flush:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isStraightFlush()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}

		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);
			if(CardHand1.isStraightFlush()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}	
		achieved = false;
			
		//TESTS 2 FOUR OF A KINDS AGAINST EACH OTHER:
		System.out.println("\nTesting Four Of A Kind:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isFourOfAKind()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}

		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);
			if(CardHand1.isFourOfAKind()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved = false;
		
		//TESTS 2 FULL HOUSES AGAINST EACH OTHER:
		System.out.println("\nTesting Full House:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isFullHouse()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);

			if(CardHand1.isFullHouse()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved=false;
		
		//TESTS 2 STRAIGHTS AGAINST EACH OTHER:
		System.out.println("\nTesting Straight:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isStraight()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);

			if(CardHand1.isStraight()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved = false;
		
		//TESTS 2 THREE OF A KINDS AGAINST EACH OTHER:
		System.out.println("\nTesting Three Of A Kind:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isThreeOfAKind()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);

			if(CardHand1.isThreeOfAKind()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved = false;
		
		//TESTS 2 TWO PAIRS AGAINST EACH OTHER:
		System.out.println("\nTesting Two Pair:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isTwoPair()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);
			if(CardHand1.isTwoPair()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved = false;
		
		//TESTS 2 ONE PAIRS AGAINST EACH OTHER:
		System.out.println("\nTesting One Pair:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isOnePair()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);

			if(CardHand1.isOnePair()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
		achieved = false;
		
		//TESTS 2 HIGH HANDS AGAINST EACH OTHER:
		System.out.println("\nTesting High Hand:");
		while(!achieved){
			CardDeck.shuffle();
			CardHand = new HandOfCards(CardDeck);		
			if(CardHand.isHighHand()){
				System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
				achieved=true;
			}
		}
		achieved = false;
		while(!achieved){
			CardDeck.shuffle();
			CardHand1 = new HandOfCards(CardDeck);

			if(CardHand1.isHighHand()){
				System.out.println("Hand 2: "+CardHand1.handString()+"\tGame value: "+CardHand1.getGameValue());
				achieved=true;
			}
		}
		if(CardHand.getGameValue()>CardHand1.getGameValue()){
			System.out.println("Hand 1 wins!");
		}
		else if(CardHand.getGameValue()<CardHand1.getGameValue()){
			System.out.println("Hand 2 wins!");
		}
		else{
			System.out.println("Split Pot");
		}
	}
}