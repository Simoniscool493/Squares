package bin;

import ch.aplu.xboxcontroller.XboxController;
import ch.aplu.xboxcontroller.XboxControllerAdapter;

public class ControllerMapping 
{
	int dir1;
	int dir2;
	
	ControllerMapping()
	{
		   XboxController xc = new XboxController();  
	        
	    	//up down left right turn fire build place delete
	    	//601,602,603,  604  605, 606, 607,   608,  609);

	        
	        xc.addXboxControllerListener(new XboxControllerAdapter()
	        {
	        	public void buttonA(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(601);
	        		}
	        		else
	        		{
	        			Main.da.getKeyReleased(601);
	        		}
	        	}
	        	public void buttonB(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(602);
	        		}
	        	}
	        	public void buttonX(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(603);
	        		}
	        		else
	        		{
	        			Main.da.getKeyReleased(603);
	        		}
	        	}
	        	public void buttonY(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(604);
	        		}
	        		else
	        		{
	        			Main.da.getKeyReleased(604);
	        		}
	        	}
	        	public void back(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(605);
	        		}   
	        	}
	        	public void start(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(606);
	        		}   
	        	}
	        	public void leftShoulder(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(607);
	        		}   
	        	}
	        	public void rightShoulder(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(608);
	        		}   
	        	}
	        	public void leftThumb(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(609);
	        		}   
	        	}
	        	public void rightThumb(boolean pressed)
	        	{
	        		if(pressed)
	        		{
	        			Main.da.getKeyInput(610);
	        		}   
	        	}
	        	public void dpad(int direction, boolean pressed)
	        	{
	        		//System.out.println("Dpad " + direction);
	        	}
	        	public void rightTrigger(double value)
	        	{
	        		if(value>0.2)
	        		{
	        			Main.da.getKeyInput(619);
	        		}   
	        	}
	        	public void leftTrigger(double value)
	        	{
	        		if(value>0.2)
	        		{
	        			Main.da.getKeyInput(620);
	        		}
	        	}
	        	public void leftThumbMagnitude(double magnitude)
	        	{
	        		//System.out.println(magnitude);

	        		if(magnitude>0.6)
	        		{

	        			if(dir1>305||dir1<45)
	        			{
	        				Main.da.getKeyInput(624);
	        			}
	        			else if(dir1>225)
	        			{
	        				Main.da.getKeyInput(623);
	        			}
	        			else if(dir1>135)
	        			{
	        				Main.da.getKeyInput(622);
	        			}
	        			else if(dir1>45)
	        			{
	        				Main.da.getKeyInput(621);
	        			}
	        		}
	        		else
	        		{
	        			Main.da.getKeyReleased(624);
	        		}
	        	}
	        	public void leftThumbDirection(double direction)
	        	{
	        		dir1 = (int)direction;
	        	}
	        	public void rightThumbMagnitude(double magnitude)
	        	{
	        		if(magnitude>0.5)
	        		{
	        			if(dir2>305||dir2<45)
	        			{
	        				Main.da.getKeyInput(628);
	        			}
	        			else if(dir2>225)
	        			{
	        				Main.da.getKeyInput(627);
	        			}
	        			else if(dir2>135)
	        			{
	        				Main.da.getKeyInput(626);
	        			}
	        			else if(dir2>45)
	        			{
	        				Main.da.getKeyInput(625);
	        			}
	        		}  
	        	}
	        	public void rightThumbDirection(double direction)
	        	{
	        		dir2 = (int)direction;
	        	}
	        });
	}
}
