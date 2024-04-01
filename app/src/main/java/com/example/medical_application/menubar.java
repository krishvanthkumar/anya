package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class menubar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = getIntent().getStringExtra("username");
        System.out.println(username);
        setContentView(R.layout.activity_menubar);
        ImageView bt1 = findViewById(R.id.menubar1);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),MainActivity2.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        LinearLayout bt2 = findViewById(R.id.profile);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),profile.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        LinearLayout bt3 = findViewById(R.id.tracker);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),tracker.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        LinearLayout bt4 = findViewById(R.id.mytest);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),my_test.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        LinearLayout bt5 = findViewById(R.id.logout);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),login.class);
                startActivity(i1);
            }
        });
    }

}