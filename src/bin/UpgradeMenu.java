package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class UpgradeMenu 
{
	int numComponents = 3;
	
	int x;
	int y;
	int textAlign;
	int textYOffset;
	int componentWidth;
	int componentHeight;
	Color textColor;
	Color background;
	
	Player p;
	Menu m;
	
	UpgradeMenu(Menu me)
	{
		m = me;
		p = me.p;
		
		x  = (int)U.drawWidth;
		y  = (int)(m.menuHeight-m.menuHeight*0.75f);
		textAlign = (int)(x+m.menuWidth/8);
		textYOffset = (int)(m.menuComponentHeight*1.2);
		textColor = m.textColor;
		background = m.background;
		componentWidth = m.menuWidth;
		componentHeight = m.menuComponentHeight*2;
	}
	
	void render(Graphics2D g2)
	{
		if(p.buildMode)
		{
			drawSelectedText(g2);
			drawMenuBox(g2,Color.black,"Health",1);
			drawMenuBox(g2,Color.gray,"Rate",2);
		}
		else
		{
			g2.setColor(background);
			g2.fillRect(x,y,componentWidth,componentHeight*numComponents);
		}
	}
	
	void drawSelectedText(Graphics2D g2)
	{		
		String sel;
		
		if(p.box==null)
		{
			sel = "null";
		}
		else
		{
			sel = p.box.loc.getContentsName();
		}
				
		drawMenuBox(g2,Color.darkGray,sel,0);
	}
	
	void drawMenuBox(Graphics2D g2,Color c,String s,int order)
	{
		g2.setColor(c);
		g2.fillRect(x,y+(componentHeight*order),componentWidth,componentHeight);
		g2.setColor(textColor);
		g2.drawString(s,textAlign,(int)(y+textYOffset)+(componentHeight*order));
	}
}
