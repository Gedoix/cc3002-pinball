package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.almasb.fxgl.settings.GameSettings;
import controller.Game;
import gui.FXGLentities.PinballEntityFactory;
import gui.FXGLentities.components.DefaultStateComponent;
import gui.FXGLentities.components.HittableComponent;
import gui.FXGLentities.events.HitEvent;
import gui.FXGLentities.events.NewGameEvent;
import gui.FXGLentities.states.flipper_states.LeftFlipperActiveState;
import gui.FXGLentities.states.flipper_states.LeftFlipperInactiveState;
import gui.FXGLentities.states.flipper_states.RightFlipperActiveState;
import gui.FXGLentities.states.flipper_states.RightFlipperInactiveState;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import javafx.util.Duration;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Main class for handling game gui and interactions among elements.
 *
 * @author Diego Ortego Prieto
 *
 * @see com.almasb.fxgl.app.GameApplication
 * @see javafx.application.Application
 *
 * @see Game
 */
public class PinballGameApplication extends GameApplication {

    //  Enum types

    /**
     * Entity dynamic types for collision detection.
     */
    public enum Types {
        BACKGROUND,
        BALL,
        WALL,
        BOTTOM_WALL,
        SCORE_COUNTER,
        BALLS_COUNTER,
        KICKER_BUMPER,
        POP_BUMPER,
        DROP_TARGET,
        SPOT_TARGET,
        LEFT_FLIPPER,
        RIGHT_FLIPPER,
        PARTICLE_EMITTER
    }

    //  Instancing reusable objects

    /**
     * Instance of main {@link Game} controller.
     *
     * Keeps score and ball counters, along with individual Hittable behaviour.
     */
    private Game pinball = new Game();

    /**
     * Random number generator for Hittable placement at game start.
     *
     * @see Random
     */
    private Random random_number_generator = new Random();

    /**
     * For muting game audio.
     */
    private boolean mute = false;

    /**
     * Background music for the game.
     *
     * Loaded from resources/assets/music/
     */
    private Music background_song = getAssetLoader().loadMusic("bensound-thelounge.mp3");

    /**
     * PNG image for generating star particle effects.
     *
     * Loaded from resources/assets/textures/
     */
    private Image star = getAssetLoader().loadImage("Star.png");

    //  Overridden methods

