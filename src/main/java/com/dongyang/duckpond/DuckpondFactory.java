package com.dongyang.duckpond;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.dongyang.duckpond.components.DuckBehaviorComponent;
import com.dongyang.duckpond.components.DuckComponent;
import com.dongyang.duckpond.components.LilypadComponent;
import com.dongyang.duckpond.components.MoveComponent;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.dongyang.duckpond.DuckpondConfig.*;
import static com.dongyang.duckpond.DuckpondType.*;

/**
 * Factory for creating in-game entities.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public final class DuckpondFactory implements EntityFactory {

    @Spawns("pond")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .at(0, 0)
                .view(texture("pond.png", WINDOW_WIDTH, WINDOW_HEIGHT))
                .zIndex(-1)
                .build();
    }

    @Spawns("duck")
    public Entity newDuck(SpawnData data) {
        return entityBuilder()
                .type(DUCK)
                .from(data)
                .bbox(new HitBox(new Point2D(25,30), BoundingShape.circle(12)))
                .with(new CollidableComponent(true))
                .with(new DuckComponent())
                .with(new MoveComponent())
                .with(new DuckBehaviorComponent())
                .build();
    }

    @Spawns("lilypad")
    public Entity newLilypad(SpawnData data) {
        return entityBuilder()
                .type(LILYPAD)
                .from(data)
                .bbox(new HitBox(new Point2D(15,15), BoundingShape.circle(16)))
                .with(new CollidableComponent(true))
                .with(new LilypadComponent())
                .build();
    }

    @Spawns("rock")
    public Entity newRock(SpawnData data) {
        return entityBuilder()
                .type(ROCK)
                .from(data)
                .viewWithBBox("rock" + random(1, 2) + ".png")
                .with(new CollidableComponent(true))
                .build();
    }
}
