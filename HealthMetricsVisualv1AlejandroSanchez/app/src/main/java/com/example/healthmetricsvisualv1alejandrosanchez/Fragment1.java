package com.example.healthmetricsvisualv1alejandrosanchez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmetricsvisualv1alejandrosanchez.R;

import org.w3c.dom.Text;

public class Fragment1 extends Fragment {
    DatabaseHelper databaseHelper;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView name, age, weight, height1, height2, currentWater;
        Button addButton, subtractButton;
        View rootView = inflater.inflate(R.layout.fragment1_layout, container, false);

        databaseHelper = new DatabaseHelper(getActivity());

        // Text Views
        name = (TextView) rootView.findViewById(R.id.wellness_user_name);
        age = (TextView) rootView.findViewById(R.id.wellness_user_age);
        weight = (TextView) rootView.findViewById(R.id.wellness_user_weight);
        height1 = (TextView) rootView.findViewById(R.id.wellness_user_height_1);
        height2 = (TextView) rootView.findViewById(R.id.wellness_user_height_2);
        currentWater = (TextView) rootView.findViewById(R.id.wellness_user_water);
        addButton = rootView.findViewById(R.id.wellness_add_water_button);
        subtractButton = rootView.findViewById(R.id.wellness_subtract_water_button);

        // Variables and placeholders
        String user_name, user_age, user_weight, user_current_water;
        user_name = databaseHelper.getUsername();
        user_age = databaseHelper.getUserAge();
        user_weight = databaseHelper.getUserWeight();

        user_current_water = Integer.toString(databaseHelper.getUserCurrentWater());

        double[] heightDataArray = databaseHelper.getUserHeight();

        name.setText(user_name);
        age.setText(user_age);
        weight.setText(user_weight);
        height1.setText(Integer.toString((int) heightDataArray[0]));
        height2.setText(Integer.toString((int) heightDataArray[1]));
        currentWater.setText(user_current_water);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current value of water intake
                int current_val = databaseHelper.getUserCurrentWater() + 1;
                // Set the new value into the database
                databaseHelper.setUserCurrentWater(current_val);
                // Update the text
                currentWater.setText(current_val);
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_val = databaseHelper.getUserCurrentWater() - 1;
                databaseHelper.setUserCurrentWater(current_val);
                currentWater.setText(current_val);
            }
        });
        return rootView;
    }


}
