package com.example.wy.fingerkitchen.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author LiLang
 * @date 2019/3/30
 */
public class DishSQLiteHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "finger_kitchen.db";
    public static final int DB_VERSION = 1;
    public static final String TABLE_COLLECT = "table_collect";

    private static final String CREATE_TABLE_COLLECT_SQL =
            "create table " + TABLE_COLLECT + "(dish_id integer primary key autoincrement,dish_image_url text,dish_name text,dish_info text)";

    public DishSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_COLLECT_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
