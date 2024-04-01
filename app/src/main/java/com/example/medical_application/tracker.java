package com.example.medical_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class tracker extends AppCompatActivity {
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        username = getIntent().getStringExtra("username");
        System.out.println(username);
        LinearLayout bt1 = findViewById(R.id.sleep);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), sleeptracker.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        LinearLayout bt2 = findViewById(R.id.mood);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), mood_tracker.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
    }
}