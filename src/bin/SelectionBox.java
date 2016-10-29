package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class SelectionBox extends Entity
{
	private Player source;
	
	SelectionBox(Player p,GridPoint l,Color c)
	{
		setColor(c);
		setSource(p);
		setLoc(l);
		getLoc().addBox(this);
	}
	
	public void render(Graphics2D g2)
	{
		g2.setColor(getColor());
	    g2.drawRect(((int)(width*getLoc().getX()))+1,((int)(height*getLoc().getY()))+1,((int)width)-2,((int)height)-2);
	}
	
	public void die()
	{
		getLoc().removeBox();
		getSource().setBox(null);
	}

	public Player getSource() {
		return source;
	}

	public void setSource(Player source) {
		this.source = source;
	}
	
	
	
}
