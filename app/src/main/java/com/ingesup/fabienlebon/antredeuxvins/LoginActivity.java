package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Tasks.TaskService;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EmailValidator;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EncryptPassword;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;
import com.ingesup.fabienlebon.antredeuxvins.Tools.GlobalData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class LoginActivity extends AppCompatActivity implements TaskService.OnAsyncRequestComplete {

    private static final String TAG = "LoginActivity";
    private EmailValidator emailValidator;
    private EncryptPassword encryptPassword;
    private TextInputLayout mailWrapper, pswWrapper;

    private User user;
    private static final String apiURL = "https://reqres.in/api/login";
    private ArrayList<NameValuePair> params ;
    private String results ="";
    private JSONObject objects;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        mailWrapper     = (TextInputLayout) findViewById(R.id.login_TIL_Mailwrapper);
        pswWrapper      = (TextInputLayout) findViewById(R.id.login_TIL_pswWrapper);

        TextView textView = (TextView) findViewById(R.id.textViewLink);
        textView.setText(Html.fromHtml(getString(R.string.inscrivez_vous)));

        emailValidator  = new EmailValidator();
        encryptPassword = new EncryptPassword();

        User u = GlobalData.getInstance().getUserDao().selectionnerTout();
        if(u!=null){
            TaskService getPosts = new TaskService(this, "POST", getParams(u),"AUTO_LOGIN");
            getPosts.execute(apiURL);
        }

        Intent intent = getIntent();
        Bundle extra = intent.getExtras();


        if(extra != null) {
            if(extra.containsKey("parcel_user")){
                User userFromRegister = (User) getIntent().getParcelableExtra("parcel_user");
                mailWrapper.getEditText().setText(userFromRegister.getMail());
                pswWrapper.getEditText().setText(userFromRegister.getPassword());
            }
        }

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
                TaskService getPosts = new TaskService(this, "POST", params,"FIRST_LOGIN");
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
    public void asyncResponse(String response, String label) {
        // create a JSON array from the response string
        switch(label){
            case "FIRST_LOGIN" :
                try {
                objects = new JSONObject(response);

                    if(objects.has("token")){
                        user.setToken(objects.getString("token"));

                        GlobalData.getInstance().setUser(user);
                        GlobalData.getInstance().getUserDao().ajouter(user);

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
                break;
            case "AUTO_LOGIN" :

                try {
                    objects = new JSONObject(response);

                    if(objects.has("token")){
                        Intent i = new Intent(this.getApplicationContext(), CellarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }

    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", user.getMail()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        return params;
    }

    private ArrayList<NameValuePair> getParams(User u) {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", u.getMail()));
        params.add(new BasicNameValuePair("password", u.getPassword()));
        return params;
    }
}