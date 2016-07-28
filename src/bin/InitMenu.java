package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class InitMenu 
{
	int width =(int)(U.drawWidth+U.menuWidth);
	int height = (int)(U.drawHeight);

	DrawApp parent;
	boolean start = false;
	boolean changed = true;
	
	Color background = Color.yellow;
	
	
	InitMenu(DrawApp p)
	{
		parent = p;
        parent.t.start();
	}
	
	void render(Graphics2D g2)
	{
		if(start)
		{
			start(g2);
		}
		
		if(changed)
		{
			g2.setColor(background);
			g2.fillRect(0,0,width,height);
			changed = false;
		}
	}
	
	void start(Graphics2D g2)
	{
		g2.setColor(Color.white);
		g2.fillRect(0,0,width,height);

    	parent.init(g2);
    	DrawApp.m.init(g2);
        DrawApp.started = true;
	}
	
	void mouse(int x,int y)
	{

		start = true;

	}
}
