package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import controller.Game;
import gui.FXGLentities.Components.HittableComponent;
import gui.FXGLentities.Events.HitEvent;
import gui.FXGLentities.Events.NewGameEvent;
import gui.FXGLentities.GameEntityFactory;
import gui.FXGLentities.States.DefaultStateComponent;
import gui.FXGLentities.States.FlipperStates.LeftFlipperActiveState;
import gui.FXGLentities.States.FlipperStates.LeftFlipperInactiveState;
import gui.FXGLentities.States.FlipperStates.RightFlipperActiveState;
import gui.FXGLentities.States.FlipperStates.RightFlipperInactiveState;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import logic.gameelements.bumper.Bumper;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;
import logic.gameelements.target.Target;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Main extends GameApplication {

    public enum Types {
        BALL,
        WALL,
        KICKER_BUMPER,
        POP_BUMPER,
        DROP_TARGET,
        SPOT_TARGET,
        LEFT_FLIPPER,
        RIGHT_FLIPPER
    }

    private Game pinball = new Game();

    private Random randomizer = new Random();

    private boolean mute = true;

    private Music background_song = getAssetLoader().loadMusic("bensound-thelounge.mp3");
    //private Sound hit_sound = getAssetLoader().loadSound();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Pinball");
        gameSettings.setVersion("Pre-Alpha");
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(ActivateLeftFlipperAction, KeyCode.A);

        input.addAction(ActivateRightFlipperAction, KeyCode.D);

        input.addAction(MakeBallAction, KeyCode.SPACE);

        input.addAction(NewTableAction, KeyCode.N);

    }

    @Override
    protected void preInit() {}

    @Override
    protected void initGameVars(Map<String, Object> vars) {

    }

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
            getAudioPlayer().setGlobalMusicVolume(0.1);
            getAudioPlayer().playMusic(background_song);
        }

    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0.0, 150.0);
        getPhysicsWorld().addCollisionHandler(KickerBumperCollisionHandler);
        getPhysicsWorld().addCollisionHandler(PopBumperCollisionHandler);
        getPhysicsWorld().addCollisionHandler(SpotTargetCollisionHandler);
        getPhysicsWorld().addCollisionHandler(DropTargetCollisionHandler);
    }

    @Override
    protected void initUI() {}

    @Override
    protected void onUpdate(double tpf) {
        pinball.update();
    }

    @Override
    protected void onPostUpdate(double tpf) {}

    private void resetAllEntitiesAndGame() {
        getGameWorld().removeEntities(getGameWorld().getEntities());

        pinball.setTable(pinball.createRandomTable("Game", 10, 0.5, 5, 5));

        List<Entity> entities = new LinkedList<>();

        entities.add(GameEntityFactory.newBackground());
        entities.add(GameEntityFactory.newTableWall());
        entities.add(GameEntityFactory.newTopLeftWall());
        entities.add(GameEntityFactory.newTopRightWall());
        entities.add(GameEntityFactory.newScoreCounter(pinball));
        entities.add(GameEntityFactory.newBallCounter(pinball));

        for (Bumper bumper :
                pinball.getBumpers()) {
            if (bumper.getClass() == KickerBumper.class) {
                entities.add(GameEntityFactory.newKickerBumper(250+randomizer.nextInt(300), 50+randomizer.nextInt(350), 5, (KickerBumper) bumper));
            }
            else {
                entities.add(GameEntityFactory.newPopBumper(250+randomizer.nextInt(300), 50+randomizer.nextInt(350), 5, (PopBumper) bumper));
            }
        }

        for (Target target :
                pinball.getTargets()) {
            if (target.getClass() == SpotTarget.class) {
                entities.add(GameEntityFactory.newSpotTarget(250+randomizer.nextInt(300), 50+randomizer.nextInt(350), 10, (SpotTarget) target));
            }
            else {
                entities.add(GameEntityFactory.newDropTarget(250+randomizer.nextInt(300), 50+randomizer.nextInt(350), 10, (DropTarget) target));
            }
        }
        entities.add(GameEntityFactory.newBall());
        entities.add(GameEntityFactory.newLeftFlipper());
        entities.add(GameEntityFactory.newRightFlipper());

        for (Entity entity :
                entities) {
            getGameWorld().addEntity(entity);
        }

        if (!mute) {
            getAudioPlayer().stopMusic(background_song);
            getAudioPlayer().playMusic(background_song);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    //  Actions

    private UserAction ActivateLeftFlipperAction = new UserAction("Activate Left Flipper") {

        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperActiveState(e)));
        }

        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperInactiveState(e)));
        }

    };

    private UserAction ActivateRightFlipperAction = new UserAction("Activate Right Flipper") {

        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperActiveState(e)));
        }

        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperInactiveState(e)));
        }

    };

    private UserAction MakeBallAction = new UserAction("Make Ball") {

        @Override
        protected void onAction() {
            Entity ball = GameEntityFactory.newBall();
            getGameWorld().addEntity(ball);
        }

    };

    private UserAction NewTableAction = new UserAction("New Game Table") {

        @Override
        protected void onActionBegin() {
            getEventBus().fireEvent(new NewGameEvent(NewGameEvent.DEFAULT));
        }

    };

    //  Event Handlers

    private EventHandler<NewGameEvent> NewGameEventHandler = event -> resetAllEntitiesAndGame();

    //  Hit Event Handlers

    private EventHandler<HitEvent> HittableHit = event -> {
        Entity hit = event.getHitEntity();
        for (Node node :
                hit.getView().getNodes()) {
            Shape shape = (Shape)node;
            shape.setFill(Color.WHITE);
        }
        pinball.hit(hit.getComponent(HittableComponent.class).getHittable());
    };

    private EventHandler<HitEvent> BumperHit = event -> {

    };

    private EventHandler<HitEvent> TargetHit = event -> {

    };

    private EventHandler<HitEvent> KickerBumperHit = event -> {

    };

    private EventHandler<HitEvent> PopBumperHit = event -> {

    };

    private EventHandler<HitEvent> SpotTargetHit = event -> {

    };

    private EventHandler<HitEvent> DropTargetHit = event -> {

    };

    //  Collision Handlers

    private CollisionHandler KickerBumperCollisionHandler = new CollisionHandler(Types.BALL, Types.KICKER_BUMPER) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity bumper) {
            super.onCollisionBegin(ball, bumper);
            getEventBus().fireEvent(new HitEvent(HitEvent.KICKER_BUMPER, bumper));
        }

        @Override
        protected void onCollision(Entity ball, Entity bumper) {
            super.onCollision(ball, bumper);
        }

        @Override
        protected void onCollisionEnd(Entity ball, Entity bumper) {
            super.onCollisionEnd(ball, bumper);
        }

    };

    private CollisionHandler PopBumperCollisionHandler = new CollisionHandler(Types.BALL, Types.POP_BUMPER) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity bumper) {
            super.onCollisionBegin(ball, bumper);
            getEventBus().fireEvent(new HitEvent(HitEvent.POP_BUMPER, bumper));
        }

        @Override
        protected void onCollision(Entity ball, Entity bumper) {
            super.onCollision(ball, bumper);
        }

        @Override
        protected void onCollisionEnd(Entity ball, Entity bumper) {
            super.onCollisionEnd(ball, bumper);
        }

    };

    private CollisionHandler SpotTargetCollisionHandler = new CollisionHandler(Types.BALL, Types.SPOT_TARGET) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity target) {
            super.onCollisionBegin(ball, target);
            getEventBus().fireEvent(new HitEvent(HitEvent.SPOT_TARGET, target));
        }

        @Override
        protected void onCollision(Entity ball, Entity target) {
            super.onCollision(ball, target);
        }

        @Override
        protected void onCollisionEnd(Entity ball, Entity target) {
            super.onCollisionEnd(ball, target);
        }

    };

    private CollisionHandler DropTargetCollisionHandler = new CollisionHandler(Types.BALL, Types.DROP_TARGET) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity target) {
            super.onCollisionBegin(ball, target);
            getEventBus().fireEvent(new HitEvent(HitEvent.DROP_TARGET, target));
        }

        @Override
        protected void onCollision(Entity ball, Entity target) {
            super.onCollision(ball, target);
        }

        @Override
        protected void onCollisionEnd(Entity ball, Entity target) {
            super.onCollisionEnd(ball, target);
        }

    };

}
