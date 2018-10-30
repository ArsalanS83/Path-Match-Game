/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;


/**
 * Represents Checker Objects on a Game
 * Value of Checkers represent the Move Number
 * @author 105977
 */
public class Checker {
    private int value;
    private final Player owner;
    
    /**
     * Creates a Checker
     * @param p Player object that Checker belongs to 
     */
    public Checker(Player p) {
        owner = p;
        value = 1;
    }
    
    /**
     * Get Value of Checker
     * Represents Getting aMove Number
     * @return value of Checker
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Get Owner of Checker
     * @return Player object that the Checker belongs to
     */
    public Player getOwner() {
        return owner;
    }
    
    /**
     * Set Value of Checker
     * Represents Setting Move Number
     * @param num number to set
     */
    public void setValue(int num) {
        value = num;
    }
}
