package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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
 * Created by hideya on 2016/02/23.
 */
public class RecordActivity  extends BaseActivity implements View.OnClickListener {

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

        makeTextView("今までの記録", TEXT_SIZE4, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);
        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", TEXT_SIZE1, TEXT_COLOR_1, NO_ID, li_la, null, this);

        int cnt = 0;
        for (int i = 0; ; i++) {
            try {
                String date = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[1];
                makeTextView(date, TEXT_SIZE2, TEXT_COLOR_1, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
                for(int k=2; k < 5; k++){
                    String data = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[k];
                    makeTextView(k-1+"位 : " + data, TEXT_SIZE3, TEXT_COLOR_1, NO_ID, li_la, null, this);
                }
            } catch (Exception e) {
                break;
            }
            makeTextView(" ", TEXT_SIZE2, TEXT_COLOR_1, NO_ID, li_la, null, this);

        }

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        Button button = makeButton("戻る", 0, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(20);
        button.setWidth(w);
        button.setHeight(h);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

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
                RecordActivity.this.finish();
                break;

        }
    }

}
