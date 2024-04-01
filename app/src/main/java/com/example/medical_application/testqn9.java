package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class testqn9 extends AppCompatActivity {

    RadioButton oc1,oc2,oc3,oc4;
    String username,q2,q1,q3,q4,q5,q6,q7,q8,q9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testqn9);
        username = getIntent().getStringExtra("username");
        q1=getIntent().getStringExtra("q1");
        q2=getIntent().getStringExtra("q2");
        q3=getIntent().getStringExtra("q3");
        q4=getIntent().getStringExtra("q4");
        q5=getIntent().getStringExtra("q5");
        q6=getIntent().getStringExtra("q6");
        q7=getIntent().getStringExtra("q7");
        q8=getIntent().getStringExtra("q8");

        oc1 = findViewById(R.id.radioButton11);
        oc2 = findViewById(R.id.radioButton12);
        oc3 = findViewById(R.id.radioButton13);
        oc4 = findViewById(R.id.radioButton14);

        oc1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q9 = "3";
            }
        });
        oc2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q9 = "2";
            }
        });
        oc3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q9 = "1";
            }
        });
        oc4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q9 = "0";
            }
        });

        LinearLayout bt1 = findViewById(R.id.next);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), testqn10.class);
                i1.putExtra("username", username);
                i1.putExtra("q1", q1);
                i1.putExtra("q2", q2);
                i1.putExtra("q3", q3);
                i1.putExtra("q4", q4);
                i1.putExtra("q5", q5);
                i1.putExtra("q6", q6);
                i1.putExtra("q7", q7);
                i1.putExtra("q8", q8);
                i1.putExtra("q9", q9);
                startActivity(i1);
            }
        });
        ImageView bt2 = findViewById(R.id.imageView21);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), testqn8.class);
                i1.putExtra("username", username);
                i1.putExtra("q1", q1);
                i1.putExtra("q2", q2);
                i1.putExtra("q3", q3);
                i1.putExtra("q4", q4);
                i1.putExtra("q5", q5);
                i1.putExtra("q6", q6);
                i1.putExtra("q7", q7);
                startActivity(i1);
            }
        });
        TextView bt3 = findViewById(R.id.textView26);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), MainActivity2.class);
                i1.putExtra("username", username);
                startActivity(i1);
            }
        });
    }
}