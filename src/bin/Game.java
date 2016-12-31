package bin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Random;

import javax.swing.Timer;

public class Game implements ActionListener, Serializable
{
	public int timerCounter = 0;
	
	public static final boolean IS_HOSTED = true;
	public static final boolean IS_LOCAL = false;

	public KeyMapping clientPlayerKeyMapping;
	public Player clientPlayer;

	public Random r;
	
	int playerId;
	boolean isHosted = false;
	boolean isClient = false;
	
	Timer gameTimer = new Timer(50,this);	
	
	boolean isPaused = false;

	int maxWalls = 500;
	int numWalls = 0;
	
	public Grid grid;
	public ZoomGrid zoomGrid;
	
	public Menu sideMenu;
	public PauseMenu pauseMenu;
	
	public ArrayList<Player> players = new ArrayList<Player>();

	public LinkedHashSet<Projectile> projectiles = new LinkedHashSet<Projectile>();
	public LinkedHashSet<ConstructedEntity> constructs = new LinkedHashSet<ConstructedEntity>();
	public LinkedHashSet<Entity> deadlist = new LinkedHashSet<Entity>();
	public LinkedHashSet<GridPoint> changed = new LinkedHashSet<GridPoint>();
	public LinkedHashSet<GridPoint> activeBirthList = new LinkedHashSet<GridPoint>();
	public LinkedHashSet<GridPoint> activeSpots = new LinkedHashSet<GridPoint>();
	public LinkedHashSet<GridPoint> activeDeadList = new LinkedHashSet<GridPoint>();

	boolean started = false;

	Game(DrawApp p,boolean hosted)
	{
		isHosted = hosted;
	}
	
	public void initialize()
	{
		grid = new Grid();
		zoomGrid = new ZoomGrid(U.p1startX,U.p1startY);
		
		//up down left right turn fire build place delete
		if(!isHosted)
		{
			clientPlayerKeyMapping = new KeyMapping('W','S','A','D','U','H','K','I','J');
			clientPlayer = new Player(clientPlayerKeyMapping,grid.getPoint(U.p1startX,U.p1startY),U.p1,U.p1cap);
			players.add(clientPlayer);
		}

		r = new Random();
		
		sideMenu = new Menu(clientPlayer);
		pauseMenu = new PauseMenu();
        gameTimer.start();
	}
	
	public void actionPerformed(ActionEvent e)
	{		
        update();
        
        /*if(!isHosted)
        {
    		DrawApp.rp.repaint();
        }*/
		
        DrawApp.rp.repaint();
        
        //System.out.println(timerCounter);
		timerCounter++;
  	}
	
	public void update()
	{
		for(Player p:players)
		{
			p.regen();
	        p.update();
		}
		
		projectiles.removeAll(deadlist);
		constructs.removeAll(deadlist);
		deadlist.clear();
		
		activeSpots.removeAll(activeDeadList);
		activeDeadList.clear();

		activeSpots.addAll(activeBirthList);
		activeBirthList.clear();
				
		for(GridPoint g:activeSpots)
		{
			g.progress();
		}
		
		for(Projectile p:projectiles)
		{
			p.update();
		}
				
		for(ConstructedEntity c:constructs)
		{
			c.update();
		}		
		
        //spawn();  
	}
	
	public void spawn()
	{
		//if(r.nextInt()>200000000&&numWalls<maxWalls)
		if(r.nextInt()>200000000)
		{
			int w = (int)(r.nextInt(U.gridWidth));
			int h = (int)(r.nextInt(U.gridHeight));
			int lv = (int)(r.nextInt(5));
						
			GridPoint point = grid.getPoint(w,h);
			
			if(point.isEmpty())
			{
				new Wall(point,Color.black,lv);
			}
		}
	}

	public void foreignKeyInput(int key,int playerNumber)
	{
		players.get(playerNumber).keyInput(key);
	}
	
	public void keyInput(int key)
	{
		clientPlayer.keyInput(key);
		
		if(isClient)
		{
			Network.sendInput(key,playerId);
		}

		if(key=='1')
		{
			reset();
		}
		else if(key=='2')
		{
			grid.coverGrid(40,1);
		}
		else if(key=='3')
		{
			//p1.getLoc().startClaim(p1,1);
		}
		else if(key==' ')
		{
			togglePause();
		}
	}
	
	public void addPlayer()
	{
		clientPlayerKeyMapping = new KeyMapping('W','S','A','D','U','H','K','I','J');
		clientPlayer = new Player(clientPlayerKeyMapping,grid.getPoint(U.p1startX,U.p1startY),U.p1,U.p1cap);
		players.add(clientPlayer);
		sideMenu = new Menu(clientPlayer);
	}
	
	public void togglePause()
	{
		isPaused = !isPaused;
		
		if(!isPaused)
		{
			DrawApp.refreshScreenFlag = true;
			gameTimer.start();
		}
		else
		{
			pauseMenu.changed = true;
			gameTimer.stop();
		}
	}
	
	public void mouseInput(int x, int y)
	{
		if(isPaused)
		{
			pauseMenu.mouseInput(x,y);
		}
		else if(!isHosted)
		{
			sideMenu.mouseInput(x,y);
		}
	}
	
	public void reset()
	{
		projectiles.clear();
		constructs.clear();
		deadlist.clear();
		changed.clear();
		activeBirthList.clear();
		activeSpots.clear();
		activeDeadList.clear();
		numWalls = 0;

		grid.init();

		//p1.setLoc(Grid.getPoint(p1.getLoc().getX(), p1.getLoc().getY()));
		//Grid.getPoint(p1.getLoc().getX(), p1.getLoc().getY()).addEntity(p1);
		//p1.setSpots(0);

		grid.drawPoints();
	}
}
