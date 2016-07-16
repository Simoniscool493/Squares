package bin;

import java.awt.Graphics2D;

public class Turret extends ConstructedEntity 
{
	static int defaultCost = 20;
	int buildCost = 20;
	
	Turret(GridPoint g,Player p)
	{
		super(g,p);
		hp = 5;
		dislplayName = "Turret";
		power = p.lv;
		System.out.println("power");
	}
	
	int getCost()
	{
		return buildCost;
	}

	void setStats()
	{
		align = source.align;
		loc = source.front();
		color = source.color;
		lv = source.lv;
		power = lv;
		life = 10;
	}
	
	void render(Graphics2D g2)
	{
		int curWidth = ((int)(width*loc.x));
		int curHeight = ((int)(height*loc.y));

		g2.setColor(color);
        //g2.drawRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        g2.drawRect((curWidth)+5,(curHeight)+5,((int)(width))-10,((int)height)-10);
        
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
	
	void update()
	{
		timer++;
		if(timer == rate)
		{
			new Projectile(this,loc,life,power);
			timer = 0;
		}
	}
}
