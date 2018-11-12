package com.allyants.chiplibraryexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestsClient client = RequestsClient.getInstance(getApplicationContext());

        StringRequest request = new StringRequest(
                Request.Method.GET,
                Constants.endpoint("/me"),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        TextView debug = (TextView)findViewById(R.id.debug);
                        debug.setText(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        TextView debug = (TextView)findViewById(R.id.debug);
                        debug.setText(error.toString());
                    }
                });

        client.addToRequestQueue(request);
    }
}
