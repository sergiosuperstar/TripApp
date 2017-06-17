package com.example.icf.tripappclient.activities;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    private SessionManager session;

    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());

        setContentView(R.layout.activity_settings);
        toolbar = (Toolbar) findViewById(R.id.toolbar_settings);
        toolbar.setTitle("Settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        getFragmentManager().beginTransaction().replace(R.id.settings_fragment_container, new SettingsFragment()).commit();
    }

    public static class SettingsFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            addPreferencesFromResource(R.xml.settings_screen);
        }

        @Override
        public void onStart() {
            super.onStart();

            SessionManager session = ((SettingsActivity) getActivity()).getSession();
            android.preference.PreferenceCategory preferencesCategory =
                    (android.preference.PreferenceCategory) findPreference("history_category");
            android.preference.ListPreference scannedOption =
                    (android.preference.ListPreference) preferencesCategory.findPreference("scanned_history");

            if (!session.getUserRole().equals("controller")) {
                preferencesCategory.removePreference(scannedOption);
            }
        }
    }

    public SessionManager getSession() {
        return session;
    }

}