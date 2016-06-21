package bin;

import java.awt.Graphics2D;

public class Player extends Entity
{
	int points;
	int toNextLvReq = 10;
	int toNextLv = 10;
	int energy = 150;
	int maxEnergy = 150;
	int laserCost = 10;
	int energyRegen = 1;
	
	boolean strafing = false;
	boolean buildMode = false;
	
	
	Player(GridPoint g)
	{
		super(g);
		align = 3;
		clipping = true;
		points = 0;
	}
	
	void move(int Xoffs,int Yoffs)
	{
		super.move(Xoffs, Yoffs);
		
		if(buildMode)
		{
			placeWall();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);

        g2.fillRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        
        if(align == 0)//up
        {
        	g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+1,5,5);
        }
        else if(align == 1)//right
        {
        	g2.fillRect((width*loc.x)+width-5,(height*loc.y)+height/2-2,5,5);
        }
        else if(align == 2)//down
        {
        	g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+height-5,5,5);
        }
        else if(align == 3)//left
        {
        	g2.fillRect((width*loc.x)+1,(height*loc.y)+height/2-2,5,5);
        }
	}
	
	void addPoints(int n)
	{
		points+=n;
		toNextLv-=n;
		
		while(toNextLv<1)
		{
			levelUp();
		}
		
		Menu.pointsChanged = true;
	}
	
	void levelUp()
	{
		lv++;
		if(lv%3==0)
		{
			energyRegen++;
		}
		toNextLvReq = (int)(toNextLvReq*1.2f);
		toNextLv+=toNextLvReq;
		maxEnergy+=10;
		Menu.levelChanged = true;
	}
	
	void laser()
	{
		if(!loc.hasProjectile()&&energy>laserCost-1)
		{
			new Projectile(this,loc,align,40,color);
			energy-=laserCost;
		}
	}
	
	void regen()
	{
		if(energy<maxEnergy)
		{
			energy+=energyRegen;
			if(energy>maxEnergy)
			{
				energy=maxEnergy;
			}
		}
	}
	
	void placeWall()
	{
		if(loc.front(align)!=null)
		{
			new Wall(loc.front(align),color,lv);
		}
	}
	
	void placeTurret()
	{
		if(loc.front(align)!=null)
		{
			if(!(loc.front(align).hasWall()))
			{
				new Turret(loc.front(align),this,align);
			}
		}
	}
}
