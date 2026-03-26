package com.example.carogame.model.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.carogame.model.history.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class HistoryDAO {

    DatabaseHelper dbHelper;

    public HistoryDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    // INSERT
    public void insert(HistoryItem item) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("boardSize", item.boardSize);
        values.put("gameMode", item.gameMode);
        values.put("dateTime", item.dateTime);
        values.put("playerX", item.playerX);
        values.put("playerO", item.playerO);
        values.put("result", item.result);
        values.put("moves", item.moves);
        values.put("duration", item.duration);

        db.insert("history", null, values);
        db.close();
    }

    // GET ALL
    public List<HistoryItem> getAll() {

        List<HistoryItem> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM history ORDER BY id DESC", null);

        while (cursor.moveToNext()) {

            HistoryItem item = new HistoryItem(
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );

            list.add(item);
        }

        cursor.close();
        db.close();

        return list;
    }

    // DELETE ALL
    public void deleteAll() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("history", null, null);
        db.close();
    }
}