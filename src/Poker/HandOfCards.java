package Poker;


public class HandOfCards {
	private int handCapacity = 5;
	private PlayingCard[] hand = new PlayingCard[handCapacity];
	private int[] royalFlushGameValue = new int[]{14, 13, 12, 11, 10};
	private DeckOfCards deck = new DeckOfCards();
	private PlayingCard temp;
	
	public HandOfCards(DeckOfCards deck){
		this.deck = deck;
		for(int i=0; i<handCapacity; i++){
		hand[i] =deck.dealNext();
		}
		sort();
	}
	
	public DeckOfCards ReturnDeck(){
		return deck;
	}
	
	private boolean sameSet(){ //Tests if all of the cards in the hand are of the same set, made its own method to remove duplicate code
		int i=0;
		while (i < handCapacity-1) { 
			if (hand[i].getSuit() == hand[i+1].getSuit()){
				i++;
			}
			else
				return false;
		}
		return true;
	}
	
	private boolean isSequential(){ //Determines if hands in card are sequence of 5 consecutive game values, made its own method to remove duplicate code
		int i=0, j=1;
		while(j<handCapacity){
			//***DONT HAVE SORTED FOR A,2,3,4,5*****
			if (hand[i].getGameValue()-j == hand[j].getGameValue()){//Checks if cards in hand are consecutive in their game values
				j++;
			}
			else{
				return false;
			} 
		}
		return true;
	
	}
	
	private void sort(){ //Uses a Bubble sort to sort the cards in order of their game value.
		for (int i = 0; i < handCapacity-1; i++) {
		 for(int j=1;  j < handCapacity-i;  j++ )
         {
                if ( hand[j-1].getGameValue() < hand[j].getGameValue() )   
                {
                        temp = hand[j-1];               
                        hand[j-1] = hand[j];
                        hand[j] = temp;
               } 
         }
		}
	     
	for(int i=0; i<handCapacity; i++){
		System.out.println(hand[i].toString());
	}
	System.out.println();
	}
	
	public boolean isRoyalFlush() {
		boolean set = sameSet();
		if (!set){
			return false;
		}
		else{
		int i=0;
		while (i < handCapacity) { //If all cards are of the same suit, the game values of each card is compared to the array containing the game values of a royal flush
			if (hand[i].getGameValue() == royalFlushGameValue[i]){
				i++;
			}
			else
				return false;
		}
		return true;
		}
	}
	
	public boolean isStraightFlush(){
		boolean set = sameSet();
		boolean sequential = isSequential();
		if(isRoyalFlush()||!set||!sequential){
			return false;
		}
		return true;
				
	}
	
	public boolean isFourOfAKind() {
		if(isRoyalFlush()||isStraightFlush()){
			return false;
		}
		
		else{
			int u=0;
				while(u+3<handCapacity){
				if(hand[u].getFaceValue() == hand[u+3].getFaceValue()){ //Array is sorted already so cards with same face value automatically beside each other. Meaning if a card has the same face value as one 3 away from it there are 4 of a kind
					return true;
				}
				u++;
				}
			}
		return false;
		}
		
	
	public boolean isFullHouse() {
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
	
	public boolean isFlush() {
		boolean set = sameSet();
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||!set){
			return false;
		}
		return true;
		}
	
	public boolean isStraight(){ //**NEED TO ADAPT FOR OTHER FLUSHES EG Q,K,A,2,3***
		boolean sequential = isSequential();
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||!sequential){
			return false;
		}
		return true;
		
		}
	
	public boolean isThreeOfAKind(){
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()){
			return false;
		}
		else{
			int u=0;
			while(u+2<handCapacity){
				if(hand[u].getFaceValue() == hand[u+2].getFaceValue()){ //Array is sorted already so cards with same face value automatically beside each other. Meaning if the first and fourth or second and fifth values are the same there are 4 of a kind.
					return true;
				}
				u++;
				}
			}
		
	return false;
		
	}
		
	
	public boolean isTwoPair(){
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()){
			return false;
		}
		else{
			if((hand[0].getFaceValue() == hand[1].getFaceValue()&&(hand[2].getFaceValue() == hand[3].getFaceValue()||hand[3].getFaceValue() == hand[4].getFaceValue()))||hand[1].getFaceValue() == hand[2].getFaceValue()&&hand[3].getFaceValue() == hand[4].getFaceValue()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isOnePair() {
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()||isTwoPair()){
			return false;
		}
		else{
			if(hand[0].getFaceValue() == hand[1].getFaceValue()||hand[1].getFaceValue() == hand[2].getFaceValue()||hand[2].getFaceValue() == hand[3].getFaceValue()||hand[3].getFaceValue() == hand[4].getFaceValue()){
				return true;
			}
		}
		return false;
	}
	
	public boolean isHighHand(){
		if(isRoyalFlush()||isStraightFlush()||isFourOfAKind()||isFullHouse()||isFlush()||isStraight()||isThreeOfAKind()||isTwoPair()||isOnePair()){
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		
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
