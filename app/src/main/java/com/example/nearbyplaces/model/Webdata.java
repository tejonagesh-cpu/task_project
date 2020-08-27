package com.example.nearbyplaces.model;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Webdata {

    static Retrofit retrofit;
    static Retrofit retrofitGeoCoder;
    static OkHttpClient.Builder httpClientGeoCoder;
    static OkHttpClient.Builder httpClient;
    private static final String googleGeoCodingUrl = "https://maps.googleapis.com/maps/api/place/";


    public static Retrofit getGeoRetrofit() {
        if(retrofitGeoCoder == null) {
            if (httpClientGeoCoder == null) {
                httpClientGeoCoder =
                        new OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                                .connectTimeout(30, TimeUnit.SECONDS)
                                .writeTimeout(30, TimeUnit.SECONDS);
                ;

                httpClientGeoCoder.addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();


                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Accept", "application/json")
                                .header("Authorization", "auth-token")
                                .method(original.method(), original.body())
                                .build();

                        Response response = chain.proceed(request);


                        // Customize or return the response
                        return response;
                    }
                });
            }

            OkHttpClient OkHttpClient = httpClientGeoCoder.build();


            retrofitGeoCoder = new Retrofit.Builder()
                    .baseUrl(googleGeoCodingUrl)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .client(OkHttpClient)
                    .build();
        }
        return retrofitGeoCoder;
    }
}
