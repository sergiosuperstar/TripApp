package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.icf.tripappclient.R;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketPurchase extends Fragment {

    private double ticketPrice;

    public TicketPurchase() {
        ticketPrice = 6.99;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.ticket_purchase_title);


        return inflater.inflate(R.layout.fragment_ticket_purchase, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView priceText = (TextView) getView().findViewById(R.id.priceValue);
        priceText.setText(Double.toString(ticketPrice));

        //View view=inflater.inflate(R.layout.fragment_ticket_purchase, container, false);
        final Spinner countSpinner = (Spinner) getView().findViewById(R.id.numberValue);
        final TextView totalText = (TextView) getView().findViewById(R.id.totalValue);

        countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int count = Integer.parseInt(countSpinner.getSelectedItem().toString());
                String totalString = Double.toString(count * ticketPrice);
                totalText.setText(totalString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
}
