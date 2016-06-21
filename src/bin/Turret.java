package bin;

import java.awt.Graphics2D;

public class Turret extends ConstructedEntity
{
	Turret(GridPoint g,Player p,int a)
	{
		super(g,p);
		align = a;
		color = source.color;
	}
	
	void render(Graphics2D g2)
	{
		//System.out.println("yes");
		g2.setColor(color);

        g2.drawRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        
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
	
	void update()
	{
		timer++;
		if(timer == 10)
		{
			new Projectile(source,loc,align,20,color);
			timer = 0;
		}
	}
}
