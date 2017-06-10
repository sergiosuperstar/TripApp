package com.example.icf.tripappclient.service;

import android.content.Context;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import io.swagger.client.model.TicketValidation;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by NemanjaM on 17.5.2017.
 */

public class ServiceUtils {

    //public static final String SERVICE_API_PATH = "http://tripappftn.azurewebsites.net/sergiosuperstar/TripAppSimple/1.0.0/";
    public static final String SERVICE_API_PATH = "http://10.0.2.2:5000/sergiosuperstar/TripAppSimple/1.0.0/";

    public static OkHttpClient test(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
                .addInterceptor(interceptor).build();

        return client;
    }

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(SERVICE_API_PATH)
            .addConverterFactory(GsonConverterFactory.create())
            .client(test())
            .build();

    public static UserService userService = retrofit.create(UserService.class);
    public static TicketPurchaseService ticketPurchaseService = retrofit.create(TicketPurchaseService.class);
    public static TicketValidationService ticketValidationService = retrofit.create(TicketValidationService.class);
    public static CodeService codeService = retrofit.create(CodeService.class);
}
