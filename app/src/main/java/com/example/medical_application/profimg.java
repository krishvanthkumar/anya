package com.example.medical_application;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class profimg extends AppCompatActivity {
    private String username;
    private static final int GALLERY_REQUEST_CODE = 1;

    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profimg);
        username = getIntent().getStringExtra("username");
        Button button4 = findViewById(R.id.upload);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        ImageView imageView = findViewById(R.id.imageView47);
        Button button = findViewById(R.id.submit);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedImageUri != null) {
                    try {
                        ImageDecoder.Source source = ImageDecoder.createSource(getContentResolver(), selectedImageUri);
                        Bitmap bitmap = ImageDecoder.decodeBitmap(source);

                        String base64Image = convertBitmapToBase64(bitmap);

                        // Execute AsyncTask to send data to the server
                        new SendDataToServer().execute(username, selectedImageUri.toString(), base64Image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(profimg.this, "Please select an image first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            ImageView imageView = findViewById(R.id.imageView47);
            imageView.setImageURI(selectedImageUri);
        }
    }

    private class SendDataToServer extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String username = params[0];
                String imageUriString = params[1];
                String base64Image = params[2];
                // Create a JSON object
                JSONObject jsonParams = new JSONObject();
                jsonParams.put("username", username);
                jsonParams.put("base64image", base64Image);

                // Convert JSON object to string
                String jsonData = jsonParams.toString();

                URL url = new URL(ipforall.ipt + "uploadimg.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                urlConnection.setDoOutput(true);

                // Write JSON data to the server
                try (OutputStream os = urlConnection.getOutputStream()) {
                    byte[] input = jsonData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                // Get the response from the server
                InputStream inputStream = urlConnection.getInputStream();
                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(profimg.this, "Profile Picture Updated", Toast.LENGTH_SHORT).show();
                    return "Data inserted successfully";
                } else {
                    Toast.makeText(profimg.this, "Failed To Update Profile Picture", Toast.LENGTH_SHORT).show();
                    return "Failed to insert data";
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null && result.equals("Data inserted successfully")) {

                Intent intent = new Intent(profimg.this, profile.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        }
    }
}