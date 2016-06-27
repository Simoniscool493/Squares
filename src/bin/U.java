package bin;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Random;

public final class U
{
    public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    public static float drawWidth = (float)(screenSize.getHeight())/1.25f;
    public static float drawHeight = (float)(screenSize.getHeight())/1.25f;
    
    public static float menuWidth = (float)screenSize.getHeight()/5f;
    
	public static boolean showGrid = true;

	public static int gridWidth = 120;
	public static int gridHeight = 120;
	
    public static float incWidth = drawWidth/gridWidth;
    public static float incHeight = drawHeight/gridHeight;
    
	public static Random r = new Random();
	
	private U()
	{
		
	}

}
