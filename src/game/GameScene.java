package game;

import game.card.Card;
import game.card.Deck;
import game.card.Hand;
import server.Client;
import server.Server;
import user.Player;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.scene.paint.Color;
import javafx.util.Duration;
import menu.MainMenu;
import menu.MenuItem;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

public class GameScene {

	// Variables
	private Slider betSlider;
	private ImageView[] dealerCardImages;
	private ImageView[][] playerCardImages;
	private ImageView smallBlindImages;
	private ImageView bigBlindImages;
	private static Scene scene;
	private static Stage stage;
	private Button fold, check, bet;
	private ImageView foldImg, checkImg, betImg;
	private boolean actionMade;
	private Rectangle[] playerFrames;
	private Rectangle dealerFrame;
	private MenuItem[] playerMI;
	private MenuItem[] playerAmountCalledMI;
	private MenuItem[] playerMoneyMI;
	private MenuItem potMI;
	private Pane cardPane;
	private Text dispWinner;

	private Player[] players;
	private Player ownPlayer;
	private int currentPlayer;
	private int smallBlind, bigBlind;
	private int startingPos;
	private int pot;
	private int changeBlinds = 0;
	private boolean gameEnded;
	private boolean roundEnded;
	private int roundNum;
	private int amountToCall;
	private int playerNum;
	private Server server;
	private Client client;
	private boolean roundLoop;
	private int stat_fold;
	private int stat_check;
	private int stat_call;
	private int stat_bet;
	private int[] stat_hands;

	private Deck deck;
	private Card[] dealerCards;

	// Constant
	private final int SCENE_WIDTH = 1200;
	private final int SCENE_HEIGHT = 650;
	private final String BACKGROUND_IMG_PATH = "images/Cards/pokertable.jpg";

	private final int INITIAL_MONEY = 10000;
	private final int INITIAL_POSITION = 0;
	private final int INITIAL_SMALL_BLIND = 50;
	private final int INITIAL_BIG_BLIND = 100;
	private final int BLIND_RAISE_ROUND_NUM = 5;
	private final int BLIND_RAISE_RATIO = 2;

	public GameScene(Server server, int clientNum) {

		System.out.println("Server In Game Scene");

		statsInit();

		this.playerNum = clientNum + 1; // Including the server

		initializeScene();

		this.server = server;

		initializePlayersServer(this.server);

		new Thread(() -> runGame()).start();
	}

	public GameScene(Client client, int clientNum) {

		System.out.println("Client In Game Scene");

		statsInit();

		this.playerNum = clientNum + 1; // Including the server

		initializeScene();

		this.client = client;

		initializePlayersClient(this.client);

		new Thread(() -> runGame()).start();
	}

	// -----------------------------------------
	// GAME METHODS
	// -----------------------------------------

	public Card[] getDealerCards() {
		return dealerCards;
	}

	public Player[] getPlayers() {
		return players;
	}

	public int getRoundNum() {
		return roundNum;
	}

	public boolean getRoundEnded() {
		return roundEnded;
	}

	public int getPlayerNum() {
		return playerNum;
	}

