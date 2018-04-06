package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Tasks.TaskService;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EmailValidator;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EncryptPassword;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class MainActivity extends AppCompatActivity implements TaskService.OnAsyncRequestComplete {

    private static final String TAG = "MainActivity";
    private EmailValidator emailValidator;
    private EncryptPassword encryptPassword;
    private TextInputLayout mailWrapper, pswWrapper;

    private User user;
    private String apiURL = "https://reqres.in/api/login";
    private ArrayList<NameValuePair> params ;
    private String results ="";
    private JSONObject objects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        mailWrapper     = (TextInputLayout) findViewById(R.id.login_TIL_Mailwrapper);
        pswWrapper      = (TextInputLayout) findViewById(R.id.login_TIL_pswWrapper);

        emailValidator  = new EmailValidator();
        encryptPassword = new EncryptPassword();

    }

    public void loginToRegisterOnclick(View view) {
        Intent i = new Intent(this.getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    public void loginOnClick(View view) {
        String email    = mailWrapper.getEditText().getText().toString();
        String password = pswWrapper.getEditText().getText().toString();

        if(!email.equals("") && !password.equals(""))
        {
            if(emailValidator.validate(email)){
                mailWrapper.setError(null);
                user = new User(email,password);

                params = getParams();
                TaskService getPosts = new TaskService(this, "POST", params);
                getPosts.execute(apiURL);

            }
            else{
                mailWrapper.setError("Adresse mail non valide");
            }

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Veuillez remplir les champs",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void asyncResponse(String response) {
        // create a JSON array from the response string
        try {
        objects = new JSONObject(response);

            if(objects.has("token")){
                user.setToken(objects.getString("token"));
                Intent i = new Intent(this.getApplicationContext(), CellarActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
            else {
                mailWrapper.setError("Mail ou password incorrect(s)");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", user.getMail()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        return params;
    }
}
