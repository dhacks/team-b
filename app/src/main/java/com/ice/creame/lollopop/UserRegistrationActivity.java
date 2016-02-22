package com.ice.creame.lollopop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import static com.ice.creame.lollopop.MethodLibrary.*;

/**
 * Created by hideya on 2016/02/20.
 */

public class UserRegistrationActivity extends BaseActivity implements View.OnClickListener {

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

        makeTextView("ニックネーム登録", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        makeTextView("※名前は6文字まで", 20, Color.BLACK, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        int textSize = (int) (p.x / 28.8);

        //男
        for (int i = 0; i < globals.indexFlag; i++) {
            makeTextView("男" + (i + 1), 40, TEXT_COLOR_1, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), param3, this);
            EditText et = makeEditText(DEFAULT_NAME_M[i], textSize, i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this)
                    , param4, this);
            et.setWidth((int) (p.x / 1.44));
            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(LIMIT_NAME); //文字数指定
            et.setFilters(_inputFilter);
            et.setBackgroundResource(R.drawable.layout_shape); //XMLでフレーム定義
            makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        }

        //女
        for (int i = 0; i < globals.indexFlag; i++) {
            makeTextView("女" + (i + 1), 40, TEXT_COLOR_1, NO_ID, makeRelativeLayout(COLOR_1, li_la, null, this), param3, this);
            EditText et = makeEditText(DEFAULT_NAME_F[i], textSize, i + globals.indexFlag, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this)
                    , param4, this);
            et.setWidth((int) (p.x / 1.44));
            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(LIMIT_NAME); //文字数指定
            et.setFilters(_inputFilter);
            et.setBackgroundResource(R.drawable.layout_shape); //XMLでフレーム定義
            makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        }

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 5.3);
        Button button = makeButton("マッチング\nスタート", 0, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button.setGravity(Gravity.CENTER_HORIZONTAL);
        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(40);
        button.setWidth(w);
        button.setHeight(h);

    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(this, UserRegistrationActivity.class);

        /* 一生使わないんじゃね */
//        globals.GlobalsNameInit();
//        globals.GlobalsAHPnetInit();

        switch (id) {
            case 0:
//                Log.d("mydebug", "UserRegistration_onClick_0");

                for (int i = 0; i < globals.indexFlag; i++) {
                    //globalに名前登録
                    EditText et = (EditText) findViewById(i);
//                    Log.d("mydebug", "name : " + et.getText().toString());
                    globals.nameM.add(et.getText().toString());
                }

                for (int i = 0; i < globals.indexFlag; i++) {
                    EditText et = (EditText) findViewById(i + globals.indexFlag);
//                    Log.d("mydebug", "name : " + et.getText().toString());
                    globals.nameF.add(et.getText().toString());
                }

                //遷移
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.BeforeQuestionActivity");
                startActivity(intent);
                UserRegistrationActivity.this.finish();

                break;
        }

    }
}
