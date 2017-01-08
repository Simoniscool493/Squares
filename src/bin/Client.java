package bin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client 
{
	static Socket clientSocket;
	
	static long latency;
	
	static int port = 81;
	static String ip = "localhost";

	static ObjectOutputStream out;
	static ObjectInputStream in;

	static void connect()
	{
		try
		{
	    	clientSocket = new Socket(ip, port);
	
	        in = new ObjectInputStream(clientSocket.getInputStream());
		    out = new ObjectOutputStream(clientSocket.getOutputStream());
		}
		catch(Exception e)
		{
        	System.out.println("Failed to connect to host");
			e.printStackTrace();
		}
	}

	static Game getGame()
	{		
        try
        {
            return (Game)in.readObject();
        }
        catch(Exception e)
        {
        	System.out.println("Failed to retrieve game");
        	e.printStackTrace();
        	return null;
        }        
	}
	
	static void getLatency()
	{
		try
		{
			Long sentTime = System.currentTimeMillis();
			out.writeObject(new Long(1));
			in.readObject();
			latency = System.currentTimeMillis()-sentTime;
			out.writeObject(new Long(latency));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

	}
	
	static void listenForInput()
	{
        new ClientThread(in).start();
	}
	
	static void sendKeyInput(int key,int id)
	{
		try 
		{
		    int[] output = new int[] {key,id};

			out.writeObject(output);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}


}
