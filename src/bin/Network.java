package bin;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Network 
{
	static boolean listening = false;
	
	static int port = 81;
	static String ip = "192.168.0.10";

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
        	Socket socket = new Socket(ip, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
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