	public void runGame() {

		roundNum = 0;

		gameEnded = false;
		roundEnded = false;

		while (!gameEnded) { // For every round

			int numPlayersPlayed = 0;
			int subRound = 0;
			while (subRound < 4) {

				if (!players[currentPlayer].hasFolded()) {
					numPlayersPlayed++;

					amountToCall = getMaxAmToCall() - players[currentPlayer].getAmountCalled();

					if (amountToCall != 0) {
						checkImg.setImage(new Image(new File("images/callButton.png").toURI().toString()));
						adjustSlider(getMaxAmToCall() * 2, players[currentPlayer].getCurrentMoney());
					} else {
						checkImg.setImage(new Image(new File("images/checkButton.png").toURI().toString()));
						adjustSlider(bigBlind, players[currentPlayer].getCurrentMoney());
					}

					setActive(); // decision is called by UI

					// Check if playerNum - 1 people folded
					if (checkFolded()) {
						pushCallsToPot();
						break;
					}
				}

				players[currentPlayer].setCurrent(false);

				currentPlayer++;

				if (currentPlayer == playerNum) {
					currentPlayer = 0;

					roundLoop = true;
				}

				if (roundLoop) {
					if (checkAllCalled() && numPlayersPlayed >= playerNum) { // Next subround

						pushCallsToPot();

						subRound++;

						numPlayersPlayed = 0;

						if (subRound == 1) {
							showFlop();
						} else if (subRound == 2) {
							showRiver();
						} else if (subRound == 3) {
							showTurn();
						}

						for (int i = 0; i < playerNum; i++) {
							players[i].setAmountCalled(0);
						}

						roundLoop = false;
					}
				}

				players[currentPlayer].setCurrent(true);
			}

			// End of Round
			// Check who won
			assignPot(); // Assigns pot to a player
			//Sleep for 5 seconds after each end round
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			checkEliminated(); // Check if someone was eliminated

			// Game Ends
			if (playerNum == 1) {
				Platform.runLater(() -> {
					System.out.println("YOU WON");
					
					try {
						sendStat();
					} catch (FileNotFoundException e) {
						System.err.println("File not found exception");
					}

					gameEnded = true;
					stage.setScene(MainMenu.getScene());
				});
			}

			setPlayerBlinds(startingPos, false);

			startingPos = (startingPos + 1) % playerNum;

			if (ownPlayer.getPlayerPosition() == 0) { // Server
				resetScene();
				resetServer();
			} else { // Client
				resetScene();
				resetClient();
			}

			roundNum++;

			if (roundNum % BLIND_RAISE_ROUND_NUM == BLIND_RAISE_ROUND_NUM - 1) {
				raiseBlinds();
			}
		}
	}

	private void resetServer() {
		deck = new Deck();

		dealerCards = deck.dealCards(5);
		String dealerCardsString = "";
		for (int i = 0; i < 5; i++) {
			if (i > 0) {
				dealerCardsString += ",";
			}
			dealerCardsString += dealerCards[i];
		}

		for (int i = 0; i < playerNum; i++) {
			players[i] = new Player("Player " + Integer.toString(i + 1), players[i].getCurrentMoney(), i, false, false,
					false, false, deck.dealCards(2));
		}

		// players[0]: server player
		ownPlayer = players[0];

		currentPlayer = startingPos;
		players[currentPlayer].setCurrent(true);

		setPlayerBlinds(startingPos, true);

		// Send cards to clients
		String playersText = "";
		for (int i = 0; i < playerNum; i++) {
			playersText += players[i].getCards()[0] + "," + players[i].getCards()[1] + ";";
		}
		playersText += dealerCardsString;

		for (int i = 0; i < playerNum - 1; i++) {
			server.sendMsg(playersText, i);
		}

		adjustScene();
	}

	private void resetClient() {
		// Read players info
		String[] cardsInfo = client.readMsg().split(";");

		for (int i = 0; i < playerNum; i++) {
			Card[] playerCards = new Card[2];
			playerCards[0] = new Card(cardsInfo[i].split(",")[0]);
			playerCards[1] = new Card(cardsInfo[i].split(",")[1]);

			players[i] = new Player("Player " + Integer.toString(i + 1), players[i].getCurrentMoney(), i, false, false,
					false, false, playerCards);
		}

		// Dealer Cards
		dealerCards = new Card[5];
		for (int i = 0; i < 5; i++) {
			dealerCards[i] = new Card(cardsInfo[playerNum].split(",")[i]);
		}

		ownPlayer = players[client.getPosition() + 1]; // Including server

		currentPlayer = startingPos;
		players[currentPlayer].setCurrent(true);

		setPlayerBlinds(startingPos, true);

		adjustScene();
	}

