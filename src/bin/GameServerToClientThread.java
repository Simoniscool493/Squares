package bin;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class GameServerToClientThread extends Thread
{
    private Socket socket = null;
    
    GameServerToClientThread(Socket s)
    {
        super("MessengerServerThread");
        this.socket = s;
    }
    
	public void run()
	{
		try
		{
			ObjectOutputStream in = new ObjectOutputStream(socket.getOutputStream());
			DrawApp.currentGame.togglePause();
			System.out.println(DrawApp.currentGame.p1.getLv());
			in.writeObject(DrawApp.currentGame);
			DrawApp.currentGame.togglePause();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
