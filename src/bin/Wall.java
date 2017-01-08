package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Wall extends Entity
{
	static Color defaultColor = U.wallColor;
	static Color halfColor = U.wallHalfColor;
	
	private Player attacker;
	private Player source;
	private int buildCost = 1;
	private int maxHp;
		
	Wall () {}
	
	Wall(GridPoint g,Player p)
	{
		Game.currentGame.numWalls++;
		setLoc(g);
		getLoc().addWall(this);

		setSource(p);
		setColor(source.getColor());
		setLv(getSource().getLv());
		setMaxHp(getLv()*2);
		setHp(getLv()*2);
		
		getLoc().wallOn();
	}
	
	Wall(GridPoint g,int level)
	{
		Game.currentGame.numWalls++;
		setLoc(g);
		getLoc().addWall(this);

		setColor(defaultColor);
		setLv(level);
		setMaxHp(getLv()*2);
		setHp(getLv()*2);
		
		getLoc().wallOn();
	}
	
	Wall(GridPoint g,Color c,int level)
	{
		this(g,level);
		setColor(c);	
	}
	
	void die()
	{
		Game.currentGame.numWalls--;
		getLoc().removeWall();
	}
	
	boolean damage(Player p,int n)
	{
		setHp(getHp()-n);
		
		if(getHp()<1)
		{
			p.kill(this);
			return true;
		}
		else
		{			
			int r = getColor().getRed();
			int g = getColor().getGreen();
			int b = getColor().getBlue();
			
			r+=n*(200/maxHp);
			g+=n*(200/maxHp);
			b+=n*(200/maxHp);
			
			if(r>255) { r = 255; }
			if(g>255) { g = 255; }
			if(b>255) { b = 255; }
			
			setColor(new Color(r,g,b));
			
			getLoc().refresh();
		}
		
		return false;
	}
	
	void render(Graphics2D g2)
	{
		g2.setColor(getColor());
		if(U.showGrid)
		{
			g2.fillRect((int)(width*getLoc().getX())+1,(int)(height*getLoc().getY())+1,(int)width-1,(int)height-1);
        }
		else
		{
			g2.fillRect((int)(width*getLoc().getX()),(int)(height*getLoc().getY()),(int)width,(int)height);
		}
	}

	public Player getAttacker() {
		return attacker;
	}

	public void setAttacker(Player attacker) {
		this.attacker = attacker;
	}

	public Player getSource() {
		return source;
	}

	public void setSource(Player source) {
		this.source = source;
	}

	public int getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(int buildCost) {
		this.buildCost = buildCost;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}
	
	
}
