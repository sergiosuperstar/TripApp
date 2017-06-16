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
import com.example.icf.tripappclient.activities.MainActivity;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.swagger.client.model.*;

public class Home extends Fragment {

    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(R.string.home_title);

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    private class CustomComparator implements Comparator<TicketType> {
        @Override
        public int compare(TicketType o1, TicketType o2) {
            return o1.getDuration().compareTo(o2.getDuration());
        }
    }
}
