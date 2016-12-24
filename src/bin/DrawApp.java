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
	MainMenu mainMenu = new MainMenu(this);
	
	static boolean inGame = false;
	static Game currentGame;

	static Font font = new Font("TimesRoman", Font.PLAIN, 25);

	static boolean refreshScreenFlag = false;
	
	public void init()
	{		
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
	
	public void startGame()
	{
		currentGame = new Game(this);
		currentGame.initialize();
		refreshScreenFlag = true;
		inGame = true;
	}
	
	public void hostGame()
	{
		//currentGame = new Game(this);
		//currentGame.initialize();
		//refreshScreenFlag = true;
		//inGame = true;
		
		Thread t = new Thread(){
			
			@Override
			public void run()
			{
				Network.listenForPlayers(currentGame);
			}
		};
		
		t.start();
		
		System.out.println("Game hosted");
	}
	
	public void connectToGame()
	{
		System.out.println(Network.getTestMessage());
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
	            	            
	            if(U.zoom)
	            {
	            	ZoomGrid.render(g2);
	            }
	            else
	            {
	            	renderAllChangedTiles(g2);
	            }
	            
	            currentGame.sideMenu.render(g2);
	
	        }
	        else
	        {
	        	currentGame.pauseMenu.render(g2);
	        }
		}
		else
		{
			mainMenu.render(g2);
		}
	}
	
	public void renderAllChangedTiles(Graphics2D g2)
	{			
		for(GridPoint g: currentGame.changed)
		{
			g.render(g2);
		}
				
		currentGame.changed.clear();
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
		if(!inGame)
		{
			mainMenu.mouseInput(x,y);
		}
		else
		{
			currentGame.mouseInput(x,y);
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
