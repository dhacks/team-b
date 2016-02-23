package com.ice.creame.lollopop;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import static com.ice.creame.lollopop.DBHelper.readDB;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/23.
 */
public class TestActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // TableLayoutのグループを取得
        LinearLayout parent = (LinearLayout) findViewById(R.id.parent);
        parent.setBackgroundResource(R.drawable.background);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.r1);
        r1.setBackgroundColor(COLOR_3);

        TextView t3 = (TextView) findViewById(R.id.textView3);
        t3.setText("今までの記録");
        t3.setTextSize(TEXT_SIZE3);
        t3.setTextColor(TITLE_COLOR);


        ScrollView sc = (ScrollView) findViewById(R.id.scrollView2);
//        sc.setBackgroundResource(R.drawable.paper);
        sc.setBackgroundColor(COLOR_2);

        LinearLayout ll1 = (LinearLayout) findViewById(R.id.ll1);
//        ll1.setBackgroundResource(R.drawable.paper);

        makeTextView(" ", TEXT_SIZE3, TEXT_COLOR_1, NO_ID, ll1, null, this);

        for (int i = 0; ; i++) {
            try {
                String date = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[1];
                makeTextView(date, TEXT_SIZE2, TEXT_COLOR_3, NO_ID, ll1, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
                for(int k=2; k < 5; k++){
                    String data = readDB(String.valueOf(i), DBHelper.DB_TABLE, db)[k];
                    makeTextView("　　　"+(k-1)+"位 : " + data, TEXT_SIZE1, TEXT_COLOR_3, NO_ID, ll1, null, this);
                }
            } catch (Exception e) {
                break;
            }
            makeTextView(" ", TEXT_SIZE2, TEXT_COLOR_1, NO_ID, ll1, null, this);

        }

        RelativeLayout r2 = (RelativeLayout) findViewById(R.id.r2);
        r2.setBackgroundColor(COLOR_3);

        Button b = (Button)findViewById(R.id.button);
//        b.setBackgroundResource(R.drawable.button2);
        b.setTypeface(tf);

    }
}