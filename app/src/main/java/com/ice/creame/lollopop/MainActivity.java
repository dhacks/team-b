package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* パラメータ設定 */
        //aapname用
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
        param1.addRule(RelativeLayout.CENTER_IN_PARENT);

        //tap to start用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_IN_PARENT);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);
        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 32, Color.RED, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), null, this);
        makeTextView(" ", 60, Color.RED, NO_ID, li_la, null, this);
        makeTextView(APP_NAME, 40, TEXT_COLOR_1, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), param1, this);
        makeTextView(" ", 100, Color.RED, NO_ID, li_la, null, this);
        TextView textView = makeTextView("Tap to start", 40, TEXT_COLOR_1, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Log.d("mydebug", "Main_onClick_taptostart");
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View view) {// クリック時に呼ばれる
        int id = view.getId();
        Intent intent = new Intent(this, MainActivity.class);

		/* 遷移先の指定 */

        intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
        startActivity(intent);
//        MainActivity.this.finish();


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
