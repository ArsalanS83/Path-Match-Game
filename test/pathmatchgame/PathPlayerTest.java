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
 * Tests Players on a Path Game
 * Main Focus: Test Place Checker Method & Game IsLegal Method
 * @author 105977
 */
public class PathPlayerTest {
    private Game pathGame;
    private PathPlayer player1;
    private PathPlayer player2;
    
    public PathPlayerTest() {
    }
    
    @Before
    public void setUpPathGame() {
        player1 = new PathPlayer("Player 1");
        player2 = new PathPlayer("Player 2");
        
        pathGame = new Game(8,8,player1,player2);
        
        player1.setGame(pathGame);
        player2.setGame(pathGame);
    }
    
    /**
     * Tests that a Path Player's move is valid (adjacent)
     */
    @Test
    public void testPlaceCheckerValid() {
        player1.placeChecker(4, 4);
        
        assertTrue(player2.placeChecker(3, 4));
    }
    
    /**
     * Tests that a Path Player's move is invalid
     * Moves must be adjacent to the previous move
     * 1) Checks if move is legal (adjacent) to the previous one
     * 2) Checks if position is not blocked
     */
    @Test
    public void testPlaceCheckerInvalid() {
        
        player1.placeChecker(4, 4);  
        
        assertFalse(player2.placeChecker(4, 6));
        assertFalse(player2.placeChecker(4, 2));
        assertFalse(player2.placeChecker(2, 4));
        assertFalse(player2.placeChecker(7, 4));
        assertFalse(player2.placeChecker(3, 3));
        assertFalse(player2.placeChecker(3, 5));
        assertFalse(player2.placeChecker(5, 3));
        assertFalse(player2.placeChecker(5, 5));
    }
}
