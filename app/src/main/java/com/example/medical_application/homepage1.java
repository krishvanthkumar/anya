package com.example.medical_application;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link homepage1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class homepage1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView pname;
    private String username,tdate="",mdate="",intera,mood,sleep,mama;
    LocalDate parsedDate;
    LocalDate currentDate;
    long daysDifference;
    DateTimeFormatter formatter;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public homepage1() {
        // Required empty public constructor
    }
    public homepage1(String username) {
        this.username = username;
    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment homepage1.
     */
    // TODO: Rename and change types and number of parameters
    public static homepage1 newInstance(String param1, String param2) {
        homepage1 fragment = new homepage1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_homepage1, container, false);

        pname = view.findViewById(R.id.pname);

        fetchData(username);
        fetchData1(username);
        fetchData2(username);

        FrameLayout a1 = view.findViewById(R.id.act1);
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdate != "") {
                    System.out.println(mdate);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    parsedDate = LocalDate.parse(mdate, formatter);

                    // Current date
                    currentDate = LocalDate.now();

                    // Calculating the difference in days
                    daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                    if (daysDifference >= 1 || mood.equals("1")) {
                        Intent i2 = new Intent(getActivity(), moodanalysis.class);
                        i2.putExtra("username", username);
                        startActivity(i2);
                    }
                    else{
                        System.out.println(mdate);
                        System.out.println(daysDifference);
                        Log.d("CardView", "Displaying card view dialog");
                        Dialog myDialog = new Dialog(requireActivity());
                        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Inflate the cardview.xml layout
                        myDialog.setContentView(R.layout.cardview);

                        // Set background transparent
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // Show the dialog
                        myDialog.show();
                    }
                }
            }
        });

        FrameLayout a2 = view.findViewById(R.id.act2);
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdate != "") {
                    System.out.println(mdate);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    parsedDate = LocalDate.parse(mdate, formatter);

                    // Current date
                    currentDate = LocalDate.now();

                    // Calculating the difference in days
                    daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                    if (daysDifference >= 1 || sleep.equals("1")) {
                        Intent i2 = new Intent(getActivity(), sleeping.class);
                        i2.putExtra("username", username);
                        startActivity(i2);
                    }
                    else{
                        System.out.println(mdate);
                        System.out.println(daysDifference);
                        Log.d("CardView", "Displaying card view dialog");
                        Dialog myDialog = new Dialog(requireActivity());
                        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Inflate the cardview.xml layout
                        myDialog.setContentView(R.layout.cardview);

                        // Set background transparent
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // Show the dialog
                        myDialog.show();
                    }
                }

            }
        });

        FrameLayout a3 = view.findViewById(R.id.act3);
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mdate != "") {
                    System.out.println(mdate);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    parsedDate = LocalDate.parse(mdate, formatter);

                    // Current date
                    currentDate = LocalDate.now();

                    // Calculating the difference in days
                    daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                    if (daysDifference >= 1 || intera.equals("1")) {
                        Intent i3 = new Intent(getActivity(), interaction.class);
                        i3.putExtra("username", username);
                        startActivity(i3);
                    }
                    else{
                        System.out.println(mdate);
                        System.out.println(daysDifference);
                        Log.d("CardView", "Displaying card view dialog");
                        Dialog myDialog = new Dialog(requireActivity());
                        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Inflate the cardview.xml layout
                        myDialog.setContentView(R.layout.cardview);

                        // Set background transparent
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // Show the dialog
                        myDialog.show();
                    }

                }

            }
        });

        FrameLayout a4 = view.findViewById(R.id.act4);
        a4.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (mdate != "") {
                    System.out.println(mdate);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    parsedDate = LocalDate.parse(mdate, formatter);

                    // Current date
                    currentDate = LocalDate.now();

                    // Calculating the difference in days
                    daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                    if (daysDifference >= 1 || mama.equals("1")) {
                        Intent i3 = new Intent(getActivity(), mamaandme.class);
                        i3.putExtra("username", username);
                        startActivity(i3);
                    }
                    else{
                        System.out.println(mdate);
                        System.out.println(daysDifference);
                        Log.d("CardView", "Displaying card view dialog");
                        Dialog myDialog = new Dialog(requireActivity());
                        myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                        // Inflate the cardview.xml layout
                        myDialog.setContentView(R.layout.cardview);

                        // Set background transparent
                        myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                        // Show the dialog
                        myDialog.show();
                    }

                }

            }
        });





        FrameLayout a5 = view.findViewById(R.id.act5);
        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tdate != "") {
                    System.out.println(tdate);
                    formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    parsedDate = LocalDate.parse(tdate, formatter);

                    // Current date
                    currentDate = LocalDate.now();

                    // Calculating the difference in days
                    daysDifference = ChronoUnit.DAYS.between(parsedDate, currentDate);

                }
                if (daysDifference > 3) {
                    Intent i5 = new Intent(getActivity(), testqn1.class);
                    i5.putExtra("username", username);
                    startActivity(i5);
                } else {
                    System.out.println(tdate);
                    System.out.println(daysDifference);
                    Log.d("CardView", "Displaying card view dialog");
                    Dialog myDialog = new Dialog(requireActivity());
                    myDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    // Inflate the cardview.xml layout
                    myDialog.setContentView(R.layout.cardview);

                    // Set background transparent
                    myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                    // Show the dialog
                    myDialog.show();
                }
            }
        });

        ImageView a6 = view.findViewById(R.id.menu);
        a6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i6 = new Intent(getActivity(), menubar.class);
                i6.putExtra("username", username);
                startActivity(i6);
            }
        });
        return view;
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

        Volley.newRequestQueue(requireContext()).add(stringRequest);

    }

    private void handleResponse(String response) {
        Log.d("JSON Response", response);
        System.out.println(response);

        String[] values = response.split(", ");
        System.out.println("1 " + values[0]);

        pname.setText("Hello "+values[0].substring(1, values[0].length())+",");


    }

    private void handleError(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }
    private void fetchData1(String username) {
        String apiUrl1 = ipforall.ipt+"testtime.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse1(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError1(error);
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

    private void handleResponse1(String response) {
        Log.d("JSON Response", response);
        System.out.println(response);

        String[] values = response.split(", ");
        System.out.println("1 " + values[0]);

        if (values[0].substring(1, values[0].length()-1).equals("0")){
            System.out.println("1 " + values[0]);
        }
        else {
            tdate = values[0].substring(1, values[0].length());
        }
    }

    private void handleError1(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
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
            intera = values[3]+"1";
            mama= values[1]+"1";
            mood =values[2]+"1";
            sleep=values[4]+"1";
            System.out.println(mdate+"kk"+mama+"kk"+mood+"kk"+intera+"kk"+sleep);
        }
    }

    private void handleError2(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }
}