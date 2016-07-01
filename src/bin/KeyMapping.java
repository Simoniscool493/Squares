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
			p.moving = 0;
			
			if(p.turning)
			{
				p.align = 0;
			}
		}
		else if(n==down) //down
		{
			p.moving = 2;
			
			if(p.turning)
			{
				p.align = 2;
			}	
		}
		else if(n==left) //left
		{
			p.moving = 3;
			
			if(p.turning)
			{
				p.align = 3;
			}	
		}
		else if(n==right) //right
		{
			p.moving = 1;
			
			if(p.turning)
			{
				p.align = 1;
			}		
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
		if(n==up||n==down||n==left||n==right)
		{
			p.moving = -1;
		}
	}
}
