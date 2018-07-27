package main;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.extra.entity.components.DraggableComponent;
import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.extra.entity.state.StateComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.LinkedList;
import java.util.List;

public class Main extends GameApplication {

    public enum Types {
        EXPLORER,
        BALL,
        WALL,
        LEFT_FLIPPER
    }

    //Game pinball = new Game();
    private FXGLEntityFactory factory = new FXGLEntityFactory();

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Pinball");
        gameSettings.setVersion("Pre- Alpha");
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0.0, 100.0);
    }

    @Override
    protected void initGame() {
        List<Entity> entities = new LinkedList<>();

        entities.add(factory.newBackground());
        entities.add(factory.newWall());

        entities.add(factory.newBall());
        entities.add(factory.newLeftFlipper());
        entities.add(factory.newExplorer());

        for (Entity entity :
                entities) {
            getGameWorld().addEntity(entity);
        }

        Music song = getAssetLoader().loadMusic("bensound-thelounge.mp3");
        getAudioPlayer().setGlobalMusicVolume(0.1);
        getAudioPlayer().playMusic(song);

    }

    @Override
    protected void initInput() {
        Input input = getInput();

        double explorer_force = 1000;
        double explorer_push = 50;

        input.addAction(new UserAction("Activate Left Flipper") {
            @Override
            protected void onActionBegin() {
                getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                        .forEach(e -> e.getComponent(LeftFlipperStateComponent.class).setState(new ActiveState(e)));
            }

            @Override
            protected void onAction() {
                super.onAction();
            }

            @Override
            protected void onActionEnd() {
                getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                        .forEach(e -> e.getComponent(LeftFlipperStateComponent.class).setState(new InactiveState(e)));
            }
        }, KeyCode.E);

        input.addAction(new UserAction("Make Ball") {
            @Override
            protected void onAction() {
                Entity ball = factory.newBall();
                getGameWorld().addEntity(ball);
            }
        }, KeyCode.SPACE);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onActionBegin() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(explorer_force, 0)));
            }
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(explorer_push, 0)));
            }
            @Override
            protected void onActionEnd() {
                //getGameWorld().getEntitiesByType(Types.EXPLORER)
                //        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(-explorer_force, 0)));
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onActionBegin() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(-explorer_force, 0)));
            }
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(-explorer_push, 0)));
            }
            @Override
            protected void onActionEnd() {
                //getGameWorld().getEntitiesByType(Types.EXPLORER)
                //        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(explorer_force, 0)));
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onActionBegin() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, -explorer_force)));
            }
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, -explorer_push)));
            }
            @Override
            protected void onActionEnd() {
                //getGameWorld().getEntitiesByType(Types.EXPLORER)
                //        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, explorer_force)));
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Move Down") {
            @Override
            protected void onActionBegin() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, explorer_force)));
            }
            @Override
            protected void onAction() {
                getGameWorld().getEntitiesByType(Types.EXPLORER)
                        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, explorer_push)));
            }
            @Override
            protected void onActionEnd() {
                //getGameWorld().getEntitiesByType(Types.EXPLORER)
                //        .forEach(e -> e.getComponent(PhysicsComponent.class).applyForceToCenter(new Point2D(0, -explorer_force)));
            }
        }, KeyCode.S);

    }

    public static void main(String[] args){
        launch(args);
    }

    class FXGLEntityFactory implements EntityFactory {

        Entity newBackground() {
            double w = (double) FXGL.getSettings().getWidth();
            double h = (double) FXGL.getSettings().getHeight();
            return Entities.builder()
                    .viewFromNode(new Rectangle(w, h, Color.BLACK))
                    .renderLayer(RenderLayer.BACKGROUND)
                    .build();
        }

        Entity newWall() {
            double w = (FXGL.getSettings().getWidth() * 0.5);
            double h = (double) FXGL.getSettings().getHeight();
            double thickness = 100;
            return Entities.builder()
                    .at((FXGL.getSettings().getWidth() * 0.25), 0)
                    .viewFromNode(new Rectangle(400, 600, Color.GREEN))
                    .renderLayer(RenderLayer.BACKGROUND)
                    .type(Types.WALL)
                    .bbox(new HitBox("LEFT", new Point2D(-thickness, 0.0D), BoundingShape.box(thickness, h)))
                    .bbox(new HitBox("RIGHT", new Point2D(w, 0.0D), BoundingShape.box(thickness, h)))
                    .bbox(new HitBox("TOP", new Point2D(0.0D, -thickness), BoundingShape.box(w, thickness)))
                    .bbox(new HitBox("BOT", new Point2D(0.0D, h), BoundingShape.box(w, thickness)))
                    .with(new PhysicsComponent(), new CollidableComponent(true))
                    .build();
        }

        Entity newBall() {
            double x = (FXGL.getSettings().getWidth() * 0.72);
            double y = (FXGL.getSettings().getHeight() * 0.96);
            double speed = 5*60;
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);
            physics.setOnPhysicsInitialized(
                    () -> physics.setLinearVelocity(0, -speed)
            );
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(0.5f)
                            .density(0.1f)
                            .friction(0.1f)
            );
            return Entities.builder()
                    .at(x, y)
                    .type(Types.BALL)
                    .bbox(new HitBox("Ball", BoundingShape.circle(10)))
                    .viewFromNode(new Circle(10, Color.RED))
                    .with(physics, new CollidableComponent(true))
                    .build();

        }

        Entity newLeftFlipper() {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.KINEMATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(1.1f)
                            .density(0.1f)
                            .friction(0f)
            );
            Entity flipper = Entities.builder()
                    .at(300, 500)
                    .type(Types.LEFT_FLIPPER)
                    .bbox(new HitBox("Left Flipper", BoundingShape.box(100, 40)))
                    .viewFromNode(new Rectangle(100, 40, Color.BLUE))
                    .with(physics, new CollidableComponent(true))
                    .build();
            StateComponent states = new LeftFlipperStateComponent(new InactiveState(flipper));
            flipper.addComponent(states);
            return flipper;
        }

        Entity newExplorer() {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(0.5f)
                            .density(0.1f)
                            .friction(0.1f)
            );
            return Entities.builder()
                    .at(400, 300)
                    .type(Types.EXPLORER)
                    .bbox(new HitBox("Explorer", BoundingShape.box(100, 50)))
                    .viewFromNode(new Rectangle(100, 50, Color.BLUE))
                    .with(physics, new CollidableComponent(true))
                    .build();
        }

    }

    class LeftFlipperStateComponent extends StateComponent {

        LeftFlipperStateComponent(State initial_state) {
            super(initial_state);
        }

        @Override
        public boolean isAllowStateReentrance() {
            return super.isAllowStateReentrance();
        }

        @Override
        public void setAllowStateReentrance(boolean allowStateReentrance) {
            super.setAllowStateReentrance(allowStateReentrance);
        }

        @Override
        protected void preUpdate(double tpf) {
            super.preUpdate(tpf);
        }
    }

    class InactiveState extends com.almasb.fxgl.extra.entity.state.State {

        private final Entity entity;

        InactiveState(Entity entity) {
            this.entity = entity;
        }

        private boolean is_displaced;

        private void updateIfDisplaced(Entity entity) {
            is_displaced = !entity.getComponent(RotationComponent.class).angleProperty().isEqualTo(0, 0.5).getValue();
        }

        @Override
        protected void onEnter(com.almasb.fxgl.extra.entity.state.State prevState) {
            updateIfDisplaced(entity);
            if (is_displaced) {
                entity.getComponent(PhysicsComponent.class).setAngularVelocity(-1.0);
            }
        }

        @Override
        protected void onUpdate(double var1) {
            if (is_displaced) {
                updateIfDisplaced(entity);
                if (!is_displaced) {
                    entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
                }
            }
        }

        @Override
        protected void onExit() {
            entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
        }

    }

    class ActiveState extends com.almasb.fxgl.extra.entity.state.State {

        private final Entity entity;

        ActiveState(Entity entity) {
            this.entity = entity;
        }

        private boolean is_displaced;

        private void updateIfDisplaced(Entity entity) {
            is_displaced = !entity.getComponent(RotationComponent.class).angleProperty().isEqualTo(45, 0.5).getValue();
        }

        @Override
        protected void onEnter(com.almasb.fxgl.extra.entity.state.State prevState) {
            updateIfDisplaced(entity);
            if (is_displaced) {
                entity.getComponent(PhysicsComponent.class).setAngularVelocity(1.0);
            }
        }

        @Override
        protected void onUpdate(double var1) {
            if (is_displaced) {
                updateIfDisplaced(entity);
                if (!is_displaced) {
                    entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
                }
            }
        }

        @Override
        protected void onExit() {
            entity.getComponent(PhysicsComponent.class).setAngularVelocity(0);
        }

    }

}
