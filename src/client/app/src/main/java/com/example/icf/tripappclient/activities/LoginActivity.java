package com.example.icf.tripappclient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;

public class LoginActivity extends AppCompatActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());
    }

    public void loginClicked(View view){
        EditText mUsername = (EditText)findViewById(R.id.usernameInput);
        EditText mPassword = (EditText)findViewById(R.id.passwordInput);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        UsersApi api = new UsersApi();

        // TODO FTN: Test call - for some reason we are getting time out exception!!!

        // TODO FTN: FOUND IT. It should be called on another thread (NOT UI THREAD)!!!
        /*try {
            String result = api.loginUser(username, password);
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ApiException e) {
            e.printStackTrace();
        }*/

        session.login(username, password);

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }

}
