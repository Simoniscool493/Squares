package bin;

import java.awt.Color;
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

	public static int gridWidth = 30;
	public static int gridHeight = 30;
	
    public static float incWidth = drawWidth/(float)gridWidth;
    public static float incHeight = drawHeight/(float)gridHeight;
    
    public static Color gridColor = Color.black;
    public static Color background  = Color.white;
    public static Color wallColor = Color.black;
    public static Color wallHalfColor = Color.gray;
    public static Color p1 = Color.red;
    public static Color p1cap =  new Color(255, 233, 234);
    public static Color menu = Color.darkGray;
    public static Color menuText = Color.white;

    
	public static Random r = new Random();
	
	private U()
	{
		
	}

}
