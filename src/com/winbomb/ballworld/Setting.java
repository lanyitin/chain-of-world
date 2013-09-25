package com.winbomb.ballworld;

public interface Setting {

	public static final float EPSILON = 1e-5f;

	public static final float ALLOW_PENETRATION = 0.001f;

	public static final float BIAS_FACTOR = 10f;

	public static final float SCREEN_RATE = 40;

	public static final float VELOCITY_THREADHOLD = -200.0f;

	public static final float ESCAPE_VELOCITY = 100f;

	public static final int WORLD_WIDTH = 300;

	public static final int WORLD_HEIGHT = 440;

	public static final float GRAVITY_RATIO = 98 * 2;

	public static final float MAX_DELTA_TIME = 1 / 20.f;
}
