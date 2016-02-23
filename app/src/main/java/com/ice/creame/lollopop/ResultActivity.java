package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import java.util.Calendar;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* パラメータ設定 */
        //タイトル用
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //ボタン用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);

        makeTextView("結果発表", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);
        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

        makeTextView("finalResult", 20, TEXT_COLOR_1, NO_ID, li_la, null, this);
        for (int i = 0; i < globals.finalResult.length; i++) {
            for (int j = 0; j < globals.finalResult[0].length; j++) {
                makeTextView(globals.nameM.elementAt(i) + "と" + globals.nameF.elementAt(j) + ":" + globals.finalResult[i][j], 20, TEXT_COLOR_1, NO_ID, li_la, null, this);
            }
        }
        makeTextView("rank", 20, TEXT_COLOR_1, NO_ID, li_la, null, this);
        for (int i = 0; i < globals.rank.length - 1; i++) {
            makeTextView((i + 1) + "位" + globals.nameM.elementAt((int) globals.rank[i][1]) + "と" + globals.nameF.elementAt((int) globals.rank[i][0]) + ":" + globals.rank[i][2], 20, TEXT_COLOR_1, NO_ID, li_la, null, this);
        }


        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);

        Button button = makeButton("データを保存せずに終了", 0, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(20);
        button.setWidth(w);
        button.setHeight(h);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

        Button button2 = makeButton("データを保存して終了", 1, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button2.setTextColor(TEXT_COLOR_1);
        button2.setTextSize(20);
        button2.setWidth(w);
        button2.setHeight(h);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

        Button button3 = makeButton("ワースト1位を見る", 2, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button3.setTextColor(TEXT_COLOR_1);
        button3.setTextSize(20);
        button3.setWidth(w);
        button3.setHeight(h);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent = new Intent();
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
                        readDB(String.valueOf(i), DBHelper.DB_TABLE, db);
                        cnt++;
                    } catch (Exception e) {
                        break;
                    }
                }

                String value[] = new String [3];
                for (int i = 0; i < globals.rank.length - 1; i++) {
                    value[i] = globals.nameM.elementAt((int) globals.rank[i][1]) + "と" + globals.nameF.elementAt((int) globals.rank[i][0]);
//                    makeTextView((i + 1) + "位" + globals.nameM.elementAt((int) globals.rank[i][1]) + "と" + globals.nameF.elementAt((int) globals.rank[i][0]) + ":" + globals.rank[i][2], 20, TEXT_COLOR_1, NO_ID, li_la, null, this);
                }

                try {
                    writeDB(String.valueOf(cnt), date, value[0], value[1], value[2], DBHelper.DB_TABLE, db);
                } catch (Exception e) {
                    Log.d("mydebug", "dbError");
                }

                for (int i = 0; i <= cnt; i++) {
                    try {
                        Log.d("mydebug", " id : " + readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[0]);
                        Log.d("mydebug", " date : " + readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[1]);
                        Log.d("mydebug", " v1 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[2]);
                        Log.d("mydebug", " v2 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[3]);
                        Log.d("mydebug", " v3 : " + readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[4]);

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
                                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.WorstResultActivity");
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
}