	private void initializePlayersServer(Server server) {

		players = new Player[playerNum];

		this.startingPos = INITIAL_POSITION;
		this.smallBlind = INITIAL_SMALL_BLIND;
		this.bigBlind = INITIAL_BIG_BLIND;

		deck = new Deck();
		
		dealerCards = deck.dealCards(5);
		String dealerCardsString = "";
		for (int i = 0; i < 5; i++) {
			if (i > 0) {
				dealerCardsString += ",";
			}
			dealerCardsString += dealerCards[i];
		}

		for (int i = 0; i < playerNum; i++) {
			players[i] = new Player("Player " + Integer.toString(i + 1), INITIAL_MONEY, i, false, false, false, false,
					deck.dealCards(2));
		}

		// players[0]: server player
		ownPlayer = players[0];

		playerCardImages[ownPlayer.getPlayerPosition()][0].setImage(ownPlayer.getCards()[0].getCardImage().getImage());
		playerCardImages[ownPlayer.getPlayerPosition()][1].setImage(ownPlayer.getCards()[1].getCardImage().getImage());

		setPlayerBlinds(startingPos, true);

		currentPlayer = startingPos;
		players[currentPlayer].setCurrent(true);

		// Send cards to clients
		String playersText = "";
		for (int i = 0; i < playerNum; i++) {
			playersText += players[i].getCards()[0] + "," + players[i].getCards()[1] + ";";
		}
		playersText += dealerCardsString;

		for (int i = 0; i < playerNum - 1; i++) {
			server.sendMsg(playersText, i);
		}

		adjustScene();
	}

	private void initializePlayersClient(Client client) {

		players = new Player[playerNum];

		this.startingPos = INITIAL_POSITION;
		this.smallBlind = INITIAL_SMALL_BLIND;
		this.bigBlind = INITIAL_BIG_BLIND;

		// Read players info
		String msg = client.readMsg();
		String[] cardsInfo = msg.split(";");

		for (int i = 0; i < playerNum; i++) {
			Card[] playerCards = new Card[2];
			playerCards[0] = new Card(cardsInfo[i].split(",")[0]);
			playerCards[1] = new Card(cardsInfo[i].split(",")[1]);

			players[i] = new Player("Player " + Integer.toString(i + 1), INITIAL_MONEY, i, false, false, false, false,
					playerCards);
		}

		// Dealer Cards
		dealerCards = new Card[5];
		for (int i = 0; i < 5; i++) {
			dealerCards[i] = new Card(cardsInfo[cardsInfo.length - 1].split(",")[i]);
		}

		ownPlayer = players[client.getPosition() + 1]; // Including server

		playerCardImages[ownPlayer.getPlayerPosition()][0].setImage(ownPlayer.getCards()[0].getCardImage().getImage());
		playerCardImages[ownPlayer.getPlayerPosition()][1].setImage(ownPlayer.getCards()[1].getCardImage().getImage());

		setPlayerBlinds(startingPos, true);

		currentPlayer = startingPos;
		players[currentPlayer].setCurrent(true);

		adjustScene();
	}

	private boolean checkFolded() {
		int num = 0;
		for (int i = 0; i < playerNum; i++) {
			if (players[i].hasFolded()) {
				num++;
			}
		}

		return num == playerNum - 1;
	}

	private void statsInit() {
		stat_fold = 0;
		stat_check = 0;
		stat_call = 0;
		stat_bet = 0;
		stat_hands = new int[10];
		for (int i = 0; i < 10; i++) {
			stat_hands[i] = 0;
		}
	}

	private void pushCallsToPot() {
		for (int i = 0; i < playerNum; i++) {
			pot += players[i].getAmountCalled();
			playerAmountCalledMI[i].setText("");
			playerAmountCalledMI[i].setMinWidth(100);
		}
		potMI.setText("$ " + pot);
		potMI.setMinWidth(100);
	}

	private void showFlop() {
		for (int i = 0; i < 3; i++) {
			dealerCardImages[i].setImage(dealerCards[i].getCardImage().getImage());
		}
	}

	private void showRiver() {
		dealerCardImages[3].setImage(dealerCards[3].getCardImage().getImage());
	}

	private void showTurn() {
		dealerCardImages[4].setImage(dealerCards[4].getCardImage().getImage());
	}

	private boolean checkAllCalled() {
		for (int i = 0; i < playerNum; i++) {
			if (players[i].getAmountCalled() != getMaxAmToCall() && players[i].getCurrentMoney() != 0) {
				return false;
			}
		}
		return true;
	}

