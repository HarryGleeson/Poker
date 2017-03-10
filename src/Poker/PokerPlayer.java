package Poker;

import java.util.Random;

public class PokerPlayer {
	private static DeckOfCards cardDeck = new DeckOfCards();

	private static HandOfCards hand = new HandOfCards(cardDeck);
	
	public PokerPlayer(DeckOfCards cardDeck){
		HandOfCards cardHand = new HandOfCards(cardDeck);
		hand = cardHand;
		returnHandOfCards(cardHand);
		System.out.println(cardHand.handString());
	}
	
	public int discard(){
		Random rand1 = new Random();
		int discardNumber = 0;
		int discardProbability = 0;
		for(int i = 0; i<HandOfCards.HAND_CAPACITY; i++){
			discardProbability = hand.getDiscardProbability(i);
			if(discardProbability == 100){
				hand.discardCard(i); 
				discardNumber++;
			}
			else if(discardProbability!=0){
				int random = rand1.nextInt(101); //Generates a random number between 1 and 100, if the number is less than the probability, it is to be discarded, if it is, it is not to be discarded.
					if(random<=discardProbability){
						hand.discardCard(i);
						discardNumber++;
					}
			}

		}
		
		return discardNumber;
	}
	
	private HandOfCards returnHandOfCards (HandOfCards cardHand){
		return cardHand;
		
	}
	public static void main(String[] args) {
		int loopCounter = 0;
		int improvedHandCounter = 0;
		while(loopCounter<1000){
		 cardDeck.reset();
		 PokerPlayer player = new PokerPlayer(cardDeck);
		 int initialGameValue = player.hand.getGameValue();
		 int discardNumber = player.discard();
		 hand.sort();
		 int postSwapGameValue = hand.getGameValue();
		 if(postSwapGameValue>initialGameValue){
			 
			 improvedHandCounter++;
			 System.out.println("Hand Improved: "+hand.handString());

		 }
		loopCounter++;
		}
	System.out.println("Hand improved "+improvedHandCounter+" times out of 1000.");	 
	}

}
