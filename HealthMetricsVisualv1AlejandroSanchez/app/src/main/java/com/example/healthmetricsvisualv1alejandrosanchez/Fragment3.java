package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.madrapps.plot.line.LinePlot;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Define
        DatabaseHelper databaseHelper;
        TextView date;
        Button waterBarChart_previousWeekButton, waterBarChart_nextWeekButton, waterBarChart_todayButton;
        Button calorieBarChart_previousWeekButton, calorieBarChart_nextWeekButton, calorieBarChart_todayButton;
        Button workoutBarChart_previousWeekButton, workoutBarChart_nextWeekButton, workoutBarChart_todayButton;
        BarChart waterChart, calorieChart, workoutChart;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Assign Text Views
        date = (TextView) rootView.findViewById(R.id.dateTest);

        // Assign Buttons
        waterBarChart_previousWeekButton = rootView.findViewById(R.id.waterBarChart_prevWeek_button);
        waterBarChart_nextWeekButton = rootView.findViewById(R.id.waterBarChart_nextWeek_button);
        waterBarChart_todayButton = rootView.findViewById(R.id.waterBarChart_today_Button);
        calorieBarChart_previousWeekButton = rootView.findViewById(R.id.calorieBarChart_prevWeek_button);
        calorieBarChart_nextWeekButton = rootView.findViewById(R.id.calorieBarChart_nextWeek_button);
        calorieBarChart_todayButton = rootView.findViewById(R.id.calorieBarChart_today_Button);
        workoutBarChart_previousWeekButton = rootView.findViewById(R.id.workoutBarChart_prevWeek_button);
        workoutBarChart_nextWeekButton = rootView.findViewById(R.id.workoutBarChart_nextWeek_button);
        workoutBarChart_todayButton = rootView.findViewById(R.id.workoutBarChart_today_Button);


        // Assign graphs
        waterChart = rootView.findViewById(R.id.waterBarChart);
        calorieChart = rootView.findViewById(R.id.calorieBarChart);
        workoutChart = rootView.findViewById(R.id.workoutBarChart);

        // Getting today's date in custom format -----------
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat queryReturnFormat = new SimpleDateFormat("dd");

        // Create 3 variables to carry the current date, beginning, and the end of the week's date
        // for each graph
        String currentDate, beginWeek, endWeek;
        String calorieCurrentDate, calorieBeginWeek, calorieEndWeek;
        String workoutCurrentDate, workoutBeginWeek, workoutEndWeek;

        // Calendars for each graph
        Calendar cal = Calendar.getInstance();
        Calendar calorieCal = Calendar.getInstance();
        Calendar workoutCal = Calendar.getInstance();

        // Get the current date for each graph
        currentDate = format.format(cal.getTime());
        calorieCurrentDate = format.format(calorieCal.getTime());
        workoutCurrentDate = format.format(workoutCal.getTime());

        // Beginning of the current week
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        calorieCal.set(Calendar.DAY_OF_WEEK, calorieCal.getFirstDayOfWeek());
        workoutCal.set(Calendar.DAY_OF_WEEK, workoutCal.getFirstDayOfWeek());

        beginWeek = format.format(cal.getTime());
        calorieBeginWeek = format.format(calorieCal.getTime());
        workoutBeginWeek = format.format(workoutCal.getTime());

        // origin will hold onto the original date that beginWeek contains
        Date origin = cal.getTime();

        // Beginning of next week
        cal.add(Calendar.DAY_OF_WEEK, 6);
        calorieCal.add(Calendar.DAY_OF_WEEK,6);
        workoutCal.add(Calendar.DAY_OF_WEEK,6);

        endWeek = format.format(cal.getTime());
        calorieEndWeek = format.format(calorieCal.getTime());
        workoutEndWeek = format.format(workoutCal.getTime());

        cal.add(Calendar.DAY_OF_WEEK, -6); // reset
        calorieCal.add(Calendar.DAY_OF_WEEK,-6);
        workoutCal.add(Calendar.DAY_OF_WEEK,-6);

        // Getting water data
        float[] xData = databaseHelper.getCurrentWaterDateDay(beginWeek, endWeek);
        int[] yData = databaseHelper.getCurrentWaterData(beginWeek, endWeek);

        // Getting calorie data
        float[] calorie_xData = databaseHelper.getCurrentCalorieDateDay(calorieBeginWeek, calorieEndWeek);
        int[] calorie_yData = databaseHelper.getCurrentCalorieData(calorieBeginWeek, calorieEndWeek);

        // Getting workout data
        float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(calorieBeginWeek, calorieEndWeek);
        int[] workout_yData = databaseHelper.getCurrentWorkoutData(calorieBeginWeek, calorieEndWeek);

        List<BarEntry> entries = new ArrayList<BarEntry>();
        List<BarEntry> calorieEntries = new ArrayList<BarEntry>();
        List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

        // queryCompare will set date back to normal after using it to
        // ensure every date in the query result has a value
        Date queryCompare = cal.getTime();
        Date calorie_queryCompare = calorieCal.getTime();
        Date workout_queryCompare = workoutCal.getTime();

        // Go through each day of the week, check if there is data for it
        // If no data is found, set its value to zero
        // WATER INTAKE CHECKER
        float dateCheck;
        boolean found;
        for (int j=0; j<7; j++) {
            found = false;
            dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
            for (int i=0; i<xData.length; i++) {
                if (dateCheck == xData[i]) {
                    entries.add(new BarEntry(dateCheck, yData[i]));
                    found = true;
                    break;
                }
            }
            if (!found) {
                entries.add(new BarEntry(dateCheck, 0));
            }
            cal.add(Calendar.DAY_OF_WEEK,1);
        }
        // Set cal back to normal
        cal.setTime(queryCompare);

        // CALORIE INTAKE CHECKER
        float calorie_dateCheck;
        boolean calorie_DataFound;
        for (int j=0; j<7; j++) {
            calorie_DataFound = false;
            calorie_dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
            for (int i=0; i<calorie_xData.length; i++) {
                if (calorie_dateCheck == calorie_xData[i]) {
                    calorieEntries.add(new BarEntry(calorie_dateCheck, calorie_yData[i]));
                    calorie_DataFound = true;
                    break;
                }
            }
            if (!calorie_DataFound) {
                calorieEntries.add(new BarEntry(calorie_dateCheck, 0));
            }
            calorieCal.add(Calendar.DAY_OF_WEEK,1);
        }
        // Set calorieCal back to normal
        calorieCal.setTime(calorie_queryCompare);

        // WORKOUT CHECKER
        float workout_dateCheck;
        boolean workout_DataFound;
        for (int j=0; j<7; j++) {
            workout_DataFound = false;
            workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
            for (int i=0; i<workout_xData.length; i++) {
                if (workout_dateCheck == workout_xData[i]) {
                    workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                    workout_DataFound = true;
                    break;
                }
            }
            if (!workout_DataFound) {
                calorieEntries.add(new BarEntry(workout_dateCheck, 0));
            }
            workoutCal.add(Calendar.DAY_OF_WEEK,1);
        }
        // Set workoutCal back to normal
        workoutCal.setTime(workout_queryCompare);

        // Inserting data into water bar graph
        BarDataSet waterDataSet = new BarDataSet(entries, "Daily intake of cups of water");
        waterDataSet.setColor(getResources().getColor(R.color.light_green));
        BarData barData = new BarData(waterDataSet);
        waterChart.setData(barData);
        waterChart.setFitBars(true);
        waterChart.animateXY(2000,2000);
        Description description = waterChart.getDescription();
        description.setEnabled(false);
        waterChart.invalidate();

        // Inserting data into calorie bar graph
        BarDataSet calorieDataSet = new BarDataSet(calorieEntries, "Daily intake of calories");
        calorieDataSet.setColor(getResources().getColor(R.color.light_green));
        BarData calorieBarData = new BarData(calorieDataSet);
        calorieChart.setData(calorieBarData);
        calorieChart.setFitBars(true);
        calorieChart.animateXY(2000,2000);
        Description calorieDescription = calorieChart.getDescription();
        calorieDescription.setEnabled(false);
        calorieChart.invalidate();

        // Inserting data into workout bar graph
        BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
        workoutDataSet.setColor(getResources().getColor(R.color.light_green));
        BarData workoutBarData = new BarData(workoutDataSet);
        workoutChart.setData(workoutBarData);
        workoutChart.setFitBars(true);
        workoutChart.animateXY(2000,2000);
        Description workoutDescription = workoutChart.getDescription();
        workoutDescription.setEnabled(false);
        workoutChart.invalidate();


        // Update what is on the screen
        date.setText("Today's date: " + currentDate + "\n" + "Week of: " + beginWeek + "\n" + "End of week: " + endWeek);

        // Update dates based on the buttons pressed
        waterBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.DAY_OF_WEEK, -7);
                String newBeginWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek);
                int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek);

                List<BarEntry> entries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = cal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                float dateCheck;
                boolean found;
                for (int j=0; j<7; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                    for (int i=0; i<xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                    }
                    cal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                cal.setTime(queryCompare);

                BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                waterDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        waterBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.add(Calendar.DAY_OF_WEEK, 7);
                String newBeginWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek);
                int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek);

                List<BarEntry> entries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = cal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                float dateCheck;
                boolean found;
                for (int j=0; j<7; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                    for (int i=0; i<xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                    }
                    cal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                cal.setTime(queryCompare);

                BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                waterDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        waterBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cal.setTime(origin);
                String newBeginWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek);
                int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek);

                List<BarEntry> entries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = cal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                float dateCheck;
                boolean found;
                for (int j=0; j<7; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                    for (int i=0; i<xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                    }
                    cal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                cal.setTime(queryCompare);

                BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                waterDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        calorieBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieCal.add(Calendar.DAY_OF_WEEK, -7);
                String newBeginWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] calorie_xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek);
                int[] calorie_yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek);

                List<BarEntry> calorieEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date calorie_queryCompare = calorieCal.getTime();

                float calorie_dateCheck;
                boolean calorie_DataFound;
                for (int j=0; j<7; j++) {
                    calorie_DataFound = false;
                    calorie_dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                    for (int i=0; i<calorie_xData.length; i++) {
                        if (calorie_dateCheck == calorie_xData[i]) {
                            calorieEntries.add(new BarEntry(calorie_dateCheck, calorie_yData[i]));
                            calorie_DataFound = true;
                            break;
                        }
                    }
                    if (!calorie_DataFound) {
                        calorieEntries.add(new BarEntry(calorie_dateCheck, 0));
                    }
                    calorieCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                calorieCal.setTime(calorie_queryCompare);

                BarDataSet calorieDataSet = new BarDataSet(calorieEntries, "Calories");
                calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData calorieBarData = new BarData(calorieDataSet);
                calorieChart.setData(calorieBarData);
                calorieChart.setFitBars(true);
                calorieChart.animateXY(2000,2000);
                Description calorieDescription = calorieChart.getDescription();
                calorieDescription.setEnabled(false);
                calorieChart.invalidate();
            }
        });

        calorieBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieCal.add(Calendar.DAY_OF_WEEK, 7);
                String newBeginWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] calorie_xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek);
                int[] calorie_yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek);

                List<BarEntry> calorieEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date calorie_queryCompare = calorieCal.getTime();

                float calorie_dateCheck;
                boolean calorie_DataFound;
                for (int j=0; j<7; j++) {
                    calorie_DataFound = false;
                    calorie_dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                    for (int i=0; i<calorie_xData.length; i++) {
                        if (calorie_dateCheck == calorie_xData[i]) {
                            calorieEntries.add(new BarEntry(calorie_dateCheck, calorie_yData[i]));
                            calorie_DataFound = true;
                            break;
                        }
                    }
                    if (!calorie_DataFound) {
                        calorieEntries.add(new BarEntry(calorie_dateCheck, 0));
                    }
                    calorieCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                calorieCal.setTime(calorie_queryCompare);

                BarDataSet calorieDataSet = new BarDataSet(calorieEntries, "Calories");
                calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData calorieBarData = new BarData(calorieDataSet);
                calorieChart.setData(calorieBarData);
                calorieChart.setFitBars(true);
                calorieChart.animateXY(2000,2000);
                Description calorieDescription = calorieChart.getDescription();
                calorieDescription.setEnabled(false);
                calorieChart.invalidate();
            }
        });

        calorieBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calorieCal.setTime(origin);
                String newBeginWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] calorie_xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek);
                int[] calorie_yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek);

                List<BarEntry> calorieEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date calorie_queryCompare = calorieCal.getTime();

                float calorie_dateCheck;
                boolean calorie_DataFound;
                for (int j=0; j<7; j++) {
                    calorie_DataFound = false;
                    calorie_dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                    for (int i=0; i<calorie_xData.length; i++) {
                        if (calorie_dateCheck == calorie_xData[i]) {
                            calorieEntries.add(new BarEntry(calorie_dateCheck, calorie_yData[i]));
                            calorie_DataFound = true;
                            break;
                        }
                    }
                    if (!calorie_DataFound) {
                        calorieEntries.add(new BarEntry(calorie_dateCheck, 0));
                    }
                    calorieCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                calorieCal.setTime(calorie_queryCompare);

                BarDataSet calorieDataSet = new BarDataSet(calorieEntries, "Calories");
                calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData calorieBarData = new BarData(calorieDataSet);
                calorieChart.setData(calorieBarData);
                calorieChart.setFitBars(true);
                calorieChart.animateXY(2000,2000);
                Description calorieDescription = calorieChart.getDescription();
                calorieDescription.setEnabled(false);
                calorieChart.invalidate();
            }
        });

        workoutBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutCal.add(Calendar.DAY_OF_WEEK, -7);
                String newBeginWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek);
                int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek);

                List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date workout_queryCompare = workoutCal.getTime();

                float workout_dateCheck;
                boolean workout_DataFound;
                for (int j=0; j<7; j++) {
                    workout_DataFound = false;
                    workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                    for (int i=0; i<workout_xData.length; i++) {
                        if (workout_dateCheck == workout_xData[i]) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                            workout_DataFound = true;
                            break;
                        }
                    }
                    if (!workout_DataFound) {
                        workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                    }
                    workoutCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                workoutCal.setTime(workout_queryCompare);

                BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData workoutBarData = new BarData(workoutDataSet);
                workoutChart.setData(workoutBarData);
                workoutChart.setFitBars(true);
                workoutChart.animateXY(2000,2000);
                Description workoutDescription = workoutChart.getDescription();
                workoutDescription.setEnabled(false);
                workoutChart.invalidate();
            }
        });

        workoutBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutCal.add(Calendar.DAY_OF_WEEK, 7);
                String newBeginWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek);
                int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek);

                List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date workout_queryCompare = workoutCal.getTime();

                float workout_dateCheck;
                boolean workout_DataFound;
                for (int j=0; j<7; j++) {
                    workout_DataFound = false;
                    workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                    for (int i=0; i<workout_xData.length; i++) {
                        if (workout_dateCheck == workout_xData[i]) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                            workout_DataFound = true;
                            break;
                        }
                    }
                    if (!workout_DataFound) {
                        workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                    }
                    workoutCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                workoutCal.setTime(workout_queryCompare);

                BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData workoutBarData = new BarData(workoutDataSet);
                workoutChart.setData(workoutBarData);
                workoutChart.setFitBars(true);
                workoutChart.animateXY(2000,2000);
                Description workoutDescription = workoutChart.getDescription();
                workoutDescription.setEnabled(false);
                workoutChart.invalidate();
            }
        });

        workoutBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                workoutCal.setTime(origin);
                String newBeginWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK,6);
                String newEndWeek = format.format(workoutCal.getTime());
                workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek);
                int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek);

                List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date workout_queryCompare = workoutCal.getTime();

                float workout_dateCheck;
                boolean workout_DataFound;
                for (int j=0; j<7; j++) {
                    workout_DataFound = false;
                    workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                    for (int i=0; i<workout_xData.length; i++) {
                        if (workout_dateCheck == workout_xData[i]) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                            workout_DataFound = true;
                            break;
                        }
                    }
                    if (!workout_DataFound) {
                        workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                    }
                    workoutCal.add(Calendar.DAY_OF_WEEK,1);
                }
                // Set cal back to normal
                workoutCal.setTime(workout_queryCompare);

                BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                BarData workoutBarData = new BarData(workoutDataSet);
                workoutChart.setData(workoutBarData);
                workoutChart.setFitBars(true);
                workoutChart.animateXY(2000,2000);
                Description workoutDescription = workoutChart.getDescription();
                workoutDescription.setEnabled(false);
                workoutChart.invalidate();
            }
        });

        return rootView;
    }
}
