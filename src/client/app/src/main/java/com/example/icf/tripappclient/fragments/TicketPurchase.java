package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.icf.tripappclient.R;

import io.swagger.client.model.TicketType;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketPurchase extends Fragment {

    private double ticketPrice;
    private int ticketTypeId;

    private TicketType type;

    public TicketPurchase() {
        ticketPrice = 6.99;
        ticketTypeId = 1;
    }

    public static TicketPurchase newInstance(TicketType type) {
        TicketPurchase fragment = new TicketPurchase();
        Bundle args = new Bundle();
        args.putSerializable("ticket_type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.ticket_purchase_title);

        type = (TicketType) getArguments().getSerializable("ticket_type");

        View view = inflater.inflate(R.layout.fragment_ticket_purchase,
                container, false);
        Button button = (Button) view.findViewById(R.id.buyButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //TODO: rest api -> ticket_id, user_id, broj ljudi
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        TextView priceText = (TextView) getView().findViewById(R.id.priceValue);
        priceText.setText(type.getPrice().toString());

        TextView ticketHeader = (TextView) getView().findViewById(R.id.ticketHeader);
        ticketHeader.setText(type.getName());

        TextView duration = (TextView) getView().findViewById(R.id.durationValue); // TODO: obrisati iz dizajna
        duration.setText(type.getDuration() + " hours");


        //View view=inflater.inflate(R.layout.fragment_ticket_purchase, container, false);
        final Spinner countSpinner = (Spinner) getView().findViewById(R.id.numberValue);
        final TextView totalText = (TextView) getView().findViewById(R.id.totalValue);

        countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int count = Integer.parseInt(countSpinner.getSelectedItem().toString());
                String totalString = Double.toString(count * type.getPrice());
                totalText.setText(totalString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


}
