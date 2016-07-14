package bin;

public abstract class ConstructedEntity extends Entity implements Cloneable
{
	int timer = 0;
	Player source;
	
	ConstructedEntity() {}
	
	ConstructedEntity(GridPoint g,Player p)
	{
		loc = g;
		source = p;
		lv = source.lv;
		align = source.align;
		color = source.color;
	}
	
	void update()
	{
		
	}
	
	void die()
	{
		DrawApplet.deadlist.add(this);
		loc.removeConstruct();
	}
	
	int getCost()
	{
		return 1;
	}
	
	void init()
	{
		loc.addConstruct(this);
		DrawApplet.constructs.add(this);
	}
	
	public Object clone()
	{  
	    try
	    {  
	        return super.clone();  
	    }
	    catch(Exception e)
	    { 
	        return null; 
	    }
	}
	
}
