package com.example.sqldemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

// Type in extends SQLiteOpenHelper and the rest should fill in
// Go over the error and implement methods
// There will be no default constructor available
// This is a problem because we're extending from a parent class
public class DatabaseHelper extends SQLiteOpenHelper {
    // Public constructor found from extends error, or going to SQLOpenHelper site
    public DatabaseHelper(@Nullable Context context) {
        super(context, "customer.db", null, 1);
    }

    // this is called the first time a database is accessed. There should be code in here to create a new database.
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement =   "CREATE TABLE customers (c_id INT, c_name TEXT, c_age INT, c_active BOOL);";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    // this is called if the database version number changes.
    // It prevents previous users apps from breaking when you change the database design.
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(CustomerModel customerModel) {
        // Access database
        SQLiteDatabase db = this.getWritableDatabase();

        // Acts like a hashmap
        ContentValues cv = new ContentValues();

        cv.put("c_name", customerModel.getName());
        cv.put("c_age", customerModel.getAge());
        cv.put("c_active", customerModel.isActive());

        if (cv.get("c_name") == "error" || cv.get("c_name").toString().isEmpty()) {
            return false;
        }
        else {
            // Insert
            long insert = db.insert("customers", null, cv);
            if (insert == -1) {
                return false;
            } else {
                return true;
            }
        }
    }

    public List<CustomerModel> getEveryone() {
        List<CustomerModel> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM customers";
        /* Whenever you open up a writable database, the database becomes locked,
        * so no other operations can be done.
        */
        SQLiteDatabase db = this.getReadableDatabase();

        // Selecting rawQuery that returns a cursor.
        // Introduced local variable. Cursor is result set.
        Cursor cursor = db.rawQuery(queryString, null);

        // Move to the first result in the result set.
        if (cursor.moveToFirst()) {
            // loop through the cursor (result set) and create new customer objects.
            // Put them into the return list.
            do {
                int customerID = cursor.getInt(0);
                String customerName = cursor.getString(1);
                int customerAge = cursor.getInt(2);
                boolean customerActive = cursor.getInt(3) == 1 ? true:false;

                CustomerModel newCustomer = new CustomerModel(customerID, customerName, customerAge, customerActive);
                returnList.add(newCustomer);


            } while (cursor.moveToNext());
        }
        else {
            /*
            * Failure. Do not add anything to the list.
            * */
        }

        // Close both the cursor and the db when done.
        cursor.close();
        db.close();

        return returnList;
    }

}