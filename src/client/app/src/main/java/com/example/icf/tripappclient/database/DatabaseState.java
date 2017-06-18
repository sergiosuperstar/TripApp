package com.example.icf.tripappclient.database;

import android.content.Context;

import com.example.icf.tripappclient.SessionManager;
import com.example.icf.tripappclient.service.ServiceUtils;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.PurchaseCode;
import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketPurchaseLocal;
import io.swagger.client.model.TicketScannedModel;
import io.swagger.client.model.TicketType;
import io.swagger.client.model.TicketValidation;
import io.swagger.client.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by NemanjaM on 18.6.2017.
 */

public class DatabaseState {

    private SessionManager session;
    private DatabaseHelper databaseHelper;
    private Context context;

    public DatabaseState(SessionManager session, Context context) {
        this.session = session;
        this.context = context;
        this.databaseHelper = OpenHelperManager.getHelper(context, DatabaseHelper.class);
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
                            databaseHelper.getPaymentDAO().create(payment);
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

    public void refillPayments() {
        Call<List<PurchaseCode>> callPlus = ServiceUtils.purchaseCodeService.get(session.getUser().getId().toString());
        callPlus.enqueue(new Callback<List<PurchaseCode>>() {
            @Override
            public void onResponse(Call<List<PurchaseCode>> call, Response<List<PurchaseCode>> response) {
                if (response.code() == 200) {
                    List<PurchaseCode> purchaseCodes = response.body();
                    refillUserPaymentsFromTickets(purchaseCodes);
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

    private void refillUserPaymentsFromTickets(List<PurchaseCode> paymentsFromCodes) {
        final List<PurchaseCode> balancePaymentCodes = paymentsFromCodes;
        Call<List<TicketPurchase>> call = ServiceUtils.userTicketsService.get("all:"+session.getUser().getId());
        call.enqueue(new Callback<List<TicketPurchase>>() {
            @Override
            public void onResponse(Call<List<TicketPurchase>> call, Response<List<TicketPurchase>> response) {
                if (response.code() == 200) {
                    databaseHelper.emptyPayments();
                    List<TicketPurchase> tickets = response.body();
                    for (TicketPurchase ticket: tickets) {
                        Double price = ticket.getPrice() * ticket.getNumberOfPassangers();
                        Date endDateTime = ticket.getEndDateTime();
                        String ticketName = ticket.getType().getName();
                        boolean isExpense = true;
                        AdapterPayment payment = new AdapterPayment(price, ticket.getStartDateTime(),
                                ticketName, isExpense);
                        try {
                            databaseHelper.getPaymentDAO().create(payment);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                    for (PurchaseCode purchaseCode: balancePaymentCodes) {
                        Double price = purchaseCode.getValue();
                        Date endDateTime = purchaseCode.getUsageDateTime();
                        String ticketName = "";
                        boolean isExpense = false;
                        AdapterPayment payment = new AdapterPayment(price, endDateTime, ticketName, isExpense);
                        try {
                            databaseHelper.getPaymentDAO().create(payment);
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

    private void fillScannedTickets() {
        Call<List<TicketValidation>> call = ServiceUtils.ticketValidationService.getValidations(session.getUser().getId().toString());
        call.enqueue(new Callback<List<TicketValidation>>() {
            @Override
            public void onResponse(Call<List<TicketValidation>> call, Response<List<TicketValidation>> response) {
                if (response.code() == 200) {
                    List<TicketValidation> validations = response.body();
                    for (TicketValidation validation: validations) {

                        String ticketMix = "";
                        String userMix = "";

                        if (validation.getTicket() != null) {
                            TicketType ticketType = validation.getTicket().getType();
                            String ticketTypeName = "";
                            if (ticketType != null){
                                ticketTypeName = ticketType.getName();
                            }
                            int ticketId = validation.getTicket().getId();

                            User validationUser = validation.getTicket().getUser();

                            String userSurname = "";
                            String userLetter = "";
                            if (validationUser != null) {
                                userSurname = validation.getTicket().getUser().getLastName();
                                userLetter = validation.getTicket().getUser().getFirstName().substring(0, 1);
                            }

                            ticketMix = ticketTypeName + " (" + ticketId + ")";
                            userMix = userLetter + ". " + userSurname;
                        }

                        TicketScannedModel scannedTicket = new TicketScannedModel(
                                validation.getValidationDateTime(), validation.getIsValid(), ticketMix, userMix);
                        try {
                            databaseHelper.getScannedDAO().create(scannedTicket);
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

    public void refillScannedTickets() {
        Call<List<TicketValidation>> call = ServiceUtils.ticketValidationService.getValidations(session.getUser().getId().toString());
        call.enqueue(new Callback<List<TicketValidation>>() {
            @Override
            public void onResponse(Call<List<TicketValidation>> call, Response<List<TicketValidation>> response) {
                if (response.code() == 200) {
                    databaseHelper.emptyScannedTickets();
                    List<TicketValidation> validations = response.body();
                    for (TicketValidation validation: validations) {

                        String ticketMix = "";
                        String userMix = "";

                        if (validation.getTicket() != null) {
                            TicketType ticketType = validation.getTicket().getType();
                            String ticketTypeName = "";
                            if (ticketType != null){
                                ticketTypeName = ticketType.getName();
                            }
                            int ticketId = validation.getTicket().getId();

                            User validationUser = validation.getTicket().getUser();

                            String userSurname = "";
                            String userLetter = "";
                            if (validationUser != null) {
                                userSurname = validation.getTicket().getUser().getLastName();
                                userLetter = validation.getTicket().getUser().getFirstName().substring(0, 1);
                            }

                            ticketMix = ticketTypeName + " (" + ticketId + ")";
                            userMix = userLetter + ". " + userSurname;
                        }

                        TicketScannedModel scannedTicket = new TicketScannedModel(
                                validation.getValidationDateTime(), validation.getIsValid(), ticketMix, userMix);
                        try {
                            databaseHelper.getScannedDAO().create(scannedTicket);
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

    private void fillUserTickets() {
        Call<List<TicketPurchase>> call = ServiceUtils.userTicketsService.get("all:"+session.getUser().getId());
        call.enqueue(new Callback<List<TicketPurchase>>() {
            @Override
            public void onResponse(Call<List<TicketPurchase>> call, Response<List<TicketPurchase>> response) {
                if (response.code() == 200) {
                    List<TicketPurchase> tickets = response.body();
                    for (TicketPurchase ticket: tickets) {
                        Double price = ticket.getPrice() * ticket.getNumberOfPassangers();
                        Date endDateTime = ticket.getEndDateTime();
                        String ticketName = ticket.getType().getName();
                        boolean isExpense = true;
                        AdapterPayment payment = new AdapterPayment(price, ticket.getStartDateTime(),
                                ticketName, isExpense);
                        TicketPurchaseLocal ticketLocal = new TicketPurchaseLocal(ticket.getId(),
                                ticket.getCode().toString(), price, ticket.getStartDateTime(),
                                endDateTime, ticket.getNumberOfPassangers(), ticketName,
                                ticket.getUserId());
                        try {
                            databaseHelper.getTicketDAO().create(ticketLocal);
                            databaseHelper.getPaymentDAO().create(payment);
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

    public void refillTickets() {
        Call<List<TicketPurchase>> call = ServiceUtils.userTicketsService.get("all:"+session.getUser().getId());
        call.enqueue(new Callback<List<TicketPurchase>>() {
            @Override
            public void onResponse(Call<List<TicketPurchase>> call, Response<List<TicketPurchase>> response) {
                if (response.code() == 200) {
                    databaseHelper.emptyTickets();
                    List<TicketPurchase> tickets = response.body();
                    for (TicketPurchase ticket: tickets) {
                        Double price = ticket.getPrice() * ticket.getNumberOfPassangers();
                        Date endDateTime = ticket.getEndDateTime();
                        String ticketName = ticket.getType().getName();
                        boolean isExpense = true;
                        AdapterPayment payment = new AdapterPayment(price, ticket.getStartDateTime(),
                                ticketName, isExpense);
                        TicketPurchaseLocal ticketLocal = new TicketPurchaseLocal(ticket.getId(),
                                ticket.getCode().toString(), price, ticket.getStartDateTime(),
                                endDateTime, ticket.getNumberOfPassangers(), ticketName,
                                ticket.getUserId());
                        try {
                            databaseHelper.getTicketDAO().create(ticketLocal);
                            databaseHelper.getPaymentDAO().create(payment);
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


    public void emptyPayments() {
        databaseHelper.emptyPayments();
    }

    public void emptyTickets() {
        databaseHelper.emptyTickets();
    }

    public void emptyScannedTickets() {
        databaseHelper.emptyScannedTickets();
    }

    public void emptyDatabase() {
        databaseHelper.emptyDatabase();
    }

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    private void closeApplication() {
        // TODO zatvoriti aplikaciju jer ne moze da radi bez ovih podataka
    }
}
