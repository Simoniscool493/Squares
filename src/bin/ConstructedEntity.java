package bin;

public abstract class ConstructedEntity extends Entity implements Cloneable
{
	String dislplayName = "What";
	
	int upgrades[];
		
	int timer = 0;
	int rate = 10;
	int power = 10;
	int life = 10;
	Player source;
	
	ConstructedEntity() {}
	
	ConstructedEntity(GridPoint g,Player p)
	{	
		loc = g;
		source = p;
		lv = source.lv;
		align = source.align;
		color = source.color;
		
		if(loc!=null)
		{
			loc.addConstruct(this);
		}
	}
	
	void die()
	{
		DrawApp.deadlist.add(this);
		loc.removeConstruct();
	}
	
	int getCost()
	{
		return 1;
	}
	
	void init()
	{
		loc.addConstruct(this);
		DrawApp.constructs.add(this);
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
