package com.example.icf.tripappclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.icf.tripappclient.activities.LoginActivity;
import com.example.icf.tripappclient.service.ServiceUtils;

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
//
//        UsersApi api = new UsersApi();
//
//        // TODO FTN: Test call - for some reason we are getting time out exception!!!
//
//        // TODO FTN: FOUND IT. It should be called on another thread (NOT UI THREAD)!!!
//        /*try {
//            String result = api.loginUser(username, password);
//        } catch (TimeoutException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ApiException e) {
//            e.printStackTrace();
//        }*/
//
//        editor.putBoolean("loggedIn", true);
//        editor.putString("username", username);
//        editor.putString("role", "passenger");
//        editor.commit();
        final LoginActivity login = activity;
        Call<User> call = ServiceUtils.userService.login(username, password);
        call.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    User user = response.body();
                    if (user != null) {
                        editor.putBoolean("loggedIn", true);
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
