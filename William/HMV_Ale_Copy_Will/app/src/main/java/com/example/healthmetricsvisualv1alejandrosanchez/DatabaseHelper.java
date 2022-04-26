package com.example.healthmetricsvisualv1alejandrosanchez;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(@Nullable Context context) {
        super(context, "metrics.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE users " +
                "(u_name TEXT, " +
                "u_age INT, " +
                "u_height_1 INT, " +
                "u_height_2 INT, " +
                "u_weight DECIMAL, " +
                "u_water INT, " +
                "u_calories INT, " +
                "u_workout DECIMAL );";

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addOne(UserModel userModel) {
        // Access database
        SQLiteDatabase db = this.getWritableDatabase();

        // Acts like a hashmap
        ContentValues cv = new ContentValues();

        cv.put("u_name", userModel.getName());
        cv.put("u_age", userModel.getAge());
        cv.put("u_height_1", userModel.getHeight1());
        cv.put("u_height_2", userModel.getHeight2());
        cv.put("u_weight", userModel.getWeight());
        cv.put("u_water", userModel.getWater());
        cv.put("u_calories", userModel.getCalories());
        cv.put("u_workout", userModel.getWorkout());

        long insert = db.insert("users", null, cv);

//        if (cv.get("c_name") == "error" || cv.get("c_name").toString().isEmpty()) {
//            return false;
//        }
//        else {
//            // Insert
//            long insert = db.insert("customers", null, cv);
//            if (insert == -1) {
//                return false;
//            } else {
//                return true;
//            }
//        }
        return true;
    }

    public String getUsername() {
        String username = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_name FROM users LIMIT 1;";
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            username = cursor.getString(0);
        }
//
//        cursor.close();
        return username;
    }

    public List<UserModel> getEveryone() {
        List<UserModel> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM users";
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
                int userID = cursor.getInt(0);
                String userName = cursor.getString(1);
                int userAge = cursor.getInt(2);
                int userHeight1 = cursor.getInt(3);
                int userHeight2 = cursor.getInt(4);
                double userWeight = cursor.getDouble(5);
                int userWater = cursor.getInt(6);
                int userCalories = cursor.getInt(7);
                double userWorkout = cursor.getDouble(8);

                UserModel newCustomer = new UserModel(userID, userName, userAge, userHeight1, userHeight2, userWeight, userWater, userCalories, userWorkout);
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

    public Cursor userData(){
        SQLiteDatabase dbl = this.getWritableDatabase();
        Cursor cursor = dbl.rawQuery("SELECT * FROM users", null);
        return cursor;

    }

    public void insertProfileData(String toString, int id, int id1, int id2, int id3, int id4, int id5, int id6) {
    }
}
