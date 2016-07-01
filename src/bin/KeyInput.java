package bin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener 
{ 
	public void keyPressed(KeyEvent k)
	{
		int i = k.getKeyCode();
		Main.da.getKeyInput(i);
	}
	
	public void keyTyped(KeyEvent k)
	{
		
	}
	
	public void keyReleased(KeyEvent k)
	{
		int i = k.getKeyCode();
		Main.da.getKeyReleased(i);
	}
}