package com.example.blooddonationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.security.PrivateKey;

public class RegistrationActivity extends AppCompatActivity {

    private Button DonatorButton,recipientbutton;
    private TextView backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        DonatorButton=findViewById(R.id.DonatorButton);
        recipientbutton = findViewById(R.id.recipientbutton);
        backButton = findViewById(R.id.backButton);


//       donorbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(RegistrationActivity.this, DonorActivity.class);
//                startActivity(intent);
//            }
//        });


        DonatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, DonorActivity.class);
                startActivity(intent);
            }
        });
        recipientbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, recipientactivity.class);
                startActivity(intent);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}