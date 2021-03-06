package bin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Random;

public final class U
{
	public static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	
	static Font defaultFont = new Font("TimesRoman", Font.PLAIN, 25);
	
	public static int p1startX = 1;
	public static int p1startY = 1;

    public static boolean zoom = false;
	public static boolean showGrid = true;

	public static int gridWidth = 10;
	public static int gridHeight = 10;
	
    public static int zoomRad = 15;
    public static int zoomGridSize = (zoomRad*2) + 1;

    public static float drawWidth = getDrawDist();
    public static float drawHeight = getDrawDist();
    
    public static float menuWidth = (float)screenSize.getHeight()/4f;
    
    public static float incWidth = (drawWidth/(float)gridWidth);
    public static float incHeight = (drawHeight/(float)gridHeight);
    //public static float incWidth = 0;
    //public static float incHeight = 0;
    
    public static float zoomIncWidth = drawWidth/(float)((zoomRad*2)+1);
    public static float zoomIncHeight = drawHeight/(float)((zoomRad*2)+1);

    public static Color gridColor = Color.black;
    public static Color background  = Color.white;
    public static Color wallColor = Color.black;
    public static Color wallHalfColor = Color.gray;
    public static Color p1 = Color.blue;
    public static Color p1cap =  new Color(255, 213, 214);
    //public static Color p1cap =  Color.blue;

    public static Color p2 = Color.blue;
    public static Color p2cap = new Color(204, 235, 255);

    public static Color menu = Color.darkGray;
    public static Color menuText = Color.white;
    public static Color menuText2 = Color.gray;
	
	private U() {}
	
	private static float getDrawDist()
	{
		//double main = (screenSize.getHeight()/1.25f);
		double main = (screenSize.getHeight()/3f);
		
		if(!zoom)
		{
			while(main%gridWidth!=0)
			{
				main++;
			}
		}
		else
		{
			while(main%zoomGridSize!=0)
			{
				main++;
			}
		}
				
		return (float)(main);
	}

}
