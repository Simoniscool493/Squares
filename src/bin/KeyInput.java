package bin;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener 
{ 
	public void keyPressed(KeyEvent k)
	{
		int i = k.getKeyCode();
		Main.d.getKeyInput(i);
	}
	
	public void keyTyped(KeyEvent k)
	{
		
	}
	
	public void keyReleased(KeyEvent k)
	{
		
	}
}