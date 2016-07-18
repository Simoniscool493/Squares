package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity 
{
	static float width = (U.incWidth);
	static float height = (U.incHeight);
	//static float width = 0;
	//static float height = 0;

	static float gridWidth = U.gridWidth;
	static float gridHeight = U.gridHeight;
	
	GridPoint loc;
	int align;
	int lv = 1;
	int hp;
	int buildCost = 1;
	int type = -1;
	boolean clipping = true;
	Color color = Color.black;
	
	Entity(){}
	
	Entity(GridPoint g)
	{
		loc = g;
		loc.addEntity(this);
	}
	
	Entity(GridPoint g,Color c)
	{
		this(g);
		color = c;
	}
		
	void render(Graphics2D g2)
	{		
		g2.setColor(color);
        g2.fillRect((int)(width*loc.x)+1,(int)(height*loc.y)+1,((int)width)-1,((int)height)-1);
	}
	
	void update()
	{
		
	}
	
	void move(int Xoffs,int Yoffs)
	{
		loc.refresh();

		int newX = loc.x+Xoffs;
		int newY = loc.y+Yoffs;
		
		GridPoint newG = Grid.getPoint(newX,newY);

		if(newG.isNullPoint())
		//goes out of bounds
		{
			outOfBounds();
		}
		else
		{			
			if(U.zoom)
			{
				loc.zoomMoved = true;
				newG.zoomMoved = true;
			}
						
			if((newG.hasWall()||newG.hasConstruct())&&clipping)
			//stops
			{
				bump(newG);
			}
			else
			//keeps going
			{
				loc.removeEntity(this);
				newG.addEntity(this);

				newG.refresh();
				
				loc = newG;
				
			}
		}
	}
	
	void outOfBounds()
	{
		//System.out.println("Out Of Bounds " + (x+Xoffs) + " " + (y+Yoffs));
	}
	
	void place(GridPoint g)
	{
		loc.removeEntity(this);
		loc = g;
		g.addEntity(this);
	}
	
	void bump(GridPoint g)
	{
		
	}
	
	void die()
	{
		loc.removeEntity(this);
	}
		
	void damage(int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			die();
		}
	}
	
	public String toString()
	{
		return "entity";
	}
	
	GridPoint front()
	{
		return loc.getFront(align);
	}

}
