package com.ice.creame.lollopop;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import static com.ice.creame.lollopop.MethodLibrary.makeLinearLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeRelativeLayout;
import static com.ice.creame.lollopop.MethodLibrary.makeScrollView;
import static com.ice.creame.lollopop.MethodLibrary.makeTextView;

/**
 * Created by hpyus on 2016/02/25.
 */
public class SettingActivity extends BaseActivity{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* パラメータ設定 */
        //タイトル用
        RelativeLayout.LayoutParams param1 = new RelativeLayout.LayoutParams(WC, WC);
        param1.addRule(RelativeLayout.CENTER_HORIZONTAL);

        //ボタン用
        RelativeLayout.LayoutParams param2 = new RelativeLayout.LayoutParams(WC, WC);
        param2.addRule(RelativeLayout.CENTER_HORIZONTAL);

        /* 画面レイアウト */
        LinearLayout li_la_super = makeLinearLayout(COLOR_D, LinearLayout.VERTICAL, null, this);
        li_la_super.setBackgroundResource(BACK_GROUND_IMAGE);

        setContentView(li_la_super);

        makeTextView("設定", 40, TITLE_COLOR, NO_ID, makeRelativeLayout(COLOR_3, li_la_super, null, this), param1, this);

        ScrollView sc_vi = makeScrollView(COLOR_1, li_la_super, this);
        LinearLayout li_la = makeLinearLayout(COLOR_1, LinearLayout.VERTICAL, sc_vi, this);

        makeTextView(" ", 150, Color.RED, NO_ID, li_la, null, this);






        makeTextView("fasdfa", 150, Color.RED, NO_ID, li_la, null, this);

        makeTextView(" ", 150, Color.RED, NO_ID, li_la, null, this);

    }
}
