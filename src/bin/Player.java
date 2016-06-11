package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	int points; 
	
	Player(Location l)
	{
		super(l);
		align = 3;
		clipping = true;
		points = 0;
	}
	
	void move()
	{
		
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);

        g2.fillRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        
        if(align == 0)//up
        {
        	g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+1,5,5);
        }
        else if(align == 1)//right
        {
        	g2.fillRect((width*loc.x)+width-5,(height*loc.y)+height/2-2,5,5);
        }
        else if(align == 2)//down
        {
        	g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+height-5,5,5);
        }
        else if(align == 3)//left
        {
        	g2.fillRect((width*loc.x)+1,(height*loc.y)+height/2-2,5,5);
        }
	}
	
	void addPoints(int n)
	{
		points+=n;
		Menu.pointsChanged = true;
	}
	
	void laser()
	{
		DrawApplet.projectiles.add(new Projectile(this,loc,align,40,color));
	}

}
