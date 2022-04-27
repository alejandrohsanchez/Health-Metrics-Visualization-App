package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.healthmetricsvisualv1alejandrosanchez.ui.main.SectionsPagerAdapter;
import com.example.healthmetricsvisualv1alejandrosanchez.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    private ActivityMain2Binding binding;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = binding.viewPager;
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = binding.tabs;
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = binding.fab;
        View welcomeMessage = findViewById(R.id.welcomeText);

        name = findViewById(R.id.wellness_user_name);
        age = findViewById(R.id.wellness_user_age);
        weight = findViewById(R.id.wellness_user_weight);

        databaseHelper = new DatabaseHelper(MainActivity2.this);

        String user_name, user_age, user_weight;
        user_name = databaseHelper.getUsername();
        user_age = databaseHelper.getUserAge();
        user_weight = databaseHelper.getUserWeight();

        name.setText(user_name);
        age.setText(user_age);
        weight.setText(user_weight);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
    }
}