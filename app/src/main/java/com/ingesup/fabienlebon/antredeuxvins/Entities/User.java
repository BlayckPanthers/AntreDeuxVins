package com.ingesup.fabienlebon.antredeuxvins.Entities;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public class User {
    private String Mail;
    private String Name;
    private String Password;
    private String Token;

    public User(String mail, String name, String password, String token) {
        Mail = mail;
        Name = name;
        Password = password;
        Token = token;
    }

    public User(String mail, String name, String password) {
        Mail = mail;
        Name = name;
        Password = password;
    }

    public User(String mail, String password){
        this.Mail       = mail;
        this.Password   = password;
    }

    public String getMail() {

        return Mail;
    }

    public String getToken() {

        return Token;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
