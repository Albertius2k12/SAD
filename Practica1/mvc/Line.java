import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable{

    private ArrayList<Character> line;
    private int pos;    //posicion del cursor
    private Object[] vector;
    private boolean insertar;

    public Line(){
        line = new ArrayList<>();
        pos = 0;
        insertar = false;
        //Object vector [] = new Object[2];
        vector = new Object[2];
    }
    
    public void right(){
        if(!this.line.isEmpty() && pos != this.line.size()){
            pos++;
            this.setChanged();                     //marca a line (observando) como que ha cambiado --> hasChanged method return true now
            this.notifyObservers(new Console.Command(Console.Action.RIGHT, null));
        }
    }
    
    public void left(){
        if(pos != 0){
            pos--;
            this.setChanged();
             this.notifyObservers(new Console.Command(Console.Action.LEFT, null));
        } 
    }
    
    public void insertChar(char c){
        if(insertar){
            this.line.set(pos,c);
            this.setChanged();
            this.notifyObservers(new Console.Command(Console.Action.INS, c));
            pos++;
        }else{
            line.add(pos,c);
            this.setChanged();
            this.notifyObservers(new Console.Command(Console.Action.DEF, c));
            pos++;
        }
    }
    
    public void overwriteChar(){
        if(insertar){
            insertar = false;
        }else{
            insertar = true;
        }
    }
    
    public void delete(){
        if(pos != 0 && !this.line.isEmpty()){
            this.line.remove(pos-1); //eliminas el caracter de la izquierda cursor
            pos--;
            this.setChanged();
            this.notifyObservers(new Console.Command(Console.Action.DELETE, null));
        } 
    }
    
    //elimina el character del cursor sin mover el cursor
    public void suprimir(){
        if(!this.line.isEmpty() && line.size() != pos){
            this.line.remove(pos);
            this.setChanged();
            this.notifyObservers(new Console.Command(Console.Action.SUPR, null));
        }
    }
    
    public void home(){
        int pos2 = pos;
        pos = 0;
        this.setChanged();
        this.notifyObservers(new Console.Command(Console.Action.HOME, pos2));
    }
    
    public void end(){
        int pos2 = pos;
        pos = this.line.size();
        int res = pos-pos2;
        if(res != 0){
            this.setChanged();
            this.notifyObservers(new Console.Command(Console.Action.END, res));
        }
    }
    
    public String getString(){
        StringBuilder sb = new StringBuilder(this.line.size());
		for(Character ch : this.line){
			sb.append(ch);
		}
		return sb.toString();
    }
}