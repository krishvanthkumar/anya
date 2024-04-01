package com.example.medical_application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class patientadapter2 extends RecyclerView.Adapter<patientadapter2.ViewHolder>{
private Context context;
private List<Patient> patientList;
private OnItemClickListener onItemClickListener;

public interface OnItemClickListener {
    void onItemClick(int position);
}

    public patientadapter2(Context context, List<Patient> patients) {
        this.context = context;
        this.patientList = patients;
    }

// ViewHolder class
public static class ViewHolder extends RecyclerView.ViewHolder {
    TextView nameTextView;

    ImageView image;
    public ViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.textView40);
        image = itemView.findViewById(R.id.imageView10);

    }

}

    // Override onCreateViewHolder
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient2, parent, false);
        return new ViewHolder(view);
    }

    // Override onBindViewHolder
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Patient patient = patientList.get(position);
        holder.nameTextView.setText(patient.getName());
        if(patient.getdp()!= null && !patient.getdp().isEmpty()){
            byte[] decodedImageBytes = Base64.decode(patient.getdp(),Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.length);
            holder.image.setImageBitmap(decodedBitmap);
        }

        // Set item click listener
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    // Override getItemCount
    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}