package com.dongyang.duckpond.components;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.dongyang.duckpond.DuckpondConfig.DUCK_MOVE_SPEED;

/**
 * The movement component for the duck entity.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */

public class MoveComponent extends Component {
    private Entity target;
    private Point2D vector = Point2D.ZERO;

    private double tpf = 0;

    @Override
    public void onUpdate(double tpf) {
        this.tpf = tpf;

        if (target != null) {
            if (entity.distance(target) <= 16) {
                entity.setPosition(target.getPosition());
                entity.getComponent(DuckBehaviorComponent.class).cancelCurrentTargetLilypad();
                target = null;
            } else {
                setDirection();
                entity.translate(vector);
            }
        }
    }

    private void setDirection() {
        if (vector.getY() < 0) {
            entity.getComponent(DuckComponent.class).changeAnimationByDirection(MoveDirection.UP);
        } else if (vector.getY() > 0) {
            entity.getComponent(DuckComponent.class).changeAnimationByDirection(MoveDirection.DOWN);
        }
        if (Math.abs(vector.getX()) > Math.abs(vector.getY())) {
            if (vector.getX() < 0) {
                entity.getComponent(DuckComponent.class).changeAnimationByDirection(MoveDirection.LEFT);
            } else if (vector.getX() > 0) {
                entity.getComponent(DuckComponent.class).changeAnimationByDirection(MoveDirection.RIGHT);
            }
        }
    }

    public Point2D getVector() {
        return vector;
    }

    public void moveTo(Entity e) {
        target = e;
        vector = e.getPosition().subtract(entity.getPosition()).normalize().multiply(DUCK_MOVE_SPEED * tpf);
    }

    public void moveTo(Point2D point) {
        vector = point.subtract(entity.getPosition()).normalize().multiply(DUCK_MOVE_SPEED * tpf);
    }
}
