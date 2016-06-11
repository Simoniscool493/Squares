package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu 
{
	int menuHeight = (int)U.drawHeight;
	int menuWidth = (int)U.menuWidth;
	int oWidth = (int)U.drawWidth;
	
	static boolean pointsChanged = true;
	
	Color background = Color.darkGray;
	Color textColor = Color.white;
	
	Menu()
	{
		
	}
	
	void init(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight);	
	}
	
	void render(Graphics2D g2,Player p)
	{
		if(pointsChanged)
		{
			g2.setColor(background);
			g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight);	
			g2.setColor(textColor);
			g2.drawString(String.valueOf(p.points),oWidth+menuWidth*0.2f,menuHeight-menuHeight*0.2f);
			pointsChanged = false;
		}
	}	
}
