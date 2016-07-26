package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class PauseMenu 
{
	int width =(int)(U.drawWidth+U.menuWidth);
	int height = (int)(U.drawHeight);
	Color background = Color.white;
	
	PauseMenu()
	{
		
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(0, 0, width, height);
	}
}
