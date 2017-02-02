// Harry Gleeson - 14455822 - COMP30050 - Assignment 1
package Poker;

public class PlayingCard {
	static public final char HEARTS = 'H'; 
	static public final char DIAMONDS = 'D'; 
	static public final char CLUBS = 'C'; 
	static public final char SPADES = 'S'; 
	
	private String type;
	private char suit;
	private Integer face;
	private Integer game;
	
	public PlayingCard(String type, char suit, int face, int game) { //Constructor for PlayingCards
		this.type = type;
		this.suit = suit;
		this.face = face;
		this.game = game;
	}
	
	
	public String toString(){ //Public accessor returns 2 character string representing suit and card type of given card
		return type+suit;
	}
	
	public int getFaceValue(){ //Public accessor returns integer representing face value of given card
		return face;
	}

	public int getGameValue(){ //Public accessor returns integer representing game value of given card
		return game;
	}
	
	public char getSuit(){ //Public accessor returns character representing suit of given card
		return suit;
	}

	public static void main(String[] args) {
		PlayingCard[] cards = new PlayingCard[52];
		String[] type = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
		int[] gameValues = new int[] {14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
		int[] faceValues = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};		
		char[] suits = new char[] {PlayingCard.HEARTS, PlayingCard.DIAMONDS, PlayingCard.CLUBS, PlayingCard.SPADES};
		int x=0, i, z = 0;
		while(z<52){ //Iterates through array of PlayingCards and populates it with the 52 cards in a deck 
		for(i=0; i<13; i++){
			cards[z] = new PlayingCard(type[i], suits[x], faceValues[i], gameValues[i]);
			z++;
		}
		x++;
		i=0;
		}
		
		for (z=0; z<52; z++){ //Prints the string representation for all cards in the deck
			System.out.println(cards[z].toString());		
		}		
	}		
}