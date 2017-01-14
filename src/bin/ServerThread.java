package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerThread extends Thread
{
	Socket socket;
	boolean listening = true;
	
	ObjectInputStream in;
	
    ServerThread(Socket s,ObjectInputStream in)
    {
        super("ServerThread");
        this.in = in;
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
				Server.sendToOtherPlayers(socket,serverOutput);
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}

	}

}
