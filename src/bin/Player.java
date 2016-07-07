package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	boolean godmode = true;
	
	KeyMapping mapping;
	
	int points;
	
	int toNextLvReq = 10;
	int toNextLv = 10;
	
	int energy = 150;
	int maxEnergy = 150;
	
	int build = 50;
	int maxBuild = 100;

	int laserCost = 10;
	int energyRegen = 1;
	
	int claimCap = 300;
	
	Color claimColor = U.p1cap;
	
	ConstructedEntity selected = new Turret(null,this,align);
	
	boolean buildMode = false;
	boolean turning = false;
	boolean active = false;
	boolean deleting = false;

	boolean movingUp;
	boolean movingDown;
	boolean movingLeft;
	boolean movingRight;
	boolean justPressed = false;
	
	SelectionBox box;
	
	Player(KeyMapping m,GridPoint g,Color c,Color ccap)
	{
		super(g);
		mapping = m;
		m.p = this;
		align = 3;
		clipping = true;
		points = 0;
		color = c;
		claimColor = ccap; 
		
		if(godmode)
		{
			laserCost = 0;
			build = 10000;
			maxBuild = 10000;
		}
	}
	
	void checkInput(int n)
	{
		mapping.input(n);
	}
	
	void checkReleased(int n)
	{
		mapping.released(n);
	}
	
	void update()
	{		
		checkMoving();
		
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
	
	void checkMoving()
	{
		if(movingUp)
		{
			move(0,-1);
		}
		else if(movingRight)
		{
			move(1,0);
		}
		else if(movingDown)
		{
			move(0,1);
		}
		else if(movingLeft)
		{
			move(-1,0);
		}
	}
	
	void move(int Xoffs,int Yoffs)
	{
		int x = loc.x;
		int y = loc.y;
		
		if(!turning)
		{
			super.move(Xoffs, Yoffs);
			
			if(U.zoom&&((x!=loc.x)||(y!=loc.y)))
			{
				ZoomGrid.move(Xoffs,Yoffs);
			}
		}
		else if(justPressed)
		{
			front().refresh();

			if(movingUp)
			{
				align = 0;
			}
			if(movingRight)
			{
				align = 1;
			}
			if(movingDown)
			{
				align = 2;
			}
			if(movingLeft)
			{
				align = 3;
			}
			
			loc.refresh();
			justPressed = false;
		}
		
		if(box!=null)
		{
			box.place(front());
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
  	}
	
	void delete()
	{
		if(front().hasWall()&&front().wall.source==this)
		{
			front().wall.die();
		}
		if(front().hasConstruct()&&front().construct.source==this)
		{
			addBuild(front().construct.getCost()/2);
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
		if((front().isEmpty())&&!(front().isNullPoint())&&takeBuild(1))
		{
			new Wall(front(),this);
		}
	}
	
	void placeTurret()
	{
		if((!front().isNullPoint())&&(front().isEmpty())&&takeBuild(selected.getCost()))
		{
			Turret t = (Turret)selected.clone();
			t.setStats();
			t.init();
			front().startClaim(this,claimCap/3);
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
			box = new SelectionBox(this,front(),Color.red);
		}
		else
		{
			loc.refresh();
			box.die();
			if(turning)
			{
				box = new SelectionBox(this,front(),Color.blue);
			}
		}
		
		buildMode = !buildMode;
		Menu.modeChanged = true;
	}
	
	void startTurning()
	{
		turning = true;
		loc.refresh();
		if(buildMode)
		{
			box.die();
		}
		
		box = new SelectionBox(this,front(),Color.blue);
	}
	
	void stopTurning()
	{
		turning = false;
		box.die();

		if(buildMode)
		{
			box = new SelectionBox(this,front(),Color.red);
		}
		else
		{
			front().refresh();
		}
	}
}
