package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Entity 
{
	static float width = (U.incWidth);
	static float height = (U.incHeight);
	//static float width = 0;
	//static float height = 0;

	static float gridWidth = U.gridWidth;
	static float gridHeight = U.gridHeight;
	
	private GridPoint loc;
	private int align;
	private int lv = 1;
	private int hp;
	private int buildCost = 1;
	private boolean clipping = true;
	private Color color = Color.black;
	
	Entity(){}
	
	Entity(GridPoint g)
	{
		loc = g;
		loc.addEntity(this);
	}
	
	Entity(GridPoint g,Color c)
	{
		this(g);
		color = c;
	}
		
	void render(Graphics2D g2)
	{		
		g2.setColor(color);
        g2.fillRect((int)(width*getLoc().getX())+1,(int)(height*getLoc().getY())+1,((int)width)-1,((int)height)-1);
	}
	
	void update()
	{
		
	}
	
	void move(int Xoffs,int Yoffs)
	{
		loc.refresh();

		int newX = getLoc().getX()+Xoffs;
		int newY = getLoc().getY()+Yoffs;
		
		GridPoint newG = Grid.getPoint(newX,newY);

		if(newG.isNullPoint())
		//goes out of bounds
		{
			outOfBounds();
		}
		else
		{			
			if(U.zoom)
			{
				loc.setZoomMoved(true);
				newG.setZoomMoved(true);;
			}
						
			if((newG.hasWall()||newG.hasConstruct())&&clipping)
			//stops
			{
				bump(newG);
			}
			else
			//keeps going
			{
				loc.removeEntity(this);
				newG.addEntity(this);

				newG.refresh();
				
				loc = newG;
				
			}
		}
	}
	
	void outOfBounds()
	{
		//System.out.println("Out Of Bounds " + (x+Xoffs) + " " + (y+Yoffs));
	}
	
	void place(GridPoint g)
	{
		loc.removeEntity(this);
		loc = g;
		g.addEntity(this);
	}
	
	void bump(GridPoint g)
	{
		
	}
	
	void die()
	{
		loc.removeEntity(this);
	}
		
	void damage(int n)
	{
		hp-=n;
		
		if(hp<1)
		{
			die();
		}
	}
	
	public String toString()
	{
		return "entity";
	}
	
	GridPoint front()
	{
		return loc.getFront(align);
	}

	public GridPoint getLoc() {
		return loc;
	}

	public void setLoc(GridPoint loc) {
		this.loc = loc;
	}

	public int getAlign() {
		return align;
	}

	public void setAlign(int align) {
		this.align = align;
	}

	public int getLv() {
		return lv;
	}

	public void setLv(int lv) {
		this.lv = lv;
	}

	public int getHp() {
		return hp;
	}

	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getBuildCost() {
		return buildCost;
	}

	public void setBuildCost(int buildCost) {
		this.buildCost = buildCost;
	}

	public boolean isClipping() {
		return clipping;
	}

	public void setClipping(boolean clipping) {
		this.clipping = clipping;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
}
