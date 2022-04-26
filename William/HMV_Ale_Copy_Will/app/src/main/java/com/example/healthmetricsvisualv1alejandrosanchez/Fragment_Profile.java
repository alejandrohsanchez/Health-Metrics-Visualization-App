package com.example.healthmetricsvisualv1alejandrosanchez;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.List;

public class Fragment_Profile extends Fragment {

    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper myDB;
    EditText editProfileName, editProfileAge, editProfileWeight,
        editProfileHeightFeet, editProfileHeightInches, editProfileWater,
        editProfileCalories, editProfileWorkout;
    Button btnSaveChanges;
    Cursor cursor;
    //List<DatabaseHelper> la;

    private EditText profileText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmentprofile_layout, container, false);

        myDB = new DatabaseHelper(getActivity());

        editProfileName = (EditText) rootView.findViewById(R.id.name_profile);
        editProfileAge = (EditText) rootView.findViewById(R.id.age_profile);
        editProfileWeight = (EditText) rootView.findViewById(R.id.weight_profile);
        editProfileHeightFeet = (EditText) rootView.findViewById(R.id.heightFeet_profile);
        editProfileHeightInches = (EditText) rootView.findViewById(R.id.heightInches_profile);
        editProfileWater = (EditText) rootView.findViewById(R.id.water_profile);
        editProfileCalories = (EditText) rootView.findViewById(R.id.calories_profile);
        editProfileWorkout = (EditText) rootView.findViewById(R.id.workout_profile);

        btnSaveChanges = (Button) rootView.findViewById(R.id.saveChangesButton_profile);

        addData();

        Bundle data = getArguments();
        if(data != null){
            
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sqLiteDatabase = myDB.getReadableDatabase();
        cursor = myDB.userData();

        if(cursor.moveToFirst()){
            do{
                String name, age, weight, heightFeet, heightInches, water, calories, workout;

                name = cursor.getString(0);
                age = cursor.getString(1);
                heightFeet = cursor.getString(2);
                heightInches = cursor.getString(3);
                weight = cursor.getString(4);
                water = cursor.getString(5);
                calories = cursor.getString(6);
                workout = cursor.getString(7);

                //DatabaseHelper newDB = new DatabaseHelper(name, age, weight, heightFeet, heightInches, water, calories, workout);
                //la.add(myDB);

                editProfileName.setText(name);
                editProfileAge.setText(age);
                editProfileWeight.setText(weight);
                editProfileHeightFeet.setText(heightFeet);
                editProfileHeightInches.setText(heightInches);
                editProfileWater.setText(water);
                editProfileCalories.setText(calories);
                editProfileWorkout.setText(workout);
            } while(cursor.moveToNext());
        }

    }

    private void addData() {
        btnSaveChanges.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                myDB.insertProfileData(
                        editProfileName.getText().toString(),
                        editProfileAge.getId(),
                        editProfileWeight.getId(),
                        editProfileHeightFeet.getId(),
                        editProfileHeightInches.getId(),
                        editProfileWater.getId(),
                        editProfileCalories.getId(),
                        editProfileWorkout.getId()
                );


            }
        });
    }
}


    /*
        onViewCreate(){
            nestedfragment()
      }

      nestedfragment(){

      }

     */

