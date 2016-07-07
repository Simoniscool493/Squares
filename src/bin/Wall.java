package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Entity
{
	static Color defaultColor = U.wallColor;
	static Color halfColor = U.wallHalfColor;
	
	Player attacker;
	Player source;
	int buildCost = 1;
	int maxHp;
	
	Wall () {}
	
	Wall(GridPoint g,Player p)
	{
		DrawApplet.numWalls++;
		loc = g;
		loc.addWall(this);

		source = p;
		color = source.color;
		lv = source.lv;
		maxHp = hp = lv*2;
		loc.wallOn();
	}
	
	Wall(GridPoint g,int level)
	{
		DrawApplet.numWalls++;
		loc = g;
		loc.addWall(this);

		color = defaultColor;
		lv = level;
		maxHp = hp = lv*2;
		
		loc.wallOn();
	}
	
	Wall(GridPoint g,Color c,int level)
	{
		this(g,level);
		color = c;	
	}
	
	void die()
	{
		DrawApplet.numWalls--;
		loc.removeWall();
	}
	
	void damage(Player p,int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			p.kill(this);
		}
		else
		{			
			int r = color.getRed();
			int g = color.getGreen();
			int b = color.getBlue();
			
			r+=n*(200/maxHp);
			g+=n*(200/maxHp);
			b+=n*(200/maxHp);
			
			if(r>255) { r = 255; }
			if(g>255) { g = 255; }
			if(b>255) { b = 255; }
			
			color = new Color(r,g,b);
			
			loc.refresh();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
		if(U.showGrid)
		{
			g2.fillRect((int)(width*loc.x)+1,(int)(height*loc.y)+1,(int)width-1,(int)height-1);
        }
		else
		{
			g2.fillRect((int)(width*loc.x),(int)(height*loc.y),(int)width,(int)height);
		}
	}
}
