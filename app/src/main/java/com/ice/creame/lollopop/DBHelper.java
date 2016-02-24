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
    static final String DB_TABLE_RECORD = "testtable"; //記録保存のテーブル名
    static final String DB_TABLE_NODE = "nodetable";//ノード変更のテーブル名
    static final int DB_VERSION = 1; //バージョン

    //データベースヘルパーのコンストラクタ
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    //データベースの生成
    /* アンインストールしないと呼ばれない */
    @Override
    public void onCreate(SQLiteDatabase db) {
        //テーブルの生成
        Log.d("mydebug", "DBのonCreate");
        db.execSQL("create table if not exists " + DB_TABLE_RECORD + "(id text primary key, date text, value1 text, value2 text, value3 text)");
        Log.d("mydebug", "DBのonCreate2");
        db.execSQL("create table if not exists " + DB_TABLE_NODE + "(condition1 text, condition2 text, condition3 text)");

        //ノードデフォルト設定
        String d_node[] = {"性格さふぁ", "ルックス", "知的さ"};
        ContentValues d_values = new ContentValues();
        d_values.put("condition1", d_node[0]);
        d_values.put("condition2", d_node[1]);
        d_values.put("condition3", d_node[2]);
        db.insert(DB_TABLE_NODE, "", d_values);
    }

    //データベースのアップグレード
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("d", "DBのonUpgrade");
        db.execSQL("drop table if exists " + DB_TABLE_RECORD);
        onCreate(db);
        //これわからぬ
        db.execSQL("drop table if exists " + DB_TABLE_NODE);
        onCreate(db);

    }

    //データベースからの読み込み
    public static String[] readDB(String id, String db_table, SQLiteDatabase db) throws Exception {
        //node
        if (db_table.equals(DB_TABLE_NODE)) {
            Cursor c = null;
            c = db.query(db_table, new String[]{"condition1", "condition2", "condition3"},null, null, null, null, null);
            if (c.getCount() == 0) throw new Exception();
            c.moveToFirst();
            String str[] = new String[3];
            str[0] = c.getString(1);
            str[1] = c.getString(2);
            str[2] = c.getString(3);
            c.close();
            return str;
        }
        //それ以外(record)
        else {
            Cursor c = null;
            c = db.query(db_table, new String[]{"id", "date", "value1", "value2", "value3"}, "id='" + id + "'", null, null, null, null);
            if (c.getCount() == 0) throw new Exception();
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

    }

    //データベースへの書き込み
    public static void writeDB(String id, String date, String value1, String value2, String value3, String db_table, SQLiteDatabase db) throws Exception {

        ContentValues values = new ContentValues();

        if (db_table.equals(DB_TABLE_NODE)) {
            values.put("condition1", value1);
            values.put("condition2", value2);
            values.put("condition3", value3);
            long Id = db.insert(db_table, "", values);
        } else {
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


}