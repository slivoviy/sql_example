package com.example.sql_example.repository;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sql_example.domain.Friendship;
import com.example.sql_example.domain.User;

import java.util.ArrayList;

public class DatabaseRepository {
    private final String TAG = "DatabaseRepository";
    private DatabaseHelper databaseHelper;

    public DatabaseRepository(Context context) {
        initDb(context);
    }

    private void initDb(Context context) {
        databaseHelper = new DatabaseHelper(context, "UserDb", null);
    }

    public boolean insertUser(String name, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        if (getUser(name, password) != null) {
            return false;
        } else {
            db.execSQL(SQLScripts.insertUserScript(name, password));
            return true;
        }
    }

    public User getUser(String name, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor userCursor = db.rawQuery(SQLScripts.getUserScript(name, password), null);
        // Cтавим позицию курсора на первую строку выборки
        // Eсли в выборке нет строк, вернется false
        if (userCursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = userCursor.getColumnIndex("id");
            int nameColIndex = userCursor.getColumnIndex("name");
            int passwordColIndex = userCursor.getColumnIndex("password");

            // получаем значения по номерам столбцов
            User user = new User(userCursor.getString(idColIndex),
                    userCursor.getString(nameColIndex),
                    userCursor.getString(passwordColIndex));
            userCursor.close();
            return user;
        } else {
            userCursor.close();
            return null;
        }
    }

    public ArrayList<User> getUsers(int limit) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor userCursor = db.rawQuery(SQLScripts.getAllUsersScript(limit), null);
        // Cтавим позицию курсора на первую строку выборки
        // Eсли в выборке нет строк, вернется false
        if (userCursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = userCursor.getColumnIndex("id");
            int nameColIndex = userCursor.getColumnIndex("name");
            int passwordColIndex = userCursor.getColumnIndex("password");

            ArrayList<User> userList = new ArrayList<>();
            do {
                // получаем значения по номерам столбцов
                User user = new User(userCursor.getString(idColIndex),
                        userCursor.getString(nameColIndex),
                        userCursor.getString(passwordColIndex));
                userList.add(user);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (userCursor.moveToNext());
            userCursor.close();
            return userList;
        } else {
            userCursor.close();
            return null;
        }
    }

    public ArrayList<User> getUsers() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Cursor userCursor = db.rawQuery(SQLScripts.getAllUsersScript(), null);
        // Cтавим позицию курсора на первую строку выборки
        // Eсли в выборке нет строк, вернется false
        if (userCursor.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int idColIndex = userCursor.getColumnIndex("id");
            int nameColIndex = userCursor.getColumnIndex("name");
            int passwordColIndex = userCursor.getColumnIndex("password");

            ArrayList<User> userList = new ArrayList<>();
            do {
                // получаем значения по номерам столбцов
                User user = new User(userCursor.getString(idColIndex),
                        userCursor.getString(nameColIndex),
                        userCursor.getString(passwordColIndex));
                userList.add(user);
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (userCursor.moveToNext());
            userCursor.close();
            return userList;
        } else {
            userCursor.close();
            return null;
        }
    }

    public Friendship getFriendship(String firstId, String secondId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int _firstId = Integer.parseInt(firstId);
        int _secondId = Integer.parseInt(secondId);
        Cursor cursor = db.rawQuery(SQLScripts.getFriendshipScript(_firstId, _secondId), null);
        if(cursor.moveToFirst()) {
            int idColIndex = cursor.getColumnIndex("id");
            int firstIdColIndex = cursor.getColumnIndex("firstId");
            int secondIdColIndex = cursor.getColumnIndex("secondId");
            int isConfirmColIndex = cursor.getColumnIndex("isConfirm");

            if(cursor.getInt(firstIdColIndex) == _firstId &&
                    cursor.getInt(secondIdColIndex) == _secondId) {
                String id = Integer.toString(cursor.getInt(idColIndex));
                boolean isConfirm = cursor.getInt(isConfirmColIndex) > 0;
                Friendship friendship = new Friendship(id, firstId, secondId, isConfirm);
                cursor.close();
                return friendship;
            } else {
                String id = Integer.toString(cursor.getInt(idColIndex));
                boolean isConfirm = cursor.getInt(isConfirmColIndex) > 0;
                Friendship friendship = new Friendship(id, secondId, firstId, isConfirm);
                cursor.close();
                return friendship;
            }
        } else {
            return null;
        }
    }

    public boolean insertFriendship(String firstId, String secondId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int _firstId = Integer.parseInt(firstId);
        int _secondId = Integer.parseInt(secondId);
        Cursor cursor = db.rawQuery(SQLScripts.getFriendshipScript(_firstId, _secondId), null);
        if (cursor.moveToFirst()) {
            cursor.close();
            return false;
        } else {
            cursor.close();
            db.execSQL(SQLScripts.insertFriendshipScript(_firstId, _secondId));
            return true;
        }
    }

    public byte checkFriendship(String firstId, String secondId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int _firstId = Integer.parseInt(firstId);
        int _secondId = Integer.parseInt(secondId);
        Cursor cursor = db.rawQuery(SQLScripts.getAllFriendshipsScript(), null);
        if(cursor.moveToFirst()) {
            int firstIdColIndex = cursor.getColumnIndex("firstId");
            int secondIdColIndex = cursor.getColumnIndex("secondId");
            int isConfirmColIndex = cursor.getColumnIndex("isConfirm");

            do {
                if((_firstId == cursor.getInt(firstIdColIndex) &&
                   _secondId == cursor.getInt(secondIdColIndex)) ||
                   (_firstId == cursor.getInt(secondIdColIndex) &&
                   _secondId == cursor.getInt(firstIdColIndex))) {
                    if(cursor.getInt(isConfirmColIndex) > 0) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return 0;
    }

    public void confirmFriendship(String firstId, String secondId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int _firstId = Integer.parseInt(firstId);
        int _secondId = Integer.parseInt(secondId);
        Cursor cursor = db.rawQuery(SQLScripts.getFriendshipScript(_firstId, _secondId), null);
        if(cursor.moveToFirst()) {
            db.execSQL(SQLScripts.updateIsConfirmScript(_firstId, _secondId));
        }
        cursor.close();
    }

    public void deleteFriendship(String firstId, String secondId) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int _firstId = Integer.parseInt(firstId);
        int _secondId = Integer.parseInt(secondId);
        db.execSQL(SQLScripts.deleteFriendshipScript(_firstId, _secondId));
    }
}

