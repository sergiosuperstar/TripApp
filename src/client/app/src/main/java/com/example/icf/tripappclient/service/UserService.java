package com.example.icf.tripappclient.service;

import io.swagger.client.model.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NemanjaM on 17.5.2017.
 */

public interface UserService {

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/{username}")
    Call<User> get(@Header("Authorization") String auth, @Path("username") String username);


    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @POST("user/")
    Call<ResponseBody> add(@Body User user);

    /*
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @DELETE("user/{username}")
    Call<ResponseBody> remove(@Path("username") String username);
    */

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/login")
    Call<User> login(@Query("username") String username, @Query("password") String password);

    /*
    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @GET("user/logout")
    Call<ResponseBody> logout(@Query("username") String username, @Query("password") String password);
    */

    @Headers({
            "User-Agent: Mobile-Android",
            "Content-Type:application/json"
    })
    @PUT("user/{username}")
    Call<User> add(@Header("Authorization") long UserId, @Path("username") String username, @Body User user);


}
