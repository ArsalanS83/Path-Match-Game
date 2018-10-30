/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pathmatchgame;

/**
 * Responsible for Match Game UI
 * Represents a General "Game"
 * @author 105977
 */
public class MatchGameGUI extends GameGUI  {

    /**
     * Creates a Match Game
     * @param x Height of Board
     * @param y Width of Board
     */
    public MatchGameGUI(int x, int y) {
        super("Match Game");
        createMatchGame(x,y);
    }
}
