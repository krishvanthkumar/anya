package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class beflogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beflogin);
        Button bt1 = findViewById(R.id.buttonp);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), login.class);
                startActivity(i1);
            }
        });
        Button bt2 = findViewById(R.id.buttond);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent( getApplicationContext(), doclogin.class);
                startActivity(i2);
            }
        });
        Button bt3 = findViewById(R.id.buttong);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent( getApplicationContext(), guardian_login.class);
                startActivity(i3);
            }
        });
    }
}