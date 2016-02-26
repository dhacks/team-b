package com.ice.creame.lollopop;


import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.Calendar;

import static com.ice.creame.lollopop.DBHelper.*;

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
    final String APP_NAME = "Deep Matching";

    //default_text
//    final String DEFAULT_NAME_M[] = {"太郎", "ポール", "アキラ", "タカシ"};
//    final String DEFAULT_NAME_F[] = {"花子", "スーザン", "よーこ", "八重"};
    final String DEFAULT_NAME_M[] = {"A男", "B男", "C男", "D男"};
    final String DEFAULT_NAME_F[] = {"A子", "B子", "C子", "D子"};

    //index_text
    final String INDEX[] = {"2 : 2", "3 : 3", "4 : 4"};

    //node_text
    String NODE[] = {null, null, null};

    //select_text
    final String SELECT1[] = {"が非常に重要", "がやや重要", "同等"};
    final String SELECT2[] = {"が非常に良い", "がやや良い", "同等"};

    //hash_table
    final int combination2[][] = {{0, 1}};
    final int combination3[][] = {{0, 1}, {0, 2}, {1, 2}};
    final int combination4[][] = {{0, 1}, {0, 2}, {0, 3}, {1, 2}, {1, 3}, {2, 3}};

    //ahp_param
    final double pair_comp[] = {9.0, 5.0, 1.0, 1.0 / 5.0, 1.0 / 9.0};

    public static final int MP = LinearLayout.LayoutParams.MATCH_PARENT; //画面最大
    public static final int WC = LinearLayout.LayoutParams.WRAP_CONTENT; //自動調節

    //色
    final int COLOR_D = Color.RED;
    final int COLOR_1 = Color.argb(0, 0, 0, 0);
    final int COLOR_2 = Color.rgb(0, 238, 0);
    final int COLOR_3 = Color.BLACK;

    final int TITLE_COLOR = Color.rgb(205, 0, 0);


    final int TEXT_COLOR_1 = Color.rgb(255, 80, 80);
    final int TEXT_COLOR_2 = Color.BLACK;
    final int TEXT_COLOR_3 = Color.WHITE;
    final int TEXT_COLOR_4 = Color.rgb(156, 156, 156);

    //サイズ
    final int TEXT_SIZE1 = 10;
    final int TEXT_SIZE2 = 20;
    final int TEXT_SIZE2_5 = 25;
    final int TEXT_SIZE3 = 30;
    final int TEXT_SIZE3_5 = 35;
    final int TEXT_SIZE4 = 40;
    final int TEXT_SIZE5 = 50;

    //制限定数
    final int LIMIT_NAME = 6; //名前の最大文字数

    //フォント
    public static Typeface tf;

    //画像（背景）
    public final int BACK_GROUND_IMAGE = R.drawable.carpet;

    /* DBデータ */
    SQLiteDatabase db; //データベースオブジェクト

    //グローバル変数
    Globals globals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        globals = (Globals) this.getApplication();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        /* データベースオブジェクトの取得 */
        DBHelper dbHelper = new DBHelper(this);
        db = dbHelper.getWritableDatabase();

        try {
            NODE[0] = readDB("0", DB_TABLE_NODE, db)[1];
            NODE[1] = readDB("1", DB_TABLE_NODE, db)[1];
            NODE[2] = readDB("2", DB_TABLE_NODE, db)[1];

        } catch (Exception e) {
            Log.d("d", "dbError");
        }

        /* 画面サイズ取得→機種対応 */
        display = getWindowManager().getDefaultDisplay();
        p = new Point();
        display.getSize(p);
        //フォント情報
        tf = Typeface.createFromAsset(getAssets(), "TanukiMagic.ttf");
        //連打対応カウント
        globals.soundFlag = false;
    }

    protected void onResume() {
        super.onResume();

        String isPlaySound = "-1"; //error
        try{
            isPlaySound = readDB("sound", DB_TABLE_NODE, db)[1];
        }catch(Exception e){

        }
        globals.soundpool = new SoundPool(3, AudioManager.STREAM_MUSIC, 0);

        if(isPlaySound.equals("1")) {
            globals.soundClick = globals.soundpool.load(this, R.raw.click, 1);
            globals.soundError = globals.soundpool.load(this, R.raw.miss, 1);
        }else{
            globals.soundClick = 0;
            globals.soundError = 0;
        }

        globals.soundFlag = false;
    }

    protected void onPause() {
        super.onPause();
        //解放
        globals.soundpool.unload(globals.soundClick);
        globals.soundpool.unload(globals.soundError);
        globals.soundpool.release();
    }


    public void seplay(SoundPool pool, int sound, boolean count) {
        //カウントtrueで連打
        if (count) pool.play(sound, 0.2f, 0.2f, 1, 0, 1.0f);
        else {
            pool.play(sound, 0.2f, 0.2f, 1, 0, 1.0f);
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
            ;
        }
    }
}
