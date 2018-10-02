package com.example.shreeya.tindevour;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Your Activity Title"); // for set actionbar title

        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final Button bRegister = (Button) findViewById(R.id.bRegister);
        final TextView forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        final TextView createAccount = (TextView) findViewById(R.id.createAccount);


        bRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, registerActivity.class);
                LoginActivity.this.startActivity(registerIntent);
            }
        });

    }
}
