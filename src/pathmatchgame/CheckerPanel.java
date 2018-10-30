/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

import javax.swing.JPanel;

/**
 * Represents Panels on a Game Board
 * In effect represents each cell on the Graphical Board
 * @author 105977
 */
public class CheckerPanel extends JPanel {
    private final int x;
    private final int y;
    
    /**
     * Creates a Checker Panel
     * Coordinates are stored to allow for later access
     * and use in obtaining which panel was clicked for a MouseListener
     * @param x x coordinate
     * @param y y coordinate
     */
    public CheckerPanel(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * Get x Coordinate of JPanel
     * @return x coordinate of JPanel
     */
    public int getPanelX() {
        return x;
    }
    
    /**
     * Get y Coordinate of JPanel
     * @return y coordinate of JPanel
     */
    public int getPanelY() {
        return y;
    }
}
