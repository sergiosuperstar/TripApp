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
import com.example.icf.tripappclient.adapters.TicketAdapter;
import com.example.icf.tripappclient.database.DBContentProvider;
import com.example.icf.tripappclient.database.TrippSQLiteHelper;
import io.swagger.client.model.TicketPurchase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.swagger.client.model.TicketType;

/**
 * Created by Vuletic on 28.4.2017.
 */

public class TicketHistory extends Fragment {

    private List<TicketPurchase> ticketsValid;
    private List<TicketPurchase> ticketsExpired;
    private ListView validTicketsDisplay;
    private ListView expiredTicketsDisplay;
    private TextView noValidTicketsDisplay;
    private TextView noExpiredTicketsDisplay;

    private ContentResolver resolver;

    public TicketHistory() {
        // Required empty public constructor
        resolver = getContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.ticket_history_title);

        fillData();

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

    private void fillData() {

        ticketsValid = new ArrayList<TicketPurchase>();
        ticketsExpired = new ArrayList<TicketPurchase>();

        String[] projection = new String[]{ TrippSQLiteHelper.COLUMN_T_ID,
                TrippSQLiteHelper.COLUMN_T_CODE, TrippSQLiteHelper.COLUMN_T_PRICE,
                TrippSQLiteHelper.COLUMN_T_START, TrippSQLiteHelper.COLUMN_T_END,
                TrippSQLiteHelper.COLUMN_T_PASSANGERS, TrippSQLiteHelper.COLUMN_T_TICKET};
        String whereClauseExp = TrippSQLiteHelper.COLUMN_T_END + " < ?";
        String whereClauseVal = TrippSQLiteHelper.COLUMN_T_END + " > ?";
        String[] whereArgs = new String[] { new Date().toString() };
        String orderBy = TrippSQLiteHelper.COLUMN_T_START;

        Cursor cursor_valid = resolver.query(DBContentProvider.CONTENT_URL_T, null, whereClauseVal, whereArgs, orderBy);
        Cursor cursor_expired = resolver.query(DBContentProvider.CONTENT_URL_T, null, whereClauseExp, whereArgs, orderBy);

        if(cursor_valid.moveToFirst()) {
            do {
                int id = cursor_valid.getInt(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_ID));
                String code = cursor_valid.getString(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_CODE));
                double price = cursor_valid.getDouble(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_PRICE));
                String start = cursor_valid.getString(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_START));
                String end = cursor_valid.getString(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_END));
                int passangers = cursor_valid.getInt(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_PASSANGERS));
                String type = cursor_valid.getString(cursor_valid.getColumnIndex(TrippSQLiteHelper.COLUMN_T_TICKET));

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm", Locale.getDefault());
                java.util.Date dstart = null, dend = null;
                try {
                    dstart = sdf.parse(start);
                    dend = sdf.parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TicketPurchase ticket = new TicketPurchase(code);
                TicketType tt = new TicketType();
                tt.setName(type);
                ticket.setId(id);
                ticket.setPrice(price);
                ticket.setStartDateTime(dstart);
                ticket.setEndDateTime(dend);
                ticket.setNumberOfPassangers(passangers);
                ticket.setType(tt);

                ticketsValid.add(ticket);
            } while (cursor_valid.moveToNext());
        }
        cursor_valid.close();

        if(cursor_expired.moveToFirst()){
            do {
                int id = cursor_expired.getInt(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_ID));
                String code = cursor_expired.getString(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_CODE));
                double price = cursor_expired.getDouble(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_PRICE));
                String start = cursor_expired.getString(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_START));
                String end = cursor_expired.getString(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_END));
                int passangers = cursor_expired.getInt(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_PASSANGERS));
                String type = cursor_expired.getString(cursor_expired.getColumnIndex(TrippSQLiteHelper.COLUMN_T_TICKET));

                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/YYYY HH:mm", Locale.getDefault());
                java.util.Date dstart = null, dend = null;
                try {
                    dstart = sdf.parse(start);
                    dend = sdf.parse(end);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                TicketPurchase ticket = new TicketPurchase(code);
                TicketType tt = new TicketType();
                tt.setName(type);
                ticket.setId(id);
                ticket.setPrice(price);
                ticket.setStartDateTime(dstart);
                ticket.setEndDateTime(dend);
                ticket.setNumberOfPassangers(passangers);
                ticket.setType(tt);

                ticketsExpired.add(ticket);
            } while (cursor_expired.moveToNext());
            cursor_expired.close();
        }
    }
}
