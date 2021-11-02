package com.dongyang.duckpond.components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.LocalTimer;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.image;
import static com.almasb.fxgl.dsl.FXGL.inc;
import static com.dongyang.duckpond.DuckpondConfig.MIN_HEAD_DUCK_WEIGHT;

/**
 * The main attributes and duck animations component for the duck entity.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */

public class DuckComponent extends Component {
    private final AnimatedTexture texture;
    private AnimationChannel animDown, animLeft, animRight, animUp;

    private LocalTimer liveTimer;
    private final Duration nextWeightLose = Duration.seconds(30);

    private int weight = 100;

    public DuckComponent() {
        Image image = image("duck.png");
        initAnimationFromImage(image);

        texture = new AnimatedTexture(animDown);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);

        liveTimer = FXGL.newLocalTimer();
        liveTimer.capture();

        inc("ducks", 1);
    }

    @Override
    public void onRemoved() {
        inc("ducks", -1);
    }

    @Override
    public void onUpdate(double tpf) {
        if (liveTimer.elapsed(nextWeightLose)) {
            lessen();
            liveTimer.capture();
        }

        if (weight == 0) {
            die();
        }
    }

    private void initAnimationFromImage(Image image) {
        animDown = new AnimationChannel(image, 3, 48, 48, Duration.seconds(1), 0, 2);
        animLeft = new AnimationChannel(image, 3, 48, 48, Duration.seconds(1), 3, 5);
        animRight = new AnimationChannel(image, 3, 48, 48, Duration.seconds(1), 6, 8);
        animUp = new AnimationChannel(image, 3, 48, 48, Duration.seconds(1), 9, 11);
    }

    public void changeAnimationByDirection(MoveDirection direction) {
        switch (direction) {
            case UP:
                if (texture.getAnimationChannel() != animUp) {
                    texture.loopAnimationChannel(animUp);
                }
                break;
            case DOWN:
                if (texture.getAnimationChannel() != animDown) {
                    texture.loopAnimationChannel(animDown);
                }
                break;
            case LEFT:
                if (texture.getAnimationChannel() != animLeft) {
                    texture.loopAnimationChannel(animLeft);
                }
                break;
            case RIGHT:
                if (texture.getAnimationChannel() != animRight) {
                    texture.loopAnimationChannel(animRight);
                }
                break;
            default:
                break;
        }
    }

    public void lessen() {
        weight -= 50;

        // Be smaller.
        if (weight <= MIN_HEAD_DUCK_WEIGHT + 200) {
            texture.getTransforms().add(new Scale(0.95f, 0.95f, 0));
        }
    }

    public void grow() {
        weight += 50;


        // Get bigger.
        if (weight <= MIN_HEAD_DUCK_WEIGHT + 200) {
            texture.getTransforms().add(new Scale(1.05f, 1.05f, 0));
        }

        // Become a head duck.
        if (!entity.hasComponent(HeadDuckComponent.class) && weight >= MIN_HEAD_DUCK_WEIGHT) {
            entity.getComponent(DuckBehaviorComponent.class).cancelCurrentTargetHeadDuck();
            entity.addComponent(new HeadDuckComponent());
            Image image = image("head_duck.png");
            initAnimationFromImage(image);
        }
    }

    public void die() {
        entity.removeFromWorld();
    }
}
