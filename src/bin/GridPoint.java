package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class GridPoint 
{
	static Color defaultBackground = U.background;

	static float width = (U.incWidth);
	static float height = (U.incHeight);
	static float zoomWidth = (U.zoomIncWidth);
	static float zoomHeight = (U.zoomIncHeight);
	static int zoomRad = U.zoomRad;
	
	boolean changed;
	
	Color background;

	int x;
	int y;
	
	boolean claimed = false;
	Player claimer;
	int claimCount = 0;
	int claimCap = 100;
	
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
		background = defaultBackground;
		changed = true;
	}

	public void render(Graphics2D g2)
	{
		g2.setColor(background);
        //g2.fillRect((width*x)+1,(height*y)+1,width-1,height-1);
        g2.fillRect(((int)(width*x))+1,((int)(height*y))+1,((int)width)-1,((int)height)-1);
        
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
		//g2.drawRect((width*x)+1, (height*y)+1, width-2, height-2);
	     g2.drawRect(((int)(width*x))+1,((int)(height*y))+1,((int)width)-2,((int)height)-2);

	}
	
	public boolean isNullPoint()
	{
		if(x<0)
		{
			return true;
		}
		
		return false;
	}
	
	public void takeControl(Player p)
	{
		if(claimer==null)
		{
			claimer=p;
			claimCap = p.claimCap;
		}
		else if(claimer!=p)
		{
			claimCount-=2;
			if(claimCount==0)
			{
				claimer = p;
				claimCap = p.claimCap;
				background = defaultBackground;
				claimed = false;
				DrawApplet.activeDeadList.add(this);
			}
		}
		else if(!claimed)
		{
			claimCount++;
			if(p.claimCap<claimCap)
			{
				claimCap=p.claimCap;
			}
			
			if(claimCount>=claimCap)
			{
				claimed = true;
				background = claimer.claimColor;
				refresh();
				spreadClaim((int)(p.claimCap*1.2));

				DrawApplet.activeDeadList.add(this);
			}
		}
		
	}
	
	public void startClaim(Player p,int c)
	{
		if(claimer==null&&!hasWall())
		{
			claimer = p;
			claimCap = c;
			DrawApplet.activeBirthList.add(this);
		}
		else if(p==claimer&&!claimed&!hasWall())
		{
			if(c<claimCap)
			{
				claimCap=c;
			}
			DrawApplet.activeBirthList.add(this);
		}
	}
	
	public void progress()
	{
		claimCount++;
		
		if(claimCount>=claimCap)
		{
			claimed = true;
			background = claimer.claimColor;
			DrawApplet.activeDeadList.add(this);
			refresh();
			spreadClaim((int)(claimCap*1.2));
		}
	}
	
	public void spreadClaim(int i)
	{
		getFront(0).startClaim(claimer,i);
		getFront(1).startClaim(claimer,i);
		getFront(2).startClaim(claimer,i);
		getFront(3).startClaim(claimer,i);
	}
	
	public void wallOn()
	{
		if(DrawApplet.activeSpots.contains(this))
		{
			DrawApplet.activeDeadList.add(this);
		}
	}
	
	public boolean isEmpty()
	{
		if(hasWall()||hasProjectile()||hasConstruct()||!(contents.isEmpty()))
		{
			return false;
		}
		
		return true;
	}
	
	public boolean inView()
	{
		return true;
	}
}
