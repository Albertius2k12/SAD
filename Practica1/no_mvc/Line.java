import java.io.*;
import java.util.ArrayList;

public class Line{

	private ArrayList<Character> line;	//guarda chars
	private int pos;	//posicion del cursor

	public Line(){
		line = new ArrayList<>();
		pos = 0;
	}
	
	public boolean right(){
		if(!this.line.isEmpty() && pos != this.line.size()){
			pos++;
			return true;
		} return false;
	}
	
	public boolean left(){
		if(pos != 0){
			pos--;
			return true;
		} return false;
	}
	
	public void insertChar(char c){
		line.add(pos,c);
		pos++;
	}
	
	public void overwriteChar(char c){
		this.line.set(pos,c);
		pos ++;
	}
	
	public boolean delete(){
		if(pos != 0 && !this.line.isEmpty()){
			this.line.remove(pos-1); //eliminas el caracter de la izquierda cursor
			pos--;
			return true;
		} 
		return false;
	}
	
	//elimina el character del cursor sin mover el cursor
	public boolean suprimir(){
		if(!this.line.isEmpty() && line.size() != pos){
			this.line.remove(pos);
			return true;
		}return false;
	}
	
	public int home(){
		int pos2 = pos;
		pos = 0;
		return pos2;		
	}
	
	public int end(){
		int pos2 = pos;
		pos = this.line.size();
		return (pos-pos2);
	}
	
	public String getString(){
		String frase = "";
		for(int i = 0 ; i < line.size() ; i++){
			frase = frase + this.line.get(i);
		}
		return frase;
	}
}
