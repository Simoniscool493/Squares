package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread
{
	Socket socket;
	boolean listening = true;
	
	ObjectInputStream in;
	ObjectOutputStream out;
	
    ServerThread(Socket s,ObjectInputStream in,ObjectOutputStream out)
    {
        super("ServerThread");
        this.in = in;
        this.out = out;
        this.socket = s;
    }

	public void run()
	{
		while(listening)
		{
			try
			{
				int[] output = (int[])in.readObject();
				Game.currentGame.foreignKeyInput(output[0],output[1]);
				int[] serverOutput = {0,output[0],output[1],0};
						
				for(Socket s:Server.clients)
				{
					if(!(s==socket))
					{
					    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(serverOutput);
						System.out.println("Wrote int[] for key input");
					}
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
