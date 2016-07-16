package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UpgradeMenu
{	
	int numComponents = 5;
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
		if(p.buildMode&&p.front().hasConstruct())
		{
			ConstructedEntity c = p.front().construct;
			
			drawMenuBox(g2,Color.blue,c.dislplayName + " Lv " + c.lv,0);
			drawMenuBox(g2,Color.black,"Health: " + c.hp,1);
			drawMenuBox(g2,Color.gray,"Rate: 1/" + c.rate,2);
			drawMenuBox(g2,Color.red,"Damage: " + c.power,3);
			drawMenuBox(g2,Color.orange,"Life: " + c.life,4);

		}
		else
		{
			g2.setColor(background);
			g2.fillRect(x,y,componentWidth,componentHeight*numComponents);
		}
	}
	
	void drawMenuBox(Graphics2D g2,Color c,String s,int order)
	{
		g2.setColor(c);
		g2.fillRect(x,y+(componentHeight*order),componentWidth,componentHeight);
		g2.setColor(textColor);
		g2.drawString(s,textAlign,(int)(y+textYOffset)+(componentHeight*order));
	}
	
	void mouse(int x,int y)
	{
		if(x>this.x&&y>this.y)
		{
			int num = (y-this.y)/componentHeight;
			System.out.println(num);
		}
	}
}
