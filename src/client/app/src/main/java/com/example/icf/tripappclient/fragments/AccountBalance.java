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
import com.example.icf.tripappclient.adapters.PaymentAdapter;
import com.example.icf.tripappclient.database.DBContentProvider;
import com.example.icf.tripappclient.database.TrippSQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.swagger.client.model.AdapterPayment;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class AccountBalance extends Fragment {

    private SessionManager session;

    private List<AdapterPayment> payments;
    private ListView paymentsDisplay;
    private TextView noPaymentsDisplay;

    private ContentResolver resolver;

    public AccountBalance() {
        // Required empty public constructor
        resolver = getContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.account_balance_title);

        session = new SessionManager(getActivity().getApplicationContext());

        fillData();

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

    private void fillData() {

        payments = new ArrayList<AdapterPayment>();

        String[] projection = new String[]{ TrippSQLiteHelper.COLUMN_P_ID,
                TrippSQLiteHelper.COLUMN_P_TICKET, TrippSQLiteHelper.COLUMN_P_DATE,
                TrippSQLiteHelper.COLUMN_P_EXPENSE, TrippSQLiteHelper.COLUMN_P_PRICE};
        //String whereClause = TrippSQLiteHelper.COLUMN_T_END + " < ?";
        //String[] whereArgs = new String[] { new Date().toString() };
        String orderBy = TrippSQLiteHelper.COLUMN_P_DATE;

        Cursor cursor = resolver.query(DBContentProvider.CONTENT_URL_P, null, null, null, orderBy);

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_P_ID));
                double price = cursor.getDouble(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_P_TICKET));
                String date = cursor.getString(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_P_DATE));
                String name = cursor.getString(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_P_EXPENSE));
                boolean expense = cursor.getInt(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_P_PRICE)) > 0;

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm", Locale.getDefault());
                java.util.Date dateStr = null;
                try {
                    dateStr = sdf.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                AdapterPayment payment = new AdapterPayment();

                payment.setPaymentId(id);
                payment.setPrice(price);
                payment.setEndDateTime(dateStr);
                payment.setTicketName(name);
                payment.setIsExpense(expense);

                payments.add(payment);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }
}
