package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server
{
	static ArrayList<Socket> clients = new ArrayList<Socket>();
	static ArrayList<ObjectInputStream> inputStreams = new ArrayList<ObjectInputStream>();
	static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<ObjectOutputStream>();

	static boolean listening = false;
	
	static int port = 81;
	static String ip = "localhost";
	
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
		            	System.out.println("Waiting for player");

		            	Socket s = serverSocket.accept();
		            	System.out.println("Adding player " + clients.size());
		            	addPlayer(s);
		            	System.out.println("Player added");
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
	
	public static void addPlayer(Socket s)
	{
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());

			getLatency(in,out);

			outputStreams.add(out);
			inputStreams.add(in);
        	clients.add(s);
        	
			Game.currentGame.togglePause();
			Game.currentGame.addPlayer(clients.size()-1);
			Game.currentGame.clientId = clients.size()-1;
			out.writeObject(Game.currentGame);
			System.out.println("Wrote Game");

			Game.currentGame.clientId = -1;
			Game.currentGame.togglePause();
			
        	listenForPlayer(s,in);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void getLatency(ObjectInputStream in,ObjectOutputStream out)
	{
		try
    	{
			in.readObject();
			out.writeObject(new Long(1));
			System.out.println("Wrote Long");

			System.out.println(((Long)in.readObject()).longValue());
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public static void sendPosition(int id,int x,int y)
	{
		int[] pos = {1,id,x,y};

		int i = 0;
		
		for(Socket s:clients)
		{
			try
			{
				ObjectOutputStream out = outputStreams.get(i);
				out.writeObject(pos);
				System.out.println("Wrote int[] for new player position");
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			i++;
		}
	}
	
	public static void listenForPlayer(Socket s,ObjectInputStream in)
	{
		System.out.println("About to run thread to listen for player's input");
		new ServerThread(s,in).start();
		System.out.println("Thread made to listen for added player's input");
	}

}
