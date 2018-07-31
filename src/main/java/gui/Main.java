package gui;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.settings.GameSettings;
import controller.Game;
import gui.FXGLentities.States.DefaultStateComponent;
import gui.FXGLentities.States.FlipperStates.LeftFlipperActiveState;
import gui.FXGLentities.States.FlipperStates.LeftFlipperInactiveState;
import gui.FXGLentities.States.FlipperStates.RightFlipperActiveState;
import gui.FXGLentities.States.FlipperStates.RightFlipperInactiveState;
import gui.FXGLentities.GameEntityFactory;
import javafx.scene.input.KeyCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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

    }

    @Override
    protected void preInit() {}

    @Override
    protected void initGameVars(Map<String, Object> vars) {}

    @Override
    protected void initGame() {
        List<Entity> entities = new LinkedList<>();

        entities.add(GameEntityFactory.newBackground());
        entities.add(GameEntityFactory.newTableWall());
        entities.add(GameEntityFactory.newTopLeftWall());
        entities.add(GameEntityFactory.newTopRightWall());
        entities.add(GameEntityFactory.newScoreCounter(pinball));
        entities.add(GameEntityFactory.newBallCounter(pinball));

        entities.add(GameEntityFactory.newKickerBumper(250, 300, 10));
        entities.add(GameEntityFactory.newPopBumper(350, 300, 10));
        entities.add(GameEntityFactory.newDropTarget(450, 300, 20));
        entities.add(GameEntityFactory.newSpotTarget(550, 300, 20));

        entities.add(GameEntityFactory.newBall());
        entities.add(GameEntityFactory.newLeftFlipper());
        entities.add(GameEntityFactory.newRightFlipper());

        for (Entity entity :
                entities) {
            getGameWorld().addEntity(entity);
        }

        Music song = getAssetLoader().loadMusic("bensound-thelounge.mp3");
        getAudioPlayer().setGlobalMusicVolume(0.1);
        getAudioPlayer().playMusic(song);

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
    protected void onUpdate(double tpf) {}

    @Override
    protected void onPostUpdate(double tpf) {}

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

    //  Factory


    //  Collision Handlers

    private CollisionHandler KickerBumperCollisionHandler = new CollisionHandler(Types.BALL, Types.KICKER_BUMPER) {

        @Override
        protected void onCollisionBegin(Entity ball, Entity bumper) {
            super.onCollisionBegin(ball, bumper);
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
