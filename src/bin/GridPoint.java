package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GridPoint 
{
	static int width = (int)(U.incWidth);
	static int height = (int)(U.incHeight);

	int x;
	int y;
	
	boolean changed;
	Color background;
	Projectile projectile;
	Wall wall;
	ConstructedEntity construct;
	
	ArrayList<Entity> contents = new ArrayList<Entity>();
	
	public GridPoint() 
	{
		x = -1;
		y = -1;
	}
	
	public GridPoint(int pointX,int pointY)
	{
		x = pointX;
		y = pointY;
		background = Color.white;
		changed = true;
	}

	public void render(Graphics2D g2)
	{
		if(hasConstruct())
		{
			construct.render(g2);
		}
		else if(hasWall())
		{
			wall.render(g2);
		}
		else
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
			
			if(hasProjectile())
			{
				projectile.render(g2);
			}
		}
        
        changed = false;
	}

	public void addEntity(Entity e)
	{
		contents.add(e);
		refresh();
	}
	
	public void removeEntity(Entity e)
	{
		contents.remove(e);
		refresh();
	}
	
	public void addWall(Wall w)
	{
		if(!hasConstruct())
		{
			wall = w;
			refresh();
		}
	}
	
	public void removeWall()
	{	
		wall = null;
		refresh();
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
	
	public Entity getWall()
	{
		return wall;
	}
	
	public void addProjectile(Projectile p)
	{
		projectile = p;
		refresh();
	}
	
	public void removeProjectile()
	{
		projectile = null;
		refresh();
	}
	
	public boolean hasProjectile()
	{
		if(projectile == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public Projectile getProjectile()
	{
		return projectile;
	}
	
	public void addConstruct(ConstructedEntity c)
	{
		if(!hasWall()&&!hasConstruct())
		{
			construct = c;
			refresh();
		}
	}
	
	public void removeConstruct()
	{
		construct = null;
		refresh();
	}
	
	public boolean hasConstruct()
	{
		if(construct == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public ConstructedEntity getConstruct()
	{
		return construct;
	}
	
	public void refresh()
	{
		changed = true;
		DrawApplet.changed.add(this);
	}

	public String toString()
	{
		return Integer.toString(x) + " " + Integer.toString(y) + " ";
	}
	
	public void init()
	{
		
	}
	
	public GridPoint getFront(int n)
	{
		if(n==0)
		{
			return Grid.getPoint(x,y-1);
		}
		else if(n==1)
		{
			return Grid.getPoint(x+1,y);
		}
		else if(n==2)
		{
			return Grid.getPoint(x,y+1);
		}
		else
		{
			return Grid.getPoint(x-1,y);
		}	
	}
	
	public void drawSelectionBox(Graphics2D g2,Color c)
	{
		g2.setColor(c);
		g2.drawRect((width*x)+1, (width*y)+1, width-2, height-2);
	}
	
	public boolean isNullPoint()
	{
		if(x<0)
		{
			return true;
		}
		
		return false;
	}
}
