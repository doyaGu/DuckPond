package com.dongyang.duckpond;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.LocalTimer;
import com.dongyang.duckpond.collisions.DuckLilypadHandler;
import com.dongyang.duckpond.collisions.DuckRockHandler;
import com.dongyang.duckpond.components.DuckBehaviorComponent;
import com.dongyang.duckpond.components.HeadDuckComponent;
import com.dongyang.duckpond.events.HeadDuckQuackEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.util.Duration;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.dongyang.duckpond.DuckpondConfig.*;
import static com.dongyang.duckpond.DuckpondType.DUCK;

/**
 * This is the main class of duckpond, a little game based on the FXGL game framework.
 * <p>
 * Unless explicitly stated, methods are not thread-safe and must be executed on the JavaFX Application (UI) Thread.
 * By default all callbacks are executed on the JavaFX Application (UI) Thread.
 * <p>
 * Assets taken from www.deviantart.com.
 *
 * @author doyaGu
 */
public class DuckpondApp extends GameApplication {

    private LocalTimer duckBornTimer;
    private final Duration nextDuckBorn = Duration.minutes(1);

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Duck Pond");
        settings.setVersion("0.0.1");

        settings.setWidth(WINDOW_WIDTH);
        settings.setHeight(WINDOW_HEIGHT);

        settings.getCredits().addAll(Arrays.asList(
                "NAME: doyaGu"
        ));
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.3);
        getSettings().setGlobalSoundVolume(0.4);

        loopBGM("water-sound.wav");
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("ducks", 0);
        vars.put("headDucks", 0);
        vars.put("lilypads", 0);
    }

    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new DuckpondFactory());

        getGameWorld().spawn("pond");

        ArrayList<Point2D> positions = getRandomPositions(INIT_DUCK_NUM + MAX_LILYPAD_NUM + MAX_ROCK_NUM);
        Iterator<Point2D> iter = positions.iterator();

        for (int i = 0; i < INIT_DUCK_NUM; i++) {
            Point2D pos = iter.next();
            getGameWorld().spawn("duck", pos.getX(), pos.getY());
        }

        for (int i = 0; i < MAX_LILYPAD_NUM; i++) {
            Point2D pos = iter.next();
            getGameWorld().spawn("lilypad", pos.getX(), pos.getY());
        }

        for (int i = 0; i < MAX_ROCK_NUM; i++) {
            Point2D pos = iter.next();
            getGameWorld().spawn("rock", pos.getX(), pos.getY());
        }

        duckBornTimer = FXGL.newLocalTimer();
        duckBornTimer.capture();

        getEventBus().addEventHandler(HeadDuckQuackEvent.ANY, this::onHeadDuckQuack);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().addCollisionHandler(new DuckLilypadHandler());
        getPhysicsWorld().addCollisionHandler(new DuckRockHandler());
    }

    @Override
    protected void onUpdate(double tpf) {
        if (duckBornTimer.elapsed(nextDuckBorn)) {
            if (geti("ducks") < MAX_DUCK_NUM) {
                Point2D pos = getRandomPositions(1).get(0);
                getGameWorld().spawn("duck", pos.getX(), pos.getY());
            }
            duckBornTimer.capture();
        }

        int currentLilypads = geti("lilypads");
        if (currentLilypads < MAX_LILYPAD_NUM) {
            int newLilypads = MAX_LILYPAD_NUM - currentLilypads;
            ArrayList<Point2D> positions = getRandomPositions(newLilypads);
            for (Point2D pos : positions) {
                getGameWorld().spawn("lilypad", pos.getX(), pos.getY());
            }
        }

    }

    private ArrayList<Point2D> getRandomPositions(int n) {
        Random random = new Random(System.currentTimeMillis());

        ArrayList<Point2D> points = new ArrayList<>();
        while (points.size() < n) {
            int x = random.nextInt(WINDOW_WIDTH / TILE_SIZE - 2) + 1;
            int y = random.nextInt(WINDOW_WIDTH / TILE_SIZE - 2) + 1;

            Point2D point = new Point2D(x * TILE_SIZE, y * TILE_SIZE);
            if (!points.contains(point)) {
                points.add(point);
            }
        }

        return points;
    }

    private void onHeadDuckQuack(HeadDuckQuackEvent event) {
        Entity headDuck = event.getDuck();
        Point2D position = headDuck.getPosition();

        // Get the surrounding ducks.
        List<Entity> entities = getGameWorld().getEntitiesInRange(new Rectangle2D(position.getX() - 200, position.getY() - 200, 400, 400));
        if (entities.size() != 0) {
            for (Entity e : entities) {
                // If a duck is not following a head duck, let it follow the quacking head duck.
                // Else let it stop to follow the quacking one.
                if (e.getType() == DUCK && !e.hasComponent(HeadDuckComponent.class)) {
                    if (e.getComponent(DuckBehaviorComponent.class).isFollowingHeadDuck()) {
                        e.getComponent(DuckBehaviorComponent.class).cancelCurrentTargetHeadDuck();
                    }
                    e.getComponent(DuckBehaviorComponent.class).followHeadDuck(headDuck);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}