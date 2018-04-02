package com.ingesup.fabienlebon.antredeuxvins;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.ingesup.fabienlebon.antredeuxvins.Tools.EmailValidator;
import com.ingesup.fabienlebon.antredeuxvins.Tools.EncryptPassword;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout mailWrapper, nameWrapper, passWrapper, passRepWrapper;
    private String mail, name, password, passRep;

    private EmailValidator emailValidator;
    private EncryptPassword encryptPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        mailWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_Mailwrapper);
        nameWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_NameWrapper);
        passWrapper     = (TextInputLayout) findViewById(R.id.register_TIL_PassWrapper);
        passRepWrapper  = (TextInputLayout) findViewById(R.id.register_TIL_PassRepWrapper);

        String mail     = mailWrapper.getEditText().getText().toString();
        String name     = nameWrapper.getEditText().getText().toString();
        String password = passWrapper.getEditText().getText().toString();
        String passRep  = passRepWrapper.getEditText().getText().toString();

        emailValidator  = new EmailValidator();
        encryptPassword = new EncryptPassword();

    }

    public void registerToLoginOnclick(View view) {
        Intent i = new Intent(this.getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    public void registerOnclick(View view) {

        if(!mail.equals("") && !name.equals("") && !password.equals("") && !passRep.equals("")){
            if(emailValidator.validate(mail)){
                mailWrapper.setError(null);
                if(password.equals(passRep)){
                    passWrapper.setError(null);
                    Log.i("VAR 1",password);
                    Log.i("ACCEPT","Tout est OK, envois des données");
                    User user = new User(mail,name,password);
                    /*
                    try {
                       String result = new AsyncTaskRegister(RegisterActivity.this, user)
                                .execute("http://174.138.7.116:8080/CWS/api/createUser").get();

                        if(result.equals("-1")){
                            edtEmailWrapper.setError("Login déjà utilisé");
                        }
                        else if(result.equals("-2")){
                            edtIPCentreonWrapper.setError("Adresse IP ou compte Centreon non reconnu");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                    */
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
}
