package bin;

//Squares: a top down, 2D, sandbox strategy game
//Simon O'Neill, 2016
//Main class called when the app opens, initializes the main game window 

public class Main 
{
	public static MainWindow mw;
	
	public static void main(String[] args)
	{		
        System.out.println("Squares: Git version");
        
        mw = new MainWindow("Squares");
        mw.initialize();    
	}
}

//System.loadLibrary("jxinput");
//new ControllerMapping();

