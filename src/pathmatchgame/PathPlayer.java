/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;


/**
 * Represents a Path Player
 * @author 105977
 */
public class PathPlayer extends Player {
    
    /**
     * Creates a Path Player
     * @param name name of Player
     */
    public PathPlayer(String name) {
        super(name);
    }
    
    /**
     * Places a Checker on the board according to Path Game rules
     * Checks if the move is adjacent to the previous move
     * @param x coordinate of checker
     * @param y coordinate of checker
     * @return true if move successful, false otherwise
     */
    @Override
    public boolean placeChecker(int x, int y) {
        
        if (game.isEmpty()) // if it's the first move
        {
            Checker c = new Checker(this);
            c.setValue(1);
            game.getBoard()[x][y] = c;
            setXPosition(x);
            setYPosition(y);
            game.recordMove(this);  
            game.nextPlayerTurn();
            return true;
        }
        else
        {
             boolean legal = game.isLegal(x, y);
             
             if (legal)
             {
                Player p = game.getRecentMove();
                int prevx = p.getXPosition();
                int prevy = p.getYPosition();
                int prevVal = game.getBoard()[prevx][prevy].getValue();
                 
                Checker c = new Checker(this);
                c.setValue(prevVal+1);
                
                game.getBoard()[x][y] = c;
                setXPosition(x);
                setYPosition(y);
                game.recordMove(this);
                game.nextPlayerTurn();
                return true;
             }
             else
             {
                 return false;
             }
        }
    }
}