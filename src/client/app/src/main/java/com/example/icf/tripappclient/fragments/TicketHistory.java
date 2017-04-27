package com.example.icf.tripappclient.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.icf.tripappclient.R;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketHistory extends Fragment {

    public TicketHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ticket_history, container, false);
    }


}
