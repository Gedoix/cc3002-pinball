package controller;

import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import logic.table.DefaultTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    private long seed = 1712567478;

    @Before
    public void setUp() {
        game = new Game(this.seed);
    }

    @Test
    public void getExtraBallBonus() {
        ExtraBallBonus expected = new ExtraBallBonus();
        ExtraBallBonus actual = game.getExtraBallBonus();
        assertEquals(expected, actual);
        assertSame(actual, game.getExtraBallBonus());
    }

    @Test
    public void getJackPotBonus() {
        JackPotBonus expected = new JackPotBonus();
        JackPotBonus actual = game.getJackPotBonus();
        assertEquals(expected, actual);
        assertSame(actual, game.getJackPotBonus());
    }

    @Test
    public void getDropTargetBonus() {
        DropTargetBonus expected = new DropTargetBonus();
        DropTargetBonus actual = game.getDropTargetBonus();
        assertEquals(expected, actual);
        assertSame(actual, game.getDropTargetBonus());
    }

    @Test
    public void getTable() {
        Table table = new DefaultTable("hello world", 1, 2, 3, 4);
        game.setTable(table);
        assertEquals(table, game.getTable());
    }

    @Test
    public void isGameOver() {
        assertFalse(game.isGameOver());
        for(int i = 0; i < game.getBallCounter(); i++) {
            game.removeBall();
        }
        assertTrue(game.isGameOver());
    }

    @Test
    public void isPlayableTable() {
    }

    @Test
    public void getTableName() {
    }

    @Test
    public void getBumpers() {
    }

    @Test
    public void getTargets() {
    }

    @Test
    public void setTable() {
    }

    @Test
    public void createRandomTableNoTargets() {
    }

    @Test
    public void createRandomTable() {
    }

    @Test
    public void addPoints() {
        int before = game.getScore();
        game.addPoints(123);
        assertEquals(before+123, game.getScore());
        before = game.getScore();
        for (int i = 0; i < 20; i++) {
            game.addPoints(100+i);
            assertEquals(before+100+i, game.getScore());
            before = game.getScore();
        }
    }

    @Test
    public void addBall() {
        int before = game.getBallCounter();
        game.addBall();
        assertEquals(before+1, game.getBallCounter());
        for (int i = 0; i < 20; i++) {
            game.addBall();
        }
        assertEquals(before+21, game.getBallCounter());
    }

    @Test
    public void removeBall() {
        for (int i = 0; i < 20; i++) {
            game.addBall();
        }
        int before = game.getBallCounter();
        game.removeBall();
        assertEquals(before-1, game.getBallCounter());
        for (int i = 0; i < 20; i++) {
            game.removeBall();
        }
        assertEquals(before-21, game.getBallCounter());
    }

    @Test
    public void triggerExtraBallBonus() {
        int before = game.getBallCounter();
        game.triggerExtraBallBonus();
        assertEquals(before+1, game.getBallCounter());
        for (int i = 0; i < 20; i++) {
            game.triggerExtraBallBonus();
        }
        assertEquals(before+21, game.getBallCounter());
    }

    @Test
    public void triggerJackPotBonus() {
        int before = game.getScore();
        game.triggerJackPotBonus();
        assertEquals(before+100000, game.getScore());
        for (int i = 0; i < 20; i++) {
            game.triggerJackPotBonus();
        }
        assertEquals(before+2100000, game.getScore());
    }

    @Test
    public void upgradeAllBumpers() {
        Table table = new DefaultTable("hello world", 1, 2, 3, 4);
        game.setTable(table);
        for(Bumper bumper : game.getBumpers()) {
            assertFalse(bumper.isUpgraded());
        }
        game.upgradeAllBumpers();
        for(Bumper bumper : game.getBumpers()) {
            assertTrue(bumper.isUpgraded());
        }
    }

    @Test
    public void update() {
        Table table = new DefaultTable("hello world", 1, 2, 3, 4);
        game.setTable(table);
        for(Target target : game.getTargets()) {
            assertTrue(target.isActive());
        }
        for(Target target : game.getTargets()) {
            game.hit(target);
        }
        int before = game.getScore();
        for(Target target : game.getTargets()) {
            assertFalse(target.isActive());
        }
        game.update();
        assertEquals(before+1000000, game.getScore()); //fails
    }

    @Test
    public void hitTargets() {
        Table table = new DefaultTable("hello world", 50, 50, 5, 100);
        game.setTable(table);
        int before_score;
        int before_balls;

        int hit_score;

        List<Hittable> list = new ArrayList<>();
        list.addAll(game.getTargets());

        int random_counter = 0;
        int random_extra_score;
        int random_extra_balls;
        int[] exceptions = {10, 11, 15, 20, 31, 32, 34, 36, 37, 40, 43, 48, 51, 56, 57, 60, 61, 62, 64, 68, 71, 75, 77,
                80, 81, 84, 90, 96, 99, 101, 104};

        for(Hittable hittable : list) {
            random_counter++;

            before_score = game.getScore();
            before_balls = game.getBallCounter();

            assertFalse(hittable.wasHit());

            hit_score = hittable.getScore();

            game.hit(hittable);

            assertTrue(hittable.wasHit());

            if(hittable instanceof SpotTarget) {
                random_extra_score = 100000;
            }
            else {

                random_extra_score = 0;
            }

            if(contains(exceptions, random_counter)) {
                random_extra_balls = 1;
            }
            else {

                random_extra_balls = 0;
            }

            System.out.println(random_counter);

            assertEquals(before_score+hit_score+random_extra_score, game.getScore());

            assertEquals(before_balls+random_extra_balls, game.getBallCounter());
        }
    }

    @Test
    public void hitBumpers() {
        Table table = new DefaultTable("hello world", 50, 50, 5, 100);
        game.setTable(table);
        int before_score;
        int before_balls;

        int hit_score;

        List<Hittable> list = new ArrayList<>();
        list.addAll(game.getBumpers());

        int random_counter = 0;
        int random_extra_score;
        int random_extra_balls;
        int[] exceptions = {10, 11, 15, 20, 31, 32, 34, 36, 37, 40, 43, 48, 51, 56, 57, 60, 61, 62, 64, 68, 71, 75, 77,
                80, 81, 84, 90, 96, 99, 101, 104};

        for(Hittable hittable : list) {
            random_counter++;

            before_score = game.getScore();
            before_balls = game.getBallCounter();

            assertFalse(hittable.wasHit());

            hit_score = hittable.getScore();

            game.hit(hittable);

            assertTrue(hittable.wasHit());

            if(hittable instanceof SpotTarget) {
                random_extra_score = 100000;
            }
            else {

                random_extra_score = 0;
            }

            if(contains(exceptions, random_counter)) {
                random_extra_balls = 1;
            }
            else {

                random_extra_balls = 0;
            }

            System.out.println(random_counter);

            assertEquals(before_score+hit_score+random_extra_score, game.getScore());

            assertEquals(before_balls+random_extra_balls, game.getBallCounter());
        }
    }

    boolean contains(int[] array, int number) {
        for(int element : array) {
            if(element == number) {
                return true;
            }
        }
        return false;
    }
}