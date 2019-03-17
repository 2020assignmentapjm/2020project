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
	}

	public void play(int amountToCall){
		if (amountCalled < amountToCall){
			// decision is called by UI
		}
	}

	public String toString(){
		return playerName + "," + currentMoney + "," + playerPosition + "," + isCurrent + "," +
				isBigBlind + "," + isSmallBlind + "," + folded + "," + cards[0].toString() + "," + cards[1].toString();
	}

	public Card[] getCards(){
		return cards;
	}

	public boolean hasFolded(){
		return folded;
	}

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
	public void setBigBlind(boolean isBigBlind) {
		this.isBigBlind = isBigBlind;
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
	public void setSmallBlind(boolean isSmallBlind) {
		this.isSmallBlind = isSmallBlind;
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
	public void editCurrentMoney(int currentMoney) {
		this.currentMoney += currentMoney;
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