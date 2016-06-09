package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GridPoint 
{
	int x;
	int y;
	Color background;
	boolean changed;
	Wall wall;
	
	ArrayList<Entity> contents = new ArrayList<Entity>();
	
	public GridPoint(int pointX,int pointY)
	{
		x = pointX;
		y = pointY;
		background = Color.white;
		changed = true;
	}

	public void render(Graphics2D g2)
	{
		int width = (int)(U.incWidth);
		int height = (int)(U.incHeight);
		
		if(!hasWall())
		{
			g2.setColor(background);
	        g2.fillRect((width*x)+1,(height*y)+1,width-1,height-1);
	        
			if(contents!=null)
			{
				for(Entity e:contents)
				{
					e.render(g2);
				}
			}
		}
		else
		{
			g2.setColor(wall.color);
	        g2.fillRect((width*x)+1,(height*y)+1,width-1,height-1);

		}
        
        changed = false;
        
	}
	

	public Entity getWall()
	{
		return wall;
	}
	
	public boolean hasWall()
	{
		if(wall == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public void init()
	{
		
	}
}
