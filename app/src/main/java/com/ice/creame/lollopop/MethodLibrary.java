package com.ice.creame.lollopop;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by hideya on 2016/02/20.
 */
public class MethodLibrary {

   /* Layout関連メソッド */

    //ScrollView生成メソッド
    public static ScrollView makeScrollView(int color, ViewGroup parent, Context context) {
        ScrollView scrollView = new ScrollView(context);
        scrollView.setBackgroundColor(color);
        if (parent != null) {
            parent.addView(scrollView);
        }
        return scrollView;
    }

    //RelativeLayout生成メソッド
    public static RelativeLayout makeRelativeLayout(int color, ViewGroup parent, RelativeLayout.LayoutParams param,
                                                    Context context) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        relativeLayout.setBackgroundColor(color);
        if (parent != null) {
            if (param != null) {
                parent.addView(relativeLayout, param);
            } else {
                parent.addView(relativeLayout);
            }
        }
        return relativeLayout;
    }

    //LinearLayout生成メソッド
    public static LinearLayout makeLinearLayout(int color, int orientation, ViewGroup parent, Context context) {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setBackgroundColor(color);
        linearLayout.setOrientation(orientation);
        if (parent != null) {
            parent.addView(linearLayout);
        }
        return linearLayout;
    }

    //テキスト生成メソッド
    //param有
    public static TextView makeTextView(String text, float size, int color, int id, ViewGroup parent,
                                        RelativeLayout.LayoutParams param, Context context) {
        TextView t = new TextView(context);
        t.setText(text);
        t.setTextSize(size);
        t.setTextColor(color);
        t.setId(id);
        t.setTypeface(BaseActivity.tf);
        /* これを設定するとテーブルの行に追加されない */
        //		t.setLayoutParams(new LinearLayout.LayoutParams(WC, WC));
        if (parent != null) {
            if (param != null) {
                parent.addView(t, param);
            } else {
                parent.addView(t);
            }
        }
        return t;
    }

    //ボタン生成メソッド
    //param有(うまくいかない)
    public static Button makeButton(String text, int id, String tag, ViewGroup parent,
                                    RelativeLayout.LayoutParams param, Context context) {
        Button button = new Button(context);
        button.setText(text);
        button.setBackgroundResource(R.drawable.abc_popup_background_mtrl_mult);
//		button.setBackgroundResource(R.drawable.button_brock);
        button.setId(id);
        button.setTag(tag);// ここではボタンが押されたかを判断（0：押されていない　1：押された）
        button.setTypeface(BaseActivity.tf);
        button.setOnClickListener((View.OnClickListener) context);
		/* これを設定するとテーブルの行に追加されない */
        button.setLayoutParams(new LinearLayout.LayoutParams(MainActivity.WC, MainActivity.WC));
        //親がないとき
        if (parent != null) {
            if (param != null) {
                parent.addView(button, param);
            } else {
                parent.addView(button);
            }
        }
        return button;
    }

    //エディットテキスト
    public static EditText makeEditText(String text, int size, int id, String tag, ViewGroup parent,
                                        RelativeLayout.LayoutParams param, Context context) {
        EditText et = new EditText(context);
        et.setText(text);
        et.setTextSize(size);
        et.setId(id);
        et.setTag(tag);

        et.setTextColor(Color.WHITE);
        et.setBackgroundColor(Color.argb(0, 0, 0, 0));
        et.setTypeface(BaseActivity.tf);

        if (parent != null) {
            if (param != null) {
                parent.addView(et, param);
            } else {
                parent.addView(et);
            }
        }
        return et;
    }

}
