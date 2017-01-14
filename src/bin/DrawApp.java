package bin;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JApplet;

//the drawApplet that contains the game. menus and games are run from here,
//but the applet only handles the graphics and i/o for the game - all the
//actual game programming goes in the Game class.
public class DrawApp extends JApplet
{
	static DrawApp thisApp;
	
	MainMenu mainMenu = new MainMenu(this);
	
	static boolean inGame = false;

	static Font font = new Font("TimesRoman", Font.PLAIN, 25);

	static boolean refreshScreenFlag = false;
	
	public void init()
	{		
		thisApp = this;
		
		this.setFont(font);
		
		addMouseListener(new MouseAdapter() 
        {
            public void mouseReleased(MouseEvent e)
            {
            	mouseInput(e.getX(),e.getY());
            }
        }
        );

		if(U.zoom)
		{
			GridPoint.width = (U.zoomIncWidth);
			GridPoint.height = (U.zoomIncHeight);
			Entity.width = (U.zoomIncWidth);
			Entity.height = (U.zoomIncHeight);
		}
	}
	
	public void enterGame()
	{
		refreshScreenFlag = true;
		inGame = true;
	}
	
	public void startLocalGame()
	{
		Game.currentGame = new Game(this,Game.IS_LOCAL);
		Game.currentGame.initialize();
		enterGame();
	}
	
	public void startClientGame()
	{
		System.out.println("Attempting to establish connection to " + Client.ip);
		Client.connect();
		Game.currentGame = Client.getGame();
		Game.currentGame.initializeClient();
		enterGame();
		Client.listenForInput();
		
		System.out.println("Started game with client id " + Game.currentGame.clientId);
	}
	
	public void startServerGame()
	{
		Game.currentGame = new Game(this,Game.IS_HOSTED);
		Game.currentGame.initialize();
		
		Server.hostGame();		
		enterGame();
		
		System.out.println("Game hosted");
	}

	public void paint(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;
        
		if(inGame)
		{
	        if(!Game.currentGame.isPaused)
	        {
	            if(refreshScreenFlag)
	            {
	            	System.out.println("Calling refreshScreen");
	            	refreshScreen(g2);
	            	refreshScreenFlag = false;
	            }
	            	            
	            if(U.zoom)
	            {
	            	ZoomGrid.render(g2);
	            }
	            else
	            {
	            	renderAllChangedTiles(g2);
	            }
	            
	            if(!Game.currentGame.isHosted)
	            {
	            	Game.currentGame.sideMenu.render(g2);
	            }
	
	        }
	        else
	        {
	        	Game.currentGame.pauseMenu.render(g2);
	        }
		}
		else
		{
			mainMenu.render(g2);
		}
	}
	
	public void renderAllChangedTiles(Graphics2D g2)
	{			
		for(GridPoint g: Game.currentGame.changed)
		{
			g.render(g2);
		}
				
		Game.currentGame.changed.clear();
	}
	
	public static void keyInput(int n)
	{	
		if(inGame)
		{
			Game.currentGame.keyInput(n);
		}
	}
	
	public void mouseInput(int x,int y)
	{	
		if(!inGame)
		{
			mainMenu.mouseInput(x,y);
		}
		else
		{
			Game.currentGame.mouseInput(x,y);
		}
	}
	
	public void refreshScreen(Graphics2D g2)
	{
		if(inGame)
		{
			Game.currentGame.sideMenu.refresh(g2);
			Game.currentGame.grid.refresh(g2);
		}
	}
}
