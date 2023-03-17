import java.io.*;

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
			int second = super.read(); //compruevas que no sea un insert y et saltes el [
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
		//boolean insertar = false;
		this.setRaw();
		int word = this.read();
		while(word != 13){ // En linux ENTER es CR --> 13 en ASCII
			switch(word){
				case LEFT:
					if(line.left()){
						System.out.print("\033[D");
					}
					break;
				case RIGHT:
					if(line.right()){
						System.out.print((char) 27 + "[C");
					}
					break;
				case DELETE:
					if(line.delete()){
						System.out.print((char) 27 + "[D" + (char) 27 + "[1P"); //mueve el cursor a la izquierda y elimina el caracter en esa posicion y mueve todos los caracteres de la derecha del cursor una posicion a la izquierda
					}
					break;
				case HOME:
					System.out.print((char) 27 + "[" + line.home() + "D");
					break;
				case END:
					int move = line.end();
					if(move != 0){
						System.out.print((char) 27 + "[" + move + "C");
					}
					break;
				case SUPR:
					if(line.suprimir()){
						System.out.print("\033[1P");
					}break;
				case INS:
					line.overwriteChar();
					break;
										
				default:
					line.insertChar((char) word);
					System.out.print((char) word);
					 
			}
			word = this.read();
		}
		this.unsetRaw();
		return line.getString();
	} 
}







