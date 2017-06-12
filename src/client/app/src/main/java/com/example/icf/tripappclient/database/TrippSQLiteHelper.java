package com.example.icf.tripappclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by NemanjaM on 6.6.2017.
 */

public class TrippSQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_TICKET_TYPE = "ticket_type";
    public static final String COLUMN_TT_ID = "_id";
    public static final String COLUMN_TT_NAME = "name";
    public static final String COLUMN_TT_DURATION = "duration";
    public static final String COLUMN_TT_PRICE = "price";

    public static final String TABLE_PAYMENT = "payment";
    public static final String COLUMN_P_ID = "_id";
    public static final String COLUMN_P_TICKET = "ticket";
    public static final String COLUMN_P_DATE = "date";
    public static final String COLUMN_P_PRICE = "price";
    public static final String COLUMN_P_EXPENSE = "expense";

    public static final String TABLE_TICKET = "ticket";
    public static final String COLUMN_T_ID = "_id";
    public static final String COLUMN_T_CODE = "code";
    public static final String COLUMN_T_PRICE = "price";
    public static final String COLUMN_T_START = "startDateTime";
    public static final String COLUMN_T_END = "endDateTime";
    public static final String COLUMN_T_PASSANGERS = "numberOfPassangers";
    public static final String COLUMN_T_TICKET = "ticket";

    private static final String DATABASE_NAME = "trippApp.db";
    private static final int DATABASE_VERSION = 1;


    private static final String DB_TABLE_TICKET_TYPE_CREATE = "create table "
            + TABLE_TICKET_TYPE + "("
            + COLUMN_TT_ID  + " integer primary key, "
            + COLUMN_TT_NAME + " text, "
            + COLUMN_TT_DURATION + " integer, "
            + COLUMN_TT_PRICE + " real)";

    private static final String DB_TABLE_PAYMENT_CREATE = "create table "
            + TABLE_PAYMENT + "("
            + COLUMN_P_ID  + " integer primary key, "
            + COLUMN_P_TICKET + " text, "
            + COLUMN_P_DATE + " datetime, "
            + COLUMN_P_EXPENSE + " boolean, "
            + COLUMN_P_PRICE + " real)";

    private static final String DB_TABLE_TICKET_CREATE = "create table "
            + TABLE_TICKET + "("
            + COLUMN_T_ID  + " integer primary key, "
            + COLUMN_T_CODE  + " text, "
            + COLUMN_T_PRICE + " real, "
            + COLUMN_T_START + " datetime, "
            + COLUMN_T_END + " datetime, "
            + COLUMN_T_PASSANGERS  + " integer, "
            + COLUMN_T_TICKET + " text)";

    public TrippSQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_TABLE_TICKET_TYPE_CREATE);
        db.execSQL(DB_TABLE_PAYMENT_CREATE);
        db.execSQL(DB_TABLE_TICKET_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAYMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TICKET);
        onCreate(db);
    }

}
