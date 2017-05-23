package com.example.icf.tripappclient.service;

import io.swagger.client.model.TicketPurchase;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;


/**
 * Created by Vuletic on 23.5.2017.
 */

public interface TicketPurchaseService {
   /* @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/{username}")
    Call<User> get(@Path("username") String username);*/


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("tickets/")
    Call<TicketPurchase> add(@Body TicketPurchase ticketPurchase);

    /*
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("user/{username}")
    Call<ResponseBody> remove(@Path("username") String username);
    */


   /* @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("user/{username}")
    Call<User> add(@Path("username") String username, @Body User user);*/
}
