package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;

public class ServerThread extends Thread
{
	int id;
	ObjectInputStream in;
	ObjectOutputStream out;

    ServerThread(int id,ObjectInputStream in,ObjectOutputStream out)
    {
        super("ServerThread");
        this.id = id;
        this.in = in;
        this.out = out;
    }

	public void run()
	{
		while(true)
		{
			try
			{
				int[] output = (int[])in.readObject();				
				Game.currentGame.foreignKeyInput(output[0],id);
				
				int[] serverOutput = {0,output[0],id,0};		
				Server.sendToClients(id,serverOutput);
			}
			catch(SocketException e)
			{
				System.out.println("Client " + id + " disconnected");
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
