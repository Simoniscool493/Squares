package bin;

import java.awt.Color;
import java.awt.Graphics2D;

//the main menu of the game. ideally it will be able to allow users to connect to
//servers and start games from them, host games, and play offline single player games.
//it'll also have an options menu and all that.
public class MainMenu 
{
	int width =(int)(U.drawWidth+U.menuWidth);
	int height = (int)(U.drawHeight);

	DrawApp parent;
	boolean changed = true;
	
	Color background = Color.yellow;
	
	MainMenu(DrawApp p)
	{
		parent = p;
	}
	
	void render(Graphics2D g2)
	{
		
		if(changed)
		{
			g2.setColor(background);
			g2.fillRect(0,0,width,height);
			changed = false;
		}
	}
	
	void mouseInput(int x,int y)
	{
		parent.startGame();
	}
}
