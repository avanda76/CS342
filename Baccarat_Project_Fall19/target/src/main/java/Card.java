class Card
{
	String suite;
	int value;
	
	//Constructor for the card: takes in 2 parameters String 
	//suite of what the card suite is and the value of the card too.
	Card(String theSuite, int theValue)
	{
		//References the string and integer value.
		this.suite = theSuite;
		this.value = theValue;
	}
}