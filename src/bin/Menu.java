package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu 
{
	int menuHeight = (int)U.drawHeight;
	int menuWidth = (int)U.menuWidth;
	int oWidth = (int)U.drawWidth;
	int menuComponentHeight = (int)(U.drawHeight/40);
	
	int barX = (int)(oWidth+(menuWidth*0.1));
	int barWidth = (int)(menuWidth*0.8);
	int expBarY = (int)(menuWidth*0.2);
	int energyBarY = (int)(menuWidth*0.4);
	int buildBarY = (int)(menuWidth*0.6);
	
	int textAlign = (int)(oWidth+menuWidth*0.2f);
	float levelTextY = menuHeight-menuHeight*0.1f;
	float pointsTextY = menuHeight-menuHeight*0.2f;
	float modeTextY = menuHeight-menuHeight*0.3f;
	
	Player p;
	UpgradeMenu u;
	
	static boolean pointsChanged = true;
	static boolean levelChanged = true;
	static boolean buildChanged = true;
	static boolean selectedChanged = true;

	Color background = U.menu;
	Color textColor = U.menuText;
	
	Menu(Player player)
	{
		p = player;
		u  = new UpgradeMenu(this);
	}
	
	void init(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(oWidth, menuHeight-menuHeight, menuWidth, menuHeight+1);	
	}
	
	void render(Graphics2D g2)
	{
		if(levelChanged)
		{
			drawLevelText(g2);
			levelChanged = false;
		}
		if(pointsChanged)
		{
			drawPointsText(g2);
			drawBar(g2,Color.yellow,expBarY,mapExp());
			pointsChanged = false;
		}
		if(buildChanged)
		{
			drawBar(g2,Color.green,buildBarY,mapBuild());
			buildChanged = false;
		}
		
		if(selectedChanged)
		{
			u.render(g2);
			selectedChanged = false;
		}
		
		drawBar(g2,Color.blue,energyBarY,mapEnergy());
	}	
	
	void drawBar(Graphics2D g2,Color c,int y,int map)
	{
		g2.setColor(Color.black);
		g2.fillRect(barX,y,barWidth, menuComponentHeight);

		g2.setColor(c);
		g2.fillRect(barX,y,map, menuComponentHeight);
	}
		

	int mapExp()
	{
		return (int)((1-(p.toNextLv/(float)(p.toNextLvReq)))*barWidth);
	}
	
	int mapEnergy()
	{
		return (int)(((float)p.energy/((float)p.maxEnergy))*barWidth);
	}
	
	int mapBuild()
	{
		return (int)(((float)p.build/((float)p.maxBuild))*barWidth);
	}
	
	void drawLevelText(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(textAlign,(int)levelTextY-menuComponentHeight, menuWidth/2, menuComponentHeight);
		g2.setColor(textColor);
		g2.drawString("Level:    " + String.valueOf(p.lv),textAlign,levelTextY);
	}
	
	void drawPointsText(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect(textAlign,(int)pointsTextY-menuComponentHeight, (int)(menuWidth/1.5), menuComponentHeight);
		g2.setColor(textColor);
		g2.drawString("Points:    " + String.valueOf(p.points),textAlign,pointsTextY);
	}
	
	void refresh(Graphics2D g2)
	{
		init(g2);
		pointsChanged = true;
		levelChanged = true;
		buildChanged = true;
		selectedChanged = true;
	}
}
