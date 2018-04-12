package com.ingesup.fabienlebon.antredeuxvins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ingesup.fabienlebon.antredeuxvins.Dao.UserDao;
import com.ingesup.fabienlebon.antredeuxvins.Tools.GlobalData;

public class MainActivity extends Activity {

    /**
     * This activity is used to check if a user has been already logged to the app
     * @param savedInstanceState
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Open Sqlite database connexion
        GlobalData.getInstance().setUserDao(new UserDao(getApplicationContext()));
        GlobalData.getInstance().getUserDao().open();
        // Intent to LoginActivity
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}