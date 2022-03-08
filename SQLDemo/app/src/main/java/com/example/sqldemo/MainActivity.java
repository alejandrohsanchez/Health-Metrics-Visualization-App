package com.example.sqldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // references to buttons and other controls that are on the layout
    Button btn_add, btn_viewAll;
    EditText et_name, et_age;
    Switch sw_activeCustomer;
    ListView lv_customerList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // On create starts the application
        // When we create the application, these variables are given a value
        // Find their actual values assigned by the computer

        btn_add = findViewById(R.id.btn_add);
        btn_viewAll = findViewById(R.id.btw_viewAll);
        et_name = findViewById(R.id.et_name);
        et_age = findViewById(R.id.et_age);
        sw_activeCustomer = findViewById(R.id.sw_active);
        lv_customerList = findViewById(R.id.lv_customerList);

        // button listeners for the add and view all buttons

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                CustomerModel customerModel = new CustomerModel(-1, et_name.getText().toString(), Integer.parseInt(et_age.getText().toString()), sw_activeCustomer.isChecked());

                Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_viewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "View All button", Toast.LENGTH_SHORT).show();
            }
        });


    }
}