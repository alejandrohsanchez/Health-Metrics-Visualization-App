package com.example.healthmetricsvisualv1alejandrosanchez;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmetricsvisualv1alejandrosanchez.R;

import org.w3c.dom.Text;

public class Fragment1 extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Define
        DatabaseHelper databaseHelper;
        TextView name, age, weight, height1, height2, currentWater;
        TextView currentCalorie;
        EditText newCalorie;
        Button addButton, subtractButton, updateCalorieButton;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment1_layout, container, false);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Assign Text Views
        name = (TextView) rootView.findViewById(R.id.wellness_user_name);
        age = (TextView) rootView.findViewById(R.id.wellness_user_age);
        weight = (TextView) rootView.findViewById(R.id.wellness_user_weight);
        height1 = (TextView) rootView.findViewById(R.id.wellness_user_height_1);
        height2 = (TextView) rootView.findViewById(R.id.wellness_user_height_2);
        currentWater = (TextView) rootView.findViewById(R.id.wellness_user_water);
        currentCalorie = (TextView) rootView.findViewById(R.id.wellness_user_calorie);

        // Assign Edit Text
        newCalorie = (EditText) rootView.findViewById(R.id.wellness_user_edit_calories);

        // Attach Buttons
        addButton = rootView.findViewById(R.id.wellness_add_water_button);
        subtractButton = rootView.findViewById(R.id.wellness_subtract_water_button);
        updateCalorieButton = rootView.findViewById(R.id.wellness_user_edit_calories_update);

        // Get current values in database
        String user_name, user_age, user_weight, user_current_water, user_current_calorie;
        user_name = databaseHelper.getUsername();
        user_age = databaseHelper.getUserAge();
        user_weight = databaseHelper.getUserWeight();
        user_current_water = Integer.toString(databaseHelper.getUserCurrentWater());
        user_current_calorie = Integer.toString(databaseHelper.getUserCurrentCalorie());

        // Variables and placeholders
        int waterGoal = databaseHelper.getUserGoalWater();
        int calorieGoal = databaseHelper.getUserGoalCalorie();
        double[] heightDataArray = databaseHelper.getUserHeight();
        String goal = "";

        // Update what is on the page
        name.setText(user_name);
        age.setText(user_age);
        weight.setText(user_weight);
        height1.setText(Integer.toString((int) heightDataArray[0]));
        height2.setText(Integer.toString((int) heightDataArray[1]));
        if (waterGoal == 0) {

        }
        currentWater.setText(user_current_water);
        currentCalorie.setText(user_current_calorie);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current value of water intake
                int current_val = databaseHelper.getUserCurrentWater() + 1;
                // Set the new value into the database
                databaseHelper.setUserCurrentWater(current_val);
                // Update the text
                currentWater.setText(current_val + "/" + current_val);
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_val = databaseHelper.getUserCurrentWater() - 1;
                databaseHelper.setUserCurrentWater(current_val);
                currentWater.setText(current_val + "/" + current_val);
            }
        });

        updateCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updateCalories = Integer.parseInt(newCalorie.getText().toString());
                int current_val = updateCalories;
                databaseHelper.setUserCurrentCalorie(current_val);
                currentCalorie.setText(current_val + "/" + current_val);
            }
        });
        return rootView;
    }


}
