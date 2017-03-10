package Poker;

import java.util.Random;

public class PokerPlayer {
	private static DeckOfCards cardDeck = new DeckOfCards();

	private static HandOfCards hand = new HandOfCards(cardDeck);
	
	public PokerPlayer(DeckOfCards cardDeck){
		HandOfCards cardHand = new HandOfCards(cardDeck);
		hand = cardHand;
		System.out.println("Initial Hand: "+cardHand.handString()+" Game Value:"+ cardHand.getGameValue());
	}
	
	public int discard(){//This method discards cards depending on their discard probability and returns the number of cards that were discarded
		Random randomProbability = new Random();
		int discardNumber = 0;
		int discardProbability = 0;
		for(int i = 0; i<HandOfCards.HAND_CAPACITY; i++){
			discardProbability = hand.getDiscardProbability(i);
			if(discardProbability == 100){ //Means the card at index i should be 100% discarded
				hand.discardCard(i, cardDeck.dealNext()); //Implemented new method in DeckOfCards to replace a card in a hand at index i with the next card dealt from the deck		
				discardNumber++;
			}
			else if(discardProbability!=0){//Means the probability that the card at index i should potentially be discarded
				int randomNumber = randomProbability.nextInt(101); //Generates a random number between 1 and 100, if the number is less than the probability, it is to be discarded, if it is, it is not to be discarded.
					if(randomNumber<=discardProbability){
						hand.discardCard(i, cardDeck.dealNext());
						discardNumber++;
					}
			}

		}
		return discardNumber;
	}
	
	public static void main(String[] args) { //The main method tests 1000 hands that have swapped at least 1 card and counts the number of times the hands improved in game value
		int loopCounter = 0;
		int improvedHandCounter = 0;
		while(loopCounter<1000){
			cardDeck.reset(); //Reinitializes the deck of cards for each iteration of the loop
			PokerPlayer player = new PokerPlayer(cardDeck);
			int initialGameValue = player.hand.getGameValue();
			int discardCounter = player.discard(); //Gets the new hand with the replacements for discarded cards as well as the number of cards discarded
			hand.sort();
			int postSwapGameValue = hand.getGameValue();
			System.out.println("Swapped "+discardCounter+" card(s).");
			System.out.println("Hand Post Swap: "+hand.handString()+" Game Value:"+postSwapGameValue);
			if(discardCounter>0){
				loopCounter++; //Want only to test for hands where there were cards swapped
			}
			if(postSwapGameValue>initialGameValue){
				System.out.println("Swap improved hand!");
				improvedHandCounter++;
			}
			System.out.println();
		}
		System.out.println("When at least 1 card was swapped, hand improved "+improvedHandCounter+" times out of 1000.");	 
	}
}
