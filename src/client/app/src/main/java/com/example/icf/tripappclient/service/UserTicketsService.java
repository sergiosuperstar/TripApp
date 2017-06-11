package com.example.icf.tripappclient.service;

import java.util.List;

import io.swagger.client.model.TicketPurchase;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NemanjaM on 11.6.2017.
 */

public interface UserTicketsService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("tickets")
    Call<List<TicketPurchase>> get(@Query("searchString") String searchString);    // searchString should be "all:{userId}"
}
