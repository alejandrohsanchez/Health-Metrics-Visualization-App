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
import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

import org.w3c.dom.Text;

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
        TextView waterDate, calorieDate, workoutDate;
        Button waterBarChart_previousWeekButton, waterBarChart_nextWeekButton, waterBarChart_todayButton;
        Button calorieBarChart_previousWeekButton, calorieBarChart_nextWeekButton, calorieBarChart_todayButton;
        Button workoutBarChart_previousWeekButton, workoutBarChart_nextWeekButton, workoutBarChart_todayButton;
        Button toWeeklyWaterChart, toMonthlyWaterChart, toWeeklyCalorieChart, toMonthlyCalorieChart, toWeeklyWorkoutChart, toMonthlyWorkoutChart;
        CombinedChart waterChart, calorieChart;
        BarChart workoutChart;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Assign Text Views
        waterDate = (TextView) rootView.findViewById(R.id.waterDate);
        calorieDate = (TextView) rootView.findViewById(R.id.calorieDate);
        workoutDate = (TextView) rootView.findViewById(R.id.workoutDate);

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
        toWeeklyWaterChart = rootView.findViewById(R.id.toWeekly_waterChart);
        toMonthlyWaterChart = rootView.findViewById(R.id.toMonthly_waterChart);
        toWeeklyCalorieChart = rootView.findViewById(R.id.toWeekly_calorieChart);
        toMonthlyCalorieChart = rootView.findViewById(R.id.toMonthly_calorieChart);


        // Assign graphs
        waterChart = rootView.findViewById(R.id.waterBarChart);
        calorieChart = rootView.findViewById(R.id.calorieBarChart);
        workoutChart = rootView.findViewById(R.id.workoutBarChart);

        // Getting today's date in custom format -----------
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat queryReturnFormat = new SimpleDateFormat("dd");
        // If needed ---> SimpleDateFormat getMonth = new SimpleDateFormat("MM");
        SimpleDateFormat getDay = new SimpleDateFormat("dd");
        SimpleDateFormat getMonthName = new SimpleDateFormat("MMMM");

        String currentWaterChartSetting = databaseHelper.getWaterChartViewSetting();
        String currentCalorieChartSetting = databaseHelper.getCalorieChartViewSetting();
        String currentWorkoutChartSetting = databaseHelper.getWorkoutChartViewSetting();
        String currentWeightChartSetting = databaseHelper.getWeightChartViewSetting();

        // Calendars for each graph
        Calendar cal = Calendar.getInstance();
        Calendar calorieCal = Calendar.getInstance();
        Calendar workoutCal = Calendar.getInstance();

        // Create 3 variables to carry the current date, beginning, and the end of the week's date
        // for each graph
        String currentDate, beginWeek, endWeek, beginMonth, endMonth, month1, day1, month2, day2;
        String calorieBeginWeek, calorieEndWeek, calorieBeginMonth, calorieEndMonth;
        String workoutBeginWeek, workoutEndWeek, workoutBeginMonth, workoutEndMonth;
        currentDate = format.format(cal.getTime());


        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        Date origin = cal.getTime();

        if (currentWaterChartSetting.equals("week")) {
            waterBarChart_previousWeekButton.setText("prev week");
            waterBarChart_nextWeekButton.setText("next week");
            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            beginWeek = format.format(cal.getTime());
            month1 = getMonthName.format(cal.getTime());
            day1 = getDay.format(cal.getTime());
            // Beginning of next week
            cal.add(Calendar.DAY_OF_WEEK, 6);
            endWeek = format.format(cal.getTime());
            day2 = getDay.format(cal.getTime());
            month2 = getMonthName.format(cal.getTime());
            cal.add(Calendar.DAY_OF_WEEK, -6); // reset

            waterDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

            // Getting water data
            float[] xData = databaseHelper.getCurrentWaterDateDay(beginWeek, endWeek, 7);
            int[] goalData = databaseHelper.getCurrentWaterGoalData(beginWeek, endWeek, 7);
            int[] yData = databaseHelper.getCurrentWaterData(beginWeek, endWeek, 7);

            List<BarEntry> entries = new ArrayList<BarEntry>();
            List<Entry> goalEntries = new ArrayList<Entry>();

            // queryCompare will set date back to normal after using it to
            // ensure every date in the query result has a value
            Date queryCompare = cal.getTime();

            // Go through each day of the week, check if there is data for it
            // If no data is found, set its value to zero
            // WATER INTAKE CHECKER
            float dateCheck;
            boolean found;
            for (int j = 0; j < 7; j++) {
                found = false;
                dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                for (int i = 0; i < xData.length; i++) {
                    if (dateCheck == xData[i]) {
                        entries.add(new BarEntry(dateCheck, yData[i]));
                        goalEntries.add(new Entry(dateCheck,goalData[i]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    entries.add(new BarEntry(dateCheck, 0));
                    goalEntries.add(new Entry(dateCheck,0));
                }
                cal.add(Calendar.DAY_OF_WEEK, 1);
            }
            // Set cal back to normal
            cal.setTime(queryCompare);

            // Inserting data into water bar graph
            BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
            LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
            dataSet.setColor(getResources().getColor(R.color.black));
            waterDataSet.setColor(getResources().getColor(R.color.light_green));
            LineData lineData = new LineData(dataSet);
            BarData barData = new BarData(waterDataSet);
            lineData.setDrawValues(false);
            CombinedData combinedData = new CombinedData();
            combinedData.setData(lineData);
            combinedData.setData(barData);
            combinedData.setValueTextSize(12f);
            waterChart.setData(combinedData);
            waterChart.animateXY(2000, 2000);
            Description description = waterChart.getDescription();
            description.setEnabled(false);

            // Axes changes
            XAxis xAxis = waterChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            YAxis leftAxis = waterChart.getAxisLeft();
            leftAxis.setDrawLabels(false);
            leftAxis.setDrawAxisLine(false);
            YAxis rightAxis = waterChart.getAxisRight();
            rightAxis.setDrawLabels(false);
            rightAxis.setDrawAxisLine(false);

            // Legend changes
            Legend legend = waterChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            // Load chart
            waterChart.invalidate();

        }
        else if (currentWaterChartSetting.equals("month")) {
            waterBarChart_previousWeekButton.setText("prev month");
            waterBarChart_nextWeekButton.setText("next month");
            int daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, 1);
            beginMonth = format.format(cal.getTime());
            month1 = getMonthName.format(cal.getTime());
            // Beginning of next week
            cal.add(Calendar.DAY_OF_WEEK, daysInWaterMonth - 1);
            endMonth = format.format(cal.getTime());
            cal.add(Calendar.DAY_OF_WEEK, -1 * (daysInWaterMonth - 1)); // reset

            waterDate.setText("Month of " + month1);

            // Getting water data
            float[] xData = databaseHelper.getCurrentWaterDateDay(beginMonth, endMonth, daysInWaterMonth);
            int[] goalData = databaseHelper.getCurrentWaterGoalData(beginMonth, endMonth, daysInWaterMonth);
            int[] yData = databaseHelper.getCurrentWaterData(beginMonth, endMonth, daysInWaterMonth);

            List<BarEntry> entries = new ArrayList<BarEntry>();
            List<Entry> goalEntries = new ArrayList<Entry>();

            // queryCompare will set date back to normal after using it to
            // ensure every date in the query result has a value
            Date queryCompare = cal.getTime();

            // Go through each day of the week, check if there is data for it
            // If no data is found, set its value to zero
            // WATER INTAKE CHECKER
            float dateCheck;
            boolean found;
            for (int j = 0; j < daysInWaterMonth; j++) {
                found = false;
                dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                for (int i = 0; i < xData.length; i++) {
                    if (dateCheck == xData[i]) {
                        entries.add(new BarEntry(dateCheck, yData[i]));
                        goalEntries.add(new Entry(dateCheck,goalData[i]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    entries.add(new BarEntry(dateCheck, 0));
                    goalEntries.add(new Entry(dateCheck,0));
                }
                cal.add(Calendar.DAY_OF_WEEK, 1);
            }
            // Set cal back to normal
            cal.setTime(queryCompare);

            // Inserting data into water bar graph
            BarDataSet waterDataSet = new BarDataSet(entries, "Cups of Water");
            LineDataSet dataSet = new LineDataSet(goalEntries, "Daily Goal");
            dataSet.setColor(getResources().getColor(R.color.black));
            waterDataSet.setColor(getResources().getColor(R.color.light_green));
            LineData lineData = new LineData(dataSet);
            BarData barData = new BarData(waterDataSet);
            lineData.setDrawValues(false);
            barData.setDrawValues(false);
            CombinedData combinedData = new CombinedData();
            combinedData.setData(lineData);
            combinedData.setData(barData);
            combinedData.setValueTextSize(12f);
            waterChart.setData(combinedData);
            waterChart.animateXY(2000, 2000);
            Description description = waterChart.getDescription();
            description.setEnabled(false);

            // Axes changes
            XAxis xAxis = waterChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            YAxis leftAxis = waterChart.getAxisLeft();
            leftAxis.setDrawLabels(true);
            leftAxis.setDrawAxisLine(false);
            YAxis rightAxis = waterChart.getAxisRight();
            rightAxis.setDrawLabels(true);
            rightAxis.setDrawAxisLine(false);

            // Legend changes
            Legend legend = waterChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            // Load chart
            waterChart.invalidate();
        }

        if (currentCalorieChartSetting.equals("week")) {
            calorieBarChart_previousWeekButton.setText("prev week");
            calorieBarChart_nextWeekButton.setText("next week");
            calorieCal.set(Calendar.DAY_OF_WEEK, calorieCal.getFirstDayOfWeek());
            calorieBeginWeek = format.format(calorieCal.getTime());
            month1 = getMonthName.format(calorieCal.getTime());
            day1 = getDay.format(calorieCal.getTime());
            // Beginning of next week
            calorieCal.add(Calendar.DAY_OF_WEEK, 6);
            calorieEndWeek = format.format(calorieCal.getTime());
            day2 = getDay.format(calorieCal.getTime());
            month2 = getMonthName.format(calorieCal.getTime());
            calorieCal.add(Calendar.DAY_OF_WEEK, -6); // reset

            calorieDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

            // Getting calorie data
            float[] xData = databaseHelper.getCurrentCalorieDateDay(calorieBeginWeek, calorieEndWeek, 7);
            int[] goalData = databaseHelper.getCurrentCalorieGoalData(calorieBeginWeek, calorieEndWeek, 7);
            int[] yData = databaseHelper.getCurrentCalorieData(calorieBeginWeek, calorieEndWeek, 7);

            List<BarEntry> entries = new ArrayList<BarEntry>();
            List<Entry> goalEntries = new ArrayList<Entry>();

            // queryCompare will set date back to normal after using it to
            // ensure every date in the query result has a value
            Date queryCompare = calorieCal.getTime();

            // Go through each day of the week, check if there is data for it
            // If no data is found, set its value to zero
            // CALORIE INTAKE CHECKER
            float dateCheck;
            boolean found;
            for (int j = 0; j < 7; j++) {
                found = false;
                dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                for (int i = 0; i < xData.length; i++) {
                    if (dateCheck == xData[i]) {
                        entries.add(new BarEntry(dateCheck, yData[i]));
                        goalEntries.add(new Entry(dateCheck,goalData[i]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    entries.add(new BarEntry(dateCheck, 0));
                    goalEntries.add(new Entry(dateCheck,0));
                }
                calorieCal.add(Calendar.DAY_OF_WEEK, 1);
            }
            // Set cal back to normal
            calorieCal.setTime(queryCompare);

            // Inserting data into calorie bar graph
            BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
            LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
            dataSet.setColor(getResources().getColor(R.color.black));
            calorieDataSet.setColor(getResources().getColor(R.color.light_green));
            LineData lineData = new LineData(dataSet);
            BarData barData = new BarData(calorieDataSet);
            lineData.setDrawValues(false);
            CombinedData combinedData = new CombinedData();
            combinedData.setData(lineData);
            combinedData.setData(barData);
            combinedData.setValueTextSize(12f);
            calorieChart.setData(combinedData);
            calorieChart.animateXY(2000, 2000);
            Description description = calorieChart.getDescription();
            description.setEnabled(false);

            // Axes changes
            XAxis xAxis = calorieChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            YAxis leftAxis = calorieChart.getAxisLeft();
            leftAxis.setDrawLabels(false);
            leftAxis.setDrawAxisLine(false);
            YAxis rightAxis = calorieChart.getAxisRight();
            rightAxis.setDrawLabels(false);
            rightAxis.setDrawAxisLine(false);

            // Legend changes
            Legend legend = calorieChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            // Load chart
            calorieChart.invalidate();
        }
        else if (currentCalorieChartSetting.equals("month")) {
            calorieBarChart_previousWeekButton.setText("prev month");
            calorieBarChart_nextWeekButton.setText("next month");
            int daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
            calorieCal.set(Calendar.DAY_OF_MONTH, 1);
            calorieBeginMonth = format.format(calorieCal.getTime());
            month1 = getMonthName.format(calorieCal.getTime());
            // Beginning of next week
            calorieCal.add(Calendar.DAY_OF_WEEK, daysInCalorieMonth - 1);
            calorieEndMonth = format.format(calorieCal.getTime());
            calorieCal.add(Calendar.DAY_OF_WEEK, -1 * (daysInCalorieMonth - 1)); // reset

            calorieDate.setText("Month of " + month1);

            // Getting calorie data
            float[] xData = databaseHelper.getCurrentCalorieDateDay(calorieBeginMonth, calorieEndMonth, daysInCalorieMonth);
            int[] goalData = databaseHelper.getCurrentCalorieGoalData(calorieBeginMonth, calorieEndMonth, daysInCalorieMonth);
            int[] yData = databaseHelper.getCurrentCalorieData(calorieBeginMonth, calorieEndMonth, daysInCalorieMonth);

            List<BarEntry> entries = new ArrayList<BarEntry>();
            List<Entry> goalEntries = new ArrayList<Entry>();

            // queryCompare will set date back to normal after using it to
            // ensure every date in the query result has a value
            Date queryCompare = calorieCal.getTime();

            // Go through each day of the week, check if there is data for it
            // If no data is found, set its value to zero
            // CALORIE INTAKE CHECKER
            float dateCheck;
            boolean found;
            for (int j = 0; j < daysInCalorieMonth; j++) {
                found = false;
                dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                for (int i = 0; i < xData.length; i++) {
                    if (dateCheck == xData[i]) {
                        entries.add(new BarEntry(dateCheck, yData[i]));
                        goalEntries.add(new Entry(dateCheck,goalData[i]));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    entries.add(new BarEntry(dateCheck, 0));
                    goalEntries.add(new Entry(dateCheck,0));
                }
                calorieCal.add(Calendar.DAY_OF_WEEK, 1);
            }
            // Set cal back to normal
            calorieCal.setTime(queryCompare);

            // Inserting data into calorie bar graph
            BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
            LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
            dataSet.setColor(getResources().getColor(R.color.black));
            calorieDataSet.setColor(getResources().getColor(R.color.light_green));
            LineData lineData = new LineData(dataSet);
            BarData barData = new BarData(calorieDataSet);
            lineData.setDrawValues(false);
            barData.setDrawValues(false);
            CombinedData combinedData = new CombinedData();
            combinedData.setData(lineData);
            combinedData.setData(barData);
            combinedData.setValueTextSize(12f);
            calorieChart.setData(combinedData);
            calorieChart.animateXY(2000, 2000);
            Description description = calorieChart.getDescription();
            description.setEnabled(false);

            // Axes changes
            XAxis xAxis = calorieChart.getXAxis();
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setDrawGridLines(false);
            YAxis leftAxis = calorieChart.getAxisLeft();
            leftAxis.setDrawLabels(true);
            leftAxis.setDrawAxisLine(false);
            YAxis rightAxis = calorieChart.getAxisRight();
            rightAxis.setDrawLabels(true);
            rightAxis.setDrawAxisLine(false);

            // Legend changes
            Legend legend = calorieChart.getLegend();
            legend.setForm(Legend.LegendForm.LINE);
            legend.setTextSize(12f);
            legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
            legend.setDrawInside(false);

            // Load chart
            calorieChart.invalidate();
        }

        if (currentWorkoutChartSetting.equals("week")) {
            workoutBarChart_previousWeekButton.setText("prev week");
            workoutBarChart_nextWeekButton.setText("next week");
            //workoutCurrentDate = format.format(workoutCal.getTime());

            // Beginning of the current week
            workoutCal.set(Calendar.DAY_OF_WEEK, workoutCal.getFirstDayOfWeek());
            // origin will hold onto the original date that beginWeek contains
            //Date origin = workoutCal.getTime();
            workoutBeginWeek = format.format(workoutCal.getTime());
            workoutCal.add(Calendar.DAY_OF_WEEK, 6);
            workoutEndWeek = format.format(workoutCal.getTime());
            workoutCal.add(Calendar.DAY_OF_WEEK, -6);

            // Getting workout data
            float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(workoutBeginWeek, workoutEndWeek, 7);
            int[] workout_yData = databaseHelper.getCurrentWorkoutData(workoutBeginWeek, workoutEndWeek, 7);

            List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

            Date workout_queryCompare = workoutCal.getTime();

            // WORKOUT CHECKER
            float workout_dateCheck;
            boolean workout_DataFound;
            for (int j = 0; j < 7; j++) {
                workout_DataFound = false;
                workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                for (int i = 0; i < workout_xData.length; i++) {
                    if (workout_dateCheck == workout_xData[i]) {
                        workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                        workout_DataFound = true;
                        break;
                    }
                }
                if (!workout_DataFound) {
                    workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                }
                workoutCal.add(Calendar.DAY_OF_WEEK, 1);
            }
            // Set workoutCal back to normal
            workoutCal.setTime(workout_queryCompare);

            // Inserting data into workout bar graph
            BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
            workoutDataSet.setColor(getResources().getColor(R.color.light_green));
            BarData workoutBarData = new BarData(workoutDataSet);
            workoutChart.setData(workoutBarData);
            workoutChart.setFitBars(true);
            workoutChart.animateXY(2000, 2000);
            Description workoutDescription = workoutChart.getDescription();
            workoutDescription.setEnabled(false);
            workoutChart.invalidate();
        }
        else if (currentWorkoutChartSetting.equals("month")) {

        }

//        int daysinWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        System.out.println(daysinWaterMonth);

        // Update what is on the screen
        //date.setText("Today's date: " + currentDate + "\n" + "Week of: " + beginWeek + "\n" + "End of week: " + endWeek);

        toWeeklyWaterChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newBeginWeek, newEndWeek, month1, day1, month2, day2;
                databaseHelper.setWaterChartViewSetting("week");
                waterBarChart_previousWeekButton.setText("prev week");
                waterBarChart_nextWeekButton.setText("next week");
                cal.setTime(origin);
                cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
                newBeginWeek = format.format(cal.getTime());
                month1 = getMonthName.format(cal.getTime());
                day1 = getDay.format(cal.getTime());
                // Beginning of next week
                cal.add(Calendar.DAY_OF_WEEK, 6);
                newEndWeek = format.format(cal.getTime());
                month2 = getMonthName.format(cal.getTime());
                day2 = getDay.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK, -6); // reset

                waterDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                // Getting water data
                float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek, 7);
                int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginWeek, newEndWeek, 7);
                int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek, 7);

                List<BarEntry> entries = new ArrayList<BarEntry>();
                List<Entry> goalEntries = new ArrayList<Entry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = cal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                // WATER INTAKE CHECKER
                float dateCheck;
                boolean found;
                for (int j = 0; j < 7; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                    for (int i = 0; i < xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            goalEntries.add(new Entry(dateCheck,goalData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                        goalEntries.add(new Entry(dateCheck,0));
                    }
                    cal.add(Calendar.DAY_OF_WEEK, 1);
                }
                // Set cal back to normal
                cal.setTime(queryCompare);

                // Inserting data into water bar graph
                BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                dataSet.setColor(getResources().getColor(R.color.black));
                waterDataSet.setColor(getResources().getColor(R.color.light_green));
                LineData lineData = new LineData(dataSet);
                BarData barData = new BarData(waterDataSet);
                lineData.setDrawValues(false);
                CombinedData combinedData = new CombinedData();
                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedData.setValueTextSize(12f);
                waterChart.setData(combinedData);
                waterChart.animateXY(2000, 2000);
                Description description = waterChart.getDescription();
                description.setEnabled(false);

                // Axes changes
                XAxis xAxis = waterChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                YAxis leftAxis = waterChart.getAxisLeft();
                leftAxis.setDrawLabels(false);
                leftAxis.setDrawAxisLine(false);
                YAxis rightAxis = waterChart.getAxisRight();
                rightAxis.setDrawLabels(false);
                rightAxis.setDrawAxisLine(false);

                // Legend changes
                Legend legend = waterChart.getLegend();
                legend.setForm(Legend.LegendForm.LINE);
                legend.setTextSize(12f);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);

                // Load chart
                waterChart.invalidate();
            }
        });

        toMonthlyWaterChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newBeginMonth, newEndMonth, month1;
                int daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                databaseHelper.setWaterChartViewSetting("month");
                waterBarChart_previousWeekButton.setText("prev month");
                waterBarChart_nextWeekButton.setText("next month");
                cal.setTime(origin);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                newBeginMonth = format.format(cal.getTime());
                month1 = getMonthName.format(cal.getTime());
                // Beginning of next week
                cal.add(Calendar.DAY_OF_WEEK, daysInWaterMonth - 1);
                newEndMonth = format.format(cal.getTime());
                cal.add(Calendar.DAY_OF_WEEK, -1 * (daysInWaterMonth - 1)); // reset

                waterDate.setText("Month of " + month1);

                // Getting water data
                float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginMonth, newEndMonth, daysInWaterMonth);
                int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginMonth, newEndMonth, daysInWaterMonth);
                int[] yData = databaseHelper.getCurrentWaterData(newBeginMonth, newEndMonth, daysInWaterMonth);

                List<BarEntry> entries = new ArrayList<BarEntry>();
                List<Entry> goalEntries = new ArrayList<Entry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = cal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                // WATER INTAKE CHECKER
                float dateCheck;
                boolean found;
                for (int j = 0; j < daysInWaterMonth; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                    for (int i = 0; i < xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            goalEntries.add(new Entry(dateCheck,goalData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                        goalEntries.add(new Entry(dateCheck,0));
                    }
                    cal.add(Calendar.DAY_OF_WEEK, 1);
                }
                // Set cal back to normal
                cal.setTime(queryCompare);

                // Inserting data into water bar graph
                BarDataSet waterDataSet = new BarDataSet(entries, "Cups of Water");
                LineDataSet dataSet = new LineDataSet(goalEntries, "Daily Goal");
                dataSet.setColor(getResources().getColor(R.color.black));
                waterDataSet.setColor(getResources().getColor(R.color.light_green));
                LineData lineData = new LineData(dataSet);
                BarData barData = new BarData(waterDataSet);
                lineData.setDrawValues(false);
                barData.setDrawValues(false);
                CombinedData combinedData = new CombinedData();
                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedData.setValueTextSize(12f);
                waterChart.setData(combinedData);
                waterChart.animateXY(2000, 2000);
                Description description = waterChart.getDescription();
                description.setEnabled(false);

                // Axes changes
                XAxis xAxis = waterChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                YAxis leftAxis = waterChart.getAxisLeft();
                leftAxis.setDrawLabels(true);
                leftAxis.setDrawAxisLine(false);
                YAxis rightAxis = waterChart.getAxisRight();
                rightAxis.setDrawLabels(true);
                rightAxis.setDrawAxisLine(false);

                // Legend changes
                Legend legend = waterChart.getLegend();
                legend.setForm(Legend.LegendForm.LINE);
                legend.setTextSize(12f);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);

                // Load chart
                waterChart.invalidate();
            }
        });

        // Update water charts -- done
        waterBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWaterChartSetting = databaseHelper.getWaterChartViewSetting();
                if (currentWaterChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    cal.add(Calendar.DAY_OF_WEEK, -7);
                    String newBeginWeek = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());
                    day1 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(cal.getTime());
                    month2 = getMonthName.format(cal.getTime());
                    day2 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, -6);

                    waterDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();

                }
                else if (currentWaterChartSetting.equals("month")) {
                    String month1;
                    // Go into the last day of the previous month
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    cal.add(Calendar.DAY_OF_WEEK, -1);
                    int daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    String newEndMonth = format.format(cal.getTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    String newBeginMonth = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());

                    waterDate.setText("Month of " + month1);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginMonth, newEndMonth, daysInWaterMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInWaterMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of Water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily Goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();
                }
            }
        });

        waterBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWaterChartSetting = databaseHelper.getWaterChartViewSetting();
                if (currentWaterChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    cal.add(Calendar.DAY_OF_WEEK, 7);
                    String newBeginWeek = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());
                    day1 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(cal.getTime());
                    month2 = getMonthName.format(cal.getTime());
                    day2 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, -6);

                    waterDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();

                }
                else if (currentWaterChartSetting.equals("month")) {
                    String month1;
                    // Go to the first day of the next month
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    int daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    cal.add(Calendar.DAY_OF_WEEK, daysInWaterMonth);
                    String newBeginMonth = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());
                    daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    cal.add(Calendar.DAY_OF_MONTH, daysInWaterMonth - 1);
                    String newEndMonth = format.format(cal.getTime());
                    cal.set(Calendar.DAY_OF_MONTH, 1);

                    waterDate.setText("Month of " + month1);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginMonth, newEndMonth, daysInWaterMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInWaterMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of Water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily Goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();
                }
            }
        });

        waterBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWaterChartSetting = databaseHelper.getWaterChartViewSetting();
                if (currentWaterChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    cal.setTime(origin);
                    String newBeginWeek = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());
                    day1 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(cal.getTime());
                    month2 = getMonthName.format(cal.getTime());
                    day2 = getDay.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, -6);

                    waterDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();

                }
                else if (currentWaterChartSetting.equals("month")) {
                    String month1;
                    cal.setTime(origin);
                    cal.set(Calendar.DAY_OF_MONTH, 1);
                    String newBeginMonth = format.format(cal.getTime());
                    month1 = getMonthName.format(cal.getTime());
                    int daysInWaterMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    cal.add(Calendar.DAY_OF_WEEK, daysInWaterMonth - 1);
                    String newEndMonth = format.format(cal.getTime());
                    cal.add(Calendar.DAY_OF_WEEK, -1 * (daysInWaterMonth - 1));

                    waterDate.setText("Month of " + month1);

                    // Getting water data
                    float[] xData = databaseHelper.getCurrentWaterDateDay(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] goalData = databaseHelper.getCurrentWaterGoalData(newBeginMonth, newEndMonth, daysInWaterMonth);
                    int[] yData = databaseHelper.getCurrentWaterData(newBeginMonth, newEndMonth, daysInWaterMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = cal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // WATER INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInWaterMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(cal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        cal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    cal.setTime(queryCompare);

                    // Inserting data into water bar graph
                    BarDataSet waterDataSet = new BarDataSet(entries, "Cups of Water");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily Goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    waterDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(waterDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    waterChart.setData(combinedData);
                    waterChart.animateXY(2000, 2000);
                    Description description = waterChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = waterChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    waterChart.invalidate();
                }
            }
        });

        // Update calorie charts -- incomplete
        toWeeklyCalorieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newBeginWeek, newEndWeek, month1, day1, month2, day2;
                databaseHelper.setCalorieChartViewSetting("week");
                calorieBarChart_previousWeekButton.setText("prev week");
                calorieBarChart_nextWeekButton.setText("next week");
                calorieCal.setTime(origin);
                calorieCal.set(Calendar.DAY_OF_WEEK, calorieCal.getFirstDayOfWeek());
                newBeginWeek = format.format(calorieCal.getTime());
                month1 = getMonthName.format(calorieCal.getTime());
                day1 = getDay.format(calorieCal.getTime());
                // Beginning of next week
                calorieCal.add(Calendar.DAY_OF_WEEK, 6);
                newEndWeek = format.format(calorieCal.getTime());
                month2 = getMonthName.format(calorieCal.getTime());
                day2 = getDay.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK, -6); // reset

                calorieDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                // Getting calorie data
                float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek, 7);
                int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginWeek, newEndWeek, 7);
                int[] yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek, 7);

                List<BarEntry> entries = new ArrayList<BarEntry>();
                List<Entry> goalEntries = new ArrayList<Entry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = calorieCal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                // CALORIE INTAKE CHECKER
                float dateCheck;
                boolean found;
                for (int j = 0; j < 7; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                    for (int i = 0; i < xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            goalEntries.add(new Entry(dateCheck,goalData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                        goalEntries.add(new Entry(dateCheck,0));
                    }
                    calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                }
                // Set cal back to normal
                calorieCal.setTime(queryCompare);

                // Inserting data into calorie bar graph
                BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                dataSet.setColor(getResources().getColor(R.color.black));
                calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                LineData lineData = new LineData(dataSet);
                BarData barData = new BarData(calorieDataSet);
                lineData.setDrawValues(false);
                CombinedData combinedData = new CombinedData();
                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedData.setValueTextSize(12f);
                calorieChart.setData(combinedData);
                calorieChart.animateXY(2000, 2000);
                Description description = calorieChart.getDescription();
                description.setEnabled(false);

                // Axes changes
                XAxis xAxis = calorieChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                YAxis leftAxis = calorieChart.getAxisLeft();
                leftAxis.setDrawLabels(false);
                leftAxis.setDrawAxisLine(false);
                YAxis rightAxis = calorieChart.getAxisRight();
                rightAxis.setDrawLabels(false);
                rightAxis.setDrawAxisLine(false);

                // Legend changes
                Legend legend = calorieChart.getLegend();
                legend.setForm(Legend.LegendForm.LINE);
                legend.setTextSize(12f);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);

                // Load chart
                calorieChart.invalidate();
            }
        });

        toMonthlyCalorieChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newBeginMonth, newEndMonth, month1;
                int daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                databaseHelper.setCalorieChartViewSetting("month");
                calorieBarChart_previousWeekButton.setText("prev month");
                calorieBarChart_nextWeekButton.setText("next month");
                calorieCal.setTime(origin);
                calorieCal.set(Calendar.DAY_OF_MONTH, 1);
                newBeginMonth = format.format(calorieCal.getTime());
                month1 = getMonthName.format(calorieCal.getTime());
                // Beginning of next week
                calorieCal.add(Calendar.DAY_OF_WEEK, daysInCalorieMonth - 1);
                newEndMonth = format.format(calorieCal.getTime());
                calorieCal.add(Calendar.DAY_OF_WEEK, -1 * (daysInCalorieMonth - 1)); // reset

                calorieDate.setText("Month of " + month1);

                // Getting calorie data
                float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginMonth, newEndMonth, daysInCalorieMonth);
                int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginMonth, newEndMonth, daysInCalorieMonth);
                int[] yData = databaseHelper.getCurrentCalorieData(newBeginMonth, newEndMonth, daysInCalorieMonth);

                List<BarEntry> entries = new ArrayList<BarEntry>();
                List<Entry> goalEntries = new ArrayList<Entry>();

                // queryCompare will set date back to normal after using it to
                // ensure every date in the query result has a value
                Date queryCompare = calorieCal.getTime();

                // Go through each day of the week, check if there is data for it
                // If no data is found, set its value to zero
                // CALORIE INTAKE CHECKER
                float dateCheck;
                boolean found;
                for (int j = 0; j < daysInCalorieMonth; j++) {
                    found = false;
                    dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                    for (int i = 0; i < xData.length; i++) {
                        if (dateCheck == xData[i]) {
                            entries.add(new BarEntry(dateCheck, yData[i]));
                            goalEntries.add(new Entry(dateCheck,goalData[i]));
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        entries.add(new BarEntry(dateCheck, 0));
                        goalEntries.add(new Entry(dateCheck,0));
                    }
                    calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                }
                // Set cal back to normal
                calorieCal.setTime(queryCompare);

                // Inserting data into calorie bar graph
                BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                dataSet.setColor(getResources().getColor(R.color.black));
                calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                LineData lineData = new LineData(dataSet);
                BarData barData = new BarData(calorieDataSet);
                lineData.setDrawValues(false);
                barData.setDrawValues(false);
                CombinedData combinedData = new CombinedData();
                combinedData.setData(lineData);
                combinedData.setData(barData);
                combinedData.setValueTextSize(12f);
                calorieChart.setData(combinedData);
                calorieChart.animateXY(2000, 2000);
                Description description = calorieChart.getDescription();
                description.setEnabled(false);

                // Axes changes
                XAxis xAxis = calorieChart.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                YAxis leftAxis = calorieChart.getAxisLeft();
                leftAxis.setDrawLabels(true);
                leftAxis.setDrawAxisLine(false);
                YAxis rightAxis = calorieChart.getAxisRight();
                rightAxis.setDrawLabels(true);
                rightAxis.setDrawAxisLine(false);

                // Legend changes
                Legend legend = calorieChart.getLegend();
                legend.setForm(Legend.LegendForm.LINE);
                legend.setTextSize(12f);
                legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                legend.setDrawInside(false);

                // Load chart
                calorieChart.invalidate();
            }
        });

        calorieBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentCalorieChartSetting = databaseHelper.getCalorieChartViewSetting();
                if (currentCalorieChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    calorieCal.add(Calendar.DAY_OF_WEEK, -7);
                    String newBeginWeek = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());
                    day1 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(calorieCal.getTime());
                    month2 = getMonthName.format(calorieCal.getTime());
                    day2 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, -6);

                    calorieDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = waterChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = waterChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = waterChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();
                }
                else if (currentCalorieChartSetting.equals("month")) {
                    String month1;
                    // Go into the last day of the previous month
                    calorieCal.set(Calendar.DAY_OF_MONTH, 1);
                    calorieCal.add(Calendar.DAY_OF_WEEK, -1);
                    int daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    String newEndMonth = format.format(calorieCal.getTime());
                    calorieCal.set(Calendar.DAY_OF_MONTH, 1);
                    String newBeginMonth = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());

                    calorieDate.setText("Month of " + month1);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginMonth, newEndMonth, daysInCalorieMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInCalorieMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = calorieChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = calorieChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = calorieChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();
                }
            }
        });

        calorieBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentCalorieChartSetting = databaseHelper.getCalorieChartViewSetting();
                if (currentCalorieChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    calorieCal.add(Calendar.DAY_OF_WEEK, 7);
                    String newBeginWeek = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());
                    day1 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(calorieCal.getTime());
                    month2 = getMonthName.format(calorieCal.getTime());
                    day2 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, -6);

                    calorieDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = calorieChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = calorieChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = calorieChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();
                }
                else if (currentCalorieChartSetting.equals("month")) {
                    String month1;
                    // Go to the first day of the next month
                    calorieCal.set(Calendar.DAY_OF_MONTH, 1);
                    int daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    calorieCal.add(Calendar.DAY_OF_WEEK, daysInCalorieMonth);
                    String newBeginMonth = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());
                    daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    calorieCal.add(Calendar.DAY_OF_MONTH, daysInCalorieMonth - 1);
                    String newEndMonth = format.format(calorieCal.getTime());
                    calorieCal.set(Calendar.DAY_OF_MONTH, 1);

                    calorieDate.setText("Month of " + month1);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginMonth, newEndMonth, daysInCalorieMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInCalorieMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = calorieChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = calorieChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = calorieChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();
                }
            }
        });

        calorieBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentCalorieChartSetting = databaseHelper.getCalorieChartViewSetting();
                if (currentCalorieChartSetting.equals("week")) {
                    String month1, day1, month2, day2;
                    calorieCal.setTime(origin);
                    String newBeginWeek = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());
                    day1 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(calorieCal.getTime());
                    month2 = getMonthName.format(calorieCal.getTime());
                    day2 = getDay.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, -6);

                    calorieDate.setText("Week of " + month1 + " " + day1 + " - " + month2 + " " + day2);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginWeek, newEndWeek, 7);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginWeek, newEndWeek, 7);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < 7; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = calorieChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = calorieChart.getAxisLeft();
                    leftAxis.setDrawLabels(false);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = calorieChart.getAxisRight();
                    rightAxis.setDrawLabels(false);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();

                }
                else if (currentCalorieChartSetting.equals("month")) {
                    String month1;
                    calorieCal.setTime(origin);
                    calorieCal.set(Calendar.DAY_OF_MONTH, 1);
                    String newBeginMonth = format.format(calorieCal.getTime());
                    month1 = getMonthName.format(calorieCal.getTime());
                    int daysInCalorieMonth = calorieCal.getActualMaximum(Calendar.DAY_OF_MONTH);
                    calorieCal.add(Calendar.DAY_OF_WEEK, daysInCalorieMonth - 1);
                    String newEndMonth = format.format(calorieCal.getTime());
                    calorieCal.add(Calendar.DAY_OF_WEEK, -1 * (daysInCalorieMonth - 1));

                    calorieDate.setText("Month of " + month1);

                    // Getting calorie data
                    float[] xData = databaseHelper.getCurrentCalorieDateDay(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] goalData = databaseHelper.getCurrentCalorieGoalData(newBeginMonth, newEndMonth, daysInCalorieMonth);
                    int[] yData = databaseHelper.getCurrentCalorieData(newBeginMonth, newEndMonth, daysInCalorieMonth);

                    List<BarEntry> entries = new ArrayList<BarEntry>();
                    List<Entry> goalEntries = new ArrayList<Entry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date queryCompare = calorieCal.getTime();

                    // Go through each day of the week, check if there is data for it
                    // If no data is found, set its value to zero
                    // CALORIE INTAKE CHECKER
                    float dateCheck;
                    boolean found;
                    for (int j = 0; j < daysInCalorieMonth; j++) {
                        found = false;
                        dateCheck = Float.parseFloat(queryReturnFormat.format(calorieCal.getTime()));
                        for (int i = 0; i < xData.length; i++) {
                            if (dateCheck == xData[i]) {
                                entries.add(new BarEntry(dateCheck, yData[i]));
                                goalEntries.add(new Entry(dateCheck,goalData[i]));
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            entries.add(new BarEntry(dateCheck, 0));
                            goalEntries.add(new Entry(dateCheck,0));
                        }
                        calorieCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    calorieCal.setTime(queryCompare);

                    // Inserting data into calorie bar graph
                    BarDataSet calorieDataSet = new BarDataSet(entries, "Daily calorie intake");
                    LineDataSet dataSet = new LineDataSet(goalEntries, "Daily goal");
                    dataSet.setColor(getResources().getColor(R.color.black));
                    calorieDataSet.setColor(getResources().getColor(R.color.light_green));
                    LineData lineData = new LineData(dataSet);
                    BarData barData = new BarData(calorieDataSet);
                    lineData.setDrawValues(false);
                    barData.setDrawValues(false);
                    CombinedData combinedData = new CombinedData();
                    combinedData.setData(lineData);
                    combinedData.setData(barData);
                    combinedData.setValueTextSize(12f);
                    calorieChart.setData(combinedData);
                    calorieChart.animateXY(2000, 2000);
                    Description description = calorieChart.getDescription();
                    description.setEnabled(false);

                    // Axes changes
                    XAxis xAxis = calorieChart.getXAxis();
                    xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                    xAxis.setDrawGridLines(false);
                    YAxis leftAxis = calorieChart.getAxisLeft();
                    leftAxis.setDrawLabels(true);
                    leftAxis.setDrawAxisLine(false);
                    YAxis rightAxis = calorieChart.getAxisRight();
                    rightAxis.setDrawLabels(true);
                    rightAxis.setDrawAxisLine(false);

                    // Legend changes
                    Legend legend = calorieChart.getLegend();
                    legend.setForm(Legend.LegendForm.LINE);
                    legend.setTextSize(12f);
                    legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
                    legend.setDrawInside(false);

                    // Load chart
                    calorieChart.invalidate();
                }
            }
        });

        workoutBarChart_previousWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWorkoutChartSetting = databaseHelper.getWorkoutChartViewSetting();
                if (currentWorkoutChartSetting.equals("week")) {
                    workoutCal.add(Calendar.DAY_OF_WEEK, -7);
                    String newBeginWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                    //date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                    float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek, 7);
                    int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date workout_queryCompare = workoutCal.getTime();

                    float workout_dateCheck;
                    boolean workout_DataFound;
                    for (int j = 0; j < 7; j++) {
                        workout_DataFound = false;
                        workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                        for (int i = 0; i < workout_xData.length; i++) {
                            if (workout_dateCheck == workout_xData[i]) {
                                workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                                workout_DataFound = true;
                                break;
                            }
                        }
                        if (!workout_DataFound) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                        }
                        workoutCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    workoutCal.setTime(workout_queryCompare);

                    BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                    workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                    BarData workoutBarData = new BarData(workoutDataSet);
                    workoutChart.setData(workoutBarData);
                    workoutChart.setFitBars(true);
                    workoutChart.animateXY(2000, 2000);
                    Description workoutDescription = workoutChart.getDescription();
                    workoutDescription.setEnabled(false);
                    workoutChart.invalidate();
                }
            }
        });

        workoutBarChart_nextWeekButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWorkoutChartSetting = databaseHelper.getWorkoutChartViewSetting();
                if (currentWorkoutChartSetting.equals("week")) {
                    workoutCal.add(Calendar.DAY_OF_WEEK, 7);
                    String newBeginWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                    //date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                    float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek, 7);
                    int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date workout_queryCompare = workoutCal.getTime();

                    float workout_dateCheck;
                    boolean workout_DataFound;
                    for (int j = 0; j < 7; j++) {
                        workout_DataFound = false;
                        workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                        for (int i = 0; i < workout_xData.length; i++) {
                            if (workout_dateCheck == workout_xData[i]) {
                                workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                                workout_DataFound = true;
                                break;
                            }
                        }
                        if (!workout_DataFound) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                        }
                        workoutCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    workoutCal.setTime(workout_queryCompare);

                    BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                    workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                    BarData workoutBarData = new BarData(workoutDataSet);
                    workoutChart.setData(workoutBarData);
                    workoutChart.setFitBars(true);
                    workoutChart.animateXY(2000, 2000);
                    Description workoutDescription = workoutChart.getDescription();
                    workoutDescription.setEnabled(false);
                    workoutChart.invalidate();
                }
            }
        });

        workoutBarChart_todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String currentWorkoutChartSetting = databaseHelper.getWorkoutChartViewSetting();
                if (currentWorkoutChartSetting.equals("week")) {
                    workoutCal.setTime(origin);
                    String newBeginWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, 6);
                    String newEndWeek = format.format(workoutCal.getTime());
                    workoutCal.add(Calendar.DAY_OF_WEEK, -6);
                    //date.setText("Today's date: " + currentDate + "\n" + "Week of: " + newBeginWeek + "\n" + "End of week: " + newEndWeek);

                    float[] workout_xData = databaseHelper.getCurrentWorkoutDateDay(newBeginWeek, newEndWeek, 7);
                    int[] workout_yData = databaseHelper.getCurrentWorkoutData(newBeginWeek, newEndWeek, 7);

                    List<BarEntry> workoutEntries = new ArrayList<BarEntry>();

                    // queryCompare will set date back to normal after using it to
                    // ensure every date in the query result has a value
                    Date workout_queryCompare = workoutCal.getTime();

                    float workout_dateCheck;
                    boolean workout_DataFound;
                    for (int j = 0; j < 7; j++) {
                        workout_DataFound = false;
                        workout_dateCheck = Float.parseFloat(queryReturnFormat.format(workoutCal.getTime()));
                        for (int i = 0; i < workout_xData.length; i++) {
                            if (workout_dateCheck == workout_xData[i]) {
                                workoutEntries.add(new BarEntry(workout_dateCheck, workout_yData[i]));
                                workout_DataFound = true;
                                break;
                            }
                        }
                        if (!workout_DataFound) {
                            workoutEntries.add(new BarEntry(workout_dateCheck, 0));
                        }
                        workoutCal.add(Calendar.DAY_OF_WEEK, 1);
                    }
                    // Set cal back to normal
                    workoutCal.setTime(workout_queryCompare);

                    BarDataSet workoutDataSet = new BarDataSet(workoutEntries, "Daily workout hours");
                    workoutDataSet.setColor(getResources().getColor(R.color.light_green));
                    BarData workoutBarData = new BarData(workoutDataSet);
                    workoutChart.setData(workoutBarData);
                    workoutChart.setFitBars(true);
                    workoutChart.animateXY(2000, 2000);
                    Description workoutDescription = workoutChart.getDescription();
                    workoutDescription.setEnabled(false);
                    workoutChart.invalidate();
                }
            }
        });

        return rootView;
    }
    private class MyValueFormatter extends ValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return value + " day";
        }
    }
}
