package bin;

public class Location 
{
	int x;
	int y;
	
	Location(int xPos,int yPos)
	{
		x = xPos;
		y = yPos;
	}
	
	public Location copy()
	{
		return new Location(x,y);
	}

}
