package bin;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Network 
{
	static ArrayList<Socket> clients = new ArrayList<Socket>();

	static boolean listening = false;
	
	static int port = 81;
	static String ip = "localhost";
	
	static Socket clientSocket;
	static ObjectOutputStream out;

	static void sendInput(int key,int id)
	{
		try 
		{
		    int[] output = new int[] {key,id};
			System.out.println("Sent output " + output[0]);

			out.writeObject(output);
			out.reset();
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
            	Socket s = serverSocket.accept();
            	System.out.println("Player connected");

            	clients.add(s);
	            new GameServerToClientThread(s,clients.size()-1).start();
	            System.out.println("Thread made");
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
            
		    out = new ObjectOutputStream(clientSocket.getOutputStream());
           
            new ClientToServerThread(clientSocket).start();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        	System.out.println("Failed to connect to host");
        }
        
        return g;
	}
}
