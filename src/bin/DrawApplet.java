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
	
	public static Grid g = new Grid();
	
	public static LinkedHashSet<Projectile> projectiles = new LinkedHashSet<Projectile>();
	public static LinkedHashSet<ConstructedEntity> constructs = new LinkedHashSet<ConstructedEntity>();
	public static LinkedHashSet<Entity> deadlist = new LinkedHashSet<Entity>();
	public static LinkedHashSet<GridPoint> changed = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeBirthList = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeSpots = new LinkedHashSet<GridPoint>();
	public static LinkedHashSet<GridPoint> activeDeadList = new LinkedHashSet<GridPoint>();
	
	static boolean started = false;
	
	int gw = U.gridWidth;
	int gh = U.gridHeight;

	static Player p = new Player(Grid.getPoint(1,1));
	
	Timer t = new Timer(50,this);
	Menu m = new Menu(p);
	
	Font font = new Font("TimesRoman", Font.PLAIN, 25);

	public void init(Graphics2D g2)
	{
		float dw = U.drawWidth;
		float dh = U.drawHeight;
		float iw = U.incWidth;
		float ih = U.incHeight;
		
		if(U.showGrid)
		{
	        for(float i=0;i<dw+10;i+=iw)
	        {
	           g2.drawLine((int)i,0,(int)i,(int)dw);
	        }
	        for(float i=0;i<dh+10;i+=ih)
	        {
	            g2.drawLine(0,(int)i,(int)dw,(int)i);
	        }
	        started = true;
		}
		
		this.setFont(font);
		g2.setFont(font);
	}

	public void paint(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;

        if(!started)
        {
        	init(g2);
        	m.init(g2);
            t.start();
        }
        
        render(g2);
        p.update();
        spawn();
        
        //System.out.println(activeSpots.size());
	}
	
	public void render(Graphics2D g2)
	{	
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
				
		for(GridPoint g: changed)
		{
			g.render(g2);
		}
				
		changed.clear();
		
        m.render(g2);
	}
	
	public void spawn()
	{
		//if(U.r.nextInt()>2100000000)
		if(U.r.nextInt()>200000000)
		//if(false)
		{
			int w = (int)(Math.random() * gw);
			int h = (int)(Math.random() * gh);
			int lv = (int)((Math.random()*3)+1);
						
			new Wall(Grid.getPoint(w,h),Color.black,lv);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		p.regen();
		repaint();
	}
	
	public void getKeyInput(int n)
	{	
		if(n=='W') //up
		{
			p.move(0,-1);
			
			if(p.turning)
			{
				p.align = 0;
			}
		}
		else if(n=='S') //down
		{
			p.move(0,1);
			
			if(p.turning)
			{
				p.align = 2;
			}	
		}
		else if(n=='A') //left
		{
			p.move(-1,0);
			
			if(p.turning)
			{
				p.align = 3;
			}	
		}
		else if(n=='D') //right
		{
			p.move(1,0);
			
			if(p.turning)
			{
				p.align = 1;
			}		
		}
		else if(n==' ')
		{
			p.placeWall();
		}		
		else if(n=='H')
		{
			p.active = true;
		}
		else if(n=='K')
		{
			p.toggleBuildMode();
		}
		else if(n=='I')
		{
			p.placeTurret();
		}
		else if(n=='U')
		{
			p.startTurning();
		}
	}	
}
