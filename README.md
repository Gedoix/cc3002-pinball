# cc3002-pinball

Second homework of the cc3002 course dictated in Universidad de Chile, consisting in the creation of the logic 
and rules for a functioning virtual pinball game in java.

The project implements the following specifications:

* The game code inside the `logic` package describes all game interacting elements so far.

* In `logic.bonus` the bonus events are implemented, which are special events the game triggers under certain conditions.
These bonus objects must extend the `AbstractBonus` abstract class, and implement a `bonusBehaviour` method, detailing 
the bonus's effects, this method will later be called using the template design pattern.

* In `logic.utils` some utility classes and interfaces are implemented.
This is where the `VisitableGameElement` and `GameElementVisitor` interfaces are located, which detail the expected 
behaviour of the visitor pattern, occupied by `Game` to interact with it's elements.

* In `logic.gameelements` the game's interacting elements are implemented.
Here is where both `Bumper`s and `Target`s are.
All game elements that are hittable by the ball must extend the `AbstractHittable` abstract class and can choose to 
implement the `beforeHitBehaviour` and/or the `afterHitBehaviour` methods to specify their own changes on hit, which 
are too called using the template pattern.
These game elements also must implement the `accept` method coming from the previously mentioned `VisitableGameElement`
interface, and therefore detail what their effects on the `Game` are when hit.
The `HitVisitor` element implements how this interactions are managed.

* A `Bumper` is an object that can be hit by the ball, and give a score to the player, furthermore, once hit a certain 
number of times it 'upgrades' giving a bigger score on hit. All bumpers must extend the `AbstractBumper` abstract class,
and detail their respective scores and hit-counters for upgrading, also implementing the `accept` method.

* A `Target` is a hittable object too, that may or may not give a score on hit. Once hit it deactivates, and has a 
chance of triggering a `Bonus`. All targets must extend the `AbstractTarget` abstract class, and detail their respective
scores, also implementing the `accept` method.

* In `logic.table` the game's `Table` objects are implemented.
These tables serve as containers, generators and managers of game elements for the `Game` class.
All tables must extend the `AbstractTable` class and implement a proper constructor method that ensures their inner 
objects are created accordingly.

* In `controller` the `Game` class can be found.
This object acts as the game's controller and describes all interactions of it's elements either directly or through the
`Table` object it can instance.

* In `facade` the class `HomeworkTwoFacade` can be found, which implements the facade design pattern, serving as a 
demonstrator of the game's workings.
This class should be using when asserting it's correct implementations.

## Getting Started

Getting a copy of this project is easy, the next few steps need to be followed:

* Install [Git](https://git-scm.com/ "Git's Homepage") on your local system.
* Open git bash in the directory where you wish to save the project's copy.
* Use the `git clone https://github.com/Gedoix/cc3002-pinball.git` command to download the project's 
source files onto a new `cc3002-pinball` folder in the directory.

### Prerequisites

* Only [Git](https://git-scm.com/ "Git's Homepage") is necessary for downloading the software.
Install instructions for git can be found [here](https://git-scm.com/book/en/v2/Getting-Started-Installing-Git 
"Getting Started - Installing Git")
* The latest [java JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk10-downloads-4416644.html) should be 
installed, otherwise there's no way to run the software.
* A java IDE is recommended for running automated tests for the project.
The recommended IDE (the one used to make this) is [JetBrain's IntelliJ Idea IDE](https://www.jetbrains.com/idea/ 
"IntelliJ's main page").

### Installing

Once the project has been downloaded, follow these next steps to get it running in your favorite IDE:

* Find and select the `Import Project` option in your IDE.
* Make sure to configure the import to expect a [Maven](https://maven.apache.org/ "Welcome to Apache Maven") 
project structure.
* Find and select your new `cc3002-pinball` directory as the project's root.
* Name the project as you please.
* If you're using the recommended IntelliJ IDE, allow it to overwrite the existing `.idea` folder.
* And you're done!

All source files for the project can be found in the `cc3002-pinball/src/main/java/` directory, while the test sources 
can be found inside `cc3002-pinball/src/test/java/`.

## Running the tests

Now that the project is be loaded into your IDE, find and select the `Run all tests with coverage` option to run 
all the automated testing cases for the project. This option can be easily be found in IntelliJ by right-clicking the 
project root directory.

```
Tests Passed: 54 of 54 tests
```

Or a similar message should be displayed on your IDE, meaning the project is configured accordingly.

## Built With

* [JetBrain's IntelliJ Idea](https://www.jetbrains.com/idea/) - The IDE used
* [Maven](https://maven.apache.org/) - Dependency Management
* [ShareLatex](https://www.sharelatex.com/project) - PDF creation

## Versioning

I used [GitHub](https://github.com/) for versioning. 
For the versions available, see the [tags on this repository](https://github.com/your/project/tags). 

## Authors

* **Diego Ortego Prieto** - *Implementation and testing* - [Gedoix](https://github.com/Gedoix)
* **Juan-Pablo Silva** - *Initial project structure template* - [juanpablos](https://github.com/juanpablos)
* **Bensound.com** - *Background song* - [Bensound](https://www.bensound.com)

See also the list of [contributors](https://github.com/Gedoix/cc3002-pinball//contributors) 
who participated in this project.

## Acknowledgments

* Hat tip to [Stack Overflow](https://stackoverflow.com/) for being awesome.
* A [Markdown Cheat-sheet](https://github.com/adam-p/markdown-here/wiki/Markdown-Cheatsheet) and a proper 
[README.md template](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2) were used, credit of which goes to their 
respective creators.
* A [JetBrains .gitignore file](https://gist.github.com/PurpleBooth/109311bb0361f32d87a2) was also used for this 
project's versioning, credit of which goes to it's respective creator too.
