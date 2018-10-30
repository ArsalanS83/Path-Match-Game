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
 * Tests Random Player Functionality
 * @author 105977
 */
public class RandomPlayerTest {
    private Game game;
    private RandomPlayer player1;
    private PathPlayer player2;
    
    public RandomPlayerTest() {
    }
    
    @Before
    public void setUpGame() {
        player1 = new RandomPlayer("Player 1");
        player2 = new PathPlayer("Player 2");
        
        game = new Game(8,8,player1,player2);
        
        player1.setGame(game);
        player2.setGame(game);
    }
    
    /**
     * Tests Placing Initial Random Checker
     * Tests if Random Move is in range of Board
     * Tests if Owner of Random Move is Random Player
     * 
     */
    @Test
    public void testPlaceFirstRandomChecker()
    {
        Move initialMove = player1.generateRandomMove();
        
        int x= initialMove.getX();
        int y = initialMove.getY();
        
        // Checks if the Random Move is in range
        assertTrue(x < game.getHeight());
        assertTrue(y < game.getWidth());
        
        
        // Checks The Random Move has been Made
        assertNotNull(game.getBoard()[x][y]);
        
        // Check owner of Checker is Random Player
        Checker c = game.getBoard()[x][y];
        assertEquals(player1,c.getOwner());
        assertEquals(1,c.getValue());
    }
    
    /**
     * Tests Making a Random Move after Human Player
     */
    @Test
    public void testplaceRandomChecker()
    {
        player2.placeChecker(0, 0);
                
        // 1) Create Available Moves & Add To Player 1's Moves Collection
        // 2) Pick an Available Move at Random
        Move randomMove = player1.generateRandomMove();
        
        int x = randomMove.getX();
        int y = randomMove.getY();
        
        // Check that X coordinate of Random Move is Up/Down/Unchanged
        assertTrue(x == player2.getXPosition()+1 || x== player2.getXPosition()-1 || x== player2.getXPosition());
        
        // Check that Y coordinate of Random Move is Left/Right/Unchanged
        assertTrue(y == player2.getYPosition()+1 || y== player2.getYPosition()-1 || y== player2.getYPosition());
        
        // Check Random Move Was Successfull
        assertEquals(player1,game.getBoard()[x][y].getOwner());  
            
    }

    /**
     * Tests Random Player Has Won 
     */
    @Test
    public void testRandomPlayerWinner()
    {
        player2.placeChecker(0, 0);
        player1.placeChecker(1, 0);
        player2.placeChecker(2,0);
        player1.placeChecker(2,1);
        player2.placeChecker(2,2);
        player1.placeChecker(1,2);
        player2.placeChecker(0,2);
        player1.placeChecker(0,1);
        player2.placeChecker(1,1);
        
        // Generate a Random Move
        Move randomMove = player1.generateRandomMove();
        
        // There are no Available Moves
        assertNull(randomMove);
        
        assertTrue(game.isWinner(player1));
    }
}
