package gui.FXGLentities;

import controller.Game;
import gui.FXGLentities.components.HittableComponent;
import gui.FXGLentities.states.counter_states.BallCounterUpdatingState;
import gui.FXGLentities.states.counter_states.ScoreCounterUpdatingState;
import gui.FXGLentities.states.DefaultStateComponent;
import gui.FXGLentities.states.flipper_states.LeftFlipperInactiveState;
import gui.FXGLentities.states.flipper_states.RightFlipperInactiveState;
import gui.PinballGameApplication.Types;
import com.almasb.fxgl.app.FXGL;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.extra.entity.state.StateComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.physics.box2d.dynamics.FixtureDef;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import logic.gameelements.bumper.KickerBumper;
import logic.gameelements.bumper.PopBumper;
import logic.gameelements.target.DropTarget;
import logic.gameelements.target.SpotTarget;

public class PinballEntityFactory implements EntityFactory {

    public static Entity newBackground() {
        double w = (double) FXGL.getSettings().getWidth();
        double h = (double) FXGL.getSettings().getHeight();
        return Entities.builder()
                .type(Types.BACKGROUND)
                .viewFromNode(new Rectangle(w, h, Color.BLACK))
                .renderLayer(RenderLayer.BACKGROUND)
                .build();
    }

    public static Entity newTableWall() {
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
                .with(new PhysicsComponent(), new CollidableComponent(true))
                .build();
    }

    public static Entity newBottomWall() {
        double w = (FXGL.getSettings().getWidth() * 0.5);
        double h = (double) FXGL.getSettings().getHeight();
        double thickness = 100;
        return Entities.builder()
                .at((FXGL.getSettings().getWidth() * 0.25), 0)
                .renderLayer(RenderLayer.BACKGROUND)
                .type(Types.BOTTOM_WALL)
                .bbox(new HitBox("BOTTOM", new Point2D(0.0D, h), BoundingShape.box(w, thickness)))
                .with(new PhysicsComponent(), new CollidableComponent(true))
                .build();
    }

    public static Entity newTopLeftWall() {
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

    public static Entity newTopRightWall() {
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

    public static Node newWhiteText(String contents) {
        Text text = new Text(contents);
        text.setFont(new Font("Times New Roman", 25));
        text.setFill(Color.WHITE);
        return text;
    }

    public static Entity newScoreCounter(Game game) {
        double x = 625;
        double y = 100;
        Entity counter = Entities.builder()
                .at(x, y)
                .type(Types.SCORE_COUNTER)
                .build();
        counter.addComponent(new DefaultStateComponent(new ScoreCounterUpdatingState(counter, game)));
        return counter;
    }

    public static Entity newBallCounter(Game game) {
        double x = 625;
        double y = 200;
        Entity counter = Entities.builder()
                .at(x, y)
                .type(Types.BALLS_COUNTER)
                .build();
        counter.addComponent(new DefaultStateComponent(new BallCounterUpdatingState(counter, game)));
        return counter;
    }

    public static Entity newKickerBumper(double x, double y, double radius, KickerBumper owner) {
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
                .with(physics, new CollidableComponent(true), new HittableComponent(owner))
                .build();
    }

    public static Entity newPopBumper(double x, double y, double radius, PopBumper owner) {
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
                .with(physics, new CollidableComponent(true), new HittableComponent(owner))
                .build();
    }

    public static Entity newDropTarget(double x, double y, double size, DropTarget owner) {
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
                .with(physics, new CollidableComponent(true), new HittableComponent(owner))
                .build();
    }

    public static Entity newSpotTarget(double x, double y, double size, SpotTarget owner) {
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
                .with(physics, new CollidableComponent(true), new HittableComponent(owner))
                .build();
    }

    private static double ball_speed = 500;

    public static Entity newBall() {
        double x = (FXGL.getSettings().getWidth() * 0.72);
        double y = (FXGL.getSettings().getHeight() * 0.96);
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC);
        physics.setOnPhysicsInitialized(
                () -> physics.setLinearVelocity(0, -ball_speed)
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

    private static double flipper_height = 20;
    private static double flipper_width = 150;
    private static float flipper_restitution = 1.0f;
    private static float flipper_density = 0.1f;
    private static float flipper_friction = 0f;

    public static Entity newLeftFlipper() {
        double x = 300;
        double y = 500;
        //  Physics
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        physics.setFixtureDef(
                new FixtureDef()
                        .restitution(flipper_restitution)
                        .density(flipper_density)
                        .friction(flipper_friction)
        );
        //  Node
        Arc arc = new Arc();
        arc.setCenterX(flipper_width);
        arc.setCenterY(flipper_height /2);
        arc.setRadiusX(flipper_width);
        arc.setRadiusY(flipper_height);
        arc.setStartAngle(135.0f);
        arc.setLength(90.0f);
        arc.setType(ArcType.ROUND);
        arc.setFill(Color.BLUE);
        //  HitBox
        HitBox box = new HitBox(BoundingShape.polygon(
                new Point2D(flipper_width, flipper_height /2),
                new Point2D(flipper_width*0.25, flipper_height),
                new Point2D(0, flipper_height /2),
                new Point2D(flipper_width*0.25, 0)));
        //  Entity
        Entity flipper = Entities.builder()
                .at(x- flipper_width /2, y- flipper_height /2)
                .type(Types.LEFT_FLIPPER)
                .viewFromNode(arc)
                .bbox(box)
                .with(physics, new CollidableComponent(true))
                .rotate(20)
                .build();
        //  State
        StateComponent states = new DefaultStateComponent(new LeftFlipperInactiveState(flipper));
        flipper.addComponent(states);
        return flipper;
    }

    public static Entity newRightFlipper() {
        double x = 500;
        double y = 500;
        //  Physics
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.KINEMATIC);
        physics.setFixtureDef(
                new FixtureDef()
                        .restitution(flipper_restitution)
                        .density(flipper_density)
                        .friction(flipper_friction)
        );
        //  Node
        Arc arc = new Arc();
        arc.setCenterX(0.0f);
        arc.setCenterY(flipper_height /2);
        arc.setRadiusX(flipper_width);
        arc.setRadiusY(flipper_height);
        arc.setStartAngle(315.0f);
        arc.setLength(90.0f);
        arc.setType(ArcType.ROUND);
        arc.setFill(Color.BLUE);
        //  HitBox
        HitBox box = new HitBox(BoundingShape.polygon(
                new Point2D(0, flipper_height /2),
                new Point2D(flipper_width*0.75, flipper_height),
                new Point2D(flipper_width, flipper_height /2),
                new Point2D(flipper_width*0.75, 0)));
        //  Entity
        Entity flipper = Entities.builder()
                .at(x- flipper_width /2, y- flipper_height /2)
                .type(Types.RIGHT_FLIPPER)
                .viewFromNode(arc)
                .bbox(box)
                .with(physics, new CollidableComponent(true))
                .rotate(-20)
                .build();
        //  State
        StateComponent states = new DefaultStateComponent(new RightFlipperInactiveState(flipper));
        flipper.addComponent(states);
        return flipper;
    }

}
