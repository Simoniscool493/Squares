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

public class DrawApplet extends JApplet implements ActionListener
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
	static boolean refreshScreen = false;
	
	static int maxWalls = 100;
	static int numWalls = 0;
	
	int gw = U.gridWidth;
	int gh = U.gridHeight;
	
	public static int p1startX = 1;
	public static int p1startY = 1;

	public static Grid g = new Grid();
	public static ZoomGrid zg = new ZoomGrid(p1startX,p1startY);
	
	//up down left right turn fire build place delete
	public static KeyMapping m1 = new KeyMapping('W','S','A','D','U','H','K','I','J');
	public static KeyMapping m2 = new KeyMapping(624,622,623,621,604,603,602,619,601);

	public static Player p1 = new Player(m1,Grid.getPoint(p1startX,p1startY),U.p1,U.p1cap);
	public static Player p2 = new Player(m2,Grid.getPoint(20,3),U.p2,U.p2cap);

	Timer t = new Timer(50,this);
	Menu m = new Menu(p1);
	
	Font font = new Font("TimesRoman", Font.PLAIN, 25);
	
	public void init(Graphics2D g2)
	{
		Grid.drawGrid(g2);
		
		this.setFont(font);
		g2.setFont(font);	
		
		//Grid.wallRect(1,1,20,22,Color.green,100);
		
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
        	init(g2);
        	m.init(g2);
            t.start();
            started = true;
        }
        if(refreshScreen)
        {
        	refreshScreen(g2);
        	refreshScreen = false;
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
        
        m.render(g2);

        //spawn();
        
        System.out.println(p1.spots);
	}
	
	public void update()
	{
        p1.update();
        p2.update();

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
		changed.clear();
	}
	
	public void spawn()
	{
		if(U.r.nextInt()>200000000&&numWalls<maxWalls)
		//if(U.r.nextInt()>2100000000)
		//if(false)
		{
			int w = (int)(Math.random() * gw);
			int h = (int)(Math.random() * gh);
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
		p1.regen();
		p2.regen();
		repaint();
	}
	
	public void getKeyInput(int n)
	{	
		p1.checkInput(n);
		p2.checkInput(n);
		if(n=='1')
		{
			reset();
		}
		else if(n=='2')
		{
			Grid.coverGrid(40);
		}
		else if(n=='3')
		{
			p1.loc.startClaim(p1,1);
		}

		//System.out.println(n);
	}
	
	public void getKeyReleased(int n)
	{
		p1.checkReleased(n);
		p2.checkReleased(n);
	}
	
	public void refreshScreen(Graphics2D g2)
	{
		m.refresh(g2);
		Grid.refresh(g2);
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

		p1.loc = Grid.getPoint(p1.loc.x, p1.loc.y);
		Grid.getPoint(p1.loc.x, p1.loc.y).addEntity(p1);
		p1.spots = 0;
		p2.spots = 0;

		Grid.drawPoints();
	}
}
