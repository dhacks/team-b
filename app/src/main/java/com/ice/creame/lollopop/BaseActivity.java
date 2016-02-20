package com.ice.creame.lollopop;


import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.LinearLayout;

/**
 * Created by hideya on 2016/02/20.
 */

public class BaseActivity extends AppCompatActivity {

    /* システム情報 */
    Display display;
    Point p;

    /* Final変数 */
    //ID
    final int NO_ID = -1;
    final String NO_TAG = "-1";

    //name
    final String APP_NAME = "Lolliopop";

    //default_text
    final String DEFAULT_NAME_M[] = {"太郎", "ポール", "アキラ", "タカシ"};
    final String DEFAULT_NAME_F[] = {"花子", "スーザン", "よーこ", "八重"};

    //index_text
    final String INDEX[] = {"2 : 2", "3 : 3", "4 : 4"};


    public static final int MP = LinearLayout.LayoutParams.MATCH_PARENT; //画面最大
    public static final int WC = LinearLayout.LayoutParams.WRAP_CONTENT; //自動調節


    //色
    final int COLOR_D = Color.RED;
    final int COLOR_1 = Color.argb(0, 0, 0, 0);
    final int COLOR_2 = Color.BLACK;
    final int COLOR_3 = Color.argb(255, 176, 48, 96);

    final int TITLE_COLOR = Color.rgb(255, 192, 203);

    final int TEXT_COLOR_1 = Color.rgb(255, 80, 80);

    //制限定数
    final int LIMIT_NAME = 6; //名前の最大文字数

    //フォント
    public static Typeface tf;

    //画像（背景）
    public final int BACK_GROUND_IMAGE = R.drawable.background;
//    public TextView textView;
//    public String str = "NotRead";
//    public XmlPullParser xmlPullParser = Xml.newPullParser();

    //グローバル変数
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        globals = (Globals) this.getApplication();

        /* 画面サイズ取得→機種対応 */
        display = getWindowManager().getDefaultDisplay();
        p = new Point();
        display.getSize(p);

        tf = Typeface.createFromAsset(getAssets(), "TanukiMagic.ttf");
    }
}
