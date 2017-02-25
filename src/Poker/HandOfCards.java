// Harry Gleeson - 14455822 - COMP30050 - Assignment 3
package Poker;

import static java.lang.Math.pow;
public class HandOfCards {
	private PlayingCard[] hand = new PlayingCard[HAND_CAPACITY];
	private int[] royalFlushGameValue = new int[]{14, 13, 12, 11, 10}; //To allow to see if game values correspond to that of a royal flush
	private int[] wrappedStraightGameValue = new int[]{14, 5, 4, 3, 2}; //To allow for comparison of special straight case A,2,3,4,5
	private DeckOfCards deck = new DeckOfCards();
	private PlayingCard temp;
	
	
	static final int HAND_CAPACITY = 5;
	//Constants for defaults used in getGameValue() method
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
	
	//Constants used for array indexes
	static final int FIRST_CARD_INDEX = 0;
	static final int SECOND_CARD_INDEX = 1;
	static final int THIRD_CARD_INDEX = 2;
	static final int FOURTH_CARD_INDEX = 3;
	static final int FIFTH_CARD_INDEX = 4;
	
	
	public HandOfCards(DeckOfCards deck){ //Deals the hand of cards and sorts them 
		this.deck = deck;
		for(int i=0; i<HAND_CAPACITY; i++){
			hand[i] =deck.dealNext();
		}
		sort();
	}
	
	public DeckOfCards ReturnDeck(){  //Public method to return deck
		return deck;
	}
	
	public String handString(){ //Returns the toString representations of the cards in the hand
		String handString="";
		for(int i=0; i<HAND_CAPACITY; i++){
			handString+=hand[i].toString()+" ,";
		}
		return handString;
	}
	
