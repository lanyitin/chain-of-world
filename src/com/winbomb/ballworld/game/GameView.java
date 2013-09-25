package com.winbomb.ballworld.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.winbomb.ballworld.BallWorld;
import com.winbomb.ballworld.Setting;
import com.winbomb.ballworld.game.impl.StandardCreator;
import com.winbomb.ballworld.input.AccHandler;

public class GameView extends SurfaceView implements Runnable {

	private SurfaceHolder mHolder;
	private AccHandler mAccHandler;
	private Thread mThread = null;

	private BallWorld world;
	private WorldCreator creator;
	private WorldRender worldRender;
	private Handler mHandler;
	private Rect dstRect;

	volatile boolean running = true;

	private long lastPause;

	private long costTime;

	public GameView(Context context) {
		super(context);
		this.setKeepScreenOn(true);

		mHolder = this.getHolder();
		mAccHandler = new AccHandler(context);

		world = new BallWorld(Setting.WORLD_WIDTH, Setting.WORLD_HEIGHT,
				context);

		creator = new StandardCreator(world);
		world.setBallList(creator.createBalls());
		world.setHoles(creator.createHoles());

		worldRender = new WorldRender(world, Cocobox.SCREEN_WIDTH,
				Cocobox.SCREEN_HEIGHT);
		dstRect = worldRender.getDestRect();

		lastPause = System.currentTimeMillis();
		costTime = 0;
	}

	public void setHandler(Handler handler) {
		mHandler = handler;
	}

	@Override
	public void run() {
		lastPause = System.currentTimeMillis();
		long startTime = System.nanoTime();
		float remainingTime = 60;
		while (running) {

			float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;

			startTime = System.nanoTime();

			deltaTime = (deltaTime < Setting.MAX_DELTA_TIME) ? deltaTime
					: Setting.MAX_DELTA_TIME;

			costTime += System.currentTimeMillis() - lastPause;
			remainingTime = Math.max(remainingTime - deltaTime, 0);
			lastPause = System.currentTimeMillis();

			if (!mHolder.getSurface().isValid()) {
				continue;
			}

			Canvas canvas = mHolder.lockCanvas();

			float accX = -mAccHandler.getAccelX();
			float accY = mAccHandler.getAccelY();

			world.setGravity(accX, accY);
			world.step(deltaTime);

			Bitmap worldFrame = worldRender.drawWorldFrame(costTime,
					(int) remainingTime);
			canvas.drawBitmap(worldFrame, null, dstRect, null);

			mHolder.unlockCanvasAndPost(canvas);

			if (world.isFinished() || remainingTime == 0) {

				running = false;
				Message msg = mHandler.obtainMessage();
				Bundle data = new Bundle();
				data.putString("action", "pause");
				msg.setData(data);
				mHandler.sendMessage(msg);

				msg = mHandler.obtainMessage();
				data = new Bundle();
				data.putString("action", "showResult");
				data.putLong("TIME", costTime);
				msg.setData(data);
				costTime = 0;
				mHandler.sendMessage(msg);
			}
		}
	}

	public void pause() {
		running = false;
		while (true) {
			try {
				mThread.join();
				Log.w("game view", "pause");
				break;
			} catch (InterruptedException ex) {

			}
		}
	}

	public void resume(boolean resetBall) {
		running = true;
		lastPause = System.currentTimeMillis();
		if (resetBall) {
			costTime = 0;
			world.setBallList(creator.createBalls());
		}

		mThread = new Thread(this);
		mThread.start();
		Log.w("game view", "resume");
	}
	
	public int getRemainingBallCount() {
		return world.getRemaingBallsCount();
	}
	
	public int getMaxBallCount() {
		return this.creator.getMaxBallCount();
	}
}
