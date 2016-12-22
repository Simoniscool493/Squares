package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class PauseMenu 
{
	int width =(int)(U.drawWidth+U.menuWidth);
	int height = (int)(U.drawHeight);
	Color background = Color.white;
	Color lines  = Color.black;

	
	int numItems = 5;
	int itemHeight = height/numItems;
	
	boolean changed = true;
	
	PauseMenu()
	{
		
	}
	
	void mouseInput(int x,int y)
	{
		System.out.println((int)(y/itemHeight));
	}
	
	void render(Graphics2D g2)
	{
		if(changed)
		{
			System.out.println("Menu drawn");
			g2.setColor(background);
			g2.fillRect(0, 0, width, height);
			
			g2.setColor(lines);
			for(int i=0;i<numItems;i++)
			{
				g2.drawLine(0, (i+1)*itemHeight, width, (i+1)*itemHeight);
			}
			
			
			changed = false;
		}
	}
}
