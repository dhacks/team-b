package com.ice.creame.lollopop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import static com.ice.creame.lollopop.MethodLibrary.makeButton;
import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hideya on 2016/02/20.
 */
public class IndexActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /* IndexActivityで初期化しておく */
//        globals.GlobalsAllInit();

        /* パラメータ設定 */
        //text用
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //ボタン用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);

        makeTextView("男女比を選ぼう！", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", TEXT_SIZE4, TEXT_COLOR_1, NO_ID, li_la, null, this);

        int w = (int) (p.x / 1.3);
        int h = (int) (p.y / 7.9);
        for (int i = 0; i < INDEX.length; i++) {
            Button button = makeButton(INDEX[i], i, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
            button.setTextColor(TEXT_COLOR_1);
            button.setTextSize(TEXT_SIZE4);
            button.setWidth(w);
            button.setHeight(h);
        }

        makeTextView(" ", TEXT_SIZE4, TEXT_COLOR_1, NO_ID, li_la, null, this);

        //idはindex.lengthで自動調節
        Button button = makeButton("今までの記録", INDEX.length, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), param2, this);
        button.setTextColor(TEXT_COLOR_1);
        button.setTextSize(TEXT_SIZE4);
        button.setWidth(w);
        button.setHeight(h);

        Button button2 = makeButton("testview", INDEX.length, NO_TAG, makeRelativeLayout(COLOR_1, li_la, null, this), null, this);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.TestActivity");
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        Intent intent = new Intent(this, IndexActivity.class);

        globals.GlobalsAllInit();

        boolean goRecord = false;

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
            case 3:
                Log.d("mydebug", "Index_onClick_3");
                goRecord = true;
                break;
        }

        if (goRecord) {
            intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.RecordActivity");
        } else {
            globals.GlobalsALLmatrixSet(globals.indexFlag, NODE.length); //AHPに必要な行列サイズのセット
            intent.setClassName("com.ice.creame.lollopop", "com.ice.creame.lollopop.UserRegistrationActivity");
        }
        startActivity(intent);
        IndexActivity.this.finish();
    }

}
