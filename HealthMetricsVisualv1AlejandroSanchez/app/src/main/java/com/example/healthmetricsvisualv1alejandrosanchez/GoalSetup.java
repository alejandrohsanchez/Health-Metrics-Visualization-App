package com.example.healthmetricsvisualv1alejandrosanchez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class GoalSetup extends AppCompatActivity {

    EditText water_goal, weight_goal, calorie_goal, workout_goal;
    Button createButton;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal_setup);

        createButton = findViewById(R.id.goal_setup_button);
        water_goal = findViewById(R.id.water_intake_goal);
        weight_goal = findViewById(R.id.weight_goal);
        calorie_goal = findViewById(R.id.calorie_goal);
        workout_goal = findViewById(R.id.workout_goal);

        databaseHelper = new DatabaseHelper(GoalSetup.this);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int waterGoal, workoutGoal;
                double weightGoal, calorieGoal;

                waterGoal = Integer.parseInt(water_goal.getText().toString());
                weightGoal = Double.parseDouble(weight_goal.getText().toString());
                calorieGoal = Double.parseDouble(calorie_goal.getText().toString());
                workoutGoal = Integer.parseInt(workout_goal.getText().toString());
                // Implement setter for goals here
                databaseHelper.setUserGoalWater(waterGoal);
                databaseHelper.setUserGoalWeight(weightGoal);
                databaseHelper.setUserGoalCalorie(calorieGoal);
                databaseHelper.setUserGoalWorkout(workoutGoal);
                // Go to next page
                openMainActivity2();
            }
        });
    }

    public void openMainActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }
}