import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable{

    private ArrayList<Character> line;
    private int pos;    //posicion del cursor
    private Object[] vector;

    public Line(){
        line = new ArrayList<>();
        pos = 0;
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
        line.add(pos,c);
        pos++;
        this.setChanged();
        this.notifyObservers(new Console.Command(Console.Action.DEF, c));
    }
    
    public void overwriteChar(char c){
        this.line.set(pos,c);
        pos ++;
        this.setChanged();
        /*vector[0] = -2;
        vector[1] = c;
        this.notifyObservers(vector);*/
        this.notifyObservers(new Console.Command(Console.Action.INS, c));
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
        String frase = "";
        for(int i = 0 ; i < line.size() ; i++){
            frase = frase + this.line.get(i);
        }
        return frase;
    }
}