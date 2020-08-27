package com.example.nearbyplaces.model;

import com.google.gson.JsonElement;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {





    @GET("nearbysearch/json")
    Call<JsonElement> getAddressComponents(@Query("location") String location,
                                           @Query("radius") String radius,
                                           @Query("type") String type,
                                           @Query("sensor") String sensor,
                                           @Query("key") String key);
}
