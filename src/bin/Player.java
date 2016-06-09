package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	int points; 
	
	Player(int xPos,int yPos)
	{
		super(xPos,yPos);
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

        g2.fillRect((width*x)+5,(height*y)+5,width-10,height-10);
        
        if(align == 0)//up
        {
        	g2.fillRect((width*x)+(width/2)-2,(height*y)+1,5,5);
        }
        else if(align == 1)//right
        {
        	g2.fillRect((width*x)+width-5,(height*y)+height/2-2,5,5);
        }
        else if(align == 2)//down
        {
        	g2.fillRect((width*x)+(width/2)-2,(height*y)+height-5,5,5);
        }
        else if(align == 3)//left
        {
        	g2.fillRect((width*x)+1,(height*y)+height/2-2,5,5);
        }
	}
	
	void laser()
	{
		DrawApplet.projectiles.add(new Projectile(this,x,y,align,10,color));
	}

}
