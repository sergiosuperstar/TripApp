package com.example.icf.tripappclient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
