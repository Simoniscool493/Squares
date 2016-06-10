package bin;

public class Grid 
{
	public static GridPoint[][] grid = new GridPoint[U.gridWidth][U.gridHeight];

	Grid()
	{
		init();
	}
	
	void init()
	{
		for(int i=0;i<U.gridWidth;i++)
		{
			for(int j=0;j<U.gridHeight;j++)
			{
				grid[i][j] = new GridPoint(i,j);
			}
		}
	}
	
	static void addEntity(Entity e,Location l)
	{
		grid[l.x][l.y].contents.add(e);
		refresh(l);
	}
	
	static void removeEntity(Entity e)
	{
		grid[e.loc.x][e.loc.y].contents.remove(e);
		refresh(e.loc);
	}
	
	static void addWall(Wall w,Location l)
	{
		grid[l.x][l.y].wall = w;
		refresh(l);
	}
	
	static void removeWall(Location l)
	{	
		grid[l.x][l.y].wall = null;
		refresh(l);
	}
	
	static void refresh(Location l)
	{
		grid[l.x][l.y].changed = true;
		DrawApplet.changed.add(grid[l.x][l.y]);

	}
	
}
