package bin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
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
	
	static String getTestMessage()
	{
		String s = "No message recieved";
		
        try
        {
        	Socket socket = new Socket(ip, port);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            s = in.readLine();
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return s;
	}
}
