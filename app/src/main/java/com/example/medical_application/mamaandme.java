package com.example.medical_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class mamaandme extends AppCompatActivity {

    private String username;
    private String sdate;
    private String pick;
    private String rating;
    private String rating1;
    private NumberPicker picker1;
    private NumberPicker picker2;
    private SeekBar seekBar;
    private SeekBar seekBar1;
    private LocalDate currentDate;
    private String URL = ipforall.ipt + "mamame.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mamaandme);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("username")) {
            username = intent.getStringExtra("username");
        } else {
            // Handle case when username is not provided
            Toast.makeText(this, "Username not provided", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sdate = currentDate.format(formatter);

        picker1 = findViewById(R.id.picker1);
        picker1.setMaxValue(15);
        picker1.setMinValue(1);
        picker1.setValue(1);
        picker1.setOnValueChangedListener((picker, oldVal, newVal) ->
                pick = newVal + "." + (picker2 != null ? picker2.getValue() : ""));

        picker2 = findViewById(R.id.picker2);
        picker2.setMaxValue(59);
        picker2.setMinValue(1);
        picker2.setValue(1);
        picker2.setOnValueChangedListener((picker, oldVal, newVal) ->
                pick = (picker1 != null ? picker1.getValue() : "") + "." + newVal);

        seekBar = findViewById(R.id.seekBar1);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rating = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        seekBar1 = findViewById(R.id.seekBar2);
        seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rating1 = String.valueOf(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        Button bt1 = findViewById(R.id.submit);
        bt1.setOnClickListener(view -> {
            if (username == null || username.isEmpty() || sdate == null || sdate.isEmpty() || rating == null || rating.isEmpty() || rating1 == null || rating1.isEmpty()) {
                Toast.makeText(mamaandme.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
            } else {
                sendLoginRequest();
            }
        });

        ImageView bt2 = findViewById(R.id.back);
        bt2.setOnClickListener(view -> {
            Intent i1 = new Intent(getApplicationContext(), MainActivity2.class);
            startActivity(i1);
        });
    }

    private void sendLoginRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                response -> handleResponse(response),
                this::handleError) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> data = new HashMap<>();
                data.put("username", username);
                data.put("mdate", sdate);
                data.put("pick", pick);
                data.put("rating", rating);
                data.put("rating1", rating1);
                return data;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void handleResponse(String response) {
        Log.d("JSON Response", response);
        if (response.toLowerCase().contains("success")) {
            Toast.makeText(this, "Sign Up successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(mamaandme.this, MainActivity2.class);
            intent.putExtra("username", username);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Sign Up failed", Toast.LENGTH_SHORT).show();
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
