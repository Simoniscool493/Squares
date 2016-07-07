package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class SelectionBox extends Entity
{
	Player source;
	
	SelectionBox(Player p,GridPoint l,Color c)
	{
		color = c;
		source = p;
		loc = l;
		loc.addBox(this);
	}
	
	public void render(Graphics2D g2)
	{
		g2.setColor(color);
	    g2.drawRect(((int)(width*loc.x))+1,((int)(height*loc.y))+1,((int)width)-2,((int)height)-2);
	}
	
	public void die()
	{
		loc.removeBox();
		source.box = null;
	}
}
