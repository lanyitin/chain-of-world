package com.winbomb.ballworld.game;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.StreamCorruptedException;
import java.util.Date;

import tw.edu.niu.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winbomb.ballworld.HighScore;

public class Game2 extends Activity {
	private GameView mGameView;
	private Handler mHandler;
	private LayoutInflater mInflater;
	private MediaPlayer mp;

	private boolean bResume = true;

	private static final int HIGH_SCORE_COUNT = 10;
	private static final String HIGH_SCORE_FILE = "highscore.data";
	private HighScore[] highScores = new HighScore[HIGH_SCORE_COUNT];

	private int ballNum = 0, intimacy = 0, money = 0, power = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);

		Cocobox.SCREEN_WIDTH = localDisplayMetrics.widthPixels;
		Cocobox.SCREEN_HEIGHT = localDisplayMetrics.heightPixels;

		Resources.initResources(this);

		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				Bundle data = msg.getData();
				String action = data.getString("action");
				if (action.equals("showResult")) {
					long costTime = data.getLong("TIME");
					showResult(costTime);
				} else if (action.equals("pause")) {
					pauseGame();
				}
			};
		};

		mGameView = new GameView(this);
		mGameView.setHandler(mHandler);

		loadHighScores();

		setContentView(mGameView);

		mInflater = LayoutInflater.from(this);

		// ----- 背景音樂 -----
		mp = MediaPlayer.create(this, R.raw.program);
		mp.start();
		mp.setLooping(true);
		// ----- END -----
	}

	@Override
	public void onPause() {
		super.onPause();
		pauseGame();

		if (isFinishing()) {
			writeHighScoreToFile();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		resumeGame();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		super.onCreateOptionsMenu(menu);

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);

		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);

		bResume = true;
		pauseGame();

		return true;
	}

	@Override
	public void onOptionsMenuClosed(Menu menu) {
		super.onOptionsMenuClosed(menu);

		if (bResume) {
			resumeGame();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		bResume = false;

		switch (item.getItemId()) {
		case R.id.quit:
			ShowMsgDialog("遊戲小叮嚀", "確定要離開救援隊伍？");
			return true;
		case R.id.about:
			showAboutInfo();
			return true;

		default:
			resumeGame();
			return false;
		}
	}

	// @Override
	// public boolean onTouchEvent(MotionEvent param) {
	// super.onTouchEvent(param);
	//
	// openOptionsMenu();
	// return true;
	// }

	private void showResult(long time) {

		ballNum = mGameView.getMaxBallCount()
				- mGameView.getRemainingBallCount();

		if (ballNum > 45) {
			showMsgDialog_Result("救援報告書", "任務結束囉！\n" + "成功送達：" + ballNum + "\n"
					+ "完成獎勵：" + "\n" + "信任 +" + ballNum + "\n" + "金錢 +"
					+ ballNum * 200 + "\n" + "能量消耗：500");
		} else if (ballNum <= 45 && ballNum > 15) {
			showMsgDialog_Result("救援報告書", "任務結束囉！\n" + "成功送達：" + ballNum + "\n"
					+ "完成獎勵：" + "\n" + "信任 +" + ballNum + "\n" + "金錢 +"
					+ ballNum * 100 + "\n" + "能量消耗：700");
		} else {
			showMsgDialog_Result("救援報告書", "任務結束囉！\n" + "成功送達：" + ballNum + "\n"
					+ "完成獎勵：" + "\n" + "信任 +" + ballNum + "\n" + "金錢 +"
					+ ballNum * 100 + "\n" + "能量消耗：1000");
		}

		// AlertDialog resultDlg = new AlertDialog.Builder(this)
		// .setTitle("救援報告書")
		// .setMessage(
		// "任務結束囉！\n" + "成功送達：" + ballNum + "\n" + "完成獎勵：" + "\n"
		// + "信任 +" + ballNum + "\n" + "金錢 +" + ballNum
		// * 10 + "\n" + "能量消耗：100")
		// .setPositiveButton("確定", new QuitGameListener()).create();
		//
		// resultDlg.show();
	}

	private boolean isHighScore(long time) {

		if (highScores[highScores.length - 1] == null) {
			return true;
		}

		return time < highScores[highScores.length - 1].getTimeCost();
	}

	// 遊戲教學
	private void showAboutInfo() {
		String teach_game2 = getResources().getString(R.string.teach_game2);
		showMsgDialog_Teach("遊戲教學", teach_game2);
	}

	private void loadHighScores() {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = openFileInput(HIGH_SCORE_FILE);
			ois = new ObjectInputStream(fis);

			HighScore high;
			int i = 0;
			while (i < HIGH_SCORE_COUNT
					&& (high = (HighScore) ois.readObject()) != null) {
				highScores[i++] = high;
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (StreamCorruptedException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} finally {
			closeQuietly(fis);
			closeQuietly(ois);
		}

	}

	private void showHighScores() {

		final View highScoreView = mInflater.inflate(
				R.layout.alert_dialog_high_score, null);
		ListView highScoreList = (ListView) highScoreView
				.findViewById(R.id.lstHighScore);

		HighScoreAdapter adapter = new HighScoreAdapter(this, highScores);
		highScoreList.setAdapter(adapter);

		AlertDialog highScoreDlg = new AlertDialog.Builder(this)
				.setTitle(R.string.highscore).setView(highScoreView)
				.setPositiveButton("確定", new ResumeGameListener()).create();

		highScoreDlg.show();

	}

	private void quitGame() {
		if (ballNum > 45) {

			intimacy = ballNum * 2;
			money = ballNum * 200;
			power = -500;

			Toast.makeText(getApplicationContext(), "救援大成功！獲得獎勵加倍！！耗費體力減半！！！",
					Toast.LENGTH_SHORT).show();

			// ----- PHONE VIBRATE -----
			Vibrator myvibrator = (Vibrator) getApplication().getSystemService(
					android.app.Service.VIBRATOR_SERVICE);
			myvibrator.vibrate(500);
			// ----- END -----

		} else if (ballNum <= 45 && ballNum > 15) {
			intimacy = ballNum;
			money = ballNum * 100;
			power = -700;

			Toast.makeText(getApplicationContext(), "救援成功！", Toast.LENGTH_SHORT)
					.show();
		} else {
			intimacy = ballNum;
			money = ballNum * 100;
			power = -1000;

			Toast.makeText(getApplicationContext(), "救援完成！", Toast.LENGTH_SHORT)
					.show();
		}

		Intent intent = new Intent();
		intent.putExtra("intimacyOffset", intimacy);
		intent.putExtra("moneyOffset", money);
		intent.putExtra("powerOffset", power);
		setResult(Activity.RESULT_OK, intent);
		this.finish();
	}

	private void resumeGame() {
		if (!mp.isPlaying()) {
			mp.start();
		}
		mGameView.resume(false);
	}

	private void pauseGame() {
		if (mp.isPlaying()) {
			mp.pause();
		}
		mGameView.pause();
	}

	private void writeHighScoreToFile() {

		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = openFileOutput(HIGH_SCORE_FILE, 0);
			oos = new ObjectOutputStream(fos);

			for (HighScore highScore : highScores) {
				oos.writeObject(highScore);
			}

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			closeQuietly(fos);
			closeQuietly(oos);
		}
	}

	private void closeQuietly(InputStream is) {

		if (is != null) {
			try {
				is.close();
			} catch (Exception ex) {

			}
		}
	}

	private void closeQuietly(OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (Exception ex) {

			}
		}
	}

	class ResumeGameListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			resumeGame();
		}
	}

	class QuitGameListener implements OnClickListener {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			quitGame();
		}
	}

	class HighScoreUpdateListener implements OnClickListener {

		private long time;
		private TextView txtPlayer;

		public HighScoreUpdateListener(long time, TextView txtPalyer) {
			this.time = time;
			this.txtPlayer = txtPalyer;
		}

		@Override
		public void onClick(DialogInterface dialog, int which) {

			int pos;
			for (pos = 0; pos < highScores.length; pos++) {
				if (highScores[pos] == null
						|| time < highScores[pos].getTimeCost()) {
					break;
				}
			}

			for (int i = highScores.length - 1; i > pos; i--) {
				highScores[i] = highScores[i - 1];
			}

			HighScore high = new HighScore();
			high.setTimeCost(time);
			high.setPlayTime(new Date());
			if (txtPlayer == null || txtPlayer.getText() == null
					|| "".equals(txtPlayer.getText())) {
				high.setPlayer("匿名");
			} else {
				high.setPlayer(txtPlayer.getText().toString());
			}

			highScores[pos] = high;

			quitGame();
		}

	}

	public void onBackPressed() {
		super.onDestroy();
		ShowMsgDialog("遊戲小叮嚀", "確定要離開救援隊伍？");
	}

	private void ShowMsgDialog(String title, String msg) {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(title);
		MyAlertDialog.setMessage(msg);
		MyAlertDialog.setPositiveButton("掉頭離開！",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
					}
				});
		MyAlertDialog.setNegativeButton("參與救援！",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						resumeGame();
					}
				});
		MyAlertDialog.show();
	}

	// 遊戲教學
	private void showMsgDialog_Teach(String Title, String Msg) {
		Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle(Title);
		myAlertDialog.setMessage(Msg);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				resumeGame();
			}
		};
		myAlertDialog.setNeutralButton("確定", OkClick);
		myAlertDialog.show();
	}

	// 遊戲結果
	private void showMsgDialog_Result(String Title, String Msg) {
		AlertDialog resultDlg = new AlertDialog.Builder(this).setTitle(Title)
				.setMessage(Msg)
				.setPositiveButton("確定", new QuitGameListener()).create();

		resultDlg.show();
	}

}