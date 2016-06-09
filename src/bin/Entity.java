package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity 
{
	static int width = (int)U.incWidth;
	static int height = (int)U.incHeight;
	static float gridWidth = U.gridWidth;
	static float gridHeight = U.gridHeight;
	
	int x;
	int y;
	int align;
	boolean clipping = true;
	Color color = Color.black;
	
	Entity(){}
	
	Entity(int xpos,int ypos)
	{
		Grid.addEntity(this,xpos,ypos);
		x = xpos;
		y = ypos;
	}
	
	Entity(int xpos,int ypos,Color c)
	{
		this(xpos,ypos);
		color = c;
	}
		
	void render(Graphics2D g2)
	{		
		g2.setColor(color);
        g2.fillRect((width*x)+1,(height*y)+1,width-1,height-1);
	}
	
	void move(int Xoffs,int Yoffs)
	{
		Grid.refresh(x,y);

		int newX = x+Xoffs;
		int newY = y+Yoffs;
		
		if(newX >= gridWidth || newY >= gridHeight ||newX<0||newY<0)
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
			GridPoint oldG = Grid.grid[x][y];
			GridPoint newG = Grid.grid[newX][newY];
			
			if(!newG.hasWall()||!clipping)
			{
				oldG.contents.remove(this);
				newG.contents.add(this);

				Grid.refresh(newX,newY);
				
				x+=Xoffs;
				y+=Yoffs;	
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
		Grid.removeEntity(this);
	}
	
	void placeWall()
	{
		new Wall(x,y,color);
	}
	
	public String toString()
	{
		return "entity";
	}
}
