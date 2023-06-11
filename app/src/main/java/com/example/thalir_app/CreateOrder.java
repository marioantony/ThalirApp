package com.example.thalir_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import okhttp3.OkHttpClient;

public class CreateOrder extends AppCompatActivity {
    ApolloClient apolloClient;
    EditText bagCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);

        bagCount = findViewById(R.id.uploadTopic);
        Button saveButton = (Button) findViewById(R.id.saveButton);
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .okHttpClient(okHttpClient)
                .build();
//        saveButton.setOnClickListener(e ->{
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mutateBook();
            }
        });
    }

    private void mutateBook() {
        apolloClient.mutate(AddBookMutation.builder().farmer_id("1").schedule_id("1").requestedBag(Integer.parseInt(bagCount.getText().toString()))
                        .build())
                .enqueue(new ApolloCall.Callback<AddBookMutation.Data>() {
            @Override
            public void onResponse(@NonNull Response<AddBookMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getData().addBook()!= null){
                            Toast.makeText(CreateOrder.this, "Creating Order...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(CreateOrder.this, "Can not create Order, Something went wrong...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }

            @Override
            public void onFailure(@NonNull ApolloException e) {

            }
        });
    }
}
