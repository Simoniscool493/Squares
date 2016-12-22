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

public class DrawApp extends JApplet implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static LinkedHashSet<Projectile> projectiles = new LinkedHashSet<Projectile>();
	public static LinkedHashSet<ConstructedEntity> constructs = new LinkedHashSet<ConstructedEntity>();
	public static LinkedHashSet<Entity> deadlist = new LinkedHashSet<Entity>();
	public static LinkedHashSet<GridPoint> changed = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeBirthList = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeSpots = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeDeadList = new LinkedHashSet<GridPoint>();
	
	static boolean started = false;
	static boolean refreshScreenFlag = false;
	
	static int maxWalls = 500;
	static int numWalls = 0;
		
	public static int p1startX = 1;
	public static int p1startY = 1;

	public static Grid grid = new Grid();
	public static ZoomGrid zoomGrid = new ZoomGrid(p1startX,p1startY);
	
	//up down left right turn fire build place delete
	public static KeyMapping p1KeyMapping = new KeyMapping('W','S','A','D','U','H','K','I','J');
	public static KeyMapping p2KeyMapping = new KeyMapping(624,622,623,621,604,603,602,619,601);

	public static Player p1 = new Player(p1KeyMapping,Grid.getPoint(p1startX,p1startY),U.p1,U.p1cap);
	//public static Player p2 = new Player(p2KeyMapping,Grid.getPoint(20,3),U.p2,U.p2cap);

	Timer gameTimer = new Timer(50,this);

	public static Menu sideMenu = new Menu(p1);
	public static PauseMenu pm = new PauseMenu();
	
	InitMenu im = new InitMenu(this);
	
	Font font = new Font("TimesRoman", Font.PLAIN, 25);
	
	static boolean pause;
	
	public void init(Graphics2D g2)
	{
		Grid.drawGrid(g2);
		
		this.setFont(font);
		g2.setFont(font);	
		
		//Grid.wallRect(1,1,20,22,Color.green,100);
		//new Turret(p1.front(),p1);
		
		if(U.zoom)
		{
			GridPoint.width = (U.zoomIncWidth);
			GridPoint.height = (U.zoomIncHeight);
			Entity.width = (U.zoomIncWidth);
			Entity.height = (U.zoomIncHeight);
		}
	}

	public void paint(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;
        
        if(!started)
        {
        	im.render(g2);
        }
        else if(!pause)
        {
    		p1.regen();
    		//p2.regen();
    		
            if(refreshScreenFlag)
            {
            	refreshScreen(g2);
            	refreshScreenFlag = false;
            }
            
            update();
            
            if(U.zoom)
            {
            	zoomRender(g2);
            }
            else
            {
            	fullRender(g2);
            }
            
            sideMenu.render(g2);

            spawn();  
        }
        else
        {
        	pm.render(g2);
        }
	}
	
	public void update()
	{
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
	}
	
	public void fullRender(Graphics2D g2)
	{			
		for(GridPoint g: changed)
		{
			g.render(g2);
		}
				
		changed.clear();
	}
	
	public void zoomRender(Graphics2D g2)
	{			
		ZoomGrid.render(g2);
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
		
	public void actionPerformed(ActionEvent e)
	{		
		repaint();
	}
	
	public static void getKeyInput(int n)
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
			pause = !pause;
			
			if(!pause)
			{
				refreshScreenFlag = true;
			}
			else
			{
				pm.changed = true;
			}
		}
	}
	
	public void mouse(int x,int y)
	{	
		
		if(!started)
		{
			im.mouse(x,y);
		}
		if(pause)
		{
			pm.mouse(x,y);
		}
		else
		{
			Menu.u.mouse(x,y);
		}
	}
	
	public void getKeyReleased(int n)
	{
		if(started)
		{
			p1.checkReleased(n);
		}
	}
	
	public void refreshScreen(Graphics2D g2)
	{
		sideMenu.refresh(g2);
		Grid.refresh(g2);
	}
	
	public static void reset()
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
