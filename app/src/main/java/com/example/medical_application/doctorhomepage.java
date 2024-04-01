package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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

public class doctorhomepage extends AppCompatActivity {
    private List<Patient> patientList;
    private ListView listView;

    private EditText c;

    private String m;
    private PatientAdapter adapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorhomepage);

        listView = findViewById(R.id.myListView);
        searchView = findViewById(R.id.searchView);
        patientList = new ArrayList<>(); // Assuming Patient is a class representing patient details

        // Create a custom adapter for your patients
        adapter = new PatientAdapter(this, patientList);

        // Set item click listener to open pat_view activity with selected patient details
        adapter.setOnItemClickListener(new PatientAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Patient selectedPatient = patientList.get(position);
                Intent intent = new Intent(doctorhomepage.this, patientstats.class);
                intent.putExtra("username", selectedPatient.getuser());

                startActivity(intent);
            }
        });

        listView.setAdapter(adapter);

        // Set item click listener for ListView
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click if needed
            }
        });


        String url = ipforall.ipt +"patientlist.php";
        makeRequest(url);
        setupSearchView();
    }
    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the parent list based on the entered text
                filterParentList(newText);
                return true;
            }
        });
    }
    private void filterParentList(String searchText) {
        Log.d("SearchText", searchText);

        List<Patient> filteredList = new ArrayList<>();

        // Trim leading and trailing whitespaces
        searchText = searchText.trim().toLowerCase();

        for (Patient patient : patientList) {
            Log.d("PatientName", patient.getName());
            if (patient.getName().toLowerCase().contains(searchText)) {
                filteredList.add(patient);
            }
        }

        // Update the adapter with the filtered list
        adapter.setFilteredList(filteredList);
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
                        Toast.makeText(doctorhomepage.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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
                    patientList.add(new Patient(name, user, base64Image));
                }

                // Notify your adapter that the data set has changed
                adapter.notifyDataSetChanged();
            } else {
                // Handle empty response
                Toast.makeText(doctorhomepage.this, "Empty response", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            Toast.makeText(doctorhomepage.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }
}