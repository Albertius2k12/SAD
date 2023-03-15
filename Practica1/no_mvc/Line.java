import java.io.*;
import java.util.ArrayList;

public class Line{

	private ArrayList<Character> line;	//guarda chars
	private int pos;	//posicion del cursor
	private boolean insertar;

	public Line(){
		insertar = false;
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
		if(this.insertar){
			this.line.set(pos, c);
			pos++;
		}else{
			line.add(pos,c);
			System.out.print("\033[@");	//fa el print del char en el blank space
			pos++;
		}

		/*line.add(pos,c);
		pos++;*/
	}
	
	public void overwriteChar(){
		if(insertar){			//si clicas insertar se mantiene hasta q vuelves a clicar
			insertar = false;
		} else{
			insertar = true;
		}
		//this.line.set(pos,c);
		//pos ++;
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
		StringBuilder sb = new StringBuilder(this.line.size());
		for(Character ch : this.line){
			sb.append(ch);
		}
		return sb.toString();
	}
}
