package tw.edu.niu.pet.work;

import tw.edu.niu.MainActivity;
import tw.edu.niu.R;
import tw.edu.niu.pet.work.SQLiteDB;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Game1 extends Activity {
	private SQLiteDB mySQLiteDB;
	private Cursor myCursor;
	private TextView tvTitle;
	private Button btnAns, btnNext, btnSent;
	private ListView lvQuestion;
	private Spinner spWorkPlace;
	private MediaPlayer mp;
	int i = 0, j = 0, k = 0;
	int intimacy = 0, money = 0, power = 0;

	String[] que;
	String[] ans;
	String[][] opt;
	String[] knowledge;
	String choice = "";
	String[] exam = { "地點1：核能發電廠", "地點2：火力發電廠", "地點3：水力發電廠", "地點4：風力發電廠",
			"地點5：潮汐發電廠", "地點6：太陽能發電" };
	String[] exitMsg = { "工作結束囉！", "P.s. menu有離開按鈕喔！" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game1);

		tvTitle = (TextView) findViewById(R.id.textView1);
		btnAns = (Button) findViewById(R.id.Button01);
		btnNext = (Button) findViewById(R.id.Button02);
		btnSent = (Button) findViewById(R.id.Button03);
		lvQuestion = (ListView) findViewById(R.id.listView1);
		spWorkPlace = (Spinner) findViewById(R.id.spinner1);

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				exam);
		spWorkPlace.setAdapter(adapter);
		mySQLiteDB = new SQLiteDB(this);
		btnAns.setEnabled(false);
		btnNext.setEnabled(false);

		// ----- 背景音樂 -----
		mp = MediaPlayer.create(this, R.raw.work);
		mp.start();
		mp.setLooping(true);
		// ----- END -----

		spWorkPlace.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				i = 0;
				j = arg2;
				myCursor = mySQLiteDB.select(j);
				que = new String[myCursor.getCount()];
				ans = new String[myCursor.getCount()];
				knowledge = new String[myCursor.getCount()];
				opt = new String[myCursor.getCount()][4];
				myCursor.moveToFirst();
				for (int i = 0; i < que.length; i++) {
					if (myCursor.getString(6).length() == 1)
						que[i] = Integer.toString(i + 1) + "."
								+ myCursor.getString(1);
					else if (myCursor.getString(6).length() > 1)
						que[i] = Integer.toString(i + 1) + "."
								+ myCursor.getString(1) + "(複選)";
					else {
					}
					opt[i][0] = "(A) " + myCursor.getString(2);
					opt[i][1] = "(B) " + myCursor.getString(3);
					opt[i][2] = "(C) " + myCursor.getString(4);
					opt[i][3] = "(D) " + myCursor.getString(5);
					ans[i] = myCursor.getString(6);
					knowledge[i] = myCursor.getString(7);
					myCursor.moveToNext();
				}
				tvTitle.setText(que[0]);
				listviewUpgrade(0);

				Toast.makeText(getApplicationContext(),
						"目前選擇" + exam[j] + "(請作答)", Toast.LENGTH_SHORT).show();

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
			}

		});

		btnAns.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				ShowMsgDialog("能源小學堂", knowledge[i], "我懂了！");
				// ----- 學知識賺獎金 -----
				Toast.makeText(getApplicationContext(), "學知識賺獎金！金錢+50",
						Toast.LENGTH_SHORT).show();
				money += 50;
				// ----- END -----

				// ----- BUTTON -----
				btnAns.setEnabled(false);
				// ----- END -----
			}
		});

		btnNext.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				i++;
				if (i < que.length) {
					tvTitle.setText(que[i]);
					// ----- BUTTON CONTROLL -----
					btnSent.setEnabled(true);
					btnAns.setEnabled(false);
					btnNext.setEnabled(false);
					// ----- END -----
				} else {
					i = que.length - 1;
					tvTitle.setText(que[i]);

					for (k = 0; k < 2; k++) {
						Toast.makeText(getApplicationContext(), exitMsg[k],
								Toast.LENGTH_SHORT).show();
					}

					ShowMsgDialog("能源小學堂", "信任 + ( " + intimacy + " % )" + "\n"
							+ "金錢 + ( " + money + " NT$ )" + "\n" + "能量 + ( "
							+ power + " W )", "領薪水！");

					// ----- BUTTON CONTROLL -----
					btnNext.setEnabled(false);
					btnSent.setEnabled(false);
					btnAns.setEnabled(false);
					// ----- END -----
				}
				listviewUpgrade(i);
				choice = "";
			}
		});

		btnSent.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (choice.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Oops！別摸魚！信任度-5 金錢-50 能量-50 (請選擇答案)",
							Toast.LENGTH_SHORT).show();

					// ----- PHONE VIBRATE -----
					Vibrator myvibrator = (Vibrator) getApplication()
							.getSystemService(
									android.app.Service.VIBRATOR_SERVICE);
					myvibrator.vibrate(100);
					// ----- END -----

					intimacy -= 5;
					money -= 1000;
					power -= 1000;

				} else if (choice.equals(ans[i])) {
					Toast.makeText(getApplicationContext(),
							"答對了！信任度+10 金錢+100 能量-100 (請選擇:下一題或學知識)",
							Toast.LENGTH_SHORT).show();

					intimacy += 10;
					money += 2000;
					power -= 2000;

					// ----- BUTTON CONTROLL -----
					btnSent.setEnabled(false);
					btnAns.setEnabled(true);
					btnNext.setEnabled(true);
					spWorkPlace.setEnabled(false);
					// ----- END -----
				} else {
					Toast.makeText(
							getApplicationContext(),
							"答錯了！答案是" + ans[i]
									+ " 信任度-10 金錢-100 能量-100 (請選擇:下一題或學知識)",
							Toast.LENGTH_SHORT).show();

					// ----- PHONE VIBRATE -----
					Vibrator myvibrator = (Vibrator) getApplication()
							.getSystemService(
									android.app.Service.VIBRATOR_SERVICE);
					myvibrator.vibrate(500);
					// ----- END -----

					intimacy -= 10;
					money -= 2000;
					power -= 2000;

					// ----- BUTTON CONTROLL -----
					btnSent.setEnabled(false);
					btnAns.setEnabled(true);
					btnNext.setEnabled(true);
					// ----- END -----
				}

			}

		});

		lvQuestion.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				SparseBooleanArray choiceArray = lvQuestion
						.getCheckedItemPositions();
				choice = "";
				if (choiceArray.get(0)) {
					choice = choice + "A";
				}
				if (choiceArray.get(1)) {
					choice = choice + "B";
				}
				if (choiceArray.get(2)) {
					choice = choice + "C";
				}
				if (choiceArray.get(3)) {
					choice = choice + "D";
				}

			}

		});

	}

	public void listviewUpgrade(int i) {
		if (ans[i].length() == 1) {
			lvQuestion.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_single_choice, opt[i]);
			lvQuestion.setAdapter(adapter);
		} else if (ans[i].length() > 1) {
			lvQuestion.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					getApplicationContext(),
					android.R.layout.simple_list_item_single_choice, opt[i]);
			lvQuestion.setAdapter(adapter);
		}
	}

	private void ShowMsgDialog(String title, String msg, String btnText) {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(title);
		MyAlertDialog.setMessage(msg);
		DialogInterface.OnClickListener buttonClick = new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		MyAlertDialog.setNegativeButton(btnText, buttonClick);
		MyAlertDialog.show();
	}

	private void showMsgDialog_Teach(String Title, String Msg) {
		Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle(Title);
		myAlertDialog.setMessage(Msg);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		};
		myAlertDialog.setNeutralButton("確定", OkClick);
		myAlertDialog.show();
	}

	// ----- RULE AND EXIT -----
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 0, "教學");
		menu.add(0, 1, 1, "離開");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			// 遊戲教學
			String teach_game1 = getResources().getString(R.string.teach_game1);
			showMsgDialog_Teach("遊戲教學", teach_game1);
			break;
		case 1:
			Intent intent = new Intent();
			intent.putExtra("intimacyOffset", intimacy);
			intent.putExtra("moneyOffset", money);
			intent.putExtra("powerOffset", power);
			setResult(Activity.RESULT_OK, intent);
			finish();
			break;

		default:
		}
		return super.onOptionsItemSelected(item);
	}

	// ----- END -----

	public void onPause() {
		super.onPause();
		mp.reset();
	}

	/** CATCH EVENT WITH HARDWARE BACK BUTTON */
	public void onBackPressed() {
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "Oops！工作中請勿離開崗位！信任度-50",
				Toast.LENGTH_SHORT).show();

		// ----- PHONE VIBRATE -----
		Vibrator myvibrator = (Vibrator) getApplication().getSystemService(
				android.app.Service.VIBRATOR_SERVICE);
		myvibrator.vibrate(100);
		// ----- END -----

		intimacy = -50;
		Intent intent = new Intent();
		intent.putExtra("intimacyOffset", intimacy);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}

}
