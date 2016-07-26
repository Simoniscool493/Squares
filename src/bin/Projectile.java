package bin;

import java.awt.Graphics2D;

public class Projectile extends Entity
{
	int life;
	int power;
	boolean twisting = false;
	Player source;
	
	Projectile(Player p)
	{
		source = p;
		loc = p.loc;
		loc.addProjectile(this);


		color = source.color;
		align = source.align;
		life = p.laserLife;
		power = source.lv;
		DrawApp.projectiles.add(this);
	}
	
	Projectile(ConstructedEntity e,int al)
	{
		source = e.source;
		loc = e.loc;
		loc.addProjectile(this);
		
		color = source.color;
		align = al;
		life = e.life;
		power = e.power;
		DrawApp.projectiles.add(this);
		
		if(e.upgrades[1]>0)
		{
			twisting = true;
		}
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
		
		if(twisting)
		{
			if((Math.random() * 3)>2)
			{
				align = (int)(Math.random() * 4);
			}
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
			int residualHp = g.wall.hp;
			boolean killed = g.wall.damage(source,power);
			
			if(!killed)
			{
				((Player)source).addPoints(power);
				System.out.println(power);
			}
			else
			{
				((Player)source).addPoints(residualHp);
				System.out.println(residualHp);
			}
		}
	}
	
	void die()
	{
		DrawApp.deadlist.add(this);
		loc.removeProjectile(this);
	}
}

