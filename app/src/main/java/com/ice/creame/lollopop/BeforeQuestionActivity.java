package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/20.
 */
public class BeforeQuestionActivity extends BaseActivity implements View.OnClickListener {

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


        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        //男女の切り替え
        TextView tv;
        if (globals.name_index < globals.nameM.size()) {
            tv = makeTextView(globals.nameM.elementAt(globals.name_index) + "の番", 32, TEXT_COLOR_1, NO_ID, li_la, null, this);
        } else {
            tv = makeTextView(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番", 32, TEXT_COLOR_1, NO_ID, li_la, null, this);
        }
        tv.setGravity(Gravity.CENTER_HORIZONTAL);

        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);

        Button button = makeButton("スタート", 0, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);

        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(20);
        button.setWidth(w);
        button.setHeight(h);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {

            Button button = (Button) findViewById(0);
            RotateAnimation a2 = new RotateAnimation(-2.0f, 2.0f, button.getWidth() / 2, button.getHeight() / 2);
            a2.setDuration(10);
            a2.setRepeatCount(15);
            a2.setRepeatMode(a2.REVERSE);
            a2.setStartOffset(10);

            button.startAnimation(a2);

        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        Intent intent = new Intent();
        switch (id) {
            case 0:
                //遷移
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.QuestionActivity");
                startActivity(intent);
                BeforeQuestionActivity.this.finish();
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
                            BeforeQuestionActivity.this.finish();
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