package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icf.tripappclient.R;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class AccountBalance extends Fragment {

    public AccountBalance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.account_balance_title);

        return inflater.inflate(R.layout.fragment_account_balance, container, false);
    }


}
