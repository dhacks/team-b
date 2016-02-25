package com.ice.creame.lollopop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
public class NodeCustomizeActivity extends BaseActivity implements View.OnClickListener {

    EditText[] et = {null, null, null};

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

        //男女用
        RelativeLayout.LayoutParams param3 = new RelativeLayout.LayoutParams(WC, WC);
        param3.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //エディットテキスト用
        RelativeLayout.LayoutParams param4 = new RelativeLayout.LayoutParams(WC, WC);
        param4.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);

        makeTextView("ノード変更(仮)", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        int textSize = (int) (p.x / 28.8);

        //ノード設定edit text
        for (int i = 0; i < NODE.length; i++) {
            makeTextView("  " +i , 40, TEXT_COLOR_1, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), param3, this);
            String text = "bubu-";
            try {
                text = readDB(String.valueOf(i), DB_TABLE_NODE, db)[1];
            }catch (Exception e){

            }

            et[i] = makeEditText(text, textSize, i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param4, this);
            et[i].setWidth((int) (p.x / 1.44));
//            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(LIMIT_NAME); //文字数指定
            et[i].setFilters(_inputFilter);
            et[i].setBackgroundResource(R.drawable.layout_shape); //XMLでフレーム定義
            //改行禁止
            et[i].setInputType(InputType.TYPE_CLASS_TEXT);
            makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
            }

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);

        Button button = makeButton("登録", 0, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(20);
        button.setWidth(w);
        button.setHeight(h);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent();
        //音の再生
        seplay(globals.soundpool,globals.sound1,globals.soundFlag);
        //空白の検出
        boolean flag=true;
        for (int i = 0; i < NODE.length; i++) {
            EditText et = (EditText) findViewById(i);
            if (et.getText().toString().equals("") ){
                Toast.makeText(this, "すべての項目に入力してください", Toast.LENGTH_SHORT).show();
                flag=false;
            }
        }

        if(flag) {
            switch (id) {
                case 0:
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
                    NodeCustomizeActivity.this.finish();
                    break;
            }
        }
    }

}
