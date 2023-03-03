import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class Console implements Observer{

	public Console(){
	}

	public void update(Observable o, Object arg){
		if(o instanceof Line){	//check if the Observable object (o) is an instance of the MyObservable class or its subclass
			System.out.print(arg.toString());
		}	
	}
}