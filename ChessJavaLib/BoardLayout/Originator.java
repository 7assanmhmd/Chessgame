/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BoardLayout;

import ChessCore.*;
import java.util.Stack;

/**
 *
 * @author LOTUS
 */
public class Originator {
    //ORIGINATOR
    //CARRIES CHECKPOINT (LAST BOARD)
    
    private ChessBoard state;
   
    
    public Originator(ChessBoard state) {
       save(state);
    }
    
    public void save(ChessBoard state){
       this.state = state;
       
       
    }

    public ChessBoard getState() {
        return state;
    }
   /* public void setState(ChessBoard state)
    {
       this.state = state;
    }*/
    public Memento createMemento()
    {
        return new Memento(state);
    }
    
    public void restoreFromMomento(Memento memento){
        this.state = memento.getState();
}
}