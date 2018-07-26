package main;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.entity.Entities;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.RenderLayer;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.InputModifier;
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

public class Main extends GameApplication {

    public enum Types {
        EXPLORER,
        BALL,
        WALL
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setWidth(800);
        gameSettings.setHeight(600);
        gameSettings.setTitle("Example");
        gameSettings.setVersion("0.1");
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0.0, 100.0);
    }

    @Override
    protected void initGame() {
        Entity background = FXGLEntityFactory.newBackground();
        Entity walls = FXGLEntityFactory.newWalls();
        Entity ball = FXGLEntityFactory.newBall(400, 300);
        Entity explorer = FXGLEntityFactory.newExplorer();
        getGameWorld().addEntities(background, ball, walls, explorer);
    }

    @Override
    protected void initInput() {
        Input input = getInput();

        double explorer_speed = 200;
        double explorer_force = 1000;
        double explorer_push = 50;

        input.addAction(new UserAction("Make Ball") {
            @Override
            protected void onAction() {
                Entity ball = FXGLEntityFactory.newBall(400, 300);
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

    static class FXGLEntityFactory implements EntityFactory {

        static Entity newBackground() {
            return Entities.builder()
                    .viewFromNode(new Rectangle(800, 600, Color.GREEN))
                    .renderLayer(RenderLayer.BACKGROUND)
                    .build();
        }

        static Entity newWalls() {
            Entity wall = Entities.makeScreenBounds(100);
            wall.setType(Types.WALL);
            wall.addComponent(new CollidableComponent(true));
            return wall;
        }

        static Entity newBall(double x, double y) {
            PhysicsComponent physics = new PhysicsComponent();
            physics.setBodyType(BodyType.DYNAMIC);
            physics.setOnPhysicsInitialized(
                    () -> physics.setLinearVelocity(5*60, -5*60)
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

        static Entity newExplorer() {
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

}
