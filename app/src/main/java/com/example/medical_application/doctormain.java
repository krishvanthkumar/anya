package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class doctormain extends AppCompatActivity {
    private List<Patient> patientList;
    private RecyclerView listView;

    private EditText c;

    private String m;
    private patientadapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctormain);

        listView = findViewById(R.id.myListView);
        patientList = new ArrayList<>(); // Assuming Patient is a class representing patient details

        // Create a custom adapter for your patients
        adapter = new patientadapter2(this, patientList);

        // Set item click listener to open pat_view activity with selected patient details
        adapter.setOnItemClickListener(new patientadapter2.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Patient selectedPatient = patientList.get(position);
                Intent intent = new Intent(doctormain.this, patientstats.class);
                intent.putExtra("username", selectedPatient.getuser());

                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);

        // Set item click listener for ListView

        ImageView bt1 = findViewById(R.id.logout);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(getApplicationContext(),doclogin.class);
                startActivity(i1);
            }
        });
        String url = ipforall.ipt +"patientlist.php";
        makeRequest(url);
        TextView bt2 = findViewById(R.id.textView43);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), doctorhomepage.class);
                startActivity(i1);
            }
        });

        LinearLayout bt3 = findViewById(R.id.audio);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), audioupload.class);
                startActivity(i1);
            }
        });
        LinearLayout bt4 = findViewById(R.id.video);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), videoupload.class);
                startActivity(i1);
            }
        });
        LinearLayout bt5 = findViewById(R.id.add);
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), register1.class);
                startActivity(i1);
            }
        });
    }

    private void makeRequest(String url) {
        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                        Log.d("Volley Response", response);
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                        Toast.makeText(doctormain.this, "Error fetching data", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void parseResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            if (jsonArray.length() > 0) {
                // Clear existing data
                patientList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject patientObject = jsonArray.getJSONObject(i);
                    String user= patientObject.getString("username");
                    String name = patientObject.getString("firstname");
                    String base64Image = patientObject.getString("dp");

                    // Add this patient to your patientList
                    patientList.add(new Patient(name, user ,base64Image));
                }

                // Notify your adapter that the data set has changed
                adapter.notifyDataSetChanged();
            } else {
                // Handle empty response
                Toast.makeText(doctormain.this, "Empty response", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            Toast.makeText(doctormain.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }
}