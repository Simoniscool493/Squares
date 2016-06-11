package bin;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JApplet;
import javax.swing.Timer;

public class DrawApplet extends JApplet implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	public static ArrayList<Projectile> projectiles = new ArrayList<Projectile>();
	public static ArrayList<Projectile> deadlist = new ArrayList<Projectile>();
	public static ArrayList<GridPoint> changed = new ArrayList<GridPoint>();
	
	static boolean started = false;
	
	Menu m = new Menu();
	Timer t = new Timer(50,this);
	Player p = new Player(new Location(10,10));

	public void paint(Graphics g)
	{
        Graphics2D g2 = (Graphics2D)g;
  
        if(!started)
        {
        	init(g2);
        	m.init(g2);
            t.start();
        }
        
        update(g2);
        spawn();
        
        //System.out.println(Grid.grid[4][0].contents.size());
	}
	
	public void update(Graphics2D g2)
	{
		projectiles.removeAll(deadlist);

		if(!projectiles.isEmpty())
		{
			for(int i = 0;i<projectiles.size();i++)
			{
				projectiles.get(i).update();
			}
		}
				
		for(GridPoint g: changed)
		{
			g.contents.removeAll(deadlist);
			g.render(g2);
		}
		
		System.out.println(changed.size());

		changed.clear();
		
        m.render(g2,p);
	}
	
	public void spawn()
	{
		if(U.r.nextInt()>2100000000)
		{
			int w = (int)(Math.random() * U.gridWidth);
			int h = (int)(Math.random() * U.gridHeight);
			
			new Wall(new Location(w,h),Color.black);
		}
	}
	
	public void actionPerformed(ActionEvent e)
	{
		repaint();
	}
	
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
	}
	
	public void getKeyInput(int n)
	{	
		if(n==87) //up
		{
			p.move(0,-1);
			p.align = 0;
		}
		else if(n==83) //down
		{
			p.move(0,1);
			p.align = 2;
		}
		else if(n==65) //left
		{
			p.move(-1,0);
			p.align = 3;
		}
		else if(n==68) //right
		{
			p.move(1,0);
			p.align = 1;
		}
		else if(n==69)
		{
			p.color = Color.BLUE;
			Grid.refresh(p.loc);
		}
		else if(n==81)
		{
			p.color = Color.ORANGE;
			Grid.refresh(p.loc);
		}	
		else if(n==32)
		{
			p.placeWall();
		}		
		else if(n==70)
		{
			p.laser();
		}
	}
	
	public void randColor()
	{
		int r1 = (int)(Math.random() * 255 + 1);
		int r2 = (int)(Math.random() * 255 + 1);
		int r3 = (int)(Math.random() * 255 + 1);

		//drawColor = new Color(r1,r2,r3);
	}
}
