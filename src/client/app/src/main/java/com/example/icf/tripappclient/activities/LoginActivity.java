package com.example.icf.tripappclient.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;

public class LoginActivity extends AppCompatActivity {

    private SessionManager session;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        progress = new ProgressDialog(this);
        progress.setTitle(R.string.loading);
        progress.setCancelable(false);
    }

    public void loginClicked(View view){
        progress.setMessage("Collecting data. Please wait...");
        progress.show();
        EditText mUsername = (EditText)findViewById(R.id.usernameInput);
        EditText mPassword = (EditText)findViewById(R.id.passwordInput);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        session.login(this, username, password);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void respond(boolean successful) {
        if (successful) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
        }
        progress.dismiss();
    }

}
