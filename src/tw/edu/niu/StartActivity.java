package tw.edu.niu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Application;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class StartActivity extends Activity {

	private Button btnStart;
	private Button btnExit;
	private Button btnTeach;
	private Button btnAbout;
	private MediaPlayer mp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.start);

		btnStart = (Button) findViewById(R.id.button1);
		btnExit = (Button) findViewById(R.id.button2);
		btnTeach = (Button) findViewById(R.id.button3);
		btnAbout = (Button) findViewById(R.id.button4);

		// ----- 背景音樂 -----
		mp = MediaPlayer.create(this, R.raw.opening);
		mp.start();
		mp.setLooping(true);
		// ----- END -----

		btnStart.setEnabled(false);
		btnExit.setEnabled(false);
		btnAbout.setEnabled(false);
		
		Toast.makeText(getApplicationContext(), "請先閱讀遊戲說明喔！",
				Toast.LENGTH_SHORT).show();

		btnStart.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(StartActivity.this, MainActivity.class);
				startActivity(intent);
				StartActivity.this.finish();

			}

		});

		btnExit.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}

		});

		btnTeach.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String story = getResources().getString(R.string.story);
				showMsgDialog("前情提要", story);
				
				btnStart.setEnabled(true);
				btnExit.setEnabled(true);
				btnAbout.setEnabled(true);
			}

		});

		btnAbout.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				String writer = getResources().getString(R.string.writer);
				showMsgDialog("遊戲製作", writer);
			}

		});

	}

	private void showMsgDialog(String Title, String Msg) {
		Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle(Title);
		myAlertDialog.setMessage(Msg);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(getApplicationContext(), "歡迎來到連鎖世界CHAIN OF WORLD！",
						Toast.LENGTH_SHORT).show();
			}
		};
		myAlertDialog.setNeutralButton("確定", OkClick);
		myAlertDialog.show();
	}

	public void onPause() {
		super.onPause();
		mp.reset();
	}

}
