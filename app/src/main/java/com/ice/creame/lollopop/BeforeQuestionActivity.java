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
import android.widget.FrameLayout;
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

    static volatile Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thread = null;

        setContentView(R.layout.beforequestion);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent4);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        FrameLayout fl_2 = (FrameLayout) findViewById(R.id.fl4_2);
        fl_2.setBackgroundResource(R.drawable.waku2);

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll4);

//<<<<<<< HEAD
        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);
        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);
//=======
//        makeTextView(" ", 150, Color.RED, NO_ID, li_la, null, this);
//
//>>>>>>> origin/settingA
        //男女の切り替え
        TextView tv;

        if (globals.name_index < globals.nameM.size()) {
            tv = makeTextView(globals.nameM.elementAt(globals.name_index) + "の番", 32, TEXT_COLOR_3, NO_ID, li_la, null, this);
        } else {
            tv = makeTextView(globals.nameF.elementAt(globals.name_index - globals.nameM.size()) + "の番", 32, TEXT_COLOR_3, NO_ID, li_la, null, this);
        }
        tv.setGravity(Gravity.CENTER_HORIZONTAL);

        //エディットテキスト用
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC, WC);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL);


        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(w, h);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelativeLayout re = new RelativeLayout(this);
        li_la.addView(re);

        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(param1);
        re.addView(fl);

        Button b1 = new Button(this);
        b1.setBackgroundResource(R.drawable.button);
        b1.setText("スタート");
        b1.setId(0);
        b1.setTypeface(tf);
        b1.setTextSize(TEXT_SIZE3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //音の再生
                seplay(globals.soundpool,globals.sound1,globals.soundFlag);
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.QuestionActivity");
                startActivity(intent);
                BeforeQuestionActivity.this.finish();
            }
        });
        fl.addView(b1);



        
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        if (hasFocus) {


            if (thread == null) {
                thread = new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (true) {
                            // ネットワーク処理などの時間のかかる処理

                            BeforeQuestionActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Button button = (Button) findViewById(0);
                                    RotateAnimation a2 = new RotateAnimation(-2.0f, 2.0f, button.getWidth() / 2, button.getHeight() / 2);
                                    a2.setDuration(10);
                                    a2.setRepeatCount(15);
                                    a2.setRepeatMode(a2.REVERSE);
                                    a2.setStartOffset(10);

                                    button.startAnimation(a2);
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
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        //音の再生
        seplay(globals.soundpool, globals.sound1,globals.soundFlag);

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