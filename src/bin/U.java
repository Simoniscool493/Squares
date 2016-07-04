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

	public static int gridWidth = 60;
	public static int gridHeight = 60;
	
    public static float incWidth = drawWidth/(float)gridWidth;
    public static float incHeight = drawHeight/(float)gridHeight;
    
    public static boolean zoom = false;
    public static int zoomRad = 3;
    
    public static float zoomIncWidth = drawWidth/(float)((zoomRad*2)+1);
    public static float zoomIncHeight = drawHeight/(float)((zoomRad*2)+1);

    public static Color gridColor = Color.black;
    public static Color background  = Color.white;
    public static Color wallColor = Color.black;
    public static Color wallHalfColor = Color.gray;
    public static Color p1 = Color.red;
    public static Color p1cap =  new Color(255, 213, 214);
    public static Color p2 = Color.blue;
    public static Color p2cap = new Color(204, 235, 255);

    public static Color menu = Color.darkGray;
    public static Color menuText = Color.white;

    
	public static Random r = new Random();
	
	private U()
	{
		
	}

}
