package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Calendar;

import static com.ice.creame.lollopop.DBHelper.DB_TABLE_NODE;
import static com.ice.creame.lollopop.DBHelper.readDB;
import static com.ice.creame.lollopop.DBHelper.writeDB;
import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/21.
 */
public class ResultActivity extends BaseActivity implements View.OnClickListener {

    TextView textView[] = {null, null, null};
    static volatile Thread thread;
    static volatile Thread thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thread = null;
        thread2 = null;

        globals.tsubuyaki = "";

        setContentView(R.layout.result);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent5);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        FrameLayout fl_2 = (FrameLayout) findViewById(R.id.fl5_2);
        fl_2.setBackgroundResource(R.drawable.waku2);

        TextView tv = (TextView) findViewById(R.id.textView5);
        tv.setText("結果発表");
        tv.setTextSize(TEXT_SIZE3);
        tv.setTextColor(TITLE_COLOR);
        tv.setTypeface(tf);

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll5);

        makeTextView(" ", TEXT_SIZE5, TEXT_COLOR_1, NO_ID, li_la, null, this);

        for (int i = 0; i < globals.rank.length - 1; i++) {
            textView[i] = makeTextView((i + 1) + "位 " + globals.nameM.elementAt((int) globals.rank[i][1]) + "×" + globals.nameF.elementAt((int) globals.rank[i][0]), TEXT_SIZE3, TEXT_COLOR_3, NO_ID, li_la, null, this);
            textView[i].setGravity(Gravity.CENTER_HORIZONTAL);
            globals.tsubuyaki += (i + 1) + "位 " + globals.nameM.elementAt((int) globals.rank[i][1]) + "×" + globals.nameF.elementAt((int) globals.rank[i][0]) + "\n";
        }

        makeTextView(" ", TEXT_SIZE3_5, TEXT_COLOR_1, NO_ID, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(w, h);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        String text[] = {"データを保存せずに終了", "データを保存して終了", "ワースト1位を見る"};
        for (int i = 0; i < 3; i++) {
            RelativeLayout re = new RelativeLayout(this);
            li_la.addView(re);
            FrameLayout fl = new FrameLayout(this);
            fl.setLayoutParams(param1);
            re.addView(fl);

            Button b1 = new Button(this);
            b1.setBackgroundResource(R.drawable.button);
            b1.setOnClickListener((View.OnClickListener) this);
            b1.setText(text[i]);
            b1.setId(i);
            b1.setTypeface(tf);
            b1.setTextSize(TEXT_SIZE2);
            fl.addView(b1);
        }

        TextView test = (TextView) findViewById(R.id.textView5_2);
        test.setText("共有");
        test.setTextColor(COLOR_2);
        test.setTypeface(tf);
        test.setTextSize(TEXT_SIZE3);
        test.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //音の再生
                seplay(globals.soundpool, globals.soundClick, globals.soundFlag);
                try {
                    Intent sentIntent = new Intent();
                    sentIntent.setAction(Intent.ACTION_SEND);
                    sentIntent.setType("text/plain");
                    sentIntent.putExtra(Intent.EXTRA_TEXT, APP_NAME + "の結果\n" + globals.tsubuyaki);
                    startActivity(sentIntent);
                } catch (Exception e) {
                    Log.d("mydebug", "not_tsubuyaki");
                }
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent = new Intent();
        //音の再生

        seplay(globals.soundpool, globals.soundClick, globals.soundFlag);

        switch (id) {
            case 0:
                //遷移
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                startActivity(intent);
                ResultActivity.this.finish();
                break;

            case 1:

                final Calendar calendar = Calendar.getInstance();
                final int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                final int minute = calendar.get(Calendar.MINUTE);
                final int second = calendar.get(Calendar.SECOND);

                String date = year + "/" + (month + 1) + "/" + day + "/" + " " +
                        hour + ":" + minute + ":" + second;

                Log.d("mydebug", date);

                int cnt = 0;
                for (int i = 0; ; i++) {
                    try {
                        readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db);
                        cnt++;
                    } catch (Exception e) {
                        break;
                    }
                }

                String value[] = new String[3];
                for (int i = 0; i < globals.rank.length - 1; i++) {
                    value[i] = globals.nameM.elementAt((int) globals.rank[i][1]) + "×" + globals.nameF.elementAt((int) globals.rank[i][0]);
                }

                try {
                    writeDB(String.valueOf(cnt), date, value[0], value[1], value[2], DBHelper.DB_TABLE_RECORD, db);
                } catch (Exception e) {
                    Log.d("mydebug", "dbError");
                }

                for (int i = 0; i <= cnt; i++) {
                    try {
                        Log.d("mydebug", " id : " + readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db)[0]);
                        Log.d("mydebug", " date : " + readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db)[1]);
                        Log.d("mydebug", " v1 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db)[2]);
                        Log.d("mydebug", " v2 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db)[3]);
                        Log.d("mydebug", " v3 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE_RECORD, db)[4]);

                    } catch (Exception e) {
                        Log.d("mydebug", "dbError");
                    }
                }

                //遷移
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                startActivity(intent);
                ResultActivity.this.finish();
                break;

            case 2:
                new AlertDialog.Builder(this).setTitle("確認").setMessage("本当に見ますか？")
                        .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent();
                                /* ここで画面遷移 */
                                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.WorstResult" +
                                        "Activity");
                                startActivity(intent);
                                ResultActivity.this.finish();
                            }
                        }).setNegativeButton("いいえ", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).show();

                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {


        if (hasFocus) {
            /* title */

            ScaleAnimation a2 = new ScaleAnimation(0.0f, 1, 0.0f, 1, textView[2].getWidth() / 2, textView[2].getHeight() / 2);
            a2.setDuration(5000);
            a2.setStartOffset(1000);
            textView[2].startAnimation(a2);

            ScaleAnimation a1 = new ScaleAnimation(0.0f, 1, 0.0f, 1, textView[1].getWidth() / 2, textView[2].getHeight() / 2);
            a1.setDuration(5000);
            a1.setStartOffset(6000);
            textView[1].startAnimation(a1);


            ScaleAnimation a = new ScaleAnimation(0.0f, 1, 0.0f, 1, textView[0].getWidth() / 2, textView[2].getHeight() / 2);
            a.setDuration(5000);
            a.setStartOffset(11000);
            textView[0].startAnimation(a);

            //BGM再生

            String isPlaySound = "-1"; //error
            try {
                isPlaySound = readDB("sound", DB_TABLE_NODE, db)[1];
            } catch (Exception e) {

            }

            if (isPlaySound.equals("1")) {
                globals.mpFirst = MediaPlayer.create(this, R.raw.bravo);
            } else {
                globals.mpFirst = null;
            }
//            globals.mpFirst.start();

            if (thread == null) {
                thread = new Thread(new Runnable() {


                    @Override
                    public void run() {
                        while (true) {

                            ResultActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    TextView textView = (TextView) findViewById(R.id.textView5_2);
                                    RotateAnimation a2 = new RotateAnimation(-5.0f, 5.0f, textView.getWidth() / 2, textView.getHeight() / 2);
                                    a2.setDuration(10);
                                    a2.setRepeatCount(15);
                                    a2.setRepeatMode(a2.REVERSE);
                                    a2.setStartOffset(10);

                                    textView.startAnimation(a2);
                                }
                            });
                            try {
                                //15秒停止します。
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                            }

                        }

                    }
                });
                thread.start();
            }

            if (thread2 == null) {
                thread2 = new Thread(new Runnable() {


                    @Override
                    public void run() {

                            try {
                                Thread.sleep(13000);
                            } catch (InterruptedException e) {
                            }

                            globals.mpFirst.start();


                    }
                });
                thread2.start();
            }

        }
    }

    // BACKボタンで終了させる
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setTitle("確認").setMessage("途中で終了すると今までに回答したデータが失われますがよろしいですか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ResultActivity.this.finish();
                        }
                    }).setNegativeButton("いいえ", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            }).show();

            return true;
        }
        return false;
    }

    public void onDestroy() {
        super.onDestroy();
        globals.mpFirst.release();
        globals.mpFirst = null;
    }
}