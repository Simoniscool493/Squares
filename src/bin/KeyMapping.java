package bin;

public class KeyMapping
{
	Player p;
	int up;
	int down;
	int left;
	int right;
	int turn;
	int fire;
	int build;
	int place;
	int delete;
	
	KeyMapping(int u,int d,int l,int r,int t,int f,int b,int p,int de)
	{
		up = u;
		down = d;
		left = l;
		right = r;
		turn = t;
		fire = f;
		build = b;
		place = p;
		delete = de;
	}
	
	void input(int n)
	{
		if(n==up) //up
		{
			p.movingUp = true;
			p.justPressed = true;	
		}
		else if(n==down) //down
		{
			p.movingDown = true;
			p.justPressed = true;
		}
		else if(n==left) //left
		{
			p.movingLeft = true;
			p.justPressed = true;
		}
		else if(n==right) //right
		{
			p.movingRight = true;
			p.justPressed = true;
		}
		else if(n==fire)
		{
			p.active = true;
		}
		else if(n==build)
		{
			p.toggleBuildMode();
		}
		else if(n==place)
		{
			p.placeTurret();
		}
		else if(n==turn)
		{
			p.startTurning();
		}
		else if(n==delete&&!p.active&&p.buildMode)
		{
			p.deleting = true;
		}
		else if(n=='X')
		{
			p.loc.takeControl(p);
		}
	}
	
	void released(int n)
	{
		if(n==turn)
		{
			p.stopTurning();
		}
		if(n==fire)
		{
			p.active = false;
		}
		if(n==delete)
		{
			p.deleting = false;
		}
		if(n==up)
		{
			p.movingUp = false;
		}
		if(n==down)
		{
			p.movingDown = false;
		}
		if(n==left)
		{
			p.movingLeft = false;
		}
		if(n==right)
		{
			p.movingRight = false;
		}
	}
}
