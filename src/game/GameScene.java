package game;

import game.card.Card;
import game.card.Deck;
import game.card.Hand;
import server.Client;
import server.Server;
import user.Player;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.paint.Color;
import javafx.util.Duration;
import javafx.scene.shape.Line;

public class GameScene {

	// Variables
	private Slider betSlider;
	private ImageView[] dealerCardImages;
	private ImageView[][] playerCardImages;
	private static Scene scene;
	private static Stage stage;
	private Button fold, check, bet;
	private ImageView foldImg, checkImg, callImg, betImg;


    private Player[] players;
    private int currentPlayer;
    private int smallBlind, bigBlind;
    private int startingPos;
    private int pot;
    private boolean gameEnded;
    private boolean roundEnded;
    private int roundNum;
    private int amountToCall;
    private int playerNum;

    private Deck deck;
    private Card[] dealerCards;

	// Constant
	final int SCENE_WIDTH = 1200;
	final int SCENE_HEIGHT = 650;
	final String BACKGROUND_IMG_PATH = "images/Cards/pokertable.jpg";

	private final int INITIAL_MONEY = 10000;
	private final int INITIAL_POSITION = 0;
	private final int INITIAL_SMALL_BLIND = 50;
	private final int INITIAL_BIG_BLIND = 100;
	private final int BLIND_RAISE_ROUND_NUM = 5;
	private final int BLIND_RAISE_RATIO = 2;
	

	public GameScene(Server server, int playerNum) {

		initializeScene();

		this.playerNum = playerNum;

		initializePlayersServer(server);
	}

	public GameScene(Client client, int playerNum) {

		initializeScene();

		this.playerNum = playerNum;

		initializePlayersClient(client);
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

            amountToCall = bigBlind;

            while (!roundEnded) { // Within 1 round
                if (!players[currentPlayer].hasFolded()) {

                    players[currentPlayer].play(amountToCall);
                    // decision is called by UI

                }
                players[currentPlayer].setCurrent(false);

                currentPlayer = (currentPlayer + 1) % playerNum;

                players[currentPlayer].setCurrent(true);
            }
        }

        // End of Round
        // Check who won
        assignPot();        // Assigns pot to a player
        checkEliminated();    // Check if someone was eliminated

        setPlayerBlinds(startingPos, false);

        startingPos = (startingPos + 1) % playerNum;

        setPlayerBlinds(startingPos, true);

        roundNum++;

