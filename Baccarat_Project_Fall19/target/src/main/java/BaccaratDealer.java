import java.util.ArrayList;
import java.util.Collections;
class BaccaratDealer
{
	String suites[] = new String[] {"Spades", "Hearts", "Diamonds", "Clubs"};//String of suites
	ArrayList<Card> deck;
	BaccaratDealer()//Constructor
	{
		deck = new ArrayList<Card>();
	}
	public void generateDeck()//Generates new deck of cards
	{
		deck.clear();//clears deck in order to create new one
		for(int i = 0; i < 4; i++)//loop for the suites
		{
			for(int j = 1; j <= 13; j++)//make 13 cards for every other suite
			{
				Card card = new Card(suites[i],j);
				deck.add(card);
			}
		}
	}
	public ArrayList<Card> dealHand()//Deals player 2 cards
	{
		shuffleDeck();//Shuffle deck
		ArrayList<Card> hands = new ArrayList<Card>();//Makes new hand to return
		hands.add(deck.get(0));
		hands.add(deck.get(1));
		deck.remove(0);
		deck.remove(0);
		return hands;	
	}
	public Card drawOne()//Draw one card from deck
	{
		shuffleDeck();
		Card c1  = deck.get(0);
		deck.remove(0);
		return c1;
	}
	public void shuffleDeck()//Shuffles deck
	{
		generateDeck();
		Collections.shuffle(deck);
	}
	public int deckSize()//Get size of deck
	{
		return deck.size();
	}
}
