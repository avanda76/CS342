import java.util.ArrayList;
class BaccaratGameLogic
{
	public String whoWon(ArrayList<Card> hand1, ArrayList<Card> hand2)
	{
		int distance_hand1 = Math.abs(handTotal(hand1) - 9); //distance of how close it is to 9 (Player)
		int distance_hand2 = Math.abs(handTotal(hand2) - 9); //distance of how close it is to 9 (Dealer)
		//Checks who is closer
		if(distance_hand1 < distance_hand2 )//If player is closer to 9 then player wins
		{
			return "Player Wins";
		}	
		else if(distance_hand1 > distance_hand2 )//If banker is closer to 9 then banker wins
		{
			return "Banker Wins";
		}
		else if(distance_hand1 == distance_hand2)//They are equal
		{
			return "Draw";
		}
		else
			return "";	
	}
	public int handTotal(ArrayList<Card> hand)//Counts the points you have in hand
	{
		int count = 0;
		for(Card c : hand)//Loops through cards 
		{
			if(c.value > 9)//Ignores if it is 10 or face card
				continue;
			else
				count += c.value;//Adds all values
		}
		if(count > 9)
			count = count % 10;
		return count;
	}
	public boolean evaluateBankerDraw(ArrayList<Card> hand, Card playerCard)//Checks if banker can draw
	{
		int val = playerCard.value;
		if(playerCard.value > 9)//if the card is a face card then the value is zero
			val = 0;
		int score = handTotal(hand);
		if(hand.size() == 2)//if the banker has not drawn yet
		{
			//Based on the table from the baccarat rules
			if(score <= 2)
				return true;
			else if(score == 3 && val != 8)
				return true;
			else if(score == 4 && val != 0 && val != 1 && val != 8 && val != 9)
				return true;
			else if(score == 5 && val != 0 && val != 1 && val != 2 && val != 3 && val != 8 && val != 9)
				return true;
			else if(score == 6 && val == 6)
				return true;
			else if(score == 6 && val == 7)
				return true;
		}
		return false;
	}
	public boolean evaluatePlayerDraw(ArrayList<Card> hand)//Checks if player can draw
	{
		int score = handTotal(hand);
		if(score <= 5 && hand.size() == 2)//If hasnt drawn yet and size is 5 or less
			return true;
		else
			return false;
	}
}