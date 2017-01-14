package bin;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

public class Server
{
	private static int hostingPort = 81;

	private static ArrayList<Socket> clients = new ArrayList<Socket>();
	private static ArrayList<ObjectOutputStream> outputStreams = new ArrayList<ObjectOutputStream>();

	private static boolean listening = false;
	
	static void hostGame()
	{
		listenForPlayers();
	}
	
	static void listenForPlayers()
	{
		listening = true;

		Thread t = new Thread()
		{
			@Override
			public void run()
			{
		        try (ServerSocket serverSocket = new ServerSocket(hostingPort))
		        { 
		        	System.out.println("Started listening for players.");
		            while (listening) 
		            {
		            	Socket s = serverSocket.accept();
		            	addPlayer(s);
		            	System.out.println("Added player " + (clients.size()-1));
			        }
			    } 
		        catch(Exception e) 
		        { 
		        	System.out.println("Server listen error");
		        	listening = false;
		        }
			}
		};
		
		t.start();
	}
	
	public static void addPlayer(Socket s)
	{
		try
		{
        	int newPlayerId = clients.size();

			ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream in = new ObjectInputStream(s.getInputStream());
			
			getLatency(in,out);

			outputStreams.add(out);
        	clients.add(s);

        	sendGame(newPlayerId,out);

			int[] newPlayerData = {2,newPlayerId,U.p1startX,U.p1startY};
			sendToClients(newPlayerId,newPlayerData);
			
        	listenForPlayerInput(newPlayerId,in,out);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendGame(int id,ObjectOutputStream out)
	{
		try
		{
			Game.currentGame.togglePause();
			Game.currentGame.addPlayer(id);
			
			System.out.println("Player of menu: " + Game.currentGame.sideMenu.p);
			out.writeObject(Game.currentGame);
			out.writeObject(new int[]{-1,id});

			Game.currentGame.togglePause();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void sendToClients(int originPlayer,int[] output)
	{
		boolean sendToAll = (originPlayer==-1);

		for(int id=0;id<clients.size();id++)
		{
			if(sendToAll||!(id==originPlayer))
			{
			    ObjectOutputStream out = Server.outputStreams.get(id);
			    
			    try
			    {
			    	out.writeObject(output);
				}
			    catch(SocketException e)
			    {
			    	System.out.println("Could not send data to client " + id);
			    }
			    catch(Exception e)
			    {
			    	e.printStackTrace();
			    }
			}
		}
	}
	
	public static void getLatency(ObjectInputStream in,ObjectOutputStream out)
	{
		try
    	{
			in.readObject();
			out.writeObject(new Long(1));
			Long latency = ((Long)in.readObject()).longValue();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
	
	public static void sendPosition(int id,int x,int y)
	{
		int[] pos = {1,id,x,y};
		sendToClients(-1,pos);
	}
	
	public static void listenForPlayerInput(int id,ObjectInputStream in,ObjectOutputStream out)
	{
		new ServerThread(id,in,out).start();
	}
}
