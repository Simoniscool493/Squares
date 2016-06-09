package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	Entity source;
	
	Projectile(Entity s,int xpos,int ypos,int a,int l,Color c)
	{
		super(xpos,ypos,c);
		align = a;
		life = l;
		source = s;
		color = c;
	}
	
	void update()
	{
		if(align==0) //up
		{
			move(0,-1);
		}
		else if(align==2) //down
		{
			move(0,1);
		}
		else if(align==3) //left
		{
			move(-1,0);
		}
		else if(align==1) //right
		{
			move(1,0);
		}
		
		life--;
		
		if(life==0)
		{
			kill();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
		
		if(align == 1||align == 3)//horizontal
		{
			g2.fillRect((width*x)+2,(height*y)+height/2-1,width-2,3);
		}
		else//vertical
		{
			g2.fillRect((width*x)+width/2-1, (height*y)+2,3,height-2);
		}
	}
		
	void bump(GridPoint g)
	{
		kill();
		
		if(g.hasWall())
		{
			Grid.removeWall(g.x,g.y);
			((Player)source).points+=100;
		}
	}
	
	void kill()
	{
		DrawApplet.deadlist.add(this);
		Grid.grid[x][y].changed = true;	
	}
}

