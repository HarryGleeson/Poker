package Poker;

import java.util.Random;

public class DeckOfCards {
	private PlayingCard[] cards = new PlayingCard[52];
	private String[] type = new String[] {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
	private int[] gameValues = new int[] {14, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	private int[] faceValues = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
	private int[] arrayIndex = new int[52];
	private char[] suits = new char[] {PlayingCard.HEARTS, PlayingCard.DIAMONDS, PlayingCard.CLUBS, PlayingCard.SPADES};
	private int x=0, i=0, z = 0;
	
	public DeckOfCards(){ //Constructor uses loop to create all 52 cards in a deck
		while(z<52){ //Iterates through array of PlayingCards and populates it with the 52 cards in a deck 
			for(i=0; i<13; i++){
				cards[z] = new PlayingCard(type[i], suits[x], faceValues[i], gameValues[i]);
				arrayIndex[z] = z;
				z++;
			}
			x++;
			i=0;
			}
	}
	
	void reset(){// Reinitializes deck with all of the cards
		z=0;
		x=0;
		while(z<52){ //Iterates through array of PlayingCards and populates it with the 52 cards in a deck 
			for(i=0; i<13; i++){
				cards[z] = new PlayingCard(type[i], suits[x], faceValues[i], gameValues[i]);
				arrayIndex[z] = z;
				z++;
			}
			x++;
			i=0;
			}
	}
	
	void shuffle(){// Mixes up the cards in the deck by choosing 2 random cards at a time in the deck and swapping their positions
		Random rand1 = new Random();
		Random rand2 = new Random();
		
		int r = 0;
		while (r<(52*52)){ 
			int randomCard1 = rand1.nextInt(52); // Generates 2 random integers, each representing a random card in the deck
			int randomCard2 = rand2.nextInt(52);
			PlayingCard temp = cards[randomCard1]; // Swaps the 2 cards at the position of the 2 random integers in the deck
			cards[randomCard1] = cards[randomCard2];
			cards[randomCard2] = temp;
			r++;
		}
	}
	
	PlayingCard dealNext(){// Goes through the deck in order and deals the next time in the deck each time it is called
		if(i>51){// Checks that not all of the cards in the deck have been dealt, if they have, return to start of deck
			i=1;
		}
		else{
			i++;
		}
		return cards[i-1];
	}
	
	void returnCard(PlayingCard discarded){// Once a card has been dealt, it cannot be dealt again and therefore its value is set to null
		cards[i-1] = null;
	}
	
	public static void main(String[] args) {		
		DeckOfCards CardDeck = new DeckOfCards();
		CardDeck.shuffle();
		int counter=0;
		PlayingCard deal;
		while(counter < 52){
		deal = CardDeck.dealNext();
		System.out.println(deal);
		CardDeck.returnCard(deal);
		counter++;
		}
		System.out.println(CardDeck.dealNext());
		CardDeck.reset();

	}
	
}
