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

import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.PurchaseCode;
import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketPurchaseLocal;
import io.swagger.client.model.TicketScannedModel;
import io.swagger.client.model.TicketValidation;
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
        fillUserPayments();
        fillUserTickets();
        if (session.getUserRole().equals("controller")) {
            fillScannedTickets();
        }
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
        Call<List<TicketValidation>> call = ServiceUtils.ticketValidationService.getValidations(session.getUser().getId());
        call.enqueue(new Callback<List<TicketValidation>>() {
            @Override
            public void onResponse(Call<List<TicketValidation>> call, Response<List<TicketValidation>> response) {
                if (response.code() == 200) {
                    List<TicketValidation> validations = response.body();
                    for (TicketValidation validation: validations) {
                        String ticketType = validation.getTicket().getType().getName();
                        int ticketId = validation.getTicket().getId();
                        String userSurname = validation.getTicket().getUser().getLastName();
                        String userLetter = validation.getTicket().getUser().getFirstName().substring(0,1);

                        String ticketMix = ticketType + " (" + ticketId + ")";
                        String userMix = userLetter + ". " + userSurname;

                        TicketScannedModel scannedTicket = new TicketScannedModel(
                                validation.getValidationDateTime(), validation.getIsValid(), ticketMix, userMix);
                        try {
                            session.getHelper().getScannedDAO().create(scannedTicket);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    closeApplication();
                }
            }
            @Override
            public void onFailure(Call<List<TicketValidation>> call, Throwable t) {
                closeApplication();
            }
        });
    }

    private void fillScannedTickets() {
        Call<List<TicketPurchase>> call = ServiceUtils.userTicketsService.get("all:"+session.getUser().getId());
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
                        TicketPurchaseLocal ticketLocal = new TicketPurchaseLocal(ticket.getId(),
                                ticket.getCode().toString(), price, ticket.getStartDateTime(),
                                endDateTime, ticket.getNumberOfPassangers(), ticketName,
                                ticket.getUserId());
                        try {
                            session.getHelper().getTicketDAO().create(ticketLocal);
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
