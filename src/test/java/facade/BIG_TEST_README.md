# Details on the changes made to `BigTestT3.java`

Here I list the changes made to the original testing file for making it compatible with my way of implementing the game.

## An extra note

I should add that in order for the next changes to work, it was necessary to add the next method 
to `HomeworkTwoFacade.java`:
```
/**
 * Gets the instance of {@link Game} being demonstrated.
 * 
 * @return the current game instance.
 */
public Game getGame() {
    return game;
}
```
Since hitting a hittable is only valid when done by it's respective game instance.

## Change 1
###    Imports

In line 1:
```
import facade.HomeworkTwoFacade;
import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
```
Changed to:
```
package facade;

import logic.gameelements.Hittable;
import logic.gameelements.bumper.Bumper;
```

## Change 2
###    Variables

Around line 24:
>```
>private final int popBumperBaseScore = 100;
>private final int popBumperUpgradeScore = 300;
>
>private final int kickerBumperBaseScore = 500;
>private final int kickerBumperUpgradeScore = 1000;
>
>private final int spotTargetScore = 0;
>private final int dropTargetScore = 100;
>
>private final int dropTargetAllDropBonusScore = 1_000_000;
>private final int jackPotBonusScore = 100_000;
>```
>Changed to:
>```
>private final int spotTargetScore = 0;
>private final int dropTargetScore = 100;
>```

Around line 228, this was added:
```
// count expected score
int popBumperBaseScore = 100;
int kickerBumperBaseScore = 500;
```

Around line 244, this was added:
```
// pop upgraded
int popBumperUpgradeScore = 300;
```

Around line 271, this was added:
```
int kickerBumperUpgradeScore = 1000;
```

Around line 451, this was added:
```
int dropTargetAllDropBonusScore = 1_000_000;
```

Around line 480, this was added:
```
int jackPotBonusScore = 100_000;
```

## Change 3
###    `testBasicBumperBehaviour()`

Around lines 227, 291, 303 and 316:
>```
>bumpers.forEach(Hittable::hit);
>```
>Were changed to:
>```
>bumpers.forEach((Hittable) -> hw2.getGame().hit(Hittable));
>```

Around lines 243 and 274:
>```
>repeat(2, () -> bumpers.forEach(Hittable::hit));
>```
>Were changed to:
>```
>repeat(2, () -> bumpers.forEach((Hittable) -> hw2.getGame().hit(Hittable)));
>```

##  Change 4
### `testAllDropBonus()`

Around line 435:
>```
>dropTargetList.get(0).hit();
>```
>Was changed to:
>```
>hw2.getGame().hit(dropTargetList.get(0));
>```

Around line 443:
>```
>dropTargetList.forEach(Hittable::hit);
>```
>Was changed to:
>```
>dropTargetList.forEach((Hittable) -> hw2.getGame().hit(Hittable));
>```

Around line 457:
>```
>repeat(10, () -> dropTargetList.forEach(Hittable::hit));
>```
>Was changed to:
>```
>repeat(10, () -> dropTargetList.forEach((Hittable) -> hw2.getGame().hit(Hittable)));
>```

##  Change 5
### `testJackPotBonus()`

Around line 472:
>```
>spotTargetList.forEach(Hittable::hit);
>```
>Was changed to:
>```
>spotTargetList.forEach((Hittable) -> hw2.getGame().hit(Hittable));
>```

##  Change 6
### `testExtraBallBonus()`

Around line 500:
>```
>repeat(10, () -> {
>   repeat(6, () -> bumpers.forEach(Hittable::hit));
>   bumpers.forEach(Bumper::downgrade);
>});
>```
>Was changed to:
>```
>repeat(10, () -> {
>   repeat(6, () -> bumpers.forEach((Hittable) -> hw2.getGame().hit(Hittable)));
>   bumpers.forEach(Bumper::downgrade);
>});
>```

Around line 509:
>```
>repeat(10, () -> dropTargetList.forEach(Hittable::hit));
>```
>Was changed to:
>```
>repeat(10, () -> dropTargetList.forEach((Hittable) -> hw2.getGame().hit(Hittable)));
>```
