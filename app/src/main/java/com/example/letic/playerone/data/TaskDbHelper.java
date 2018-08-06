package com.example.letic.playerone.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TaskDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "favoritos.db";

    private static final int VERSION = 1;


    TaskDbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        final String CREATE_TABLE = "CREATE TABLE "  + TaskContract.TaskEntry.TABLE_NAME + " (" +
                TaskContract.TaskEntry._ID                + " INTEGER PRIMARY KEY, " +
                TaskContract.TaskEntry.COLUMN_ID + " TEXT NOT NULL, " +
                TaskContract.TaskEntry.COLUMN_NAME    + " TEXT NOT NULL, "+
                TaskContract.TaskEntry.COLUMN_AUTHOR + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE_NAME);
        onCreate(db);
    }
}