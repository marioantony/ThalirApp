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

public class LoginActivity extends AppCompatActivity {
    ApolloClient apolloClient;
    TextView switchSignUp;
    EditText loginEmail;
    EditText loginPassword;
    Button loginButton;
//    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

         loginEmail = findViewById(R.id.login_email);
         loginPassword = findViewById(R.id.login_password);

        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        apolloClient = ApolloClient.builder()
                .serverUrl(UrlHelper.BASE_URL)
                .okHttpClient(okHttpClient)
                .build();

        switchSignUp = findViewById(R.id.signUpRedirectText);
        loginButton = findViewById(R.id.login_button);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginFarmer();
            }
        });


        String type = getIntent().getStringExtra("type");
        if (type.equals("farmer")){
            switchSignUp.setOnClickListener(e ->{
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.putExtra("type","farmer");
                startActivity(intent);
            });
        }
        if (type.equals("transporter")){
            switchSignUp.setOnClickListener(e ->{
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                intent.putExtra("type","transporter");
                startActivity(intent);
            });
        }
    }



    private void loginFarmer() {
        Intent intent = getIntent();

        String type = intent.getStringExtra("type");
        apolloClient.mutate(LoginUserMutation.builder()
                .password(loginPassword.getText().toString())
                .token("this is sample token")
                .type(type)
                .email(loginEmail.getText().toString())
                .build()).enqueue(new ApolloCall.Callback<LoginUserMutation.Data>() {
            @Override
            public void onResponse(@NonNull Response<LoginUserMutation.Data> response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.getData().loginUser() != null){
                            Toast.makeText(LoginActivity.this, "Farmer Logged in Successfully...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(LoginActivity.this, "Check Email or Password", Toast.LENGTH_SHORT).show();
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