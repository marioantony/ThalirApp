package com.example.thalir_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class WelcomeScreen extends AppCompatActivity {

    Button farmer;
    Button transporter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        Button farmer = findViewById(R.id.buttonFarmer);
        Button transporter = findViewById(R.id.buttonTransporter);

        farmer.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("type","farmer");
            startActivity(intent);
        });

        transporter.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("type","transporter");

            startActivity(intent);
        });
    }
}