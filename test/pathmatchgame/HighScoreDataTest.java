/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;


import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests Management of High Scores
 * @author 105977
 */
public class HighScoreDataTest {
    private Player player1;
    private PathPlayer player2;
    private Game game;
    private HighScoreData highScores;
    
    public HighScoreDataTest() {
    }
    
    
    
    @Before
    public void setUpGame() {
        player1 = new Player("Player 1");
        player2 = new PathPlayer("Player 2");
        
        game = new Game(8,8,player1,player2);
        
        
        player1.setGame(game);
        player2.setGame(game);
    }
    
    @Before
    public void setUpHighScoreData() {
        highScores = new HighScoreData();
    }
    
    
    /**
     * Tests Adding The First High Score
     */
    @Test
    public void testAddScoresEmpty() {

        // player2 has a score of 58
        player2.setScore(58);
        
        // check that the high scores are empty
        assertTrue(highScores.getHighScores().isEmpty());
        
        // create the new high score consisting of player2's name and 58
        HighScore player2Score;
        player2Score = new HighScore(player2.getName(),player2.getScore());
        
        // add the score to the collection of high scores
        highScores.addHighScore(player2Score);
        
        // check to see if the score saved
        assertEquals(player2Score,highScores.getHighScores().get(0));
    }
    
    /**
     * Tests Updating a Player's High Score
     * Scenario - If The Score obtained is Higher than the player's old score
     */
    @Test
    public void testAddNewHighScore() {
        
        player2.setScore(58);
        
        // create the new high score consisting of player2's name and 58
        HighScore player2Score;
        player2Score = new HighScore(player2.getName(),player2.getScore());
        
        // add the score to the collection of high scores
        highScores.addHighScore(player2Score);
        
        
        // Now Search The HighScores
        // If Player2' New Score (116) is greater than 58
        // Then Remove Player2's Old Score(58) From The High Scores
        assertTrue(highScores.searchHigher(player2.getName(), 116));
        
        // Create The New High Score and Add It
        HighScore newPlayer2Score;
        newPlayer2Score = new HighScore(player2.getName(),116);
        highScores.addHighScore(newPlayer2Score);
        
        // Check That New Score has replaced old score
        assertEquals(newPlayer2Score,highScores.getHighScores().get(0));
        
        // Check That The Old Score has successfully been removed
        assertFalse(highScores.getHighScores().contains(player2Score));
    }
    
    /**
     * Tests Updating The High Score
     * Scenario - If the New Score obtained is lower than the Old Score 
     */
    @Test
    public void testCheckLowerHighScore()
    {
        player2.setScore(58);
        
        // create the new high score consisting of player2's name and 58
        HighScore player2Score;
        player2Score = new HighScore(player2.getName(),player2.getScore());
        
        // add the score to the collection of high scores
        highScores.addHighScore(player2Score);
        
        // Now Search High Scores
        // If Player 2's New Score (34) is lower than 58
        // Then Don't add it to the High Scores
        
        // Player2's Score obtained is lower so return false
        assertFalse(highScores.searchHigher(player2.getName(),34));
        
        // Player2's Score obtained is lower so return true
        assertTrue(highScores.searchLowerandEqual(player2.getName(),34));
        
        // Check That Player2's Score remains unchanged
        assertEquals(player2Score,highScores.getHighScores().get(0)); 
    }
    
    /**
     * Tests Updating The High Score
     * Scenario - If the New Score obtained is equal to the Old Score
     */
    @Test
    public void testCheckEqualHighScore()
    {
        player2.setScore(58);
        
        // create the new high score consisting of player2's name and 58
        HighScore player2Score;
        player2Score = new HighScore(player2.getName(),player2.getScore());
        
        // add the score to the collection of high scores
        highScores.addHighScore(player2Score); 
        
        // Now Search High Scores
        // If Player2's New Score (58) is equal to 58
        // Then Do Nothing
        assertTrue(highScores.searchLowerandEqual(player2.getName(),58));
        
        // Player 2's Score obtained is equal so return false
        assertFalse(highScores.searchHigher(player2.getName(),58));
        
        // Check That Player2's Score remains unchanged
        assertEquals(player2Score,highScores.getHighScores().get(0));  
    }
    
    /**
     * Tests Adding Multiple Scores
     * Tests High Scores are Sorted
     */
    @Test
    public void testAddMultipleScores()
    {
        player1.setScore(58); // Player 1's High Score
        player2.setScore(116); // Player 2's High Score
        
        // Create The High Score Objects
        HighScore player1Score;
        player1Score = new HighScore(player1.getName(),player1.getScore());
        HighScore player2Score;
        player2Score = new HighScore(player2.getName(),player2.getScore());
        
        // Add The High Scores to The Collection
        highScores.addHighScore(player1Score); 
        highScores.addHighScore(player2Score); 
        
        
        //Tests to see if High Scores are sorted in Ascending Order
        
        // Check if Player2's Score is 1st
        assertEquals(player2Score,highScores.getHighScores().get(0));
        
        // Check if Player 1's Score is 2nd
        assertEquals(player1Score,highScores.getHighScores().get(1));
    }
}
