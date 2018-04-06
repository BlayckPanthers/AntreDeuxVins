package com.ingesup.fabienlebon.antredeuxvins.Tools;

import com.ingesup.fabienlebon.antredeuxvins.Dao.UserDao;
import com.ingesup.fabienlebon.antredeuxvins.Entities.User;

/**
 * Created by fabienlebon on 06/04/2018.
 */


public class GlobalData  {
    private static GlobalData instance;

    // Global variable
    private User user;
    private UserDao userDao;

    // Restrict the constructor from being instantiated
    private GlobalData(){}

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDao getUserDao() { return userDao; }

    public void setUserDao(UserDao userDao) {this.userDao = userDao;}

    public static synchronized GlobalData getInstance(){
        if(instance==null){
            instance=new GlobalData();
        }
        return instance;
    }
}

