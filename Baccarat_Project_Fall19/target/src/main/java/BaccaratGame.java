import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//import javafx.scene.text.FontWeight;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Iterator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
public class BaccaratGame extends Application
{
	//Initializes hands for banker and player and old hands of those cards
	ArrayList<Card> playerHand;
	ArrayList<Card> bankerHand;
	//Creates dealer and logic
	BaccaratDealer theDealer = new BaccaratDealer();
	BaccaratGameLogic gameLogic = new BaccaratGameLogic();
	//Keeps track of bet amount and total winnings
	double currentBet = 0;
	double totalWinnings = 0;
	
	MenuBar menu;//Menu with drop down options 
	Button play;//play game
	VBox pane;
	HashMap<String, Scene> sceneMap; 
	PauseTransition pause = new PauseTransition(Duration.seconds(3));//For natural win
	PauseTransition drawFirst = new PauseTransition(Duration.seconds(4));//For player draw
	PauseTransition drawWait = new PauseTransition(Duration.seconds(5));//For banker draw
	PauseTransition endScreen = new PauseTransition(Duration.seconds(6));////For banker draw
	//Checks what player bid on
	boolean playerBid;
	boolean bankerBid;
	boolean drawBid;
	//Checks for end of game
	boolean end;
	//Shows 3rd card
	boolean showP;
	boolean showB;
	//Checks if can go to next scene
	boolean nextScene;
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		launch(args);
	}
	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		BorderPane titleCard = new BorderPane();//Pane for title
		Text title = new Text("Baccarat: The Movie\nThe Game based on the Card Game");//Title Name
		title.setStyle("-fx-font: 15px Verdana;" + "-fx-fill: purple;");//CSS for title
		title.setTextAlignment(TextAlignment.CENTER);//Aligns text
		sceneMap = new HashMap<>();//Scene Manager
		play = new Button("Lets Play!!");//Initiates game
		play.setMaxSize(270, 280);//Sets size of button
		play.setTextFill(Color.RED);//Sets color of text
		play.setStyle("-fx-background-color: Black;");//Button is black
		pane = new VBox(title,play);//new VBox for menu
		titleCard.setCenter(pane);//Sets title and button to center
		titleCard.setPadding(new Insets(250,0,0,150));//Sets padding for title card
		//Event handler for play button
		EventHandler<ActionEvent> start = new EventHandler<ActionEvent>()//Handles if you press start button
		{
            public void handle(ActionEvent event)
            {

                Button b = (Button)event.getSource();//If clicked
                if(b == play)
                {
                	primaryStage.setScene(Bid(primaryStage));//Goes to bid scene
            		primaryStage.show();
                }
            }
		};
		play.setOnAction(start);//sets even to play button
		/////////
		titleCard.setStyle("-fx-background-color: #32CD32;");//sets style for background
		// TODO Auto-generated method stub
		primaryStage.setTitle("Let's Play Baccarat!!!");//Gives title
		Scene scene = new Scene(titleCard,600,600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	public Scene Bid(Stage primaryStage)
	{
		end = false;//Game not finished
		nextScene = false;//Do not load new scene yet
		BorderPane bidding = new BorderPane();//Pane for bidding scene
		GridPane gPane = new GridPane();//Pane for button
		//Buttons for betting on who will win
		Button Player = new Button("Player");
		Button Banker = new Button("Banker");
		Button Draw = new Button("Draw");
		
		//Change color and background
		Player.setTextFill(Color.RED);
		Player.setStyle("-fx-background-color: Black;");
		Banker.setTextFill(Color.RED);
		Banker.setStyle("-fx-background-color: Black;");
		Draw.setTextFill(Color.RED);
		Draw.setStyle("-fx-background-color: Black;");
		
		Text bid = new Text("Bid boi ;)");//Tells you to bid
		//Sets style and color
		bid.setStyle("-fx-font: 15px Verdana;");
		bid.setFill(Color.LIGHTGREEN);
		
		TextField bidamt = new TextField();//Text field for amount bid
		bidamt.setMaxSize(300, 200);//Sets size for field
		pane = new VBox(bid,bidamt);//VBox for the bid field
		EventHandler<ActionEvent> bidWho = new EventHandler<ActionEvent>()//Checks who you bid on
		{
			public void handle(ActionEvent event)
            {
				//After buttons pressed they disappear
            	Button b = (Button)event.getSource();
                if(b == Player)//If you bid player
                {
                	playerBid = true;
                	bankerBid = false;
                	drawBid = false;
                	pane.setVisible(true);
                	gPane.setVisible(false);
                }
                else if(b == Banker)//Banker bid
                {
                	bankerBid = true;
                	playerBid = false;
                	drawBid = false;
                	pane.setVisible(true);
                	gPane.setVisible(false);
                }
                else if(b == Draw)//If you bid draw
                {
                	drawBid = true;
                	bankerBid = false;
                	playerBid = false;
                	pane.setVisible(true);
                	gPane.setVisible(false);
                }
            }
		};
		//Sets action on buttons
		Player.setOnAction(bidWho);
		Banker.setOnAction(bidWho);
		Draw.setOnAction(bidWho);
		//Adds buttons to GridPane and padding and gap
		gPane.add(Player, 0, 0, 1, 2);
		gPane.add(Banker, 1, 0, 1, 2);
		gPane.add(Draw, 2, 0, 1, 2);
		gPane.setPadding(new Insets(250,0,0,225));
		gPane.setHgap(20);
		
		bidamt.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER))//If you press enter then saves amount bid and goes to game
		{
			//Gets user input
			String amt = bidamt.getText();
			boolean isNumeric = amt.chars().allMatch(Character::isDigit);
			if(isNumeric)
			{
				currentBet = Double.parseDouble(amt);
				bidamt.clear();
				nextScene = true;
				if(nextScene)
				{
					primaryStage.setScene(game(primaryStage));
					primaryStage.show();	
				}
			}
			else
			{
				Text error = new Text("Error: not a number dummy :p");
				error.setStyle("-fx-font: 15px Verdana;");
				error.setFill(Color.LIGHTGREEN);
				bidding.setBottom(error);
			}
		}	
		});
		bidding.setTop(pane);//Sets bidding to the top of pane
		pane.setPadding(new Insets(0,300,0,250));
		pane.setVisible(false);
		bidding.setCenter(gPane);
		bidding.setStyle("-fx-background-color: Blue;");
		return new Scene(bidding,600,600);
		
		//pause.setOnFinished(e->Bid().setScene(sceneMap.get("scene")));
	}
	public double evaluateWinnings()//Checks if you bid on the right outcome
	{
		String win = gameLogic.whoWon(playerHand, bankerHand);//Gets the winner
		//Checks if the win is the same as who you bid on and if so then you win that amount
		if(win == "Banker Wins" && bankerBid ==true)
		{
			totalWinnings += currentBet;
		}
		else if(win == "Player Wins" && playerBid == true)
		{
			totalWinnings += currentBet;
		}
		else if(win == "Draw" && drawBid == true)
		{
			totalWinnings += currentBet;
		}
		return totalWinnings;
	}
	public Scene resultScreen(Stage primaryStage)//, String s)//Displays results
	{
		String s = gameLogic.whoWon(playerHand, bankerHand);
		Button again = new Button("Play Again?");//Play again
		again.setTextFill(Color.RED);//Sets color of text
		again.setStyle("-fx-background-color: Black;");//Button is black
		BorderPane pane = new BorderPane();
		String bid = "You did not bet somehow?!!";
		String congratsOrBad;
		String betSee;
		//Checks who you bet on
		if(playerBid)
			bid =  " you bet Player!"; 
		else if(bankerBid)
			bid =  " you bet Banker!";
		else if(drawBid)
			bid =  " you bet Draw!";
		//Checks if you got the bid right
		if(s == "Player Wins" && playerBid)
			congratsOrBad = "Congrats, You Won,";
		else if(s == "Banker Wins" && bankerBid)
			congratsOrBad = "Congrats, You Won,";
		else if(s == "Draw" && drawBid)
			congratsOrBad = "Congrats, You Won,";
		else
			congratsOrBad = "Damn, You lost,";
		betSee = congratsOrBad + bid;
		//Creates text based on results
		Text res = new Text("Player Total: " + gameLogic.handTotal(playerHand) +
							" Banker Total: " + gameLogic.handTotal(bankerHand) + "\n" + 
							  s + "\n" + betSee);
		TextField field = new TextField("Player Total: " + gameLogic.handTotal(playerHand) +
				" Banker Total: " + gameLogic.handTotal(bankerHand) + "\n" + 
				  s + "\n" + betSee + "\n\n");
		res.setFill(Color.LIGHTBLUE);
		res.setStyle("-fx-font: 15px Verdana;" + "-fx-font-weight: bold;");
		VBox results = new VBox(field,again);
		pane.setCenter(results);
		pane.setPadding(new Insets(200,0,0,225));
		EventHandler<ActionEvent> go = new EventHandler<ActionEvent>()//Action to play again
		{
			public void handle(ActionEvent event)
			{
				primaryStage.setScene(Bid(primaryStage));
        		primaryStage.show();
			}
		};
		again.setOnAction(go);
		pane.setStyle("-fx-background-color: Blue;");
		return new Scene(pane,600,600);
	}
	
	public Scene game(Stage primaryStage)//Actual game
	{
		//Do not show cards
		showP = false;
		showB = false;
		
		nextScene = false;//Do not go to next scene
		//Show 3rd card before they draw it
		Button showPlayer = new Button("Expose Player\nPress before card drawn");
		Button showBanker = new Button("Expose Banker\nPress before card drawn");
		//Style button
		showPlayer.setTextFill(Color.RED);
		showPlayer.setStyle("-fx-background-color: Black;");
		showBanker.setTextFill(Color.RED);
		showBanker.setStyle("-fx-background-color: Black;");
		//Horizontal box for buttons
		HBox expose = new HBox(showPlayer,showBanker);
		//GridPane for banker and player
		GridPane gPanePlayer = new GridPane();
		GridPane gPaneBanker = new GridPane();
		//Rectangle for every card and style
		Rectangle recC1 = new Rectangle(80,30);
		Rectangle recC2 = new Rectangle(80,30);
		Rectangle recC3 = new Rectangle(80,30);
		Rectangle recB1 = new Rectangle(80,30);
		Rectangle recB2 = new Rectangle(80,30);
		Rectangle recB3 = new Rectangle(80,30);
		recC1.setStroke(Color.RED);
		recC2.setStroke(Color.RED);
		recC3.setStroke(Color.RED);
		recB1.setStroke(Color.RED);
		recB2.setStroke(Color.RED);
		recB3.setStroke(Color.RED);
		end = false;//Game does not end yet
		menu = new MenuBar();//Menu
		//Makes menu item and drop down
		Menu options = new Menu("Options");
		MenuItem exit = new MenuItem("Exit");
		MenuItem restart = new MenuItem("Fresh Start");
		menu.getMenus().add(options);//Adds to menu
		options.getItems().addAll(exit,restart);//Adds to drop down
		pane = new VBox(menu,play);
		/////////Menu Options////////
		exit.setOnAction(e -> System.exit(0));//Exit game
		EventHandler<ActionEvent> re = new EventHandler<ActionEvent>()//Restart game
		{
            public void handle(ActionEvent event)
            {

            	MenuItem i = (MenuItem)event.getSource();
                if(i == restart)
                {
                	//Resets bid and winning
                	currentBet = 0;
                	totalWinnings = 0;
                	primaryStage.setScene(Bid(primaryStage));
            		primaryStage.show();
                }
            }
		};
		restart.setOnAction(re);
		////////////////////////////
		BorderPane plays = new BorderPane();//Pane for game
		//Deals hands
		playerHand = theDealer.dealHand();
		bankerHand = theDealer.dealHand();
		//Gets total cards
		int pT = gameLogic.handTotal(playerHand);
		int bT = gameLogic.handTotal(bankerHand);
		//Makes card objects for hand
		Card playerC1 = playerHand.get(0);
		Card playerC2 = playerHand.get(1);
		Card bankerC1 = bankerHand.get(0);
		Card bankerC2 = bankerHand.get(1);
		//Displays person and card value
		Text player = new Text("You: " + pT );
		Text banker = new Text("Banker: " + bT);
		//Sets text and color
		Text playerCard1 = new Text(playerC1.suite + " : " + playerC1.value);
		Text playerCard2 = new Text(playerC2.suite + " : " + playerC2.value);
		playerCard1.setFill(Color.RED);
		playerCard2.setFill(Color.RED);
		Text bankerCard1 = new Text(bankerC1.suite + " : " + bankerC1.value);
		Text bankerCard2 = new Text(bankerC2.suite + " : " + bankerC2.value);
		bankerCard1.setFill(Color.RED);
		bankerCard2.setFill(Color.RED);
		//Bid text
		Text bet = new Text("Current Bet: " + currentBet);
		Text winnings = new Text("Current Winnings: " + totalWinnings);
		/////Creates GridPane for player and banker/////
		gPanePlayer.add(player, 0, 0, 1, 2);
		gPanePlayer.add(recC1, 0, 1, 1, 2);
		gPanePlayer.add(playerCard1, 0, 1, 1, 2);
		gPanePlayer.add(recC2, 0, 2, 2, 2);
		gPanePlayer.add(playerCard2, 0, 2, 1, 2);
		gPanePlayer.setVgap(60);
		
		gPaneBanker.add(banker, 0, 0, 1, 2);
		gPaneBanker.add(recB1, 0, 1, 1, 2);
		gPaneBanker.add(bankerCard1, 0, 1, 1, 2);
		gPaneBanker.add(recB2, 0, 2, 2, 2);
		gPaneBanker.add(bankerCard2, 0, 2, 1, 2);
		gPaneBanker.setVgap(60);
		gPanePlayer.setPadding(new Insets(200,50,50,50));
		gPaneBanker.setPadding(new Insets(200,50,50,0));
		
		VBox money = new VBox(25,bet,winnings);//VBox for money
		plays.setLeft(gPanePlayer);
		plays.setRight(gPaneBanker);
		plays.setTop(menu);
		plays.setBottom(money);
		plays.setCenter(expose);
		plays.setStyle("-fx-background-color: #32CD32;");
		EventHandler<ActionEvent> win = new EventHandler<ActionEvent>()//This event plays to see who won
		{
			public void handle(ActionEvent event)
			{
				end = true;
				totalWinnings = evaluateWinnings();
				primaryStage.setScene(resultScreen(primaryStage));
	    		primaryStage.show();
			}
		};
		EventHandler<ActionEvent> showCards = new EventHandler<ActionEvent>()//Expose 3rd card of player or banker
		{
			public void handle(ActionEvent event)
			{
				Button i = (Button)event.getSource();
                if(i == showPlayer)
                {
                	showP = true;
                }
                else if(i == showBanker)
                {
                	showB = true;
                }
			}
		};
		showPlayer.setOnAction(showCards);
		showBanker.setOnAction(showCards);
		//Check for natural win
		if(playerHand.size() == 2 && bankerHand.size() == 2)
		{
			int playerCards = gameLogic.handTotal(playerHand);
			int bankCards = gameLogic.handTotal(bankerHand);
			if(playerCards >= 8 || bankCards >= 8)
			{
				pause.setOnFinished(win);
				pause.play();
			}
		}
		
		EventHandler<ActionEvent> drawPlayer = new EventHandler<ActionEvent>()//Plays if the player can draw a card
		{
			public void handle(ActionEvent event)
			{
				//Draws new card and adds to pane
				Card playerC3 = theDealer.drawOne();
	        	playerHand.add(playerC3);
	        	Text playerCard3 = new Text(playerC3.suite + " : " + playerC3.value);
	        	if(showP)
	        	{
	        	playerCard3.setFill(Color.RED);
	        	}
	        	int newpT = gameLogic.handTotal(playerHand);
	        	Text newPlayer = new Text("You: " + newpT );
	        	gPanePlayer.getChildren().remove(player);
	        	gPanePlayer.add(newPlayer, 0, 0,1,2);
	        	gPanePlayer.add(recC3, 0, 3, 2, 2);
	    		gPanePlayer.add(playerCard3, 0, 3, 1, 2);
	    		gPanePlayer.setVgap(60);
	    		plays.setLeft(gPanePlayer);
			}
		};
		EventHandler<ActionEvent> drawBankerNoPlayerCard = new EventHandler<ActionEvent>()//Plays if banker can draw
		{
			public void handle(ActionEvent event)
			{
				//Draws new card and adds to pane
				int oldbT = gameLogic.handTotal(bankerHand);
	        	if(oldbT == 3 || oldbT == 4 || oldbT == 5)
	        	{
	        		Card bankerC3 = theDealer.drawOne();
	            	bankerHand.add(bankerC3);
	            	Text bankerCard3 = new Text(bankerC3.suite + " : " + bankerC3.value);
	            	if(showB)
	            	{
	            	bankerCard3.setFill(Color.RED);
	            	}
	            	int newbT = gameLogic.handTotal(bankerHand);
	            	Text newBanker = new Text("Dealer: " + newbT );
	            	gPaneBanker.getChildren().remove(banker);
	            	gPaneBanker.add(newBanker, 0, 0,1,2);
	            	gPaneBanker.add(recB3, 0, 3, 2, 2);
	            	gPaneBanker.add(bankerCard3, 0, 3, 1, 2);
	            	gPaneBanker.setVgap(60);
		    		plays.setRight(gPaneBanker);
	        	}
			}
		};
		EventHandler<ActionEvent> drawBanker = new EventHandler<ActionEvent>()//Plays if banker can draw
		{
			public void handle(ActionEvent event)
			{
				//Draws new card and adds to pane
				Card bankerC3 = theDealer.drawOne();
	        	bankerHand.add(bankerC3);
	        	Text bankerCard3 = new Text(bankerC3.suite + " : " + bankerC3.value);
	        	int newbT = gameLogic.handTotal(bankerHand);
	        	Text newBanker = new Text("Dealer: " + newbT );
	        	gPaneBanker.getChildren().remove(banker);
            	gPaneBanker.add(newBanker, 0, 0,1,2);
            	gPaneBanker.add(recB3, 0, 3, 2, 2);
            	gPaneBanker.add(bankerCard3, 0, 3, 1, 2);
            	gPaneBanker.setVgap(60);
	    		plays.setRight(gPaneBanker);
			}
		};
        if(gameLogic.evaluatePlayerDraw(playerHand))//Checks if the player can draw
        {
        	if(!end)
        	{
	        	drawFirst.setOnFinished(drawPlayer);
	        	drawFirst.play();
				System.out.println("Done Player!");
        	}
        }
        if(!gameLogic.evaluatePlayerDraw(playerHand))//Checks if you cant draw but banker can
        {
        	if(!end)
        	{
	        	drawFirst.setOnFinished(drawBankerNoPlayerCard);
	        	drawFirst.play();
				System.out.println("Done Only Bankers!");
        	}
        }
        if(playerHand.size() == 3 && gameLogic.evaluateBankerDraw(bankerHand,playerHand.get(playerHand.size() - 1)))//checks if you draw and if banker can draw
		{
        	if(!end)
        	{
	        	drawWait.setOnFinished(drawBanker);
	        	drawWait.play();
	        	System.out.println("Done Banker!");
        	}
		}
        endScreen.setOnFinished(win);
		endScreen.play();
		return new Scene(plays,600,600);	
	}
}