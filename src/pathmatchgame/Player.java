/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;


import java.util.Objects;

/**
 * Represents a Player in the Game
 * Plays by Match Game rules - can place anywhere
 * Also Supports Random Player Operations
 * @author 105977
 */
public class Player {
    private String name;
    private int score;
    protected Game game;
    private int xPosition;
    private int yPosition;
    
    /**
     * Creates a Player
     * @param name name of player
     */
    public Player(String name) {
        this.name = name;
        score = 0;
    }
    
    /**
     * Sets the Player's game
     * @param game game that player is playing
     */
    public void setGame(Game game) {
        this.game = game;
    }
    
    /**
     * Retrieve the player's location at x coordinate
     * @return location of x coordinate
     */
    public int getXPosition() {
        return xPosition;
    }
    
    /**
     * Retrieve the player's location at y coordinate
     * @return location of y coordinate
     */
    public int getYPosition() {
        return yPosition;
    }
    
    /**
     * Sets the player's location at x coordinate
     * @param x position to be set
     */
    public void setXPosition(int x) {
        xPosition = x;
    }
    
    /**
     * Sets the player's location at y coordinate
     * @param y position to set
     */
    public void setYPosition(int y) {
        yPosition = y;
    }
    
    /**
     * Gain access to the player's game
     * @return the game that the player is playing
     */
    public Game getGame() {
        return game;
    }
              
    /**
     * Coursework 2 - Increases the score of the player
     * Player has won a game
     */
    public void winner() {
        int scoreCount = 0;
        
        for (int i = 0; i<game.getHeight(); i++)
        {
            for (int j = 0; j<game.getWidth(); j++)
            {
               if (game.getBoard()[i][j] == null)
               {
                   scoreCount++;
               }
            }
        }
        score = score + scoreCount;
    }
    
    /**
     * Get the player's name
     * @return name of player
     */
    public String getName() {
        return name;
    }
    
    /**
     * Get the player's score
     * @return player's score
     */
    public int getScore() {
        return score;
    }
        
    /**
     * Places a Checker on the board according to Match Game rules
     * 1) Can place a checker anywhere
     * 2) Checks if position isn't blocked
     * @param x location at x coordinate
     * @param y location at y coordinate
     * @return true if move successful, false otherwise
     */
    public boolean placeChecker(int x, int y) {
        
        if (game.isEmpty()) // if first move
        {
            // no recent move to get - causes EmptyStackException!
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
            if (game.getBoard()[x][y] == null)
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
                return false; // position blocked
            }
        }        
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    /**
     * Changes the name of a Player
     * Coursework 2 - Allow player name to be changed
     * @param name name to be changed to 
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Changes the score of a Player
     * Coursework 2 - Name Change causes Score to reset to 0
     * @param score score of player to be changed to
     */
    public void setScore(int score) {
        this.score = score;
    }
}