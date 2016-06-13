package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu 
{
	int menuHeight = (int)U.drawHeight;
	int menuWidth = (int)U.menuWidth;
	int oWidth = (int)U.drawWidth;
	
	Player p;
	
	static boolean dataChanged = true;
	
	Color background = Color.darkGray;
	Color textColor = Color.white;
	
	Menu(Player player)
	{
		p = player;
	}
	
	void init(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight);	
	}
	
	void render(Graphics2D g2)
	{
		if(dataChanged)
		{
			g2.setColor(background);
			g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight);	
			g2.setColor(textColor);
			g2.drawString("Level:    " + String.valueOf(p.lv),oWidth+menuWidth*0.2f,menuHeight-menuHeight*0.1f);
			g2.drawString("Points:    " + String.valueOf(p.points),oWidth+menuWidth*0.2f,menuHeight-menuHeight*0.2f);
			dataChanged = false;
		}
	}	
}
