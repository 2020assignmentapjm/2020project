package game;

import user.Player;

import java.util.ArrayList;
import java.util.Collections;

import game.card.Card;
import game.card.Deck;
import game.card.Hand;
import server.Client;
import server.Server;

public class GameLoop {

    // Variables
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
    private final int INITIAL_MONEY = 10000;
    private final int INITIAL_POSITION = 0;
    private final int INITIAL_SMALL_BLIND = 50;
    private final int INITIAL_BIG_BLIND = 100;
    private final int BLIND_RAISE_ROUND_NUM = 5;
    private final int BLIND_RAISE_RATIO = 2;

    public GameLoop(int playerNum, Server server) {

        this.playerNum = playerNum;

        initializePlayersServer(server);
    }

    public GameLoop(int playerNum, Client client) {

        this.playerNum = playerNum;

        initializePlayersClient(client);
    }

    public Card[] getDealerCards(){
        return dealerCards;
    }

    public Player[] getPlayers(){
        return players;
    }

    public int getRoundNum(){
        return roundNum;
    }

    public boolean getRoundEnded(){
        return roundEnded;
    }

    public int getPlayerNum(){
        return playerNum;
    }

    public void runGame(){
        roundNum = 0;

        gameEnded = false;
        roundEnded = false;

        while(!gameEnded){      // For every round

            amountToCall = bigBlind;

            while(!roundEnded){     // Within 1 round
                if (!players[currentPlayer].hasFolded()){

                    players[currentPlayer].play(amountToCall);
                    // decision is called by UI



                }
                players[currentPlayer].setCurrent(false);

                currentPlayer = (currentPlayer + 1) % playerNum;

                players[currentPlayer].setCurrent(true);
            }

            // End of Round
            // Check who won
            assignPot();
            checkElimated();

            setPlayerBlinds(startingPos, false);

            startingPos = (startingPos + 1) % playerNum;

            setPlayerBlinds(startingPos, true);

            roundNum++;

            if (roundNum % BLIND_RAISE_ROUND_NUM == BLIND_RAISE_ROUND_NUM-1){
                raiseBlinds();
            }
        }
    }

    private void initializePlayersServer(Server server){
        players = new Player[playerNum];

        deck = new Deck();

        dealerCards = deck.dealCards(5);
        String dealerCardsString = "";
        for (int i=0; i<5; i++){
            dealerCardsString += "," + dealerCards[i];
        }


        for (int i=0; i<playerNum; i++){
            players[i] = new Player("Player " + Integer.toString(i+1), INITIAL_MONEY,
                    i, false, false, false, false, deck.dealCards(2));
        }


        // Send player info to clients (array of 14 strings)
        for (int i=1; i<playerNum; i++){
            server.sendMsg(players[i].toString() + dealerCardsString, i);
        }

        startingPos = INITIAL_POSITION;
        smallBlind = INITIAL_SMALL_BLIND;
        bigBlind = INITIAL_BIG_BLIND;

        setPlayerBlinds(startingPos, true);

        currentPlayer = startingPos;
        players[currentPlayer].setCurrent(true);
    }

    private void initializePlayersClient(Client client){

        // Read player info
        String[] info = client.readMsg().split(",");

        // Player cards
        Card[] playerCards = {new Card(info[7]), new Card(info[8])};

        // Dealer Cards
        dealerCards = new Card[5];
        for (int i=0; i<5; i++){
            dealerCards[i] = new Card(info[i+9]);
        }


        Player player = new Player(info[0], Integer.parseInt(info[1]), Integer.parseInt(info[2]),
                Boolean.parseBoolean(info[3]), Boolean.parseBoolean(info[4]),
                Boolean.parseBoolean(info[5]), Boolean.parseBoolean(info[6]), playerCards);

        startingPos = INITIAL_POSITION;
        smallBlind = INITIAL_SMALL_BLIND;
        bigBlind = INITIAL_BIG_BLIND;

        setPlayerBlinds(startingPos, true);
    }

    private void raiseBlinds(){
        smallBlind *= BLIND_RAISE_RATIO;
        bigBlind *= BLIND_RAISE_RATIO;
    }

    private void setPlayerBlinds(int startingPos, boolean state){
        players[(startingPos - 1) % playerNum].setBigBlind(state);
        players[(startingPos - 2) % playerNum].setSmallBlind(state);
    }

    // execute when fold button is clicked
    public void fold(){
        players[currentPlayer].setFolded(true);
    }

    // execute when call button is clicked
    public void call(){
        players[currentPlayer].editCurrentMoney((-1)*amountToCall);
        increasePot(amountToCall);
    }

    // execute when check button is clicked
    public void check(){
        // same as calling 0 when amountCalled == amountToCall
    }

    // execute when bet button is clicked
    public void bet(int wager){
        players[currentPlayer].editCurrentMoney((-1)*wager);
        increasePot(wager);
    }
    private void checkElimated(){
        for (Player player : players){
            if (player.getCurrentMoney() == 0){
                removePlayer(player.getPlayerPosition());
            }
        }
    }
    private void removePlayer(int index){
        Player playersTemp[] = new Player[this.playerNum];
        for (Player player : players){
            if (player.getPlayerPosition() != index){
                playersTemp[player.getPlayerPosition()] = player;
            }
        }
    }
    private void assignPot(){
        ArrayList <Double> handVals = new ArrayList<Double>();
        for (Player player : players){
            if (!player.hasFolded()){
               handVals.add(new Hand(getDealerCards(),player.getCards()).getHandValue());
            }
        }
        double winningHand = Collections.max(handVals);
        int winnerIndex = handVals.indexOf(winningHand);
        players[winnerIndex].editCurrentMoney(pot);
    }
    private void increasePot(int wager){
        pot += wager;
    }
}