package user;

public class Player
{
	public String playerName;
	public int currentMoney;
	//money attribute, chip amount, name, file io stats, profile, fold,call, raise methods.
	public Player(String playerName, int currentMoney)
	{
		this.playerName = playerName;
		this.currentMoney = currentMoney;
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
		
	}
}