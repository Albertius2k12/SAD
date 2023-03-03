import java.io.*;
import java.util.ArrayList;
import java.util.Observable;

public class Line extends Observable{

    private ArrayList<Character> line;
    private int pos;    //posicion del cursor

    public Line(){
        line = new ArrayList<>();
        pos = 0;
    }
    
    public void right(){
        if(!this.line.isEmpty() && pos != this.line.size()){
            pos++;
            this.setChanged();                     //marca a line (observando) como que ha cambiado --> hasChanged method return true now
            this.notifyObservers("\033[C");        //si Line ha cambiado --> hasChanged returns true, notifica a los observers de Line q ha cambiado
        }
    }
    
    public void left(){
        if(pos != 0){
            pos--;
            this.setChanged();
            this.notifyObservers("\033[D");
        } 
    }
    
    public void insertChar(char c){
        line.add(pos,c);
        pos++;
        this.setChanged();
        this.notifyObservers("\033[@" + c);    //default del switch de console
    }
    
    public void overwriteChar(char c){
        this.line.set(pos,c);
        pos ++;
        this.setChanged();
        this.notifyObservers(c);
    }
    
    public void delete(){
        if(pos != 0 && !this.line.isEmpty()){
            this.line.remove(pos-1); //eliminas el caracter de la izquierda cursor
            pos--;
            this.setChanged();
            this.notifyObservers((char) 27 + "[D" + (char) 27 + "[1P");
        } 
    }
    
    //elimina el character del cursor sin mover el cursor
    public void suprimir(){
        if(!this.line.isEmpty() && line.size() != pos){
            this.line.remove(pos);
            this.setChanged();
            this.notifyObservers("\033[1P");
        }
    }
    
    public void home(){
        int pos2 = pos;
        pos = 0;
        //if(pos2 != 0)
        this.setChanged();
        this.notifyObservers((char) 27 + "[" + pos2 + "D");      
    }
    
    public void end(){
        int pos2 = pos;
        pos = this.line.size();
        int res = pos-pos2;
        if(res != 0){
            this.setChanged();
            this.notifyObservers((char) 27 + "[" + (pos-pos2) + "C");
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