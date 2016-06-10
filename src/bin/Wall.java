package bin;

import java.awt.Color;

public class Wall extends Entity
{
	Wall () {}
	
	Wall(Location l,Color c)
	{
		loc = l.copy();
		color = c;
		Grid.addWall(this,loc);
	}
}
