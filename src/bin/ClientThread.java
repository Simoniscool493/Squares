package bin;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientThread extends Thread
{
    private Socket socket = null;
    boolean listening = true;
    
    ObjectInputStream in;
    
    ClientThread(ObjectInputStream i)
    {
        super("ClientThread");
        this.in = i;
    }
    
	public void run()
	{				
		while(listening)
		{
			try
			{
				int[] n = (int[])in.readObject();
				
				if(n[0]==0)
				{
					Game.currentGame.foreignKeyInput(n[1],n[2]);
				}
				else if(n[0]==1)
				{
					System.out.println("Sendposition recieved");
					Game.currentGame.positionUpdate(n[1],n[2],n[3]);
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
}
