package com.allyants.chiplibraryexample;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.net.CookieHandler;
import java.net.CookieManager;

public class RequestsClient {
    private Context context;
    private RequestQueue requestsQueue;
    private static RequestsClient client;

    private RequestsClient(Context context) {
        this.context = context;
        this.requestsQueue = getRequestsQueue();
        CookieHandler.setDefault(new CookieManager());
    }

    public static synchronized RequestsClient getInstance(Context context) {
        if (client == null) {
            client = new RequestsClient(context);
        }
        return client;
    }

    public RequestQueue getRequestsQueue() {
        if (requestsQueue == null) {
            requestsQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestsQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestsQueue().add(req);
    }
}
