package com.ingesup.fabienlebon.antredeuxvins.Entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by fabienlebon on 16/03/2018.
 */

public class User implements Parcelable {
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
        Token = "";
    }

    public User(String mail, String password) {
        Mail = mail;
        Password = password;
        Token = "";
    }

    public User() {
    }

    public String getMail() {

        return Mail;
    }

    public String getToken() {

        return Token;
    }

    @Override
    public String toString() {
        return "User{" +
                "Mail='" + Mail + '\'' +
                ", Name='" + Name + '\'' +
                ", Password='" + Password + '\'' +
                ", Token='" + Token + '\'' +
                '}';
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

    public User(Parcel in) {
        Mail = in.readString();
        Name = in.readString();
        Password = in.readString();
        Token = in.readString();

    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        public User[] newArray(int size) {
            return new User[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(Mail);
        parcel.writeString(Name);
        parcel.writeString(Password);
        parcel.writeString(Token);
    }
}
