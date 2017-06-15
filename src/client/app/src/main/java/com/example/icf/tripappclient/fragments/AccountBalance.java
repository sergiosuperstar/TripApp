package com.example.icf.tripappclient.fragments;

import android.content.ContentResolver;
import android.database.Cursor;
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
import com.example.icf.tripappclient.activities.MainActivity;
import com.example.icf.tripappclient.adapters.PaymentAdapter;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.swagger.client.model.*;
import io.swagger.client.model.TicketPurchase;

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

        fillData();

        return inflater.inflate(R.layout.fragment_account_balance, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView balance = (TextView) getView().findViewById(R.id.balanceValue);
        balance.setText(String.format("%.2f", session.getUser().getBalance()));

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        this.paymentsDisplay = (ListView) activity.findViewById(R.id.paymentsList);
        this.noPaymentsDisplay = (TextView) activity.findViewById(R.id.emptyPaymentsLabel);
        if (payments.size() > 0) {
            this.paymentsDisplay.setVisibility(View.VISIBLE);
            this.noPaymentsDisplay.setVisibility(View.GONE);
            this.paymentsDisplay.setAdapter(new PaymentAdapter((AppCompatActivity)getActivity(), payments));
        } else {
            this.paymentsDisplay.setVisibility(View.GONE);
            this.noPaymentsDisplay.setVisibility(View.VISIBLE);
        }
    }

    private void fillData() {

        payments = new ArrayList<>();

        final Dao<AdapterPayment, Integer> paymentsDAO = ((MainActivity)getActivity()).getSession().getHelper().getPaymentDAO();

        try {
            payments = paymentsDAO.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Collections.sort(payments, new CustomComparator());

    }

    private class CustomComparator implements Comparator<AdapterPayment> {
        @Override
        public int compare(AdapterPayment o1, AdapterPayment o2) {
            return o1.getEndDateTime().compareTo(o2.getEndDateTime());
        }
    }

   /* @Override
    public void onResume() {
        super.onResume();

        TextView balance = (TextView) getView().findViewById(R.id.balanceValue);
        balance.setText(String.format("%.2f", session.getUser().getBalance()));

    }*/

}
