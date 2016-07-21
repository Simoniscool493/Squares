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
	
	int healthCost = 5;
	int rateCost = 8;
	int powerCost = 10;
	int lifeCost = 3;
	
	Image plus;
	Image plusten;
	Image one;
	Image two;
	Image three;
	
	Player p;
	Menu m;
	//ConstructedEntity c;
	
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
		
		plus = loadImage("plus.bmp");
		plusten = loadImage("plusten.bmp");
		one = loadImage("1.bmp");
		two = loadImage("2.bmp");
		three = loadImage("3.bmp");
	}
	
	Image loadImage(String s)
	{
		Image i = null;
		try { i = ImageIO.read(new File("data/"+ s)); }catch(IOException e) { System.out.println(s + " not found");}
		return i;
	}
	
	void render(Graphics2D g2)
	{
		if(p.buildMode&&p.front().hasConstruct())
		{
			ConstructedEntity c = p.front().construct;
			
			drawTitleBox(g2,Color.blue,c.dislplayName + " Lv " + c.lv + "   Cost: " + c.buildCost,0);
			drawMenuBox(g2,Color.black,"Health: " + c.hp,1);
			drawMenuBox(g2,Color.gray,"Rate: 1/" + c.rate,2);
			drawMenuBox(g2,Color.red,"Damage: " + c.power,3);
			drawMenuBox(g2,Color.orange,"Life: " + c.life,4);

			drawUpgrades(g2);
		}
		else
		{
			g2.setColor(background);
			g2.fillRect(x,y,componentWidth,componentHeight*(numComponents+1));
		}
	}
	
	void drawUpgrades(Graphics2D g2)
	{
		g2.setColor(Color.black);
		g2.drawImage(one,x,y+(componentHeight*numComponents),componentHeight,componentHeight,null);
		g2.drawImage(two,x+componentHeight,y+(componentHeight*numComponents),componentHeight,componentHeight,null);
		g2.drawImage(three,x+(componentHeight*2),y+(componentHeight*numComponents),componentHeight,componentHeight,null);		
	}
	
	void drawMenuBox(Graphics2D g2,Color c,String s,int order)
	{
		g2.setColor(c);
		g2.fillRect(x,y+(componentHeight*order),componentWidth,componentHeight);
		g2.setColor(textColor);
		g2.drawString(s,textAlign,(int)(y+textYOffset)+(componentHeight*order));
		g2.drawImage(plus,plusStartDistance,y+(componentHeight*order),componentHeight,componentHeight,null);
		g2.drawImage(plusten,plusStartDistance-componentHeight,y+(componentHeight*order),componentHeight,componentHeight,null);

	}
	
	void drawTitleBox(Graphics2D g2,Color c,String s,int order)
	{
		g2.setColor(c);
		g2.fillRect(x,y+(componentHeight*order),componentWidth,componentHeight);
		g2.setColor(textColor);
		g2.drawString(s,textAlign,(int)(y+textYOffset)+(componentHeight*order));
	}
	
	void mouse(int x,int y)
	{
		if(y>this.y)
		{
			ConstructedEntity c = p.front().construct;

			int num = (y-this.y)/componentHeight;

			if(num==numComponents)
			{
				int num2 = (x-this.x)/componentHeight;
				if(c!=null)
				{
					c.upgrades[num2] = true;	
				}
			}
			else if(c!=null)
			{
				if(x>plusStartDistance)
				{
					numericUpgrade(num,1,c);
				}
				else if(x>(plusStartDistance-componentWidth))
				{
					numericUpgrade(num,10,c);
				}
			}
		}
	}

	void numericUpgrade(int n,int count,ConstructedEntity c)
	{
		int z = count;
		
		while(z!=0)
		{
			if(n==1&&p.takeBuild(healthCost))
			{
				c.hp++;
				c.buildCost+=healthCost;
			}
			else if(n==2&&p.takeBuild(rateCost))
			{
				c.rate--;
				c.buildCost+=rateCost;
			}
			else if(n==3&&p.takeBuild(powerCost))
			{
				c.power++;
				c.buildCost+=powerCost;
			}
			else if(n==4&&p.takeBuild(lifeCost))
			{
				c.life++;
				c.buildCost+=lifeCost;
			}
			
			z--;
		}
		
		Menu.selectedChanged = true;
	}
}
