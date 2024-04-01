package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class register2 extends AppCompatActivity {

    Button btn1, btn2;
    private EditText elmp, eedd, ega, enoc;
    private String lmp, edd, ga, noc, username, password;
    private String URL = ipforall.ipt+"addpatient2.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);
        initializeViews();
        username = getIntent().getStringExtra("username");
        password = getIntent().getStringExtra("password");
        btn1.setOnClickListener(v -> sendLoginRequest(register3.class));
        btn2.setOnClickListener(v -> sendLoginRequest(doctorhomepage.class));
    }

    private void initializeViews() {
        elmp = findViewById(R.id.inputlmp);
        eedd = findViewById(R.id.inputedd);
        ega = findViewById(R.id.inputga);
        enoc = findViewById(R.id.inputnoc);
        btn1 = findViewById(R.id.yes);
        btn2 = findViewById(R.id.submit);

        elmp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // Not needed in this case
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calculateEDD(); // Update EDD when LMP changes
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // Not needed in this case
            }
        });
    }

    private void calculateEDD() {
        String lmpDateString = elmp.getText().toString();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date lmpDate = dateFormat.parse(lmpDateString);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(lmpDate);
            calendar.add(Calendar.DAY_OF_YEAR, 280);

            Date edd = calendar.getTime();
            String eddString = dateFormat.format(edd);
            eedd.setText(eddString);
        } catch (ParseException e) {
            eedd.setText("Invalid date format. Please use yyyy-MM-dd.");
        }
    }

    private void sendLoginRequest(Class<?> targetActivity) {
        lmp = elmp.getText().toString().trim();
        edd = eedd.getText().toString().trim();
        ga = ega.getText().toString().trim();
        noc = enoc.getText().toString().trim();

        if (lmp.isEmpty() || edd.isEmpty() || ga.isEmpty() || noc.isEmpty()) {
            System.out.println("qqqqq");
            Toast.makeText(register2.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("22222");
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> handleResponse( response, username, targetActivity),
                    this::handleError) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();
                    System.out.println(lmp+" "+edd+" "+ga+" "+noc+" "+username);
                    data.put("lmp", lmp);
                    data.put("edd", edd);
                    data.put("ga", ga);
                    data.put("noc", noc);
                    data.put("username",username);
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

                Intent intent = new Intent(register2.this, targetActivity);
                intent.putExtra("username", username);
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
