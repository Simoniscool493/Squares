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
        
        //latency = getLatency();
        //System.out.println("Latency recieved");
    }
    
    public int getLatency()
    {
    	try
    	{
	    	DataOutputStream dout = new DataOutputStream(socket.getOutputStream());
	    	DataInputStream din = new DataInputStream(socket.getInputStream());

	    	Timestamp t1 = new Timestamp(System.currentTimeMillis());
	    	long time1 = t1.getTime();

	    	System.out.println("Writing long from server to client");
	    	dout.writeLong(time1);
	    	System.out.println("Reading long from client to server");
	    	long lin = din.readLong();
	    	System.out.println("Long read.");
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
			
			Game.currentGame.togglePause();
			Game.currentGame.playerId = id;
			Game.currentGame.addPlayer();
			System.out.println("Writing game from server to client");
			in.writeObject(Game.currentGame);
			Game.currentGame.playerId = -1;
			Game.currentGame.togglePause();
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
				System.out.println("Reading key input from client to server");
				int[] output = (int[])d.readObject();
				Game.currentGame.foreignKeyInput(output[0],output[1]);
				//System.out.println("Recieved input " + output[0]);
				
				for(Socket s:Server.clients)
				{
					if(!(s==socket))
					{
					    ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
					    System.out.println("Writing key input from server to client");
						out.writeObject(output);
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
