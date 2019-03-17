package game;

import user.Player;

public class GameLoop {

    // Variables
    private Player[] players;
    private int currentPlayer;
    private int pot;
    private int smallBlind, bigBlind;
    private int startingPos;
    private boolean gameEnded;
    private boolean roundEnded;
    private int roundNum;
    private int amountToCall;
    private int playerNum;

    // Constant
    private final int INITIAL_MONEY = 10000;
    private final int INITIAL_POSITION = 0;
    private final int INITIAL_SMALL_BLIND = 50;
    private final int INITIAL_BIG_BLIND = 100;
    private final int BLIND_RAISE_ROUND_NUM = 5;
    private final int BLIND_RAISE_RATIO = 2;

    public GameLoop(int playerNum) {

        this.playerNum = playerNum;

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

            checkEliminated();

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
        players = new Player[this.playerNum];

        for (int i=0; i<playerNum; i++){
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
        players[(startingPos - 1) % this.playerNum].setBigBlind(state);
        players[(startingPos - 2) % this.playerNum].setSmallBlind(state);
    }
    public void increasePot(int wager){
        pot += wager;
    }

    public void fold(){
        players[currentPlayer].setFolded(true);
    }

    public void call(){
        players[currentPlayer].setCurrentMoney(players[currentPlayer].getCurrentMoney() - amountToCall);
        increasePot(amountToCall);
    }

    public void check(){
        // same as calling 0 when amountCalled == amountToCall

    }
    public void bet(int wager){
        players[currentPlayer].setCurrentMoney(players[currentPlayer].getCurrentMoney() - wager);
        increasePot(wager);
    }
    /**
     * Checks if any player has zero dollars
     */
    private void checkEliminated(){
       for (Player player : players){
           if (player.getCurrentMoney() == 0){
               removePlayer(player.getPlayerPosition());
           }
       }
    }
    /**
     * Removes player at the specified index
     *
     * @param index index of player to be removed
    */
    private void removePlayer(int index){
        Player  playersTemp [] = new Player[(this.playerNum)-1];
        for (Player player : players){
            if (player.getPlayerPosition() != index){
                playersTemp[player.getPlayerPosition()] = player;
            }
        }
        players = playersTemp;
    }
}