package com.example.medical_application;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class notification1 extends Fragment {


    LocalDate parsedDate;
    LocalDate currentDate;
    long daysDifference;
    DateTimeFormatter formatter;
    ListView listView;
    String mdate=" ",username;

    List<String> notificationTitles = new ArrayList<>();
    List<String> notificationMessages = new ArrayList<>();
    public notification1() {
        // Required empty public constructor
    }
    public notification1(String username) {
        this.username = username;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification1, container, false);
        listView = view.findViewById(R.id.myListView);

        fetchData2(username);

        return view;
    }
    private void fetchData2(String username) {
        String apiUrl1 = ipforall.ipt+"datecheck.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse2(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError2(error);
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

        Volley.newRequestQueue(requireContext()).add(stringRequest);

    }

    private void handleResponse2(String response) {
        Log.d("JSON Response", response);
        System.out.println(response);

        String[] values = response.split(", ");
        System.out.println("1" + values[0]);

        if (values[0].substring(2, values[0].length()-1).equals("0")){
            System.out.println("1 " + values[0]);
        }
        else {
            mdate = values[0].substring(2, values[0].length());
            if (!mdate.equals(" ")) {
                System.out.println(mdate);
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                parsedDate = LocalDate.parse(mdate, formatter);

                // Current date
                currentDate = LocalDate.now();

                // Calculating the difference in days
                daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                if (daysDifference >= 0) {
                    notificationTitles.add("Reminder");
                    notificationMessages.add("Don't forget about the upcoming tasks!");
                    NotifAdapter adapter = new NotifAdapter(requireContext(), notificationTitles.toArray(new String[0]), notificationMessages.toArray(new String[0]));

                    // Get reference to ListView


                    // Set adapter to ListView
                    listView.setAdapter(adapter);
                }

            }
        }
    }

    private void handleError2(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }
}