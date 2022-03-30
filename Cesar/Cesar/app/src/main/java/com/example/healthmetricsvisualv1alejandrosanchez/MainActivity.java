package com.example.healthmetricsvisualv1alejandrosanchez;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button createButton;
    EditText user_name,user_age, user_height_1, user_height_2, user_weight;
    DatabaseHelper databaseHelper;
    ArrayAdapter userArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Check if user exists before prompting registration
        if (userExists()) openMainActivity2();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createButton = findViewById(R.id.createButton);
        user_name = findViewById(R.id.user_name);
        user_age = findViewById(R.id.user_age);
        user_height_1 = findViewById(R.id.user_height_1);
        user_height_2 = findViewById(R.id.user_height_2);
        user_weight = findViewById(R.id.user_weight);

        databaseHelper = new DatabaseHelper(MainActivity.this);




        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserModel userModel;

                try {
                    userModel = new UserModel(-1, user_name.getText().toString(), Integer.parseInt(user_age.getText().toString()), Integer.parseInt(user_height_1.getText().toString()), Integer.parseInt(user_height_2.getText().toString()), Double.parseDouble(user_weight.getText().toString()));
                    Toast.makeText(MainActivity.this, userModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error creating user", Toast.LENGTH_SHORT).show();
                    userModel = new UserModel(-1, "error", 0, 0, 0, 0);
                }

                DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
                boolean success = databaseHelper.addOne(userModel);
                Toast.makeText(MainActivity.this, "Success=" + success, Toast.LENGTH_SHORT).show();

                // Proceed to the next page
                openMainActivity2();
            }
        });

    }

    public void openMainActivity2() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public boolean userExists() {
        DatabaseHelper databaseHelper;
        databaseHelper = new DatabaseHelper(MainActivity.this);
        String user_name = databaseHelper.getUsername();
        Toast.makeText(MainActivity.this, "Welcome " + user_name, Toast.LENGTH_SHORT).show();
        if (user_name=="") {
            return false;
        } else {
            return true;
        }
    }

}