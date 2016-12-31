package bin;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;

public class GameServerToClientThread extends Thread
{
	int id;
	int latency;
    private Socket socket = null;
    boolean listening = true;
    
    GameServerToClientThread(Socket s,int id)
    {
        super("GameServerToClientThread");
        this.socket = s;
        this.id = id;
        
        latency = getLatency();
    }
    
    public int getLatency()
    {
    	try
    	{
	    	DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
	    	DataInputStream din = new DataInputStream(socket.getInputStream());

	    	Timestamp t1 = new Timestamp(System.currentTimeMillis());
	    	long time1 = t1.getTime();

	    	dout.writeLong(time1);
	    	long lin = din.readLong();
	    	
	    	Timestamp t2 = new Timestamp(System.currentTimeMillis());
	    	long time2 = t2.getTime();
	    	
	    	long latency = time2-time1;
	    	
	    	System.out.println(latency);
	    	return (int)latency;
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    		return 0;
    	}
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
				System.out.println("Recieved output " + output[0]);
				
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
