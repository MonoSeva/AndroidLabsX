package com.example.appnanrj;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Users.db";

    public DatabaseHandler(Context context, SQLiteDatabase.CursorFactory factory) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE users (id INTEGER PRIMARY KEY, login TEXT, pass TEXT)";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("id", user.getId());
        values.put("pass", user.getPass());

        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("users", null, values);
        db.close();
    }

    public boolean getUser(String login, String pass) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor result = db.rawQuery("SELECT * FROM users WHERE login = '" + login + "' AND pass = '" + pass + "'", null);
        boolean exists = result.moveToFirst();
        result.close();
        return exists;
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String selectQuery = "SELECT * FROM users";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String login = cursor.getString(cursor.getColumnIndexOrThrow("login"));
                String pass = cursor.getString(cursor.getColumnIndexOrThrow("pass"));
                User user = new User(id, login, pass);
                userList.add(user);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return userList;
    }

    public void deleteUserFromDB(String loginToDelete) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "login=?", new String[]{loginToDelete});
        db.close();
    }

    public void PassChangeClick(User user, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Смена пароля");

        // Вводимая инф
        final EditText input = new EditText(context);
        builder.setView(input);

        // Кнопки
        builder.setPositiveButton("Сохранить", (dialog, which) -> {
            String newPassword = input.getText().toString();
            // Обновление пароля пользователя в БД
            DatabaseHandler dbHandler = new DatabaseHandler(context, null);
            User updatedUser = new User(user.getId(), user.getLogin(), newPassword);
            dbHandler.updateUser(updatedUser);
        });
        builder.setNegativeButton("Отмена", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("pass", user.getPass());

        db.update("users", values, "id = ?", new String[]{String.valueOf(user.getId())});

        db.close();
    }
}
