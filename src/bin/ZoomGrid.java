package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.Serializable;

public class ZoomGrid implements Serializable
{
	public class ZoomGridPoint
	{
		int prev;
		Color prevColor;
		int x;
		int y;
		
		ZoomGridPoint(int x2,int y2)
		{
			x = x2;
			y = y2;
		}
		
		public void renderPoint(Graphics2D g2,int x2,int y2)
		{
			getPoint().zoomRender(g2,x2,y2);
		}
		
		public GridPoint getPoint()
		{
			return Game.currentGame.grid.getPoint(x, y);
		}
		
		public void refresh()
		{
			Game.currentGame.grid.getPoint(x,y).refresh();
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
			}
		}
	}
	
	public static void move(int Xoffs,int Yoffs)
	{
		int n = 0;
		
		for(int i = 0;i<gridSize;i++)
		{
			for(int j = 0;j<gridSize;j++)
			{
				ZoomGridPoint z = zoomGrid[j][i];

				z.x += Xoffs;
				z.y += Yoffs;

				GridPoint newp = z.getPoint();
				
				if(newp.isZoomMoved())
				{
					z.refresh();
					n++;
					newp.setZoomMoved(false);
				}
				else if(z.prev==1&&newp.hasWall()&&newp.getWall().getColor()==z.prevColor)
				{
					
				}
				/*else if(z.prev==2&&newp.isEmpty()&&newp.background==z.prevColor)
				{
					
				}*/
				else
				{
					z.refresh();
					n++;
				}
				
				if(newp.hasWall())
				{
					z.prev = 1;
					z.prevColor = newp.getWall().getColor();
				}
				/*else if(newp.isEmpty())
				{
					z.prev = 2;
					z.prevColor = newp.background;
				}*/
				else
				{
					z.prev = 0;
				}
			}
		}
	}
}
