package com.ice.creame.lollopop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import static com.ice.creame.lollopop.DBHelper.DB_TABLE_NODE;
import static com.ice.creame.lollopop.DBHelper.readDB;
import static com.ice.creame.lollopop.DBHelper.writeDB;
import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeEditText;
import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hpyus on 2016/02/24.
 */
public class SettingActivity extends BaseActivity {

    EditText[] et = {null, null, null};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.setting);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent7);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        TextView t = (TextView) findViewById(R.id.textView7);
        t.setText("設定");
        t.setTextSize(TEXT_SIZE3);
        t.setTextColor(TITLE_COLOR);
        t.setTypeface(tf);

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll7);

        int textSize = (int) (p.x / 28.8);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);

        //エディットテキスト用
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(w, h);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        makeTextView("※条件設定は6文字まで", TEXT_SIZE2, TEXT_COLOR_3, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);

        //ノード設定edit text
        for (int i = 0; i < NODE.length; i++) {


            String text = "readError";
            try {
                text = readDB(String.valueOf(i), DB_TABLE_NODE, db)[1];
            } catch (Exception e) {

            }

            makeTextView("条件" + (i + 1), TEXT_SIZE2_5, TEXT_COLOR_3, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);

            RelativeLayout re = new RelativeLayout(this);
            li_la.addView(re);

            FrameLayout fl = new FrameLayout(this);
            fl.setLayoutParams(param);
            re.addView(fl);

            et[i] = makeEditText(text, textSize, i, NO_TAG, fl, null, this);
            et[i].setWidth((int) (p.x / 1.44));
            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(LIMIT_NAME); //文字数指定
            et[i].setFilters(_inputFilter);
            et[i].setBackgroundResource(R.drawable.edittext);
            et[i].setInputType(InputType.TYPE_CLASS_TEXT);
            makeTextView(" ", TEXT_SIZE3_5, Color.RED, NO_ID, li_la, null, this);
        }

        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(w, h);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        RelativeLayout re = new RelativeLayout(this);
        li_la.addView(re);

        FrameLayout fl = new FrameLayout(this);
        fl.setLayoutParams(param1);
        re.addView(fl);

        Button b1 = new Button(this);
        b1.setBackgroundResource(R.drawable.button);
        b1.setText("登録");
        b1.setTypeface(tf);
        b1.setTextSize(TEXT_SIZE3);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                //音の再生
                seplay(globals.soundpool, globals.sound1, globals.soundFlag);
                boolean flag = true;
                for (int i = 0; i < NODE.length; i++) {
                    EditText et = (EditText) findViewById(i);
                    if (et.getText().toString().equals("")) {
                        Toast.makeText(SettingActivity.this, "すべての項目に入力してください", Toast.LENGTH_SHORT).show();
                        flag = false;
                    }
                }
                if (flag) {
                    //DBに登録する
                    for (int i = 0; i < NODE.length; i++) {
                        try {
                            writeDB(String.valueOf(i), null, et[i].getText().toString(), null, null, DBHelper.DB_TABLE_NODE, db);

                        } catch (Exception e) {
                            Log.d("mydebug", "dbError");
                        }
                    }

                    //遷移
                    intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.IndexActivity");
                    startActivity(intent);
                    SettingActivity.this.finish();
                }
            }

        });
        fl.addView(b1);

    }

}
