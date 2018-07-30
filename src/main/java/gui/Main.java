package gui;

import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.audio.*;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.entity.components.RotationComponent;
import com.almasb.fxgl.extra.entity.state.State;
import com.almasb.fxgl.extra.entity.state.StateComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import com.almasb.fxgl.settings.GameSettings;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.LinkedList;
import java.util.List;

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

    //private Game pinball = new Game();
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
        getPhysicsWorld().setGravity(0.0, 150.0);
    }

    @Override
    protected void initGame() {
        List<Entity> entities = new LinkedList<>();

        entities.add(factory.newBackground());
        entities.add(factory.newTableWall());
        entities.add(factory.newTopLeftWall());
        entities.add(factory.newTopRightWall());
        entities.add(factory.newScoreCounter());
        entities.add(factory.newBallCounter());

        entities.add(factory.newKickerBumper(250, 300, 25));
        entities.add(factory.newPopBumper(350, 300, 25));
        entities.add(factory.newDropTarget(450, 300, 50));
        entities.add(factory.newSpotTarget(550, 300, 50));

        entities.add(factory.newBall());
        entities.add(factory.newLeftFlipper());
        entities.add(factory.newRightFlipper());

        for (Entity entity :
                entities) {
            getGameWorld().addEntity(entity);
        }

        //Music song = getAssetLoader().loadMusic("bensound-thelounge.mp3");
        //getAudioPlayer().setGlobalMusicVolume(0.1);
        //getAudioPlayer().playMusic(song);

    }

    @Override
    protected void initInput() {
        Input input = getInput();

        input.addAction(new ActivateLeftFlipperAction(), KeyCode.A);

        input.addAction(new ActivateRightFlipperAction(), KeyCode.D);

        input.addAction(new MakeBallAction(), KeyCode.SPACE);

    }

    public static void main(String[] args){
        launch(args);
    }

    //  Actions

    class ActivateLeftFlipperAction extends UserAction {

        ActivateLeftFlipperAction() {
            super("Activate Left Flipper");
        }

        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperActiveState(e)));
        }

        @Override
        protected void onAction() {
            super.onAction();
        }

        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.LEFT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new LeftFlipperInactiveState(e)));
        }

    }

    class ActivateRightFlipperAction extends UserAction {

        ActivateRightFlipperAction() {
            super("Activate Right Flipper");
        }

        @Override
        protected void onActionBegin() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperActiveState(e)));
        }

        @Override
        protected void onAction() {
            super.onAction();
        }

        @Override
        protected void onActionEnd() {
            getGameWorld().getEntitiesByType(Types.RIGHT_FLIPPER)
                    .forEach(e -> e.getComponent(DefaultStateComponent.class).setState(new RightFlipperInactiveState(e)));
        }

    }

    class MakeBallAction extends UserAction {

        MakeBallAction() {
            super("Make Ball");
        }

        @Override
        protected void onAction() {
            Entity ball = factory.newBall();
            getGameWorld().addEntity(ball);
        }

    }

    //  Factory

    class FXGLEntityFactory implements EntityFactory {

        Entity newBackground() {
            double w = (double) FXGL.getSettings().getWidth();
            double h = (double) FXGL.getSettings().getHeight();
            return Entities.builder()
                    .viewFromNode(new Rectangle(w, h, Color.BLACK))
                    .renderLayer(RenderLayer.BACKGROUND)
                    .build();
        }

        Entity newTableWall() {
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
                    .bbox(new HitBox("BOTTOM", new Point2D(0.0D, h), BoundingShape.box(w, thickness)))
                    .with(new PhysicsComponent(), new CollidableComponent(true))
                    .build();
        }

        Entity newTopLeftWall() {
            double x = 200;
            double y = 0;
            Polygon polygon = new Polygon(0, 0, 0, 50, 50, 0);
            polygon.setFill(Color.BLACK);
            return Entities.builder()
                    .type(Types.WALL)
                    .bbox(new HitBox("TOP LEFT", BoundingShape.polygon(new Point2D(0, 0), new Point2D(0, 50), new Point2D(50, 0))))
                    .viewFromNode(polygon)
                    .at(x, y)
                    .with(new PhysicsComponent(), new CollidableComponent(true))
                    .build();
        }

        Entity newTopRightWall() {
            double x = 600;
            double y = 0;
            Polygon polygon = new Polygon(0, 0, -50, 0, 0, 50);
            polygon.setFill(Color.BLACK);
            return Entities.builder()
                    .type(Types.WALL)
                    .bbox(new HitBox("TOP RIGHT", BoundingShape.polygon(new Point2D(0, 0), new Point2D(-50, 0), new Point2D(0, 50))))
                    .viewFromNode(polygon)
                    .at(x, y)
                    .with(new PhysicsComponent(), new CollidableComponent(true))
                    .build();
        }

        Entity newScoreCounter() {
            double x = 625;
            double y = 100;
            Text text = new Text("Score: ");
            text.setFont(new Font("Times New Roman", 25));
            text.setFill(Color.WHITE);
            return Entities.builder()
                    .at(x, y)
                    .viewFromNode(text)
                    .build();
        }

        Entity newBallCounter() {
            double x = 625;
            double y = 200;
            Text text = new Text("Balls: ");
            text.setFont(new Font("Times New Roman", 25));
            text.setFill(Color.WHITE);
            return Entities.builder()
                    .at(x, y)
                    .viewFromNode(text)
                    .build();
        }

        Entity newKickerBumper(double x, double y, double radius) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(1.1f)
                            .density(0.1f)
            );
            return Entities.builder()
                    .at(x, y)
                    .type(Types.KICKER_BUMPER)
                    .bbox(new HitBox("KickerBumper", BoundingShape.circle(radius)))
                    .viewFromNode(new Circle(radius, Color.PURPLE))
                    .with(physics, new CollidableComponent(true))
                    .build();
        }

        Entity newPopBumper(double x, double y, double radius) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(1.1f)
                            .density(0.1f)
            );
            return Entities.builder()
                    .at(x, y)
                    .type(Types.POP_BUMPER)
                    .bbox(new HitBox("PopBumper", BoundingShape.circle(radius)))
                    .viewFromNode(new Circle(radius, Color.ORANGE))
                    .with(physics, new CollidableComponent(true))
                    .build();
        }

        Entity newDropTarget(double x, double y, double size) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(1.1f)
                            .density(0.1f)
            );
            return Entities.builder()
                    .at(x, y)
                    .type(Types.DROP_TARGET)
                    .bbox(new HitBox("DropTarget", BoundingShape.box(size, size)))
                    .viewFromNode(new Rectangle(size, size, Color.DARKSEAGREEN))
                    .with(physics, new CollidableComponent(true))
                    .build();
        }

        Entity newSpotTarget(double x, double y, double size) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.STATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(1.1f)
                            .density(0.1f)
            );
            return Entities.builder()
                    .at(x, y)
                    .type(Types.SPOT_TARGET)
                    .bbox(new HitBox("SpotTarget", BoundingShape.box(size, size)))
                    .viewFromNode(new Rectangle(size, size, Color.DARKRED))
                    .with(physics, new CollidableComponent(true))
                    .build();
        }

        Entity newBall() {
            double x = (FXGL.getSettings().getWidth() * 0.72);
            double y = (FXGL.getSettings().getHeight() * 0.96);
            double speed = 400;
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

        double flipper_height = 20;
        double flipper_width = 150;
        float flipper_restitution = 1.0f;
        float flipper_density = 0.1f;
        float flipper_friction = 0f;

        Entity newLeftFlipper() {
            double x = 300;
            double y = 500;
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.KINEMATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(flipper_restitution)
                            .density(flipper_density)
                            .friction(flipper_friction)
            );
            Arc arc = new Arc();
            arc.setCenterX(flipper_width);
            arc.setCenterY(flipper_height /2);
            arc.setRadiusX(flipper_width);
            arc.setRadiusY(flipper_height);
            arc.setStartAngle(135.0f);
            arc.setLength(90.0f);
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.BLUE);
            Entity flipper = Entities.builder()
                    .at(x- flipper_width /2, y- flipper_height /2)
                    .type(Types.LEFT_FLIPPER)
                    .viewFromNodeWithBBox(arc)
                    .with(physics, new CollidableComponent(true))
                    .build();
            StateComponent states = new DefaultStateComponent(new LeftFlipperInactiveState(flipper));
            flipper.addComponent(states);
            return flipper;
        }

        Entity newRightFlipper() {
            double x = 500;
            double y = 500;
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.KINEMATIC);
            physics.setFixtureDef(
                    new FixtureDef()
                            .restitution(flipper_restitution)
                            .density(flipper_density)
                            .friction(flipper_friction)
            );
            Arc arc = new Arc();
            arc.setCenterX(0.0f);
            arc.setCenterY(flipper_height /2);
            arc.setRadiusX(flipper_width);
            arc.setRadiusY(flipper_height);
            arc.setStartAngle(315.0f);
            arc.setLength(90.0f);
            arc.setType(ArcType.ROUND);
            arc.setFill(Color.BLUE);
            Entity flipper = Entities.builder()
                    .at(x- flipper_width /2, y- flipper_height /2)
                    .type(Types.RIGHT_FLIPPER)
                    .viewFromNodeWithBBox(arc)
                    .with(physics, new CollidableComponent(true))
                    .build();
            StateComponent states = new DefaultStateComponent(new RightFlipperInactiveState(flipper));
            flipper.addComponent(states);
            return flipper;
        }

    }

    //  Collision Handler

    CollisionHandler KickerBumperCollisionHandler  = new CollisionHandler(Types.BALL, Types.KICKER_BUMPER) {

        @Override
        protected void onHitBoxTrigger(Entity ball, Entity bumper, HitBox boxBall, HitBox boxBumper) {
            super.onHitBoxTrigger(ball, bumper, boxBall, boxBumper);
        }

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

    //  StateComponent

    class DefaultStateComponent extends StateComponent {

        DefaultStateComponent(State initial_state) {
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

    //  Flipper States

    class FlipperActivationState extends State {

        private final Entity owner;
        private final boolean counter_clockwise;
        private final double speed_multiplier;
        private final double end_angle;
        private final double epsilon;

        private boolean is_displaced;
        private double distance;

        FlipperActivationState(Entity owner, boolean counter_clockwise, double end_angle) {
            this.owner = owner;
            this.counter_clockwise = counter_clockwise;
            this.speed_multiplier = 2.0;
            assert end_angle >= 0;
            this.end_angle = end_angle;
            this.epsilon = 0.1;
        }

        private void checkIfDisplaced() {
            //is_displaced = !owner.getComponent(RotationComponent.class).angleProperty().isEqualTo(counter_clockwise ? -end_angle : end_angle, epsilon).getValue();
            distance = Math.abs(owner.getComponent(RotationComponent.class).angleProperty().get() - (counter_clockwise ? -end_angle : end_angle));
            is_displaced = distance > epsilon;
        }

        @Override
        protected void onEnter(State prevState) {
            checkIfDisplaced();
            if (is_displaced) {
                owner.getComponent(PhysicsComponent.class).setAngularVelocity((counter_clockwise ? -speed_multiplier : speed_multiplier)*distance/10);
            }
        }

        @Override
        protected void onUpdate(double var1) {
            if (is_displaced) {
                checkIfDisplaced();
                if (!is_displaced) {
                    owner.getComponent(PhysicsComponent.class).setAngularVelocity(0);
                }
                else {
                    owner.getComponent(PhysicsComponent.class).setAngularVelocity((counter_clockwise ? -speed_multiplier : speed_multiplier)*distance/10);
                }
            }
        }

        @Override
        protected void onExit() {
            owner.getComponent(PhysicsComponent.class).setAngularVelocity(0);
        }

    }

    class LeftFlipperInactiveState extends FlipperActivationState {

        LeftFlipperInactiveState(Entity owner) {
            super(owner, false, 0);
        }

    }

    class LeftFlipperActiveState extends FlipperActivationState {

        LeftFlipperActiveState(Entity owner) {
            super(owner, true, 45);
        }

    }

    class RightFlipperInactiveState extends FlipperActivationState {

        RightFlipperInactiveState(Entity owner) {
            super(owner, true, 0);
        }

    }

    class RightFlipperActiveState extends FlipperActivationState {

        RightFlipperActiveState(Entity owner) {
            super(owner, false, 45);
        }

    }

}
