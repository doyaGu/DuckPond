package com.dongyang.duckpond.components;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.time.LocalTimer;
import com.dongyang.duckpond.events.HeadDuckQuackEvent;
import javafx.util.Duration;

import java.util.ArrayList;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;
import static com.dongyang.duckpond.DuckpondConfig.HEAD_DUCK_QUACK_INTERVAL;

/**
 * The head duck functions component for the duck entity.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */

public class HeadDuckComponent extends Component {
    private final ArrayList<Entity> followingDucks = new ArrayList<>();

    private LocalTimer quackTimer;
    private final Duration nextQuack = Duration.seconds(HEAD_DUCK_QUACK_INTERVAL);

    @Override
    public void onAdded() {
        quackTimer = FXGL.newLocalTimer();
        quackTimer.capture();
    }

    @Override
    public void onUpdate(double tpf) {
        if (quackTimer.elapsed(nextQuack)) {
            if (FXGLMath.randomBoolean(0.8f)) {
                quack();
            }
            quackTimer.capture();
        }
    }

    @Override
    public void onRemoved() {
        for (Entity duck : followingDucks) {
            duck.getComponent(DuckBehaviorComponent.class).cancelCurrentTargetHeadDuck();
        }
    }

    public ArrayList<Entity> getFollowingDucks() {
        return followingDucks;
    }

    public void quack() {
        play("quack" + random(1, 2) + ".wav");
        getEventBus().fireEvent(new HeadDuckQuackEvent(entity));
    }
}
