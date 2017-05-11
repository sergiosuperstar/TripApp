package com.example.icf.tripappclient;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.icf.tripappclient.activities.LoginActivity;

import io.swagger.client.model.User;

/**
 * Created by Vuletic on 9.5.2017.
 */

public class SessionManager {

    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private int PRIVATE_MODE = 0;

    /*private boolean loggedIn = false;
    private String username;
    private String role;
    private String email;
    private String phoneNumber;
    private User user;*/

    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences("Session", PRIVATE_MODE);
        editor = pref.edit();
    }

    public boolean login(String username, String password){

        editor.putBoolean("loggedIn", true);
        editor.putString("username", username);
        editor.putString("role", "passenger");
        editor.commit();

        return true; //TODO: vratiti false pri failed login
    }

    public boolean isLoggedIn(){
        return pref.getBoolean("loggedIn", false);
    }

    public void logOut(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(_context, LoginActivity.class);

        _context.startActivity(i);
    }

    public User getUser(){
        User u = new User();
        u.setUsername(pref.getString("username", null));
        u.setRole(pref.getString("role", null));

        return u;
    }

    public String getUserRole(){
        return pref.getString("role", "passenger");
    }

}
