package controller;

import logic.table.Table;

/**
 * Game logic controller class.
 *
 * @author Juan-Pablo Silva
 */
public class Game {

    private int ball_counter;
    private int score;
    private Table table;

    public void addBall() {
        ball_counter++;
    }

    public void addPoints(int points) {
        score += points;
    }

    public void upgradeAllBumpers() {
        table.upgradeAllBumpers();
    }
}
