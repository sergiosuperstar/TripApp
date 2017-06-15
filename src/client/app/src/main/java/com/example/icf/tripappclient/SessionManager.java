package com.example.icf.tripappclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.icf.tripappclient.activities.LoginActivity;
import com.example.icf.tripappclient.database.DatabaseHelper;
import com.example.icf.tripappclient.activities.MainActivity;
import com.example.icf.tripappclient.fragments.AccountBalance;
import com.example.icf.tripappclient.service.ServiceUtils;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import io.swagger.client.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vuletic on 9.5.2017.
 */

public class SessionManager {

    private SharedPreferences pref;
    private Editor editor;
    private Context _context;
    private DatabaseHelper databaseHelper = null;
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

    public void login(LoginActivity activity, String username, String password){
        final LoginActivity login = activity;
        Call<User> call = ServiceUtils.userService.login(username, password);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user != null) {
                        editor.putBoolean("loggedIn", true);
                        editor.putLong("userId", user.getId());
                        editor.putString("username", user.getUsername());
                        editor.putString("email", user.getEmail());
                        editor.putString("firstName", user.getFirstName());
                        editor.putString("lastName", user.getLastName());
                        editor.putString("phone", user.getPhone());
                        editor.putString("role", user.getRole());
                        editor.putString("balance", user.getBalance().toString());
                        editor.commit();
                        login.respond(true);
                    }
                } else {
                    login.respond(false);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                login.respond(false);
            }
        });

        return;
    }

    public boolean isLoggedIn(){
        return pref.getBoolean("loggedIn", false);
    }

    public void logOut(){

        editor.clear();
        editor.commit();
    }

    public void setBalance(Double balance){
        editor.putString("balance", balance.toString());
        editor.commit();
    }

    public void addBalance(Double balance){
        User user = getUser();
        user.setBalance(user.getBalance() + balance);
        setBalance(user.getBalance());
    }

    public User getUser(){
        User u = new User();
        u.setUsername(pref.getString("username", null));
        u.setRole(pref.getString("role", "none"));
        u.setId(pref.getLong("userId", 0));
        u.setEmail(pref.getString("email", null));
        u.setFirstName(pref.getString("firstName", null));
        u.setLastName(pref.getString("lastName", null));
        u.setPhone(pref.getString("phone", null));
        u.setBalance(Double.parseDouble(pref.getString("balance", "0")));

        return u;
    }

    public String getUserRole(){
        return pref.getString("role", "none");
    }

    public void reloadUserBalance(MainActivity activity){

        final MainActivity main = activity;

        Call<User> call = ServiceUtils.userService.get(getUser().getId().toString(), getUser().getUsername());
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user != null) {
                        editor.putBoolean("loggedIn", true);
                        editor.putLong("userId", user.getId());
                        editor.putString("username", user.getUsername());
                        editor.putString("email", user.getEmail());
                        editor.putString("firstName", user.getFirstName());
                        editor.putString("lastName", user.getLastName());
                        editor.putString("phone", user.getPhone());
                        editor.putString("role", user.getRole());
                        editor.putString("balance", user.getBalance().toString());
                        editor.commit();

                        AccountBalance abFragment = new AccountBalance();
                        main.changeFragment(abFragment);

                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });

    }

    public DatabaseHelper getHelper() {
        if (databaseHelper == null) {
            databaseHelper = OpenHelperManager.getHelper(_context, DatabaseHelper.class);
        }
        return databaseHelper;
    }
}
