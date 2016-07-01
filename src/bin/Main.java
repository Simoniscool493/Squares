package bin;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class Main 
{
	public static JFrame df = new JFrame("Squares");
	public static KeyInput k = new KeyInput();
	public static DrawApplet da = new DrawApplet();
	
	public static int dir1;
	public static int dir2;
	
	public static void main(String[] args)
	{		
        System.out.println("Git version\n");
          
        //String path = System.getProperty("java.library.path");
        //System.out.println(path);
        //System.loadLibrary("jxinput");

        new ControllerMapping();
                
		da.init();
		
		df.addWindowListener(new WindowAdapter() 
        {
            public void windowDeiconified(WindowEvent e)
            {
            	DrawApplet.refreshScreen = true;
            }
        }
        );

		df.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		df.addKeyListener(k);
        df.getContentPane().add("Center",da);
		
        df.pack();
		df.setSize((int)U.drawWidth+(int)U.menuWidth+6,(int)U.drawHeight+63);
		df.setVisible(true);
		df.setResizable(false);
	}
}


