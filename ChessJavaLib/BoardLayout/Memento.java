/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BoardLayout;

import ChessCore.*;

/**
 *
 * @author LOTUS
 */
public class Memento {
    private  ChessBoard state;

    public Memento(ChessBoard state) {
     this.state=state;
    }

    public ChessBoard getState() {
        return state;
}
     public void setState(ChessBoard state) {
        this.state=state;
}

}