package bin;

public abstract class ConstructedEntity extends Entity
{
	int timer = 0;
	Player source;
	
	ConstructedEntity() {}
	
	ConstructedEntity(GridPoint g,Player p)
	{
		loc = g;
		loc.addConstruct(this);

		source = p;
		DrawApplet.constructs.add(this);
	}
	
	void update()
	{
		
	}
	
	void die()
	{
		DrawApplet.deadlist.add(this);
		loc.removeConstruct();
	}
}
