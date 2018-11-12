package com.allyants.chiplibraryexample;


import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;


public class ProfilePage extends AppCompatActivity {

    final ImageView partOf = (ImageView) findViewById(R.id.partOf);
    final ImageView ideas = (ImageView) findViewById(R.id.ideas);
    final LinearLayout parentLayout = (LinearLayout)findViewById(R.id.layout);

    ArrayList<View> ownerViews;
    ArrayList<View> partOfViews;

    protected void showOwner() {
        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < ownerViews.size(); i++){
            parentLayout.addView(ownerViews.get(i));
        }
    }

    protected void showPartOf() {
        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < partOfViews.size(); i++){
            parentLayout.addView(partOfViews.get(i));
        }
    }

    protected void removePartOf() {
        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < partOfViews.size(); i++){
            parentLayout.removeView(partOfViews.get(i));
        }
    }

    protected void removeOwner() {
        // Layout inflater
        LayoutInflater layoutInflater = getLayoutInflater();
        for (int i = 0; i < ownerViews.size(); i++){
            parentLayout.removeView(partOfViews.get(i));
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                Constants.endpoint("/me"),
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject obj) {
                        try {
                            String name = obj.getString("name");
                            ArrayList<String> owner = new ArrayList<>();
                            ArrayList<String> part = new ArrayList<>();

                            JSONArray ownerArray = obj.getJSONArray("owner");

                            for(int i = 0; i < ownerArray.length(); i++) {
                                String title = ownerArray.getJSONObject(i).getString("title");
                                owner.add(title);
                            }

                            JSONArray partArray = obj.getJSONArray("part");

                            for(int i = 0; i < partArray.length(); i++) {
                                String title = partArray.getJSONObject(i).getString("title");
                                part.add(title);
                            }

                            int colorValue = Color.parseColor("#31373d");

                            partOf.setBackgroundColor(colorValue);
                            LinearLayout parentLayout = (LinearLayout)findViewById(R.id.layout);

                            ideas.setBackgroundColor(Color.parseColor("#999999"));
                            // Layout inflater
                            LayoutInflater layoutInflater = getLayoutInflater();

                            for (int i = 0; i < ownerArray.length(); i++){
                                // Add the text layout to the parent layout
                                View view = layoutInflater.inflate(R.layout.text_layout, parentLayout, false);
                                TextView textView1 = (TextView)view.findViewById(R.id.text);
                                textView1.setText(ownerArray.get(i).toString());
                                ownerViews.add(view);
                            }

                            for (int i = 0; i < partArray.length(); i++){
                                // Add the text layout to the parent layout
                                View view = layoutInflater.inflate(R.layout.text_layout, parentLayout, false);
                                TextView textView1 = (TextView)view.findViewById(R.id.text);
                                textView1.setText(partArray.get(i).toString());
                                partOfViews.add(view);
                            }

                            showOwner();
                        }
                        catch(JSONException ex) {

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

        final TextView t0 = (TextView)findViewById(R.id.t0);
        ImageView mShowDialog0 = (ImageView) findViewById(R.id.location);
        mShowDialog0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfilePage.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_link0, null);
                final EditText etlink = (EditText) mView.findViewById(R.id.etLink0);
                Button insertLink = (Button) mView.findViewById(R.id.insertLink0);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                insertLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etlink.getText().toString().isEmpty()) {
                            Toast.makeText(ProfilePage.this,
                                    "Location added",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            String setLink = etlink.getText().toString();
                            t0.setText(setLink);
                        } else {
                            Toast.makeText(ProfilePage.this,
                                    "Location not added",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        final TextView t1 = (TextView)findViewById(R.id.t1);
        ImageView mShowDialog = (ImageView) findViewById(R.id.link);
        mShowDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfilePage.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_link, null);
                final EditText etlink = (EditText) mView.findViewById(R.id.etLink);
                Button insertLink = (Button) mView.findViewById(R.id.insertLink);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                insertLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etlink.getText().toString().isEmpty()) {
                            Toast.makeText(ProfilePage.this,
                                    "Link Inserted",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();

                            String setLink = etlink.getText().toString();
                            t1.setText(setLink);
                        } else {
                            Toast.makeText(ProfilePage.this,
                                    "Link not Inserted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });

        final TextView t2 = (TextView)findViewById(R.id.t2);
        ImageView mShowDialog2 = (ImageView) findViewById(R.id.link2);
        mShowDialog2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfilePage.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_link, null);
                final EditText etlink = (EditText) mView.findViewById(R.id.etLink);
                Button insertLink = (Button) mView.findViewById(R.id.insertLink);


                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();
                insertLink.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!etlink.getText().toString().isEmpty()) {
                            Toast.makeText(ProfilePage.this,
                                    "Link Inserted",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                            String setLink2 = etlink.getText().toString();
                            t2.setText(setLink2);
                        } else {
                            Toast.makeText(ProfilePage.this,
                                    "Link not Inserted",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });


        ideas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ideas.setBackgroundColor(Color.parseColor("#31373d"));

                partOf.setBackgroundColor(Color.parseColor("#999999"));


                LinearLayout parentLayout = (LinearLayout)findViewById(R.id.layout);

                removeOwner();
                showPartOf();
            }
        });


        partOf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int colorValue = Color.parseColor("#31373d");

                partOf.setBackgroundColor(colorValue);
                LinearLayout parentLayout = (LinearLayout)findViewById(R.id.layout);

                ideas.setBackgroundColor(Color.parseColor("#999999"));
                removePartOf();
                showOwner();
            }
        });

        showOwner();
    }
}