/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 * Represents High Score Data
 * High Scores are stored in a Collection
 * Displayed using a Table
 * @author 105977
 */
public class HighScoreData extends AbstractTableModel {
    
    private final ArrayList<HighScore> highScores = new ArrayList<>();
    private final String[] columnNames =  {"Player Name","High Score"};
    
    private static final String HIGH_SCORES = "highScores.txt";


    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
     public int getRowCount() {
        return highScores.size();
    }

    
    @Override
    public String getColumnName(int i) {
        return columnNames[i];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        HighScore h = highScores.get(rowIndex);
        
        if (columnIndex == 0) {
            return h.getPlayerName();
        }
        if (columnIndex == 1) {
            return h.getPlayerScore();
        }
        
        return null;
    }
    
    @Override
    public void setValueAt(Object value,int row, int column) {
        HighScore h = highScores.get(row);
        
        if (column ==0) {
            h.setPlayerName((String)value);
        }
        
        if (column == 1) {
            h.setPlayerScore((Integer)value);
        }
    }
    
    /**
     * Adds High Score to High Scores Collection
     * Sorts High Scores in Descending Order
     * @param h High Score to be added
     */
    public void addHighScore(HighScore h) {
        highScores.add(h);
        Collections.sort(highScores);
    }
    
    /**
     * Test Method - Gets High Scores Collection
     * @return High Scores Collection 
     */
    public ArrayList<HighScore> getHighScores() {
        return highScores;
    }
    
    /**
     * Searches & Updates High Score Collection
     * If given score of player is greater than existing score then update
     * @param name name of player to search
     * @param score score of player to search
     * @return true if High Scores were updated, false otherwise 
     */
    public boolean searchHigher(String name,int score) {
        
        Iterator<HighScore> highScoreIterator = highScores.iterator();
		while (highScoreIterator.hasNext()) {
                    HighScore h = highScoreIterator.next();
                    
                    if (h.getPlayerName().equals(name)) {
                        if (score > h.getPlayerScore()) {
                            highScoreIterator.remove();
                            return true;
                        }
                    }
                }
        return false; // player/ old score not found
    }
    
    /**
     * Searches High Scores for existing score or equal score of player
     * If given score is < existing score of player then do nothing
     * If given score is equal to existing score of player then do nothing
     * @param name name of player to search
     * @param score score of player to search
     * @return true if given score is lower than existing score or given score is equal to existing score
     */
    public boolean searchLowerandEqual(String name, int score)
    {
        Iterator<HighScore> highScoreIterator = highScores.iterator();
		while (highScoreIterator.hasNext())
                {
                    HighScore h = highScoreIterator.next(); 
                    
                    if (h.getPlayerName().equals(name)) {
                        if (score < h.getPlayerScore() || score == h.getPlayerScore()) {
                            return true;
                        }
                    }
                } 
        return false;
    }

    
    
    
    
    
   /********************** Saving Scores To File ***********************************************/
    
    /**
     * Processes each High Score in Collection
     * Writes each High Score to a Text File
     */
    public void saveScores() {
        
        try {
            try (BufferedWriter br = new BufferedWriter(new FileWriter(HIGH_SCORES))) {
                
                for (HighScore h : highScores) {
                    br.write(h.toString());
                    br.newLine();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HighScoreData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    
   /************************* Read File and Load Scores into Table ******************************/
    
    /**
     * Reads each High Score from a Text File
     * Processes each Line using format of High Score object
     * Uses Scanners and Delimeters to identify each Name and Score
     * 1) Error if No File exists
     * 2) Error if No Scores exist in file
     */
    public void loadScores() {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(HIGH_SCORES))) {
                String line;
                
                int rowNum = 0;
                
                while ((line = br.readLine()) != null) {
                    Scanner s = new Scanner(line);
                    s.useDelimiter("\\s*:\\s*");
                    String playerName = s.next();
                    setValueAt(playerName,rowNum,0);
                    String playerScore = s.next();
                    int score = Integer.parseInt(playerScore);
                    setValueAt(score,rowNum,1);
                    rowNum++;
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("Error File Not Found!");
        }
        catch (IOException e) {
            System.out.println("Error No Scores!");
        }
    }    
}