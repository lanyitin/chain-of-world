package com.winbomb.ballworld.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Bitmap.Config;
import android.graphics.Paint.Style;

import com.winbomb.ballworld.Ball;
import com.winbomb.ballworld.BallWorld;
import com.winbomb.ballworld.Hole;

public class WorldRender {

	private static final float MARGIN_LEFT = 10f;
	private static final float MARGIN_RIGHT = 10f;
	private static final float MARGIN_TOP = 30f;
	private static final float MARGIN_BOTTOM = 10f;

	private BallWorld world;
	private Paint holePaint;
	private Paint timerPaint;

	private Canvas canvas;
	private Bitmap frameBuffer;

	private int holeNum;
	private int ballInHole;

	private float worldWidth;

	private float worldHeight;

	private float boxWidth;

	private float boxHeight;

	private float canvasWidth;

	private float canvasHeight;

	private float renderWidth;

	private float renderHeight;

	private float offsetX;

	private float offsetY;

	private float scale;

	public WorldRender(BallWorld world, int canvasWidth, int canvasHeight) {

		this.world = world;
		this.canvasWidth = canvasWidth;
		this.canvasHeight = canvasHeight;
		this.worldWidth = world.getWorldWidth();
		this.worldHeight = world.getWorldHeight();
		this.boxWidth = this.worldWidth + MARGIN_LEFT + MARGIN_RIGHT;
		this.boxHeight = this.worldHeight + MARGIN_TOP + MARGIN_BOTTOM;

		float ratioX = (float) canvasWidth / boxWidth;
		float ratioY = (float) canvasHeight / boxHeight;

		if (ratioY >= ratioX) {
			scale = ratioX;
			renderWidth = canvasWidth;
			renderHeight = this.boxHeight * scale;
			offsetX = 0;
			offsetY = (canvasHeight - renderHeight) / 2;
		} else {
			scale = ratioY;
			renderHeight = canvasHeight;
			renderWidth = this.boxWidth * scale;
			offsetY = 0;
			offsetX = (canvasWidth - renderWidth) / 2;
		}

		this.frameBuffer = Bitmap.createBitmap((int) renderWidth,
				(int) renderHeight, Config.RGB_565);
		this.canvas = new Canvas(frameBuffer);

		holePaint = new Paint();
		holePaint.setStyle(Style.FILL);
		holePaint.setColor(Color.BLACK);

		timerPaint = new Paint();
		timerPaint.setStyle(Style.FILL);
		timerPaint.setTextSize(18);
		timerPaint.setColor(Color.WHITE);
		timerPaint.setTextScaleX(scale);
	}

	public Bitmap drawWorldFrame(long costTime, long remainingTime) {

		holeNum = 0;
		ballInHole = 0;

		drawBackground();

		if (world.getHoles() != null) {

			holeNum = world.getHoles().length;
			for (Hole hole : world.getHoles()) {
				drawHole(hole);
			}
		}

		if (world.getBallList() != null) {
			for (Ball ball : world.getBallList()) {
				if (ball.isInHole()) {
					ballInHole++;
				}
				drawBall(ball);
			}
		}

		drawGameInfo(costTime, remainingTime);

		return frameBuffer;
	}

	private void drawBackground() {

		canvas.drawColor(Color.WHITE);

		Rect dst = new Rect(0, 0, (int) renderWidth, (int) renderHeight);
		canvas.drawBitmap(Resources.imgBg, null, dst, null);
	}

	private void drawGameInfo(long timeCost, long remainingTime) {

		float t = timeCost / 1000f;

		canvas.drawText("時間：" + String.valueOf(remainingTime), 10 * scale,
				20 * scale, timerPaint);
		canvas.drawText("數量：" + world.getRemaingBallsCount(), renderWidth / 2
				+ 80 * scale, 20 * scale, timerPaint);
	}

	private void drawBall(Ball ball) {
		float x = (ball.getX() - ball.getRadius() + MARGIN_LEFT) * scale;
		float y = (ball.getY() - ball.getRadius() + MARGIN_TOP) * scale;

		if (ball.getTexture() == null) {
			int dstSize = (int) (ball.getRadius() * 2 * scale);
			Bitmap texture = Bitmap.createScaledBitmap(Resources.imgBall,
					dstSize, dstSize, false);

			ball.setTexture(texture);
		}

		canvas.drawBitmap(ball.getTexture(), x, y, null);
	}

	private void drawHole(Hole hole) {
		float x = (hole.getX() - hole.getRadius() + MARGIN_LEFT) * scale;
		float y = (hole.getY() - hole.getRadius() + MARGIN_TOP) * scale;

		if (hole.getTexture() == null) {
			int dstSize = (int) (hole.getRadius() * 2 * scale);
			Bitmap texture = Bitmap.createScaledBitmap(Resources.imgHole,
					dstSize, dstSize, false);

			hole.setTexture(texture);
		}

		canvas.drawBitmap(hole.getTexture(), x, y, null);
	}

	public float getOffsetX() {
		return offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public Rect getDestRect() {
		float left = getOffsetX();
		float top = getOffsetY();
		float right = canvasWidth - getOffsetX();
		float bottom = canvasHeight - getOffsetY();

		return new Rect((int) left, (int) top, (int) right, (int) bottom);
	}
}
