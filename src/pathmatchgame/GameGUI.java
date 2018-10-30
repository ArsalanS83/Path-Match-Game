/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * Represents a Generic GUI for Both Games
 * @author 105977
 */
public class GameGUI extends JFrame {
    protected Player p1;
    protected Player p2;
    protected Game game;
    
    protected CheckerPanel[][] gameCells;
    private JPanel gameboard;
    private JPanel gamecontainer;
    
    private JTextField player1Name;
    private JTextField player1Score;
    private JTextField player2Name;
    private JTextField player2Score;
    private JTextField playerTurn;
    private final String playerTypes[] = {"Human","Random"};
    private JComboBox player1Type;
    private JComboBox player2Type;
    
    private HighScoreData highScoreData;
    private JTable highScoreTable;
    
    /**
     * Sets Up GUI
     * @param gamename name of game
     */
    public GameGUI(String gamename) {
        super (gamename);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setVisible(true);
    }
    
    /**
     * Creates a Match Game Model
     * @param x height of board
     * @param y width of board
     */
    public void createMatchGame(int x, int y) {
        p1 = new Player("Player 1");
        p2 = new PathPlayer("Player 2");
        game = new Game(x,y,p1,p2);
        p1.setGame(game);
        p2.setGame(game);
        gameCells = new CheckerPanel[game.getHeight()][game.getWidth()];
        setUpGame();
        setUpGameBoard();
        highScoreData = new HighScoreData();
        highScoreTable = new JTable (highScoreData);
        pack();
    }
    
    /**
     * Creates a Path Game Model
     * @param x height of board
     * @param y width of board
     */
    public void createPathGame(int x, int y) {
        p1 = new PathPlayer("Player 1");
        p2 = new PathPlayer("Player 2");
        game = new Game(x,y,p1,p2);
        p1.setGame(game);
        p2.setGame(game);
        gameCells = new CheckerPanel[game.getHeight()][game.getWidth()];
        setUpGame();
        setUpGameBoard();
        highScoreData = new HighScoreData();
        highScoreTable = new JTable (highScoreData);
        pack();
    }
    
     
    /**
     * Sets Up the Game Info
     * 1) Player Info 
     * 2) Score Info
     * 3) Turn Info
     */
    private void setUpGame() {
        
        getContentPane().setLayout(new BorderLayout());
        
        JPanel masterPanel= new JPanel();
        masterPanel.setLayout(new BorderLayout());
        masterPanel.add(setUpPlayerPanel(),BorderLayout.NORTH);
        masterPanel.add(setUpGameControls(),BorderLayout.CENTER);
        masterPanel.add(setUpTurnInfo(),BorderLayout.SOUTH);
        
        getContentPane().add(masterPanel,BorderLayout.NORTH);
    }
    
    /**
     * Sets Up The Player Panel
     * 1) Player Names
     * 2) Player Types
     * 3) Player Scores
     * @return Component that generates this area 
     */
    private Component setUpPlayerPanel() {
        JPanel playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(3,3));
        
        JLabel playerinfo = new JLabel("Players");
        playerPanel.add(playerinfo);
        
        JLabel playertypeinfo = new JLabel("Player Type");
        playerPanel.add(playertypeinfo);
        
        JLabel scoreinfo = new JLabel("Score");
        playerPanel.add(scoreinfo);

        player1Name = new JTextField(p1.getName());
        player1Name.setForeground(Color.RED);
        player1Name.setEditable(false);
        playerPanel.add(player1Name);
        
