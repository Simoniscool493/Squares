package bin;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UpgradeMenu
{	
	int numComponents = 5;
	int x;
	int y;
	int textAlign;
	int textYOffset;
	int componentWidth;
	int componentHeight;
	int plusStartDistance;
	Color textColor;
	Color background;
	
	Image plus;
	
	Player p;
	Menu m;
	ConstructedEntity c;
	
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
		plusStartDistance = x+componentWidth-componentHeight;
		
		try { plus = ImageIO.read(new File("data/plus.bmp")); }catch(IOException e) { System.out.println("Plus Image not found");}
	}
	
	void render(Graphics2D g2)
	{
		if(p.buildMode&&p.front().hasConstruct())
		{
			c = p.front().construct;
			
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
		g2.drawImage(plus,plusStartDistance,y+(componentHeight*order),componentHeight,componentHeight,null);
	}
	
	void mouse(int x,int y)
	{
		if(x>plusStartDistance&&y>this.y)
		{
			int num = (y-this.y)/componentHeight;
			if(c!=null)
			{
				upgrade(num);
			}
		}
	}
	
	void upgrade(int n)
	{
		if(n==1)
		{
			c.hp++;
		}
		else if(n==2)
		{
			c.rate--;
		}
		else if(n==3)
		{
			c.power++;
		}
		else if(n==4)
		{
			c.life++;
		}
		
		Menu.selectedChanged = true;
	}
}
