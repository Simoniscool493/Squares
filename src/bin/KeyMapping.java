package bin;

import java.io.Serializable;

public class KeyMapping implements Serializable
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
	
	void pressed(int n)
	{
		if(n==up)
		{
			p.setMovingUp(true);
			p.setJustPressed(true);
		}
		else if(n==down)
		{
			p.setMovingDown(true);
			p.setJustPressed(true);
		}
		else if(n==left)
		{
			p.setMovingLeft(true);
			p.setJustPressed(true);
		}
		else if(n==right)
		{
			p.setMovingRight(true);
			p.setJustPressed(true);
		}
		else if(n==fire)
		{
			p.setActive(true);
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
		else if(n==delete&&!p.isActive()&&p.isBuildMode())
		{
			p.setDeleting(true);
		}
		else if(n=='X')
		{
			p.getLoc().takeControl(p);
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
			p.setActive(false);
		}
		if(n==delete)
		{
			p.setDeleting(false);
		}
		if(n==up)
		{
			p.setMovingUp(false);
		}
		if(n==down)
		{
			p.setMovingDown(false);
		}
		if(n==left)
		{
			p.setMovingLeft(false);
		}
		if(n==right)
		{
			p.setMovingRight(false);
		}
	}
}
