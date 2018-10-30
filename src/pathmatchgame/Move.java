/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

/**
 * Stores Player Moves - Used by Random Player
 * @author 105977
 */
public class Move {
    
    private final int x;
    private final int y;
    
    /**
     * Creates a New Move
     * @param x x coordinate of move
     * @param y y coordinate of move
     */
    public Move(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Gets the y coordinate of The Move
     * @return y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Gets the y coordinate of The Move
     * @return x coordinate 
     */
    public int getX() {
        return x;
    }
}
