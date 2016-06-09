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
	
	static void addEntity(Entity e,int x,int y)
	{
		grid[x][y].contents.add(e);
		refresh(x,y);
	}
	
	static void removeEntity(Entity e)
	{
		grid[e.x][e.y].contents.remove(e);
		refresh(e.x,e.y);
	}
	
	static void addWall(Wall w,int x,int y)
	{
		grid[x][y].wall = w;
		refresh(x,y);
	}
	
	static void removeWall(int x,int y)
	{	
		grid[x][y].wall = null;
		refresh(x,y);
	}
	
	static void refresh(int x,int y)
	{
		grid[x][y].changed = true;
	}
	
}
