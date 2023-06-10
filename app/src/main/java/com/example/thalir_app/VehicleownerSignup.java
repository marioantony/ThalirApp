package com.example.thalir_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import okhttp3.OkHttpClient;

public class VehicleownerSignup extends AppCompatActivity {
    ApolloClient apolloClient;
    TextView switchLogin;
    Button signUp;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicleowner_signup);

        firstName = findViewById(R.id.firstName1);
        lastName = findViewById(R.id.lastName1);
        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        TextView switchLogin = findViewById(R.id.vehicleLoginRedirectText);
        Button signUp = findViewById(R.id.signup_button1);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signupTransporter();
            }
        });

        switchLogin.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), VehicleownerLogin.class);
            startActivity(intent);
        });
    }

    private void signupTransporter() {

        apolloClient.mutate(AddVehicleOwnerMutation.builder()
                .firstName(firstName.getText().toString())
                .lastName(lastName.getText().toString())
                .email(email.getText().toString())
                .password(password.getText().toString())
                .build()).enqueue(new ApolloCall.Callback<AddVehicleOwnerMutation.Data>() {
            @Override
            public void onResponse(@NonNull Response<AddVehicleOwnerMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getData().addVehicleOwner() != null){
                            Toast.makeText(VehicleownerSignup.this, "Creating Transporter ...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), VehicleownerLogin.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(VehicleownerSignup.this, "Can not create Transporter, Something went wrong...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), VehicleownerSignup.class);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull ApolloException e) {
                Log.d("ApolloException", "Message: " + e.getMessage());

            }
        });

    }
}