    /**
     * Sets up window margins, window title and game version before anything else.
     *
     * @param gameSettings  Handy package containing desired settings, will be ignored.
     */
    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Pinball");
        gameSettings.setVersion("Pre-Alpha");
    }

    /**
     * Sets up input key to action assignment allowing most interactions.
     *
     * A, D activate the left and right flippers, respectively.
     *
     * Space throws a new ball into the game, if allowed.
     *
     * N resets the game, re-generating a random board.
     */
    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(ActivateLeftFlipperAction, KeyCode.A);

        input.addAction(ActivateRightFlipperAction, KeyCode.D);

        input.addAction(MakeBallAction, KeyCode.SPACE);

        input.addAction(NewTableAction, KeyCode.N);
    }

    /**
     * Sets up event handlers and calls {@link #resetAllEntitiesAndGame()} to generate the playing board.
     *
     * Adds music to the audio player and sets the global play volume.
     */
    @Override
    protected void initGame() {

        getEventBus().addEventHandler(NewGameEvent.DEFAULT, NewGameEventHandler);
        getEventBus().addEventHandler(HitEvent.HITTABLE, HittableHit);
        getEventBus().addEventHandler(HitEvent.BUMPER, BumperHit);
        getEventBus().addEventHandler(HitEvent.TARGET, TargetHit);
        getEventBus().addEventHandler(HitEvent.KICKER_BUMPER, KickerBumperHit);
        getEventBus().addEventHandler(HitEvent.POP_BUMPER, PopBumperHit);
        getEventBus().addEventHandler(HitEvent.SPOT_TARGET, SpotTargetHit);
        getEventBus().addEventHandler(HitEvent.DROP_TARGET, DropTargetHit);

        resetAllEntitiesAndGame();

        if (!mute) {
            double volume = 0.6;
            getAudioPlayer().setGlobalMusicVolume(volume);
            getAudioPlayer().playMusic(background_song);
        }

    }

    /**
     * Sets up collision handlers and configures gravity.
     */
    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0.0, 150.0);
        getPhysicsWorld().addCollisionHandler(KickerBumperCollisionHandler);
        getPhysicsWorld().addCollisionHandler(PopBumperCollisionHandler);
        getPhysicsWorld().addCollisionHandler(SpotTargetCollisionHandler);
        getPhysicsWorld().addCollisionHandler(DropTargetCollisionHandler);
        getPhysicsWorld().addCollisionHandler(BottomWallCollisionHandler);
    }

    /**
     * Makes sure {@link Game} triggers {@link DropTarget} at the appropriate time.
     *
     * @param tpf   Unused parameter inherited from superclass.
     */
    @Override
    protected void onUpdate(double tpf) {
        pinball.update();
    }

    //  Private complex behaviours

    /**
     * Removes all entities from the app.
     *
     * Resets the {@link Game} instance.
     *
     * Adds all the entities necessary for a functioning board.
     *
     * Adds all {@link logic.gameelements.Hittable}'s entities.
     *
     * Adds a starting ball, and flippers.
     *
     * Restarts background music.
     */
    private void resetAllEntitiesAndGame() {
        List<Entity> old_entities = new LinkedList<>(getGameWorld().getEntities());
        getGameWorld().removeEntities(old_entities);

        pinball = new Game();
        pinball.setTable(pinball.createRandomTable("Game", 10, 0.5, 5, 5));

        List<Entity> entities = new LinkedList<>();

        entities.add(PinballEntityFactory.newBackground());
        entities.add(PinballEntityFactory.newTableWall());
        entities.add(PinballEntityFactory.newBottomWall());
        //entities.add(PinballEntityFactory.newTopLeftWall());
        //entities.add(PinballEntityFactory.newTopRightWall());
        entities.add(PinballEntityFactory.newScoreCounter(pinball));
        entities.add(PinballEntityFactory.newBallCounter(pinball));

        double hittable_size = 10;

        for (Bumper bumper :
                pinball.getBumpers()) {
            if (bumper.getClass() == KickerBumper.class) {
                entities.add(PinballEntityFactory.newKickerBumper(250+ random_number_generator.nextInt(300), 50+ random_number_generator.nextInt(350), hittable_size, (KickerBumper) bumper));
            }
            else {
                entities.add(PinballEntityFactory.newPopBumper(250+ random_number_generator.nextInt(300), 50+ random_number_generator.nextInt(350), hittable_size, (PopBumper) bumper));
            }
        }

        for (Target target :
                pinball.getTargets()) {
            if (target.getClass() == SpotTarget.class) {
                entities.add(PinballEntityFactory.newSpotTarget(250+ random_number_generator.nextInt(300), 50+ random_number_generator.nextInt(350), hittable_size, (SpotTarget) target));
            }
            else {
                entities.add(PinballEntityFactory.newDropTarget(250+ random_number_generator.nextInt(300), 50+ random_number_generator.nextInt(350), hittable_size, (DropTarget) target));
            }
        }
        entities.add(PinballEntityFactory.newBall());
        entities.add(PinballEntityFactory.newLeftFlipper());
        entities.add(PinballEntityFactory.newRightFlipper());

        for (Entity entity :
                entities) {
            getGameWorld().addEntity(entity);
        }

        if (!mute) {
            getAudioPlayer().stopMusic(background_song);
            getAudioPlayer().playMusic(background_song);
        }

    }

    //  Reach

    /**
     * Ensures the class can be launched from the outside.
     *
     * @param args  Should be left empty.
     */
    public static void main(String[] args) {
        launch(args);
    }

    //  Actions

    /**
     * Activates and de-activates the left flipper, allowing for smooth movement.
     */
    private UserAction ActivateLeftFlipperAction = new UserAction("Activate Left Flipper") {

        /**
         * Sets flipper state to active.
         */
        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperActiveState(e)));
        }

        /**
         * Sets flipper state to inactive.
         */
        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperInactiveState(e)));
        }

    };

    /**
     * Activates and de-activates the right flipper, allowing for smooth movement.
     */
    private UserAction ActivateRightFlipperAction = new UserAction("Activate Right Flipper") {

        /**
         * Sets flipper state to active.
         */
        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperActiveState(e)));
        }

        /**
         * Sets flipper state to inactive.
         */
        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperInactiveState(e)));
        }

    };

    /**
     * Generates a new ball if there's none already on screen, and if the ball counter allows it.
     */
    private UserAction MakeBallAction = new UserAction("Make Ball") {

        @Override
        protected void onActionBegin() {
            if (getGameWorld().getEntitiesByType(Types.BALL).isEmpty() && pinball.getBallCounter() != 0) {
                Entity ball = PinballEntityFactory.newBall();
                getGameWorld().addEntity(ball);
                pinball.removeBall();
            }
        }

    };

    /**
     * Calls {@link #resetAllEntitiesAndGame()} to reset all.
     */
    private UserAction NewTableAction = new UserAction("New Game Table") {

        @Override
        protected void onActionBegin() {
            getEventBus().fireEvent(new NewGameEvent(NewGameEvent.DEFAULT));
        }

    };

    //  Event Handlers

    /**
     * Event handler that triggers {@link #resetAllEntitiesAndGame()}, allowing more widespread
     * use by calling a {@link NewGameEvent}.
     */
    private EventHandler<NewGameEvent> NewGameEventHandler = event -> resetAllEntitiesAndGame();

    //  Hit Event Handlers

    /**
     * Event handler supposed to trigger whenever a {@link logic.gameelements.Hittable} is hit.
     *
     * Paints the proper entity white for a moment.
     *
     * Creates a star explosion effect.
     *
     * Transmits the hit to {@link Game}.
     */
    private EventHandler<HitEvent> HittableHit = event -> {
        Entity hittable = event.getHitEntity();
        //  Painted white for an instant to symbolize an effective hit
        for (Node node :
                hittable.getView().getNodes()) {
            Shape shape = (Shape)node;
            Paint old_paint = shape.getFill();
            shape.setFill(Color.WHITE);
            getMasterTimer().runOnceAfter(() -> shape.setFill(old_paint), Duration.seconds(0.1));
        }
        //  Added star explosion effect
        double x = hittable.getPosition().getX();
        double y = hittable.getPosition().getY();
        getGameWorld().addEntity(PinballEntityFactory.newTexturedExplosionParticleMaker(x, y, star));
        //  Transmit hit to Game
        pinball.hit(hittable.getComponent(HittableComponent.class).getHittable());
    };

    /**
     * Event handler supposed to trigger whenever a {@link Bumper} type hittable is hit.
     *
     * Ensures the bumper resets after 10 seconds.
     */
    private EventHandler<HitEvent> BumperHit = event -> {
        Bumper bumper = (Bumper) event.getHitEntity().getComponent(HittableComponent.class).getHittable();
        getMasterTimer().runOnceAfter(bumper::downgrade, Duration.seconds(10));
    };

    /**
     * Event handler supposed to trigger whenever a {@link Target} type hittable is hit.
     *
     * Does nothing, may have implemented behaviour later.
     */
    private EventHandler<HitEvent> TargetHit = event -> {

    };

    /**
     * Event handler supposed to trigger whenever a {@link KickerBumper} type hittable is hit.
     *
     * Does nothing, may have implemented behaviour later.
     */
    private EventHandler<HitEvent> KickerBumperHit = event -> {

    };

    /**
     * Event handler supposed to trigger whenever a {@link PopBumper} type hittable is hit.
     *
     * Does nothing, may have implemented behaviour later.
     */
    private EventHandler<HitEvent> PopBumperHit = event -> {

    };

    /**
     * Event handler supposed to trigger whenever a {@link SpotTarget} type hittable is hit.
     *
     * Ensures the target resets after 20 seconds.
     */
    private EventHandler<HitEvent> SpotTargetHit = event -> {
        SpotTarget target = (SpotTarget) event.getHitEntity().getComponent(HittableComponent.class).getHittable();
        getMasterTimer().runOnceAfter(target::reset, Duration.seconds(20));
    };

    /**
     * Event handler supposed to trigger whenever a {@link DropTarget} type hittable is hit.
     *
     * Ensures the target resets after 120 seconds.
     */
    private EventHandler<HitEvent> DropTargetHit = event -> {
        DropTarget target = (DropTarget) event.getHitEntity().getComponent(HittableComponent.class).getHittable();
        getMasterTimer().runOnceAfter(target::reset, Duration.seconds(120));
    };

    //  Collision Handlers

    /**
     * Collision handler that fires a {@link HitEvent} when a {@link KickerBumper}'s entity is hit.
     */
    private CollisionHandler KickerBumperCollisionHandler = new CollisionHandler(Types.BALL, Types.KICKER_BUMPER) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity bumper) {
            getEventBus().fireEvent(new HitEvent(HitEvent.KICKER_BUMPER, bumper));
        }

    };

    /**
     * Collision handler that fires a {@link HitEvent} when a {@link PopBumper}'s entity is hit.
     */
    private CollisionHandler PopBumperCollisionHandler = new CollisionHandler(Types.BALL, Types.POP_BUMPER) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity bumper) {
            getEventBus().fireEvent(new HitEvent(HitEvent.POP_BUMPER, bumper));
        }

    };

    /**
     * Collision handler that fires a {@link HitEvent} when a {@link SpotTarget}'s entity is hit.
     */
    private CollisionHandler SpotTargetCollisionHandler = new CollisionHandler(Types.BALL, Types.SPOT_TARGET) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity target) {
            getEventBus().fireEvent(new HitEvent(HitEvent.SPOT_TARGET, target));
        }

    };

    /**
     * Collision handler that fires a {@link HitEvent} when a {@link DropTarget}'s entity is hit.
     */
    private CollisionHandler DropTargetCollisionHandler = new CollisionHandler(Types.BALL, Types.DROP_TARGET) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity target) {
            getEventBus().fireEvent(new HitEvent(HitEvent.DROP_TARGET, target));
        }

    };

    /**
     * Collision handler that removes the game's ball and it's physics whenever it touches the bottom wall of the board.
     *
     * Removes a ball from the {@link Game} too.
     */
    private CollisionHandler BottomWallCollisionHandler = new CollisionHandler(Types.BALL, Types.BOTTOM_WALL) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity wall) {
            Body body = ball.getComponent(PhysicsComponent.class).getBody();
            ball.removeComponent(PhysicsComponent.class);

            //  Ensures the proper deletion of the ball, avoiding a strange nullPointerException bug
            getMasterTimer().runOnceAfter(() -> getPhysicsWorld().getJBox2DWorld().destroyBody(body), Duration.seconds(1));

            getGameWorld().removeEntity(ball);

        }

    };

}
