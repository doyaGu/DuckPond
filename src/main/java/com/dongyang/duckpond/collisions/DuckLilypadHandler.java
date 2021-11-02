package com.dongyang.duckpond.collisions;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.dongyang.duckpond.DuckpondType;
import com.dongyang.duckpond.components.DuckComponent;

/**
 * The handler to handle the collision between the duck and the lilypad.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public class DuckLilypadHandler extends CollisionHandler {
    public DuckLilypadHandler() {
        super(DuckpondType.DUCK, DuckpondType.LILYPAD);
    }

    @Override
    protected void onCollisionBegin(Entity duck, Entity lilypad) {
        duck.getComponent(DuckComponent.class).grow();
        lilypad.removeFromWorld();
    }
}
