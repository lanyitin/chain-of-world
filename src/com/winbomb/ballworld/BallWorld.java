package com.winbomb.ballworld;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tw.edu.niu.R;
import android.content.Context;
import android.media.MediaPlayer;

import com.winbomb.ballworld.collision.Contact;
import com.winbomb.ballworld.collision.Wall;
import com.winbomb.ballworld.common.Vec2;

public class BallWorld {
	private Context context;
	private MediaPlayer mp;

	private static boolean accumulateImpulses = true;

	private static boolean warmStarting = true;

	private static boolean positionCorrection = true;

	private Vec2 gravity;

	private int iterationNum;

	private List<Ball> ballList;

	private Hole[] holes;

	private float worldWidth;

	private float worldHeight;

	private List<Contact> contacts;

	private Random random;

	public BallWorld(Context context) {
		this.iterationNum = 10;
		this.contacts = new ArrayList<Contact>();
		this.ballList = new ArrayList<Ball>();
		this.gravity = new Vec2();
		this.worldWidth = 0f;
		this.worldHeight = 0f;
		this.random = new Random();
		this.context = context;
	}

	public BallWorld(float width, float height, Context context) {
		this(context);

		this.worldWidth = width;
		this.worldHeight = height;
	}

	public void step(float dt) {

		checkForHoles();

		checkForContacts();

		for (Ball ball : ballList) {
			if (ball.getInvMass() == 0.f) {
				continue;
			}

			Vec2 deltaVel = gravity.mul(dt);
			deltaVel.addLocal(ball.getForce().mul(dt));
			ball.getVelocity().addLocal(deltaVel);
		}

		for (Contact contact : contacts) {
			contact.prepare();
		}

		for (int i = 0; i < iterationNum; i++) {
			for (Contact contact : contacts) {
				contact.solve();
			}
		}

		for (Ball ball : ballList) {

			ball.getPosition().addLocal(ball.getVelocity().mul(dt));
			ball.clearForce();
		}
	}

	public boolean isFinished() {

		if (ballList == null) {
			return false;
		}

		for (Ball ball : ballList) {
			if (!ball.isInHole()) {
				return false;
			}
		}

		return true;

	}

	private void checkForHoles() {
		if (holes == null || holes.length == 0) {
			return;
		}
		ArrayList<Ball> toRemove = new ArrayList<Ball>();
		for (Ball ball : ballList) {

			boolean isInHole = false;

			for (Hole hole : holes) {

				if (ball.isInHole(hole)) {

					ball.setPosition(hole.getX(), hole.getY());

					Vec2 vec = gravity.mul(1);
					vec.addLocal(ball.getVelocity());

					float randFactor = random.nextFloat() * 0.5f + 0.5f;
					if (Vec2.length(vec) < Setting.ESCAPE_VELOCITY * randFactor) {
						ball.clearVelocity();
					}

					isInHole = true;
					toRemove.add(ball);
					break;
				}
			}

			ball.setInHole(isInHole);
		}
		for (Ball ball : toRemove) {
			ballList.remove(ball);
			// ----- 背景音樂 -----
			mp = MediaPlayer.create(this.context, R.raw.laser);
			mp.start();
			// mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
			// {
			//
			// @Override
			// public void onCompletion(MediaPlayer mp) {
			// mp.start();
			// }
			// });
			// ----- END -----
		}
	}

	private void checkForContacts() {

		contacts.clear();

		for (int i = 0; i < ballList.size(); i++) {

			Ball srcBody = ballList.get(i);

			checkForCollideWalls(srcBody);

			for (int j = i + 1; j < ballList.size(); j++) {
				Ball destBody = ballList.get(j);

				if (srcBody.getInvMass() == 0f && destBody.getInvMass() == 0f) {
					continue;
				}

				if (srcBody.isCollideBall(destBody)) {
					Contact contact = new Contact(srcBody, destBody);
					contacts.add(contact);
				}
			}
		}
	}

	private void checkForCollideWalls(Ball srcBody) {

		if (srcBody.isCollideLeftWall()) {
			Contact contact = new Contact(srcBody, Wall.LEFT_WALL);
			contacts.add(contact);
		}

		if (srcBody.isCollideRightWall()) {
			Contact contact = new Contact(srcBody, Wall.RIGHT_WALL);
			contacts.add(contact);
		}

		if (srcBody.isCollideTopWall()) {
			Contact contact = new Contact(srcBody, Wall.TOP_WALL);
			contacts.add(contact);
		}

		if (srcBody.isCollideBottomWall()) {
			Contact contact = new Contact(srcBody, Wall.BOTTOM_WALL);
			contacts.add(contact);
		}
	}

	public void setGravity(float gx, float gy) {
		this.gravity.set(gx, gy);
	}

	public float getWorldWidth() {
		return worldWidth;
	}

	public void setWorldWidth(float worldWidth) {
		this.worldWidth = worldWidth;
	}

	public float getWorldHeight() {
		return worldHeight;
	}

	public void setWorldHeight(float worldHeight) {
		this.worldHeight = worldHeight;
	}

	public static boolean isAccumulateImpulses() {
		return accumulateImpulses;
	}

	public static void setAccumulateImpulses(boolean accumulateImpulses) {
		BallWorld.accumulateImpulses = accumulateImpulses;
	}

	public static boolean isWarmStarting() {
		return warmStarting;
	}

	public static void setWarmStarting(boolean warmStarting) {
		BallWorld.warmStarting = warmStarting;
	}

	public static boolean isPositionCorrection() {
		return positionCorrection;
	}

	public static void setPositionCorrection(boolean positionCorrection) {
		BallWorld.positionCorrection = positionCorrection;
	}

	public List<Ball> getBallList() {
		return ballList;
	}

	public void setBallList(List<Ball> ballList) {
		this.ballList = ballList;

		for (Ball ball : ballList) {
			ball.setWorld(this);
		}
	}

	public Hole[] getHoles() {
		return holes;
	}

	public void setHoles(Hole[] holes) {
		this.holes = holes;
	}

	public int getRemaingBallsCount() {
		return this.ballList.size();
	}
}
