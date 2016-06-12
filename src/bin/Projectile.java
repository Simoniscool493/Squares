package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	Entity source;
	
	Projectile(Entity s,Location l,int a,int li,Color c)
	{
		super(l,c);
		align = a;
		life = li;
		source = s;
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
			g2.fillRect((width*loc.x)+2,(height*loc.y)+height/2-1,width-2,3);
		}
		else//vertical
		{
			g2.fillRect((width*loc.x)+width/2-1, (height*loc.y)+2,3,height-2);
		}
	}
		
	void bump(GridPoint g)
	{
		kill();
		
		if(g.hasWall())
		{
			g.wall.damage(1);
			((Player)source).addPoints(100);
		}
	}
	
	void kill()
	{
		DrawApplet.deadlist.add(this);
		Grid.grid[loc.x][loc.y].changed = true;	
	}
}

