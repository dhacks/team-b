package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

public class MainActivity extends BaseActivity {

    static volatile Thread thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        thread = null;

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll2);
        li_la.setBackgroundResource(R.drawable.carpet);

        FrameLayout fr_la2 = (FrameLayout) findViewById(R.id.fl2_2);
        fr_la2.setBackgroundResource(R.drawable.frame2);

        FrameLayout fr_la3 = (FrameLayout) findViewById(R.id.fl2_3);
        fr_la3.setBackgroundResource(R.drawable.corner);

        TextView te_vi = (TextView) findViewById(R.id.textView2_1);
        te_vi.setText(APP_NAME);
        te_vi.setTextSize(TEXT_SIZE5);
        te_vi.setTextColor(TITLE_COLOR);
        te_vi.setTypeface(tf);

        TextView te_vi2 = (TextView) findViewById(R.id.textView2_2);
        te_vi2.setText("Tap to start");
        te_vi2.setTextSize(TEXT_SIZE3_5);
        te_vi2.setTextColor(TITLE_COLOR);
        te_vi2.setTypeface(tf);
        te_vi2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("mydebug", "Main_onClick_taptostart");
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        TextView titleView = (TextView) findViewById(R.id.textView2_1);

        if (hasFocus) {
            /* title */
            AnimationSet set = new AnimationSet(true);

            RotateAnimation rotate = new RotateAnimation(0, 360, titleView.getWidth()/2, titleView.getHeight()/2); // imgの中心を軸に、0度から360度にかけて回転
            rotate.setDuration(5000);
            set.addAnimation(rotate);

            ScaleAnimation a = new ScaleAnimation(0.0f, 1, 0.0f, 1, titleView.getWidth() / 2, titleView.getHeight() / 2);
            a.setDuration(5000);
            set.addAnimation(a);

            titleView.startAnimation(set);

            /* Tap to Start */
            if (thread == null) {
                thread = new Thread(new Runnable() {


                    @Override
                    public void run() {

                        while (true) {
                            // ネットワーク処理などの時間のかかる処理

                            MainActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    TextView textView = (TextView) findViewById(R.id.textView2_2);
                                    RotateAnimation a2 = new RotateAnimation(-2.0f, 2.0f, textView.getWidth() / 2, textView.getHeight() / 2);
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

        }
    }

    // BACKボタンで終了させる
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setTitle("確認").setMessage(APP_NAME + "を終了してもよろしいですか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
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
