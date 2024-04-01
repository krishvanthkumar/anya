package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class homepage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        FrameLayout a1 = findViewById(R.id.act1);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(), homepage.class);
                startActivity(i1);
            }
        });

        FrameLayout a2 = findViewById(R.id.act2);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(getApplicationContext(), homepage.class);
                startActivity(i2);
            }
        });

        FrameLayout a3 = findViewById(R.id.act3);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(getApplicationContext(), homepage.class);
                startActivity(i3);
            }
        });

        FrameLayout a4 = findViewById(R.id.act4);
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i4 = new Intent(getApplicationContext(), homepage.class);
                startActivity(i4);
            }
        });

        ImageView mb = findViewById(R.id.menu);
        mb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(getApplicationContext(), homepage.class);
                startActivity(i5);
            }
        });



    }

}