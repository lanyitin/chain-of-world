package tw.edu.niu;

import tw.edu.niu.pet.OnPetIntimacyChangeListener;
import tw.edu.niu.pet.OnPetLevelUpListener;
import tw.edu.niu.pet.OnPetMoneyChangeListener;
import tw.edu.niu.pet.OnPetPowerChangeListener;
import tw.edu.niu.pet.Pet;
import tw.edu.niu.pet.feed.AbsPetFeed;
import tw.edu.niu.pet.feed.NuclearFeed;
import tw.edu.niu.pet.feed.FireFeed;
import tw.edu.niu.pet.feed.WaterFeed;
import tw.edu.niu.pet.feed.WindFeed;
import tw.edu.niu.pet.feed.SunFeed;
import tw.edu.niu.pet.feed.SeaFeed;
import tw.edu.niu.pet.work.Game1;

import com.winbomb.ballworld.game.Game2;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnPetLevelUpListener,
		OnPetPowerChangeListener, OnPetMoneyChangeListener,
		OnPetIntimacyChangeListener {
	public static int REQUEST_SPORT = Integer.MAX_VALUE;
	public TextView tvLevel;
	public TextView tvIntimacy;
	public TextView tvPower;
	public TextView tvMoney;
	public Button btnWork;
	public Button btnRecord;
	public ImageButton imgbtnAtom;
	public ImageButton imgbtnFire;
	public ImageButton imgbtnWater;
	public ImageButton imgbtnWind;
	public ImageButton imgbtnSun;
	public ImageButton imgbtnSea;
	public ImageView image;
	private Pet pet;
	private AbsPetFeed nuclearFeed;
	private AbsPetFeed fireFeed;
	private AbsPetFeed waterFeed;
	private AbsPetFeed windFeed;
	private AbsPetFeed sunFeed;
	private AbsPetFeed seaFeed;
	private String[] countGame1 = { "GO！", "請選擇工作地點？", "預設為地點1:核能發電廠",
			"P.s. menu有遊戲教學喔！" };
	private String[] countGame2 = { "GO！", "請上下左右搖動手機或平板，將電力送至洞穴中以幫助遇難的背包客",
			"P.s. menu有遊戲教學喔！" };
	private MediaPlayer mp;
	private String record, gameFinished;
	private String fences_pet, fences_game, record_teach_main, record_game1,
			record_game2;
	int i = 0;
	double nuclear = 0, fire = 0, water = 0, wind = 0, sun = 0, sea = 0,
			nuclearPercent = 0, firePercent = 0, waterPercent = 0,
			windPercent = 0, sunPercent = 0, seaPercent = 0;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		pet = new Pet();
		pet.setLevelUpListener(this);
		pet.setMoneyChangeListner(this);
		pet.setPowerChangeListener(this);
		pet.setIntimacyChangeListener(this);

		/** 強制玩家閱讀遊戲教學 */
		nuclearFeed = new NuclearFeed();
		fireFeed = new FireFeed();
		waterFeed = new WaterFeed();
		windFeed = new WindFeed();
		sunFeed = new SunFeed();
		seaFeed = new SeaFeed();

		init_UI_Elements();
		onWindowFocusChanged(true);
		bgMusic();

		imgbtnAtom.setEnabled(false);
		imgbtnFire.setEnabled(false);
		imgbtnSea.setEnabled(false);
		imgbtnSun.setEnabled(false);
		imgbtnWater.setEnabled(false);
		imgbtnWind.setEnabled(false);
		btnWork.setEnabled(false);
		btnRecord.setEnabled(false);

		Toast.makeText(getApplicationContext(), "請先閱讀menu的遊戲教學喔！",
				Toast.LENGTH_SHORT).show();

		fences_pet = getResources().getString(R.string.fence_pet);
		fences_game = getResources().getString(R.string.fence_game);
		record_teach_main = "世界初探：?????(未獲得)";
		record_game1 = "新任員工：?????(未獲得)";
		record_game2 = "救援部隊：?????(未獲得)";

		btnRecord.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				switch (pet.getLevel()) {
				case 1:
					record = fences_pet + "\n" + "等級1：我是一隻小小蟲！" + "\n"
							+ "等級2：?????(未獲得)" + "\n" + "等級3：?????(未獲得)" + "\n"
							+ "等級4：?????(未獲得)" + "\n" + "等級5：?????(未獲得)" + "\n"
							+ fences_game + "\n" + record_teach_main + "\n"
							+ record_game1 + "\n" + record_game2;
					break;
				case 2:
					record = fences_pet + "\n" + "等級1：我是一隻小小蟲！" + "\n"
							+ "等級2：翻滾吧！海豹" + "\n" + "等級3：?????(未獲得)" + "\n"
							+ "等級4：?????(未獲得)" + "\n" + "等級5：?????(未獲得)" + "\n"
							+ fences_game + "\n" + record_teach_main + "\n"
							+ record_game1 + "\n" + record_game2;
					break;
				case 3:
					record = fences_pet + "\n" + "等級1：我是一隻小小蟲！" + "\n"
							+ "等級2：翻滾吧！海豹" + "\n" + "等級3：人家才不會喵喵叫？" + "\n"
							+ "等級4：?????(未獲得)" + "\n" + "等級5：?????(未獲得)" + "\n"
							+ fences_game + "\n" + record_teach_main + "\n"
							+ record_game1 + "\n" + record_game2;
					break;
				case 4:
					record = fences_pet + "\n" + "等級1：我是一隻小小蟲！" + "\n"
							+ "等級2：翻滾吧！海豹" + "\n" + "等級3：人家才不會喵喵叫？" + "\n"
							+ "等級4：大熊維尼愛吃魚？" + "\n" + "等級5：?????(未獲得)" + "\n"
							+ fences_game + "\n" + record_teach_main + "\n"
							+ record_game1 + "\n" + record_game2;
					break;
				case 5:
					record = fences_pet + "\n" + "等級1：我是一隻小小蟲！" + "\n"
							+ "等級2：翻滾吧！海豹" + "\n" + "等級3：人家才不會喵喵叫？" + "\n"
							+ "等級4：大熊維尼愛吃魚？" + "\n" + "等級5：百獸之王就是我！" + "\n"
							+ fences_game + "\n" + record_teach_main + "\n"
							+ record_game1 + "\n" + record_game2;
					break;
				default:
				}

				ShowMsgDialog_Record("收集冊", record, "確定");
			}

		});

		/** Pet Working */
		btnWork.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {

				new AlertDialog.Builder(MainActivity.this)
						.setTitle(R.string.alert_title)
						.setItems(R.array.item_sport_dialog,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										switch (which) {
										case 0:

											for (i = 0; i < 4; i++) {
												Toast.makeText(
														getApplicationContext(),
														countGame1[i],
														Toast.LENGTH_SHORT)
														.show();
											}

											startActivityForResult(new Intent(
													MainActivity.this,
													Game1.class), REQUEST_SPORT);

											record_game1 = "新任員工：熱血新人王！";

											break;

										case 1:

											for (i = 0; i < 3; i++) {
												Toast.makeText(
														getApplicationContext(),
														countGame2[i],
														Toast.LENGTH_SHORT)
														.show();
											}

											startActivityForResult(new Intent(
													MainActivity.this,
													Game2.class), REQUEST_SPORT);

											record_game2 = "救援部隊：傑出好青年！";

											break;
										}
									}
								})
						.setNegativeButton(R.string.alert_cancel,
								new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										dialog.dismiss();
									}
								}).show();
			}
		});

	}

	private void init_UI_Elements() {
		tvLevel = (TextView) findViewById(R.id.txtLevel);
		tvIntimacy = (TextView) findViewById(R.id.txtIntimacy);
		tvPower = (TextView) findViewById(R.id.txtPower);
		tvMoney = (TextView) findViewById(R.id.txtMoney);
		btnWork = (Button) findViewById(R.id.btnWork);
		btnRecord = (Button) findViewById(R.id.btnRecord);
		imgbtnAtom = (ImageButton) findViewById(R.id.imgBtnAtom);
		imgbtnFire = (ImageButton) findViewById(R.id.imgBtnFire);
		imgbtnWater = (ImageButton) findViewById(R.id.imgBtnWater);
		imgbtnWind = (ImageButton) findViewById(R.id.imgBtnWind);
		imgbtnSun = (ImageButton) findViewById(R.id.imgBtnSun);
		imgbtnSea = (ImageButton) findViewById(R.id.imgBtnSea);
		image = (ImageView) findViewById(R.id.frame_image);

		tvLevel.setText(String.format(getResources().getString(R.string.level),
				pet.getLevel()));
		tvIntimacy
				.setText(String.format(
						getResources().getString(R.string.intimacy),
						pet.getIntimacy()));
		tvPower.setText(String.format(getResources().getString(R.string.power),
				pet.getPower()));
		tvMoney.setText(String.format(getResources().getString(R.string.money),
				pet.getMoney()));

		setUpPetFeedButtons();
	}

	private void setUpPetFeedButtons() {
		/** Pet Feeding */
		imgbtnAtom.setOnClickListener(new ImageButton.OnClickListener() {
			@Override
			public void onClick(View v) {
				pet.feed(nuclearFeed);
				nuclear++;

				Toast.makeText(getApplicationContext(), "信任度-5 金錢-112 能量+793",
						Toast.LENGTH_SHORT).show();
			}
		});

		imgbtnFire.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				pet.feed(fireFeed);
				fire++;
				
				Toast.makeText(getApplicationContext(), "信任度-3 金錢-89 能量+520",
						Toast.LENGTH_SHORT).show();
			}
		});

		imgbtnWater.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				pet.feed(waterFeed);
				water++;
				
				Toast.makeText(getApplicationContext(), "信任度+4 金錢-171 能量+254",
						Toast.LENGTH_SHORT).show();
			}
		});

		imgbtnWind.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				pet.feed(windFeed);
				wind++;
				
				Toast.makeText(getApplicationContext(), "信任度+5 金錢-403 能量+201",
						Toast.LENGTH_SHORT).show();
			}
		});

		imgbtnSun.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				pet.feed(sunFeed);
				sun++;
				
				Toast.makeText(getApplicationContext(), "信任度+7金錢-2381 能量+403",
						Toast.LENGTH_SHORT).show();
			}
		});

		imgbtnSea.setOnClickListener(new ImageButton.OnClickListener() {

			@Override
			public void onClick(View v) {
				pet.feed(seaFeed);
				sea++;
				
				Toast.makeText(getApplicationContext(), "信任度+3金錢-171 能量+403",
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	/** Animation */
	/** 動畫：呼叫*.xml */
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);

		// image.setBackgroundResource(R.anim.framelv1);
		// AnimationDrawable anim = (AnimationDrawable)
		// image.getBackground();
		// anim.start();

		// if (pet.getLevel() == 1) {
		// image.setBackgroundResource(R.anim.framelv1);
		// }
		//
		// if (pet.getLevel() == 2) {
		// image.setBackgroundResource(R.anim.framelv2);
		// }

		switch (pet.getLevel()) {
		case 1:
			image.setBackgroundResource(R.anim.framelv1);

			break;
		case 2:
			image.setBackgroundResource(R.anim.framelv2);

			break;
		case 3:
			image.setBackgroundResource(R.anim.framelv3);

			break;
		case 4:
			image.setBackgroundResource(R.anim.framelv4);

			break;
		case 5:
			image.setBackgroundResource(R.anim.framelv5);

			nuclearPercent = nuclear
					/ (nuclear + fire + water + wind + sun + sea) * 100;
			firePercent = fire / (nuclear + fire + water + wind + sun + sea)
					* 100;
			waterPercent = water / (nuclear + fire + water + wind + sun + sea)
					* 100;
			windPercent = wind / (nuclear + fire + water + wind + sun + sea)
					* 100;
			sunPercent = sun / (nuclear + fire + water + wind + sun + sea)
					* 100;
			seaPercent = sea / (nuclear + fire + water + wind + sun + sea)
					* 100;

			gameFinished = getResources().getString(R.string.gameFinished)
					+ "培育過程中能量方塊使用數量" + "\n" + "核能方塊：" + nuclear + "個　"
					+ format(nuclearPercent) + "%\n" + "火力方塊：" + fire + "個　"
					+ format(firePercent) + "%\n" + "水力方塊：" + water + "個　"
					+ format(waterPercent) + "%\n" + "風力方塊：" + wind + "個　"
					+ format(windPercent) + "%\n" + "太陽方塊：" + sun + "個　"
					+ format(sunPercent) + "%\n" + "潮汐方塊：" + sea + "個　"
					+ format(seaPercent) + "%\n\n";

			showMsgDialog("破關通知", gameFinished);
			break;
		default:
		}

	}

	@Override
	public void onPetIntimacyChange(Pet pet) {
		tvIntimacy
				.setText(String.format(
						getResources().getString(R.string.intimacy),
						pet.getIntimacy()));
	}

	@Override
	public void onPetLevelUp(Pet pet) {
		tvLevel.setText(String.format(getResources().getString(R.string.level),
				pet.getLevel()));
		Toast.makeText(MainActivity.this, tvLevel.getText(), Toast.LENGTH_LONG)
				.show();
		onWindowFocusChanged(true);
	}

	@Override
	public void onPetPowerChange(Pet pet) {
		if (pet.getPower() == 0) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.power_title)
					.setMessage(R.string.power_message_1)
					.setPositiveButton(R.string.power_ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									btnWork.setEnabled(false);
								}
							}).show();
		} else if (pet.getPower() < 0) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.power_title)
					.setMessage(R.string.power_message_2)
					.setPositiveButton(R.string.power_ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									btnWork.setEnabled(false);
								}
							}).show();
		} else {
			btnWork.setEnabled(true);
		}
		tvPower.setText(String.format(getResources().getString(R.string.power),
				pet.getPower()));
	}

	@Override
	public void onPetMoneyChange(Pet pet) {
		if (pet.getMoney() == 0) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.money_title)
					.setMessage(R.string.money_message_1)
					.setPositiveButton(R.string.money_ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									disableFeedBtns();
								}
							}).show();
		} else if (pet.getMoney() < 0) {
			new AlertDialog.Builder(MainActivity.this)
					.setTitle(R.string.money_title)
					.setMessage(R.string.money_message_2)
					.setPositiveButton(R.string.money_ok,
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									disableFeedBtns();
								}
							}).show();
		} else {
			enableFeedBtns();
		}
		tvMoney.setText(String.format(getResources().getString(R.string.money),
				pet.getMoney()));
	}

	private void enableFeedBtns() {
		imgbtnAtom.setEnabled(true);
		imgbtnFire.setEnabled(true);
		imgbtnWater.setEnabled(true);
		imgbtnWind.setEnabled(true);
		imgbtnSun.setEnabled(true);
		imgbtnSea.setEnabled(true);
	}

	private void disableFeedBtns() {
		imgbtnAtom.setEnabled(false);
		imgbtnFire.setEnabled(false);
		imgbtnWater.setEnabled(false);
		imgbtnWind.setEnabled(false);
		imgbtnSun.setEnabled(false);
		imgbtnSea.setEnabled(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_SPORT) {
			if (resultCode == RESULT_OK) {
				pet.setIntimacy(pet.getIntimacy()
						+ data.getIntExtra("intimacyOffset", 0));
				pet.setMoney(pet.getMoney()
						+ data.getIntExtra("moneyOffset", 0));
				pet.setPower(pet.getPower()
						+ data.getIntExtra("powerOffset", 0));
			}
		}
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
			String teach_main = getResources().getString(R.string.teach_main);
			showMsgDialog_Teach("遊戲教學", teach_main);

			/** 完成閱讀遊戲教學，解除所有功能鍵 */
			imgbtnAtom.setEnabled(true);
			imgbtnFire.setEnabled(true);
			imgbtnSea.setEnabled(true);
			imgbtnSun.setEnabled(true);
			imgbtnWater.setEnabled(true);
			imgbtnWind.setEnabled(true);
			btnWork.setEnabled(true);
			btnRecord.setEnabled(true);
			record_teach_main = "世界初探：認真乖寶寶！";

			break;
		case 1:
			finish();
			break;

		default:
		}
		return super.onOptionsItemSelected(item);
	}

	// ----- END -----

	private void ShowMsgDialog(String title, String msg) {
		Builder MyAlertDialog = new AlertDialog.Builder(this);
		MyAlertDialog.setTitle(title);
		MyAlertDialog.setMessage(msg);
		MyAlertDialog.setPositiveButton("非走不可！",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						finish();
					}
				});
		MyAlertDialog.setNegativeButton("留連忘返！",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {

					}
				});
		MyAlertDialog.show();
	}

	private void ShowMsgDialog_Record(String title, String msg, String btnText) {
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

	private void showMsgDialog(String Title, String Msg) {
		Builder myAlertDialog = new AlertDialog.Builder(this);
		myAlertDialog.setTitle(Title);
		myAlertDialog.setMessage(Msg);
		DialogInterface.OnClickListener OkClick = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				finish();
			}
		};
		myAlertDialog.setNeutralButton("確定", OkClick);
		myAlertDialog.show();
	}

	private String format(double num) {
		return String.format("%.2f", num);
	}

	public void bgMusic() {
		// ----- 背景音樂 -----
		mp = MediaPlayer.create(this, R.raw.pet);
		mp.start();
		mp.setLooping(true);
		// ----- END -----
	}

	public void onPause() {
		super.onPause();
		mp.reset();
	}

	public void onBackPressed() {
		super.onDestroy();
		ShowMsgDialog("遊戲小叮嚀", "確定要離開連鎖世界嗎？");
	}

}