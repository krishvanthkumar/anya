package com.example.medical_application;

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

import java.util.List;

public class PatientAdapter extends BaseAdapter {

    private Context context;
    private List<Patient> patientList;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public PatientAdapter(Context context, List<Patient> patients) {
        this.context = context;
        this.patientList = patients;
    }

    @Override
    public int getCount() {
        return patientList.size();
    }

    @Override
    public Object getItem(int position) {
        return patientList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.epatient, parent, false);
        }

        TextView nameTextView = convertView.findViewById(R.id.textView40);
        TextView unameTextView = convertView.findViewById(R.id.pid);
        ImageView image= convertView.findViewById(R.id.imageView10);
        Patient patient = patientList.get(position);
        nameTextView.setText(patient.getName());
        unameTextView.setText(patient.getuser());
        if(patient.getdp()!= null && !patient.getdp().isEmpty()){
            byte[] decodedImageBytes = Base64.decode(patient.getdp(),Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedImageBytes, 0, decodedImageBytes.length);
            image.setImageBitmap(decodedBitmap);
        }

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
    public void setFilteredList(List<Patient> filteredList) {
        patientList = filteredList;
        notifyDataSetChanged();
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }


}
