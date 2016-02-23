package com.ice.creame.lollopop;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hideya on 2016/02/22.
 */
public class DBHelper extends SQLiteOpenHelper {

    /* DBデータ */
    static final String DB_NAME = "mydatabase.db";//DB名
    static final String DB_TABLE = "testtable"; //テーブル名
    static final int DB_VERSION = 1; //バージョン

    //データベースヘルパーのコンストラクタ
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //データベースの生成
    /* アンインストールしないと呼ばれない */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("mydebug", "DBのonCreate");
        db.execSQL("create table if not exists " + DB_TABLE + "(id text primary key, date text, value1 text, value2 text, value3 text)");
        Log.d("mydebug", "DBのonCreate2");
    }

    //データベースのアップグレード
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("d", "DBのonUpgrade");
        db.execSQL("drop table if exists " + DB_TABLE);
        onCreate(db);
    }

    //データベースからの読み込み
    public static String[] readDB(String id, String db_table, SQLiteDatabase db) throws Exception {

        Cursor c = null;
        c = db.query(db_table, new String[]{"id", "date", "value1", "value2", "value3"}, "id='" + id + "'", null, null, null, null);

        if (c.getCount() == 0)
            throw new Exception();
        c.moveToFirst();
        String str[] = new String[5];
        str[0] = c.getString(0);
        str[1] = c.getString(1);
        str[2] = c.getString(2);
        str[3] = c.getString(3);
        str[4] = c.getString(4);
        c.close();
        return str;
    }

    //データベースへの書き込み
    public static void writeDB(String id, String date, String value1 ,String value2 ,String value3 , String db_table, SQLiteDatabase db) throws Exception {
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("date", date);
        values.put("value1", value1);
        values.put("value2", value2);
        values.put("value3", value3);
//        int colNum = db.update(db_table, values, null, null);
//        if (colNum == 0)
            db.insert(db_table, "", values);
    }
}