package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Tools.EmailValidator;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EncryptPassword;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;

public class MainActivity extends AppCompatActivity {

    private EmailValidator emailValidator;
    private EncryptPassword encryptPassword;
    private TextInputLayout mailWrapper, pswWrapper;

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
    }

    public void loginOnClick(View view) {
        String email    = mailWrapper.getEditText().getText().toString();
        String password = pswWrapper.getEditText().getText().toString();

        if(!email.equals("") && !password.equals(""))
        {
            if(emailValidator.validate(email)){
                mailWrapper.setError(null);
                User user = new User(email,password);
              //  new AsyncTaskConnexion(LoginActivty.this, user,mailWrapper)
              //          .execute("http://174.138.7.116:8080/CWS/api/verifUser");

              //  Intent intentToCellar = new Intent(getApplicationContext(), CellarActivity.class);
              //  startActivity(intentToCellar);

                Toast.makeText(getApplicationContext(),"Connexion ...",Toast.LENGTH_SHORT).show();

                Intent i = new Intent(this.getApplicationContext(), CellarActivity.class);
                startActivity(i);
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
}
