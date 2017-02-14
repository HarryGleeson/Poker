package Poker;


public class HandOfCards {
	private int handCapacity = 5;
	private PlayingCard[] hand = new PlayingCard[handCapacity];
	private int[] royalFlushGameValue = new int[]{14, 13, 12, 11, 10}; //To allow to see if game values correspond to that of a royal flush
	private int[] wrappedStraightGameValue = new int[]{14, 5, 4, 3, 2}; //To allow for comparison of special straight case A,2,3,4,5
	private DeckOfCards deck = new DeckOfCards();
	private PlayingCard temp;
	
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
	     
		for(int i=0; i<handCapacity; i++){
			System.out.println(hand[i].toString());
		}
	}
	
	//**For all of the following methods to determine what hand you have, they will check that the hand is not belonging to a stronger hand
	
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
	
	public static void main(String[] args){ //The main method generates a hand of cards, prints out the toString() representation of each card and then the best possible poker hand it belongs to is printed
		DeckOfCards CardDeck = new DeckOfCards();
		CardDeck.shuffle();
		HandOfCards CardHand = new HandOfCards(CardDeck);
		boolean rf = CardHand.isRoyalFlush();
		boolean sf = CardHand.isStraightFlush();
		boolean fk = CardHand.isFourOfAKind();
		boolean fh = CardHand.isFullHouse();
		boolean fl = CardHand.isFlush();
		boolean st = CardHand.isStraight();
		boolean tk = CardHand.isThreeOfAKind();
		boolean tp = CardHand.isTwoPair();
		boolean op = CardHand.isOnePair();
		boolean hh = CardHand.isHighHand();
		if (rf)
			System.out.println("Royal Flush");
		if (sf)
			System.out.println("Straight Flush");
		if (fk)
			System.out.println("Four Of A Kind");
		if (fh)
			System.out.println("Full House");
		if (fl)
			System.out.println("Flush");
		if (st)
			System.out.println("Straight");
		if (tk)
			System.out.println("Three Of A Kind");
		if (tp)
			System.out.println("Two Pair");
		if (op)
			System.out.println("One Pair");
		if (hh)
			System.out.println("High Hand");
	}
}