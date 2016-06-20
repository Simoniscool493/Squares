package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Entity
{
	Player attacker;
	
	Wall () {}
	
	Wall(GridPoint g,Color c,int level)
	{
		loc = g;
		color = c;
		loc.addWall(this);
		lv = level;
		hp = lv*2;
	}
	
	void kill()
	{
		loc.removeWall();
	}
	
	void damage(int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			kill();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
        g2.fillRect((width*loc.x)+1,(height*loc.y)+1,width-1,height-1);
	}
}
