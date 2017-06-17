package com.example.icf.tripappclient.service;

import java.util.List;

import io.swagger.client.model.TicketPurchase;
import io.swagger.client.model.TicketValidation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Vuletic on 9.6.2017.
 */

public interface TicketValidationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("tickets/validation/")
    Call<TicketPurchase> add(@Body TicketValidation validation);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("tickets/validation/controller")
    Call<List<TicketValidation>> getValidations(@Query("searchString") long controllerId);

}
