package bin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Network 
{
	public static void test()
	{
		String s = "Tester data to send as a byte";
		byte[] b = s.getBytes();

		//sendMessage(b,81);
		System.out.println(recieveMessage(81,"192.168.0.10"));
		
		//to send classes, an ObjectInputStream is used somewhere
        
	}
	
	static void sendMessage(byte[] b,int port)
	{
		try
        {
	        ServerSocket serverSocket = new ServerSocket(port);
	        Socket socket = serverSocket.accept();
	        //PrintStream out = new PrintStream(socket.getOutputStream(), false);
	        socket.getOutputStream().write(b);
	        //out.println(message);
	        System.out.println("Message sent");
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }	
	}
	
	static String recieveMessage(int port,String ip)
	{
		String s = "No message recieved";
		
        try
        {
        	Socket socket = new Socket(ip, port);
            //BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        	byte[] b = new byte[255];
        	socket.getInputStream().read(b, 0, 255);
            s = new String(b,"UTF-8");
            
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return s;
	}
}
