package com.ice.creame.lollopop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/20.
 */
public class IndexActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent3);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        FrameLayout fr_la = (FrameLayout) findViewById(R.id.fl3);
        fr_la.setBackgroundResource(R.drawable.waku2);
        fr_la.bringToFront();

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.r3_1);
        r1.setBackgroundColor(COLOR_3);

        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.r3_2);
        r2.setBackgroundColor(COLOR_3);

        TextView t = (TextView) findViewById(R.id.textView3);
        t.setText("男女比を選んでください");
        t.setTextSize(TEXT_SIZE3);
        t.setTextColor(TITLE_COLOR);
        t.setTypeface(tf);


        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll3);

        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(w, h);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        makeTextView(" ", TEXT_SIZE4, TEXT_COLOR_1, NO_ID, li_la, null, this);

        for (int i = 0; i < INDEX.length; i++) {
            FrameLayout fl = new FrameLayout(this);
            fl.setLayoutParams(param1);
            li_la.addView(fl);

            Button b = new Button(this);
            b.setBackgroundResource(R.drawable.button);
            b.setText(INDEX[i]);
            b.setTypeface(tf);
            b.setTextSize(TEXT_SIZE3);
            b.setId(i);
            b.setOnClickListener((View.OnClickListener) this);
            fl.addView(b);

        }


        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(param1);
        li_la.addView(fl);
        Button b1 = new Button(this);
        b1.setBackgroundResource(R.drawable.button);
        b1.setText("今までの記録");
        b1.setTextColor(TEXT_COLOR_3);
        b1.setTypeface(tf);
        b1.setTextSize(TEXT_SIZE3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //音の再生
                seplay(globals.soundpool, globals.soundClick, globals.soundFlag);
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.RecordActivity");
                startActivity(intent);
                IndexActivity.this.finish();
            }
        });
        fl.addView(b1);

        FrameLayout f2 = new FrameLayout(this);
        f2.setLayoutParams(param1);
        li_la.addView(f2);
        Button b2 = new Button(this);
        b2.setBackgroundResource(R.drawable.button);
        b2.setText("設定");
        b2.setTextColor(TEXT_COLOR_3);
        b2.setTypeface(tf);
        b2.setTextSize(TEXT_SIZE3);
        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //音の再生
                seplay(globals.soundpool, globals.soundClick, globals.soundFlag);
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.SettingActivity");
                startActivity(intent);
                IndexActivity.this.finish();
            }
        });
        f2.addView(b2);


    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();

        globals.GlobalsAllInit();
        //音の再生
        seplay(globals.soundpool, globals.soundClick, globals.soundFlag);
        //id=0~2が2:2,3:3,4:4の設定
        switch (id) {
            case 0:
                Log.d("mydebug", "Index_onClick_0");
                globals.indexFlag = 2;
                break;
            case 1:
                Log.d("mydebug", "Index_onClick_1");
                globals.indexFlag = 3;
                break;
            case 2:
                Log.d("mydebug", "Index_onClick_2");
                globals.indexFlag = 4;
                break;
        }

        globals.GlobalsALLmatrixSet(globals.indexFlag, NODE.length); //AHPに必要な行列サイズのセット
        intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.UserRegistrationActivity");
        IndexActivity.this.finish();
        startActivity(intent);

    }

    public void onDestroy() {
        super.onDestroy();
    }

}
