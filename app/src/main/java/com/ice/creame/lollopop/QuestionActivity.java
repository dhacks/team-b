package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
public class QuestionActivity extends BaseActivity implements View.OnClickListener {

    int name_index = 0;
    int node_index = 0;

    //idは-1より小さい値
    public static final int T1_VIEW = -2;
    public static final int T2_VIEW = -3;

    TextView t1;

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

        t1 = makeTextView(globals.nameM.elementAt(name_index) + "の番", 40, TITLE_COLOR, T1_VIEW, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        String comp1;
        String comp2;

        if (NODE.length == 3) {
            comp1 = NODE[combination3[node_index][0]];
            comp2 = NODE[combination3[node_index][1]];
        } else {
            comp1 = "unimplemented";
            comp2 = "unimplemented";
        }

        makeTextView("Q " + comp1 + " VS " + comp2, 40, Color.RED, T2_VIEW, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        for (int i = 0; i < SELECT.length * 2 - 1; i++) {
            Log.d("mydebug", "i:" + i);
            Button button;
            if (i < SELECT.length - 1) {
                button = makeButton(comp1 + SELECT[i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            } else if (i == SELECT.length - 1) {
                button = makeButton(SELECT[i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            } else {
                button = makeButton(comp2 + SELECT[SELECT.length * 2 - 2 - i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            }
            button.setTextColor(TEXT_COLOR_1);
            button.setTextSize(20);
            button.setWidth(w);
            button.setHeight(h);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
        }

        node_index++;
        t1.setText("yatta--");


    }

    // BACKボタンで終了させる
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this).setTitle("確認").setMessage("途中で終了すると今までに回答したデータが失われますがよろしいですか？")
                    .setPositiveButton("はい", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            QuestionActivity.this.finish();
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

//    class MyAsyncTask extends AsyncTask<Integer, Integer, Integer> {
//
//        Bitmap bitmap[] = new Bitmap[14];
//
//        public MyAsyncTask() {
//            super();
//        }
//
//        @Override
//        protected Integer doInBackground(Integer... params) {
//
//            Log.d("mydebug", "doinb");
//
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Integer result) {
//            Log.d("mydebug", "onpos");
//            ((TextView)findViewById(2));
//        }
//
//    }
}
