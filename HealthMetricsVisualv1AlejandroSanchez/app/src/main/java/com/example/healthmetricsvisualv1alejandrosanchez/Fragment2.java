package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.healthmetricsvisualv1alejandrosanchez.R;

public class Fragment2 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Define
        DatabaseHelper databaseHelper;
        TextView water_goal, calorie_goal, workout_goal;
        EditText new_water_goal, new_calorie_goal, new_workout_goal;
        Button updateWater, updateCalorie, updateWorkout;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment2_layout, container, false);

        // Assign Text Views
        water_goal = (TextView) rootView.findViewById(R.id.goal_currentWater);
        calorie_goal = (TextView) rootView.findViewById(R.id.goal_currentCalorie);
        workout_goal = (TextView) rootView.findViewById(R.id.goal_currentWorkout);

        // Assign Edit Text
        new_water_goal = (EditText) rootView.findViewById(R.id.goal_edit_goalWater);
        new_calorie_goal = (EditText) rootView.findViewById(R.id.goal_edit_goalCalorie);
        new_workout_goal = (EditText) rootView.findViewById(R.id.goal_edit_goalWorkout);

        // Assign Buttons
        updateWater = rootView.findViewById(R.id.update_goalWater_button);
        updateCalorie = rootView.findViewById(R.id.update_goalCalorie_button);
        updateWorkout = rootView.findViewById(R.id.update_goalWorkout_button);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Update what is on the page
        water_goal.setText(Integer.toString(databaseHelper.getUserGoalWater()));
        calorie_goal.setText(Integer.toString((int)databaseHelper.getUserGoalCalorie()));
        workout_goal.setText(Integer.toString(databaseHelper.getUserGoalWorkout()));

        // Update values in database
        updateWater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updateWater = Integer.parseInt(new_water_goal.getText().toString());
                int current_val = updateWater;
                databaseHelper.setUserGoalWater(current_val);
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        updateCalorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double updateCalories = Double.parseDouble(new_calorie_goal.getText().toString());
                double current_val = updateCalories;
                databaseHelper.setUserGoalCalorie(current_val);
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        updateWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updateWorkout = Integer.parseInt(new_workout_goal.getText().toString());
                int current_val = updateWorkout;
                databaseHelper.setUserGoalWorkout(current_val);
                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        return rootView;
    }
}
