package com.example.medical_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class videosAdapter extends BaseAdapter {
    private Context context;
    private List<Videos> videoList;
    private videosAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public videosAdapter(Context context, List<Videos> videos) {
        this.context = context;
        this.videoList = videos;
    }

    @Override
    public int getCount() {
        return videoList.size();
    }

    @Override
    public Object getItem(int position) {
        return videoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.evideo, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.textView35);


        Videos video = videoList.get(position);
        nameTextView.setText(video.getName());


        // Set item click listener
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        return convertView;
    }

    public void setOnItemClickListener(videosAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
