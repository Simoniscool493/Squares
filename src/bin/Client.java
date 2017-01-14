package bin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client 
{
	static Socket clientSocket;
	static int id;
	
	static long latency;
	
	static int port = 81;
	static String ip = "localhost";

	static ObjectInputStream in;
	static ObjectOutputStream out;

	static void connect()
	{
		try
		{
	    	clientSocket = new Socket(ip, port);	

	        in = new ObjectInputStream(clientSocket.getInputStream());
		    out = new ObjectOutputStream(clientSocket.getOutputStream());
		    
		    getLatency();
		}
		catch(Exception e)
		{
        	System.out.println("Failed to connect to host");
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
        	return null;
        }        
	}
	
	static void getId()
	{
		try
        {
            int[] idData = (int[])in.readObject();
            Client.id = idData[1];
        }
        catch(Exception e)
        {
        	System.out.println("Failed to retrieve id");
        	return;
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
        new ClientThread(in,out).start();
	}
	
	static void sendKeyInput(int key)
	{
		try 
		{
		    int[] output = new int[] {key};
			out.writeObject(output);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
