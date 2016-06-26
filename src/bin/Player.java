package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	int points;
	
	int toNextLvReq = 10;
	int toNextLv = 10;
	
	int energy = 150;
	int maxEnergy = 150;
	
	int build = 5000;
	int maxBuild = 5000;

	int laserCost = 10;
	int energyRegen = 1;
	
	int claimCap = 300;
	
	Color claimColor = new Color(255, 213, 214);
	
	boolean buildMode = false;
	boolean turning = false;
	boolean active = false;
	
	Player(GridPoint g)
	{
		super(g);
		align = 3;
		clipping = true;
		points = 0;
		color = Color.red;
	}
	
	void update()
	{		
		if(active)
		{
			if(buildMode)
			{
				placeWall();
			}
			else
			{
				laser();
			}
		}
		
		loc.takeControl(this);
	}
	
	void move(int Xoffs,int Yoffs)
	{
		if(!turning)
		{
	        if(buildMode)
	        {
	        	front().refresh();
	        }
	
			super.move(Xoffs, Yoffs);
		}
		else
		{
			front().refresh();
			loc.refresh();
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
       
        if(turning)
        {
        	front().drawSelectionBox(g2, Color.blue);
        }
        else if(buildMode)
        {
        	front().drawSelectionBox(g2,color);
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
	
	void addBuild(int n)
	{
		if(build+n>maxBuild)
		{
			build = maxBuild;
		}
		else
		{
			build+=n;
		}
		
		Menu.buildChanged = true;
	}
	
	boolean takeBuild(int n)
	{
		if(build-n<0)
		{
			return false;
		}
		
		build-=n;
		Menu.buildChanged = true;
		return true;
	}
	
	void levelUp()
	{
		lv++;
		if(lv%5==0)
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
		if((!front().hasWall())&&!(front().isNullPoint())&&takeBuild(1))
		{
			new Wall(front(),color,lv);
		}
	}
	
	void placeTurret()
	{
		if((!front().isNullPoint())&&takeBuild(20))
		{
			if(!(front().hasWall())&&!(front().hasConstruct()))
			{
				new Turret(front(),this,align);
				front().startClaim(this,claimCap/3);
			}
		}
	}
	
	void kill(Entity e)
	{
		addBuild(e.buildCost);
		e.die();
	}

	void toggleBuildMode()
	{
		if(!buildMode)
		{
			loc.refresh();
		}
		else
		{
			loc.refresh();
			front().refresh();
		}
		
		buildMode = !buildMode;
		Menu.modeChanged = true;
	}
	
	void startTurning()
	{
		turning = true;
		loc.refresh();
	}
	
	void stopTurning()
	{
		turning = false;
		if(buildMode)
		{
			loc.refresh();
		}
		else
		{
			front().refresh();
		}
	}
}
