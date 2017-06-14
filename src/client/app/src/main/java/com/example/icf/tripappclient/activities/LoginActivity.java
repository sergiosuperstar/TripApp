package com.example.icf.tripappclient.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.icf.tripappclient.R;
import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.service.ServiceUtils;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.PurchaseCode;
import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private SessionManager session;
    private ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(getApplicationContext());

        progress = new ProgressDialog(this);
        progress.setTitle(R.string.loading);
        progress.setCancelable(false);
    }

    public void loginClicked(View view){
        progress.setMessage("Collecting data. Please wait...");
        progress.show();
        EditText mUsername = (EditText)findViewById(R.id.usernameInput);
        EditText mPassword = (EditText)findViewById(R.id.passwordInput);

        String username = mUsername.getText().toString();
        String password = mPassword.getText().toString();
        session.login(this, username, password);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void respond(boolean successful) {
        if (successful) {

            prepareDatabase();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        } else {
            Toast.makeText(this, R.string.login_failed, Toast.LENGTH_LONG).show();
        }
        progress.dismiss();
    }

    private void prepareDatabase() {
        session.getHelper().emptyDatabase();
        fillTicketTypes();
        fillUserPayments();
        fillUserTickets();
    }

    private void fillTicketTypes() {
        Call<List<TicketType>> call = ServiceUtils.tickeTypeService.get();
        call.enqueue(new Callback<List<TicketType>>() {
            @Override
            public void onResponse(Call<List<TicketType>> call, Response<List<TicketType>> response) {
                if (response.code() == 200) {
                    List<TicketType> ticketTypes = response.body();
                    for (TicketType ticket: ticketTypes) {
                        try {
                            session.getHelper().getTicketTypeDAO().create(ticket);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    closeApplication();
                }
            }
            @Override
            public void onFailure(Call<List<TicketType>> call, Throwable t) {
                closeApplication();
            }
        });
    }

    private void fillUserPayments() {
        Call<List<PurchaseCode>> callPlus = ServiceUtils.purchaseCodeService.get(session.getUser().getId().toString());
        callPlus.enqueue(new Callback<List<PurchaseCode>>() {
            @Override
            public void onResponse(Call<List<PurchaseCode>> call, Response<List<PurchaseCode>> response) {
                if (response.code() == 200) {
                    List<PurchaseCode> purchaseCodes = response.body();
                    for (PurchaseCode purchaseCode: purchaseCodes) {
                        Double price = purchaseCode.getValue();
                        Date endDateTime = purchaseCode.getUsageDateTime();
                        String ticketName = "";
                        boolean isExpense = false;
                        AdapterPayment payment = new AdapterPayment(price, endDateTime, ticketName, isExpense);
                        try {
                            session.getHelper().getPaymentDAO().create(payment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    closeApplication();
                }
            }
            @Override
            public void onFailure(Call<List<PurchaseCode>> call, Throwable t) {
                closeApplication();
            }
        });
    }

    private void fillUserTickets() {
        Call<List<TicketPurchase>> call = ServiceUtils.userTicketsService.get("all"+session.getUser().getId());
        call.enqueue(new Callback<List<TicketPurchase>>() {
            @Override
            public void onResponse(Call<List<TicketPurchase>> call, Response<List<TicketPurchase>> response) {
                if (response.code() == 200) {
                    List<TicketPurchase> tickets = response.body();
                    for (TicketPurchase ticket: tickets) {
                        Double price = ticket.getPrice();
                        Date endDateTime = ticket.getEndDateTime();
                        String ticketName = ticket.getType().getName();
                        boolean isExpense = true;
                        AdapterPayment payment = new AdapterPayment(price, endDateTime, ticketName, isExpense);
                        try {
                            session.getHelper().getTicketDAO().create(ticket);
                            session.getHelper().getPaymentDAO().create(payment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    closeApplication();
                }
            }
            @Override
            public void onFailure(Call<List<TicketPurchase>> call, Throwable t) {
                closeApplication();
            }
        });
    }

    private void closeApplication() {
        // TODO zatvoriti aplikaciju jer ne moze da radi bez ovih podataka
    }

}