	private void raiseBlinds() {
		smallBlind *= BLIND_RAISE_RATIO;
		bigBlind *= BLIND_RAISE_RATIO;
	}

	private void setPlayerBlinds(int startingPos, boolean state) {
		players[(((startingPos - 1) % playerNum) + playerNum) % playerNum].setBigBlind(state, bigBlind);
		players[(((startingPos - 2) % playerNum) + playerNum) % playerNum].setSmallBlind(state, smallBlind);
		int translateX = 235;
		int translateY = 450;
		int translateX2 = 30;
		int translateY2 = 450;
		if(changeBlinds == 0)
		{
			ImageView bigBlindImages = new ImageView("file:///C:/Users/notbe/eclipse-workspace/Working2020Project/images/bigblind.png");
			bigBlindImages.setTranslateX(translateX );
			bigBlindImages.setTranslateY(translateY);
			bigBlindImages.setFitHeight(50);
			bigBlindImages.setFitWidth(50);
			
			ImageView smallBlindImages = new ImageView("file:///C:/Users/notbe/eclipse-workspace/Working2020Project/images/smallblind.png");
			smallBlindImages.setTranslateX(translateX2);
			smallBlindImages.setTranslateY(translateY2);
			smallBlindImages.setFitHeight(50);
			smallBlindImages.setFitWidth(50);
			cardPane.getChildren().addAll(bigBlindImages, smallBlindImages);
			changeBlinds = 1;
		}
		else
		{
			ImageView bigBlindImages = new ImageView("file:///C:/Users/notbe/eclipse-workspace/Working2020Project/images/bigblind.png");
			bigBlindImages.setTranslateX(translateX2);
			bigBlindImages.setTranslateY(translateY2);
			bigBlindImages.setFitHeight(50);
			bigBlindImages.setFitWidth(50);
			
			ImageView smallBlindImages = new ImageView("file:///C:/Users/notbe/eclipse-workspace/Working2020Project/images/smallblind.png");
			smallBlindImages.setTranslateX(translateX);
			smallBlindImages.setTranslateY(translateY);
			smallBlindImages.setFitHeight(50);
			smallBlindImages.setFitWidth(50);
			cardPane.getChildren().addAll(bigBlindImages, smallBlindImages);
			changeBlinds = 0;
		}
		
		
	}

	// execute when fold button is clicked
	private void fold() {
		players[currentPlayer].setFolded(true);
		playerCardImages[currentPlayer][0].setOpacity(0);
		playerCardImages[currentPlayer][1].setOpacity(0);
		sendAction(0, 0);
		actionMade = true;

		// Stats
		if (ownPlayer == players[currentPlayer]) {
			addStat("fold");
		}
	}

	// execute when call button is clicked
	private void call(int wager) {
		if (players[currentPlayer].getCurrentMoney() < wager) {
			wager = players[currentPlayer].getCurrentMoney();
		}

		players[currentPlayer].addMoney((-1) * wager);
		players[currentPlayer].editAmountCalled(wager);

		playerMoneyMI[currentPlayer].setText("$ " + String.valueOf(players[currentPlayer].getCurrentMoney()));
		playerAmountCalledMI[currentPlayer].setText("$ " + String.valueOf(players[currentPlayer].getAmountCalled()));

		playerMoneyMI[currentPlayer].setMinWidth(100);
		playerAmountCalledMI[currentPlayer].setMinWidth(100);

		sendAction(1, wager);
		actionMade = true;

		// Stats
		if (ownPlayer == players[currentPlayer]) {
			addStat("call" + String.valueOf(wager));
		}
	}

	// execute when bet button is clicked
	private void bet(int wager) {
		players[currentPlayer].addMoney((-1) * wager);
		players[currentPlayer].editAmountCalled(wager);

		playerMoneyMI[currentPlayer].setText("$ " + String.valueOf(players[currentPlayer].getCurrentMoney()));
		playerAmountCalledMI[currentPlayer].setText("$ " + String.valueOf(players[currentPlayer].getAmountCalled()));

		playerMoneyMI[currentPlayer].setMinWidth(100);
		playerAmountCalledMI[currentPlayer].setMinWidth(100);

		sendAction(2, wager);
		actionMade = true;

		// Stats
		if (ownPlayer == players[currentPlayer]) {
			addStat("bet");
		}
	}

