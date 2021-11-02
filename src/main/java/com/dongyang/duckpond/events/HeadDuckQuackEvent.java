package com.dongyang.duckpond.events;

import com.almasb.fxgl.entity.Entity;
import javafx.event.Event;
import javafx.event.EventType;

/**
 * The event sent by a head duck which will notify the surrounding ducks to follow the head duck.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public class HeadDuckQuackEvent extends Event {
    public static final EventType<HeadDuckQuackEvent> ANY
            = new EventType<>(Event.ANY, "HEAD_DUCK_QUACKED");

    private Entity duck;

    public Entity getDuck() {
        return duck;
    }

    public HeadDuckQuackEvent(Entity duck) {
        super(ANY);
        this.duck = duck;
    }
}
