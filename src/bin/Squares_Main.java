package bin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Squares_Main 
{
	public static JFrame df = new JFrame("Squares");
	public static KeyInput k = new KeyInput();
	public static DrawApp da = new DrawApp();
	
	public static int dir1;
	public static int dir2;
	
	public static void main(String[] args)
	{		
        System.out.println("Git version");
          
        //String path = System.getProperty("java.library.path");
        //System.out.println(path);
        //System.loadLibrary("jxinput");

        new ControllerMapping();
                
		da.init();
		
		df.addWindowListener(new WindowAdapter() 
        {
            public void windowDeiconified(WindowEvent e)
            {
            	DrawApp.refreshScreen = true;
            }
        }
        );
		
		da.addMouseListener(new MouseAdapter() 
        {
            public void mouseReleased(MouseEvent e)
            {
            	Menu.u.mouse(e.getX(),e.getY());
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


