import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

class DealerTest {
	//Cards and Classes
	BaccaratDealer test;
	BaccaratGameLogic logic;
	BaccaratGame testGame;
	Card testCard;
	Card testCard_2;
	Card testCard_3;
	Card testCard_4;
	@BeforeEach
	void init()
	{
		//Created some classes and cards used for the test cases. Initialized the variables
		test = new BaccaratDealer();
		logic  = new BaccaratGameLogic();
		testGame = new BaccaratGame();
		testCard = new Card("Heart", 9);
		testCard_2 = new Card("Spade", 4);
		testCard_3 = new Card("Diamonds", 4);
		testCard_4 = new Card("Clubs", 4);
		test.generateDeck(); // generates the deck for the game
	}

	//Testing for class names using their constructors//
	@Test
	void testInitBD()  //testing Baccarat Dealer constructor
	{
		assertEquals("BaccaratDealer", test.getClass().getName(), "did not initialize proper object");
	}

	@Test
	void testInitBGL() //Testing the Baccarat Game Logic constructor
	{
		assertEquals("BaccaratGameLogic", logic.getClass().getName(), "did not initialize proper object");
	}

	@Test
	void testInitCard() //Testing the constructor of Card
	{
		assertEquals("Card", testCard.getClass().getName(), "did not initialize proper object");
	}

	@Test
	void testInitBG() //Testing the of Baccarat Game
	{
		assertEquals("BaccaratGame", testGame.getClass().getName(), "did not initialize proper object");
	}
	//Testing for class names using their constructors//

	/////Card Class Tests/////
	
	@Test
	void testSuite()
	{
		assertEquals("Spades", test.deck.get(1).suite, "Error");
	}
	
	@Test
	void testValue()
	{
		assertEquals(2, test.deck.get(1).value, "Error");
	}



	/////Card Class Tests End /////

	/////BaccaratDealer Tests/////
	@Test
	void testGenerateDeck()
	{
		//Generates all of the cards in the deck
		assertEquals(52, test.deckSize(), "Wrong amount");
	}

	void testGenerateDeck2()
	{
		//see if it generates one card on the deck
		ArrayList<Card> deck = new ArrayList<Card>();



		Card card = new Card("Spades",1);
		deck.add(card);


		assertEquals(1, test.deckSize(), "Wrong amount");
	}

	@Test
	void testDrawOne()
	{
		//Makes a deck, shuffles it, goes through the loop and finds if the card would have the same suite as the one drawn
		ArrayList<Card> deck = new ArrayList<Card>();
		test.shuffleDeck();


		for(Card c1: deck)
		{
			assertEquals(c1.suite, test.drawOne().suite, "ERROR");
		}



	}

	@Test
	void testDrawOne2()
	{
		//Makes a deck, shuffles it, goes through the loop and finds if the card would have the same face value as the one drawn
		ArrayList<Card> deck = new ArrayList<Card>();
		test.shuffleDeck();

		for(Card c1: deck)
		{
			assertEquals(c1.value, test.drawOne().value, "ERROR");
		}

	}

	@Test
	void testDeckSize()
	{
		//pick up a card, test to see if it got one left
		test.drawOne();
		assertEquals(51, test.deckSize());
	}
	@Test
	void testDeckSize2()
	{
		// deck size if nothing happened
		assertEquals(52, test.deckSize());
	}


	@Test
	void testShuffleDeck()
	{
		//Shuffling deck and returns to see if the first card came up
		test.shuffleDeck();
		assertNotEquals(1, test.deck.get(0));
	}

    @Test
    void testShuffleDeck2()
    {
    	//Shuffles the deck expects to see if the second card came up
        test.shuffleDeck();
        assertNotEquals(2, test.deck.get(1));
    }

	@Test
	void testDealHand()
	{

		

		assertNotEquals(1, test.deck.get(0), "1st Card Matches");
		assertNotEquals(2, test.deck.get(1), "2nd Card Matches");

	}
	@Test
	void testDealHand2()
	{

		ArrayList<Card> hand = test.dealHand();
		assertEquals(2, hand.size(), "ERROR ON SIZE");

	}


	/////BaccaratDealer Tests/////

	//BaccaratGameLogic Tests//

	@Test
		void testInitwhoWon1()
		{
			//creating two hands for the player and banker
			ArrayList<Card> playerHand = new ArrayList<>(2);
			ArrayList<Card> bankerHand = new ArrayList<>(2);

			//playerHand cards
			playerHand.add(testCard);
			playerHand.add(testCard_2);

			//bankerHand cards
			bankerHand.add(testCard_3);
			bankerHand.add(testCard_2);

			//Banker has a higher value of cards and wins
			assertEquals("Banker Wins", logic.whoWon(playerHand, bankerHand), "did not get the right winner");
		}

