package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
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

    int node_id = 0;
    int name_index = 0;
    int node_index = 0;
    int ahp_hierarchy = 1; //ahpの階層

    TextView text_name;
    TextView text_about;
    TextView text_question;

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

        text_name = makeTextView(globals.nameM.elementAt(name_index) + "の番", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

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

        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        text_about = makeTextView("", 32, TEXT_COLOR_1, NO_ID, li_la, null, this);
        text_about.setGravity(Gravity.CENTER_HORIZONTAL);
        text_question = makeTextView(comp1 + " VS " + comp2, 32, TEXT_COLOR_1, NO_ID, li_la, null, this);
        text_question.setGravity(Gravity.CENTER_HORIZONTAL);
        makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);

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

        String comp1;
        String comp2;

        if (ahp_hierarchy == 1 && node_index == (NODE.length * (NODE.length - 1)) / 2 - 1) {
            node_index = 0;
            ahp_hierarchy++;
            node_id++;
        } else if (ahp_hierarchy == 1) {
            node_index++;
        } else if (ahp_hierarchy == 2 && node_index == (globals.nameF.size() * (globals.nameF.size() - 1)) / 2 - 1 && node_id == (NODE.length + globals.nameF.size() - 1)) {
            name_index++;
            ahp_hierarchy = 0;
            node_id = 0;
        } else if (ahp_hierarchy == 2 && node_index == (globals.nameF.size() * (globals.nameF.size() - 1)) / 2 - 1) {
            node_index = 0;
            node_id++;
        } else if (ahp_hierarchy == 2) {
            node_index++;
        }

        if (node_id == 0) {
            text_about.setText("");
        } else {
            text_about.setText("「" + NODE[node_id - 1] + "」のみで考えると");
        }

        if (ahp_hierarchy == 1) {
            if (NODE.length == 2) {
                comp1 = NODE[combination2[node_index][0]];
                comp2 = NODE[combination2[node_index][1]];
            } else if (NODE.length == 3) {
                comp1 = NODE[combination3[node_index][0]];
                comp2 = NODE[combination3[node_index][1]];
            } else {
                comp1 = "unimplemented";
                comp2 = "unimplemented";
            }
        } else {

            if (globals.nameF.size() == 2) {
                comp1 = globals.nameF.elementAt(combination2[node_index][0]);
                comp2 = globals.nameF.elementAt(combination2[node_index][1]);
            } else if (globals.nameF.size() == 3) {
                comp1 = globals.nameF.elementAt(combination3[node_index][0]);
                comp2 = globals.nameF.elementAt(combination3[node_index][1]);
            } else {
                comp1 = "unimplemented";
                comp2 = "unimplemented";
            }
        }

        text_question.setText(comp1 + " VS " + comp2);
        text_name.setText(globals.nameM.elementAt(name_index) + "の番");

        for (int i = 0; i < SELECT.length * 2 - 1; i++) {
            Button b = (Button) findViewById(i);
            if (i < SELECT.length - 1) {
                b.setText(comp1 + SELECT[i]);
            } else if (i == SELECT.length - 1) {
                b.setText(SELECT[i]);
            } else {
                b.setText(comp2 + SELECT[SELECT.length * 2 - 2 - i]);
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

}
