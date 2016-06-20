package bin;

import java.awt.Graphics2D;

public abstract class ConstructedEntity extends Entity
{
	int timer = 0;
	Player source;
	
	ConstructedEntity() {}
	
	ConstructedEntity(GridPoint g,Player p)
	{
		source = p;
		loc = g;
		loc.addConstruct(this);
		DrawApplet.constructs.add(this);
	}
	
	void update()
	{
		
	}
}