	private boolean sameSuit(){ //Tests if all of the cards in the hand are of the same set, made its own method to remove duplicate code in isRoyalFlush(), isStraightFlush() and isStraight()
		int i=0;
		while (i < HAND_CAPACITY-1){ 
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
		while(j<HAND_CAPACITY&&i<HAND_CAPACITY){
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
		for (int i = 0; i < HAND_CAPACITY-1; i++){
			for(int j=1;  j < HAND_CAPACITY-i;  j++ ){
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
			while (i < HAND_CAPACITY){ //If all cards are of the same suit, the game values of each card is compared to the array containing the game values of a royal flush 
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
			while(u+3<HAND_CAPACITY){
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
			while(u+4<HAND_CAPACITY){
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
			while(u+2<HAND_CAPACITY){
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
			while(u+1<HAND_CAPACITY){
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
			while(u+1<HAND_CAPACITY){
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
	
	public int getGameValue(){ //This method returns an integer value depending on the quality of the hand.
		int gameValue=0;
		if(isRoyalFlush()){//Formula = HandOfCards.ROYAL_FLUSH_DEFAULT 
			gameValue = HandOfCards.ROYAL_FLUSH_DEFAULT;//If 2 players have a royal flush, they cannot be separated and the pot is split. 
		}
		
		else if(isStraightFlush()){//Formula =  HandOfCards.STRAIGHT_FLUSH_DEFAULT+hand[SECOND_CARD_INDEX].getGameValue
			gameValue = HandOfCards.STRAIGHT_FLUSH_DEFAULT+hand[SECOND_CARD_INDEX].getGameValue();//If 2 players have straight flushes, the winner is decided by the straight with the highest high card. Adds second card game value because of the case with A,2,3,4,5 if first card compared would give misleading result. If 2 hands have the same second card in a straight flush, the pot is split.
		}
		
		else if(isFourOfAKind()){//Formula = HandOfCards.FOUR_OF_A_KIND_DEFAULT+(game value of four of a kind)^3+(game value of non four of a kind card). If 2 hands have 4 of a kind, the winner is the hand with the higher 4 of a kind. If two hands have the same four of a kind, they are separated by the non four of a kind card in the hand. If they have the same four of a kind and the same other card, the pot is split.
			if(hand[THIRD_CARD_INDEX].getGameValue()==hand[FIRST_CARD_INDEX].getGameValue()){//This checks the location of the non four of a kind card, if this is true, the non matching card is the last card in the hand so the game value of it is added to the game value to separate in case 2 hands have the same 4 of a kind
				gameValue = (int) (HandOfCards.FOUR_OF_A_KIND_DEFAULT+pow(hand[THIRD_CARD_INDEX].getGameValue(), 3)+hand[FIFTH_CARD_INDEX].getGameValue());//The second card in the hand is guaranteed to be part of the four of a kind so its value can separate 2 fourOfAKind hands, then the value of the other card is added on to determine a winner in the case of 2 identical 4 of a kinds
			}
			else//In this case, the non four of a kind card is the first card in the hand and its game value is added to the four of a kind's value cubed
				gameValue = (int) (HandOfCards.FOUR_OF_A_KIND_DEFAULT+pow(hand[THIRD_CARD_INDEX].getGameValue(), 3)+hand[FIRST_CARD_INDEX].getGameValue());
		}
		
		else if(isFullHouse()){//To separate 2 full houses, the full house with the higher three of a kind wins. The third card in the hand, hand[2] is guaranteed to be in the 3 of a kind. Formula = HandOfCards.FULL_HOUSE_DEFAULT+ (face value of three of a kind)^4+(face value of pair)
			if(hand[THIRD_CARD_INDEX].getGameValue()==hand[SECOND_CARD_INDEX].getGameValue()){//This checks the location of the pair, if this is true, the pair is the last 2 cards in the hand so the game value of them is added to the game value to separate in case 2 hands have the same 3 of a kind in their full houses
				gameValue = (int) (HandOfCards.FULL_HOUSE_DEFAULT+pow(hand[THIRD_CARD_INDEX].getGameValue(), 4)+hand[FOURTH_CARD_INDEX].getGameValue());
			}
			else
				gameValue = (int) (HandOfCards.FULL_HOUSE_DEFAULT+pow(hand[THIRD_CARD_INDEX].getGameValue(), 4)+hand[SECOND_CARD_INDEX].getGameValue());
		}
		
		else if(isFlush()){//To separate 2 flushes look at highest card in both flushes. If both have same high card, move to next highest. Formula = HandOfCards.FLUSH_DEFAULT+hand[0].getGameValue^5 + hand[1].getGameValue^4...
			int j=HAND_CAPACITY;
			gameValue = HandOfCards.FLUSH_DEFAULT;
			for(int i=0; i<HAND_CAPACITY; i++){
				gameValue += (int) pow(hand[i].getGameValue(), j);
				j--;
			}
		}
		
		else if(isStraight()){//Formula = HandOfCards.STRAIGHT_DEFAULT+hand[SECOND_CARD_INDEX].getGameValue
			gameValue = (int) (HandOfCards.STRAIGHT_DEFAULT+hand[SECOND_CARD_INDEX].getGameValue());//The highest card in the straight wins in the case of 2 straights. I have used the second highest card in the hand to separate 2 straights instead of the first card as in the case of A,2,3,4,5, the result would be incorrect. So the game value of the second card in the hand is added on, if 2 hands have the same second card in a straight, the pot is split
		}
		
		else if(isThreeOfAKind()){//Formula = HandOfCards.THREE_OF_A_KIND_DEFAULT + (game value of three of a kind)^3 + (game value of of higher non-three of a kind card)^2 + game value of lower non-three of a kind card
			gameValue = (int) (HandOfCards.THREE_OF_A_KIND_DEFAULT+pow(hand[THIRD_CARD_INDEX].getGameValue(), 3));//To separate three of a kind, the hand with the higher three wins. The third card in the hand is guaranteed to be part of the three of a kind so its value is added. If two hands have the same three of a kind, the highest card not part of the three wins, if these are the same the higher of the other card not part of the three wins. If this is also the same, the pot is split.
			if(hand[THIRD_CARD_INDEX].getGameValue()!=hand[FOURTH_CARD_INDEX].getGameValue()){//Means that the non-three of a kind cards are in hand[FOURTH_CARD_INDEX] and hand[FIFTH_CARD_INDEX]
				gameValue+=pow(hand[FOURTH_CARD_INDEX].getGameValue(), 2)+hand[FIFTH_CARD_INDEX].getGameValue();
			}
			else if(hand[SECOND_CARD_INDEX].getGameValue()!=hand[THIRD_CARD_INDEX].getGameValue()){//Means that the non-three of a kind cards are in hand[FIRST_CARD_INDEX] and hand[SECOND_CARD_INDEX]
				gameValue+=pow(hand[FIRST_CARD_INDEX].getGameValue(), 2)+hand[SECOND_CARD_INDEX].getGameValue();
			}
			else//Otherwise the non three of a kind cards are in hand[FIRST_CARD_INDEX] and hand[FIFTS_CARD_INDEX]
				gameValue+=pow(hand[FIRST_CARD_INDEX].getGameValue(), 2)+hand[FIFTH_CARD_INDEX].getGameValue();
		}
		
		else if(isTwoPair()){//Formula = HandOfCards.TWO_PAIR_DEFAULT + (higher pair game value)^3 + (lower pair game value)^2 + non-pair card game value
			gameValue = (int) (HandOfCards.TWO_PAIR_DEFAULT+pow(hand[SECOND_CARD_INDEX].getGameValue(),3)+pow(hand[FOURTH_CARD_INDEX].getGameValue(),2));//To separate two of a kind, the hand with the higher high pair wins. The second card in the hand is guaranteed to be part of the high pair due to the deck being sorted so its value is added, and the fourth card in the hand is guaranteed to be part of the lower of the 2 pairs. Then if the 2 sets of 2 pairs are the same, the outlier card's value determines the winner. If the pairs and the outlier are the same, the pot is split.
			if(hand[FIRST_CARD_INDEX].getGameValue()!=hand[SECOND_CARD_INDEX].getGameValue()){//Means non pair card is hand[FIRST_CARD_INDEX]
				gameValue+=hand[FIRST_CARD_INDEX].getGameValue();
			}
			else if(hand[SECOND_CARD_INDEX].getGameValue()!=hand[THIRD_CARD_INDEX].getGameValue()&&hand[THIRD_CARD_INDEX].getGameValue()!=hand[FOURTH_CARD_INDEX].getGameValue()){//Means non pair card is hand[THIRD_CARD_INDEX]
				gameValue+=hand[THIRD_CARD_INDEX].getGameValue();
			}
			else//Otherwise the non pair card is hand[FIFTH_CARD_INDEX]
				gameValue+=hand[FIFTH_CARD_INDEX].getGameValue();
		}
		
		else if(isOnePair()){//Formula = HandOfCards.ONE_PAIR_DEFAULT + (game value of pair * 3)^4 + (game value highest non pair card)^3 + (game value of second lowest non pair card)^2 + game value of lowest non pair card
			int u=0;
			while(u+1<HAND_CAPACITY){
				if(hand[u].getGameValue() == hand[u+1].getGameValue()){//To separate 2 hands with the same one pair, you look at the highest card outside the pair. If these are the same you go to the next highest card outside the pair, if these are the same then the second highest outside the pair then if these are still the same the lowest card outside the pair. If all of these are the same then the pot is split.
					gameValue = HandOfCards.ONE_PAIR_DEFAULT;
					if(u+1==1){//This means that the pair is hand[FIRST_CARD_INDEX] & hand[SECOND_CARD_INDEX], so the highest to lowest non-pair values are, in descending order, hand[THIRD_CARD_INDEX], hand[FOURTH_CARD_INDEX] and hand[FIFTH_CARD_INDEX]
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[THIRD_CARD_INDEX].getGameValue(), 3)+pow(hand[FOURTH_CARD_INDEX].getGameValue(), 2)+hand[FIFTH_CARD_INDEX].getGameValue());
					}
					else if(u+1==2){//This means that the pair is hand[SECOND_CARD_INDEX] & hand[THIRD_CARD_INDEX], so the highest to lowest non-pair values are, in descending order, hand[FIRST_CARD_INDEX], hand[FOURTH_CARD_INDEX] and hand[FIFTH_CARD_INDEX]
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[FIRST_CARD_INDEX].getGameValue(), 3)+pow(hand[FOURTH_CARD_INDEX].getGameValue(), 2)+hand[FIFTH_CARD_INDEX].getGameValue());
					}
					else if(u+1==3){//This means that the pair is hand[THIRD_CARD_INDEX] & hand[FOURTH_CARD_INDEX], so the highest to lowest non-pair values are, in descending order, hand[FIRST_CARD_INDEX], hand[SECOND_CARD_INDEX] and hand[FIFTH_CARD_INDEX]
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[FIRST_CARD_INDEX].getGameValue(), 3)+pow(hand[SECOND_CARD_INDEX].getGameValue(), 2)+hand[FIFTH_CARD_INDEX].getGameValue());
					}
					else //This means that the pair is hand[FOURTH_CARD_INDEX] & hand[FIFTH_CARD_INDEX], so the highest to lowest non-pair values are, in descending order, hand[FIRST_CARD_INDEX], hand[SECOND_CARD_INDEX] and hand[THIRD_CARD_INDEX]
						gameValue += (int) (pow((hand[u].getGameValue()*3), 4)+pow(hand[FIRST_CARD_INDEX].getGameValue(), 3)+pow(hand[SECOND_CARD_INDEX].getGameValue(), 2)+hand[THIRD_CARD_INDEX].getGameValue());//Finds the location of the pair in the hand and adds on the game value, the highest pair separates 2 one pairs.
				}
				u++;
			}
		}
		else if(isHighHand()){//Formula = HandOfCards.HIGH_HAND_DEFAULT+hand[0].getGameValue^5 + hand[1].getGameValue^4...
			int j=HAND_CAPACITY; //In the case two hands have the same high hands, the higher game value of the next highest card wins, if these are the same the next highest card's game value is compared if all card game values are the same in both hands, the pot is split.
			gameValue = HandOfCards.HIGH_HAND_DEFAULT;
			for(int i=0; i<HAND_CAPACITY; i++){
				gameValue += (int) pow(hand[i].getGameValue(), j);
				j--;
			}
		}
		
		return gameValue;
	}
	
	private boolean isFourFlush(){//Returns true if the hand contains four cards of the same suit
		int j = 0, sameSuitCounterFirstCard = 0, sameSuitCounterSecondCard = 0;
		while(j<HAND_CAPACITY){//Checks if there are 3 other cards in the hand that are the same suit as the first card
			if(hand[FIRST_CARD_INDEX].getSuit()==hand[j].getSuit()){
				sameSuitCounterFirstCard++;
			}
			j++;
		} 
		j=0;
		while(j<HAND_CAPACITY){//Can also be a four flush with 3 cards the same suit as the second card in the hand. Also have to check this
			if(hand[SECOND_CARD_INDEX].getSuit()==hand[j].getSuit()){
				sameSuitCounterSecondCard++;
			}
			j++;
		}
		if(sameSuitCounterFirstCard==4||sameSuitCounterSecondCard==4){
			return true;
		}
		else
			return false;
	}
	
	private boolean isOpenEndedStraight(){//Returns true if the hand contains an open ended straight, meaning 4 cards in a row of a straight missing a fifth card at either the beginning or end, eg 3,4,5,6
		int j=1, straightCounterFirstCard = 0, straightCounterSecondCard = 0;
		while(j<HAND_CAPACITY-1){
			if(hand[FIRST_CARD_INDEX].getGameValue()==hand[j].getGameValue()+j){//Checks if the first card has three other cards that's game values are sequentially after it, e.g. 6,5,4,3,x
				straightCounterFirstCard++;
			}
			j++;
		}
		j=2;
		while(j<HAND_CAPACITY){//Checks if the second card has three other cards that's game values are sequentially after it e.g. x,5,4,3,2
			if(hand[SECOND_CARD_INDEX].getGameValue()==hand[j].getGameValue()+(j-1)){
				straightCounterSecondCard++;
			}
			j++;
		}
		if(straightCounterFirstCard==3||straightCounterSecondCard==3){
			return true;
		}
		else
			return false;
	}
	
	private boolean isInsideStraight(){
		int j=1, straightCounterSpecialCase=0;
		while(j<HAND_CAPACITY-1){//Accounts for the special case where a hand has A,x,4,3,2 which is the only inside straight possible because any other one would contain one pair
			if(hand[FIRST_CARD_INDEX].getFaceValue()==hand[HAND_CAPACITY-j].getGameValue()-j){
				straightCounterSpecialCase++;
			}
			j++;
		}
		if(straightCounterSpecialCase==3){
			return true;
		}
		else
			return false;
	}
	
	private int fourOfAKindDiscardProbability(int cardPosition){
		if(cardPosition==HAND_CAPACITY-1&&hand[cardPosition].getGameValue()!=hand[cardPosition-1].getGameValue()&&hand[cardPosition].getGameValue()<5){ //Tests if the game value of the non four of a kind card is less than 5, if it is, the probability is that it should most likely be discarded
			return 90;
		}
		else if(cardPosition<HAND_CAPACITY-1&&hand[cardPosition].getGameValue()!=hand[cardPosition+1].getGameValue()&&hand[cardPosition].getGameValue()<5){
			return 90;
		}
		else return 2;
	}
	
	private int fullHouseDiscardProbability(int cardPosition){
		if((cardPosition>0&&hand[cardPosition-1].getGameValue()==hand[cardPosition+1].getGameValue())||(hand[cardPosition].getGameValue()==hand[cardPosition+2].getGameValue())||(cardPosition==HAND_CAPACITY-1&&hand[cardPosition].getGameValue()==hand[cardPosition-2].getGameValue())){//Means the card at cardPosition is part of the three of a kind and should most likely not be traded
			return 3;
		}
		else{//Otherwise the card is part of the pair in the hand and is still unlikely to be traded, but slightly more likely
			return 5;
		}
	}
	
	private int flushDiscardProbability(int cardPosition){
		if(hand[cardPosition].getGameValue()<5){//Checks if there are low cards in the flush with a game value of less than 5, if there are there is a possibility they could be traded in for cards of a higher face value
			return 7;
		}
		else
			return 4;
	}
	
	private int straightDiscardProbability(int cardPosition){
		if(hand[cardPosition].getGameValue()<5){//Checks if there are low cards in the straight with a game value of less than 5, if there are there is a possibility they could be traded in for cards of a higher face value
			return 9;
		}
		else
			return 5;
	}
	
	private int threeOfAKindDiscardProbability(int cardPosition){//Probability of improving a threeOfAKind to a fourOfAKind or full house is 8.7/1 = 11/100. Return 11 if hand[cardPosition] is not part of the three of a kind.
		if(hand[cardPosition].getGameValue()!=hand[THIRD_CARD_INDEX].getGameValue()){//The third card index card is guaranteed to be part of the three of a kind. Checks if hand[cardPosition] is not part of the three of a kind.
			return 11;
		}
		else
			return 0;
	}
	
	private int twoPairDiscardProbability(int cardPosition){//Probability of improving a 2 pair to a full house is 10.75/1 = 9/100. Return 9 if hand[cardPosition] is not part of either pair
		if(hand[cardPosition].getGameValue()!=hand[SECOND_CARD_INDEX].getGameValue()&&hand[cardPosition].getGameValue()!=hand[FOURTH_CARD_INDEX].getGameValue()){//If this is the case, card[handPosition] is not part of either pairs
			return 9;
		}
		else
			return 0;
	}
	
	private int onePairDiscardProbability(int cardPosition){//Probability of improving a one pair is 2.5/1 = 40/100. If the card is not part of the pair, return 40.
		if((cardPosition==FIRST_CARD_INDEX&&hand[cardPosition].getGameValue()!=hand[cardPosition+1].getGameValue())||((hand[cardPosition].getGameValue()!=hand[cardPosition+1].getGameValue()||hand[cardPosition].getGameValue()!=hand[cardPosition-1].getGameValue()))||(cardPosition==FIFTH_CARD_INDEX&&hand[cardPosition].getGameValue()!=hand[cardPosition-1].getGameValue())){//Checks that hand[cardPosition] isn't part of the pair
			return 40;
		}
		else//Means the card is part of the pair
			return 0;
		
	}
	
	private int highHandDiscardProbability(int cardPosition){
		if(isFourFlush()){//Probability of improving a 4 flush high hand to a flush is 4.2/1 = 24/100.
			if(cardPosition==FIRST_CARD_INDEX){
				if((hand[cardPosition].getSuit()!=hand[cardPosition+1].getSuit()&&hand[cardPosition].getSuit()!=hand[cardPosition+2].getSuit())){
					return 24;
				}
				else
					return 0;
			}
			else if(cardPosition==FIFTH_CARD_INDEX){
				if(hand[cardPosition].getSuit()!=hand[cardPosition-1].getSuit()&&hand[cardPosition].getSuit()!=hand[cardPosition-2].getSuit()){
					return 24;
				}
				else
					return 0;	
			}
			else if((hand[cardPosition].getSuit()!=hand[cardPosition-1].getSuit())&&(hand[cardPosition].getSuit()!=hand[cardPosition+1].getSuit())){
				return 24;
			}
			else
				return 0;
		}
		else if(isOpenEndedStraight()){//Probability of improving an open ended straight high hand to a straight is 5/1 = 20/100
			return 20;
		}
		else if(isInsideStraight()){
			return 9;
		}
		else
			return 0;
	}
		
	public int getDiscardProbability(int cardPosition){
		if(cardPosition<0||cardPosition>=HAND_CAPACITY||isRoyalFlush()){//Tests that the passed card position belongs to a deck
			return 0;
		}
		else if(isStraightFlush()){
			return 1;
		}
		else if(isFourOfAKind()){//Determines if card not part of four of a kind is below a game value of 5, if so, it should most likely be discarded
			return fourOfAKindDiscardProbability(cardPosition);
		}
		else if(isFullHouse()){
			return fullHouseDiscardProbability(cardPosition);
		}
		else if(isFlush()){
			return flushDiscardProbability(cardPosition);
		}
		else if(isStraight()){
			return straightDiscardProbability(cardPosition);
		}
		else if(isThreeOfAKind()){
			return threeOfAKindDiscardProbability(cardPosition);
		}
		else if(isTwoPair()){
			return twoPairDiscardProbability(cardPosition);	
		}
		else if(isOnePair()){
			return onePairDiscardProbability(cardPosition);	
		}
		else{
			return highHandDiscardProbability(cardPosition);
		}
	}
	
	public static void main(String[] args){ //The main method generates a hand of cards, prints out the toString() representation of each card and then the best possible poker hand it belongs to is printed
	//BELOW GENERATES 2 OF THE SAME CATEGORY OF HAND FOR ALL POSSIBLE CATEGORISATIONS OF HANDS AND COMPARES THEM TO TEST THE getGameValue method, EXCLUDING THE ROYAL FLUSH AS IT TAKES A LONG TIME TO GENERATE AND WILL ALWAYS RESULT IN A DRAW AND A SPLIT POT. 
		boolean achieved = false;
		DeckOfCards CardDeck = new DeckOfCards();
		HandOfCards CardHand = new HandOfCards(CardDeck);
		HandOfCards CardHand1 = new HandOfCards(CardDeck);
		int[] comparisonArray = new int[10];
		int i=1, j=0;
		comparisonArray[0] = ROYAL_FLUSH_DEFAULT; //As royal flush isn't tested, its default value is added to the comparison array
		System.out.println("For testing, the program will now generate 2 instances of each type of hand, excluding royal flush, and determine a winner between them:");
		
		
		
		//TESTS 2 STRAIGHT FLUSHES AGAINST EACH OTHER:
			System.out.println("\nTesting High Hand:");
			int discardProb=0;
			while(discardProb!=24){
				CardDeck.reset();
				CardHand = new HandOfCards(CardDeck);				
				if(CardHand.isHighHand()){
					System.out.println("Hand 1: "+CardHand.handString()+"\tGame value: "+CardHand.getGameValue());
						System.out.println(CardHand.getDiscardProbability(0));
						System.out.println(CardHand.getDiscardProbability(1));
						System.out.println(CardHand.getDiscardProbability(2));
						System.out.println(CardHand.getDiscardProbability(3));
						System.out.println(CardHand.getDiscardProbability(4));
						discardProb = CardHand.getDiscardProbability(0)+CardHand.getDiscardProbability(1)+CardHand.getDiscardProbability(2)+CardHand.getDiscardProbability(3)+CardHand.getDiscardProbability(4);				
						}
			}

			achieved = false;
			i++;
	}

	
}