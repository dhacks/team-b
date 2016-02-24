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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        setContentView(R.layout.userresistration);

        LinearLayout parent = (LinearLayout) findViewById(R.id.parent4);
        parent.setBackgroundResource(BACK_GROUND_IMAGE);

        RelativeLayout r1 = (RelativeLayout) findViewById(R.id.rl4);
        r1.setBackgroundColor(COLOR_3);

        TextView t = (TextView) findViewById(R.id.textView4);
        t.setText("名前を入力してください");
        t.setTextSize(TEXT_SIZE3);
        t.setTextColor(TITLE_COLOR);
        t.setTypeface(tf);

        LinearLayout li_la = (LinearLayout) findViewById(R.id.ll4);

        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        makeTextView("※名前は6文字まで", TEXT_SIZE2, TEXT_COLOR_3, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);
        makeTextView(" ", 10, Color.RED, NO_ID, li_la, null, this);
        int textSize = (int) (p.x / 28.8);

        //エディットテキスト用
        RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(WC, WC);
        param.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //男
        for (int i = 0; i < globals.indexFlag; i++) {
            makeTextView("男" + (i + 1), 40, TEXT_COLOR_3, NO_ID, li_la, null, this).setGravity(Gravity.CENTER_HORIZONTAL);

            EditText et = makeEditText(DEFAULT_NAME_M[i], textSize, i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this)
                    , param, this);
            et.setWidth((int) (p.x / 1.44));
            //入力文字数制限
            InputFilter[] _inputFilter = new InputFilter[1];
            _inputFilter[0] = new InputFilter.LengthFilter(LIMIT_NAME); //文字数指定
            et.setFilters(_inputFilter);
            et.setBackgroundResource(R.drawable.layout_shape); //XMLでフレーム定義
            makeTextView(" ", 20, Color.RED, NO_ID, li_la, null, this);
        }

//        Button b1 = new Button(this);
//        b1.setBackgroundResource(R.drawable.button);
//        b1.setText("今までの記録");
//        b1.setTypeface(tf);
//        b1.setTextSize(TEXT_SIZE3);
//        b1.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                //画面遷移
//                Intent intent = new Intent();
//                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.RecordActivity");
//                startActivity(intent);
//                TestActivity.this.finish();
//            }
//        });
//        fl.addView(b1);


    }

//    @Override
//    public void onClick(View view) {
//        int id = view.getId();
//        Intent intent = new Intent(this, IndexActivity.class);
//
//        globals.GlobalsAllInit();
//
//        boolean goRecord = false;
//        //id=0~2が2:2,3:3,4:4の設定,id=3が記録の閲覧
//        switch (id) {
//            case 0:
//                Log.d("mydebug", "Index_onClick_0");
//                globals.indexFlag = 2;
//                break;
//            case 1:
//                Log.d("mydebug", "Index_onClick_1");
//                globals.indexFlag = 3;
//                break;
//            case 2:
//                Log.d("mydebug", "Index_onClick_2");
//                globals.indexFlag = 4;
//                break;
//        }
//
//        globals.GlobalsALLmatrixSet(globals.indexFlag, NODE.length); //AHPに必要な行列サイズのセット
//        intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.UserRegistrationActivity");
//
//        startActivity(intent);
//        TestActivity.this.finish();
//    }

}
