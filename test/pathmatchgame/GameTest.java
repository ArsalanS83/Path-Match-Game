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
 * Tests a Match Game Scenario
 * Main Focus: Test The Game Class
 * @author 105977
 */
public class GameTest {
    private Game matchGame;
    private Player player1;
    private PathPlayer player2;
    
    public GameTest() {
    }
    
    
    @Before
    public void setUpMatchGame() {
        player1 = new Player("Player 1");
        player2 = new PathPlayer("Player 2");
        
        matchGame = new Game(8,8,player1,player2);
        
        
        player1.setGame(matchGame);
        player2.setGame(matchGame);
    }
    
    /**
     * Tests recording a move (player's state)
     * Tests getting a move (player's state)
     * 1) Check correct player is obtained from stack
     * 2) Check coordinates of player match
     */
    @Test
    public void testRecordMoveGetMove() {
        
        player1.setXPosition(0);
        player1.setYPosition(4);
        // current state of player (current move made)
        
        matchGame.recordMove(player1);
        
        assertEquals(player1,matchGame.getRecentMove());
        assertEquals(0,matchGame.getRecentMove().getXPosition());
        assertEquals(4,matchGame.getRecentMove().getYPosition());
        
        player2.setXPosition(1);
        player2.setYPosition(4);
        
        matchGame.recordMove(player2);
        
        assertEquals(player2,matchGame.getRecentMove());
        assertEquals(1,matchGame.getRecentMove().getXPosition());
        assertEquals(4,matchGame.getRecentMove().getYPosition());
    }
    
    /**
     * Tests Path Player Moves that are legal and illegal
     * Legal - Adjacent to previous move
     * Illegal - Not Adjacent
     */
    @Test
    public void testIsLegal() {
        
        player1.placeChecker(0, 0);
        
        assertTrue(matchGame.isLegal(1, 0));
        assertTrue(matchGame.isLegal(0, 1));
        
        assertFalse(matchGame.isLegal(4,2));
    }
    
    /**
     * Tests that the correct player has won the game
     * Coursework 2 - Tests that the score is equal to the number of unused squares
     */
    @Test
    public void testIsWinner() {
        player1.placeChecker(0, 0);
        
        player2.placeChecker(0, 1);

        
        player1.placeChecker(1, 1);
        
        player2.placeChecker(2, 1);
        
        player1.placeChecker(2, 0);
        
        player2.placeChecker(1, 0); // player2 has lost, all adjacent checkers filled
        
        assertTrue(matchGame.isWinner(player1));
        player1.winner(); // increment score
        
        assertEquals(58,player1.getScore());
        
        assertFalse(matchGame.isWinner(player2)); 
        assertEquals(0,player2.getScore());
    }
    
    /**
     * Tests Clearing The Game results in a board with Null Checkers
     * Tests Player Turn returns to Player 1
     * Tests Stack of Moves gets cleared
     */
    @Test
    public void testResetGame() {
        
        player1.placeChecker(2, 2);
        
        matchGame.resetGame();
        
        assertTrue(matchGame.isEmpty()); // check stack of moves is empty
        assertNull(matchGame.getBoard()[2][2]); // ensures checker is null
        assertEquals(1,matchGame.getTurnNo()); // check its player 1's turn
        
    }
    
    /**
     * Tests Moving Player Turns Behaves Correctly
     * Coursework 2 Spec - Tests Turn Management Method Updates Correctly
     */
    @Test
    public void testPlayerTurns() {
        
        assertEquals(1,matchGame.getTurnNo());
        assertEquals(player1,matchGame.getCurrentPlayerTurn());
        
        player1.placeChecker(2, 4);
        
        assertEquals(2,matchGame.getTurnNo());
        assertEquals(player2,matchGame.getCurrentPlayerTurn());
    }
    
    /**
     * Coursework 2 - player name changes
     * Tests Player name is set correctly
     * Tests player name changes correctly
     * Tests collection of players updates correctly
     * Tests Score of Player Resets to 0
     */
    @Test
    public void testChangePlayerName() {
        
        player1.placeChecker(0, 0);
        
        player2.placeChecker(0, 1);

        
        player1.placeChecker(1, 1);
        
        player2.placeChecker(2, 1);
        
        player1.placeChecker(2, 0);
        
        player2.placeChecker(1, 0); 
        
        player1.winner(); 
        
        matchGame.changePlayerName(1, "Alice");
        
        assertEquals("Alice",matchGame.getPlayers().get(1).getName());
        // Tests Name Changes Correctly
        
        assertEquals(0,player1.getScore());
        // Tests Name Change Resets Score
    }
}
