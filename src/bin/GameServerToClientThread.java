package bin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameServerToClientThread extends Thread
{
    private Socket socket = null;
    boolean listening = true;
    
    GameServerToClientThread(Socket s)
    {
        super("GameServerToClientThread");
        this.socket = s;
    }
    
	public void run()
	{
		try
		{
			ObjectOutputStream in = new ObjectOutputStream(socket.getOutputStream());
			
			DrawApp.currentGame.togglePause();
			in.writeObject(DrawApp.currentGame);
			DrawApp.currentGame.togglePause();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		DataInputStream d = null;
		try
		{
			d = new DataInputStream(socket.getInputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while(listening)
		{
			System.out.println("Listening");
			try
			{
				int n = d.readInt();
				System.out.println(n);
				DrawApp.currentGame.keyInput(n);
				
				/*for(Socket s:Network.clients)
				{
					if(!(s==socket))
					{
					    DataOutputStream out = new DataOutputStream(s.getOutputStream());
						out.writeInt(n);
					}
				}*/
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
