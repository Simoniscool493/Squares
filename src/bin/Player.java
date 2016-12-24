package bin;

import java.awt.Color;
import java.awt.Graphics2D;

public class Player extends Entity
{
	private boolean godmode = false;
		
	KeyMapping mapping;
	
	private int points;
	private int spots = 0;
	
	private int toNextLvReq = 100;
	private int toNextLv = toNextLvReq;
	
	private int energy = 150;
	private int maxEnergy = 150;
	
	private int build = 50;
	private int maxBuild = 200;

	private int laserLife = 40;
	
	private int laserCost = 10;
	private int energyRegen = 1;
	
	private int claimCap = 300;
	
	private Color claimColor = U.p1cap;
	
	private ConstructedEntity selected = new Turret(null,this);
	
	private boolean buildMode = false;
	private boolean turning = false;
	private boolean active = false;
	private boolean deleting = false;

	private boolean movingUp;
	private boolean movingDown;
	private boolean movingLeft;
	private boolean movingRight;
	private boolean justPressed = false;
	
	private SelectionBox box;
	
	Player(KeyMapping m,GridPoint g,Color c,Color ccap)
	{
		super(g);
		mapping = m;
		m.p = this;
		this.setAlign(3);
		this.setClipping(true);
		points = 0;
		this.setColor(c);
		claimColor = ccap; 
		
		if(godmode)
		{
			laserCost = 0;
			build = 10000;
			maxBuild = 10000;
		}
	}
	
	void keyInput(int n)
	{
		if(n<0)
		{
			mapping.released(-n);
		}
		else
		{
			mapping.pressed(n);
		}	
	}
	
	void update()
	{	
		checkMoving();
		
		if(active)
		{
			if(buildMode)
			{
				placeWall();	
			}
			else
			{
				laser();
			}
		}
		else if(deleting)
		{
			delete();
		}
	
		//loc.takeControl(this);
	}
	
	void checkMoving()
	{
		if(movingUp)
		{
			move(0,-1);
		}
		else if(movingRight)
		{
			move(1,0);
		}
		else if(movingDown)
		{
			move(0,1);
		}
		else if(movingLeft)
		{
			move(-1,0);
		}
	}
	
	void move(int Xoffs,int Yoffs)
	{
		int x = getLoc().getX();
		int y = getLoc().getY();
		
		if(!turning)
		{
			super.move(Xoffs, Yoffs);
			
			if(U.zoom&&((x!=getLoc().getX())||(y!=getLoc().getY())))
			{
				ZoomGrid.move(Xoffs,Yoffs);
			}
		}
		else if(justPressed)
		{
			front().refresh();

			if(movingUp)
			{
				setAlign(0);
			}
			if(movingRight)
			{
				setAlign(1);
			}
			if(movingDown)
			{
				setAlign(2);
			}
			if(movingLeft)
			{
				setAlign(3);
			}
			
			getLoc().refresh();
			justPressed = false;
		}
		
		if(box!=null)
		{
			box.place(front());
			if(buildMode)
			{
				Menu.selectedChanged = true;
			}
		}
	}
	
