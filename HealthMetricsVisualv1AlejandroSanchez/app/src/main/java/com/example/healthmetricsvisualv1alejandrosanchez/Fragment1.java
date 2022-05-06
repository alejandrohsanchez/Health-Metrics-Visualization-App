package com.example.healthmetricsvisualv1alejandrosanchez;

import android.app.FragmentTransaction;
import android.content.Intent;
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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Fragment1 extends Fragment {
// On startup, insert new data after deleting the entry where the date matches the current date
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Define
        DatabaseHelper databaseHelper;
        TextView name, age, weight, height1, height2, currentWater;
        TextView currentCalorie, currentWorkout;
        EditText newCalorie, newWorkout;
        Button addButton, subtractButton, updateCalorieButton, updateWorkoutButton;

        // From fragment 2
        ProgressBar waterProgressBar, calorieProgressBar, workoutProgressBar;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment1_layout, container, false);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        // Calendars for each graph
        Calendar cal = Calendar.getInstance();

        // Update current data
        String todayDate = format.format(cal.getTime());
        databaseHelper.replaceWaterTrackerDateEntry(todayDate);
        databaseHelper.replaceCalorieTrackerDateEntry(todayDate);
        databaseHelper.replaceWorkoutTrackerDateEntry(todayDate);


        // Assign Text Views
        name = (TextView) rootView.findViewById(R.id.wellness_user_name);
        age = (TextView) rootView.findViewById(R.id.wellness_user_age);
        weight = (TextView) rootView.findViewById(R.id.wellness_user_weight);
        height1 = (TextView) rootView.findViewById(R.id.wellness_user_height_1);
        height2 = (TextView) rootView.findViewById(R.id.wellness_user_height_2);
        currentWater = (TextView) rootView.findViewById(R.id.wellness_user_water);
        currentCalorie = (TextView) rootView.findViewById(R.id.wellness_user_calorie);
        currentWorkout = (TextView) rootView.findViewById(R.id.wellness_user_workout);

        // Assign Edit Text
        newCalorie = (EditText) rootView.findViewById(R.id.wellness_user_edit_calories);
        newWorkout = (EditText) rootView.findViewById(R.id.wellness_user_edit_workout);

        // Assign Progress Bars
        waterProgressBar = (ProgressBar) rootView.findViewById(R.id.waterProgressBar);
        calorieProgressBar = (ProgressBar) rootView.findViewById(R.id.calorieProgressBar);
        workoutProgressBar = (ProgressBar) rootView.findViewById(R.id.workoutProgressBar);

        // Attach Buttons
        addButton = rootView.findViewById(R.id.wellness_add_water_button);
        subtractButton = rootView.findViewById(R.id.wellness_subtract_water_button);
        updateCalorieButton = rootView.findViewById(R.id.wellness_user_edit_calories_update);
        updateWorkoutButton = rootView.findViewById(R.id.wellness_user_edit_workout_update);

        // Get current values in database
        String user_name, user_age, user_weight, user_current_water, user_current_calorie, user_current_workout;
        user_name = databaseHelper.getUsername();
        user_age = databaseHelper.getUserAge();
        user_weight = databaseHelper.getUserWeight();
        user_current_water = Integer.toString(databaseHelper.getUserCurrentWater());
        user_current_calorie = Integer.toString((int) databaseHelper.getUserCurrentCalorie());
        user_current_workout = Integer.toString(databaseHelper.getUserCurrentWorkout());

        // Variables and placeholders
        int waterGoal = databaseHelper.getUserGoalWater();
        double calorieGoal = databaseHelper.getUserGoalCalorie();
        int workoutGoal = databaseHelper.getUserGoalWorkout();
        double[] heightDataArray = databaseHelper.getUserHeight();
        String goal = "";


        // Calculate progress values
        double waterProgressValue, calorieProgressValue, workoutProgressValue;
        waterProgressValue = ((double)databaseHelper.getUserCurrentWater() / (double)databaseHelper.getUserGoalWater()) * 100;
        calorieProgressValue = (databaseHelper.getUserCurrentCalorie() / databaseHelper.getUserGoalCalorie()) * 100;
        workoutProgressValue = ((double)databaseHelper.getUserCurrentWorkout() / (double)databaseHelper.getUserGoalWorkout()) * 100;

        // Update what is on the page
        name.setText(user_name);
        age.setText(user_age);
        weight.setText(user_weight);
        height1.setText(Integer.toString((int) heightDataArray[0]));
        height2.setText(Integer.toString((int) heightDataArray[1]));
        currentWater.setText(user_current_water + "/" + waterGoal);
        currentCalorie.setText(user_current_calorie + "/" + (int) calorieGoal);
        currentWorkout.setText(user_current_workout + "/" + workoutGoal);

        waterProgressBar.setProgress((int)waterProgressValue);
        calorieProgressBar.setProgress((int)calorieProgressValue);
        workoutProgressBar.setProgress((int)workoutProgressValue);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the current value of water intake
                int current_val = databaseHelper.getUserCurrentWater() + 1;
                // Set the new value into the database
                databaseHelper.setUserCurrentWater(current_val);
                // Update the text
                currentWater.setText(current_val + "/" + waterGoal);

                databaseHelper.replaceWaterTrackerDateEntry(todayDate);

                double progress = ((double)databaseHelper.getUserCurrentWater() / (double)databaseHelper.getUserGoalWater()) * 100;
                waterProgressBar.setProgress((int)progress);
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_val = databaseHelper.getUserCurrentWater() - 1;
                databaseHelper.setUserCurrentWater(current_val);
                currentWater.setText(current_val + "/" + waterGoal);

                databaseHelper.replaceWaterTrackerDateEntry(todayDate);

                double progress = ((double)databaseHelper.getUserCurrentWater() / (double)databaseHelper.getUserGoalWater()) * 100;
                waterProgressBar.setProgress((int)progress);
            }
        });

        updateCalorieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double updateCalories = Double.parseDouble(newCalorie.getText().toString());
                double current_val = updateCalories;
                databaseHelper.setUserCurrentCalorie(current_val);
                currentCalorie.setText((int)current_val + "/" + (int) calorieGoal);

                databaseHelper.replaceCalorieTrackerDateEntry(todayDate);

                double progress = (databaseHelper.getUserCurrentCalorie() / databaseHelper.getUserGoalCalorie()) * 100;
                calorieProgressBar.setProgress((int)progress);
            }
        });

        updateWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int updateWorkout = Integer.parseInt(newWorkout.getText().toString());
                int current_val = updateWorkout;
                databaseHelper.setUserCurrentWorkout(current_val);
                currentWorkout.setText(current_val + "/" + workoutGoal);

                databaseHelper.replaceWorkoutTrackerDateEntry(todayDate);

                double progress = ((double)databaseHelper.getUserCurrentWorkout() / (double)databaseHelper.getUserGoalWorkout()) * 100;
                workoutProgressBar.setProgress((int)progress);
            }
        });

        return rootView;
    }
}
