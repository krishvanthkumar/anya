package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.VideoView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class mvdisplay extends AppCompatActivity {

    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 1;
    String vid;
    private VideoView videoView;
    private static final int STORAGE_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvdisplay);

        Intent intent = getIntent();
        vid = intent.getStringExtra("vid");

        videoView = findViewById(R.id.videoView5);

        // Replace 'your_server_url' with the URL where your PHP script is hosted
        String serverUrl = ipforall.ipt + "mdisplay.php";
        fetchData(vid);
    }
    private void fetchData(String vid) {
        // Replace "http://192.168.156.100:80/login/prof.php" with your actual API endpoint
        String apiUrl = ipforall.ipt +"mdisplay.php";

        // Append the username as a parameter to the URL
        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("hhhh");
                handleError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Send the username as a POST parameter
                Map<String, String> data = new HashMap<>();
                data.put("vid", vid);

                // Log the parameters for debugging
                Log.d("Volley Request", "Params: " + data.toString());

                return data;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);

    }


    private void handleResponse(String response) {
        Log.d("JSON Response", response);
        String respone= String.valueOf(response);

        try {
            String[] values = response.split("/");



            if (values.length > 1) {
                // Extract the values from the response string
                String videoPath = ipforall.ipt + "videos/" + values[6].substring(0, values[6].length() - 2);
                System.out.println(videoPath);
                Log.d("Video Path", videoPath);
                playVideo(videoPath);
            } else {
                // Handle the case where the response does not contain expected data
                Log.e("handleResponse", "Unexpected response format: " + response);
            }
        } catch (Exception e) {
            // Handle any exceptions that might occur during processing
            e.printStackTrace();
            Log.e("handleResponse", "Error processing response: " + e.getMessage());
        }
    }

    private void handleError(VolleyError error) {
        System.out.println("boooooo");
    }

    private void playVideo(String videoPath) {
        CustomMediaController mediaController = new CustomMediaController(this);
        mediaController.setAnchorView(videoView);

        videoView.setMediaController(mediaController);
        videoView.setVideoPath(videoPath);
        videoView.requestFocus();
        videoView.start();
    }
}