	void render(Graphics2D g2)
	{
		int curWidth = ((int)(width*getLoc().getX()));
		int curHeight = ((int)(height*getLoc().getY()));

		g2.setColor(getColor());
		
        //g2.fillRect((width*loc.x)+5,(height*loc.y)+5,width-10,height-10);
        g2.fillRect((curWidth)+5,(curHeight)+5,((int)(width))-10,((int)height)-10);

        
        if(getAlign() == 0)//up
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+1,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+1,5,5);
        }
        else if(getAlign() == 1)//right
        {
        	//g2.fillRect((width*loc.x)+width-5,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+(int)width-5,(curHeight)+(int)height/2-2,5,5);
        }
        else if(getAlign() == 2)//down
        {
        	//g2.fillRect((width*loc.x)+(width/2)-2,(height*loc.y)+height-5,5,5);
        	g2.fillRect((curWidth)+(int)(width/2)-2,(curHeight)+(int)height-5,5,5);
        }
        else if(getAlign() == 3)//left
        {
        	//g2.fillRect((width*loc.x)+1,(height*loc.y)+height/2-2,5,5);
        	g2.fillRect((curWidth)+1,(curHeight)+(int)height/2-2,5,5);
        }
  	}
	
	void delete()
	{
		if(front().hasWall()&&front().getWall().getSource()==this)
		{
			front().getWall().die();
		}
		if(front().hasConstruct()&&front().getConstruct().getSource()==this)
		{
			addBuild(front().getConstruct().getBuildCost()/2);
			front().getConstruct().die();
			Menu.selectedChanged = true;
		}
	}
		
	void addPoints(int n)
	{
		points+=n;
		toNextLv-=n;
		
		while(toNextLv<1)
		{
			levelUp();
		}
		
		Menu.pointsChanged = true;
	}
	
	void addBuild(int n)
	{
		if(build+n>maxBuild)
		{
			build = maxBuild;
		}
		else
		{
			build+=n;
		}
		
		Menu.buildChanged = true;
	}
	
	boolean takeBuild(int n)
	{		
		if(build-n<0)
		{
			return false;
		}
		
		build-=n;
		Menu.buildChanged = true;
		return true;
	}
	
	void levelUp()
	{
		setLv(getLv()+1);
		
		if(getLv()%5==0)
		{
			energyRegen++;
			maxBuild+=5;
		}
		toNextLvReq = (int)(toNextLvReq*1.2f);
		toNextLv+=toNextLvReq;
		maxEnergy+=10;
		Menu.levelChanged = true;
	}
	
	void laser()
	{
		if(!getLoc().hasProjectile()&&energy>laserCost-1)
		{
			new Projectile(this);
			energy-=laserCost;
		}
	}
	
	void regen()
	{
		if(energy<maxEnergy)
		{
			energy+=energyRegen;
			if(energy>maxEnergy)
			{
				energy=maxEnergy;
			}
		}
	}
	
	void placeWall()
	{
		GridPoint f = front();
		
		if((!f.hasWall()&&!f.hasConstruct())&&!(f.isNullPoint())&&takeBuild(1))
		{
			f.clearProjectiles();
			new Wall(f,this);
		}
	}
	
	void placeTurret()
	{
		if((!front().isNullPoint())&&(front().isEmpty())&&takeBuild(selected.getCost()))
		{
			Turret t = (Turret)selected.clone();
			t.setStats();
			t.init();
			front().startClaim(this,claimCap/3);
			Menu.selectedChanged = true;
		}
	}
	
	void kill(Entity e)
	{
		addBuild(e.getBuildCost());
		e.die();
	}

	void toggleBuildMode()
	{
		if(!buildMode)
		{
			getLoc().refresh();
			box = new SelectionBox(this,front(),Color.red);
		}
		else
		{
			getLoc().refresh();
			box.die();
			if(turning)
			{
				box = new SelectionBox(this,front(),Color.blue);
			}
		}
		
		buildMode = !buildMode;
		Menu.selectedChanged = true;
	}
	
	void startTurning()
	{
		turning = true;
		getLoc().refresh();
		if(buildMode)
		{
			box.die();
		}
		
		box = new SelectionBox(this,front(),Color.blue);
	}
	
	void stopTurning()
	{
		turning = false;
		box.die();

		if(buildMode)
		{
			box = new SelectionBox(this,front(),Color.red);
		}
		else
		{
			front().refresh();
		}
	}

	public boolean isGodmode() {
		return godmode;
	}

	public void setGodmode(boolean godmode) {
		this.godmode = godmode;
	}

	public KeyMapping getMapping() {
		return mapping;
	}

	public void setMapping(KeyMapping mapping) {
		this.mapping = mapping;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public int getSpots() {
		return spots;
	}

	public void setSpots(int spots) {
		this.spots = spots;
	}

	public int getToNextLvReq() {
		return toNextLvReq;
	}

	public void setToNextLvReq(int toNextLvReq) {
		this.toNextLvReq = toNextLvReq;
	}

	public int getToNextLv() {
		return toNextLv;
	}

	public void setToNextLv(int toNextLv) {
		this.toNextLv = toNextLv;
	}

	public int getEnergy() {
		return energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public void setMaxEnergy(int maxEnergy) {
		this.maxEnergy = maxEnergy;
	}

	public int getBuild() {
		return build;
	}

	public void setBuild(int build) {
		this.build = build;
	}

	public int getMaxBuild() {
		return maxBuild;
	}

	public void setMaxBuild(int maxBuild) {
		this.maxBuild = maxBuild;
	}

	public int getLaserLife() {
		return laserLife;
	}

	public void setLaserLife(int laserLife) {
		this.laserLife = laserLife;
	}

	public int getLaserCost() {
		return laserCost;
	}

	public void setLaserCost(int laserCost) {
		this.laserCost = laserCost;
	}

	public int getEnergyRegen() {
		return energyRegen;
	}

	public void setEnergyRegen(int energyRegen) {
		this.energyRegen = energyRegen;
	}

	public int getClaimCap() {
		return claimCap;
	}

	public void setClaimCap(int claimCap) {
		this.claimCap = claimCap;
	}

	public Color getClaimColor() {
		return claimColor;
	}

	public void setClaimColor(Color claimColor) {
		this.claimColor = claimColor;
	}

	public ConstructedEntity getSelected() {
		return selected;
	}

	public void setSelected(ConstructedEntity selected) {
		this.selected = selected;
	}

	public boolean isBuildMode() {
		return buildMode;
	}

	public void setBuildMode(boolean buildMode) {
		this.buildMode = buildMode;
	}

	public boolean isTurning() {
		return turning;
	}

	public void setTurning(boolean turning) {
		this.turning = turning;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isDeleting() {
		return deleting;
	}

	public void setDeleting(boolean deleting) {
		this.deleting = deleting;
	}

	public boolean isMovingUp() {
		return movingUp;
	}

	public void setMovingUp(boolean movingUp) {
		this.movingUp = movingUp;
	}

	public boolean isMovingDown() {
		return movingDown;
	}

	public void setMovingDown(boolean movingDown) {
		this.movingDown = movingDown;
	}

	public boolean isMovingLeft() {
		return movingLeft;
	}

	public void setMovingLeft(boolean movingLeft) {
		this.movingLeft = movingLeft;
	}

	public boolean isMovingRight() {
		return movingRight;
	}

	public void setMovingRight(boolean movingRight) {
		this.movingRight = movingRight;
	}

	public boolean isJustPressed() {
		return justPressed;
	}

	public void setJustPressed(boolean justPressed) {
		this.justPressed = justPressed;
	}

	public SelectionBox getBox() {
		return box;
	}

	public void setBox(SelectionBox box) {
		this.box = box;
	}
	
	
}
