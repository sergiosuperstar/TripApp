package com.example.icf.tripappclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    private String[] mPlanetTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /** Called when the user touches the button */
    public void buyOneHourTicket(View view) {
        Intent intent = new Intent(getApplicationContext(), TicketPurchase.class);
        startActivity(intent);
    }

    public void buyOneDayTicket(View view) {
        Intent intent = new Intent(getApplicationContext(), TicketPurchase.class);
        startActivity(intent);
    }

    public void buyWeekDayTicket(View view) {
        Intent intent = new Intent(getApplicationContext(), TicketPurchase.class);
        startActivity(intent);
    }

    public void buyMonthDayTicket(View view) {
        Intent intent = new Intent(getApplicationContext(), TicketPurchase.class);
        startActivity(intent);
    }
}
