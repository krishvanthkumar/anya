package com.example.medical_application;


import static java.io.File.createTempFile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
public class videoupload extends AppCompatActivity {
    private static final int REQUEST_PICK_VIDEO = 1;
    private Uri videoUri;
    private TextView textSelectedVideo;

    private String video_title;
    private EditText vt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videoupload);

        textSelectedVideo = findViewById(R.id.filename);

        vt = findViewById(R.id.title);



        Button bt1 = findViewById(R.id.upload);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectVideoFile();
            }
        });

        Button bt2 = findViewById(R.id.submit);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an Intent to start the UploadVideoService
                video_title = vt.getText().toString().trim();

                if (videoUri != null && !video_title.isEmpty() ) {
                    uploadVideoFile();
                } else {
                    Toast.makeText(videoupload.this, "Please select a video file first or first enter the video title", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }

    private void selectVideoFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("video/*");
        startActivityForResult(intent, REQUEST_PICK_VIDEO);
    }

    // Inside the videoupload class
    // Inside the videoupload class
    private void uploadVideoFile() {
        if (videoUri == null) {
            Toast.makeText(this, "Please select a video file first", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the video title from the URI


        // Perform video upload in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String serverURL = ipforall.ipt + "vupload.php";
                    HttpURLConnection connection = null;
                    DataOutputStream dataOutputStream = null;

                    // Open InputStream using ContentResolver
                    InputStream inputStream = getContentResolver().openInputStream(videoUri);

                    if (inputStream != null) {
                        URL url = new URL(serverURL);
                        connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.setDoOutput(true);
                        connection.setUseCaches(false);
                        connection.setRequestMethod("POST");
                        connection.setRequestProperty("Connection", "Keep-Alive");
                        connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=*");

                        // Create a multipart form data
                        DataOutputStream request = new DataOutputStream(connection.getOutputStream());
                        request.writeBytes("--*\r\n");
                        request.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                                + videoUri.getLastPathSegment() + "\"" + "\r\n");
                        request.writeBytes("\r\n");

                        // Write video file data
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            request.write(buffer, 0, bytesRead);
                        }
                        request.writeBytes("\r\n");
                        request.writeBytes("--*--\r\n");

                        // Write video title
                        request.writeBytes("--*");
                        request.writeBytes("\r\n");
                        request.writeBytes("Content-Disposition: form-data; name=\"video_title\"");
                        request.writeBytes("\r\n");
                        request.writeBytes("\r\n");
                        request.writeBytes(video_title);
                        request.writeBytes("\r\n");
                        request.writeBytes("--*--\r\n");




                        // Close streams
                        inputStream.close();
                        request.flush();
                        request.close();

                        // Get server response
                        int serverResponseCode = connection.getResponseCode();
                        String serverResponseMessage = connection.getResponseMessage();

                        if (serverResponseCode == 200) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(videoupload.this, "Video uploaded successfully", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(videoupload.this, "Failed to upload video", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(videoupload.this, "Failed to open input stream for the selected video", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // Helper method to get the file path from URI
    @SuppressLint("Range")
    private String getRealPathFromURI(Uri uri) {
        String filePath = null;
        String scheme = uri.getScheme();
        if ("content".equalsIgnoreCase(scheme)) {
            InputStream inputStream = null;
            FileOutputStream outputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                if (inputStream != null) {
                    File tempFile = createTempFile("video_", ".mp4");
                    outputStream = new FileOutputStream(tempFile);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                    filePath = tempFile.getAbsolutePath();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else if ("file".equalsIgnoreCase(scheme)) {
            filePath = uri.getPath();
        }
        return filePath;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_PICK_VIDEO && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                videoUri = data.getData();
                if (videoUri != null) {
                    String fileName = getFileNameFromUri(videoUri);
                    textSelectedVideo.setText("Selected video: " + fileName);
                }
            }
        }
    }

    @SuppressLint("Range")
    private String getFileNameFromUri(Uri uri) {
        String result = null;

        if (uri.getScheme().equals("content")) {
            try {
                // Query the content resolver to get the file name
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (result == null) {
            // If failed to retrieve file name from content resolver, get it from the URI itself
            result = uri.getPath();
            int cut = result.lastIndexOf(File.separator);
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }
        return result;
    }


}