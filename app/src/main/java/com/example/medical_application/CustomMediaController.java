package com.example.medical_application;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;

import com.example.medical_application.R;

public class CustomMediaController extends MediaController {

    public CustomMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
        initView(context);
    }

    public CustomMediaController(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        View customMediaController = LayoutInflater.from(context).inflate(R.layout.custom_media_controller, this);
        // Customize your media controller layout as needed
    }
}