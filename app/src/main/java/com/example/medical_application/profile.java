package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.util.HashMap;
import java.util.Map;

public class profile extends AppCompatActivity {

    private TextView pname, pusername;
    private String username ;
    private ImageView bt1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        pname = findViewById(R.id.name);
        pusername = findViewById(R.id.username1);

        fetchData(username);
        bt1 = findViewById(R.id.profimg);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), profimg.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });

    }

    private void fetchData(String username) {
        String apiUrl = ipforall.ipt+"profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> data = new HashMap<>();
                data.put("username", username);
                Log.d("Volley Request", "Params: " + data.toString());
                return data;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleResponse(String response) {
        Log.d("JSON Response", response);
        System.out.println(response);

        String[] values = response.split(", ");
        System.out.println("1 " + values[0]);

        pname.setText(values[0].substring(1, values[0].length()));
        pusername.setText(values[1].substring(0, values[1].length()));
        String base64Image = values[2];
        if(base64Image!= null && !base64Image.isEmpty()){
            byte[] decodedImageBytes = Base64.decode(base64Image,Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.length);
            bt1.setImageBitmap(decodedBitmap);
        }
    }

    private void handleError(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }




}
