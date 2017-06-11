package com.example.icf.tripappclient.service;

import java.util.List;

import io.swagger.client.model.PurchaseCode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Vuletic on 10.6.2017.
 */

public interface PurchaseCodeService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("codes/")
    Call<Boolean> put(@Body PurchaseCode code);

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("codes/user")
    Call<List<PurchaseCode>> get(@Query("userId") String userId);

}
