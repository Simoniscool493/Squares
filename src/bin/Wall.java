package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Entity
{
	static Color defaultColor = Color.black;
	
	Player attacker;
	int buildCost = 1;
	
	Wall () {}
	
	Wall(GridPoint g,int level)
	{
		loc = g;
		loc.addWall(this);

		color = defaultColor;
		lv = level;
		hp = lv*2;
		
		loc.wallOn();
	}
	
	Wall(GridPoint g,Color c,int level)
	{
		this(g,level);
		color = c;	
	}
	
	void die()
	{
		loc.removeWall();
	}
	
	void damage(Player p,int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			p.kill(this);
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
        g2.fillRect((width*loc.x)+1,(height*loc.y)+1,width-1,height-1);
	}
}
