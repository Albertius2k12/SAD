import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class Console implements Observer{

	static enum Action{

		RIGHT, LEFT, DELETE, HOME, END, SUPR, INS, DEF;	//diferent options, like a state
	}

	static class Command{
		Action act;	//guarda la opcion q el observable pasa cuando el cambia
		Object c;	//guarda valores para algunas opciones, home --> cuantas posiciones se mueve?

		Command(Action act, Object c){
			this.act = act;
			this.c = c;
		}
	}

	public Console(){
	}

	public void update(Observable o, Object arg){
		if(o instanceof Line){	//check if the Observable object (o) is an instance of the Line class or its subclass (the one is being observed)
			//System.out.print(arg.toString());

			Command com = (Command) arg;

			switch(com.act){
				case RIGHT:
					 System.out.print("\033[C");
				break;
				case  LEFT:
					 System.out.print("\033[D");
				break;
				case DELETE:
					 System.out.print((char) 27 + "[D" + (char) 27 + "[1P");
				break;
			    case HOME:
			    	System.out.print((char) 27 + "[" + (Integer) com.c + "D");
			    break;
				case END:
					System.out.print((char) 27 + "[" + (Integer) com.c + "C");
				break;
				case SUPR:
					System.out.print("\033[1P");
				break;
				case INS:
					System.out.print(String.valueOf(com.c));
				break;
				case DEF:
					System.out.print("\033[@");
					System.out.print(String.valueOf(com.c));
				break;
			}		
		}
	}
}
