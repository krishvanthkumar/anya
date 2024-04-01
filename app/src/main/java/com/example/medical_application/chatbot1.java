package com.example.medical_application;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

public class chatbot1 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private RecyclerView recyclerView;
    private ChatAdapter adapter;

    public chatbot1() {
        // Required empty public constructor
    }

    public static chatbot1 newInstance(String param1, String param2) {
        chatbot1 fragment = new chatbot1();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_chatbot1, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupRecyclerView();
        return rootView;
    }

    private void setupRecyclerView() {
        List<String> messages = new ArrayList<>();
        List<String> options = new ArrayList<>();
        // Adding initial message
        messages.add("How can I help you today?");
        // Adding initial options
        options.add("Emotions, Mood Swings, Mental Health");
        options.add("Newborn Care Sleep");
        options.add("Relationship Support");
        options.add("Physical Recovery");
        options.add("Breastfeeding");
        options.add("Things to do when you are feeling stressed");

        adapter = new ChatAdapter(getContext(), messages, options);
        recyclerView.setAdapter(adapter);
    }
}
