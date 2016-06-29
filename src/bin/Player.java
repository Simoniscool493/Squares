package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	boolean godmode = false;
	
	int points;
	
	int toNextLvReq = 10;
	int toNextLv = 10;
	
	int energy = 150;
	int maxEnergy = 150;
	
	int build = 50;
	int maxBuild = 100;

	int laserCost = 10;
	int energyRegen = 1;
	
	int claimCap = 1;
	
	Color claimColor = U.p1cap;
	
	boolean buildMode = false;
	boolean turning = false;
	boolean active = false;
	boolean deleting = false;
	
	Player(GridPoint g)
	{
		super(g);
		align = 3;
		clipping = true;
		points = 0;
		color = U.p1;
		
		if(godmode)
		{
			laserCost = 0;
			build = 10000;
			maxBuild = 10000;
		}
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
		else if(deleting)
		{
			delete();
		}
		
		//loc.takeControl(this);
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
		int curWidth = ((int)(width*loc.x));
		int curHeight = ((int)(height*loc.y));

		g2.setColor(color);
		
        //g2.fillRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        g2.fillRect((curWidth)+5,(curHeight)+5,((int)(width))-10,((int)height)-10);

        
        if(align == 0)//up
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+1,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+1,5,5);
        }
        else if(align == 1)//right
        {
        	//g2.fillRect((width*loc.x)+width-5,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+(int)width-5,(curHeight)+(int)height/2-2,5,5);
        }
        else if(align == 2)//down
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+height-5,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+(int)height-5,5,5);
        }
        else if(align == 3)//left
        {
        	//g2.fillRect((width*loc.x)+1,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+1,(curHeight)+(int)height/2-2,5,5);
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
	
	void delete()
	{
		if(front().hasWall()&&front().wall.source==this)
		{
			front().wall.die();
		}
		if(front().hasConstruct()&&front().construct.source==this)
		{
			addBuild(Turret.getCost()/2);
			front().construct.die();
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
			new Wall(front(),this);
		}
	}
	
	void placeTurret()
	{
		if((!front().isNullPoint())&&takeBuild(Turret.getCost()))
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
