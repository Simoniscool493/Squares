package bin;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
	static ArrayList<Socket> clients = new ArrayList<Socket>();

	static boolean listening = false;
	
	static int port = 81;
	static String ip = "localhost";
	
	static ObjectOutputStream out;

	static void hostGame()
	{
		listenForPlayers();
	}
	
	static void listenForPlayers()
	{
		Thread t = new Thread()
		{
			@Override
			public void run()
			{
				listening = true;
				
		        try (ServerSocket serverSocket = new ServerSocket(port))
		        { 
		        	System.out.println("Started listening for players.");
		            while (listening) 
		            {
		            	Socket s = serverSocket.accept();
		            	clients.add(s);
			            new GameServerToClientThread(s,clients.size()-1).start();
			        }
			    } 
		        catch(Exception e)
		        {
		        	System.out.println("Server listen error");
		        	e.printStackTrace();
		        }
			}
		};
		
		t.start();
	}
	



}
