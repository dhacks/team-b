package com.ice.creame.lollopop;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import static com.ice.creame.lollopop.AHPCalculation.getFinalResult;
import static com.ice.creame.lollopop.AHPCalculation.getRank;
import static com.ice.creame.lollopop.AHPCalculation.matrixMultiplication;
import static com.ice.creame.lollopop.AHPCalculation.powerMethod;
import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeEditText;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/23.
 */
public class TestActivity extends BaseActivity {

    static volatile Thread thread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.question);
        thread = null;

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent5);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);


        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll5);
        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);
        makeTextView(" ", 50, Color.RED, NO_ID, li_la, null, this);

        TextView tv = makeTextView("結果発表", 32, TEXT_COLOR_3, NO_ID, li_la, null, this);
        tv.setGravity(Gravity.CENTER_HORIZONTAL);


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
        b1.setText("見る");
        b1.setId(0);
        b1.setTypeface(tf);
        b1.setTextSize(TEXT_SIZE3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.ResultActivity");
                startActivity(intent);
                TestActivity.this.finish();
            }
        });
        fl.addView(b1);

    }

}
