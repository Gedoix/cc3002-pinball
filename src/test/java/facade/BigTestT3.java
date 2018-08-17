import facade.HomeworkTwoFacade;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;
import logic.table.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class BigTestT3 {
    private HomeworkTwoFacade hw2;
    private final int numberOfInitialBalls = 3;

    private final int popBumperBaseScore = 100;
    private final int popBumperUpgradeScore = 300;

    private final int kickerBumperBaseScore = 500;
    private final int kickerBumperUpgradeScore = 1000;

    private final int spotTargetScore = 0;
    private final int dropTargetScore = 100;

    private final int dropTargetAllDropBonusScore = 1_000_000;
    private final int jackPotBonusScore = 100_000;

    @Before
    public void setUp() {
        hw2 = new HomeworkTwoFacade();
    }

    @Test
    public void testInitialSetting() {
        // general state
        assertFalse(hw2.gameOver());
        assertEquals(0, hw2.getCurrentScore());
        assertEquals(numberOfInitialBalls, hw2.getAvailableBalls());
        assertTrue(hw2.getBumpers().isEmpty());
        assertTrue(hw2.getTargets().isEmpty());
        assertEquals("", hw2.getTableName());
        assertFalse(hw2.isPlayableTable());

        // bonus
        assertEquals(0, hw2.getDropTargetBonus().timesTriggered());
        assertEquals(0, hw2.getExtraBallBonus().timesTriggered());
        assertEquals(0, hw2.getJackPotBonus().timesTriggered());

        // table
        Table table = hw2.getCurrentTable();
        assertEquals("", table.getTableName());
        assertEquals(0, table.getNumberOfDropTargets());
        assertEquals(0, table.getCurrentlyDroppedDropTargets());
        assertTrue(table.getBumpers().isEmpty());
        assertTrue(table.getTargets().isEmpty());
        assertFalse(table.isPlayableTable());
    }

    @Test
    public void testPlayableTableNoTarget() {
        String name = "Test table";
        int numberOfBumpers = 20;

        // only pop
        Table tableOnlyPop = hw2.newPlayableTableWithNoTargets(name, numberOfBumpers, 1);
        // basic
        assertEquals(name, tableOnlyPop.getTableName());
        assertEquals(0, tableOnlyPop.getNumberOfDropTargets());
        assertEquals(0, tableOnlyPop.getCurrentlyDroppedDropTargets());
        assertFalse(tableOnlyPop.getBumpers().isEmpty());
        assertEquals(numberOfBumpers, tableOnlyPop.getBumpers().size());
        // bumper count
        long numberOfKicker = tableOnlyPop.getBumpers()
                .stream()
                .filter(bumper -> bumper instanceof KickerBumper)
                .count();
        long numberOfPop = tableOnlyPop.getBumpers()
                .stream()
                .filter(bumper -> bumper instanceof PopBumper)
                .count();
        assertEquals(0, numberOfKicker);
        assertEquals(numberOfBumpers, numberOfPop);
        assertTrue(tableOnlyPop.getTargets().isEmpty());
        assertTrue(tableOnlyPop.isPlayableTable());

        // only kicker
        Table tableOnlyKick = hw2.newPlayableTableWithNoTargets(name, numberOfBumpers, 0);
        // basic
        assertEquals(name, tableOnlyKick.getTableName());
        assertEquals(0, tableOnlyKick.getNumberOfDropTargets());
        assertEquals(0, tableOnlyKick.getCurrentlyDroppedDropTargets());
        assertFalse(tableOnlyKick.getBumpers().isEmpty());
        assertEquals(numberOfBumpers, tableOnlyKick.getBumpers().size());
        // bumper count
        numberOfKicker = tableOnlyKick.getBumpers()
                .stream()
                .filter(bumper -> bumper instanceof KickerBumper)
                .count();
        numberOfPop = tableOnlyKick.getBumpers()
                .stream()
                .filter(bumper -> bumper instanceof PopBumper)
                .count();
        assertEquals(numberOfBumpers, numberOfKicker);
        assertEquals(0, numberOfPop);
        assertTrue(tableOnlyKick.getTargets().isEmpty());
        assertTrue(tableOnlyKick.isPlayableTable());

    }

    @Test
    public void testFullPlayableTable() {
        String name = "Test table";
        int numberOfBumpers = 20;
        int numberOfSpot = 10;
        int numberOfDrop = 20;

        // only spot
        Table tableOnlySpot = hw2.newFullPlayableTable(name, numberOfBumpers, 1, numberOfSpot, 0);
        // basic
        assertEquals(name, tableOnlySpot.getTableName());
        assertEquals(0, tableOnlySpot.getNumberOfDropTargets());
        assertEquals(0, tableOnlySpot.getCurrentlyDroppedDropTargets());
        assertFalse(tableOnlySpot.getBumpers().isEmpty());
        assertEquals(numberOfBumpers, tableOnlySpot.getBumpers().size());
        assertFalse(tableOnlySpot.getTargets().isEmpty());
        assertEquals(numberOfSpot, tableOnlySpot.getTargets().size());
        long countOfSpot = tableOnlySpot.getTargets()
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .count();
        long countOfDrop = tableOnlySpot.getTargets()
                .stream()
                .filter(target -> target instanceof DropTarget)
                .count();
        assertEquals(0, countOfDrop);
        assertEquals(numberOfSpot, countOfSpot);
        assertTrue(tableOnlySpot.isPlayableTable());

        // only drop
        Table tableOnlyDrop = hw2.newFullPlayableTable(name, numberOfBumpers, 1, 0, numberOfDrop);
        // basic
        assertEquals(name, tableOnlyDrop.getTableName());
        assertEquals(numberOfDrop, tableOnlyDrop.getNumberOfDropTargets());
        assertEquals(0, tableOnlyDrop.getCurrentlyDroppedDropTargets());
        assertFalse(tableOnlyDrop.getBumpers().isEmpty());
        assertEquals(numberOfBumpers, tableOnlyDrop.getBumpers().size());
        assertFalse(tableOnlyDrop.getTargets().isEmpty());
        assertEquals(numberOfDrop, tableOnlyDrop.getTargets().size());
        countOfSpot = tableOnlyDrop.getTargets()
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .count();
        countOfDrop = tableOnlyDrop.getTargets()
                .stream()
                .filter(target -> target instanceof DropTarget)
                .count();
        assertEquals(numberOfDrop, countOfDrop);
        assertEquals(0, countOfSpot);
        assertTrue(tableOnlyDrop.isPlayableTable());

        // both
        Table normalTable = hw2.newFullPlayableTable(name, numberOfBumpers, 1, numberOfSpot, numberOfDrop);
        // basic
        assertEquals(name, normalTable.getTableName());
        assertEquals(numberOfDrop, normalTable.getNumberOfDropTargets());
        assertEquals(0, normalTable.getCurrentlyDroppedDropTargets());
        assertFalse(normalTable.getBumpers().isEmpty());
        assertEquals(numberOfBumpers, normalTable.getBumpers().size());
        assertFalse(normalTable.getTargets().isEmpty());
        assertEquals(numberOfSpot + numberOfDrop, normalTable.getTargets().size());
        countOfSpot = normalTable.getTargets()
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .count();
        countOfDrop = normalTable.getTargets()
                .stream()
                .filter(target -> target instanceof DropTarget)
                .count();
        assertEquals(numberOfDrop, countOfDrop);
        assertEquals(numberOfSpot, countOfSpot);
        assertTrue(normalTable.isPlayableTable());
    }

    @Test
    public void testBallBehaviour() {
        assertFalse(hw2.gameOver());
        for (int i = numberOfInitialBalls; i > 0; i--) {
            assertEquals(i - 1, hw2.dropBall());
            assertEquals(i - 1, hw2.getAvailableBalls());
        }
        assertTrue(hw2.gameOver());

        assertEquals(0, hw2.dropBall());
        assertEquals(0, hw2.getAvailableBalls());
        assertTrue(hw2.gameOver());
    }

    @Test
    public void testNewTableBehaviour() {
        String name = "Test table";
        Table newTable = hw2.newFullPlayableTable(name, 10, 1, 10, 10);

        hw2.setGameTable(newTable);
        assertFalse(hw2.getBumpers().isEmpty());
        assertFalse(hw2.getTargets().isEmpty());
        assertEquals(name, hw2.getTableName());
        assertTrue(hw2.isPlayableTable());
        assertEquals(newTable, hw2.getCurrentTable());
    }

    @Test
    public void testBasicBumperBehaviour() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 10, 0.5, 10, 5));
        List<Bumper> bumpers = hw2.getBumpers();
        List<Bumper> popBumperList = bumpers
                .stream()
                .filter(bumper -> bumper instanceof PopBumper)
                .collect(Collectors.toList());
        List<Bumper> kickerBumperList = bumpers
                .stream()
                .filter(bumper -> bumper instanceof KickerBumper)
                .collect(Collectors.toList());

        int expectedScore = 0;
        assertEquals(expectedScore, hw2.getCurrentScore());

        // hit all
        bumpers.forEach(Hittable::hit);
        // count expected score
        int baseScore = popBumperList.size() * popBumperBaseScore + kickerBumperList.size() * kickerBumperBaseScore;
        expectedScore += baseScore;
        assertEquals(expectedScore, hw2.getCurrentScore());
        //check remaining hits to upgrade
        assertTrue(popBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 2));
        assertTrue(kickerBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 4));

        // hit everything 2 times
        repeat(2, () -> bumpers.forEach(Hittable::hit));
        // pop upgraded
        expectedScore += (baseScore + popBumperList.size() * popBumperUpgradeScore + kickerBumperList.size() * kickerBumperBaseScore);
        assertEquals(expectedScore, hw2.getCurrentScore());
        // check that pop is upgraded
        assertTrue(popBumperList
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        true,
                        (a, b) -> a && b));
        assertFalse(kickerBumperList
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        false,
                        (a, b) -> a || b));
        //check remaining hits to upgrade
        assertTrue(popBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 0));
        assertTrue(kickerBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 2));

        // 2 more times
        repeat(2, () -> bumpers.forEach(Hittable::hit));
        expectedScore += popBumperList.size() * popBumperUpgradeScore * 2 +
                kickerBumperList.size() * kickerBumperUpgradeScore + kickerBumperList.size() * kickerBumperBaseScore;
        assertEquals(expectedScore, hw2.getCurrentScore());
        // everything should be upgraded
        assertTrue(bumpers
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        true,
                        (a, b) -> a && b));
        //check remaining hits to upgrade
        assertTrue(popBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 0));
        assertTrue(kickerBumperList
                .stream()
                .allMatch(bumper -> bumper.remainingHitsToUpgrade() == 0));

        // hit 6th time
        bumpers.forEach(Hittable::hit);
        expectedScore += popBumperList.size() * popBumperUpgradeScore + kickerBumperList.size() * kickerBumperUpgradeScore;
        assertEquals(expectedScore, hw2.getCurrentScore());

        // downgrade and hit
        bumpers.forEach(Bumper::downgrade);
        assertFalse(bumpers
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        false,
                        (a, b) -> a || b));
        bumpers.forEach(Hittable::hit);
        expectedScore += baseScore;
        assertEquals(expectedScore, hw2.getCurrentScore());


        // upgrade and hit
        bumpers.forEach(Bumper::upgrade);
        assertTrue(bumpers
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        true,
                        (a, b) -> a && b));
        bumpers.forEach(Hittable::hit);
        expectedScore += popBumperList.size() * popBumperUpgradeScore + kickerBumperList.size() * kickerBumperUpgradeScore;
        assertEquals(expectedScore, hw2.getCurrentScore());
    }

    @Test
    public void testBasicTargetBehaviour() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 10, 0.5, 10, 5));
        List<Target> targets = hw2.getTargets();
        List<Target> spotTargetList = targets
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .collect(Collectors.toList());
        List<Target> dropTargetList = targets
                .stream()
                .filter(target -> target instanceof DropTarget)
                .collect(Collectors.toList());

        // all must be active
        assertTrue(targets
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));

        int expectedScore = 0;
        assertEquals(expectedScore, hw2.getCurrentScore());
        // hitting spot targets gives a score (0)
        int manualScore = spotTargetList
                .stream()
                .mapToInt(Hittable::hit)
                .sum();
        expectedScore = spotTargetScore * spotTargetList.size();
        assertEquals(expectedScore, manualScore);

        // hitting drop targets gives a score (100)
        manualScore = dropTargetList
                .stream()
                .mapToInt(Hittable::hit)
                .sum();
        expectedScore = dropTargetScore * dropTargetList.size();
        assertEquals(expectedScore, manualScore);

        // drop target auto activation is tested in other test
        // spot should still be inactive
        assertFalse(spotTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        false,
                        (a, b) -> a || b));

        // everything should be active now
        targets.forEach(Target::reset);
        assertTrue(targets
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));

    }

    @Test
    public void testTableUpdates() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 10, 0.5, 10, 5));
        // targets
        List<Target> targets = hw2.getTargets();
        List<Target> dropTargetList = targets
                .stream()
                .filter(target -> target instanceof DropTarget)
                .collect(Collectors.toList());

        assertEquals(0, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        dropTargetList.get(0).hit();
        assertEquals(1, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        dropTargetList.get(1).hit();
        assertEquals(2, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        dropTargetList.get(2).hit();
        assertEquals(3, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        dropTargetList.get(3).hit();
        assertEquals(4, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        assertFalse(dropTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));
        hw2.getCurrentTable().resetDropTargets();
        assertEquals(0, hw2.getCurrentTable().getCurrentlyDroppedDropTargets());
        assertTrue(dropTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));

        // bumpers
        List<Bumper> bumpers = hw2.getBumpers();
        hw2.getCurrentTable().upgradeAllBumpers();
        assertTrue(bumpers
                .stream()
                .map(Bumper::isUpgraded)
                .reduce(
                        true,
                        (a, b) -> a && b));
    }

    @Test
    public void testAllDropBonus() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 10, 0.5, 10, 5));
        // targets
        List<Target> targets = hw2.getTargets();
        List<Target> dropTargetList = targets
                .stream()
                .filter(target -> target instanceof DropTarget)
                .collect(Collectors.toList());

        dropTargetList.get(0).hit();
        assertFalse(dropTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));

        dropTargetList.forEach(Hittable::hit);
        // all drop have been hit, they should be false
        assertTrue(dropTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        true,
                        (a, b) -> a && b));
        int expectedScore = dropTargetAllDropBonusScore + dropTargetScore * dropTargetList.size();
        assertEquals(expectedScore, hw2.getCurrentScore());

        // times triggered
        assertEquals(1, hw2.getDropTargetBonus().timesTriggered());
        repeat(10, () -> dropTargetList.forEach(Hittable::hit));
        assertEquals(11, hw2.getDropTargetBonus().timesTriggered());
    }

    @Test
    public void testJackPotBonus() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 10, 0.5, 10, 5));
        List<Target> targets = hw2.getTargets();
        List<Target> spotTargetList = targets
                .stream()
                .filter(target -> target instanceof SpotTarget)
                .collect(Collectors.toList());

        int expectedScore = 0;
        assertEquals(expectedScore, hw2.getCurrentScore());
        spotTargetList.forEach(Hittable::hit);
        // check all inactive
        assertFalse(spotTargetList
                .stream()
                .map(Target::isActive)
                .reduce(
                        false,
                        (a, b) -> a || b));
        expectedScore += spotTargetScore * spotTargetList.size() + jackPotBonusScore * spotTargetList.size();
        assertEquals(expectedScore, hw2.getCurrentScore());

        // times triggered
        assertEquals(spotTargetList.size(), hw2.getJackPotBonus().timesTriggered());
    }

    @Test
    public void testExtraBallBonus() {
        hw2.setGameTable(hw2.newFullPlayableTable("Test", 100, 0.5, 10, 100));
        List<Bumper> bumpers = hw2.getBumpers();
        List<Target> targets = hw2.getTargets();
        List<Target> dropTargetList = targets
                .stream()
                .filter(target -> target instanceof DropTarget)
                .collect(Collectors.toList());

        assertEquals(numberOfInitialBalls, hw2.getAvailableBalls());

        repeat(10, () -> {
            repeat(6, () -> bumpers.forEach(Hittable::hit));
            bumpers.forEach(Bumper::downgrade);
        });

        // we'll need to use a seed later
        int moreBalls = hw2.getAvailableBalls();
        assertTrue(numberOfInitialBalls < moreBalls);

        repeat(10, () -> dropTargetList.forEach(Hittable::hit));
        assertTrue(moreBalls < hw2.getAvailableBalls());

    }

    private void repeat(int n, Runnable action) {
        IntStream.range(0, n).forEach(i -> action.run());
    }

}
