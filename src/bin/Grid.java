package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class Grid implements Serializable
{
	public GridPoint[][] grid = new GridPoint[U.gridWidth][U.gridHeight];
	public GridPoint nullPoint = new GridPoint()
	{
		public void refresh() {};
		public void startClaim(Player p,int c) {};
		public void zoomRender(Graphics2D g2,int newX,int newY)
		{
			g2.setColor(Color.darkGray);
	        g2.fillRect(((int)(width*newX)),((int)(height*newY)),((int)width),((int)height));
		}
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
		
	GridPoint getPoint(int x,int y)
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
	
	void refresh(Graphics2D g2)
	{
		drawPoints();
		if(U.showGrid)	
		{
			drawGrid(g2);
		}
	}
	
	void drawPoints()
	{
		for(int i=0;i<U.gridWidth;i++)
		{
			for(int j=0;j<U.gridHeight;j++)
			{
				getPoint(i,j).refresh();
			}
		}
	}
	
	void drawGrid(Graphics2D g2)
	{

		float dw = U.drawWidth;
		float dh = U.drawHeight;
						
		g2.setColor(U.gridColor);
		
		if(U.showGrid)
		{
			if(U.zoom)
			{
				float iw = U.zoomIncWidth;
				float ih = U.zoomIncHeight;

				for(float i=0;i<dw+10;i+=iw)
		        {
		           g2.drawLine((int)i,0,(int)i,(int)dw);
		        }
		        for(float i=0;i<dh+10;i+=ih)
		        {
		            g2.drawLine(0,(int)i,(int)dw,(int)i);
		        }
			}
			else
			{
				float iw = U.incWidth;
				float ih = U.incHeight;

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
	
	void coverGrid(int f,int level)
	{
		for(int i=0;i<U.gridWidth;i++)
		{
			for(int j=0;j<U.gridHeight;j++)
			{
				if((int)(Math.random() * 100)<f)
				{
					new Wall(getPoint(i,j),level);
				}
			}
		}
	}
	
	void wallRect(int x,int y,int w,int h,Color c,int lv)
	{
		for(int i=y;i<h;i++)
		{
			for(int j=x;j<w;j++)
			{
				new Wall(getPoint(i,j),c,lv);
			}
		}
	}
}
