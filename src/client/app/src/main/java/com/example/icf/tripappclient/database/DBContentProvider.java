package com.example.icf.tripappclient.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;


/**
 * Created by NemanjaM on 6.6.2017.
 */

public class DBContentProvider extends ContentProvider {

    private SQLiteDatabase sqlDB;

    public static final String PROVIDER_NAME = "com.example.icf.tripappclient.database";
    public static final String AUTHORITY = "com.example.icf.tripappclient";

    public static final Uri CONTENT_URL_TT = Uri.parse("content://" + PROVIDER_NAME + "/" + TrippSQLiteHelper.TABLE_TICKET_TYPE);
    public static final Uri CONTENT_URL_T = Uri.parse("content://" + PROVIDER_NAME + "/" + TrippSQLiteHelper.TABLE_PAYMENT);
    public static final Uri CONTENT_URL_P = Uri.parse("content://" + PROVIDER_NAME + "/" + TrippSQLiteHelper.TABLE_TICKET);

    public static final int URI_CODE_TT = 1;
    public static final int URI_CODE_P = 2;
    public static final int URI_CODE_T = 4;

    private static HashMap<String, String> values;

    public static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(PROVIDER_NAME, TrippSQLiteHelper.TABLE_TICKET_TYPE, URI_CODE_TT);
        uriMatcher.addURI(PROVIDER_NAME, TrippSQLiteHelper.TABLE_PAYMENT, URI_CODE_P);
        uriMatcher.addURI(PROVIDER_NAME, TrippSQLiteHelper.TABLE_TICKET, URI_CODE_T);
    }

    @Override
    public boolean onCreate() {
        TrippSQLiteHelper database = new TrippSQLiteHelper(getContext());
        sqlDB = database.getWritableDatabase();
        if (sqlDB != null) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case URI_CODE_TT:
                queryBuilder.setTables(TrippSQLiteHelper.TABLE_TICKET_TYPE);
                break;
            case URI_CODE_P:
                queryBuilder.setTables(TrippSQLiteHelper.TABLE_PAYMENT);
                break;
            case URI_CODE_T:
                queryBuilder.setTables(TrippSQLiteHelper.TABLE_TICKET);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(sqlDB, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Uri retVal = null;
        long id = 0;
        String tablePath;
        int uriType = uriMatcher.match(uri);

        switch (uriType) {
            case URI_CODE_TT:
                id = sqlDB.insert(TrippSQLiteHelper.TABLE_TICKET_TYPE, null, values);
                tablePath = TrippSQLiteHelper.TABLE_TICKET_TYPE;
                break;
            case URI_CODE_P:
                id = sqlDB.insert(TrippSQLiteHelper.TABLE_PAYMENT, null, values);
                tablePath = TrippSQLiteHelper.TABLE_PAYMENT;
                break;
            case URI_CODE_T:
                id = sqlDB.insert(TrippSQLiteHelper.TABLE_TICKET, null, values);
                tablePath = TrippSQLiteHelper.TABLE_TICKET;
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        if (id > 0) {
            retVal = ContentUris.withAppendedId(Uri.parse(tablePath), id);
            getContext().getContentResolver().notifyChange(retVal, null);
            return retVal;
        }
        return retVal;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsDeleted = 0;

        switch (uriMatcher.match(uri)) {
            case URI_CODE_TT:
                rowsDeleted = sqlDB.delete(TrippSQLiteHelper.TABLE_TICKET_TYPE, selection, selectionArgs);
                break;
            case URI_CODE_P:
                rowsDeleted = sqlDB.delete(TrippSQLiteHelper.TABLE_PAYMENT, selection, selectionArgs);
                break;
            case URI_CODE_T:
                rowsDeleted = sqlDB.delete(TrippSQLiteHelper.TABLE_TICKET, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int rowsUpdated = 0;

        switch (uriMatcher.match(uri)) {
            case URI_CODE_TT:
                rowsUpdated = sqlDB.update(TrippSQLiteHelper.TABLE_TICKET_TYPE, values, selection, selectionArgs);
                break;
            case URI_CODE_P:
                rowsUpdated = sqlDB.update(TrippSQLiteHelper.TABLE_PAYMENT, values, selection, selectionArgs);
                break;
            case URI_CODE_T:
                rowsUpdated = sqlDB.update(TrippSQLiteHelper.TABLE_TICKET, values, selection, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    public void emptyDatabase(){
        sqlDB.execSQL("delete from "+ TrippSQLiteHelper.TABLE_TICKET_TYPE);
        sqlDB.execSQL("delete from "+ TrippSQLiteHelper.TABLE_PAYMENT);
        sqlDB.execSQL("delete from "+ TrippSQLiteHelper.TABLE_TICKET);
        return;
    }
}
