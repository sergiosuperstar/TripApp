package com.example.icf.tripappclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.icf.tripappclient.R;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import io.swagger.client.model.AdapterPayment;
import io.swagger.client.model.TicketPurchaseLocal;
import io.swagger.client.model.TicketType;

/**
 * Created by NemanjaM on 14.6.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "trippApp.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<TicketType, Integer> ticketTypeDAO = null;
    private Dao<AdapterPayment, Integer> paymentDAO = null;
    private Dao<TicketPurchaseLocal, Integer> ticketDAO = null;

    private RuntimeExceptionDao<TicketType, Integer> ticketTypeRuntimeDAO = null;
    private RuntimeExceptionDao<AdapterPayment, Integer> paymentRuntimeDAO = null;
    private RuntimeExceptionDao<TicketPurchaseLocal, Integer> ticketRuntimeDAO = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, TicketType.class);
            TableUtils.createTable(connectionSource, AdapterPayment.class);
            TableUtils.createTable(connectionSource, TicketPurchaseLocal.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, TicketType.class, true);
            TableUtils.dropTable(connectionSource, AdapterPayment.class, true);
            TableUtils.dropTable(connectionSource, TicketPurchaseLocal.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void emptyDatabase() {
        try {
            TableUtils.clearTable(getConnectionSource(), TicketType.class);
            TableUtils.clearTable(getConnectionSource(), AdapterPayment.class);
            TableUtils.clearTable(getConnectionSource(), TicketPurchaseLocal.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
        ticketDAO = null;
        ticketTypeDAO = null;
        paymentDAO = null;
        ticketRuntimeDAO = null;
        ticketTypeRuntimeDAO = null;
        paymentRuntimeDAO = null;
    }

    public void setTicketTypeDAO(Dao<TicketType, Integer> ticketTypeDAO) {
        this.ticketTypeDAO = ticketTypeDAO;
    }

    public void setPaymentDAO(Dao<AdapterPayment, Integer> paymentDAO) {
        this.paymentDAO = paymentDAO;
    }

    public void setTicketDAO(Dao<TicketPurchaseLocal, Integer> ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void setTicketTypeRuntimeDAO(RuntimeExceptionDao<TicketType, Integer> ticketTypeRuntimeDAO) {
        this.ticketTypeRuntimeDAO = ticketTypeRuntimeDAO;
    }

    public void setPaymentRuntimeDAO(RuntimeExceptionDao<AdapterPayment, Integer> paymentRuntimeDAO) {
        this.paymentRuntimeDAO = paymentRuntimeDAO;
    }

    public void setTicketRuntimeDAO(RuntimeExceptionDao<TicketPurchaseLocal, Integer> ticketRuntimeDAO) {
        this.ticketRuntimeDAO = ticketRuntimeDAO;
    }

    public Dao<TicketType, Integer> getTicketTypeDAO() {
        if (ticketTypeDAO == null) {
            try {
                ticketTypeDAO = getDao(TicketType.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ticketTypeDAO;
    }

    public Dao<AdapterPayment, Integer> getPaymentDAO() {
        if (paymentDAO == null) {
            try {
                paymentDAO = getDao(AdapterPayment.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return paymentDAO;
    }

    public Dao<TicketPurchaseLocal, Integer> getTicketDAO() {
        if (ticketDAO == null) {
            try {
                ticketDAO = getDao(TicketPurchaseLocal.class);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ticketDAO;
    }

    public RuntimeExceptionDao<TicketType, Integer> getTicketTypeRuntimeDAO() {
        if (ticketDAO == null) {
            ticketDAO = getRuntimeExceptionDao(TicketType.class);
        }
        return ticketTypeRuntimeDAO;
    }

    public RuntimeExceptionDao<AdapterPayment, Integer> getPaymentRuntimeDAO() {
        if (paymentRuntimeDAO == null) {
            paymentRuntimeDAO = getRuntimeExceptionDao(AdapterPayment.class);
        }
        return paymentRuntimeDAO;
    }

    public RuntimeExceptionDao<TicketPurchaseLocal, Integer> getTicketRuntimeDAO() {
        if (ticketRuntimeDAO == null) {
            ticketRuntimeDAO = getRuntimeExceptionDao(TicketPurchaseLocal.class);
        }
        return ticketRuntimeDAO;
    }
}
