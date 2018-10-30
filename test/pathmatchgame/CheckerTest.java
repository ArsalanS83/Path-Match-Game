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
 * Tests Checker Objects on a Game
 * @author 105977
 */
public class CheckerTest {
    private Player player1;
    private Player player2;
    private Game game;
    
    public CheckerTest() {
    }
    
    
    @Before
    public void setUpMatchGame() {
        player1 = new Player("Player 1");
        player2 = new PathPlayer("Playe 2");
        
        game = new Game(8,8,player1,player2);
        
        player1.setGame(game);
        player2.setGame(game);
    }
    
    /**
     * Tests owner of Checker belongs to player who placed it
     */
    @Test
    public void testOwner() {
        player1.placeChecker(0, 0);
        
        assertTrue(player1.equals(game.getBoard()[0][0].getOwner()));
    }
    
    /**
     * Tests value of Checkers increment
     * Represents Move Numbers
     */
    @Test
    public void testCheckerValue() {
        player1.placeChecker(0, 0);
        
        assertEquals(1,game.getBoard()[0][0].getValue());
        
        player2.placeChecker(0, 1);
        
        assertEquals(2,game.getBoard()[0][1].getValue());
    }
}
