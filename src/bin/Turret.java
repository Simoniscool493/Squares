package bin;

import java.awt.Graphics2D;

public class Turret extends ConstructedEntity
{
	Turret(GridPoint g,Player p,int a)
	{
		super(g,p);
		align = a;
		System.out.println("yas");
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(color);
        g2.fillRect((width*loc.x)+1,(height*loc.y)+1,width-1,height-1);
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
