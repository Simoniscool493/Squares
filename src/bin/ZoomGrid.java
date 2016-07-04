package bin;

import java.awt.Graphics2D;

public class ZoomGrid 
{
	public class ZoomGridPoint
	{
		int x;
		int y;
		
		ZoomGridPoint(int x2,int y2)
		{
			x = x2;
			y = y2;
		}
		
		public void renderPoint(Graphics2D g2,int x2,int y2)
		{
			Grid.getPoint(x,y).zoomRender(g2,x2,y2);
		}
		
		public String toString()
		{
			return(x + "," + y);
		}
	}
	
	public static int gridSize = U.zoomGridSize;

	public static ZoomGridPoint[][] zoomGrid = new ZoomGridPoint[gridSize][gridSize];
	
	
	ZoomGrid(int x,int y)
	{
		for(int i = 0;i<gridSize;i++)
		{
			for(int j = 0;j<gridSize;j++)
			{
				zoomGrid[j][i] = new ZoomGridPoint(x+j-U.zoomRad,y+i-U.zoomRad);
			}
		}
	}
	
	public static void render(Graphics2D g2)
	{
		for(int i = 0;i<gridSize;i++)
		{
			for(int j = 0;j<gridSize;j++)
			{
				zoomGrid[j][i].renderPoint(g2,j,i);
				//System.out.println(zoomGrid[j][i]);			
			}
		}
	}
	
	public static void move(int Xoffs,int Yoffs)
	{
		for(int i = 0;i<gridSize;i++)
		{
			for(int j = 0;j<gridSize;j++)
			{
				zoomGrid[j][i].x += Xoffs;
				zoomGrid[j][i].y += Yoffs;
			}
		}
	}
}
