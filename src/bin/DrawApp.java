package bin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import javax.swing.JApplet;
import javax.swing.Timer;

public class DrawApp extends JApplet
{
	MainMenu mainMenu = new MainMenu(this);
	
	public static Font font = new Font("TimesRoman", Font.PLAIN, 25);

	public static Game currentGame;

	private static final long serialVersionUID = 1L;
	
	static boolean refreshScreenFlag = false;
	static boolean inGame = false;
	
	public void init()
	{		
		this.setFont(font);
		
		if(U.zoom)
		{
			GridPoint.width = (U.zoomIncWidth);
			GridPoint.height = (U.zoomIncHeight);
			Entity.width = (U.zoomIncWidth);
			Entity.height = (U.zoomIncHeight);
		}
	}
	
	public void startGame()
	{
		currentGame = new Game(this);
		currentGame.initialize();
		refreshScreenFlag = true;
		inGame = true;
	}

	public void paint(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;
        
		if(inGame)
		{
	        if(!currentGame.isPaused)
	        {
	    		
	            if(refreshScreenFlag)
	            {
	            	refreshScreen(g2);
	            	refreshScreenFlag = false;
	            }
	            
	            currentGame.update();
	            
	            if(U.zoom)
	            {
	            	zoomRender(g2);
	            }
	            else
	            {
	            	refreshAllChangedTiles(g2);
	            }
	            
	            currentGame.sideMenu.render(g2);
	
	        }
	        else
	        {
	        	currentGame.pauseMenu.render(g2);
	        }
		}
	}
	
	public void refreshAllChangedTiles(Graphics2D g2)
	{			
		for(GridPoint g: currentGame.changed)
		{
			g.render(g2);
		}
				
		currentGame.changed.clear();
	}
	
	public void zoomRender(Graphics2D g2)
	{			
		ZoomGrid.render(g2);
	}

	
	public static void keyInput(int n)
	{	
		if(inGame)
		{
			currentGame.keyInput(n);
		}
	}
	
	public void mouseInput(int x,int y)
	{	
		System.out.println("DrawApp mouseInput called");
		if(!inGame)
		{
			mainMenu.mouseInput(x,y);
		}
		else
		{
			currentGame.mouseInput(x,y);
		}
	}
	
	public void getKeyReleased(int n)
	{
		if(inGame)
		{
			currentGame.p1.checkReleased(n);
		}
	}
	
	public void refreshScreen(Graphics2D g2)
	{
		if(inGame)
		{
			currentGame.sideMenu.refresh(g2);
			Grid.refresh(g2);
		}
	}
	
	
	
}
