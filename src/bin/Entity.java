package bin;

import java.awt.Color;
import java.awt.Graphics2D;

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
	int buildCost = 1;
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
			//System.out.println("Out Of Bounds " + (x+Xoffs) + " " + (y+Yoffs));
		}
		else
		{
			GridPoint newG = Grid.getPoint(newX,newY);
						
			if((newG.hasWall()||newG.hasConstruct())&&clipping)
			{
				bump(newG);
			}
			else
			{
				loc.contents.remove(this);
				newG.contents.add(this);

				newG.refresh();
				
				loc = newG;
			}
		}
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
