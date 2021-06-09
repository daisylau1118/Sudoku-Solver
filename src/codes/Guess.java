package codes;

public class Guess 
{
	private int number;
	private int changedX;
	private int changedY;
	private Cell[][] copy;
	
	public int getNumber() 
	{
		return number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
	}
	
	public int getChangedX()
	{
		return changedX;
	}
	
	public void setChangedX(int changedX)
	{
		this.changedX = changedX;
	}
	
	public int getChangedY()
	{
		return changedY;
	}
	
	public void setChangedY(int changedY)
	{
		this.changedY = changedY;
	}
	
	public Cell[][] getCopy()
	{
		return copy;
	}
	
	public void setCopy(Cell[][] copy) 
	{
		this.copy = copy;
	}
}
