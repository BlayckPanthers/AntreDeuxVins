package com.ingesup.fabienlebon.antredeuxvins.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.ingesup.fabienlebon.antredeuxvins.Entities.User;

/**
 * Created by fabienlebon on 06/04/2018.
 */

public class UserDao extends DAOBase {
    public static final String TABLE_NAME = "User";
    public static final String USER_EMAIL = "email_user";
    public static final String USER_NAME = "name_user";
    public static final String USER_PASSWORD = "password_user";
    public static final String USER_TOKEN = "token_user";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    USER_EMAIL + " TEXT PRIMARY KEY, " +
                    USER_NAME + " TEXT, " +
                    USER_PASSWORD + " TEXT, " +
                    USER_TOKEN + " TEXT);";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public UserDao(Context pContext) {
        super(pContext);
    }

    /**
     * @param u le métier à ajouter à la base
     */
    public void ajouter(User u) {
        ContentValues value = new ContentValues();
        value.put(UserDao.USER_EMAIL, u.getMail());
        value.put(UserDao.USER_NAME, u.getName());
        value.put(UserDao.USER_PASSWORD, u.getPassword());
        value.put(UserDao.USER_TOKEN, u.getToken());
        mDb.insert(UserDao.TABLE_NAME, null, value);
    }

    /**
     * @param email l'identifiant du métier à supprimer
     */
    public void supprimer(String email) {
        mDb.delete(TABLE_NAME, USER_EMAIL + " = ?", new String[]{email});
    }

    /**
     * @param u le métier modifié
     */
    public void modifier(User u) {
        ContentValues value = new ContentValues();
        value.put(UserDao.USER_EMAIL, u.getMail());
        mDb.update(TABLE_NAME, value, USER_EMAIL + " = ?", new String[]{u.getMail()});

    }

    /**
     * @param email l'identifiant du métier à récupérer
     */
    public User selectionner(String email) {
        Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
        User u = new User();
        while (c.moveToNext()) {
            u.setMail(c.getString(0));
            u.setPassword(c.getString(1));
        }
        c.close();
        return u;
    }

    public User selectionnerTout() {
        User u = new User();
        try {
            Cursor c = mDb.rawQuery("select * from " + TABLE_NAME, null);
            if (c.getCount() != 0) {
                while (c.moveToNext()) {
                    u.setMail(c.getString(0));
                    u.setName(c.getString(1));
                    u.setPassword(c.getString(2));
                    u.setToken(c.getString(3));
                }
            } else
                u = null;
            c.close();
        } catch (Exception e) {
            u = null;
        }
        return u;
    }
}