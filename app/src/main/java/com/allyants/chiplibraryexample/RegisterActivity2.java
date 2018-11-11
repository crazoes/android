package com.allyants.chiplibraryexample;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allyants.chipview.ChipView;
import com.allyants.chipview.SimpleChipAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static android.os.Build.VERSION_CODES.N;


public class RegisterActivity2 extends AppCompatActivity {

    private ChipView areasCV;
    private ChipView skillsCV;
    private SimpleChipAdapter skillsAdapter;
    private SimpleChipAdapter areasAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        areasCV = (ChipView) findViewById(R.id.areaCV);
        skillsCV = (ChipView) findViewById(R.id.skillCV);

        // setting width and background color

        int colorValue = Color.parseColor("#3e454c");
        int width = 600;
        int height = 415;
        LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
        l1.setBackgroundColor(colorValue);
        l1.setLayoutParams(new LinearLayout.LayoutParams(width, height));
        LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
        l2.setBackgroundColor(colorValue);
        l2.setLayoutParams(new LinearLayout.LayoutParams(width, height));

        // get areas and set the adapter

        JsonArrayRequest areasRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.endpoint("/areas"),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        ArrayList<Object> areas = new ArrayList<>();

                        Map<String, Integer> amap = new HashMap<>();

                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                String area = obj.getString("area");
                                int aid = obj.getInt("aid");
                                amap.put(area, aid);
                                areas.add(area);
                            }
                            catch (JSONException ex) {

                            }
                        }

                        areasAdapter = new SimpleChipAdapter(areas);
                        areasCV.setAdapter(areasAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Couldn't get area of interests, try again later.", Toast.LENGTH_SHORT);
                    }
                }
        );

        RequestsClient.getInstance(this).addToRequestQueue(areasRequest);

        final JsonArrayRequest skillsRequest = new JsonArrayRequest(
                Request.Method.GET,
                Constants.endpoint("/skills"),
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray array) {
                        ArrayList<Object> skills = new ArrayList<Object>();
                        Map<String, Integer> smap = new HashMap<>();
                        for(int i = 0; i < array.length(); i++) {
                            try {
                                JSONObject obj = array.getJSONObject(i);
                                String skill = obj.getString("skill");
                                int sid = obj.getInt("sid");
                                smap.put(skill, sid);
                                skills.add(skill);
                            }
                            catch(JSONException ex) {

                            }
                        }

                        skillsAdapter = new SimpleChipAdapter(skills);
                        skillsCV.setAdapter(skillsAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Couldn't get skills, try again later.", Toast.LENGTH_SHORT);
                    }
                }
        );

        RequestsClient.getInstance(this).addToRequestQueue(skillsRequest);

        Button registerButton = (Button) findViewById(R.id.btnRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();

                String username = intent.getStringExtra("username");
                String email = intent.getStringExtra("email");
                String name = intent.getStringExtra("name");
                String password = intent.getStringExtra("password");

                JSONArray aids = new JSONArray();
                JSONArray sids = new JSONArray();

                for(int i = 0; i < areasAdapter.getCount(); i++)
                    if (areasAdapter.isSelected(i))
                        aids.put((int)areasAdapter.getItem(i));

                for(int i = 0; i < skillsAdapter.getCount(); i++)
                    if (skillsAdapter.isSelected(i))
                        sids.put((int)skillsAdapter.getItem(i));


                try {
                    JSONObject obj = new JSONObject();
                    obj.put("username", username);
                    obj.put("password", password);
                    obj.put("name", name);
                    obj.put("email", email);
                    obj.put("skills", sids);
                    obj.put("areas", aids);

                    JsonObjectRequest request = new JsonObjectRequest(
                            Request.Method.POST,
                            Constants.endpoint("/auth/register"),
                            obj,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(), "Registered successfully, redirecting to login page", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "An error occurred, try again later", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                }
                catch(JSONException ex) {

                }
            }
        });
    }
}

