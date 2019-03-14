package game;

import user.Player;

public class GameLoop {

    // Variables
    private Player[] players;
    private int currentPlayer;
    private int smallBlind, bigBlind;
    private int startingPos;
    private boolean gameEnded;
    private boolean roundEnded;
    private int roundNum;
    private int amountToCall;

    // Constant
    private final int INITIAL_MONEY = 10000;
    private final int INITIAL_POSITION = 0;
    private final int INITIAL_SMALL_BLIND = 50;
    private final int INITIAL_BIG_BLIND = 100;
    private final int PLAYER_NUM;
    private final int BLIND_RAISE_ROUND_NUM = 5;
    private final int BLIND_RAISE_RATIO = 2;

    public GameLoop(int playerNum) {

        PLAYER_NUM = playerNum;

        initializePlayers();

        roundNum = 0;

        gameEnded = false;
        roundEnded = false;

        while(!gameEnded){

            amountToCall = bigBlind;

            while(!roundEnded){

                players[currentPlayer].play(amountToCall);
                // decision is called by UI


                players[currentPlayer].setCurrent(false);

                currentPlayer = (currentPlayer + 1) % playerNum;

                players[currentPlayer].setCurrent(true);
            }

            setPlayerBlinds(startingPos, false);

            startingPos = (startingPos + 1) % playerNum;

            setPlayerBlinds(startingPos, true);

            roundNum++;

            if (roundNum % BLIND_RAISE_ROUND_NUM == BLIND_RAISE_ROUND_NUM-1){
                raiseBlinds();
            }
        }

    }

    private void initializePlayers(){
        players = new Player[PLAYER_NUM];
        
        for (int i=0; i<PLAYER_NUM; i++){
            players[i] = new Player("Player " + Integer.toString(i+1), INITIAL_MONEY, i);
        }

        startingPos = INITIAL_POSITION;
        smallBlind = INITIAL_SMALL_BLIND;
        bigBlind = INITIAL_BIG_BLIND;
        
        setPlayerBlinds(startingPos, true);

        currentPlayer = startingPos;
        players[currentPlayer].setCurrent(true);
    }

    private void raiseBlinds(){
        smallBlind *= BLIND_RAISE_RATIO;
        bigBlind *= BLIND_RAISE_RATIO;
    }

    private void setPlayerBlinds(int startingPos, boolean state){
        players[(startingPos - 1) % PLAYER_NUM].setBigBlind(state);
        players[(startingPos - 2) % PLAYER_NUM].setSmallBlind(state);
    }

}