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
			System.out.println("Discard Probability: "+discardProbability);
			if(discardProbability == 100){
				hand.discardCard(i); 
				discardNumber++;
			}
			else if(discardProbability!=0){
				int random = rand1.nextInt(101); //Generates a random number between 1 and 100, if the number is less than the probability, it is to be discarded, if it is, it is not to be discarded.
				System.out.println("Random = "+random);
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
		 cardDeck.reset();
		 PokerPlayer player = new PokerPlayer(cardDeck);
		 int discardNumber = player.discard();
		 hand.sort();
		 System.out.println(discardNumber);
		 System.out.println(hand.handString());
		 
	}

}
