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
import com.example.icf.tripappclient.adapters.TicketAdapter;

import java.util.List;

import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.AdapterTicket;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketHistory extends Fragment {

    private List<AdapterTicket> ticketsValid;
    private List<AdapterTicket> ticketsExpired;
    private ListView validTicketsDisplay;
    private ListView expiredTicketsDisplay;
    private TextView noValidTicketsDisplay;
    private TextView noExpiredTicketsDisplay;

    public TicketHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.ticket_history_title);

//        this.noValidTicketsDisplay = (TextView) ((AppCompatActivity)getActivity()).findViewById(R.id.emptyActiveLabel);
//        this.noExpiredTicketsDisplay = (TextView) ((AppCompatActivity)getActivity()).findViewById(R.id.emptyHistoryLabel);
//        this.validTicketsDisplay = (ListView) ((AppCompatActivity)getActivity()).findViewById(R.id.validTicketsList);
//        this.expiredTicketsDisplay = (ListView) ((AppCompatActivity)getActivity()).findViewById(R.id.expiredTicketsList);
//
//        if (ticketsValid.size() > 0) {
//            this.validTicketsDisplay.setVisibility(View.VISIBLE);
//            this.noValidTicketsDisplay.setVisibility(View.GONE);
//            this.validTicketsDisplay.setAdapter(new TicketAdapter((AppCompatActivity) getActivity(), ticketsValid));
//        } else {
//            this.validTicketsDisplay.setVisibility(View.GONE);
//            this.noValidTicketsDisplay.setVisibility(View.VISIBLE);
//        }
//        if (ticketsValid.size() > 0) {
//            this.expiredTicketsDisplay.setVisibility(View.VISIBLE);
//            this.noExpiredTicketsDisplay.setVisibility(View.GONE);
//            this.expiredTicketsDisplay.setAdapter(new TicketAdapter((AppCompatActivity)getActivity(), ticketsExpired));
//        } else {
//            this.expiredTicketsDisplay.setVisibility(View.GONE);
//            this.noExpiredTicketsDisplay.setVisibility(View.VISIBLE);
//        }


        return inflater.inflate(R.layout.fragment_ticket_history, container, false);
    }


}
