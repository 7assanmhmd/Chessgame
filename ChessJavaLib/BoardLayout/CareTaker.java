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
public class CareTaker {
  private Stack<Memento> mementos = new Stack<>();
   
   public void addBoardMemento(Memento memento){
       mementos.push(memento);
       setMementos(mementos);
   }
  /* public Memento getPrevBoardMemento(Stack<Memento> memento){ 
       Memento m =  mementos.pop();
       setMementos(mementos);
       return m;
   }*/
   public void setMementos(Stack<Memento> memento)
   { this.mementos=memento;
   
   }
    public Stack<Memento> getMementos()
   { return mementos;
   
   }
}