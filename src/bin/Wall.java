package bin;

import java.awt.Color;

public class Wall extends Entity
{
	Wall () {}
	
	Wall(int xpos,int ypos,Color c)
	{
		x = xpos;
		y = ypos;
		color = c;
		Grid.addWall(this, xpos, ypos);
	}
}