		@Test
		void testInitwhoWon2()
		{
			//creating two hands for the player and banker
			ArrayList<Card> playerHand = new ArrayList<>(2);
			ArrayList<Card> bankerHand = new ArrayList<>(2);

			//playerHand cards
			playerHand.add(testCard);
			playerHand.add(testCard_2);
			playerHand.add(testCard_3);

			//bankerHand cards
			bankerHand.add(testCard);
			bankerHand.add(testCard_2);
			bankerHand.add(testCard_4);

			//both add up the same value on player and banker, so its a draw
			assertEquals("Draw", logic.whoWon(playerHand, bankerHand), "did not get the right winner");
		}

		@Test
		void testInitHandTotal()
		{
			//hand total testing in ones hand

			ArrayList<Card> playerHand = new ArrayList<>(3);

			//add three cards to see the hand total
			playerHand.add(testCard);
			playerHand.add(testCard_2);
			playerHand.add(testCard_3);

			//sum is 7 after the arithmetic
			assertEquals(7, logic.handTotal(playerHand), "did not initialize proper object");
		}

		@Test
		void testInitHandTotal2()
		{
			//hand total testing in ones hand
			ArrayList<Card> bankerHand = new ArrayList<>(2); //new hand

			 //added 2 cards
			bankerHand.add(testCard);
			bankerHand.add(testCard_2);

			//total will be 3
			assertEquals(3, logic.handTotal(bankerHand), "did not initialize proper object");
		}

		@Test
		void testEvalBD()
		{
			//Created two hands
			ArrayList<Card> bankerHand = new ArrayList<>(2);
			ArrayList<Card> playerHand = new ArrayList<>(2);

			//Lets have a new card
			Card tCard4 = new Card("Diamonds", 6);


			playerHand.add(testCard);
			playerHand.add(testCard_2);

			bankerHand.add(testCard_3);
			bankerHand.add(tCard4);
	        //will check if it needs to pick one card

			assertEquals(true, logic.evaluateBankerDraw(bankerHand, testCard_2), "did not initialize proper object");
		}
		@Test
		void testEvalBD2()
		{
			//created two hands
			ArrayList<Card> bankerHand = new ArrayList<>(2);
			ArrayList<Card> playerHand = new ArrayList<>(2);
			Card tCard4 = new Card("Diamonds", 7);

			//added cards to hands
			playerHand.add(testCard);
			playerHand.add(testCard_2);
			playerHand.add(tCard4);

			bankerHand.add(testCard);
			bankerHand.add(testCard_3);

			assertEquals(true, logic.evaluateBankerDraw(bankerHand, testCard_2), "did not initialize proper object");
		}
		@Test
		void testEvalPD()
		{
			//2 hands for the banker and player
			ArrayList<Card> bankerHand = new ArrayList<>(2);
			ArrayList<Card> playerHand = new ArrayList<>(2);

			//added cards player and banker
			playerHand.add(testCard);
			playerHand.add(testCard_2);

			bankerHand.add(testCard);
			bankerHand.add(testCard_3);

			assertEquals(true, logic.evaluatePlayerDraw(playerHand), "did not initialize proper object");
		}

		@Test
		void testEvalPD2()
		{
			//2 hands for the banker and player
			ArrayList<Card> bankerHand = new ArrayList<>(2);  //set up two hands added cards and check to see
			ArrayList<Card> playerHand = new ArrayList<>(2);   //if it will draw another card
			Card testCard4 = new Card("Diamonds", 6);

			//added cards player and banker
			playerHand.add(testCard);
			playerHand.add(testCard4);

			bankerHand.add(testCard_3);
			bankerHand.add(testCard);

			assertEquals(true, logic.evaluatePlayerDraw(playerHand), "did not initialize proper object");
		}

		@Test
		void testEvaluateWinnings1()
		{
			ArrayList<Card> playerHand = new ArrayList<Card>();
			ArrayList<Card> bankerHand = new ArrayList<Card>();
			String s = logic.whoWon(playerHand, bankerHand);
			boolean bankerBid = false;
			double totalWinnings = 10.0;
			double currentBet = 1.0;
			double total_Payout;

			if(s == "Banker Wins" && bankerBid == true)
			{
				total_Payout = currentBet + totalWinnings;
				assertEquals(11, total_Payout, "Error");
			}

		}
		//GameLOGIC Complete//


		//BeginBacarrat game//
		
		//Testing for evaluating the wins: Adds the player hands an
		@Test
		void testEvaluateWinnings2()
		{

			ArrayList<Card> playerHand = new ArrayList<Card>();
			ArrayList<Card> bankerHand = new ArrayList<Card>();
			String s = logic.whoWon(playerHand, bankerHand);
			boolean playerBid = false;
			double totalWinnings = 10.0;
			double currentBet = 1.0;
			double total_Payout;

			if(s == "Player Wins" && playerBid == true)
			{
				total_Payout = currentBet + totalWinnings;
				assertEquals(11, total_Payout, "Error");
			}


		}

		

		//End Bacarrat game//


}
