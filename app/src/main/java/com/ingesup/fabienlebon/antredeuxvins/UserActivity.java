package com.ingesup.fabienlebon.antredeuxvins;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TextView;

import com.ingesup.fabienlebon.antredeuxvins.Tools.GlobalData;

public class UserActivity extends Activity {

    private TextView name, mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        name = (TextView) findViewById(R.id.user_username);
        mail = (TextView) findViewById(R.id.user_usermail);

        mail.setText(GlobalData.getInstance().getUserDao().selectionnerTout().getMail());

        name.setText(GlobalData.getInstance().getUserDao().selectionnerTout().getName());
    }

    public void DisconnectOnClick(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.dialog_message).setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                GlobalData.getInstance().getUserDao().supprimer(GlobalData.getInstance().getUserDao().selectionnerTout().getMail());
                GlobalData.getInstance().setUser(null);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {}
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
