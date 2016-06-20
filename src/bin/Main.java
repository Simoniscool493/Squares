package bin;

import javax.swing.JFrame;


public class Main 
{
	public static JFrame df = new JFrame("Squares");
	public static KeyInput k = new KeyInput();
	public static DrawApplet d = new DrawApplet();
	
	public static void main(String[] args)
	{		
        System.out.println("Git version\n");

		d.init();

		df.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		df.addKeyListener(k);
        df.getContentPane().add("Center",d);
		
        df.pack();
		df.setSize((int)U.drawWidth+(int)U.menuWidth+33,(int)U.drawHeight+89);
		df.setVisible(true);
	}
}


