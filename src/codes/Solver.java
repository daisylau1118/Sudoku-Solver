package codes;

import java.io.File;
import java.util.Scanner;

public class Solver
{
	private static Cell[][] board = new Cell[9][9];
	
	public static void solve(int x, int y, int number)
	{
		board[x][y].setNumber(number);
		
		//turning off the potential number for each cell in the column
		for(int i = 0; i < 9; i++)
		{
			if(i != x)
				board[i][y].turnOffPotential(number);
		}
		
		//turn off the potential number for each cell in the row
		for(int i = 0; i < 9; i++)
		{
			if(i != y)
				board[x][i].turnOffPotential(number);
		}
		
		//turn off the potential number for all items in the box.
		for(int i = 0; i < 9; i++)
		{
			for(int j = 0; j < 9; j++)
			{
				if(board[i][j].getBoxID() == board[x][y].getBoxID() && (i!=x && j !=y))
					board[i][j].turnOffPotential(number);
			}
		}
	}
	
	public static void display()
	{
	
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(y == 3 || y == 6)
					System.out.print("| ");
				
				System.out.print(board[x][y].getNumber() + " ");
			}
			
			System.out.println();
			
			if(x == 2 || x == 5)
				System.out.println("---------------------");
		}
	}
	
	public static void displayPotentials()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				if(y == 3 || y == 6)
					System.out.print("| ");
				
				board[x][y].showPotential();
			}
			
			System.out.println();
			
			if(x == 2 || x == 5)
				System.out.println("---------------------");
		}
	}
	
	public static void setBoxIDs()
	{
		for(int x = 0; x < 9; x++)
		{
			for(int y = 0; y < 9; y++)
			{
				board[x][y].setBoxID((x/3)*3 + y/3 + 1);
			}
		}
	}
	
	public static void loadPuzzle(String filename) throws Exception
	{
		File infile = new File(filename);
		Scanner input = new Scanner(infile);
		
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
			{
				int data = input.nextInt();
				if(data != 0)
					solve(x, y, data);
			}
		
		input.close();
	}
	
	public static int onlyOnePossibleCondition()
	{
		int changeCounter = 0;
		int counter = 0;
		int number = 0;
		
		for (int column = 0; column < 9; column++)
			for (int row = 0; row < 9; row++)
			{
				for (int potential = 1; potential < 10; potential++)
				{					
					if (board[column][row].getPotential()[potential] == true)
					{
						counter++;
						number = potential;
					}
				}
				
				if (counter == 1 && board[column][row].getNumber() == 0)
				{
					solve(column, row, number);
					changeCounter++;
				}
				
				counter = 0;
			}
		
		return changeCounter;
	}

	public static int rowCondition()
	{
		int changeCounter = 0;
		int counter = 0;
		int number = 0;
		int actualRow = 0;
		
		for (int column = 0; column < 9; column++)
			for (int potential = 1; potential < 10; potential++)
			{
				for (int row = 0; row < 9; row++)
				{
					if (board[column][row].getNumber() == 0 && board[column][row].getPotential()[potential] == true)
					{
						counter++;
						number = potential;
						actualRow = row;
					}
				}
				
				if (counter == 1)
				{
					solve(column, actualRow, number);
					changeCounter++;
				}
				
				counter = 0;
			}
		
		return changeCounter;
	}
	
	public static int columnCondition()
	{
		int changeCounter = 0;
		int counter = 0;
		int number = 0;
		int actualColumn = 0;
		
		for (int row = 0; row < 9; row++)
			for (int potential = 1; potential < 10; potential++)
			{
				for (int column = 0; column < 9; column++)
				{
					if (board[column][row].getNumber() == 0 && board[column][row].getPotential()[potential] == true)
					{
						counter++;
						number = potential;
						actualColumn = column;
					}
				}
				
				if (counter == 1)
				{
					solve(actualColumn, row, number);
					changeCounter++;
				}
				
				counter = 0;
			}
		
		return changeCounter;
	}
	
	public static int boxIDCondition()
	{
		int changeCounter = 0;
		int counter = 0;
		int number = 0;
		int actualColumn = 0;
		int actualRow = 0;
		
		for (int boxID = 0; boxID < 9; boxID++)
			for (int potential = 1; potential < 10; potential++)
			{
				for (int column = 0; column < 9; column++)
					for (int row = 0; row < 9; row++)
					{
						if (board[column][row].getBoxID() == boxID && board[column][row].getNumber() == 0 && board[column][row].getPotential()[potential] == true)
						{
							counter++;
							number = potential;
							actualColumn = column;
							actualRow = row;
						}
					}

				if (counter == 1)
				{
					solve(actualColumn, actualRow, number);
					changeCounter++;
				}
				
				counter = 0;
			}
		
		return changeCounter;
	}
	
	public static int logic4r()
	{
		int changes = 0;
		
		for(int x = 0; x < 9; x++)
		{
			//cycling through the columns in row x
			for(int y = 0; y < 9; y++)
			{
				//found a cell with 2 potentials (2P)
				if(board[x][y].numberOfPotentials() == 2)
				{
					//looking in the rest of the row
					for(int j = y+1; j < 9; j++)
					{
						//found another 2P cell!
						if(board[x][j].numberOfPotentials() == 2)
						{
							//if they have the same two potentials
							if(board[x][y].getFirstPotential() == board[x][j].getFirstPotential() && board[x][y].getSecondPotential() == board[x][j].getSecondPotential())
							{
								//turn off the potentials for the rest of the row
								//BUT!! not these two cells
								for(int q = 0; q < 9; q++)//cycling through the row, starting at the beginning
								{
									if(q == y || q == j)//if we are at one of the two cells
									{}//do nothing
									else
									{
										if(board[x][q].canBe(board[x][y].getFirstPotential()))
										{
											board[x][q].turnOffPotential(board[x][y].getFirstPotential());
											changes++;
										}
										
										if(board[x][q].canBe(board[x][y].getSecondPotential()))
										{
											board[x][q].turnOffPotential(board[x][y].getSecondPotential());
											changes++;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return changes;
	}
	
	public static int logic4c()
	{
		int changes = 0;
		
		for (int y = 0; y < 9; y++) //y is column
			for (int x = 0; x < 9; x++) //x is row
			{
				if (board[x][y].numberOfPotentials() == 2)
				{
					for (int j = x + 1; j < 9; j++)
					{
						if (board[j][y].numberOfPotentials() == 2)
						{
							if (board[x][y].getFirstPotential() == board[j][y].getFirstPotential() && board[x][y].getSecondPotential() == board[j][y].getSecondPotential())
								for (int q = 0; q < 9; q++)
								{
									if (q == x || q == j)
									{}
									else
									{
										if (board[q][y].canBe(board[x][y].getFirstPotential()))
										{
											board[q][y].turnOffPotential(board[x][y].getFirstPotential());
											changes++;
										}
										
										if (board[q][y].canBe(board[x][y].getSecondPotential()))
										{
											board[q][y].turnOffPotential(board[x][y].getSecondPotential());
											changes++;
										}
									}
								}
						}
					}
				}
			}
			
		return changes;
	}
	
	public static int logic4b()
	{
		int changes = 0;
		
		for (int boxID = 0; boxID < 9; boxID++)
		{
			for (int x = 0; x < 0; x++)	//x is column
				for (int y = 0; y < 0; y++)	//y is row
				{
					if (board[x][y].numberOfPotentials() == 2 && board[x][y].getBoxID() == boxID)
					{
						for (int j = x + 1; j < 9; j++)
							for (int k = y + 1; k < 9; k++)
							{
								if (board[j][k].numberOfPotentials() == 2 && board[j][k].getBoxID() == boxID)
								{
									if (board[x][y].getFirstPotential() == board[j][k].getFirstPotential() && board[x][y].getSecondPotential() == board[j][k].getSecondPotential() && board[x][y].getBoxID() == board[j][k].getBoxID())
									{
										for (int q = 0; q < 9; q++)
											for (int w = 0; w < 0; w++)
											{
												if (q == x && w == y || q == j && w == k)
												{}
												else
												{
													if (board[j][k].canBe(board[x][y].getFirstPotential()))
													{
														board[j][k].turnOffPotential(board[x][y].getFirstPotential());
														changes++;
													}
													
													if (board[j][k].canBe(board[x][y].getSecondPotential()))
													{
														board[j][k].turnOffPotential(board[x][y].getSecondPotential());
														changes++;
													}
												}
											}
										
									}
								}
							}
					}
				}
		}
		
		return changes;
	}
	
	public static int computerGuess()		//create an object with the coordinate of the changed box, number it changed into, and copy of the grid
	{
		Guess[] guessesMade = new Guess[81];
		int changes = 0;
		
		for (int guess = 0; guess < 81; guess++)
			for (int column = 0; column < 9; column++)
				for (int row = 0; row < 0; row++)
				{
					if (board[column][row].getNumber() == 0 && board[column][row].getFirstPotential() > 0)
					{
						guessesMade[guess].setNumber(board[column][row].getFirstPotential());
						guessesMade[guess].setChangedX(row);
						guessesMade[guess].setChangedY(column);
						guessesMade[guess].setCopy(board);
						solve(column, row, board[column][row].getFirstPotential());
						changes++;
					}
				}
		
		return changes;
	}
	
	public static void main(String[] args)throws Exception 
	{
		int changes = 0;
		
		//Creating each cell in the board
		for(int x = 0; x < 9; x++)
			for(int y = 0; y < 9; y++)
				board[x][y] = new Cell();
		
		setBoxIDs();
		
		//load the puzzle
		loadPuzzle("medium.txt");
		display();
		
		do
		{
			changes += onlyOnePossibleCondition();
			changes += rowCondition();
			changes += columnCondition();
			changes += boxIDCondition();
			changes += logic4r();
			changes += logic4c();
			changes += logic4b();
			
			for (int x = 0; x < 9; x++)
				for (int y = 0; y < 9; y++)
					if (changes == 0 && board[x][y].getNumber() == 0)
						for (int potential = 1; potential < 10; potential++)
							if (board[x][y].getPotential()[potential] == false)
							{}//make a revert method
							else
							{
								changes += computerGuess();
							}
			
			changes--;
		}while (changes > 0);
		
		System.out.println();
		display();
		

	}
}