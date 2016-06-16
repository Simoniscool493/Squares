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
		
		g2.setColor(Color.BLACK);
		g2.fillRect((int)(oWidth+(menuWidth*0.2)),(int)(menuWidth+0.2), (int)(menuWidth*0.6), 30);
	}
	
	void render(Graphics2D g2)
	{
		if(dataChanged)
		{
			g2.setColor(background);
			g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight);	
		
			drawExpBar(g2);
			drawStats(g2);
			dataChanged = false;
		}
	}	
	
	void drawExpBar(Graphics2D g2)
	{		
		g2.setColor(Color.black);
		g2.fillRect((int)(oWidth+(menuWidth*0.2)),(int)(menuWidth*0.2), (int)(menuWidth*0.6), 30);

		g2.setColor(Color.yellow);
		g2.fillRect((int)(oWidth+(menuWidth*0.2)),(int)(menuWidth*0.2),mapExp(), 30);
	}
	
	int mapExp()
	{
		return (int)((1-(p.toNextLv/(p.lv*1000f)))*menuWidth*0.6);
	}
	
	void drawStats(Graphics2D g2)
	{
		g2.setColor(textColor);
		g2.drawString("Level:    " + String.valueOf(p.lv),oWidth+menuWidth*0.2f,menuHeight-menuHeight*0.1f);
		g2.drawString("Points:    " + String.valueOf(p.points),oWidth+menuWidth*0.2f,menuHeight-menuHeight*0.2f);
	}
}
