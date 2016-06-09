package bin;

import java.util.Random;

public final class U
{
    public static float drawWidth = 1000;
    public static float drawHeight = 1000;
    
	public static boolean showGrid = true;

	public static int gridWidth = 40;
	public static int gridHeight = 40;
	
    public static float incWidth = drawWidth/gridWidth;
    public static float incHeight = drawHeight/gridHeight;
    
	public static Random r = new Random();

	private U()
	{
		
	}
}
