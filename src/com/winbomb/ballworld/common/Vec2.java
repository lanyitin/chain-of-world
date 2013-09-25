package com.winbomb.ballworld.common;

public class Vec2 {
	public float x;
	public float y;

	public Vec2() {
		this(0, 0);
	}

	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void set(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public float length() {
		return (float) Math.sqrt(x * x + y * y);
	}

	public Vec2 add(Vec2 v) {
		return new Vec2(x + v.x, y + v.y);
	}

	public Vec2 addLocal(Vec2 v) {
		x += v.x;
		y += v.y;
		return this;
	}

	public Vec2 sub(Vec2 v) {
		return new Vec2(x - v.x, y - v.y);
	}

	public Vec2 subLocal(Vec2 v) {
		x -= v.x;
		y -= v.y;
		return this;
	}

	public Vec2 mul(float s) {
		return new Vec2(s * x, s * y);
	}

	public float normalize() {
		float length = length();
		if (length < 0.001) {
			return 0f;
		}

		float invLength = 1.0f / length;
		x *= invLength;
		y *= invLength;
		return length;
	}

	public static float dot(Vec2 a, Vec2 b) {
		return a.x * b.x + a.y * b.y;
	}

	public static Vec2 cross(float s, Vec2 a) {
		return new Vec2(-s * a.y, s * a.x);
	}

	public static Vec2 scalarProduct(float s, Vec2 v) {
		return new Vec2(s * v.x, s * v.y);
	}

	public static float distance(Vec2 a, Vec2 b) {
		float dx = a.x - b.x;
		float dy = a.y - b.y;

		return (float) Math.sqrt(dx * dx + dy * dy);
	}

	public static float length(Vec2 a) {
		return (float) Math.sqrt(a.x * a.x + a.y * a.y);
	}
}
