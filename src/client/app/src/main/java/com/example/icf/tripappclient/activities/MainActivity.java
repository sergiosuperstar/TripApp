package com.example.icf.tripappclient.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.TextView;
import android.widget.Toast;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.fragments.AccountBalance;
import com.example.icf.tripappclient.fragments.Home;
import com.example.icf.tripappclient.fragments.TicketHistory;
import com.example.icf.tripappclient.fragments.TicketInfo;
import com.example.icf.tripappclient.fragments.TicketPurchase;
import com.example.icf.tripappclient.fragments.TicketScanned;
import com.example.icf.tripappclient.service.ServiceUtils;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import io.swagger.client.model.TicketType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private SessionManager session;
    private DrawerLayout drawer;

    private ProgressDialog progress;

    private Activity that = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        session = new SessionManager(getApplicationContext());
        progress = new ProgressDialog(this);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.ic_dehaze_white_24dp);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (drawer.isDrawerVisible(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            }
        });

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header=navigationView.getHeaderView(0);
        TextView name = (TextView)header.findViewById(R.id.user_info);
        name.setText("Welcome, " + session.getUser().getUsername());

        Menu menu = navigationView.getMenu();
        MenuItem inspectorMenu = menu.findItem(R.id.inspector_menu);
        inspectorMenu.setVisible(session.getUserRole().equals("controller"));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Home homeFragment = new Home();
        fragmentTransaction.add(R.id.fragment_container, homeFragment);
        fragmentTransaction.commit();

        // DO NOT CHANGE TOPIC NAME FROM 'news'!
        // IT MAY TAKE 24HRS TO CREATE IT AGAIN!
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        Boolean isBalance = getIntent().getBooleanExtra("balance", false);

        if(isBalance){
            session.reloadUserBalance(this);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!session.isLoggedIn()){
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0)
                getSupportFragmentManager().popBackStack();
            else
                super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.overflow_items, menu);
       // menu.getItem(0).setIcon(getResources().getDrawable(R.drawable.ic_more_vert_white_24dp));

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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_main) {  /* MOZDA NE TREBA NEW FRAGMENT NEGO IH CUVATI KAO POLJA */
            Home homeFragment = new Home();
            changeFragment(homeFragment);
        /*} else if (id == R.id.nav_ticket_info) {

            progress.setMessage("Collecting data. Please wait...");
            progress.show();

            Call<io.swagger.client.model.TicketPurchase> call = ServiceUtils.ticketPurchaseService.get("my:" + session.getUser().getId());
            call.enqueue(new Callback<io.swagger.client.model.TicketPurchase>() {

                @Override
                public void onResponse(Call<io.swagger.client.model.TicketPurchase> call, Response<io.swagger.client.model.TicketPurchase> response) {
                    if (response.code() == 200) {
                        progress.dismiss();
                        //TicketInfo tiFragment = TicketInfo.newInstance(response.body());
                        //changeFragment(tiFragment);
                    } else {
                        progress.dismiss();
                        Toast.makeText(that, "No active tickets", Toast.LENGTH_LONG).show();
                        Home homeFragment = new Home();
                        changeFragment(homeFragment);
                    }
                }

                @Override
                public void onFailure(Call<io.swagger.client.model.TicketPurchase> call, Throwable t) {
                    progress.dismiss();
                    Toast.makeText(that, "Could not connect to server.", Toast.LENGTH_LONG).show();
                    Home homeFragment = new Home();
                    changeFragment(homeFragment);
                }
            });

        */
        } else if (id == R.id.nav_ticket_balance) {
            AccountBalance abFragment = new AccountBalance();
            changeFragment(abFragment);
        } else if (id == R.id.nav_camera) {
            Intent intent = new Intent(this, ContinuousCaptureActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_scanned) {
            TicketScanned tsFragment = new TicketScanned();
            changeFragment(tsFragment);
        } else if (id == R.id.nav_ticket_history) {
            TicketHistory thFragment = new TicketHistory();
            changeFragment(thFragment);
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_logout) {
            session.logOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_add){
            View view = findViewById(R.id.addMoney);
            scanVoucher(view);
           //mockCode();
        }else if (id == R.id.nav_map) {
            Intent intent = new Intent(this, MapActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void buyOneHourTicket(View view) {
        TicketType type = new TicketType();
        type.setId(1);
        type.setDuration(1);
        type.setPrice(1.2);
        type.setName("Hourly ticket");
        TicketPurchase tpFragment = TicketPurchase.newInstance(type);
        changeFragment(tpFragment);
    }

    public void buyOneDayTicket(View view) {
        TicketType type = new TicketType();
        type.setId(2);
        type.setDuration(24);
        type.setPrice(5d);
        type.setName("Daily ticket");
        TicketPurchase tpFragment = TicketPurchase.newInstance(type);
        changeFragment(tpFragment);
    }

    public void buyWeekDayTicket(View view) {
        TicketType type = new TicketType();
        type.setId(3);
        type.setDuration(24*7);
        type.setPrice(16d);
        type.setName("Weekly ticket");
        TicketPurchase tpFragment = TicketPurchase.newInstance(type);
        changeFragment(tpFragment);
    }

    public void buyMonthDayTicket(View view) {
        TicketType type = new TicketType();
        type.setId(4);
        type.setDuration(24*30);
        type.setPrice(40d);
        type.setName("Monthly ticket");
        TicketPurchase tpFragment = TicketPurchase.newInstance(type);
        changeFragment(tpFragment);
    }

    public void changeFragment(Fragment destinationFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, destinationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void respondNewPurchase(boolean successful, io.swagger.client.model.TicketPurchase ticket) {
        if (successful) {
            Toast.makeText(this, "Purchase successful", Toast.LENGTH_LONG).show();
            session.setBalance(ticket.getUser().getBalance());
            //TicketInfo tiFragment = TicketInfo.newInstance(ticket);
            //changeFragment(tiFragment);
        } else {
            Toast.makeText(this, "Purchase failed or not enough credits", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {

            }
        } else {
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void scanVoucher(View view){
        Intent intent = new Intent(this, VoucherScannerActivity.class);
        startActivity(intent);

    }

    /*protected void mockCode() {
        PurchaseCode code = new PurchaseCode();
        code.setCode(UUID.fromString("33994bf3-0489-4897-9b87-853c76124ee1"));
        code.setUser(session.getUser());
        Call<Boolean> call = ServiceUtils.purchaseCodeService.put(code);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.code() == 200) {
                    Boolean resp = response.body();
                    session.reloadUserBalance((MainActivity) that);
                } else {
                }
            }
            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
            }
        });
    }
*/



    public SessionManager getSession() {
        return session;
    }

}
