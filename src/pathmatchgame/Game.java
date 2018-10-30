/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

import java.util.Stack;
import java.util.HashMap;

/**
 * Represents a Game
 * Could be a Match Game or a Path Game
 * @author 105977
 */
public class Game {
    private final Checker[][] board;
    private final int boardHeight;
    private final int boardWidth;
    private final Stack<Player> moves; // stack of moves, records who made move
    private final HashMap<Integer,Player> players; // collection of players
    private int turnNo;
    
    /**
     * Creates a Game
     * @param x height of board
     * @param y width of board
     * @param p1 Type of Player 1
     * @param p2 Type of Player 2
     */
    public Game(int x, int y,Player p1, Player p2)
    {
        board = new Checker[x][y];
        boardHeight = x;
        boardWidth = y;
        players = new HashMap();
        moves = new Stack();
        
        players.put(1,p1);
        players.put(2, p2); 
       
        turnNo = 1;
    }
    
    /**
     * Gets the game board
     * @return The game board 2d array
     */
    public Checker[][] getBoard() {
        return board;
    }
     
    /**
     * Records Game Moves
     * Puts a Player's State in the Stack
     * Player State contains its current X and Y Position i.e. It's Move
     * @param p Player to be inserted
     */
    public void recordMove(Player p) {
        moves.push(p);
    }
    
    /**
     * Returns most recent Player's State
     * Player's State includes move
     * @return Most Recent Player in collection of moves
     */
    public Player getRecentMove() {
        return moves.peek();
    }
     
    /**
     * Checks if the collection of moves is empty
     * i.e. Checks the game has not begun
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return moves.empty();
    }
    
    /**
     * Gets the Height of the Board
     * @return height of board
     */
    public int getHeight() {
        return boardHeight;
    }
    
    /**
     * Gets the Width of the Board
     * @return width of board
     */
    public int getWidth() {
        return boardWidth;
    }
    
    /**
     * Checks if a move is legal in a Path Game
     * 1) Checks if coordinates are adjacent
     * 2) Checks if position is not filled
     * @param x x coordinate to check
     * @param y y coordinate to check
     * @return true if move is legal, false otherwise
     */
    public boolean isLegal(int x, int y)
    {
               
        Player p = getRecentMove();
        
        int playerx = p.getXPosition();
        int playery = p.getYPosition();
        
        if (x < playerx-1 || x > playerx+1)
        {
            return false;
        }
        
        if (y < playery-1 || y > playery+1)
        {
            return false;
        }
        
        if (x == playerx-1 && y == playery-1)
        {
            return false;
        }
        
        if (x == playerx-1 && y == playery+1)
        {
            return false;
        }
        
        if (x == playerx+1 && y == playery-1)
        {
            return false;
        }
        
        if (x == playerx+1 && y == playery+1)
        {
            return false;
        }        
        return board[x][y] == null;
    }
    
    /**
     * Checks if a given Player is the winner of a game
     * @param p Player to check
     * @return true if p is winner, false otherwise
     */
    public boolean isWinner(Player p) {
        
        Player lastPlayer = getRecentMove();
        
        int x = lastPlayer.getXPosition();
        int y = lastPlayer.getYPosition();
        
        if (x==0 && y== 0)
        {
            if (board[x+1][y] != null & board[x][y+1] != null)
            {
                return true;
            }
            return false;
        }
        
        if (x!=0 && x!= boardHeight-1 && y==0)
        {
            if (board[x][y+1] != null && board[x+1][y] != null && board[x-1][y] != null)
            {
                if (lastPlayer.equals(p)) 
                {
                    // if player to check just made the recent move
                    // then they have lost the game
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        
        if (x== boardHeight-1 && y== 0)
        {
            if (board[x][y+1] != null && board[x-1][y] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        if (x== boardHeight-1 && y!= 0 && y!= boardWidth-1)
        {
            if (board[x-1][y] != null && board[x][y+1] != null && board[x][y-1] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        if (x== boardHeight-1 && y== boardWidth-1)
        {
            if (board[x-1][y] != null && board[x][y-1] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        if (x!= boardHeight-1 && y== boardWidth-1 && x!= 0)
        {
            if (board[x-1][y] != null && board[x+1][y] != null && board[x][y-1] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        if (x == 0 && y== boardWidth-1)
        {
            if (board[x+1][y] != null && board[x][y-1] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        
        if (x!= boardHeight-1 && y!= boardHeight-1 && x== 0)
        {
            if (board[x][y+1] != null && board[x][y-1] != null && board[x+1][y] != null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if (board[x+1][y] != null && board[x-1][y] != null)
            {
                if (board[x][y+1] != null && board[x][y-1] != null)
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            else
            {
                return false;
            }
        }                       
}
    
    /**
     * Resets the Game Board i.e. Resets the Game
     * 1) Sets all Checker objects in board to null
     * 2) Resets Turn Number to Player 1
     * 3) Clears Stack of Moves
     */
    public void resetGame() {
        for (int i = 0; i<boardHeight; i++)
        {
            for (int j = 0; j<boardWidth; j++)
            {
                board[i][j] = null;
            }
        }
        turnNo = 1;
        moves.clear();
    }
    
    /**
     * Sets the next player's turn
     */
    public void nextPlayerTurn() {
        
        if (turnNo == 1)
        {
            turnNo = 2;
        }
        else
        {
            turnNo = 1;
        }
    }
    
    /**
     * Returns the Player whose turn it is
     * @return Player whose turn it is
     */
    public Player getCurrentPlayerTurn() {
        return players.get(turnNo);
    }
    
    /**
     * Returns the current Turn Number
     * @return Current Turn Number
     */
    public int getTurnNo() {
        return turnNo;
    }
    
    /**
     * Gets Collection of Players
     * Coursework 2 - Used to for testing name changes
     * @return collection of players
     */
    public HashMap<Integer,Player> getPlayers() {
        return players;
    }
    
    /**
     * Coursework 2 - Changes a Player's Name and Resets Player's Score to 0
     * Updates the Collection of players
     * @param playerNum Player number to be changed
     * @param name new name to be changed to 
     */
    public void changePlayerName(int playerNum,String name) {
            players.get(playerNum).setName(name);
            players.get(playerNum).setScore(0);
        }
}
