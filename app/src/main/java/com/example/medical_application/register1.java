package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
        import android.widget.TextView;
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

public class register1 extends AppCompatActivity {

    private Spinner answerSpinner;
    private TextView resultTextView;
    Button btn;
    private EditText eid, epassword,efname,elname,econtact,eage,eheight,eweight,ebg;
    private String username, password,fname,lname,contact,age,gender,height,weight,bg,egender,sdate;
    private void vv(String s){
        username=s;
    }
    private String URL = ipforall.ipt+"addpatient1.php";
    private LocalDate currentDate;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register1);

        answerSpinner = findViewById(R.id.answergender);
        resultTextView = findViewById(R.id.resultTextView);
        currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        sdate = currentDate.format(formatter);
        // Define the options for the spinner
        String[] options = {"Male", "Female", "Other"};

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, options);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Ensure that the Spinner is not null before calling setAdapter
        if (answerSpinner != null) {
            answerSpinner.setAdapter(adapter);
        }

        // Set a listener for item selections
        if (answerSpinner != null) {
            answerSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                    String selectedAnswer = answerSpinner.getSelectedItem().toString();
                    egender = selectedAnswer;
                    resultTextView.setText(selectedAnswer);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parentView) {
                    // Do nothing here
                }
            });
        }

        eid = findViewById(R.id.inputun);
        epassword = findViewById(R.id.inputp);
        econtact = findViewById(R.id.inputcon);
        efname = findViewById(R.id.inputfn);
        elname = findViewById(R.id.inputln);
        eage = findViewById(R.id.inputage);
        eheight = findViewById(R.id.inputheight);
        eweight = findViewById(R.id.inputweight);
        ebg = findViewById(R.id.inputbg);
        btn = findViewById(R.id.button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username = eid.getText().toString().trim();
                vv(username);
                password = epassword.getText().toString().trim();
                fname= efname.getText().toString().trim();
                lname= elname.getText().toString().trim();
                contact = econtact.getText().toString().trim();
                age = eage.getText().toString().trim();
                gender= egender;
                height = eheight.getText().toString().trim();
                weight= eweight.getText().toString().trim();
                bg= ebg.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty() || fname.isEmpty() || lname.isEmpty() || age.isEmpty() || gender.isEmpty() || height.isEmpty() || weight.isEmpty() || contact.isEmpty() || bg.isEmpty() || sdate.isEmpty()) {
                    Toast.makeText(register1.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();


                } else {
                    System.out.println(username);
                    sendLoginRequest(username, password);
                }
            }
        });
    }
    private void sendLoginRequest(final String username, final String password) {
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
                data.put("username", username);
                data.put("password", password);
                data.put("firstname", fname);
                data.put("lastname", lname);
                data.put("contact", contact);
                data.put("age", age);
                data.put("gender", gender);
                data.put("height", height);
                data.put("weight", weight);
                data.put("bloodgroup", bg);
                data.put("sdate",sdate);
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

                Intent intent = new Intent(register1.this, register2.class);
                intent.putExtra("username", username);
                intent.putExtra("password", password);
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
