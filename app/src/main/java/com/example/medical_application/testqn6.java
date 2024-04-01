package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class testqn6 extends AppCompatActivity {
    RadioButton oc1,oc2,oc3,oc4;
    String username,q2,q1,q3,q4,q5,q6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testqn6);
        username = getIntent().getStringExtra("username");
        q1=getIntent().getStringExtra("q1");
        q2=getIntent().getStringExtra("q2");
        q3=getIntent().getStringExtra("q3");
        q4=getIntent().getStringExtra("q4");
        q5=getIntent().getStringExtra("q5");

        oc1 = findViewById(R.id.radioButton11);
        oc2 = findViewById(R.id.radioButton12);
        oc3 = findViewById(R.id.radioButton13);
        oc4 = findViewById(R.id.radioButton14);

        oc1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q6 = "3";
            }
        });
        oc2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q6 = "2";
            }
        });
        oc3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q6 = "1";
            }
        });
        oc4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q6 = "0";
            }
        });

        LinearLayout bt1 = findViewById(R.id.next);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), testqn7.class);
                i1.putExtra("username", username);
                i1.putExtra("q1", q1);
                i1.putExtra("q2", q2);
                i1.putExtra("q3", q3);
                i1.putExtra("q4", q4);
                i1.putExtra("q5", q5);
                i1.putExtra("q6", q6);
                startActivity(i1);
            }
        });
        ImageView bt2 = findViewById(R.id.imageView21);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), testqn5.class);
                i1.putExtra("username", username);
                i1.putExtra("q1", q1);
                i1.putExtra("q2", q2);
                i1.putExtra("q3", q3);
                i1.putExtra("q4", q4);
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