	private int getMaxAmToCall() {
		int max = 0;
		for (int i = 0; i < playerNum; i++) {
			if (players[i].getAmountCalled() > max) {
				max = players[i].getAmountCalled();
			}
		}
		return max;
	}

	private void sendAction(int actionID, int wager) {
		String msg = String.valueOf(actionID) + "," + String.valueOf(pot) + ","
				+ String.valueOf(ownPlayer.getCurrentMoney() + "," + String.valueOf(ownPlayer.getAmountCalled()));

		if (ownPlayer.getPlayerPosition() == 0) { // Server
			for (int i = 0; i < playerNum - 1; i++) {
				server.sendMsg(msg, i);
			}
		} else {
			client.sendMsg(msg);
		}
	}

	private void sendStat() throws FileNotFoundException {

		// Create a File instance
		File file = new File("stats/PlayerStats.dat");

		int[] tmpStats = new int[14]; // 10 hands + fold, check, call and bet stats
		for (int i=0; i<14; i++){
			tmpStats[i] = 0;
		}

		if (file.exists()){

			// Create a Scanner for the file
			Scanner input = new Scanner(file);
		
			// Read data from a file
			String stat = "";
			if (input.hasNext()){
				stat = input.nextLine();
			}

			for (int i=0; i<14; i++){
				tmpStats[i] = Integer.valueOf(stat.split(",")[i]);
			}
	 
			// Close the file
			input.close();
		}

		// Update
		tmpStats[0] += stat_fold;
		tmpStats[1] += stat_check;
		tmpStats[2] += stat_call;
		tmpStats[3] += stat_bet;

		for(int i=0; i<10; i++){
			tmpStats[i+4] += stat_hands[i];
		}
		

		PrintWriter output = new PrintWriter(file);

		for (int i=0; i<14; i++){
			if (i != 0){
				output.print(",");
			}
			output.print(tmpStats[i]);
		}

		// Close the file
		output.close();
	}

	private void addStat(String stat){
		if (stat.equals("fold")){
			stat_fold++;
		}
		else if (stat.contains("call")){
			if (stat.charAt(4) == '0'){
				stat_check++;
			}
			else{
				stat_call++;
			}
		}
		else if (stat.equals("bet")){
			stat_bet++;
		}
		else{
			stat_hands[(int) (Double.valueOf(stat) / 100)]++;
		}
	}

	private void checkEliminated() {
		for (Player player : players) {
			if (player.getCurrentMoney() == 0) {
				if (player.getPlayerPosition() == 0){	// Server
					Platform.runLater(() -> {
						System.out.println("SERVER HAS LOST");
						gameEnded = true;
						stage.setScene(MainMenu.getScene());
					});
				}

				if (player == ownPlayer){
					Platform.runLater(() -> {
						System.out.println("YOU LOST");

						try {
							sendStat();
						} catch (FileNotFoundException e) {
							System.err.println("File not found exception");
						}

						gameEnded = true;
						stage.setScene(MainMenu.getScene());
					});
				}
				else{
					removePlayer(player.getPlayerPosition());
				}
			}
		}
	}

	private void removePlayer(int index) {
		Player playersTemp[] = new Player[--playerNum];

		for (int i=0; i<playerNum; i++) {
			if (players[i].getPlayerPosition() != index) {
				playersTemp[i] = players[i];
			}
		}

		players = playersTemp;
	}

