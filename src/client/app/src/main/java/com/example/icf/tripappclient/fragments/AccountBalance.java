package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class AccountBalance extends Fragment {

    private SessionManager session;

    public AccountBalance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.account_balance_title);

        session = new SessionManager(getActivity().getApplicationContext());

        return inflater.inflate(R.layout.fragment_account_balance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView balance = (TextView) getView().findViewById(R.id.balanceValue);
        balance.setText(session.getUser().getBalance().toString());

    }

}
