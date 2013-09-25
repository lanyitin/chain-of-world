package com.winbomb.ballworld.collision;

import com.winbomb.ballworld.Ball;
import com.winbomb.ballworld.BallWorld;
import com.winbomb.ballworld.Setting;
import com.winbomb.ballworld.common.Vec2;

public class Contact {

	private Ball srcBody;

	private Ball destBody;

	private Wall wall;

	private float pn;

	private float bias;

	private Vec2 normal;

	private float massNormal;

	private float separation;

	private float restitution;

	public Contact(Ball ball1, Ball ball2) {
		this.srcBody = ball1;
		this.destBody = ball2;
		this.wall = null;
		this.restitution = Math.min(ball1.getRestitution(),
				ball2.getRestitution());

		this.normal = ball1.getPosition().sub(ball2.getPosition());
		this.normal.normalize();

		float dx = ball1.getX() - ball2.getX();
		float dy = ball1.getY() - ball2.getY();
		float dis = (float) Math.sqrt((dx * dx + dy * dy));

		separation = dis - ball1.getRadius() - ball2.getRadius();

		massNormal = 1.0f / (srcBody.getInvMass() + destBody.getInvMass());
	}

	public Contact(Ball ball1, Wall wall) {
		this.srcBody = ball1;
		this.destBody = null;
		this.wall = wall;
		this.restitution = ball1.getRestitution();

		if (this.wall == Wall.LEFT_WALL) {
			normal = new Vec2(1, 0);
			separation = ball1.getX() - ball1.getRadius();
		} else if (this.wall == Wall.RIGHT_WALL) {
			normal = new Vec2(-1, 0);
			separation = ball1.getWorld().getWorldWidth()
					- (ball1.getX() + ball1.getRadius());
		} else if (this.wall == Wall.TOP_WALL) {
			normal = new Vec2(0, -1);
			separation = ball1.getWorld().getWorldHeight()
					- (ball1.getY() + ball1.getRadius());
		} else if (this.wall == Wall.BOTTOM_WALL) {
			normal = new Vec2(0, 1);
			separation = ball1.getY() - ball1.getRadius();
		}

		massNormal = ball1.getMass();
	}

	public void prepare() {
		bias = -Setting.BIAS_FACTOR
				* Math.min(0.0f, separation + Setting.ALLOW_PENETRATION);
		restitution = 0.3f;

		float dv = getNormalImpulse();
		if (dv < Setting.VELOCITY_THREADHOLD) {
			bias += -restitution * dv;
		}

		if (BallWorld.isAccumulateImpulses()) {
			// Apply normal + friction impulse
			Vec2 impulse = Vec2.cross(pn, normal);
			srcBody.getVelocity().subLocal(
					Vec2.cross(srcBody.getInvMass(), impulse));

			if (destBody != null) {
				destBody.getVelocity().addLocal(
						Vec2.cross(destBody.getInvMass(), impulse));
			}
		}
	}

	public void solve() {

		float dPn = massNormal * (-1 * getNormalImpulse() + bias);

		if (BallWorld.isAccumulateImpulses()) {
			float pn0 = pn;
			pn = Math.max(pn0 + dPn, 0f);
			dPn = pn - pn0;
		} else {
			dPn = (dPn > 0) ? dPn : 0;
		}

		Vec2 impulse = normal.mul(-dPn);
		srcBody.getVelocity().subLocal(impulse.mul(srcBody.getInvMass()));

		if (destBody != null) {
			destBody.getVelocity().addLocal(impulse.mul(destBody.getInvMass()));
		}
	}

	private float getNormalImpulse() {
		return Vec2.dot(getRelativeVelocity(), normal);
	}

	private Vec2 getRelativeVelocity() {

		if (destBody != null) {
			return srcBody.getVelocity().sub(destBody.getVelocity());
		}

		return srcBody.getVelocity();
	}
}
