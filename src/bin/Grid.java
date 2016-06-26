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
		public void startClaim(Player p,int c) {};
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
	
	static void refresh(Graphics2D g2)
	{
		for(int i=0;i<U.gridWidth;i++)
		{
			for(int j=0;j<U.gridHeight;j++)
			{
				getPoint(i,j).refresh();
			}
		}
		
		drawGrid(g2);
	}
	
	static void drawGrid(Graphics2D g2)
	{
		float dw = U.drawWidth;
		float dh = U.drawHeight;
		float iw = U.incWidth;
		float ih = U.incHeight;
		
		if(U.showGrid)
		{
	        for(float i=0;i<dw+10;i+=iw)
	        {
	           g2.drawLine((int)i,0,(int)i,(int)dw);
	        }
	        for(float i=0;i<dh+10;i+=ih)
	        {
	            g2.drawLine(0,(int)i,(int)dw,(int)i);
	        }
		}
	}
}
