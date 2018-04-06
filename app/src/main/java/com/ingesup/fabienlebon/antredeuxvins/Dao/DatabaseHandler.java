package com.ingesup.fabienlebon.antredeuxvins.Dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fabienlebon on 06/04/2018.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    public static final String USER_EMAIL = "email_user";
    public static final String USER_NAME = "name_user";
    public static final String USER_PASSWORD = "password_user";
    public static final String USER_TOKEN = "token_user";

    public static final String USER_TABLE_NAME = "User";
    public static final String USER_TABLE_CREATE =
            "CREATE TABLE " + USER_TABLE_NAME + " (" +
                    USER_EMAIL + " TEXT PRIMARY KEY, " +
                    USER_NAME + " TEXT, " +
                    USER_PASSWORD + " TEXT, " +
                    USER_TOKEN + " TEXT);";

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USER_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);

        // Create tables again
        onCreate(sqLiteDatabase);
    }
}
