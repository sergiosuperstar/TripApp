package com.example.icf.tripappclient.fragments;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
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
import com.example.icf.tripappclient.database.DBContentProvider;
import com.example.icf.tripappclient.database.TrippSQLiteHelper;

import java.util.ArrayList;
import java.util.List;

import io.swagger.client.model.TicketType;

public class Home extends Fragment {

    private List<TicketType> ticketTypes;
    private GridView ticketsDisplay;

    private ContentResolver resolver;

    public Home() {
        // Required empty public constructor
        resolver = getContext().getContentResolver();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.home_title);

        fillData();

//        this.ticketsDisplay = (GridView) ((AppCompatActivity)getActivity()).findViewById(R.id.tickets_grid);
//        this.ticketsDisplay.setAdapter(new PurchaseAdapter((AppCompatActivity)getActivity(), ticketTypes));

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private void fillData() {

        ticketTypes = new ArrayList<TicketType>();

        //String whereClause = TrippSQLiteHelper.COLUMN_T_END + " < ?";
        //String[] whereArgs = new String[] { new Date().toString() };
        String orderBy = TrippSQLiteHelper.COLUMN_TT_DURATION;

        Cursor cursor = resolver.query(DBContentProvider.CONTENT_URL_TT, null, null, null, orderBy);

        if(cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_TT_ID));
                int duration = cursor.getInt(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_TT_DURATION));
                String name = cursor.getString(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_TT_NAME));
                double price = cursor.getDouble(cursor.getColumnIndex(TrippSQLiteHelper.COLUMN_TT_PRICE));

                TicketType ticketType = new TicketType();

                ticketType.setId(id);
                ticketType.setName(name);
                ticketType.setDuration(duration);
                ticketType.setPrice(price);

                ticketTypes.add(ticketType);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
}
