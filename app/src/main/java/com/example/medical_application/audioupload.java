
package com.example.medical_application;

import static android.app.PendingIntent.getActivity;

import static java.io.File.createTempFile;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class audioupload extends AppCompatActivity {
    private static final int REQUEST_PICK_AUDIO = 1;
    private Uri audioUri;
    private TextView textSelectedAudio;
    private EditText vt;
    private String video_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioupload);

        textSelectedAudio = findViewById(R.id.tv);
        vt = findViewById(R.id.title);


        Button bt1 = findViewById(R.id.upload);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectAudioFile();
            }
        });

        Button bt2 = findViewById(R.id.submit);
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create an Intent to start the UploadAudioService
                if (audioUri != null) {
                    uploadAudioFile();
                } else {
                    Toast.makeText(audioupload.this, "Please select an audio file first", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void selectAudioFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        startActivityForResult(intent, REQUEST_PICK_AUDIO);
    }

    private void uploadAudioFile() {
        if (audioUri == null) {
            Toast.makeText(this, "Please select an audio file first", Toast.LENGTH_SHORT).show();
            return;
        }
        video_title = vt.getText().toString().trim();
        // Create an OkHttpClient instance
        OkHttpClient client = new OkHttpClient();

        // Get the file path from the URI
        String filePath = getRealPathFromURI(audioUri);
        System.out.println(filePath);
        if (filePath == null) {
            Toast.makeText(this, "Failed to get file path", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a file object from the file path
        File file = new File(filePath);

        // Create a RequestBody instance from the file
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("audio", file.getName(),
                        RequestBody.create(MediaType.parse("audio/*"), file))
                .addFormDataPart("additional_data", video_title)
                .build();

        // Create a request object with the URL where you want to upload the file
        Request request = new Request.Builder()
                .url(ipforall.ipt +"audioupload.php") // Replace with your server URL
                .post(requestBody)
                .build();

        // Execute the request asynchronously
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Handle upload failure
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(audioupload.this, "Upload failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                // Handle upload success
                final String responseData = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(audioupload.this, "Upload successful: " + responseData, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                    File tempFile = createTempFile("audio_", ".mp3");
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
        if (requestCode == REQUEST_PICK_AUDIO && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                audioUri = data.getData();
                if (audioUri != null) {
                    String fileName = getFileNameFromUri(audioUri);
                    textSelectedAudio.setText("Selected audio: " + fileName);
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
                // This may vary depending on how the file provider is implemented
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);

                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));

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