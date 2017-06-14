package com.example.icf.tripappclient.service;

import java.util.List;

import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketType;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by NemanjaM on 11.6.2017.
 */

public interface TickeTypeService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("tickets/alltypes")
    Call<List<TicketType>> get();

}
