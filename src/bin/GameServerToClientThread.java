package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameServerToClientThread extends Thread
{
	int id;
    private Socket socket = null;
    boolean listening = true;
    
    GameServerToClientThread(Socket s,int id)
    {
        super("GameServerToClientThread");
        this.socket = s;
        this.id = id;
    }
    
	public void run()
	{
		try
		{
			ObjectOutputStream in = new ObjectOutputStream(socket.getOutputStream());
			
			DrawApp.currentGame.togglePause();
			DrawApp.currentGame.playerId = id;
			DrawApp.currentGame.addPlayer();
			in.writeObject(DrawApp.currentGame);
			DrawApp.currentGame.playerId = -1;
			DrawApp.currentGame.togglePause();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ObjectInputStream d = null;
		
		try
		{
			d = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while(listening)
		{
			try
			{
				int[] output = (int[])d.readObject();
				DrawApp.currentGame.foreignKeyInput(output[0],output[1]);
				
				for(Socket s:Network.clients)
				{
					if(!(s==socket))
					{
					    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
						out.writeObject(output);
						System.out.println("data written out");
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
