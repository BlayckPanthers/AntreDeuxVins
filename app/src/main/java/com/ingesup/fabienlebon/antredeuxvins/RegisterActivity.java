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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class RegisterActivity extends AppCompatActivity implements TaskService.OnAsyncRequestComplete{

    private static final String TAG = "RegisterActivity";
    private TextInputLayout mailWrapper, nameWrapper, passWrapper, passRepWrapper;
    private String mail, name, password, passRep;

    private EmailValidator emailValidator;
    private EncryptPassword encryptPassword;
    private String apiURL = "https://reqres.in/api/register";
    private ArrayList<NameValuePair> params ;
    private String results ="";
    private JSONObject objects;
    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mailWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_Mailwrapper);
        nameWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_NameWrapper);
        passWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_PassWrapper);
        passRepWrapper  = (TextInputLayout) findViewById(R.id.register_TIL_PassRepWrapper);



        emailValidator  = new EmailValidator();
        encryptPassword = new EncryptPassword();

    }

    public void registerToLoginOnclick(View view) {
        Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void registerOnclick(View view) {

        mail     = mailWrapper.getEditText().getText().toString();
        name     = nameWrapper.getEditText().getText().toString();
        password = passWrapper.getEditText().getText().toString();
        passRep  = passRepWrapper.getEditText().getText().toString();

        if(!mail.equals("") && !name.equals("") && !password.equals("") && !passRep.equals("")){
            if(emailValidator.validate(mail)){
                mailWrapper.setError(null);
                if(password.equals(passRep)){
                    passWrapper.setError(null);
                    Log.i("VAR 1",password);
                    Log.i("ACCEPT","Tout est OK, envois des données");
                    user = new User(mail,name,password);

                    params = getParams();
                    TaskService getPosts = new TaskService(this, "POST", params, "REGISTER");
                    getPosts.execute(apiURL);
                }
                else{
                    passWrapper.setError("Les mots de passes doivent être identiques");
                }
            }
            else {
                mailWrapper.setError("Adresse Email non valide");
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void asyncResponse(String response, String label) {
        if(label.equals("REGISTER")){
            try {
                objects = new JSONObject(response);

                if(objects.has("token")){
                    user.setToken(objects.getString("token"));
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("parcel_user", user);
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
    }

    private ArrayList<NameValuePair> getParams() {
        // define and ArrayList whose elements are of type NameValuePair
        ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("email", user.getMail()));
        params.add(new BasicNameValuePair("name", user.getMail()));
        params.add(new BasicNameValuePair("password", user.getPassword()));
        return params;
    }
}
