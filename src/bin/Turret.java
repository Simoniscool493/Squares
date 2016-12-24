package bin;

import java.awt.Graphics2D;

public class Turret extends ConstructedEntity 
{
	private static int defaultCost = 20;
	private int buildCost = 20;

	Turret(GridPoint g,Player p)
	{
		super(g,p);
		setHp(5);
		dislplayName = "Turret";
		setPower(1);
		setLife(10);
	}
	
	int getCost()
	{
		return buildCost;
	}

	void setStats()
	{
		setAlign(getSource().getAlign());
		setLoc(getSource().front());
		setColor(getSource().getColor());
		setLv(getSource().getLv());
		
		setUpgrades(new int[4]);
		
		for(int i = 0;i<getUpgrades().length;i++)
		{
			getUpgrades()[i] = 0;
		}
	}
	
	void render(Graphics2D g2)
	{
		int curWidth = ((int)(width*getLoc().getX()));
		int curHeight = ((int)(height*getLoc().getY()));

		g2.setColor(getColor());
        //g2.drawRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        g2.drawRect((curWidth)+5,(curHeight)+5,((int)(width))-10,((int)height)-10);
        
        if(getAlign() == 0)//up
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+1,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+1,5,5);
        }
        else if(getAlign() == 1)//right
        {
        	//g2.fillRect((width*loc.x)+width-5,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+(int)width-5,(curHeight)+(int)height/2-2,5,5);
        }
        else if(getAlign() == 2)//down
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+height-5,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+(int)height-5,5,5);
        }
        else if(getAlign() == 3)//left
        {
        	//g2.fillRect((width*loc.x)+1,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+1,(curHeight)+(int)height/2-2,5,5);
        }
	}
	
	void update()
	{
		setTimer(getTimer()+1);
		
		if(getTimer() >= getRate())
		{
			new Projectile(this,getAlign());
			setTimer(0);
			
			if(getUpgrades()[0]==1)
			{
				setAlign((getAlign()+1)%4);
				getLoc().refresh();
			}
			else if(getUpgrades()[0]>1)
			{
				new Projectile(this,(getAlign()+1)%4);
				new Projectile(this,(getAlign()+2)%4);
				new Projectile(this,(getAlign()+3)%4);
			}
		}
	}
	
	public static int getDefaultCost() {
		return defaultCost;
	}

	public static void setDefaultCost(int defaultCost) {
		Turret.defaultCost = defaultCost;
	}

	public int getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(int buildCost) {
		this.buildCost = buildCost;
	}
}
