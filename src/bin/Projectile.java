package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	Entity source;
	
	Projectile(Entity s,GridPoint g,int a,int li,Color c)
	{
		loc = g;
		loc.addProjectile(this);
		color = c;
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
	
	void move(int Xoffs,int Yoffs)
	{
		loc.refresh();

		int newX = loc.x+Xoffs;
		int newY = loc.y+Yoffs;
		
		if( newX >= gridWidth || newY >= gridHeight || newX<0 || newY<0 )
		//if out of bounds
		{
			kill();
			//System.out.println("Out Of Bounds " + (x+Xoffs) + " " + (y+Yoffs));
		}
		else
		{
			GridPoint newG = Grid.getPoint(newX,newY);
			
			if(!newG.hasWall()||!clipping)
			{
				loc.removeProjectile();
				newG.addProjectile(this);

				newG.refresh();
				
				loc = newG;
			}
			else
			{
				bump(newG);
			}
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
			g.wall.damage(source.lv);
			((Player)source).addPoints(100);
		}
	}
	
	void kill()
	{
		DrawApplet.projectiles.remove(this);
		loc.removeProjectile();
	}
}

