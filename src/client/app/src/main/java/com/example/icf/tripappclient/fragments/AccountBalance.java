package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.adapters.PaymentAdapter;

import java.util.List;

import io.swagger.client.model.AdapterPayment;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class AccountBalance extends Fragment {

    private SessionManager session;

    private List<AdapterPayment> payments;
    private ListView paymentsDisplay;
    private TextView noPaymentsDisplay;

    public AccountBalance() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.account_balance_title);

        session = new SessionManager(getActivity().getApplicationContext());

//        this.paymentsDisplay = (ListView) ((AppCompatActivity)getActivity()).findViewById(R.id.paymentsList);
//        this.noPaymentsDisplay = (TextView) ((AppCompatActivity)getActivity()).findViewById(R.id.emptyPaymentsLabel);
//        if (payments.size() > 0) {
//            this.paymentsDisplay.setVisibility(View.VISIBLE);
//            this.noPaymentsDisplay.setVisibility(View.GONE);
//            this.paymentsDisplay.setAdapter(new PaymentAdapter((AppCompatActivity)getActivity(), payments));
//        } else {
//            this.paymentsDisplay.setVisibility(View.GONE);
//            this.noPaymentsDisplay.setVisibility(View.VISIBLE);
//        }

        return inflater.inflate(R.layout.fragment_account_balance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView balance = (TextView) getView().findViewById(R.id.balanceValue);
        balance.setText(String.format("%.2f", session.getUser().getBalance()));

    }

   /* @Override
    public void onResume() {
        super.onResume();

        TextView balance = (TextView) getView().findViewById(R.id.balanceValue);
        balance.setText(String.format("%.2f", session.getUser().getBalance()));

    }*/

}
