package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;

public class Entity 
{
	static int width = (int)U.incWidth;
	static int height = (int)U.incHeight;
	static float gridWidth = U.gridWidth;
	static float gridHeight = U.gridHeight;
	
	GridPoint loc;
	int align;
	int lv = 1;
	int hp;
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
        g2.fillRect((width*loc.x)+1,(height*loc.y)+1,width-1,height-1);
	}
	
	void move(int Xoffs,int Yoffs)
	{
		loc.refresh();

		int newX = loc.x+Xoffs;
		int newY = loc.y+Yoffs;
		
		if( newX >= gridWidth || newY >= gridHeight || newX<0 || newY<0 )
		//if out of bounds
		{
			if(this instanceof Projectile)
			{
				kill();
			}
			//System.out.println("Out Of Bounds " + (x+Xoffs) + " " + (y+Yoffs));
		}
		else
		{
			GridPoint newG = Grid.getPoint(newX,newY);
			
			if(!newG.hasWall()||!clipping)
			{
				loc.contents.remove(this);
				newG.contents.add(this);

				newG.refresh();
				
				loc = newG;
			}
			else
			{
				bump(newG);
			}
		}
	}
	
	void bump(GridPoint g)
	{
		
	}
	
	void kill()
	{
		loc.removeEntity(this);
	}
	
	void placeWall()
	{
		new Wall(loc,color,lv);
	}
	
	void damage(int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			kill();
		}
	}
	
	public String toString()
	{
		return "entity";
	}
}
