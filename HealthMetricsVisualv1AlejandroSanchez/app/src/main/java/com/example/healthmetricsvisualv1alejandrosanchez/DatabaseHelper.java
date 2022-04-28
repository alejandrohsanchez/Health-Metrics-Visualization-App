package com.example.healthmetricsvisualv1alejandrosanchez;

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
        String createTableStatement =   "CREATE TABLE users (u_name TEXT, u_age INT, u_height_1 INT, u_height_2 INT, u_weight DECIMAL, u_water_current INT, u_water_daily_goal INT, u_calorie_current INT, u_calorie_daily_goal INT);";
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
        cv.put("u_water_current", userModel.getCurrentWater());
        cv.put("u_water_daily_goal", userModel.getDailyWater());
        cv.put("u_calorie_current", userModel.getCurrentCalorie());
        cv.put("u_calorie_daily_goal", userModel.getDailyCalorie());

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

        db.close();
        return true;
    }

    public String getUsername() {
        String username = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_name FROM users LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            username = cursor.getString(0);
        }
//
        cursor.close();
        db.close();
        return username;
    }

    public String getUserAge() {
        String userage = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_age FROM users LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            userage = cursor.getString(0);
        }
//
        cursor.close();
        db.close();
        return userage;
    }

    public String getUserWeight() {
        String userweight = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_weight FROM users LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            userweight = cursor.getString(0);
        }
//
        cursor.close();
        db.close();
        return userweight;
    }

    public double[] getUserHeight() {
        double[] height = new double [2];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_height_1, u_height_2 from users limit 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            height[0] = cursor.getDouble(0);
            height[1] = cursor.getDouble(1);
        }
        cursor.close();
        db.close();
        return height;
    }

    public int getUserCurrentWater() {
        int currentWater = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_water_current from users;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()) {
            currentWater = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return currentWater;
    }

    public int getUserCurrentCalorie() {
        int currentCalorie = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_calorie_current from users;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            currentCalorie = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return currentCalorie;
    }

    public void setUserCurrentWater(int val) {
        int currentWater = getUserCurrentWater();
        SQLiteDatabase db = this.getReadableDatabase();
//        String queryString = "update users set u_water_current = (replace(u_water_current, currentWater, val));";
//        db.execSQL(queryString);
        db.close();
    }

    public void setUserCurrentCalorie(int val) {
        int currentCalorie = getUserCurrentCalorie();
        SQLiteDatabase db = this.getReadableDatabase();
//        String queryString = "update users set u_calorie_current = replace(u_calorie_current, currentCalorie, val);";
//        db.execSQL(queryString);
        db.close();
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
                int userCurrentWater = cursor.getInt(6);
                int userDailyWater = cursor.getInt(7);
                int userCurrentCalorie = cursor.getInt(8);
                int userDailyCalorie = cursor.getInt(9);

                UserModel newCustomer = new UserModel(userID, userName, userAge, userHeight1, userHeight2, userWeight, userCurrentWater, userDailyWater, userCurrentCalorie, userDailyCalorie);
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
