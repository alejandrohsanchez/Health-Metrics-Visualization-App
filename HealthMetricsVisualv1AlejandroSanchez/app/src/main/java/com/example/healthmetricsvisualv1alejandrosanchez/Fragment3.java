package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

        // Initialize View
        View rootView = inflater.inflate(R.layout.fragment3_layout, container, false);

        // Assign Text Views
        date = (TextView) rootView.findViewById(R.id.dateTest);

        // Getting today's date in custom format -----------
        // Set the date format
        SimpleDateFormat format = new SimpleDateFormat("MMM-dd-yyyy");

        // Create 2 variables to carry the beginning and the end of the week's date
        String todayDate, nextDate;

        // Calendar objects
        Date calendar = Calendar.getInstance().getTime();
        Date calendar2 = Calendar.getInstance().getTime();

        Calendar changingCal = Calendar.getInstance();
        changingCal.add(Calendar.DATE, 35);

        calendar2 = changingCal.getTime();

        // Set to custom date format
        todayDate = format.format(calendar);
        nextDate = format.format(calendar2);

        // Test print the date
        date.setText("Today's date is: " + todayDate + " -- 35 days from now: " + nextDate);
        return rootView;
    }
}
