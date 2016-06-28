package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Menu 
{
	int menuHeight = (int)U.drawHeight;
	int menuWidth = (int)U.menuWidth;
	int oWidth = (int)U.drawWidth;
	int menuComponentHeight = 30;
	
	int expBarX = (int)(oWidth+(menuWidth*0.2));
	int expBarY = (int)(menuWidth*0.2);
	int energyBarX = (int)(oWidth+(menuWidth*0.2));
	int energyBarY = (int)(menuWidth*0.4);
	int buildBarX = (int)(oWidth+(menuWidth*0.2));
	int buildBarY = (int)(menuWidth*0.6);
	float levelTextX = oWidth+menuWidth*0.2f;
	float levelTextY = menuHeight-menuHeight*0.1f;
	float pointsTextX = oWidth+menuWidth*0.2f;
	float pointsTextY = menuHeight-menuHeight*0.2f;
	float modeTextX = oWidth+menuWidth*0.2f;
	float modeTextY = menuHeight-menuHeight*0.3f;
	
	Player p;
	
	static boolean pointsChanged = true;
	static boolean levelChanged = true;
	static boolean modeChanged = true;
	static boolean buildChanged = true;

	Color background = U.menu;
	Color textColor = U.menuText;
	
	Menu(Player player)
	{
		p = player;
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
			drawExpBar(g2);	
			pointsChanged = false;
		}
		if(buildChanged)
		{
			drawBuildBar(g2);
			buildChanged = false;
		}

		if(modeChanged)
		{
			drawModeText(g2);
			modeChanged = false;
		}
		
		drawEnergyBar(g2);
	}	
	
	void drawExpBar(Graphics2D g2)
	{		
		g2.setColor(Color.black);
		g2.fillRect(expBarX,expBarY,(int)(menuWidth*0.6), menuComponentHeight);

		g2.setColor(Color.yellow);
		g2.fillRect(expBarX,expBarY,mapExp(), menuComponentHeight);
	}
	
	int mapExp()
	{
		return (int)((1-(p.toNextLv/(float)(p.toNextLvReq)))*menuWidth*0.6);
	}

	void drawEnergyBar(Graphics2D g2)
	{		
		g2.setColor(Color.black);
		g2.fillRect(energyBarX,energyBarY, (int)(menuWidth*0.6), menuComponentHeight);

		g2.setColor(Color.blue);
		g2.fillRect(energyBarX,energyBarY,mapEnergy(), menuComponentHeight);
	}
	
	int mapEnergy()
	{
		return (int)(((float)p.energy/((float)p.maxEnergy))*menuWidth*0.6f);
	}
	
	void drawBuildBar(Graphics2D g2)
	{		
		g2.setColor(Color.black);
		g2.fillRect(buildBarX,buildBarY, (int)(menuWidth*0.6), menuComponentHeight);

		g2.setColor(Color.green);
		g2.fillRect(buildBarX,buildBarY,mapBuild(), menuComponentHeight);
	}
	
	int mapBuild()
	{
		return (int)(((float)p.build/((float)p.maxBuild))*menuWidth*0.6f);
	}
	
	void drawLevelText(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect((int)levelTextX,(int)levelTextY-menuComponentHeight, menuWidth/2, menuComponentHeight);
		g2.setColor(textColor);
		g2.drawString("Level:    " + String.valueOf(p.lv),levelTextX,levelTextY);
	}
	
	void drawPointsText(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect((int)pointsTextX,(int)pointsTextY-menuComponentHeight, (int)(menuWidth/1.5), menuComponentHeight);
		g2.setColor(textColor);
		g2.drawString("Points:    " + String.valueOf(p.points),pointsTextX,pointsTextY);
	}
	
	void drawModeText(Graphics2D g2)
	{
		g2.setColor(background);
		g2.fillRect((int)modeTextX,(int)modeTextY-menuComponentHeight, (int)(menuWidth/1.5), menuComponentHeight);
		g2.setColor(textColor);
		g2.drawString("Build:    " + String.valueOf(p.buildMode),modeTextX,modeTextY);
	}
	
	void refresh(Graphics2D g2)
	{
		init(g2);
		pointsChanged = true;
		levelChanged = true;
		modeChanged = true;
		buildChanged = true;
	}
}
