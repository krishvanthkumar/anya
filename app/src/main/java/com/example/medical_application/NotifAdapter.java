package com.example.medical_application;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.medical_application.R;

import java.util.List;

public class NotifAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] notificationTitles;
    private String[] notificationMessages;

    public NotifAdapter(Context context, String[] notificationTitles, String[] notificationMessages) {
        super(context, R.layout.notif1, notificationTitles);
        this.context = context;
        this.notificationTitles = notificationTitles;
        this.notificationMessages = notificationMessages;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        ViewHolder viewHolder;

        if (rowView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            rowView = inflater.inflate(R.layout.notif1 ,parent, false);

            viewHolder = new ViewHolder();
            viewHolder.titleTextView = rowView.findViewById(R.id.title);
            viewHolder.messageTextView = rowView.findViewById(R.id.content);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.titleTextView.setText(notificationTitles[position]);
        viewHolder.messageTextView.setText(notificationMessages[position]);

        return rowView;
    }

    @Override
    public int getCount() {
        return notificationTitles.length;
    }

    static class ViewHolder {
        TextView titleTextView;
        TextView messageTextView;
    }
}
