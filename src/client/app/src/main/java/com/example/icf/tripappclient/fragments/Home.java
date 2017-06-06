package com.example.icf.tripappclient.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.adapters.PurchaseAdapter;

import java.util.List;

import io.swagger.client.model.TicketType;


public class Home extends Fragment {

    private List<TicketType> ticketTypes;
    private GridView ticketsDisplay;

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.home_title);

//        this.ticketsDisplay = (GridView) ((AppCompatActivity)getActivity()).findViewById(R.id.tickets_grid);
//        this.ticketsDisplay.setAdapter(new PurchaseAdapter((AppCompatActivity)getActivity(), ticketTypes));

        return inflater.inflate(R.layout.fragment_home, container, false);
    }


}
