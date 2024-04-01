package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class after_test extends AppCompatActivity {
    TextView dscore,display;
    String username,score;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_test);
        username = getIntent().getStringExtra("username");
        score=getIntent().getStringExtra("total");
        dscore = findViewById(R.id.score);
        display=findViewById(R.id.display);
        dscore.setText(score);
        Button bt1 = findViewById(R.id.done);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), MainActivity2.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });
        if(Integer.parseInt(score)>=5){
            display.setText("Your score is high , PLease consult Doctor");
        }else{
            display.setText("Your score is in limit");
        }
    }
}