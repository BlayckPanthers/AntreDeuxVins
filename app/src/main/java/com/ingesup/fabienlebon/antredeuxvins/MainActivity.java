package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ingesup.fabienlebon.antredeuxvins.Entities.Wine;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


    }

    public void registerOnclick(View view) {
        Intent i = new Intent();

    }
}
