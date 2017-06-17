package com.example.icf.tripappclient.service;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by ICF on 6/15/2017.
 */

public class TripAppFirebaseInstanceIdService extends FirebaseInstanceIdService {
    private int PRIVATE_MODE = 0;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        _context = getApplicationContext();
        pref = _context.getSharedPreferences("notification", PRIVATE_MODE);
        editor = pref.edit();
        editor.putString("token", refreshedToken);
        editor.commit();
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        // TODO: Add custom code:
    }
}
