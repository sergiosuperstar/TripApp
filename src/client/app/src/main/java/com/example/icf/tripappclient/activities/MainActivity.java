package com.example.icf.tripappclient.activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.fragments.AccountBalance;
import com.example.icf.tripappclient.fragments.Home;
import com.example.icf.tripappclient.fragments.TicketHistory;
import com.example.icf.tripappclient.fragments.TicketInfo;
import com.example.icf.tripappclient.fragments.TicketPurchase;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Home homeFragment = new Home();
        fragmentTransaction.add(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        int id = item.getItemId();

        if (id == R.id.nav_main) {  /* MOZDA NE TREBA NEW FRAGMENT NEGO IH CUVATI KAO POLJA */
            Home homeFragment = new Home();
            fragmentTransaction.replace(R.id.fragment_container, homeFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_ticket_purchase) {
            TicketPurchase tpFragment = new TicketPurchase();
            fragmentTransaction.replace(R.id.fragment_container, tpFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_ticket_info) {
            TicketInfo tiFragment = new TicketInfo();
            fragmentTransaction.replace(R.id.fragment_container, tiFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_ticket_balance) {
            AccountBalance abFragment = new AccountBalance();
            fragmentTransaction.replace(R.id.fragment_container, abFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_camera) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        } else if (id == R.id.nav_ticket_history) {
            TicketHistory thFragment = new TicketHistory();
            fragmentTransaction.replace(R.id.fragment_container, thFragment);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_login) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buyOneHourTicket(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketPurchase tpFragment = new TicketPurchase();
        fragmentTransaction.replace(R.id.fragment_container, tpFragment);
        fragmentTransaction.commit();
    }

    public void buyOneDayTicket(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketPurchase tpFragment = new TicketPurchase();
        fragmentTransaction.replace(R.id.fragment_container, tpFragment);
        fragmentTransaction.commit();
    }

    public void buyWeekDayTicket(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketPurchase tpFragment = new TicketPurchase();
        fragmentTransaction.replace(R.id.fragment_container, tpFragment);
        fragmentTransaction.commit();
    }

    public void buyMonthDayTicket(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TicketPurchase tpFragment = new TicketPurchase();
        fragmentTransaction.replace(R.id.fragment_container, tpFragment);
        fragmentTransaction.commit();
    }
}