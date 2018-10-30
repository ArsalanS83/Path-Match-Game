/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

/**
 * Create a Path Game
 * @author 105977
 */
public class PathGameGUI extends GameGUI {
    String playerTypes[] = {"Human","Random"};
    
    /**
     * Creates a Path Game
     * @param x Height of Board
     * @param y Width of Board
     */
    public PathGameGUI(int x, int y)
    {
        super("Path Game");
        createPathGame(x,y);
    }
    
    
 
}
