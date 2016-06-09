package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu 
{
	int totalHeight = (int)U.drawHeight;
	int menuHeight = 100;
	int menuWidth = 150;
	
	Color background = Color.black;
	Color textColor = Color.white;
	
	Menu()
	{
		
	}
	
	void render(Graphics2D g2,Player p)
	{
		g2.setColor(background);
		g2.fillRect(0, totalHeight-menuHeight, menuWidth, menuHeight);	
		g2.setColor(textColor);
		g2.drawString(String.valueOf(p.points),menuWidth*0.2f,totalHeight-menuHeight*0.2f);
	}
	
}
