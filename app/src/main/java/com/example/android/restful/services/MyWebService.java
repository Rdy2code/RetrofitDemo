package com.example.android.restful.services;

import com.example.android.restful.model.DataItem;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyWebService {

    String BASE_URL = "http://560057.youcanlearnit.net/";

    String FEED = "services/json/itemsfeed.php";

    //Declare and instantiate the web service using a GET method
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    //Create call types here
    @GET(FEED)
    Call<DataItem[]> dataItems();

    //Create a second call to search for specific parameters
    @GET(FEED)
    Call<DataItem[]> dataItems(@Query("category") String category);
}
