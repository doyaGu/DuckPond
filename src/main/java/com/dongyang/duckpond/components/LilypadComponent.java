package com.dongyang.duckpond.components;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.dongyang.duckpond.DuckpondConfig.LILY_GROW_INTERVAL;

/**
 * The lilypad animations component for the lilypad entity.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public class LilypadComponent extends Component {
    private AnimatedTexture texture;

    private ArrayList<AnimationChannel> animations;

    public LilypadComponent() {
        Image image = image("lilypads.png");

        animations = new ArrayList<>();
        animations.add(new AnimationChannel(image, 3, 32, 32, Duration.seconds(LILY_GROW_INTERVAL), 0, 2));
        animations.add(new AnimationChannel(image, 3, 32, 32, Duration.seconds(LILY_GROW_INTERVAL), 3, 5));
        animations.add(new AnimationChannel(image, 3, 32, 32, Duration.seconds(LILY_GROW_INTERVAL), 6, 8));
        animations.add(new AnimationChannel(image, 3, 32, 32, Duration.seconds(LILY_GROW_INTERVAL), 9, 11));
        animations.add(new AnimationChannel(image, 3, 32, 32, Duration.seconds(LILY_GROW_INTERVAL), 12, 14));

        texture = new AnimatedTexture(animations.get(random(0, animations.size() - 1)));
        texture.loop();
        texture.setOnCycleFinished(() -> entity.removeFromWorld());
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        inc("lilypads", 1);
    }

    @Override
    public void onRemoved() {
        inc("lilypads", -1);
    }
}
