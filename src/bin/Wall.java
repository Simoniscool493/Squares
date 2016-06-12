package bin;

import java.awt.Color;

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
	
}
