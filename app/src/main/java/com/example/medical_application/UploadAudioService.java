package com.example.medical_application;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UploadAudioService extends IntentService {

    public static final String EXTRA_FILE_PATH = "file_path";

    public UploadAudioService() {
        super("UploadAudioService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String filePath = intent.getStringExtra(EXTRA_FILE_PATH);
        System.out.println(filePath);
        uploadAudioFile(filePath);
    }

    public void uploadAudioFile(String filePath) {
        // Create network request with Volley
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ipforall.ipt +"audioupload.php", // Replace with your API endpoint
                response -> {
                    // Upload successful, handle response
                    Log.d("UploadService", "Upload successful: " + response);
                },
                error -> {
                    // Handle upload error
                    Log.e("UploadService", "Upload error: " + error.getMessage());
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                // Add any additional parameters needed by your server API
                Map<String, String> params = new HashMap<>();
                // ...
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                // Add any authentication headers needed by your server API
                Map<String, String> headers = new HashMap<>();
                // ...
                return headers;
            }

            @Override
            protected VolleyError parseNetworkError(VolleyError error) {
                if (error instanceof ServerError) {
                    int statusCode = error.networkResponse.statusCode;
                    if (statusCode == HttpStatus.SC_UNAUTHORIZED ||
                            statusCode == HttpStatus.SC_FORBIDDEN) {
                        // Handle authentication errors
                        return new AuthFailureError(error.getMessage());
                    }
                }
                return super.parseNetworkError(error);
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                // Read audio file into a byte array
                File file = new File(filePath);
                try {
                    FileInputStream inputStream = new FileInputStream(file);
                    byte[] buffer = new byte[(int) file.length()];
                    int bytesRead = inputStream.read(buffer);
                    if (bytesRead != buffer.length) {
                        throw new IOException("Error reading file");
                    }
                    inputStream.close();
                    return buffer;
                } catch (IOException e) {
                    Log.e("UploadService", "Error reading audio file: " + e.getMessage());
                    throw new AuthFailureError(e.getMessage());
                }
            }

            @Override
            public String getBodyContentType() {
                // Set content type based on audio file format
                return "audio/mpeg"; // Change for other formats if needed
            }
        };

        // Add request to Volley queue
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}

