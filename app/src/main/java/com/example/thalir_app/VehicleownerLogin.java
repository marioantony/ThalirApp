package com.example.thalir_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class VehicleownerLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleowner_login);

        TextView switchSignUp = findViewById(R.id.signUpRedirectText1);

        switchSignUp.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), VehicleownerSignup.class);
            startActivity(intent);
        });
    }
}