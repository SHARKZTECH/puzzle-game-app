package com.example.puzzlegame;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tilepuzzlegame.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_SCORE = "score";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORES_TABLE = "CREATE TABLE " + TABLE_SCORES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_SCORE + " INTEGER" + ")";
        db.execSQL(CREATE_SCORES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORES);
        onCreate(db);
    }

    public void addScore(int score) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SCORE, score);
        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    public int getHighScore() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SCORES, new String[]{COLUMN_SCORE}, null, null, null, null, COLUMN_SCORE + " DESC", "1");
        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int highScore = cursor.getInt(cursor.getColumnIndex(COLUMN_SCORE));
            cursor.close();
            return highScore;
        } else {
            return 0;
        }
    }
}
