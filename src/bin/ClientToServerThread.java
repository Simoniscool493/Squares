package bin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
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
		try
		{
	    	DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
	    	DataInputStream din = new DataInputStream(socket.getInputStream());
	    	
	    	din.readLong();
	    	dout.writeLong(1);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		ObjectInputStream o = null;
		
		try
		{
			o = new ObjectInputStream(socket.getInputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		while(listening)
		{
			try
			{
				int[] n = (int[])o.readObject();
				DrawApp.currentGame.foreignKeyInput(n[0],n[1]);
			}
			catch(Exception e)
			{
				e.printStackTrace();
				break;
			}
		}
	}
}
