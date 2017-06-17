package com.example.icf.tripappclient.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.activities.MainActivity;
import com.example.icf.tripappclient.service.ServiceUtils;

import io.swagger.client.model.TicketType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketPurchase extends Fragment {

    private SessionManager session;
    private TicketType type;
    private int PRIVATE_MODE = 0;
    private Context _context;
    private SharedPreferences pref;
    private String notificationToken;
    private Boolean notificationsEnabled;

    public TicketPurchase() {

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

        _context = getActivity().getApplicationContext();
        pref = _context.getSharedPreferences("notification", PRIVATE_MODE);
        notificationToken = pref.getString("token", null);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(_context);
        notificationsEnabled = sharedPref.getBoolean("notifications_on", true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.ticket_purchase_title);

        session = new SessionManager(getActivity().getApplicationContext());

        type = (TicketType) getArguments().getSerializable("ticket_type");

        View view = inflater.inflate(R.layout.fragment_ticket_purchase,
                container, false);
        Button button = (Button) view.findViewById(R.id.buyButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                io.swagger.client.model.TicketPurchase purchase = new io.swagger.client.model.TicketPurchase();
                purchase.setTypeId(type.getId());
                purchase.setUserId(session.getUser().getId());

                Spinner mySpinner = (Spinner) getView().findViewById(R.id.numberValue);
                int num = Integer.parseInt(mySpinner.getSelectedItem().toString());
                purchase.setNumberOfPassangers(num);

                Call<io.swagger.client.model.TicketPurchase> call = ServiceUtils.ticketPurchaseService.add(session.getUser().getId().toString(), notificationToken, notificationsEnabled, purchase);
                call.enqueue(new Callback<io.swagger.client.model.TicketPurchase>() {

                    @Override
                    public void onResponse(Call<io.swagger.client.model.TicketPurchase> call, Response<io.swagger.client.model.TicketPurchase> response) {
                        if (response.code() == 201) {
                            io.swagger.client.model.TicketPurchase ticket = response.body();
                            ((MainActivity)getActivity()).respondNewPurchase(true, ticket);
                        } else {
                            ((MainActivity)getActivity()).respondNewPurchase(false, null);
                        }
                    }

                    @Override
                    public void onFailure(Call<io.swagger.client.model.TicketPurchase> call, Throwable t) {
                        ((MainActivity)getActivity()).respondNewPurchase(false, null);
                    }
                });

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

       /* TextView duration = (TextView) getView().findViewById(R.id.durationValue);
        duration.setText(type.getDuration() + " hours");*/


        //View view=inflater.inflate(R.layout.fragment_ticket_purchase, container, false);
        final Spinner countSpinner = (Spinner) getView().findViewById(R.id.numberValue);
        final TextView totalText = (TextView) getView().findViewById(R.id.totalValue);

        countSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                int count = Integer.parseInt(countSpinner.getSelectedItem().toString());
                String totalString = String.format("%.2f", count * type.getPrice());
                totalText.setText(totalString);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


}