        if (roundNum % BLIND_RAISE_ROUND_NUM == BLIND_RAISE_ROUND_NUM - 1) {
            raiseBlinds();
        }
	}
	
	private void initializePlayersServer(Server server) {
        players = new Player[playerNum];

        deck = new Deck();

        dealerCards = deck.dealCards(5);
        String dealerCardsString = "";
        for (int i = 0; i < 5; i++) {
            dealerCardsString += "," + dealerCards[i];
        }

        for (int i = 0; i < playerNum; i++) {
            players[i] = new Player("Player " + Integer.toString(i + 1), INITIAL_MONEY, i, false, false, false, false,
                    deck.dealCards(2));
        }

        // Send player info to clients (array of 14 strings)
        for (int i = 1; i < playerNum; i++) {
            server.sendMsg(players[i].toString() + dealerCardsString, i);
        }

        startingPos = INITIAL_POSITION;
        smallBlind = INITIAL_SMALL_BLIND;
        bigBlind = INITIAL_BIG_BLIND;

        setPlayerBlinds(startingPos, true);

        currentPlayer = startingPos;
        players[currentPlayer].setCurrent(true);
    }

    private void initializePlayersClient(Client client) {

        // Read player info
        String[] info = client.readMsg().split(",");

        // Player cards
        Card[] playerCards = { new Card(info[7]), new Card(info[8]) };

        // Dealer Cards
        dealerCards = new Card[5];
        for (int i = 0; i < 5; i++) {
            dealerCards[i] = new Card(info[i + 9]);
        }

        Player player = new Player(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]),
                Boolean.parseBoolean(info[3]), Boolean.parseBoolean(info[4]), Boolean.parseBoolean(info[5]),
                Boolean.parseBoolean(info[6]), playerCards);

        startingPos = INITIAL_POSITION;
        smallBlind = INITIAL_SMALL_BLIND;
        bigBlind = INITIAL_BIG_BLIND;

        setPlayerBlinds(startingPos, true);
    }

    private void raiseBlinds() {
        smallBlind *= BLIND_RAISE_RATIO;
        bigBlind *= BLIND_RAISE_RATIO;
    }

    private void setPlayerBlinds(int startingPos, boolean state) {
        players[(startingPos - 1) % playerNum].setBigBlind(state);
        players[(startingPos - 2) % playerNum].setSmallBlind(state);
    }

    // execute when fold button is clicked
    public void fold() {
        players[currentPlayer].setFolded(true);
    }

    // execute when call button is clicked
    public void call(int wager) {
        players[currentPlayer].editCurrentMoney((-1) * wager);
        increasePot(wager);
    }

    // execute when bet button is clicked
    public void bet(int wager) {
        players[currentPlayer].editCurrentMoney((-1) * wager);
        increasePot(wager);
    }

    private void checkEliminated() {
        for (Player player : players) {
            if (player.getCurrentMoney() == 0) {
                removePlayer(player.getPlayerPosition());
            }
        }
    }

    private void removePlayer(int index) {
        Player playersTemp[] = new Player[this.playerNum];
        for (Player player : players) {
            if (player.getPlayerPosition() != index) {
                playersTemp[player.getPlayerPosition()] = player;
            }
        }
    }

    private void assignPot() {
        ArrayList<Double> handVals = new ArrayList<Double>();
        for (Player player : players) {
            if (!player.hasFolded()) {
                handVals.add(new Hand(getDealerCards(), player.getCards()).getHandValue());
            }
        }
        double winningHand = Collections.max(handVals);
        int winnerIndex = handVals.indexOf(winningHand);
        players[winnerIndex].editCurrentMoney(pot);
    }

    private void increasePot(int wager) {
        pot += wager;
    }

	// -----------------------------------------
	// SCENE METHODS
	// -----------------------------------------

	private void initializeScene(){
		// Set main pane and two sub panes: one for the game display one for the control panel
		BorderPane root = new BorderPane();

		createActionControl(root);

		// Main game pane
		Pane cardPane = new Pane();

		// Set the center deck which is always upside down
		ImageView imageView = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
		imageView.setLayoutX(400);
		imageView.setLayoutY(50);
		cardPane.getChildren().add(imageView);

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
		root.setRight(vb);
		vb.setPrefWidth(200);

		// Text boxes for the control panel
		Text name = new Text("--Placeholder--");
		name.setFill(Color.WHITE);
		name.setStyle("-fx-font-size:14");

		Text money = new Text("$~~~PLACEHOLDER~~~");
		money.setFill(Color.WHITE);
		money.setStyle("-fx-font-size:14");

		// Slider for betting in the control panel
		betSlider = new Slider(0, INITIAL_MONEY, 0);	// Current is minimum
		Label label = new Label();
		label.textProperty().bind(Bindings.format("$ %.2f", betSlider.valueProperty()));
		label.setStyle("-fx-text-fill:white");
		betSlider.setMajorTickUnit(INITIAL_MONEY / 4);
		betSlider.setShowTickMarks(true);
		betSlider.setShowTickLabels(true);

		// Action button images
		foldImg = new ImageView(new File("images/foldButton.png").toURI().toString());
		checkImg = new ImageView(new File("images/checkButton.png").toURI().toString());
		checkImg = new ImageView(new File("images/callButton.png").toURI().toString());
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
			call(wager);
		});
		bet = new Button();
		bet.setGraphic(betImg);
		bet.setOnMouseClicked(e -> {
			bet(wager);
		});
		// Add item to vbox
		vb.getChildren().addAll(name, money, fold, check, bet, betSlider, label);
	}

	private void setPlayerCards(Pane cardPane, double delayDuration){
		playerCardImages = new ImageView[4][2];
		int cardGap = 0;

		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 2; j++) {
				playerCardImages[i][j] = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
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

			dealerCardImages[i] = new ImageView(new File("images/Cards/backCard.png").toURI().toString());
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
