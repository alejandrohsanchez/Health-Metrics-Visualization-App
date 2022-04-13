package com.example.healthmetricsvisualv1alejandrosanchez;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_Profile extends Fragment {

    DatabaseHelper myDB;
    EditText editProfileName, editProfileAge, editProfileWeight,
        editProfileHeightFeet, editProfileHeightInches, editProfileWater,
        editProfileCalories, editProfileWorkout;
    Button btnSaveChanges;

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

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
}
