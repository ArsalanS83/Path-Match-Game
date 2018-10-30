/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

import java.util.Objects;


/**
 * Represents a High Score in a Game
 * @author 105977
 */
public class HighScore implements Comparable {
    private String playerName;
    private int playerScore;
    
    /**
     * Creates a new High Score
     * @param name name of player
     * @param score score of player
     */
    public HighScore(String name, int score) {
        playerName = name;
        playerScore = score;
    }
    
    /**
     * Gets a Players Name
     * @return The Player's name
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Gets a Players Score
     * @return the Player's score
     */
    public int getPlayerScore() {
        return playerScore;
    }
    
    /**
     * Sets a Players Name
     * @param playerName the name to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Sets a Players Score
     * @param playerScore the score to be set
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    @Override
    public String toString() {
        return playerName + " : " + playerScore;
        // used by File Reader to identify Name and Score
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.playerName);
        hash = 19 * hash + this.playerScore;
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
        final HighScore other = (HighScore) obj;
        if (!Objects.equals(this.playerName, other.playerName)) {
            return false;
        }
        if (this.playerScore != other.playerScore) {
            return false;
        }
        return true;
    }

    /**
     * Compares The Score of each High Score
     * Used to aid sorting in descending order
     * @param o HighScore object to compare against
     * @return 
     */
    @Override
    public int compareTo(Object o) {
        
        int score = ((HighScore)o).getPlayerScore();
        
        return score - playerScore;
    } 
}