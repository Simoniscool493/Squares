package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

//thread which handles data from the server during game runtime
public class ClientThread extends Thread
{    
    ObjectInputStream in;
    ObjectOutputStream out;

    ClientThread(ObjectInputStream i,ObjectOutputStream o)
    {
        super("ClientThread");
        this.in = i;
        this.out = o;
    }
    
	public void run()
	{				
		while(true)
		{
			try
			{
				int[] n = (int[])in.readObject();
				
				//other player presses a key
				if(n[0]==0)
				{
					Game.currentGame.foreignKeyInput(n[1],n[2]);
				}
				//other player stops moving
				else if(n[0]==1)
				{
					Game.currentGame.positionUpdate(n[1],n[2],n[3]);
				}
				//new player added
				else if(n[0]==2)
				{
					System.out.println("New player with id + " + n[1] + " recieved");
					Game.currentGame.addPlayer(n[1]);
				}
			}
			catch(SocketException e)
			{
				System.out.println("Server closed the connection. I guess this is a local game now?");
				break;
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
}
