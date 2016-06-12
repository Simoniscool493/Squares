package bin;

import java.awt.Color;

public class Wall extends Entity
{
	Player attacker;
	
	Wall () {}
	
	Wall(Location l,Color c,int level)
	{
		loc = l.copy();
		color = c;
		Grid.addWall(this,loc);
		lv = level;
		hp = lv*2;
	}
	
	void kill()
	{
		Grid.removeWall(loc);
	}
	
	void damage(int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			kill();
		}
	}
	
}
