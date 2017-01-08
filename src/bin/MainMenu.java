package bin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

//the main menu of the game. ideally it will be able to allow users to connect to
//servers and start games from them, host games, and play offline single player games.
//it'll also have an options menu and all that.
public class MainMenu 
{
	int numRows = 3;
	String [] rowNames = { "Local Game","Host Game","Join Game" };
	
	static Font font = new Font("TimesRoman", Font.PLAIN, 50);

	int width =(int)(U.drawWidth+U.menuWidth);
	int height = (int)(U.drawHeight);
	
	int incHeight = (int)(U.drawHeight/numRows);
	
	DrawApp parent;
	boolean changed = true;
	
	Color background = Color.gray;
	Color lines = Color.black;

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

			g2.setColor(lines);
			g2.setFont(font);
			
			for(int i=0;i<numRows;i++)
			{
				g2.drawLine(0, incHeight*i, width, incHeight*i);
				g2.drawString(rowNames[i], width/2, (incHeight*i)+incHeight/2);
			}
			
			changed = false;
		}
	}
	
	void mouseInput(int x,int y)
	{
		int selection = y/incHeight;
		
		if(selection==0)
		{
			parent.startLocalGame();
		}
		else if(selection==1)
		{
			parent.startServerGame();
		}
		else if(selection==2)
		{
			parent.startClientGame();;
		}
	}
}
