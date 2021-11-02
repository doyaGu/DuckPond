package com.dongyang.duckpond;

/**
 * Configurations for the game.
 *
 * @author GUO Dongyang (288133@supinfo.com)
 */
public final class DuckpondConfig {
    private DuckpondConfig() {}

    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 800;

    public static final int TILE_SIZE = 32;

    public static final int INIT_DUCK_NUM = 5;

    public static final int MAX_DUCK_NUM = 10;
    public static final int MAX_LILYPAD_NUM = 20;
    public static final int MAX_ROCK_NUM = 5;

    public static final int DUCK_MOVE_SPEED = 20;
    public static final int MIN_HEAD_DUCK_WEIGHT = 500;
    public static final int HEAD_DUCK_QUACK_INTERVAL = 30;
    public static final int LILY_GROW_INTERVAL = 60;

}
