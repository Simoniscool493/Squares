package bin;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientToServerThread extends Thread
{
    private Socket socket = null;
    boolean listening = true;
    
    ClientToServerThread(Socket s)
    {
        super("ClientToServerThread");
        this.socket = s;
    }
    
	public void run()
	{
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
			try
			{
				int n = d.readInt();
				System.out.println(n);
				DrawApp.currentGame.keyInput(n);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
