package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.healthmetricsvisualv1alejandrosanchez.R;
import com.example.healthmetricsvisualv1alejandrosanchez.databinding.ActivityMain2Binding;
import com.example.healthmetricsvisualv1alejandrosanchez.ui.main.SectionsPagerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

public class Fragment1 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(Fragment1.this);
        String user_name = databaseHelper.getUsername();
        TextView name = (TextView) findViewById(R.id.textView4);
        name.setText("hi " +  user_name);

        DatabaseHelper databaseWeight;
        databaseWeight = new DatabaseHelper(Fragment1.this);
        double user_weight = databaseHelper.getWeight();
        TextView weight = (TextView) findViewById(R.id.wellnessweight);
        weight.setText("Weight: " + user_weight);

        double usercalories = 0;
        TextView calories = (TextView) findViewById(R.id.wellnessCalories);
        calories.setText("Calories: " + usercalories);
        return inflater.inflate(R.layout.fragment1_layout, container, false);
    }


    }
