package com.allyants.chiplibraryexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        loginButton = (Button)findViewById(R.id.btnLogin);
        Button goToRegisterButton = (Button)findViewById(R.id.btnLinkToRegisterScreen);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = Constants.endpoint("/auth/login");
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());

                JSONObject parameters = new JSONObject(params);

                loginButton.setEnabled(false);

                Toast.makeText(getApplicationContext(), "Logging in...", Toast.LENGTH_SHORT).show();

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        url,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
                                loginButton.setEnabled(true);
                                Intent intent = new Intent(getApplicationContext(), HomePage.class);
                                intent.putExtra("username", username.getText().toString());
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                ((TextView) findViewById(R.id.debug)).setText(Log.getStackTraceString(error));
                                loginButton.setEnabled(true);
                            }
                        }
                );

                RequestsClient.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRegisterActivity();
            }
        });
    }

    public void goToRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity1.class);
        startActivity(intent);
    }
}
