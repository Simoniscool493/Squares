package bin;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class Main 
{
	public static JFrame df = new JFrame("Squares");
	public static KeyInput k = new KeyInput()
	{
		public void keyReleased(KeyEvent e)
		{
			if(e.getKeyCode()=='U')
			{
				DrawApplet.p.stopTurning();
			}
			if(e.getKeyCode()=='H')
			{
				DrawApplet.p.active = false;
			}
			if(e.getKeyCode()=='J')
			{
				DrawApplet.p.deleting = false;
			}
		}
	};
	public static DrawApplet da = new DrawApplet();
	
	public static void main(String[] args)
	{		
        System.out.println("Git version\n");
        		
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


