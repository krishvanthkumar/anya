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
import android.widget.RadioButton;
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

public class register3 extends AppCompatActivity {

    Button btn1, btn2;
    RadioButton oc1,oc2,oc3,oc4, dd1,dd2,dd3,dd4,dd5,dd6,dd7,dd8,dd9, bd1,bd2,bd3,bd4,bd5,bd6,bd7,bd8,bd9,bd10,bd11,bd12,bd13,bd14,bd15;
    private EditText epoh, ega, epoad, edoad, edd, ecom , ebd, ebw;
    private String poh, oc, ga, poad, doad, dd, com, bd, bw, username;
    private String URL = ipforall.ipt+"addpatient3.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register3);
        username = getIntent().getStringExtra("username");
        initializeViews();

        btn1.setOnClickListener(v -> sendLoginRequest(username,register4.class));
        btn2.setOnClickListener(v -> sendLoginRequest(username,doctorhomepage.class));
    }

    private void initializeViews() {
        epoh = findViewById(R.id.poh);

        oc1 = findViewById(R.id.radioButton11);
        oc2 = findViewById(R.id.radioButton12);
        oc3 = findViewById(R.id.radioButton13);
        oc4 = findViewById(R.id.radioButton14);

        oc1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                oc = oc1.getText().toString();
            }
        });
        oc2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                oc = oc2.getText().toString();
            }
        });
        oc3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                oc = oc3.getText().toString();
            }
        });
        oc4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                oc = oc4.getText().toString();
            }
        });

        ega = findViewById(R.id.ga);
        epoad= findViewById(R.id.poad);
        edoad= findViewById(R.id.doad);

        dd1 = findViewById(R.id.radioButton21);
        dd2 = findViewById(R.id.radioButton22);
        dd3 = findViewById(R.id.radioButton23);
        dd4 = findViewById(R.id.radioButton24);
        dd5 = findViewById(R.id.radioButton25);
        dd6 = findViewById(R.id.radioButton26);
        dd7 = findViewById(R.id.radioButton27);
        dd8 = findViewById(R.id.radioButton28);
        dd9 = findViewById(R.id.radioButton29);

        dd1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd1.getText().toString();
            }
        });
        dd2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd2.getText().toString();
            }
        });
        dd3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd3.getText().toString();
            }
        });
        dd4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd4.getText().toString();
            }
        });
        dd5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd5.getText().toString();
            }
        });
        dd6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd6.getText().toString();
            }
        });
        dd7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd7.getText().toString();
            }
        });
        dd8.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd8.getText().toString();
            }
        });
        dd9.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                dd = dd9.getText().toString();
            }
        });

        bd1 = findViewById(R.id.radioButton31);
        bd2 = findViewById(R.id.radioButton32);
        bd3 = findViewById(R.id.radioButton33);
        bd4 = findViewById(R.id.radioButton34);
        bd5 = findViewById(R.id.radioButton35);
        bd6 = findViewById(R.id.radioButton36);
        bd7 = findViewById(R.id.radioButton37);
        bd8 = findViewById(R.id.radioButton38);
        bd9 = findViewById(R.id.radioButton39);
        bd10 = findViewById(R.id.radioButton310);
        bd11 = findViewById(R.id.radioButton311);
        bd12 = findViewById(R.id.radioButton312);
        bd13 = findViewById(R.id.radioButton313);
        bd14 = findViewById(R.id.radioButton314);
        bd15 = findViewById(R.id.radioButton315);

        bd1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd1.getText().toString();
            }
        });
        bd2.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd2.getText().toString();
            }
        });
        bd3.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd3.getText().toString();
            }
        });
        bd4.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd4.getText().toString();
            }
        });
        bd5.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd5.getText().toString();
            }
        });
        bd6.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd6.getText().toString();
            }
        });
        bd7.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd7.getText().toString();
            }
        });
        bd8.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd8.getText().toString();
            }
        });
        bd9.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd9.getText().toString();
            }
        });
        bd10.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd10.getText().toString();
            }
        });
        bd11.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd11.getText().toString();
            }
        });
        bd12.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd12.getText().toString();
            }
        });
        bd13.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd13.getText().toString();
            }
        });
        bd14.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd14.getText().toString();
            }
        });
        bd15.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                bd = bd15.getText().toString();
            }
        });

        ecom = findViewById(R.id.com);
        ebw=findViewById(R.id.bw);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);


    }



    private void sendLoginRequest(String username,Class<?> targetActivity) {
        poh = epoh.getText().toString().trim();
        poad = epoad.getText().toString().trim();
        doad = edoad.getText().toString().trim();
        bw = ebw.getText().toString().trim();
        ga = ega.getText().toString().trim();
        com = ecom.getText().toString().trim();

        if (poh.isEmpty() || poad.isEmpty() || ga.isEmpty() || doad.isEmpty() || bw.isEmpty() || com.isEmpty()) {
            System.out.println("qqqqq");
            Toast.makeText(register3.this, "Fields cannot be empty", Toast.LENGTH_SHORT).show();
        } else {
            System.out.println("22222"+username);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    response -> handleResponse( response, username, targetActivity),
                    this::handleError) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> data = new HashMap<>();

                    data.put("poh", poh);
                    data.put("poad", poad);
                    data.put("ga", ga);
                    data.put("doad", doad);
                    data.put("bw", bw);
                    data.put("com", com);
                    data.put("oc", oc);
                    data.put("dd", dd);
                    data.put("bd", bd);
                    data.put("username", username);
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

                Intent intent = new Intent(register3.this, targetActivity);
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
