package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class GridPoint 
{
	static Color defaultBackground = U.background;

	static float width = (U.incWidth);
	static float height = (U.incHeight);
	static float zoomWidth = (U.zoomIncWidth);
	static float zoomHeight = (U.zoomIncHeight);
	static int zoomRad = U.zoomRad;
	
	boolean changed;
	boolean zoomMoved = false;
	
	Color background;

	int x;
	int y;
	
	boolean claimed = false;
	Player claimer;
	int claimCount = 0;
	int claimCap = 100;
	
	ArrayList<Projectile> projectiles;
	Wall wall;
	ConstructedEntity construct;
	SelectionBox box;
	Player player;
		
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
		if(U.zoom)
		{
			changed = true;
		}
		
		projectiles = new ArrayList<Projectile>();
	}
	
	public void zoomRender(Graphics2D g2,int newX,int newY)
	{
		if(changed)
		{
			int realX = this.x;
			int realY = this.y;
			this.x = newX;
			this.y = newY;
			//width = U.zoomIncWidth;
			//height = U.zoomIncHeight;
			
			render(g2);
			
			this.x = realX;
			this.y = realY;
			//width = U.incWidth;
			//height = U.incHeight;
			
			changed = false;
		}
	}

	public void render(Graphics2D g2)
	{
		g2.setColor(background);
        //g2.fillRect((width*x)+1,(height*y)+1,width-1,height-1);
		if(U.showGrid)
		{
			g2.fillRect(((int)(width*x))+1,((int)(height*y))+1,((int)width)-1,((int)height)-1);
		}
		else
		{
			g2.fillRect(((int)(width*x)),((int)(height*y)),((int)width),((int)height));
		}
        
		if(hasConstruct())
		{
			construct.render(g2);
		}
		else if(hasWall())
		{
			wall.render(g2);
		}
		else if(hasProjectile())
		{
			for(Projectile p:projectiles)
			{
				p.render(g2);
			}
		}		
        
		if(hasBox())
		{
			box.render(g2);
		}	
		
		if(hasPlayer())
		{
			player.render(g2);
		}
	}

	public void addEntity(Entity e)
	{
		if(e instanceof Wall)
		{
			addWall((Wall)e);
		}
		else if(e instanceof ConstructedEntity)
		{
			addConstruct((ConstructedEntity)e);
		}
		else if(e instanceof Projectile)
		{
			addProjectile((Projectile)e);
		}
		else if(e instanceof SelectionBox)
		{
			addBox((SelectionBox)e);
		}
		else if(e instanceof Player)
		{
			addPlayer((Player)e);
		}
		
		refresh();
	}
	
	public void removeEntity(Entity e)
	{
		if(e instanceof Wall)
		{
			removeWall();
		}
		else if(e instanceof ConstructedEntity)
		{
			removeConstruct();
		}
		else if(e instanceof Projectile)
		{
			removeProjectile((Projectile)e);
		}
		else if(e instanceof SelectionBox)
		{
			removeBox();
		}
		else if(e instanceof Player)
		{
			removePlayer();
		}
		
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
	
	public void addBox(SelectionBox s)
	{
		box = s;
		refresh();
	}
	
	public void removeBox()
	{	
		box = null;
		refresh();
	}
	
	public boolean hasBox()
	{
		if(box == null)
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
		projectiles.add(p);
		refresh();
	}
	
	public void removeProjectile(Projectile p)
	{
		projectiles.remove(p);
		refresh();
	}
	
	public boolean hasProjectile()
	{
		return !(projectiles.isEmpty());	
	}
	
	public void clearProjectiles()
	{
		for(int i=0;i<projectiles.size();i++)
		{
			projectiles.get(i).die();
		}
		
		projectiles.clear();	
	}
	
	public void addPlayer(Player p)
	{
		player = p;
		refresh();
	}
	
	public void removePlayer()
	{
		player = null;
		refresh();
	}
	
	public boolean hasPlayer()
	{
		if(player == null)
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
		if(U.zoom)
		{
			changed = true;
		}
		else
		{
			DrawApp.changed.add(this);
		}
	}

	public String toString()
	{
		return Integer.toString(x) + " " + Integer.toString(y);
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
				DrawApp.activeDeadList.add(this);
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

				DrawApp.activeDeadList.add(this);
			}
		}
		
	}
	
	public void startClaim(Player p,int c)
	{
		if(claimer==null&&!hasWall())
		{
			claimer = p;
			claimCap = c;
			DrawApp.activeBirthList.add(this);
		}
		else if(p==claimer&&!claimed&!hasWall())
		{
			if(c<claimCap)
			{
				claimCap=c;
			}
			DrawApp.activeBirthList.add(this);
		}
	}
	
	public void progress()
	{
		claimCount++;
		
		if(claimCount>=claimCap)
		{
			claimed = true;
			claimer.spots++;
			background = claimer.claimColor;
			DrawApp.activeDeadList.add(this);
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
		if(DrawApp.activeSpots.contains(this))
		{
			DrawApp.activeDeadList.add(this);
		}
	}
	
	public boolean isEmpty()
	{
		if(hasWall()||hasProjectile()||hasConstruct()||hasPlayer())
		{
			return false;
		}
		
		return true;
	}
	
	public String getContentsName()
	{
		if(hasPlayer())
		{
			return "Player";
		}
		else if(hasWall())
		{
			return "Wall";
		}
		else if(hasProjectile())
		{
			return "Projectile";
		}
		else if(hasConstruct())
		{
			//return construct.getClass().toString();
			return String.valueOf(construct.lv);
		}
		
		return "Empty";
	}
}
