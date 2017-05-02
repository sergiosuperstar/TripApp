package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.icf.tripappclient.R;

//public class SettingsFragment extends PreferenceFragmentCompat {
public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings_screen);
    }

    //@Override
   // public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

    //}
}
