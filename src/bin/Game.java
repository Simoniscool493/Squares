package bin;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;

import javax.swing.Timer;

public class Game implements ActionListener
{
	DrawApp parent;
	Timer gameTimer = new Timer(50,this);	
	
	boolean isPaused = false;

	int maxWalls = 500;
	int numWalls = 0;
	
	public Grid grid;
	public ZoomGrid zoomGrid;

	//up down left right turn fire build place delete
	public static KeyMapping p1KeyMapping;
	//public static KeyMapping p2KeyMapping = new KeyMapping(624,622,623,621,604,603,602,619,601);

	public static Player p1;
	//public static Player p2 = new Player(p2KeyMapping,Grid.getPoint(20,3),U.p2,U.p2cap);

	public Menu sideMenu;
	public PauseMenu pauseMenu;

	public static LinkedHashSet<Projectile> projectiles = new LinkedHashSet<Projectile>();
	public static LinkedHashSet<ConstructedEntity> constructs = new LinkedHashSet<ConstructedEntity>();
	public static LinkedHashSet<Entity> deadlist = new LinkedHashSet<Entity>();
	public static LinkedHashSet<GridPoint> changed = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeBirthList = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeSpots = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeDeadList = new LinkedHashSet<GridPoint>();

	static boolean started = false;

	Game(DrawApp p)
	{
		parent = p;
	}
	
	public void actionPerformed(ActionEvent e)
	{		
		parent.repaint();
	}
	
	public void initialize()
	{
		grid = new Grid();
		zoomGrid = new ZoomGrid(U.p1startX,U.p1startY);
		
		p1KeyMapping = new KeyMapping('W','S','A','D','U','H','K','I','J');
		p1 = new Player(p1KeyMapping,Grid.getPoint(U.p1startX,U.p1startY),U.p1,U.p1cap);

		sideMenu = new Menu(p1);
		pauseMenu = new PauseMenu();
        gameTimer.start();
	}
	
	public void update()
	{
		p1.regen();

        p1.update();
        //p2.update();

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
		
        spawn();  
	}
	
	public void spawn()
	{
		//if(U.r.nextInt()>200000000&&numWalls<maxWalls)
		if(U.r.nextInt()>200000000)
		{
			int w = (int)(Math.random() * U.gridWidth);
			int h = (int)(Math.random() * U.gridHeight);
			int lv = (int)((Math.random()*3)+1);
						
			GridPoint point = Grid.getPoint(w,h);
			if(point.isEmpty())
			{
				new Wall(point,Color.black,lv);
			}
		}
	}

	public void keyInput(int n)
	{
		p1.checkInput(n);
		//p2.checkInput(n);
		if(n=='1')
		{
			reset();
		}
		else if(n=='2')
		{
			Grid.coverGrid(40,1);
		}
		else if(n=='3')
		{
			p1.getLoc().startClaim(p1,1);
		}
		else if(n==' ')
		{
			isPaused = !isPaused;
			
			if(!isPaused)
			{
				parent.refreshScreenFlag = true;
			}
			else
			{
				pauseMenu.changed = true;
			}
		}

	}
	
	public void mouseInput(int x, int y)
	{
		if(isPaused)
		{
			pauseMenu.mouseInput(x,y);
		}
		else
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

		Grid.init();

		p1.setLoc(Grid.getPoint(p1.getLoc().getX(), p1.getLoc().getY()));
		Grid.getPoint(p1.getLoc().getX(), p1.getLoc().getY()).addEntity(p1);
		p1.setSpots(0);
		//p2.spots = 0;

		Grid.drawPoints();
	}
}
