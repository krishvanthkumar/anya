package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class audios extends AppCompatActivity {

    private List<musics> videoList;
    private ListView listView;
    private audioAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audios);
        listView = findViewById(R.id.myListView);
        videoList = new ArrayList<>(); // Assuming Patient is a class representing patient details

        // Create a custom adapter for your patients
        adapter = new audioAdapter(this, videoList);

        // Set item click listener to open pat_view activity with selected patient details
        adapter.setOnItemClickListener(new audioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                musics selectedPatient = videoList.get(position);
                Intent intent = new Intent(audios.this, audioplayer.class);
                intent.putExtra("id", selectedPatient.getid());

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

        String url = ipforall.ipt + "audiolist.php";
        makeRequest(url);
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
                        Toast.makeText(audios.this, "Error fetching data", Toast.LENGTH_SHORT).show();
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
                videoList.clear();

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject audioObject = jsonArray.getJSONObject(i);

                    // Check if the "vid" key exists in the JSON object
                    if (audioObject.has("id")) {
                        String aid = audioObject.getString("id");
                        String audio_title = audioObject.getString("filename");

                        // Add this audio to your audioList
                        videoList.add(new musics(audio_title, aid));
                    } else {
                        Log.e("parseResponse", "No 'vid' key found in JSON object: " + audioObject.toString());
                    }
                }

                // Notify your adapter that the data set has changed
                adapter.notifyDataSetChanged();
            } else {
                // Handle empty response
                Toast.makeText(audios.this, "Empty response", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing error
            Toast.makeText(audios.this, "Error parsing JSON", Toast.LENGTH_SHORT).show();
        }
    }

}