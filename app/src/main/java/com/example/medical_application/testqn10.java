package com.example.medical_application;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
public class testqn10 extends AppCompatActivity {

    RadioButton oc1,oc2,oc3,oc4;
    String username,q2,q1,q3,q4,q5,q6,q7,q8,q9,q10;
    int fq2,fq1,fq3,fq4,fq5,fq6,fq7,fq8,fq9,fq10,tot;
    private String URL = ipforall.ipt+"testcal.php";

    LocalDate currentDate;
    String tdate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testqn10);
        username = getIntent().getStringExtra("username");
        q1=getIntent().getStringExtra("q1");
        q2=getIntent().getStringExtra("q2");
        q3=getIntent().getStringExtra("q3");
        q4=getIntent().getStringExtra("q4");
        q5=getIntent().getStringExtra("q5");
        q6=getIntent().getStringExtra("q6");
        q7=getIntent().getStringExtra("q7");
        q8=getIntent().getStringExtra("q8");
        q9=getIntent().getStringExtra("q9");

        oc1 = findViewById(R.id.radioButton11);
        oc2 = findViewById(R.id.radioButton12);
        oc3 = findViewById(R.id.radioButton13);
        oc4 = findViewById(R.id.radioButton14);

        oc1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q10 = "3";
            }
        });
        oc2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q10 = "2";
            }
        });
        oc3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q10 = "1";
            }
        });
        oc4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                q10 = "0";
            }
        });
        ImageView bt2 = findViewById(R.id.imageView21);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), testqn9.class);
                i1.putExtra("username", username);
                i1.putExtra("q1", q1);
                i1.putExtra("q2", q2);
                i1.putExtra("q3", q3);
                i1.putExtra("q4", q4);
                i1.putExtra("q5", q5);
                i1.putExtra("q6", q6);
                i1.putExtra("q7", q7);
                i1.putExtra("q8", q8);
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
        currentDate = LocalDate.now();
        System.out.println("Current Date: " + currentDate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tdate = currentDate.format(formatter);
        Button btn1=findViewById(R.id.submit);
        btn1.setOnClickListener(v -> sendLoginRequest(username,after_test.class));

    }
    private void sendLoginRequest(String username,Class<?> targetActivity) {
        fq1=Integer.parseInt(q1);
        fq2=Integer.parseInt(q2);
        fq3=Integer.parseInt(q3);
        fq4=Integer.parseInt(q4);
        fq5=Integer.parseInt(q5);
        fq6=Integer.parseInt(q6);
        fq7=Integer.parseInt(q7);
        fq8=Integer.parseInt(q8);
        fq9=Integer.parseInt(q9);
        fq10=Integer.parseInt(q10);
        tot=fq1+fq2+fq2+fq3+fq4+fq5+fq6+fq7+fq8+fq9+fq10;

        if (q1.isEmpty() || q2.isEmpty() || q3.isEmpty() || q4.isEmpty() || q5.isEmpty() || q6.isEmpty() ||q7.isEmpty() ||  q8.isEmpty() || q9.isEmpty() || q10.isEmpty())   {
            System.out.println("qqqqq");
            Toast.makeText(testqn10.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("22222"+username);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> handleResponse( response, username, targetActivity),
                    this::handleError) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();

                    data.put("username", username);
                    data.put("tdate", tdate);
                    data.put("q1", q1);
                    data.put("q2", q2);
                    data.put("q3", q3);
                    data.put("q4", q4);
                    data.put("q5", q5);
                    data.put("q6", q6);
                    data.put("q7", q7);
                    data.put("q8", q8);
                    data.put("q9", q9);
                    data.put("q10", q10);
                    data.put("q10", q10);
                    data.put("tot", String.valueOf(tot));

                    return data;
                }
            };

            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(stringRequest);
        }
    }

    private void handleResponse(String response, String username, Class<?> targetActivity) {
        Log.d("JSON Response", response);

        try {
            if (response.toLowerCase().contains("success")) {
                Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(testqn10.this, targetActivity);
                intent.putExtra("username", username);
                intent.putExtra("total", String.valueOf(tot));
                startActivity(intent);
                System.out.println("aaa " + username);

                finish();
            } else {
                System.out.println(username);
                Toast.makeText(this, "Sign Up failed", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleError(VolleyError error) {
        if (error instanceof TimeoutError) {
            Toast.makeText(this, "Request timed out. Check your internet connection.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, error.toString().trim(), Toast.LENGTH_SHORT).show();
        }
    }


}

