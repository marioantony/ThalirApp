package com.example.thalir_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.api.internal.ApolloLogger;
import com.apollographql.apollo.exception.ApolloException;

import java.util.List;

import okhttp3.OkHttpClient;

public class CreateSchedule extends AppCompatActivity {
    EditText orderDate;
    EditText bags;
    EditText amount;
    EditText time;
    EditText location;
    EditText destination;
    Button saveButton;
    ApolloClient apolloClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_schedule);

        orderDate = findViewById(R.id.date);
        bags = findViewById(R.id.bags);
        amount = findViewById(R.id.amount);
        time = findViewById(R.id.time);
        location = findViewById(R.id.location);
        destination = findViewById(R.id.destination);
        saveButton = findViewById(R.id.saveButton);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
         apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mutateSchedule();
            }
        });
    }



    private void mutateSchedule() {
        apolloClient.mutate(AddScheduleMutation.builder()
                        .date(orderDate.getText().toString())
                        .maxAllowedBag(Integer.parseInt(bags.getText().toString()))
                        .vehicleOwner_id("1")
                        .destinationLocation(destination.getText().toString())
                        .departureTime(time.getText().toString())
                        .departureLocation(location.getText().toString())
                        .amountPerBag(Float.parseFloat(amount.getText().toString()))
                        .build())
                .enqueue(new ApolloCall.Callback<AddScheduleMutation.Data>() {
                    @Override
                    public void onResponse(@NonNull Response<AddScheduleMutation.Data> response) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (response.getData().addSchedule() != null) {
                                    Toast.makeText(CreateSchedule.this, "Creating schedule...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    Toast.makeText(CreateSchedule.this, "Can not create Schedule, Something went wrong...", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
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
