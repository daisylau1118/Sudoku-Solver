package codes;

public class Cell
{
	private int number;
	private int boxID;
	private boolean[] potential = {false,true,true,true,true,true,true,true,true,true};
	
	public Cell()
	{
		number = 0;
		boxID = 0;
	}
	
	public int getNumber()
	{
		return number;
	}
	
	public void setNumber(int number)
	{
		this.number = number;
		
		for(int x = 0; x < 10; x++)
		{
			if(x != number)
				potential[x] = false;
		}
	}
	
	public int getBoxID()
	{
		return boxID;
	}
	
	public void setBoxID(int boxID)
	{
		this.boxID = boxID;
	}
	
	public boolean[] getPotential() 
	{
		return potential;
	}
	
	public void setPotential(boolean[] potential) 
	{
		this.potential = potential;
	}
	
	public void turnOffPotential(int number)
	{
		potential[number] = false;
	}
	
	public void showPotential()
	{
		for(int x = 1; x < 10; x++)
			System.out.print(x + ":" +potential[x] + " ");
	}
	
	public boolean canBe(int number)
	{
		if(number<10 && number > 0)
			return potential[number];
		
		else return false;
	}
	
	public int getFirstPotential()
	{
		for (int x = 1; x < 10; x++)
			if(potential[x] == true)
				return x;
		
		return -1;
	}
	
	public int getSecondPotential()
	{
		boolean firstPotentialFound = false;
		
		for (int x = 1; x < 10; x++)
		{
			if(potential[x] == true && firstPotentialFound == false)
			{
				firstPotentialFound = true;
			}
			else if(potential[x] == true && firstPotentialFound == true)
			{
				return x;
			}
		}
		
		return -1;
	}
	
	public int numberOfPotentials()
	{
		int count = 0;
		
		for(int x = 1; x < 10; x++)
			if(potential[x] == true)
				count++;
		
		return count;
	}
}