package user;

public class Player {
	// Variables
	private String playerName;
	private int currentMoney;
	private int playerPosition;
	private boolean isCurrent;
	private boolean isBigBlind;
	private boolean isSmallBlind;

	// money attribute, chip amount, name, file io stats, profile, fold,call, raise
	// methods.
	public Player(String playerName, int currentMoney, int playerPosition) {
		this.playerName = playerName;
		this.currentMoney = currentMoney;
		this.playerPosition = playerPosition;
		this.isCurrent = false;
		this.isBigBlind = false;
		this.isSmallBlind = false;
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
	public void setCurrentMoney(int currentMoney) {
		this.currentMoney = currentMoney;
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