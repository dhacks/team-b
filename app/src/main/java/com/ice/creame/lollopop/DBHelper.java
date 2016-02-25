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
        db.execSQL("create table if not exists " + DB_TABLE_NODE + "(id text primary key, condition text)");

        db.execSQL("Insert into " + DB_TABLE_NODE + " values('0', '性格')");
        db.execSQL("Insert into " + DB_TABLE_NODE + " values('1', 'ルックス')");
        db.execSQL("Insert into " + DB_TABLE_NODE + " values('2', '知的さ')");
        db.execSQL("Insert into " + DB_TABLE_NODE + " values('sound', '1')");

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
            c = db.query(db_table, new String[]{"id", "condition"},"id='" + id + "'", null, null, null, null);
            if (c.getCount() == 0) throw new Exception();
            c.moveToFirst();
            String str[] = new String[2];
            str[0] = c.getString(0);
            str[1] = c.getString(1);
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

            String sql = "update " + DB_TABLE_NODE + " set condition ='" + value1 + "' where id = '" + id + "';";

            db.execSQL(sql);
        } else {
            values.put("id", id);
            values.put("date", date);
            values.put("value1", value1);
            values.put("value2", value2);
            values.put("value3", value3);
            db.insert(db_table, "", values);
        }
    }

}