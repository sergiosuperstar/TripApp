package com.example.icf.tripappclient.service;

import io.swagger.client.model.TicketValidation;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by Vuletic on 9.6.2017.
 */

public interface TicketValidationService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("tickets/validation/")
    Call<Boolean> add(@Body TicketValidation validation);

}
