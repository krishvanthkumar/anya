package com.example.medical_application;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class audioAdapter extends BaseAdapter {

    private Context context;
    private List<musics> audioList;
    private audioAdapter.OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public audioAdapter(Context context, List<musics> audios) {
        this.context = context;
        this.audioList = audios;
    }

    @Override
    public int getCount() {
        return audioList.size();
    }

    @Override
    public Object getItem(int position) {
        return audioList.get(position);
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


        musics music = audioList.get(position);
        nameTextView.setText(music.getName());


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

    public void setOnItemClickListener(audioAdapter.OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
