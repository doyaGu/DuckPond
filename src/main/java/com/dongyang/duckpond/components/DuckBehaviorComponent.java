package com.dongyang.duckpond.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.dongyang.duckpond.DuckpondType;
import javafx.geometry.Point2D;

/**
 * The behavior component for the duck entity.
 * A duck will find a lilypad to eat and follow a head duck.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */

public class DuckBehaviorComponent extends Component {
    private Entity targetLilypad;
    private Entity targetHeadDuck;
    private int posInFollowingDucks;

    @Override
    public void onUpdate(double tpf) {
        if (targetHeadDuck == null) {
            if (targetLilypad == null || !targetLilypad.isActive()) {
                targetLilypad = entity.getWorld()
                        .getRandom(DuckpondType.LILYPAD)
                        .orElse(null);
            } else {
                entity.getComponent(MoveComponent.class).moveTo(targetLilypad);
            }
        } else if (targetHeadDuck.isActive()) {
            Point2D vector = targetHeadDuck.getComponent(MoveComponent.class).getVector();
            Point2D pos = targetHeadDuck.getPosition().subtract(vector.normalize().multiply(54 * posInFollowingDucks));

            entity.getComponent(MoveComponent.class).moveTo(pos);
        } else {
            targetHeadDuck = null;
        }
    }

    public void followHeadDuck(Entity targetHeadDuck) {
        this.targetHeadDuck = targetHeadDuck;
        targetHeadDuck.getComponent(HeadDuckComponent.class).getFollowingDucks().add(entity);
        posInFollowingDucks = targetHeadDuck.getComponent(HeadDuckComponent.class).getFollowingDucks().indexOf(entity) + 1;
    }

    public boolean isFollowingHeadDuck() {
        return targetHeadDuck != null;
    }

    public void cancelCurrentTargetLilypad() {
        targetLilypad = null;
    }

    public void cancelCurrentTargetHeadDuck() {
        if (targetHeadDuck != null) {
            targetHeadDuck.getComponent(HeadDuckComponent.class).getFollowingDucks().remove(entity);
            targetHeadDuck = null;
        }
    }
}
