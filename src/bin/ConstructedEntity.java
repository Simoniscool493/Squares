package bin;

public abstract class ConstructedEntity extends Entity implements Cloneable
{
	String dislplayName = "What";
	
	private int upgrades[];
		
	private int timer = 0;
	private int rate = 10;
	private int power = 10;
	private int life = 10;
	private Player source;
	
	ConstructedEntity() {}
	
	ConstructedEntity(GridPoint g,Player p)
	{	
		setLoc(g);
		setSource(p);
		setLv(source.getLv());
		setAlign(source.getAlign());
		setColor(source.getColor());
		
		if(this.getLoc()!=null)
		{
			this.getLoc().addConstruct(this);
		}
	}
	
	void die()
	{
		Game.currentGame.deadlist.add(this);
		getLoc().removeConstruct();
	}
	
	int getCost()
	{
		return 1;
	}
	
	void init()
	{
		getLoc().addConstruct(this);
		Game.currentGame.constructs.add(this);
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
	
	public int[] getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(int[] upgrades) {
		this.upgrades = upgrades;
	}

	public int getTimer() {
		return timer;
	}

	public void setTimer(int timer) {
		this.timer = timer;
	}

	public int getRate() {
		return rate;
	}

	public void setRate(int rate) {
		this.rate = rate;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public Player getSource() {
		return source;
	}

	public void setSource(Player source) {
		this.source = source;
	}
}
