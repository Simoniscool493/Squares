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
	public static LinkedHashSet<Projectile> deadlist = new LinkedHashSet<Projectile>();
	public static LinkedHashSet<GridPoint> changed = new LinkedHashSet<GridPoint>();
	
	static boolean started = false;
	
	Timer t = new Timer(50,this);
	Player p = new Player(Grid.getPoint(10,10));
	Menu m = new Menu(p);
	
	Font font = new Font("TimesRoman", Font.PLAIN, 25);

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
        
        System.out.println(projectiles.size());
	}
	
	public void update(Graphics2D g2)
	{	
		projectiles.removeAll(deadlist);
		deadlist.clear();
				
		if(!projectiles.isEmpty())
		{			
			for(Projectile p:projectiles)
			{
				p.update();
			}
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
		if(U.r.nextInt()>2100000000)
		//if(U.r.nextInt()>200000000)
		{
			int w = (int)(Math.random() * U.gridWidth);
			int h = (int)(Math.random() * U.gridHeight);
			int lv = (int)((Math.random()*3)+1);
						
			new Wall(Grid.getPoint(w,h),Color.black,lv);
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
		
		this.setFont(font);
		g2.setFont(font);
	}
	
	public void getKeyInput(int n)
	{	
		if(n==87) //up
		{
			p.move(0,-1);
			
			if(!p.strafing)
			{
				p.align = 0;
			}
		}
		else if(n==83) //down
		{
			p.move(0,1);
			
			if(!p.strafing)
			{
				p.align = 2;
			}		}
		else if(n==65) //left
		{
			p.move(-1,0);
			
			if(!p.strafing)
			{
				p.align = 3;
			}		
		}
		else if(n==68) //right
		{
			p.move(1,0);
			
			if(!p.strafing)
			{
				p.align = 1;
			}		
		}
		else if(n==69)
		{
			p.color = Color.BLUE;
			p.loc.refresh();
		}
		else if(n==81)
		{
			p.color = Color.ORANGE;
			p.loc.refresh();
		}	
		else if(n==32)
		{
			p.placeWall();
		}		
		else if(n==70)
		{
			p.laser();
		}
		else if(n==72)
		{
			p.strafing = !p.strafing;
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
