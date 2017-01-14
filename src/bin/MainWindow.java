package bin;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

//The main JFrame for gameplay - essentially just a container for the DrawApp class.
//Gets its size from the utility class U, and sets up a few listeners - one to refresh the
//Screen if moved, and one to detect mouse clicks.
public class MainWindow extends JFrame
{
	public static DrawApp da;

	MainWindow(String name)
	{
		super(name);
	}
	
	void initialize()
	{
		da = new DrawApp();
		da.init();
		
		addWindowListener(new WindowAdapter() 
        {
            public void windowDeiconified(WindowEvent e)
            {
            	DrawApp.refreshScreenFlag = true;
            }
        }
        );
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addKeyListener(new KeyInput());
        getContentPane().add("Center",da);
		
        pack();
		setSize((int)U.drawWidth+(int)U.menuWidth+6,(int)U.drawHeight+63);
		this.setLocation(500, 0);
		setVisible(true);
		setResizable(false);
	}
}
