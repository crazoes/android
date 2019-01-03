package com.allyants.chiplibraryexample;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity {

    boolean isOn = false;
    View navView;
    LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        final LayoutInflater layoutInflator = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout insertPoint = (LinearLayout) findViewById(R.id.r2);

        Intent intent = getIntent();
        String username = intent.getExtras().getString("username");

        JsonArrayRequest request = new JsonArrayRequest(
                Request.Method.GET,
                Constants.endpoint("/users/" + username + "/feed"),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                String title = obj.getString("title");
                                String description = obj.getString("description");
                                JSONArray skills = obj.getJSONArray("skills");

                                StringBuilder ss = new StringBuilder();
                                for(int j = 0; j < skills.length(); j++) {
                                    JSONObject skill = skills.getJSONObject(j);
                                    ss.append(" " + skill.getString("skill"));
                                }

                                LinearLayout insertPoint = (LinearLayout) findViewById(R.id.r2);

                                View view = layoutInflator.inflate(R.layout.feed, null);
                                ((TextView)view.findViewById(R.id.title)).setText(title);
                                ((TextView)view.findViewById(R.id.description)).setText(description);
                                ((TextView)view.findViewById(R.id.skills)).setText(ss.toString());
                                view.setLayoutParams(new AbsListView.LayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT));
                                insertPoint.addView(view);
                            }
                            catch(JSONException ex) {
                                String trace = Log.getStackTraceString(ex);
                                ((TextView)findViewById(R.id.debug)).setText(trace);
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        RequestsClient.getInstance(getApplicationContext()).addToRequestQueue(request);

        ImageView right = (ImageView)findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isOn) {
                    ImageView right = (ImageView)findViewById(R.id.right);
                    RelativeLayout nav = (RelativeLayout)findViewById(R.id.nav);
                    navView = layoutInflator.inflate(R.layout.rightside, null);
                    nav.addView(navView);

                    TextView profile = (TextView) findViewById(R.id.profile);

                    profile.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(getApplicationContext(), ProfilePage.class);
                            startActivity(intent);
                        }
                    });

                    TextView logout = (TextView) findViewById(R.id.logout);

                    logout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            StringRequest request = new StringRequest(
                                    Request.Method.POST,
                                    Constants.endpoint("/auth/logout"),
                                    new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            Toast.makeText(getApplicationContext(), "Logged out", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(intent);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Toast.makeText(getApplicationContext(), "Failed, try again", Toast.LENGTH_SHORT);
                                        }
                                    }
                            );
                            RequestsClient.getInstance(getApplicationContext()).addToRequestQueue(request);
                        }
                    });

                    TextView notificaitons = (TextView) findViewById(R.id.notifications);

                    notificaitons.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Intent intent = new Intent(getApplicationContext(), );
                            // startActivity(intent);
                        }
                    });
                }
                else {
                    RelativeLayout nav = (RelativeLayout)findViewById(R.id.nav);
                    nav.removeView(navView);
                }
                isOn = !isOn;
            }
        });
    }
}