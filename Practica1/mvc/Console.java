import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class Console implements Observer{

	public Console(){
	}

	public void update(Observable o, Object arg){
		if(o instanceof Line){	//check if the Observable object (o) is an instance of the Line class or its subclass (the one is being observed)
			System.out.print(arg.toString());
		}	
	}
}