        // Set Up Player 1 Type Box
        player1Type = new JComboBox(playerTypes);
        player1Type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (player2Type.getSelectedItem().equals("Random")) {
                    JOptionPane.showMessageDialog(getContentPane(),"Can't Have 2 Random Players!");
                    player1Type.setSelectedItem("Human");
                }
                else
                {
                    if (player1Type.getSelectedItem().equals("Random"))
                    {
                    p1 = new RandomPlayer("Game Master 1");
                    game.getPlayers().put(1, p1);
                    p1.setGame(game);
                    player1Name.setText(p1.getName());
                    // Create The First Random Move
                    Move randomMove = ((RandomPlayer)p1).generateRandomMove();
                    createChecker();
                    gameCells[randomMove.getX()][randomMove.getY()].setBackground(Color.RED);
                    playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                    }
                }
                // if player 1 decides to change back to a human
                if (player1Type.getSelectedItem().equals("Human"))
                {
                    // if player 1 is a random player
                    // then create a new human player
                    if (p1 instanceof RandomPlayer)
                    {
                        p1 = new PathPlayer("Player 1");
                        game.getPlayers().put(1, p1);
                        p1.setGame(game);
                        player1Name.setText(p1.getName()); 
                        player1Score.setText("0");
                    }
                }
            }
        }); 
        
    
        playerPanel.add(player1Type);
            
        // Set Up Player 1 Score Area
        player1Score = new JTextField(String.valueOf(p1.getScore()));
        player1Score.setForeground(Color.RED);
        player1Score.setEditable(false);
        playerPanel.add(player1Score);
        
        // Set Up Player 2 Name Area
        player2Name = new JTextField(p2.getName());
        player2Name.setEditable(false);
        player2Name.setForeground(Color.BLUE);
        playerPanel.add(player2Name);
        
        // Set Up Player 2 Type Box
        player2Type = new JComboBox(playerTypes);
        player2Type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (player1Type.getSelectedItem().equals("Random")) {
                    JOptionPane.showMessageDialog(getContentPane(),"Can't Have 2 Random Players!");
                    player2Type.setSelectedItem("Human");
                    p2.setName("Player 2");
                    player2Name.setText(p2.getName());
                }
                else {
                    if (player2Type.getSelectedItem().equals("Random")) {
                        p2 = new RandomPlayer("Game Master 2");
                        game.getPlayers().put(2, p2);
                        p2.setGame(game);
                        player2Name.setText(p2.getName());
                        // Create The First Random Move
                        Move randomMove = ((RandomPlayer)p2).generateRandomMove();
                        createChecker();
                        gameCells[randomMove.getX()][randomMove.getY()].setBackground(Color.BLUE);
                        playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                    }
                }
                // if player 2 decidees to change back to human
                if (player2Type.getSelectedItem().equals("Human"))
                {
                    // if player 2 is a random player
                    // then create a new human player
                    if (p2 instanceof RandomPlayer)
                    {
                        p2 = new PathPlayer("Player 2");
                        game.getPlayers().put(2, p2);
                        p2.setGame(game);
                        player2Name.setText(p2.getName());
                        player2Score.setText("0");
                    }
                }
            }
        }); 
        
        playerPanel.add(player2Type);
        
        player2Score = new JTextField(String.valueOf(p2.getScore()));
        player2Score.setEditable(false);
        player2Score.setForeground(Color.BLUE);
        playerPanel.add(player2Score);
        
        return playerPanel;
}
    
    /**
     * Set Up Game Control Buttons
     * 1) Editing of Player Names
     * 2) Starting a New Game
     * 3) Showing High Scores
     * @return Component that generates this area
     */
    private Component setUpGameControls() {
        JPanel gamecontrols = new JPanel();
        gamecontrols.setLayout(new GridLayout(2,2));
        
        JButton editP1 = new JButton("Edit Player 1 Name");
        editP1.addActionListener((ActionEvent e) -> {
            editPlayer1();
        });
        gamecontrols.add(editP1);
        
        JButton editP2 = new JButton("Edit Player 2 Name");
        editP2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                editPlayer2();
            }
        });
        gamecontrols.add(editP2);
        
        JButton newgame = new JButton("New Game");
        newgame.addActionListener(new GameGUI.resetActionListener());
        gamecontrols.add(newgame);
        
        JButton highScores = new JButton("Show High Scores");
        
        
        highScores.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFrame scores = new JFrame("High Scores"); 
                    scores.setLayout(new BorderLayout());
                    JScrollPane scrollPane = new JScrollPane(highScoreTable);
                    scores.getContentPane().add(scrollPane,BorderLayout.CENTER);
                    highScoreTable = new JTable(highScoreData);
                    highScoreData.loadScores();
                    scores.setVisible(true);
                    scores.pack();
                }
                catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(getContentPane(),"No High Scores!");
                }
            }
            
        });
        gamecontrols.add(highScores);
        
        return gamecontrols;
    }
         
    
    /**
     * Coursework 2 - Sets Up Turn Management Box
     * Generates a Text Box that contains Current Player Turn
     * @return Component that sets up this area 
     */
    private Component setUpTurnInfo() {
        playerTurn = new JTextField("Current Player: " + game.getCurrentPlayerTurn().getName());
        return playerTurn;
    }
    
    

    /**
    * Creates the Game Board
    */
    private void setUpGameBoard() {
        gamecontainer = new JPanel();
        
        gameboard = new JPanel();
        gameboard.setLayout(new GridLayout(game.getHeight(),game.getWidth()));
        
        for (int i = 0; i<game.getHeight(); i++)
        {
            for (int j = 0; j<game.getWidth(); j++)
            {
                gameCells[i][j] = new CheckerPanel(i,j);
                gameCells[i][j].setBorder(BorderFactory.createLineBorder(Color.black,2));
                gameCells[i][j].setBackground(Color.DARK_GRAY);
                gameCells[i][j].setPreferredSize(new Dimension(80,65));
                gameCells[i][j].addMouseListener(new GameGUI.GameController());
                gameboard.add(gameCells[i][j]);
            }
        }
        gamecontainer.add(gameboard);
        getContentPane().add(gamecontainer,BorderLayout.CENTER);
    }
        
    /**
     * Creates a Graphical Checker on the board
     */
    private void createChecker() {
                         
        Player p = game.getRecentMove();
        int x = p.getXPosition();
        int y = p.getYPosition();
        
        int num = game.getBoard()[x][y].getValue();
        JLabel checker = new JLabel(String.valueOf(num));
        
        gameCells[x][y].add(checker);
        pack();
    }
    
    /**
     * Resets The Game
     * 1) Resets Model
     * 2) Removes Components from Graphical Board
     * 3) Calls Set Up Board Again
     */
    private void resetGame() {
        game.resetGame();
        gamecontainer.repaint();
        gameboard.repaint();
        
        for (int i = 0; i<game.getHeight(); i++)
        {
            for (int j = 0; j<game.getWidth(); j++)
            {
              gameCells[i][j].removeAll();
              gameCells[i][j].setBackground(Color.DARK_GRAY);
            }
        }
        setUpGameBoard();
        playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
    }
    
    /**
     * Creates an External Window allowing Editing of Player 1's Name
     */
    private void editPlayer1() {
        
        if (player1Type.getSelectedItem().equals("Random")) {
            JOptionPane.showMessageDialog(getContentPane(),"Random Player Names can't be changed!");
        }
        else {
        JFrame frame = new JFrame("Player 1 Name");
        frame.setLayout(new GridLayout(2,2));
        
        JLabel enterName = new JLabel("Enter New Player 1 Name:");
        frame.getContentPane().add(enterName);
        
        JTextField p1text = new JTextField(p1.getName());
        frame.getContentPane().add(p1text);
        
        JButton start = new JButton("OK");
        frame.getContentPane().add(start);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                p1.setName(p1text.getText());
                player1Name.setText(p1text.getText());
                p1.setScore(0);
                player1Score.setText("0");
                player2Score.setText("0");
                playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                frame.dispose();
            }
        });
        frame.pack();
        frame.setVisible(true);
        }
    }
    
    /**
     * Creates an External Window allowing Editing of Player 2's Name
     */
    private void editPlayer2() {
        
        if (player2Type.getSelectedItem().equals("Random")) {
            JOptionPane.showMessageDialog(getContentPane(),"Random Player Names can't be changed!");
        }
        else {
        JFrame frame = new JFrame("Player 2 Name");
        frame.setLayout(new GridLayout(2,2));
        
        JLabel enterName = new JLabel("Enter New Player 2 Name:");
        frame.getContentPane().add(enterName);
        
        JTextField p2text = new JTextField(p2.getName());
        frame.getContentPane().add(p2text);
        
        JButton start = new JButton("OK");
        frame.getContentPane().add(start);
        start.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                p2.setName(p2text.getText());
                player2Name.setText(p2text.getText());
                p2.setScore(0);
                player2Score.setText("0");
                player1Score.setText("0");
                playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                frame.dispose();
            }
        });
        frame.pack();
        frame.setVisible(true);
        }
    }
    
    
    
        
    /****************** Manages Game Actions ***************/
    
    class GameController implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            CheckerPanel p = (CheckerPanel) e.getSource();
            int x = p.getPanelX();
            int y = p.getPanelY();
            
            if (p1.equals(game.getCurrentPlayerTurn()))
            {
                if (p1.placeChecker(x, y))
                {
                    createChecker();
                    gameCells[x][y].setBackground(Color.RED);
                    // Coursework 2 Spec - Player is Told when it is their turn
                    playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                    playerTurn.setEditable(false);
                    
                    // If Player 2 is Random then generates its Move
                    if (player2Type.getSelectedItem().equals("Random"))
                    {
                        Move randomMove = ((RandomPlayer)p2).generateRandomMove();
                        
                        // If Player 1 Has Lost
                        if (randomMove == null && game.getCurrentPlayerTurn() == p2)
                        {
                            JOptionPane.showMessageDialog(getContentPane(),player2Name.getText() + " Wins!");
                            p2.winner();
                            player2Score.setText(String.valueOf(p2.getScore()));
                              
                            if (highScoreData.getHighScores().isEmpty())
                            {
                                highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                                highScoreData.saveScores();
                            }
                            else
                            {
                                   // if the score is higher than player 2's old score then search and update the collection
                                if (highScoreData.searchHigher(p2.getName(),p2.getScore()))
                                {
                                    highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                                    highScoreData.saveScores();
                                }
                                else
                                {
                                      // if the score is lower or equal than the player 2's old score then don't add it
                                    if (!highScoreData.searchLowerandEqual(p2.getName(),p2.getScore()))
                                    {
                                        highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));                                          
                                        highScoreData.saveScores();
                                    }
                                } 
                            } 
                        }
                        else
                        {
                            // If Player 1 Hasn't Lost Then Process Graphical Representation of Random Move
                            createChecker();
                            gameCells[randomMove.getX()][randomMove.getY()].setBackground(Color.BLUE);
                            playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                            
                            if (game.isWinner(p1))
                            {
                                JOptionPane.showMessageDialog(getContentPane(),player1Name.getText() + " Wins!");
                                p1.winner();
                                player2Score.setText(String.valueOf(p1.getScore()));
                            
                            if (highScoreData.getHighScores().isEmpty())
                            {
                                highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                                highScoreData.saveScores();
                            }
                            else
                            {
                                 // if the score is higher than player 1's old score then search and update the collection
                                if (highScoreData.searchHigher(p1.getName(),p1.getScore()))
                                {
                                    highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                                    highScoreData.saveScores();
                                }
                                else
                                {
                                     // if the score is lower or equal than the player 1's old score then don't add it
                                    if (!highScoreData.searchLowerandEqual(p1.getName(),p1.getScore()))
                                    {
                                        highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));                                          
                                        highScoreData.saveScores();
                                    }
                                } 
                            }  
                            } 
                        }
                    }
                    else
                    {

                    // If Player 2 Isn't a Random Player
                    // Check if Player 2 Has Won
                    if (game.isWinner(p2))
                    {
                      JOptionPane.showMessageDialog(getContentPane(),player2Name.getText() + " Wins!");
                      p2.winner();
                      player2Score.setText(String.valueOf(p2.getScore()));
                      
                      if (highScoreData.getHighScores().isEmpty())
                      {
                         highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                         highScoreData.saveScores();
                      }
                      else
                      {
                            // if the score is higher than player 2's old score then search and update the collection
                          if (highScoreData.searchHigher(p2.getName(),p2.getScore()))
                          {
                              highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                              highScoreData.saveScores();
                          }
                          else
                          {
                                // if the score is lower or equal than the player 2's old score then don't add it
                              if (!highScoreData.searchLowerandEqual(p2.getName(),p2.getScore()))
                              {
                                  highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));                                          
                                  highScoreData.saveScores();
                              }
                          }
                      }
                    }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(getContentPane(),"Sorry " + player1Name.getText() + " can't go here!"); 
                }       
            }
            else
            {
                 if (p2.placeChecker(x, y))
                 {
                     createChecker();
                     gameCells[x][y].setBackground(Color.BLUE); 
                     // Coursework 2 Spec - Player is Told when it is their turn
                     playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                    
                    // If Player 1 Is a Random Player
                    if (player1Type.getSelectedItem().equals("Random"))
                    {
                        Move randomMove = ((RandomPlayer)p1).generateRandomMove();
                        
                        // If Player 2 Has Lost
                        if (randomMove == null && game.getCurrentPlayerTurn() == p1)
                        {
                            JOptionPane.showMessageDialog(getContentPane(),player1Name.getText() + " Wins!");
                            p1.winner();
                            player1Score.setText(String.valueOf(p1.getScore()));
                            
                            if (highScoreData.getHighScores().isEmpty())
                            {
                                highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                                highScoreData.saveScores();
                            }
                            else
                            {
                                 // if the score is higher than player 1's old score then search and update the collection
                                if (highScoreData.searchHigher(p1.getName(),p1.getScore()))
                                {
                                    highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                                    highScoreData.saveScores();
                                }
                                else
                                {
                                    // if the score is lower or equal than the player 1's old score then don't add it
                                    if (!highScoreData.searchLowerandEqual(p1.getName(),p1.getScore()))
                                    {
                                        highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));                                          
                                        highScoreData.saveScores();
                                    }
                                } 
                            } 
                        }
                 
                        else
                        {                           
                            createChecker();
                            gameCells[randomMove.getX()][randomMove.getY()].setBackground(Color.RED);
                            playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
                            
                            
                            if (game.isWinner(p2))
                            {
                                JOptionPane.showMessageDialog(getContentPane(),player2Name.getText() + " Wins!");
                                p2.winner();
                                player2Score.setText(String.valueOf(p2.getScore()));
                            
                            if (highScoreData.getHighScores().isEmpty())
                            {
                                highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                                highScoreData.saveScores();
                            }
                            else
                            {
                                // if the score is higher than player 2's old score then search and update the collection
                                if (highScoreData.searchHigher(p2.getName(),p2.getScore()))
                                {
                                    highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));
                                    highScoreData.saveScores();
                                }
                                else
                                {
                                     // if the score is lower or equal than the player 2's old score then don't add it
                                    if (!highScoreData.searchLowerandEqual(p2.getName(),p2.getScore()))
                                    {
                                        highScoreData.addHighScore(new HighScore(p2.getName(),p2.getScore()));                                          
                                        highScoreData.saveScores();
                                    }
                                }                              
                            }  
                            }
                        }
                    }
                    else
                    {
                     
                     // If Player 1 isn't Random
                     if (game.isWinner(p1))
                     {
                         JOptionPane.showMessageDialog(getContentPane(),player1Name.getText() + " Wins!");
                         p1.winner();
                         player1Score.setText(String.valueOf(p1.getScore()));
                      
                      if (highScoreData.getHighScores().isEmpty())
                      {
                         highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                         highScoreData.saveScores();
                      }
                      else
                      {
                          // if the score is higher than player 1's old score then search and update the collection
                          if (highScoreData.searchHigher(p1.getName(),p1.getScore()))
                          {
                              highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));
                              highScoreData.saveScores();
                          }
                          else
                          {
                              // if the score is lower or equal than the player 1's old score then don't add it
                              if (!highScoreData.searchLowerandEqual(p1.getName(),p1.getScore()))
                              {
                                  highScoreData.addHighScore(new HighScore(p1.getName(),p1.getScore()));                                          
                                  highScoreData.saveScores();
                              }
                          } 
                      }
                     }
                }
            }
                 else
                 {
                    JOptionPane.showMessageDialog(getContentPane(),"Sorry " + player2Name.getText() + " can't go here!"); 
                 }   
        }
        }
        
        /******* Rest Of Methods Not Needed ********////
        
        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
 
        }
        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    
    /******** Handles New Game Button *********/
    
    class resetActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e)
        {
            resetGame();
            
            // If There is a Random Player Then Make Human Player Go First
            if (player1Type.getSelectedItem().equals("Random"))
            {
                game.nextPlayerTurn();
                playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
            }
            
            if (player2Type.getSelectedItem().equals("Random"))
            {
                game.nextPlayerTurn();
                game.nextPlayerTurn();
                playerTurn.setText("Current Player: " + game.getCurrentPlayerTurn().getName());
            }
        }
    }
}