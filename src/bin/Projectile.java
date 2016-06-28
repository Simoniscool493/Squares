package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	Player source;
	
	Projectile(Player p,GridPoint g,int a,int li,Color c)
	{
		loc = g;
		loc.addProjectile(this);
		color = c;
		align = a;
		life = li;
		source = p;
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
	
	void move(int Xoffs,int Yoffs)
	{
		loc.refresh();

		int newX = loc.x+Xoffs;
		int newY = loc.y+Yoffs;
		
		if( newX >= gridWidth || newY >= gridHeight || newX<0 || newY<0 )
		//if out of bounds
		{
			die();
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
			g2.fillRect((int)(width*loc.x)+2,(int)(height*loc.y)+(int)height/2-1,(int)width-2,3);
		}
		else//vertical
		{
			g2.fillRect((int)(width*loc.x)+(int)width/2-1,(int)(height*loc.y)+2,3,(int)height-2);
		}
	}
		
	void bump(GridPoint g)
	{
		die();
		
		if(g.hasWall())
		{
			g.wall.damage(source,source.lv);
			((Player)source).addPoints(1);
		}
	}
	
	void die()
	{
		DrawApplet.deadlist.add(this);
		loc.removeProjectile();
	}
}

