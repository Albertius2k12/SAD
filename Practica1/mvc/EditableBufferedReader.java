import java.io.*;
import java.util.Observer;
import java.util.Observable;

public class EditableBufferedReader extends BufferedReader{

	public static final int RIGHT = 1000;
	public static final int LEFT = 1001;
	public static final int SUPR = 1002;	//para SUPR --> 'fn' + 'DEL'
	public static final int HOME = 1003;
	public static final int END = 1004;
	public static final int INS = 1005;	//para insert --> 'option' + 'right_arrow'

	public static final int DELETE = 127; //sequencia de control, en ASCII --> 127 

	public EditableBufferedReader(Reader r){
		super(r);
	}
	
	//primera instruccion --> path to the bash shell, segunda --> tells Bash to execute the following command, tercera --> the command to be executed
	public void setRaw() throws IOException{
		Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "stty -echo raw </dev/tty"});	
	}
	
	public void unsetRaw() throws IOException{
		Runtime.getRuntime().exec(new String[] {"/bin/sh", "-c", "stty echo cooked </dev/tty"});
	}
	
	public int read() throws IOException{
		int word = super.read();
		if(word == 27){	//correspon al ESC en ascii
			int second = super.read();	//compruevas si es un insert y et saltes el [ si no lo es
				if(second == 'f'){
					return INS;
				}
			switch(super.read()){
				case 'D':
					return LEFT;
				case 'C':
					return RIGHT;
				case 'A':
					return HOME;
				case 'B':
					return END; 
				case 51:
					super.read();
					return SUPR;
				default:
					return 0;					
			}
		}else{
			return word;	
		}	
	}
	
	/*	\ --> para indicar el comienzo de sequencia de escape
		\033 --> sequencia de escape en octal que representa el ESC caracter en ASCII
			27 es este caracter pero en decimal, lo transformamos a char --> ACII 			 
		*/
	
	public String readLine() throws IOException{
		Line line = new Line();
		Console consola = new Console();
		boolean insertar = false;
		line.addObserver(consola);	//le añade a line, consola como observer para observar line
		this.setRaw();
		int word = this.read();
		while(word != 13){
			switch(word){
				case LEFT:
					line.left();
				break;
				case RIGHT:
					line.right();
				break;
				case DELETE:
					line.delete();	//mueve el cursor a la izquierda y elimina el caracter en esa posicion y mueve todos los caracteres de la derecha del cursor una posicion a la izquierda
					break;
				case HOME:
					line.home();
					break;
				case END:
					line.end();
					break;
				case SUPR:
					line.suprimir();
					break;
				case INS:
					line.overwriteChar();
					break;
				default:
					line.insertChar((char) word);
			}
			word = this.read();
		}
		this.unsetRaw();
		return line.getString();
	}
}



