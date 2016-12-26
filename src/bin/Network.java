package bin;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Network 
{
	static boolean listening = false;
	
	static int port = 81;
	static String ip = "192.168.0.10";
	
	static Socket clientSocket;

	static void sendInput(int n)
	{
		DataOutputStream out;
		try 
		{
		    out = new DataOutputStream(clientSocket.getOutputStream());
			out.writeInt(n);
		} 
		catch (IOException e) {}

	}
	
	static void listenForPlayers(Game g)
	{
		listening = true;
		
        try (ServerSocket serverSocket = new ServerSocket(port))
        { 
        	System.out.println("Started listening for players.");
            while (listening) 
            {
	            new GameServerToClientThread(serverSocket.accept()).start();
	            System.out.println("Player connected, thread made");
	        }
	    } 
        catch(Exception e)
        {
        	e.printStackTrace();
        }
	}
	
	static Game getGame()
	{
		Game g = null;
		
        try
        {
        	clientSocket = new Socket(ip, port);
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            g = (Game)in.readObject();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.out.println("Failed to connect to host");
        }
        
        return g;
	}
}
