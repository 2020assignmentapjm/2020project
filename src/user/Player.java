package user;

public class Player
{
	public String playerName;
	public int currentMoney;
	public int chipAmount;
	//money attribute, chip amount, name, file io stats, profile, fold,call, raise methods.
	public Player()
	{
		this.playerName = "User";
		this.currentMoney = 10000;
	}
	public Player(String playerName, int currentMoney, int chipAmount)
	{
		this.playerName = playerName;
		this.currentMoney = currentMoney;
		this.chipAmount = chipAmount;
	}
	
	public String getPlayerName()
	{
		return this.playerName;
	}
	
	public void setPlayerName(String newPlayerName)
	{
		this.playerName = newPlayerName;
	}
	
	public int CurrentMoney()
	{
		return this.currentMoney;
	}
	
	public void setCurrentMoney(int newMoney)
	{
		this.currentMoney = newMoney;
	}
	
	public int getChipAmount()
	{
		return this.chipAmount;
	}
	
	public void setChipAmount(int newChipAmount)
	{
		this.chipAmount = newChipAmount;
	}
	
	public void stats()
	{
		//fill
	}
	
	public void fold()
	{
		//fill
	}
	
	public void call()
	{
		//fill
	}
	
	public void raise()
	{
		//fill
	}
	
	public void profile()
	{
		//fill
	}
}