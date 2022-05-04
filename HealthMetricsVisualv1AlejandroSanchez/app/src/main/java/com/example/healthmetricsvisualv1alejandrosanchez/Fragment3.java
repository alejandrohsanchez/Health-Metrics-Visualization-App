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
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment3 extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Define
        TextView date;
        Button previousWeekButton, nextWeekButton, todayButton;

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        // Assign Text Views
        date = (TextView) rootView.findViewById(R.id.dateTest);

        // Assign Buttons
        previousWeekButton = rootView.findViewById(R.id.prevWeek_button);
        nextWeekButton = rootView.findViewById(R.id.nextWeek_button);
        todayButton = rootView.findViewById(R.id.today_Button);

        // Getting today's date in custom format -----------
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yy");

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
        cal.add(Calendar.DAY_OF_WEEK, -6);

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
            }
        });

        return rootView;
    }
}
