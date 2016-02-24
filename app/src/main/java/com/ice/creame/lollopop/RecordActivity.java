package com.ice.creame.lollopop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.DBHelper.readDB;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/23.
 */
public class RecordActivity extends BaseActivity implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.r1);
        r1.setBackgroundColor(COLOR_3);

        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.r1_2);
        r2.setBackgroundColor(COLOR_3);

        TextView t = (TextView) findViewById(R.id.textView);
        t.setText("今までの記録");
        t.setTextSize(TEXT_SIZE3);
        t.setTextColor(TITLE_COLOR);
        t.setTypeface(tf);

        FrameLayout fr_la = (FrameLayout) findViewById(R.id.frame1_2);
        fr_la.setBackgroundResource(R.drawable.waku);


        ScrollView sc = (ScrollView) findViewById(R.id.scrollView1);
        sc.setBackgroundColor(COLOR_1);

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);

        makeTextView(" ", TEXT_SIZE3, TEXT_COLOR_1, NO_ID, ll1, null, this);

        for (int i = 0; ; i++) {

            try {
                String date = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[1];

                makeTextView(date, TEXT_SIZE2, TEXT_COLOR_4, NO_ID, ll1, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
                for (int k = 2; k < 5; k++) {

                    String data = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[k];
                    makeTextView((k - 1) + "位  " + data, TEXT_SIZE2, TEXT_COLOR_3, NO_ID, ll1, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
                }
            } catch (Exception e) {
                break;
            }
            makeTextView(" ", TEXT_SIZE2, TEXT_COLOR_1, NO_ID, ll1, null, this);

        }



        Button b = (Button) findViewById(R.id.button);
        b.setTextSize(TEXT_SIZE3);
        b.setTextColor(TITLE_COLOR);
        b.setTypeface(tf);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //画面遷移
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                startActivity(intent);
                RecordActivity.this.finish();
            }
        });

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
