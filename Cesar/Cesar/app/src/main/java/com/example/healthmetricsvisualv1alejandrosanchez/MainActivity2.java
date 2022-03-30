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
import android.widget.Toast;

import com.example.healthmetricsvisualv1alejandrosanchez.ui.main.SectionsPagerAdapter;
import com.example.healthmetricsvisualv1alejandrosanchez.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {
    DatabaseHelper databaseHelper;

    private ActivityMain2Binding binding;

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

        setContentView(R.layout.fragment1_layout);
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(MainActivity2.this);
        String user_name = databaseHelper.getUsername();
        TextView name = (TextView) findViewById(R.id.textView4);
        name.setText("hi " +  user_name);
        DatabaseHelper databaseWeight;
        databaseWeight = new DatabaseHelper(MainActivity2.this);
        double user_weight = databaseHelper.getWeight();
        TextView weight = (TextView) findViewById(R.id.wellnessweight);
        weight.setText("Weight: " + user_weight);

        double usercalories = 0;
        TextView calories = (TextView) findViewById(R.id.wellnessCalories);
        calories.setText("Calories: " + usercalories);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

            }
        });
    }
}