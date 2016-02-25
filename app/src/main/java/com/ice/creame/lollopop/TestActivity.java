package com.ice.creame.lollopop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeEditText;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/23.
 */
public class TestActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.beforequestion);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent4);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);


        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll4);

        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);
        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);
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
        b1.setTypeface(tf);
        b1.setTextSize(TEXT_SIZE3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.QuestionActivity");
                startActivity(intent);
                TestActivity.this.finish();
            }
        });
        fl.addView(b1);


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
            startActivity(intent);
            TestActivity.this.finish();
        }
        return false;
    }

}
