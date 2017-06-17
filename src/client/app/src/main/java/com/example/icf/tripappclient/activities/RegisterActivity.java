package com.example.icf.tripappclient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;

public class RegisterActivity extends AppCompatActivity {

    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);

        session = new SessionManager(getApplicationContext());
    }

    public void registerClicked(View view){
        EditText mUsername = (EditText)findViewById(R.id.usernameInput);
        EditText mPassword = (EditText)findViewById(R.id.passwordInput);
        EditText mFirstname = (EditText)findViewById(R.id.firstNameInput);
        EditText mLastname = (EditText)findViewById(R.id.lastNameInput);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();

        String firstname = mFirstname.getText().toString();
        String lastname = mLastname.getText().toString();

        session.register(this, username, password, firstname, lastname);
    }

    public void respond(boolean success) {
        if (success){
            Toast.makeText(this, R.string.registration_successful, Toast.LENGTH_LONG).show();
            this.finish();
        }else{
            Toast.makeText(this, R.string.registration_failed, Toast.LENGTH_LONG).show();
        }
    }
}
