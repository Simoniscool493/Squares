package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Grid 
{
	public static GridPoint[][] grid = new GridPoint[U.gridWidth][U.gridHeight];
	public static GridPoint nullPoint = new GridPoint()
	{
		public void refresh() {};
		public void drawSelectionBox(Graphics2D g2,Color c) {};
		public void startClaim(Player p) {};
	};

	Grid()
	{
		init();
	}
	
	void init()
	{
		for(int i=0;i<U.gridWidth;i++)
		{
			for(int j=0;j<U.gridHeight;j++)
			{
				grid[i][j] = new GridPoint(i,j);
			}
		}
	}
		
	static GridPoint getPoint(int x,int y)
	{
		try
		{
			return grid[x][y];
		}	
		catch(ArrayIndexOutOfBoundsException e)
		{
			return nullPoint;
		}
	}
}
