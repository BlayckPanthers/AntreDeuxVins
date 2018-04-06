package com.ingesup.fabienlebon.antredeuxvins;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.ingesup.fabienlebon.antredeuxvins.Dao.UserDao;
import com.ingesup.fabienlebon.antredeuxvins.Tools.GlobalData;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GlobalData.getInstance().setUserDao(new UserDao(getApplicationContext()));
        GlobalData.getInstance().getUserDao().open();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}