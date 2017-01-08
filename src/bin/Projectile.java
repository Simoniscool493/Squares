package bin;

import java.awt.Graphics2D;

public class Projectile extends Entity
{
	private int life;
	private int power;
	private boolean twisting = false;
	private Player source;
	
	Projectile(Player p)
	{
		setSource(p);
		setLoc(p.getLoc());
		getLoc().addProjectile(this);

		setColor(source.getColor());
		setAlign(source.getAlign());
		setLife(source.getLaserLife());
		setPower(source.getLv());
		
		Game.currentGame.projectiles.add(this);
	}
	
	Projectile(ConstructedEntity e,int al)
	{
		setSource(e.getSource());
		setLoc(e.getLoc());
		getLoc().addProjectile(this);
		
		setColor(source.getColor());
		setAlign(al);
		setLife(e.getLife());
		setPower(e.getPower());
		Game.currentGame.projectiles.add(this);
		
		if(e.getUpgrades()[1]>0)
		{
			twisting = true;
		}
	}
	
	void update()
	{
		if(getAlign()==0) //up
		{
			move(0,-1);
		}
		else if(getAlign()==2) //down
		{
			move(0,1);
		}
		else if(getAlign()==3) //left
		{
			move(-1,0);
		}
		else if(getAlign()==1) //right
		{
			move(1,0);
		}
		
		if(twisting)
		{
			if((Math.random() * 3)>2)
			{
				setAlign((int)(Math.random() * 4));
			}
		}
		
		life--;
		
		if(life==0)
		{
			die();
		}
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(getColor());
		
		if(getAlign() == 1||getAlign() == 3)//horizontal
		{
			g2.fillRect((int)(width*getLoc().getX())+2,(int)(height*getLoc().getY())+(int)height/2-1,(int)width-2,3);
		}
		else//vertical
		{
			g2.fillRect((int)(width*getLoc().getX())+(int)width/2-1,(int)(height*getLoc().getY())+2,3,(int)height-2);
		}
	}
	
	void outOfBounds()
	{
		die();
	}
		
	void bump(GridPoint g)
	{
		die();
		
		if(g.hasWall())
		{
			int residualHp = g.getWall().getHp();
			boolean killed = g.getWall().damage(source,power);
			
			if(!killed)
			{
				((Player)source).addPoints(power);
			}
			else
			{
				((Player)source).addPoints(residualHp);
			}
		}
	}
	
	void die()
	{
		Game.currentGame.deadlist.add(this);
		getLoc().removeProjectile(this);
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public boolean isTwisting() {
		return twisting;
	}

	public void setTwisting(boolean twisting) {
		this.twisting = twisting;
	}

	public Player getSource() {
		return source;
	}

	public void setSource(Player source) {
		this.source = source;
	}
	
}

