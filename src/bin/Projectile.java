package bin;

import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	int power;
	Player source;
	
	Projectile(Player p,GridPoint g,int li)
	{
		loc = g;
		loc.addProjectile(this);
		source = p;

		color = source.color;
		align = source.align;
		life = li;
		power = source.lv;
		DrawApplet.projectiles.add(this);
	}
	
	Projectile(ConstructedEntity e,GridPoint g,int li)
	{
		loc = g;
		loc.addProjectile(this);
		source = e.source;
		
		color = source.color;
		align = e.align;
		life = li;
		power = e.lv;
		DrawApplet.projectiles.add(this);
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
			die();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
		
		if(align == 1||align == 3)//horizontal
		{
			g2.fillRect((int)(width*loc.x)+2,(int)(height*loc.y)+(int)height/2-1,(int)width-2,3);
		}
		else//vertical
		{
			g2.fillRect((int)(width*loc.x)+(int)width/2-1,(int)(height*loc.y)+2,3,(int)height-2);
		}
	}
	
	void outOfBounds()
	{
		die();
	}
		
	void bump(GridPoint g)
	{
		die();
		
		if(g.hasWall())
		{
			g.wall.damage(source,power);
			((Player)source).addPoints(1);
		}
	}
	
	void die()
	{
		DrawApplet.deadlist.add(this);
		loc.removeProjectile();
	}
}