	private void assignPot() {
		double max = 0;
		int maxI = 0;

		for (int i=0; i<playerNum; i++) {
			if (!players[i].hasFolded()) {

				Hand hand = new Hand(dealerCards, players[i].getCards());
				
				// Stats
				if (ownPlayer == players[i]){
					addStat(String.valueOf(hand.getHandValue()));
				}

				if (hand.getHandValue() > max){
					max = hand.getHandValue();
					maxI = i;
				}
			}
		}

		players[maxI].addMoney(pot);

		pot = 0;
		potMI.setText("$");
		playerMoneyMI[maxI].setText("$ " + players[maxI].getCurrentMoney());

		potMI.setMinWidth(100);
		playerMoneyMI[maxI].setMinWidth(100);
		
		//Announce Winner
		dispWinner.setText("The winner is Player " + (maxI+1));
		//Reveal All Cards
		for (Player player: players) {
			if (player.getPlayerPosition() != ownPlayer.getPlayerPosition()) {
				playerCardImages[player.getPlayerPosition()][0].setImage(player.getCards()[0].getCardImage().getImage());
				playerCardImages[player.getPlayerPosition()][1].setImage(player.getCards()[1].getCardImage().getImage());
			}
		}
	}

	// -----------------------------------------
	// SCENE METHODS
	// -----------------------------------------


	private void resetScene(){
		// Initialize delay duration
		double delayDuration = 0;
		dispWinner.setText("");

		Platform.runLater(() -> {
			for (int i=0; i<5; i++){
				dealerCardImages[i].setImage(null);
			}
			for (int i=0; i<playerNum; i++){
				playerCardImages[i][0].setImage(null);
				playerCardImages[i][1].setImage(null);
			}

			setDealerCards(cardPane, delayDuration);
			setPlayerCards(cardPane, delayDuration);

			playerCardImages[ownPlayer.getPlayerPosition()][0].setImage(ownPlayer.getCards()[0].getCardImage().getImage());
			playerCardImages[ownPlayer.getPlayerPosition()][1].setImage(ownPlayer.getCards()[1].getCardImage().getImage());
		});
	}

	private void adjustScene(){
		for (int i=0; i<playerNum; i++){
			if (players[i].getAmountCalled() == 0){
				playerAmountCalledMI[i].setText("");
				playerAmountCalledMI[i].setMinWidth(100);
			}
			else{
				playerAmountCalledMI[i].setText("$ " + String.valueOf(players[i].getAmountCalled()));
				playerAmountCalledMI[i].setMinWidth(100);
			}
			playerMoneyMI[i].setText("$ " + String.valueOf(players[i].getCurrentMoney()));
			playerMoneyMI[i].setMinWidth(100);
		}
	}

