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

public class SignUpActivity extends AppCompatActivity {
    ApolloClient apolloClient;
    TextView switchLogin;
    Button signUp;
    EditText firstName;
    EditText lastName;
    EditText email;
    EditText password;
//    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        switchLogin = findViewById(R.id.loginRedirectText);
        Button signUp = findViewById(R.id.signup_button);

        switchLogin.setOnClickListener(e ->{
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        Intent intent = getIntent();
        String type = intent.getStringExtra("type");

        if (type.equals("farmer")){
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        signupFarmer();
                }
            });

        }

        if (type.equals("transporter")){
            signUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                        signupTransporter();
                }
            });
        }



    }

    private void signupFarmer() {

        apolloClient.mutate(AddFarmerMutation.builder()
                .firstName(firstName.getText().toString())
                .lastName(lastName.getText().toString())
                .email(email.getText().toString())
                .password(password.getText().toString())
                .build()).enqueue(new ApolloCall.Callback<AddFarmerMutation.Data>() {
            @Override
            public void onResponse(@NonNull Response<AddFarmerMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getData().addFarmer() != null){
                            Toast.makeText(SignUpActivity.this, "Creating Farmer...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("type","farmer");
                            startActivity(intent);
                        }else {
                            Toast.makeText(SignUpActivity.this, "Can not create Farmer, Something went wrong...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
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
                            Toast.makeText(SignUpActivity.this, "Creating Transporter ...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            intent.putExtra("type","transporter");
                            startActivity(intent);
                        }else {
                            Toast.makeText(SignUpActivity.this, "Can not create Transporter, Something went wrong...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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