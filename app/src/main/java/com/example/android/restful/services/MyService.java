package com.example.android.restful.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.android.restful.MainActivity;
import com.example.android.restful.model.DataItem;

import java.io.IOException;

import retrofit2.Call;

public class MyService extends IntentService {

    public static final String TAG = "MyService";
    public static final String MY_SERVICE_MESSAGE = "myServiceMessage";
    public static final String MY_SERVICE_PAYLOAD = "myServicePayload";
    public static final String SEARCH_PARAM = "searchParameter";

    public MyService() {
        super("MyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String parameter = null;
        if (intent.hasExtra(SEARCH_PARAM)) {
            parameter = intent.getStringExtra(SEARCH_PARAM);
        }

        //Make a service request

        //Get an instance of the interface where retrofit is defined
        MyWebService webService =
                MyWebService.retrofit.create(MyWebService.class);
        Call<DataItem[]> call = webService.dataItems(parameter);
        DataItem[] dataItems;
        try {
            dataItems = call.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "onHandleIntent: " + e.getMessage());
            return;
        }

        //Return the data to MainActivity
        Intent messageIntent = new Intent(MY_SERVICE_MESSAGE);
        messageIntent.putExtra(MY_SERVICE_PAYLOAD, dataItems);
        LocalBroadcastManager manager =
                LocalBroadcastManager.getInstance(getApplicationContext());
        manager.sendBroadcast(messageIntent);
    }

}
