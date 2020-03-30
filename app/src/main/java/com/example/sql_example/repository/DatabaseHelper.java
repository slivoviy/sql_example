package com.example.sql_example.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final String TAG = "DatabaseHelper";
    private static final int VERSION = 2;

    public DatabaseHelper(Context context,
                          String name,
                          SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Инициализация базы пользователей...");
        db.execSQL(SQLScripts.initUsersDbScript());
        Log.d(TAG, "База пользователей инициализирована");
        Log.d(TAG, "Инициализация базы дружбы...");
        db.execSQL(SQLScripts.initFriendshipDbScript());
        Log.d(TAG, "База дружбы инициализирована");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists friendship");
        onCreate(db);
    }
}
