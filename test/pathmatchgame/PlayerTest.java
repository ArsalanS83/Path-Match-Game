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
 * Tests Players on a Match Game
 * @author 105977
 */
public class PlayerTest {
    private Game game;
    private Player player1;
    private PathPlayer player2;

    
    public PlayerTest() {
    }
    
    @Before
    public void setUpMatchGame() {  
         
        player1 = new Player("Player 1");
        player2 = new PathPlayer("Player 2");
        
        game = new Game(8,8,player1,player2);
        
        player1.setGame(game);
        player2.setGame(game);
    }
    
    /**
     * Tests placing the first checker on the board
     * 1) Does the Checker being placed belong to the player
     * 2) Do the coordinates match?
     */
    @Test
    public void testPlaceFirstChecker() {
        player1.placeChecker(0, 0);
                
        assertEquals(player1.getXPosition(),game.getBoard()[0][0].getOwner().getXPosition());
        assertEquals(player1.getYPosition(),game.getBoard()[0][0].getOwner().getYPosition());
    }
    
    /**
     * Test Match Player rules
     * 1) Can the player place a checker not adjacent to the previous?
     * 2) Has this been correctly placed
     */
    @Test
    public void testPlaceCheckerMatchPlayerValid() {
        
        player1.placeChecker(0, 0);
        
        player2.placeChecker(1, 0);
        
        player1.placeChecker(3, 2);
        
        assertEquals(player1.getXPosition(),game.getBoard()[3][2].getOwner().getXPosition());
        assertEquals(player1.getYPosition(),game.getBoard()[3][2].getOwner().getYPosition());
        
    }
    
    /**
     * Tests Player cannot place a Checker in a blocked position
     */
    @Test
    public void testPlaceCheckerBlocked() {
        
        player1.placeChecker(4, 4);
        
        player2.placeChecker(3, 4);
 
        assertFalse("Position Blocked",player1.placeChecker(3, 4));
    } 
    
    /**
     * Coursework 2 - Tests Player's name changes correctly
     */
    public void testSetName() {
        player1.setName("Bob");
        assertEquals("Bob",player1.getName());
    }
}
