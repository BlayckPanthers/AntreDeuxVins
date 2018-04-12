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

    // View objects
    private EmailValidator emailValidator;
    private TextInputLayout mailWrapper, pswWrapper;

    /*
      Instantiate three User
      user is sent to the first login
      userFromRegister come from RegisterActivity to fill the fields in the loginActivity after a register
      userFromDb come from database if the user as already logged in
     */
    private User user, userFromRegister, userFromDb;

    // Variables used by AsyncTask
    private static final String apiURL = "https://reqres.in/api/login";
    private ArrayList<NameValuePair> params;
    private JSONObject objects;

    // Variables for handling RegisterActivity intent
    private Intent intentFromRegister;
    private Bundle extraFromRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        // Initialize variables
        mailWrapper = (TextInputLayout) findViewById(R.id.login_TIL_Mailwrapper);
        pswWrapper = (TextInputLayout) findViewById(R.id.login_TIL_pswWrapper);
        TextView textView = (TextView) findViewById(R.id.textViewLink);
        textView.setText(Html.fromHtml(getString(R.string.login_link_register)));
        emailValidator = new EmailValidator();

        // Check if the userFromDb as already logged in
        userFromDb = GlobalData.getInstance().getUserDao().selectionnerTout();
        if (userFromDb != null) {
            TaskService getPosts = new TaskService(this, "POST", getParamsUserDao(), "AUTO_LOGIN");
            getPosts.execute(apiURL);
        }

        // Check if user come from RegisterActivity and fill the fields with his credentials
        intentFromRegister = getIntent();
        extraFromRegister = intentFromRegister.getExtras();
        if (extraFromRegister != null) {
            if (extraFromRegister.containsKey("parcel_user")) {
                userFromRegister = (User) getIntent().getParcelableExtra("parcel_user");
                mailWrapper.getEditText().setText(userFromRegister.getMail());
                pswWrapper.getEditText().setText(userFromRegister.getPassword());
            }
        }

    }

    /**
     * Link to the RegisterActivity
     *
     * @param view
     */
    public void loginToRegisterOnclick(View view) {
        Intent i = new Intent(this.getApplicationContext(), RegisterActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    /**
     * Login button with verifications on the fields and send to apiUrl params as an ArrayList<NameValuePair>()
     *
     * @param view
     */
    public void loginOnClick(View view) {
        String email = mailWrapper.getEditText().getText().toString();
        String password = pswWrapper.getEditText().getText().toString();
        if (!email.equals("") && !password.equals("")) {
            if (emailValidator.validate(email)) {
                mailWrapper.setError(null);
                user = new User(email, password);
                params = getParams();
                TaskService getPosts = new TaskService(this, "POST", params, "FIRST_LOGIN");
                getPosts.execute(apiURL);

            } else {
                mailWrapper.setError(String.valueOf(getText(R.string.login_error_invalid_mail)));
            }

        } else {
            Toast.makeText(getApplicationContext(), R.string.login_error_empty_fields, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onBackPressed() {

    }

    /**
     * Get the response
     * if ok :  - set a user in local database to allows the user to auto-connect after closing/oppening the app
     * - Intent to CellarActivity
     * <p>
     * if nok : Inform the user
     *
     * @param response : response from the remote API
     * @param label    : Allows multiTasking in same Activity
     */
    @Override
    public void asyncResponse(String response, String label) {
        // create a JSON array from the response string
        switch (label) {
            case "FIRST_LOGIN":
                try {
                    objects = new JSONObject(response);

                    if (objects.has("token")) {
                        user.setToken(objects.getString("token"));
                        if (extraFromRegister != null) {
                            if (extraFromRegister.containsKey("parcel_user")) {
                                user.setName(userFromRegister.getName());
                            }
                        }
                        GlobalData.getInstance().setUser(user);
                        GlobalData.getInstance().getUserDao().ajouter(user);

                        Intent i = new Intent(this.getApplicationContext(), CellarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    } else {
                        mailWrapper.setError(getText(R.string.login_error_incorrect_mail_or_password));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case "AUTO_LOGIN":

                try {
                    objects = new JSONObject(response);

                    if (objects.has("token")) {
                        Intent i = new Intent(this.getApplicationContext(), CellarActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

        }

    }


    /**
     * @return ArrayList<NameValuePair> params
     */
    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", user.getMail()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        return params;
    }

    /**
     * @return ArrayList<NameValuePair> params
     */
    private ArrayList<NameValuePair> getParamsUserDao() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", GlobalData.getInstance().getUserDao().selectionnerTout().getMail()));
        params.add(new BasicNameValuePair("password", GlobalData.getInstance().getUserDao().selectionnerTout().getPassword()));
        return params;
    }
}
