package com.example.medical_application;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class audioplayer extends AppCompatActivity {
    private String aid;
    private MediaPlayer mediaPlayer;
    private Button playPauseButton;
    private SeekBar seekBar;
    private boolean isPlaying = false;
    private String audioPath;
    private int currentAudioPosition = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audioplayer);

        Intent intent = getIntent();
        aid = intent.getStringExtra("id");
        fetchData(aid);
        mediaPlayer = new MediaPlayer();
        playPauseButton = findViewById(R.id.playPauseButton);
        seekBar = findViewById(R.id.seekBar);

        playPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isPlaying) {
                    if (currentAudioPosition > 0) {
                        resumeAudio(); // Resume from the saved position
                    } else {
                        fetchData(aid); // Otherwise fetch and play the audio from the beginning
                    }
                } else {
                    pauseAudio();
                }
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isPlaying = false;
                playPauseButton.setText("Play");
                seekBar.setProgress(0);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Not needed for this implementation
            }
        });


    }

    private void fetchData(String aid) {
        String apiUrl = ipforall.ipt +"audioplay.php";

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
                data.put("id", aid);
                return data;
            }
        };

        Volley.newRequestQueue(this).add(stringRequest);
    }

    private void handleResponse(String response) {
        try {
            String[] values = response.split("/");

            if (values.length > 1) {
                audioPath = ipforall.ipt + "uploads/" + values[6].substring(0, values[6].length() - 2);
                playAudio(audioPath);

            } else {
                Log.e("handleResponse", "Unexpected response format: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("handleResponse", "Error processing response: " + e.getMessage());
        }
    }

    private void handleError(VolleyError error) {
        Log.e("Error", "Volley Error: " + error.toString());
    }

    private void playAudio(String audioPath) {
        try {
            if (mediaPlayer != null) {
                mediaPlayer.reset(); // Resetting the MediaPlayer to its Idle state
                mediaPlayer.setDataSource(audioPath);
                mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        mediaPlayer.start(); // Starting playback after preparing
                        isPlaying = true;
                        playPauseButton.setText("Pause");
                        seekBar.setMax(mediaPlayer.getDuration());
                        updateSeekBar();
                    }
                });
                mediaPlayer.prepareAsync(); // Asynchronously prepare the MediaPlayer
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("AudioPlayer", "Error playing audio: " + e.getMessage());
        }
    }


    private void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            currentAudioPosition = mediaPlayer.getCurrentPosition(); // Save the current position
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setText("Play");
        }
    }
    private void resumeAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(currentAudioPosition); // Seek to the saved position
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setText("Pause");
            updateSeekBar();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());
            if (mediaPlayer.isPlaying()) {
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        updateSeekBar();
                    }
                };
                seekBar.postDelayed(runnable, 1000);
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
