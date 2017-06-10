package com.example.icf.tripappclient.service;

import io.swagger.client.model.PurchaseCode;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.PUT;

/**
 * Created by Vuletic on 10.6.2017.
 */

public interface CodeService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("codes/")
    Call<Boolean> put(@Body PurchaseCode code);

}
