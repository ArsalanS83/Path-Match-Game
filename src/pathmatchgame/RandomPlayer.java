/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

import java.util.ArrayList;
import java.util.Random;

/**
 * Represents a RandomPlayer
 * Automatically makes moves in the Path Game
 * @author 105977
 */
public class RandomPlayer extends Player {
    private final Random randomMoveGenerator;
    private final ArrayList<Move> validMoves;
    
    /**
     * Creates a Random Player
     * Sets Up a Random object used to select Valid Moves
     * Sets up a collection of Valid Moves
     * @param name name of Random Player 
     */
    public RandomPlayer(String name) {
        super(name);
        randomMoveGenerator = new Random();
        validMoves = new ArrayList<>(); 
    }
    
    /**
     * Makes a Random Move in The Game
     * Places Checker on The Board
     * Updates Stack of Recorded Moves
     * Advances The Player Turn
     * @return The Random Move
     */
    public Move generateRandomMove()
    {
        if (game.isEmpty())
        {
            int x = randomMoveGenerator.nextInt(game.getHeight());
            int y = randomMoveGenerator.nextInt(game.getWidth());
            
            Checker c = new Checker(this);
            c.setValue(1);
            game.getBoard()[x][y] = c;
            setXPosition(x);
            setYPosition(y);
            game.recordMove(this);
            game.nextPlayerTurn();
            
            return new Move(x,y);
        }
        else
        {
            // get the move that was made
            Player p = game.getRecentMove();
            
            // move is in range?
            if (p.getYPosition()+1 < game.getWidth())
            {
                // checker already exists in position?
                if (game.getBoard()[p.getXPosition()][p.getYPosition()+1] == null)
                {
                    validMoves.add(new Move(p.getXPosition(),p.getYPosition()+1));
                }
            }
            
            if (p.getYPosition()-1 >= 0)
            {
               if (game.getBoard()[p.getXPosition()][p.getYPosition()-1] == null)
               {
                   validMoves.add(new Move(p.getXPosition(),p.getYPosition()-1));
               }
            }
            
            if (p.getXPosition()+1 < game.getHeight())
            {
               if (game.getBoard()[p.getXPosition()+1][p.getYPosition()] == null) 
               {
                   validMoves.add(new Move(p.getXPosition()+1,p.getYPosition())); 
               }
            }
            
            if (p.getXPosition()-1 >= 0)
            {
               if (game.getBoard()[p.getXPosition()-1][p.getYPosition()] == null) 
               {
                   validMoves.add(new Move(p.getXPosition()-1,p.getYPosition()));
               }
            }
            
            if (validMoves.isEmpty()) // if no Random Moves
            {
                return null;
            }
            
            int moveNum = randomMoveGenerator.nextInt(validMoves.size());

            Move m = validMoves.get(moveNum);
            
            int prevx = p.getXPosition();
            int prevy = p.getYPosition();
            int prevVal = game.getBoard()[prevx][prevy].getValue();
            Checker c = new Checker(this);
            c.setValue(prevVal+1);
            
            game.getBoard()[m.getX()][m.getY()] = c;
            setXPosition(m.getX());
            setYPosition(m.getY());
            game.recordMove(this);
            game.nextPlayerTurn();
            
            validMoves.clear(); 
            
            return new Move(m.getX(),m.getY());
        }
    }
    
    /**
     * Gets The Collection of Valid Moves
     * @return collection of Valid Moves
     */
    public ArrayList<Move> getValidMoves()
    {
        return validMoves;
    }
}
