package com.allyants.chiplibraryexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class RegisterActivity1 extends AppCompatActivity {

    private EditText name;
    private EditText email;
    private EditText username;
    private EditText password;
    private EditText passwordConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        name = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        passwordConfirm = (EditText)findViewById(R.id.passwordConfirm);

        Button nextButton = (Button)findViewById(R.id.btnRegisterNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                boolean error = false;
                // Check if the username is valid format
                if (!verifyUsername(username.getText().toString())) {
                    showError("Username can contain alpha-numerics only");
                    error = true;
                }

                // Check if the email is valid
                if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                    showError("Email address is not valid.\nEnter a valid Email address.");
                    error = true;
                }

                // Check if password and confirm password match
                if (!password.getText().toString().equals(passwordConfirm.getText().toString())) {
                    showError("Password and Confirm Password do not match!");
                    error = true;
                }

                if (!error) {
                    goToRegister2Screen();
                }
            }
        });
    }

    public void showError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    public void goToRegister2Screen() {
        Intent intent = new Intent(this, RegisterActivity2.class);

        intent.putExtra("name", name.getText().toString());
        intent.putExtra("username", username.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("password", password.getText().toString());

        startActivity(intent);
    }

    public boolean verifyUsername(String username) {
        return Constants.usernameRegex.matcher(username).matches();
    }
}