	private void setActive() {

		playerFrames[currentPlayer].setStroke(Color.RED);
		playerFrames[currentPlayer].setStrokeWidth(5);
		FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), playerFrames[currentPlayer]);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.5);
		fadeTransition.setAutoReverse(true);
		fadeTransition.setCycleCount(Animation.INDEFINITE);
		fadeTransition.play();

		if (currentPlayer == ownPlayer.getPlayerPosition()) {
			activateActions();
			actionMade = false;

			while (!actionMade) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			disableActions();
		}
		else{

			String msg;

			if (ownPlayer.getPlayerPosition() == 0){	// Server

				msg = server.readMsg(currentPlayer - 1);

				for (int i = 0; i<playerNum-1; i++){
					if (i != currentPlayer - 1){
						server.sendMsg(msg, i);
					}
				}	
			}
			else{		// Client
				msg = client.readMsg();
			}

			String[] msgArr = msg.split(",");

			if (Integer.valueOf(msgArr[0]) == 0){	// Fold
				playerCardImages[currentPlayer][0].setOpacity(0);
				playerCardImages[currentPlayer][1].setOpacity(0);
				players[currentPlayer].setFolded(true);
			}
			else{	// Call or Bet
				playerMoneyMI[currentPlayer].setText("$ " + msgArr[2]);
				players[currentPlayer].setMoney(Integer.valueOf(msgArr[2]));
				playerMoneyMI[currentPlayer].setMinWidth(100);

				playerAmountCalledMI[currentPlayer].setText("$ " + msgArr[3]);
				players[currentPlayer].setAmountCalled(Integer.valueOf(msgArr[3]));
				playerAmountCalledMI[currentPlayer].setMinWidth(100);

				pot = Integer.valueOf(msgArr[1]);
			}
		}

		playerFrames[currentPlayer].setStroke(Color.WHITE);
		playerFrames[currentPlayer].setStrokeWidth(1);
        fadeTransition.stop();
	}

	private void activateActions(){
		fold.setDisable(false);
		check.setDisable(false);
		bet.setDisable(false);
	}

	private void disableActions(){
		fold.setDisable(true);
		check.setDisable(true);
		bet.setDisable(true);
	}

	private void initializeScene(){
		// Set main pane and two sub panes: one for the game display one for the control panel
		BorderPane root = new BorderPane();

		createActionControl(root);

		disableActions();
		
		//Display winner when round ends
		dispWinner = new Text("");
		dispWinner.setFill(Color.WHITE);
		dispWinner.setFont(Font.font(20));
		dispWinner.setLayoutX(325);
		dispWinner.setLayoutY(425);
		

		// Main game pane
		cardPane = new Pane();
		
		// Player frames
		playerFrames = new Rectangle[playerNum];
		playerMI = new MenuItem[playerNum];
		playerAmountCalledMI = new MenuItem[playerNum];
		playerMoneyMI = new MenuItem[playerNum];

		for (int i=0; i<playerNum; i++){
			playerFrames[i] = new Rectangle(45 + (210 * i), 500, 150, 100);
			playerFrames[i].setStroke(Color.WHITE);
			playerFrames[i].setStrokeWidth(1);

			VBox playerBox = new VBox();

			playerMI[i] = new MenuItem("Player " + (i + 1), 20);

			playerAmountCalledMI[i] = new MenuItem("", 20);		// Only show something when has an amount called
			playerAmountCalledMI[i].setStyle("-fx-padding: 0 0 10 0");
			
			playerMoneyMI[i] = new MenuItem("$", 20);
			playerMoneyMI[i].setStyle("-fx-padding: 110 0 0 0");

			playerBox.getChildren().addAll(playerAmountCalledMI[i], playerMI[i], playerMoneyMI[i]);
			playerBox.setLayoutX(50 + (210 * i));
			playerBox.setLayoutY(440);
			cardPane.getChildren().add(playerBox);
		}

		// Dealer frame
		VBox dealerBox = new VBox();

		dealerFrame = new Rectangle(260, 250, 360, 100);
		dealerFrame.setStroke(Color.WHITE);

		potMI = new MenuItem("$", 20);

		dealerBox.getChildren().addAll(dealerFrame, potMI);
		dealerBox.setLayoutX(260);
		dealerBox.setLayoutY(250);
		dealerBox.setSpacing(10);

		cardPane.getChildren().add(dealerBox);
		cardPane.getChildren().addAll(playerFrames);
		cardPane.getChildren().add(dispWinner);


		// Set the center deck which is always upside down
		ImageView backCardImg = getBackCard();
		backCardImg.setLayoutX(400);
		backCardImg.setLayoutY(50);
		cardPane.getChildren().add(backCardImg);

		// Initialize delay duration
		double delayDuration = 0;

		setDealerCards(cardPane, delayDuration);
		setPlayerCards(cardPane, delayDuration);


		root.setCenter(cardPane);


		// Background
		File imgF = new File(BACKGROUND_IMG_PATH);
		root.setStyle("-fx-background-image: url(" + imgF.toURI().toString() + "); -fx-background-size: cover;");
		
		// Creating scene with pane
        scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
	}

	private void createActionControl(BorderPane root){
		VBox vb = new VBox();
		vb.setSpacing(20);
		vb.setAlignment(Pos.CENTER);
		root.setRight(vb);
		vb.setPrefWidth(200);
		vb.setStyle("-fx-padding: 0 10 0 0");

		// Slider for betting in the control panel
		betSlider = new Slider(0, INITIAL_MONEY, 0);	// Current is minimum
		MenuItem sliderTxt = new MenuItem("$ ", 20);
		sliderTxt.setStyle("-fx-text-fill:white");
		betSlider.setMajorTickUnit(INITIAL_MONEY / 4);
		betSlider.setShowTickMarks(true);
		betSlider.setShowTickLabels(true);
		
		betSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				sliderTxt.setText(String.format("$ %.0f", newValue));
				sliderTxt.setMinWidth(100);
			}
        });

		// Action button images
		foldImg = new ImageView(new File("images/foldButton.png").toURI().toString());
		checkImg = new ImageView(new File("images/checkButton.png").toURI().toString());
		betImg = new ImageView(new File("images/betButton.png").toURI().toString());

		// Player actions
		fold = new Button();
		fold.setGraphic(foldImg);
		fold.setOnMouseClicked(e -> {
			fold();
		});
		check = new Button();
		check.setGraphic(checkImg);
		check.setOnMouseClicked(e -> {
			call(amountToCall);
		});
		bet = new Button();
		bet.setGraphic(betImg);
		bet.setOnMouseClicked(e -> {
			if (!sliderTxt.getText().equals("$ NaN")){
				bet(Integer.parseInt(sliderTxt.getText().substring(2)));
			}
			else{
				sliderTxt.setText(String.valueOf(amountToCall));
				call(amountToCall);
			}
		});
		// Add item to vbox
		vb.getChildren().addAll(fold, check, bet, betSlider, sliderTxt);

	}

	private ImageView getBackCard(){
		return new ImageView(new File("images/Cards/backCard.png").toURI().toString());
	}

	private void setPlayerCards(Pane cardPane, double delayDuration){
		playerCardImages = new ImageView[4][2];
		int cardGap = 0;

		for (int i = 0; i < playerNum; i++) {
			for (int j = 0; j < 2; j++) {
				playerCardImages[i][j] = getBackCard();
				playerCardImages[i][j].setLayoutX(400);
				playerCardImages[i][j].setLayoutY(50);
				cardPane.getChildren().add(playerCardImages[i][j]);

				Line line = new Line(40, 50, -315 + cardGap, 500);

				PathTransition transition = new PathTransition();
				transition.setNode(playerCardImages[i][j]);
				transition.setDuration(Duration.seconds(0.5));
				transition.setPath(line);
				transition.setDelay(Duration.seconds(delayDuration));
				transition.play();

				cardGap += 70;
				delayDuration += 0.5;
			}
			cardGap += 70;
		}
	}

	private void setDealerCards(Pane cardPane, double delayDuration){
		dealerCardImages = new ImageView[5];
		int cardGap = 0;

		for (int i = 0; i < 5; i++) {

			dealerCardImages[i] = getBackCard();
			dealerCardImages[i].setLayoutX(400);
			dealerCardImages[i].setLayoutY(50);
			cardPane.getChildren().add(dealerCardImages[i]);

			Line line = new Line(40, 50, -100 + cardGap, 250);

			// Transition of the dealer cards
			PathTransition transition = new PathTransition();
			transition.setNode(dealerCardImages[i]);
			transition.setDuration(Duration.seconds(0.5));
			transition.setPath(line);
			transition.setDelay(Duration.seconds(delayDuration));
			transition.play();

			// Rotation of the dealer cards
			RotateTransition rt = new RotateTransition();
			rt.setNode(dealerCardImages[i]);
			rt.setDuration(Duration.seconds(0.5));
			rt.setByAngle(180);
			rt.setDelay(Duration.seconds(delayDuration));
			rt.play();

			cardGap += 70;
			delayDuration += 0.5;
		}
	}

	/**
	 * Adjusts the betting slider
	 * 
	 * @param min the minimum amount is the double of the amount to be called
	 * @param max the maximum amount is the amount of money the player has
	 */
	private void adjustSlider(int min, int max){
		if (min < max){
			betSlider.setMin(min);
			betSlider.setMax(max);
		}
		else{
			betSlider.setMin(max);
			betSlider.setMax(max);
		}
		betSlider.setValue(min);
	}

	/**
     * Sets the stage to a new stage
     * 
     * @param primaryStage new stage
     */
    public void setStage(Stage primaryStage) {
        stage = primaryStage;
    }

	/**
     * Returns the scene that is currently used
     * 
     * @return the scene that is currently used
     */
    public static Scene getScene() {
        return scene;
    }
}