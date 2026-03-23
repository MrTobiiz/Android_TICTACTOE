package com.example.carogame.model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "caro.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_HISTORY = "history";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE history (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "boardSize TEXT," +
                "gameMode TEXT," +
                "dateTime TEXT," +
                "playerX TEXT," +
                "playerO TEXT," +
                "result TEXT," +
                "moves TEXT," +
                "duration TEXT)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}