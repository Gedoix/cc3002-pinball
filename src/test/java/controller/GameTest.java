package controller;

import logic.bonus.DropTargetBonus;
import logic.bonus.ExtraBallBonus;
import logic.bonus.JackPotBonus;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import logic.table.DefaultTable;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        long seed = 1712567478;
        game = new Game(seed);
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
        IntStream.range(0, game.getBallCounter()).forEach(i -> game.removeBall());
        assertTrue(game.isGameOver());
    }

    @Test
    public void getTableName() {
        Table table = new DefaultTable("hello world", 1, 2, 3, 4);
        game.setTable(table);
        assertEquals("hello world", game.getTableName());
    }

    @Test
    public void getBumpers() {
        Table table = new DefaultTable("hello world", 123, 456, 17, 13);
        game.setTable(table);
        List<Bumper> bumpers = game.getBumpers();
        assertEquals(123+456, bumpers.size());
    }

    @Test
    public void getTargets() {
        Table table = new DefaultTable("hello world", 123, 456, 17, 13);
        game.setTable(table);
        List<Target> targets = game.getTargets();
        assertEquals(17+13, targets.size());
    }

    @Test
    public void createRandomTableNoTargets() {
        Table table = game.createRandomTableNoTargets("hello world",  1000, 0.5);
        game.setTable(table);
        assertEquals(table, game.getTable());
        assertTrue(game.isPlayableTable());
        assertEquals(0, game.getTargets().size());
        int pop_bumper_exceptions_start_at = 525;
        //  475/1000 cases are pop bumpers, which is acceptable as a 0.5 probability.
        int random_counter = 0;
        checkRandomBumpers(random_counter, pop_bumper_exceptions_start_at);
    }

    @Test
    public void createRandomTable() {
        Table table = game.createRandomTable("hello world",  1000, 0.5, 10, 10);
        game.setTable(table);
        assertEquals(table, game.getTable());
        assertTrue(game.isPlayableTable());
        int pop_bumper_exceptions_start_at = 525;
        //  475/1000 cases are pop bumpers, which is acceptable as a 0.5 probability.
        int random_counter = 0;
        checkRandomBumpers(random_counter, pop_bumper_exceptions_start_at);
    }

    private void checkRandomBumpers(int random_counter, int bumper_exceptions_start_point) {
        for (Bumper bumper : game.getBumpers()) {
            random_counter++;
            if (random_counter >= bumper_exceptions_start_point) {
                assertTrue(bumper instanceof PopBumper);
            }
            else {
                assertTrue(bumper instanceof KickerBumper);
            }
        }
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
        int before = 300400;
        for(Target target : game.getTargets()) {
            game.hit(target);
        }
        List<Target> dropTargetList = game.getTargets()
                .stream()
                .filter(target -> target instanceof DropTarget)
                .collect(Collectors.toList());
        List<Target> spotTargetList = game.getTargets()
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .collect(Collectors.toList());
        for(Target target :
                dropTargetList) {
            assertTrue(target.isActive());
        }
        for (Target target :
                spotTargetList) {
            assertFalse(target.isActive());
        }
        assertEquals(before+1000000, game.getScore());
    }

    @Test
    public void hit() {
        Table table = new DefaultTable("hello world", 100, 100, 5, 100);
        game.setTable(table);
        int before_score;
        int before_balls;

        int hit_score;

        List<Hittable> list = new ArrayList<>();
        list.addAll(game.getTargets());
        list.addAll(game.getBumpers());

        int random_counter = 0;
        int random_extra_score;
        int random_extra_balls;
        int[] drop_target_exceptions = {10, 11, 15, 20, 31, 32, 34, 36, 37, 40, 43, 48, 51, 56, 57, 60, 61, 62, 64,
                68, 71, 75, 77, 80, 81, 84, 90, 96, 99, 101, 104};
        //  32/100 cases are exceptions, which is acceptable as a 0.3 probability.
        //  The first 5 cases are spotTargets, and always add a Bonus, as expected.

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

            if(contains(drop_target_exceptions, random_counter)) {
                random_extra_balls = 1;
            }
            else {
                random_extra_balls = 0;
            }

            if (random_counter == 105) {
                random_extra_score += 1_000_000;
            }

            assertEquals(before_score+hit_score+random_extra_score, game.getScore());

            assertEquals(before_balls+random_extra_balls, game.getBallCounter());
        }

        //  The rest of the counted hit tests used can be found in BigTestT3.java
        //  TODO: Add extra tests if necessary

    }

    private boolean contains(int[] array, int number) {
        for(int element : array) {
            if(element == number) {
                return true;
            }
        }
        return false;
    }

}