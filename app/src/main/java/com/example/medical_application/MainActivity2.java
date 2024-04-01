package com.example.medical_application;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.medical_application.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String username = getIntent().getStringExtra("username");
        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new homepage1(username));

        binding.bottomNavigationView.setOnItemSelectedListener(item ->{

            int t1=item.getItemId();

            if (t1 == R.id.home){
                replaceFragment(new homepage1(username));

            }
            if (t1==R.id.chat){
                replaceFragment(new chatbot1());
            }
            if (t1==R.id.notif){
                replaceFragment(new notification1(username));
            }
            if (t1==R.id.stress){
                replaceFragment(new stress1());
            }

            return true ;
        });
    }
    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManger = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManger.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}