package com.example.medical_application;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class moodanalysis extends AppCompatActivity {


    Button btn;
    private String username,mdate;
    LocalDate currentDate;
    private String mood = "";

    private String URL = ipforall.ipt+"mood.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moodanalysis);
        username = getIntent().getStringExtra("username");

        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        mdate = currentDate.format(formatter);

        Button bt1 = findViewById(R.id.sad);
        Button bt2 = findViewById(R.id.angry);
        Button bt3 = findViewById(R.id.happy);
        Button bt4 = findViewById(R.id.irrited);
        Button bt5 = findViewById(R.id.tired);
        bt1.setOnClickListener(view -> {
            bt1.setBackground(this.getDrawable(R.drawable.homepagebox));
            bt2.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt3.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt4.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt5.setBackground(this.getDrawable(R.drawable.homepagebox2));
            mood="0";
        });
        bt2.setOnClickListener(view -> {
            bt1.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt2.setBackground(this.getDrawable(R.drawable.homepagebox));
            bt3.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt4.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt5.setBackground(this.getDrawable(R.drawable.homepagebox2));
            mood="1";
        });
        ;
        bt3.setOnClickListener(view -> {
            bt1.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt2.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt3.setBackground(this.getDrawable(R.drawable.homepagebox));
            bt4.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt5.setBackground(this.getDrawable(R.drawable.homepagebox2));
            mood="2";
        });
        bt4.setOnClickListener(view -> {
            bt1.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt2.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt3.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt4.setBackground(this.getDrawable(R.drawable.homepagebox));
            bt5.setBackground(this.getDrawable(R.drawable.homepagebox2));
            mood="3";
        });
        bt5.setOnClickListener(view -> {
            bt1.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt2.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt3.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt4.setBackground(this.getDrawable(R.drawable.homepagebox2));
            bt5.setBackground(this.getDrawable(R.drawable.homepagebox));
            mood="4";
        });

        btn=findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (username.isEmpty() || mdate.isEmpty() || mood.isEmpty() ){
                    Toast.makeText(moodanalysis.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println(username);
                    sendLoginRequest(username);
                }
            }
        });
    }
    private void sendLoginRequest(final String username) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response,username);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Send the username and password as POST parameters
                Map<String, String> data = new HashMap<>();
                System.out.println(username+mdate+mood);
                data.put("username", username);
                data.put("mdate", mdate);
                data.put("mood", mood);


                return data;
            }
        };

        // Customize the retry policy
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        // Initialize the Volley request queue and add the request
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    // Handle the JSON response
    private void handleResponse(String response,String username) {
        Log.d("JSON Response", response);

        // Handle your JSON response here without assuming a 'status' field
        // You can parse the response and handle success/failure accordingly
        try {
            // Example: Check if the response contains "success"
            if (response.toLowerCase().contains("success")) {
                Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(moodanalysis.this, MainActivity2.class);
                intent.putExtra("username", username);
                startActivity(intent);
                System.out.println("aaa "+username);

                finish();
            } else {
                Toast.makeText(this, "Sign Up failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

    // Handle network request errors
    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("dddd");
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }
}