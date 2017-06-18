package com.example.icf.tripappclient.fragments;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.activities.MainActivity;
import com.example.icf.tripappclient.adapters.TicketAdapter;
import com.j256.ormlite.dao.Dao;

import io.swagger.client.model.TicketPurchase;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import io.swagger.client.model.TicketPurchaseLocal;
import io.swagger.client.model.TicketType;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketHistory extends Fragment {

    private List<TicketPurchaseLocal> ticketsValid;
    private List<TicketPurchaseLocal> ticketsExpired;
    private ListView validTicketsDisplay;
    private ListView expiredTicketsDisplay;
    private TextView noValidTicketsDisplay;
    private TextView noExpiredTicketsDisplay;

    private MainActivity activity;

    public TicketHistory() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        activity = (MainActivity) getActivity();
        activity.getSupportActionBar().setTitle(R.string.ticket_history_title);

        fillData();

        return inflater.inflate(R.layout.fragment_ticket_history, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        this.noValidTicketsDisplay = (TextView) activity.findViewById(R.id.emptyActiveLabel);
        this.noExpiredTicketsDisplay = (TextView) activity.findViewById(R.id.emptyHistoryLabel);
        this.validTicketsDisplay = (ListView) activity.findViewById(R.id.validTicketsList);
        this.expiredTicketsDisplay = (ListView) activity.findViewById(R.id.expiredTicketsList);

        AdapterView.OnItemClickListener da = null;

        if (ticketsValid.size() > 0) {
            this.validTicketsDisplay.setVisibility(View.VISIBLE);
            this.noValidTicketsDisplay.setVisibility(View.GONE);
            this.validTicketsDisplay.setAdapter(new TicketAdapter(activity, ticketsValid));
            this.validTicketsDisplay.setOnItemClickListener(new TicketItemListener(ticketsValid));
        } else {
            this.validTicketsDisplay.setVisibility(View.GONE);
            this.noValidTicketsDisplay.setVisibility(View.VISIBLE);
        }
        if (ticketsExpired.size() > 0) {
            this.expiredTicketsDisplay.setVisibility(View.VISIBLE);
            this.noExpiredTicketsDisplay.setVisibility(View.GONE);
            this.expiredTicketsDisplay.setAdapter(new TicketAdapter(activity, ticketsExpired));
            this.expiredTicketsDisplay.setOnItemClickListener(new TicketItemListener(ticketsExpired));
        } else {
            this.expiredTicketsDisplay.setVisibility(View.GONE);
            this.noExpiredTicketsDisplay.setVisibility(View.VISIBLE);
        }
    }

    private void fillData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(((AppCompatActivity) getActivity()).getApplicationContext());
        int pref = 0 - Integer.parseInt(preferences.getString("tickets_history", "30"));

        Calendar current = new GregorianCalendar();
        current.add(Calendar.DATE, pref);
        Date margin = current.getTime();

        ticketsValid = new ArrayList<>();
        ticketsExpired = new ArrayList<>();

        List<TicketPurchaseLocal> tickets = new ArrayList<>();
        final Dao<TicketPurchaseLocal, Integer> ticketDAO = ((MainActivity)getActivity())
                .getSession().getDatabaseState().getDatabaseHelper().getTicketDAO();

        try {
            tickets = ticketDAO.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Date currentTime = new Date();
        for (TicketPurchaseLocal ticket: tickets) {
            if (ticket.getEndDateTime().after(currentTime)) {
                ticketsValid.add(ticket);
            } else {
                ticketsExpired.add(ticket);
            }
        }

        if (pref < 998) {
            ticketsExpired = filterByDate(ticketsExpired, margin);
        }
        Collections.sort(ticketsValid, new CustomComparatorInc());
        Collections.sort(ticketsExpired, new CustomComparatorDec());
    }

    private class CustomComparatorInc implements Comparator<TicketPurchaseLocal> {
        @Override
        public int compare(TicketPurchaseLocal o1, TicketPurchaseLocal o2) {
            return o1.getEndDateTime().compareTo(o2.getEndDateTime());
        }
    }

    private class CustomComparatorDec implements Comparator<TicketPurchaseLocal> {
        @Override
        public int compare(TicketPurchaseLocal o1, TicketPurchaseLocal o2) {
            return o2.getEndDateTime().compareTo(o1.getEndDateTime());
        }
    }

    private List<TicketPurchaseLocal> filterByDate(List<TicketPurchaseLocal> list, Date date) {
        List<TicketPurchaseLocal> result = new ArrayList<>();
        for (TicketPurchaseLocal element: list) {
            if (element.getEndDateTime().after(date)) {
                result.add(element);
            }
        }
        return result;
    }

    private class TicketItemListener implements AdapterView.OnItemClickListener {

        private List<TicketPurchaseLocal> list;

        public TicketItemListener(List<TicketPurchaseLocal> list) {
            this.list = list;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            TicketPurchaseLocal item = list.get(i);
            TicketInfo tiFragment = TicketInfo.newInstance(item);
            activity.changeFragment(tiFragment);
        }
    }
}
