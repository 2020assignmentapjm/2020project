package user;

import game.card.Card;

public class Player {
	// Variables
	private String playerName;
	private int currentMoney;
	private int playerPosition;
	private boolean isCurrent;
	private boolean isBigBlind;
	private boolean isSmallBlind;
	private boolean folded;
	private Card[] cards;
	private int amountCalled;


	// money attribute, chip amount, name, file io stats, profile, fold,call, raise methods.
	public Player(String playerName, int currentMoney, int playerPosition, boolean isCurrent,
				  boolean isBigBlind, boolean isSmallBlind, boolean folded, Card[] cards) {
		this.playerName = playerName;
		this.currentMoney = currentMoney;
		this.playerPosition = playerPosition;
		this.isCurrent = isCurrent;
		this.isBigBlind = isBigBlind;
		this.isSmallBlind = isSmallBlind;
		this.folded = folded;
		this.cards = cards;
		amountCalled = 0;
	}

	public String toString(){
		return playerName + "," + String.valueOf(currentMoney) + "," + String.valueOf(playerPosition) + "," +
				String.valueOf(isCurrent) + "," + String.valueOf(isBigBlind) + "," + String.valueOf(isSmallBlind) + "," +
				String.valueOf(folded) +"," + cards[0].toString() + "," + cards[1].toString();
	}
	/**
	 * @param amount The money the player has called
	 */
	public void setAmountCalled(int amount){
		this.amountCalled = amount;
	}
	/**
	 * @param amount the money the player has in game
	 */
	public void setMoney(int amount){
		this.currentMoney = amount;
	}
	/**
	 * @return users cards
	 */
	public Card[] getCards(){
		return cards;
	}
	/**
	 * @return if the player has folded
	 */
	public boolean hasFolded(){
		return folded;
	}
	/**
	 * @param state current boolean status of players fold
	 */
	public void setFolded(boolean state){
		folded = state;
	}

	/**
	 * @return the isCurrent
	 */
	public boolean isCurrent() {
		return isCurrent;
	}

	/**
	 * @param isCurrent the isCurrent to set
	 */
	public void setCurrent(boolean isCurrent) {
		this.isCurrent = isCurrent;
	}

	/**
	 * @return the isBigBlind
	 */
	public boolean isBigBlind() {
		return isBigBlind;
	}

	/**
	 * @param isBigBlind the isBigBlind to set
	 */
	public void setBigBlind(boolean isBigBlind, int bigBlind) {
		this.isBigBlind = isBigBlind;
		if (isBigBlind){
			this.currentMoney -= bigBlind;
			this.amountCalled = bigBlind;
		}
	}

	/**
	 * @return the isSmallBlind
	 */
	public boolean isSmallBlind() {
		return isSmallBlind;
	}

	/**
	 * @param isSmallBlind the isSmallBlind to set
	 */
	public void setSmallBlind(boolean isSmallBlind, int smallBlind) {
		this.isSmallBlind = isSmallBlind;
		if (isSmallBlind){
			this.currentMoney -= smallBlind;
			this.amountCalled = smallBlind;
		}
	}

	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}

	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	/**
	 * @return the currentMoney
	 */
	public int getCurrentMoney() {
		return currentMoney;
	}

	/**
	 * @param currentMoney the currentMoney to set
	 */
	public void addMoney(int currentMoney) {
		this.currentMoney += currentMoney;
	}
	/**
	 * @param wager the money the player bets
	 */
	public void editAmountCalled(int wager){
		this.amountCalled += wager;
	}
	/**
	 * @return the amount the player has called
	 */
	public int getAmountCalled(){
		return amountCalled;
	}

	/**
	 * @return the playerPosition
	 */
	public int getPlayerPosition() {
		return playerPosition;
	}

	/**
	 * @param playerPosition the playerPosition to set
	 */
	public void setPlayerPosition(int playerPosition) {
		this.playerPosition = playerPosition;
	}
}
