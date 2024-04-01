package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class guardian_home extends AppCompatActivity {

    private TextView pname, pusername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guardian_home);

        String username = getIntent().getStringExtra("username");
        System.out.println(username);
        pname = findViewById(R.id.name);
        pusername = findViewById(R.id.pid);
        fetchData2(username);
        fetchData(username);
        fetchData1(username);
        fetchData3(username);
        FrameLayout bt1 = findViewById(R.id.act5);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent( getApplicationContext(), gmood.class);
                i1.putExtra("username",username);
                startActivity(i1);
            }
        });

    }

    private void fetchData(String username) {
        String apiUrl = ipforall.ipt + "graphdata2.php";

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

        try {
            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(response);

            // Create an array to store the 'total' values
            int[] totals = new int[jsonArray.length()];

            // Extract the 'total' values from the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!(jsonArray.getString(i).equals("null"))) {
                    totals[i] = jsonArray.getInt(i);
                }
            }

            // Now you have the 'total' values in the 'totals' array
            // You can use these values to populate your graph
            GraphView graphView = findViewById(R.id.mood);
            DataPoint[] dataPoints = new DataPoint[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                dataPoints[i] = new DataPoint(i + 1, totals[i]);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(8);

            // Customize the viewport if needed
            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(0); // Set your min Y value
            graphView.getViewport().setMaxY(4);
            graphView.getViewport().setMinX(0); // Set your min Y value
            graphView.getViewport().setMaxX(jsonArray.length());
            graphView.addSeries(series);

            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (!isValueX) {
                        switch ((int) value) {
                            case 0:
                                return "\uD83D\uDE1E"; // Emoji for value 0 (Sad)
                            case 1:
                                return "\uD83D\uDE21"; // Emoji for value 1 (Angry)
                            case 2:
                                return "\uD83D\uDE42"; // Emoji for value 2 (Happy)
                            case 3:
                                return "\uD83D\uDE24"; // Emoji for value 3 (Irritated)
                            case 4:
                                return "\uD83D\uDE03"; // Emoji for value 4 (Tired)
                            default:
                                return super.formatLabel(value, isValueX);
                        }
                    } else {
                        // If x-value, use default formatting
                        return super.formatLabel(value, isValueX);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }

    private void fetchData1(String username) {
        String apiUrl = ipforall.ipt + "graphdata.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
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

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleResponse1(String response) {
        Log.d("JSON Response", response);

        try {
            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(response);

            // Create an array to store the 'total' values
            int[] totals = new int[jsonArray.length()];

            // Extract the 'total' values from the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!(jsonArray.getString(i).equals("null"))) {
                    totals[i] = jsonArray.getInt(i);
                }
            }

            // Now you have the 'total' values in the 'totals' array
            // You can use these values to populate your graph
            GraphView graphView = findViewById(R.id.test);
            DataPoint[] dataPoints = new DataPoint[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                dataPoints[i] = new DataPoint(i + 1, totals[i]);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(8);

            // Customize the viewport if needed
            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(0); // Set your min Y value
            graphView.getViewport().setMaxY(30);
            graphView.getViewport().setMinX(0); // Set your min Y value
            graphView.getViewport().setMaxX(jsonArray.length());
            graphView.addSeries(series);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError1(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }

    private void fetchData2(String username) {
        String apiUrl = ipforall.ipt + "profile.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
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

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleResponse2(String response) {
        Log.d("JSON Response", response);
        System.out.println(response);

        String[] values = response.split(", ");
        System.out.println("1 " + values[0]);

        pname.setText(values[0].substring(1, values[0].length()));
        pusername.setText(values[1].substring(0, values[1].length() - 1));

    }

    private void handleError2(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }
    private void fetchData3(String username) {
        String apiUrl = ipforall.ipt + "graphdata3.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, apiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        handleResponse3(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                handleError3(error);
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

    private void handleResponse3(String response) {
        Log.d("JSON Response", response);

        try {
            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(response);

            // Create an array to store the 'total' values
            int[] totals = new int[jsonArray.length()];

            // Extract the 'total' values from the JSON array
            for (int i = 0; i < jsonArray.length(); i++) {
                if (!(jsonArray.getString(i).equals("null"))) {
                    totals[i] = jsonArray.getInt(i);
                }
            }

            // Now you have the 'total' values in the 'totals' array
            // You can use these values to populate your graph
            GraphView graphView = findViewById(R.id.gm);
            DataPoint[] dataPoints = new DataPoint[jsonArray.length()];
            for (int i = 0; i < jsonArray.length(); i++) {
                dataPoints[i] = new DataPoint(i + 1, totals[i]);
            }

            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataPoints);
            series.setColor(Color.RED);
            series.setDrawDataPoints(true);
            series.setDataPointsRadius(8);

            // Customize the viewport if needed
            graphView.getViewport().setYAxisBoundsManual(true);
            graphView.getViewport().setMinY(0); // Set your min Y value
            graphView.getViewport().setMaxY(4);
            graphView.getViewport().setMinX(0); // Set your min Y value
            graphView.getViewport().setMaxX(jsonArray.length());
            graphView.addSeries(series);

            graphView.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (!isValueX) {
                        switch ((int) value) {
                            case 0:
                                return "\uD83D\uDE1E"; // Emoji for value 0 (Sad)
                            case 1:
                                return "\uD83D\uDE21"; // Emoji for value 1 (Angry)
                            case 2:
                                return "\uD83D\uDE42"; // Emoji for value 2 (Happy)
                            case 3:
                                return "\uD83D\uDE24"; // Emoji for value 3 (Irritated)
                            case 4:
                                return "\uD83D\uDE03"; // Emoji for value 4 (Tired)
                            default:
                                return super.formatLabel(value, isValueX);
                        }
                    } else {
                        // If x-value, use default formatting
                        return super.formatLabel(value, isValueX);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void handleError3(VolleyError error) {
        Log.e("Volley Error", "Error: " + error.toString());
        System.out.println("boooooo");
    }
}
