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

import com.example.healthmetricsvisualv1alejandrosanchez.R;
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
        Button previousWeekButton, nextWeekButton, todayButton;
        BarChart waterChart;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        // Create database object
        databaseHelper = new DatabaseHelper(getActivity());

        // Assign Text Views
        date = (TextView) rootView.findViewById(R.id.dateTest);

        // Assign Buttons
        previousWeekButton = rootView.findViewById(R.id.prevWeek_button);
        nextWeekButton = rootView.findViewById(R.id.nextWeek_button);
        todayButton = rootView.findViewById(R.id.today_Button);

        // Assign graphs
        waterChart = rootView.findViewById(R.id.waterBarChart);

        // Getting today's date in custom format -----------
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat queryReturnFormat = new SimpleDateFormat("dd");

        // Create 3 variables to carry the current date, beginning, and the end of the week's date
        String currentDate, beginWeek, endWeek;
        Calendar cal = Calendar.getInstance();
        // Get the current date
        currentDate = format.format(cal.getTime());

        // Beginning of the current week
        cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
        beginWeek = format.format(cal.getTime());
        // origin will hold onto the original date that beginWeek contains
        Date origin = cal.getTime();

        // Beginning of next week
        cal.add(Calendar.DAY_OF_WEEK, 6);
        endWeek = format.format(cal.getTime());
        cal.add(Calendar.DAY_OF_WEEK, -6); // reset

        // Getting water data
        //String[] xData = databaseHelper.getCurrentWaterDate(beginWeek, endWeek);
        float[] xData = databaseHelper.getCurrentWaterDateDay(beginWeek, endWeek);
        int[] yData = databaseHelper.getCurrentWaterData(beginWeek, endWeek);

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
        waterDataSet.setColor(getResources().getColor(R.color.purple_200));
        BarData barData = new BarData(waterDataSet);
        waterChart.setData(barData);
        waterChart.setFitBars(true);
        waterChart.animateXY(2000,2000);
        Description description = waterChart.getDescription();
        description.setEnabled(false);
        waterChart.invalidate();

        // Update what is on the screen
        date.setText("Today's date: " + currentDate + "\n" + "Week of: " + beginWeek + "\n" + "End of week: " + endWeek);

        // Update dates based on the buttons pressed
        previousWeekButton.setOnClickListener(new View.OnClickListener() {
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
                waterDataSet.setColor(getResources().getColor(R.color.purple_200));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        nextWeekButton.setOnClickListener(new View.OnClickListener() {
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
                waterDataSet.setColor(getResources().getColor(R.color.purple_200));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        todayButton.setOnClickListener(new View.OnClickListener() {
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
                waterDataSet.setColor(getResources().getColor(R.color.purple_200));
                BarData barData = new BarData(waterDataSet);
                waterChart.setData(barData);
                waterChart.setFitBars(true);
                waterChart.animateXY(2000,2000);
                waterChart.invalidate();
            }
        });

        return rootView;
    }
}
