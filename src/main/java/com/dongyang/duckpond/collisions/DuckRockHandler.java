package com.dongyang.duckpond.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.dongyang.duckpond.DuckpondType;
import com.dongyang.duckpond.components.DuckBehaviorComponent;

/**
 * The handler to handle the collision between the duck and the rock.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public class DuckRockHandler extends CollisionHandler {
    public DuckRockHandler() {
        super(DuckpondType.DUCK, DuckpondType.ROCK);
    }

    @Override
    protected void onCollisionBegin(Entity duck, Entity rock) {
        duck.getComponent(DuckBehaviorComponent.class).cancelCurrentTargetLilypad();
    }
}
