package bin;

import java.io.IOException;
import java.io.PrintStream;
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
			PrintStream in = new PrintStream(socket.getOutputStream(),false);
			in.println("Test data");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
