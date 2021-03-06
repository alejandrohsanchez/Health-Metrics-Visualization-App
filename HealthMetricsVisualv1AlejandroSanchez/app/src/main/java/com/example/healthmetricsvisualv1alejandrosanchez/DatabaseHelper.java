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
        // SETTINGS TABLE
        String init_settings =  "CREATE TABLE settings" +
                                    "(waterChartView TEXT," +
                                    "calorieChartView TEXT," +
                                    "workoutChartView TEXT," +
                                    "weightChartView TEXT);";
        sqLiteDatabase.execSQL(init_settings);

        String set_default_settings =    "INSERT INTO settings (waterChartView, calorieChartView, workoutChartView, weightChartView)" +
                                            "VALUES" +
                                            "('week', 'week', 'week', 'week')";
        sqLiteDatabase.execSQL(set_default_settings);

        // USER TABLE
        String createTableStatement =   "CREATE TABLE user" +
                                        "(u_name TEXT," +
                                        "u_age INT," +
                                        "u_height_1 INT," +
                                        "u_height_2 INT," +
                                        "u_weight DECIMAL," +
                                        "u_weight_goal DECIMAL," +
                                        "u_water_current INT," +
                                        "u_water_goal INT," +
                                        "u_calorie_current DECIMAL," +
                                        "u_calorie_goal DECIMAL," +
                                        "u_workout_current INT," +
                                        "u_workout_goal INT);";
        sqLiteDatabase.execSQL(createTableStatement);

        // WATER TRACKER TABLE
        String createWaterTable =   "CREATE TABLE waterTracker" +
                                    "(water_date DATE," +
                                    "water_current INT," +
                                    "water_goal INT);";
        sqLiteDatabase.execSQL(createWaterTable);

        String populate_TestData_water =    "INSERT INTO waterTracker (water_date,water_current,water_goal) " +
                                            "VALUES" +
                                            "('2022-03-01', 10, 8),"+
                                            "('2022-03-02', 6, 8),"+
                                            "('2022-03-03', 6, 8),"+
                                            "('2022-03-04', 5, 8),"+
                                            "('2022-03-05', 2, 8),"+
                                            "('2022-03-06', 2, 8),"+
                                            "('2022-03-07', 0, 8),"+
                                            "('2022-03-08', 1, 8),"+
                                            "('2022-03-09', 10, 8),"+
                                            "('2022-03-10', 10, 8),"+
                                            "('2022-03-11', 3, 8),"+
                                            "('2022-03-12', 8, 8),"+
                                            "('2022-03-13', 5, 8),"+
                                            "('2022-03-14', 1, 8),"+
                                            "('2022-03-15', 4, 8),"+
                                            "('2022-03-16', 3, 8),"+
                                            "('2022-03-17', 8, 8),"+
                                            "('2022-03-18', 5, 8),"+
                                            "('2022-03-19', 0, 8),"+
                                            "('2022-03-20', 1, 8),"+
                                            "('2022-03-21', 5, 8),"+
                                            "('2022-03-22', 10, 8),"+
                                            "('2022-03-23', 9, 8),"+
                                            "('2022-03-24', 5, 8),"+
                                            "('2022-03-25', 6, 8),"+
                                            "('2022-03-26', 3, 8),"+
                                            "('2022-03-27', 10, 8),"+
                                            "('2022-03-28', 12, 8),"+
                                            "('2022-03-29', 2, 8),"+
                                            "('2022-03-30', 5, 8),"+
                                            "('2022-03-31', 6, 8),"+
                                            "('2022-04-01', 7, 8),"+
                                            "('2022-04-02', 0, 8),"+
                                            "('2022-04-03', 5, 8),"+
                                            "('2022-04-04', 5, 8),"+
                                            "('2022-04-05', 4, 8),"+
                                            "('2022-04-06', 5, 8),"+
                                            "('2022-04-07', 10, 8),"+
                                            "('2022-04-08', 15, 8),"+
                                            "('2022-04-09', 7, 8),"+
                                            "('2022-04-10', 1, 8),"+
                                            "('2022-04-11', 5, 8),"+
                                            "('2022-04-12', 0, 8),"+
                                            "('2022-04-13', 2, 8),"+
                                            "('2022-04-14', 5, 8),"+
                                            "('2022-04-15', 5, 8),"+
                                            "('2022-04-16', 2, 8),"+
                                            "('2022-04-17', 10, 8),"+
                                            "('2022-04-18', 8, 8)," +
                                            "('2022-04-19', 6, 8)," +
                                            "('2022-04-20', 7, 8)," +
                                            "('2022-04-21', 4, 8)," +
                                            "('2022-04-22', 0, 8)," +
                                            "('2022-04-23', 3, 8)," +
                                            "('2022-04-24', 2, 8)," +
                                            "('2022-04-25', 8, 8)," +
                                            "('2022-04-26', 9, 8)," +
                                            "('2022-04-27', 9, 8)," +
                                            "('2022-04-28', 13, 8)," +
                                            "('2022-04-29', 7, 8)," +
                                            "('2022-04-30', 8, 8)," +
                                            "('2022-05-01', 8, 8)," +
                                            "('2022-05-02', 5, 8)," +
                                            "('2022-05-03', 4, 8)," +
                                            "('2022-05-04', 8, 8)," +
                                            "('2022-05-05', 9, 8)";
        sqLiteDatabase.execSQL(populate_TestData_water);

        // CALORIE TRACKER TABLE
        String createCalorieTable = "CREATE TABLE calorieTracker" +
                "(calorie_date DATE," +
                "calorie_current INT," +
                "calorie_goal INT);";
        sqLiteDatabase.execSQL(createCalorieTable);

        String populate_TestData_calorie =  "INSERT INTO calorieTracker (calorie_date,calorie_current,calorie_goal)" +
                                            "VALUES" +
                                            "('2022-03-01', 1500, 2000)," +
                                            "('2022-03-02', 1250, 2000)," +
                                            "('2022-03-03', 1300, 2000)," +
                                            "('2022-03-04', 2100, 2000)," +
                                            "('2022-03-05', 2300, 2000)," +
                                            "('2022-03-06', 600, 2000)," +
                                            "('2022-03-07', 2500, 2000)," +
                                            "('2022-03-08', 2000, 2000)," +
                                            "('2022-03-09', 1500, 2000)," +
                                            "('2022-03-10', 1500, 2000)," +
                                            "('2022-03-11', 1800, 2000)," +
                                            "('2022-03-12', 1920, 2000)," +
                                            "('2022-03-13', 3000, 2000)," +
                                            "('2022-03-14', 2600, 2000)," +
                                            "('2022-03-15', 1400, 2000)," +
                                            "('2022-03-16', 900, 2000)," +
                                            "('2022-03-17', 1500, 2000)," +
                                            "('2022-03-18', 1700, 2000)," +
                                            "('2022-03-19', 1360, 2000)," +
                                            "('2022-03-20', 1970, 2000)," +
                                            "('2022-03-21', 2010, 2000)," +
                                            "('2022-03-22', 2590, 2000)," +
                                            "('2022-03-23', 2390, 2000)," +
                                            "('2022-03-24', 1560, 2000)," +
                                            "('2022-03-25', 1395, 2000)," +
                                            "('2022-03-26', 2035, 2000)," +
                                            "('2022-03-27', 1480, 2000)," +
                                            "('2022-03-28', 780, 2000)," +
                                            "('2022-03-29', 2700, 2000)," +
                                            "('2022-03-30', 2380, 2000)," +
                                            "('2022-03-31', 1980, 2000)," +
                                            "('2022-04-01', 1570, 2000)," +
                                            "('2022-04-02', 1950, 2000)," +
                                            "('2022-04-03', 1984, 2000)," +
                                            "('2022-04-04', 3500, 2000)," +
                                            "('2022-04-05', 2020, 2000)," +
                                            "('2022-04-06', 1890, 2000)," +
                                            "('2022-04-07', 1470, 2000)," +
                                            "('2022-04-08', 2040, 2000)," +
                                            "('2022-04-09', 2340, 2000)," +
                                            "('2022-04-10', 1500, 2000)," +
                                            "('2022-04-11', 3000, 2000)," +
                                            "('2022-04-12', 1280, 2000)," +
                                            "('2022-04-13', 1000, 2000)," +
                                            "('2022-04-14', 1600, 2000)," +
                                            "('2022-04-15', 1670, 2000)," +
                                            "('2022-04-16', 1950, 2000)," +
                                            "('2022-04-17', 2370, 2000)," +
                                            "('2022-04-18', 1500, 2000)," +
                                            "('2022-04-19', 1800, 2000)," +
                                            "('2022-04-20', 1600, 2000)," +
                                            "('2022-04-21', 1400, 2000)," +
                                            "('2022-04-22', 1300, 2000)," +
                                            "('2022-04-23', 1400, 2000)," +
                                            "('2022-04-24', 1500, 2000)," +
                                            "('2022-04-25', 2200, 2000)," +
                                            "('2022-04-26', 2100, 2000)," +
                                            "('2022-04-27', 1900, 2000)," +
                                            "('2022-04-28', 2000, 2000)," +
                                            "('2022-04-29', 2000, 2000)," +
                                            "('2022-04-30', 2100, 2000)," +
                                            "('2022-05-01', 1800, 2000)," +
                                            "('2022-05-02', 2000, 2000)," +
                                            "('2022-05-03', 1900, 2000)," +
                                            "('2022-05-04', 2200, 2000)," +
                                            "('2022-05-05', 1600, 2000)";
        sqLiteDatabase.execSQL(populate_TestData_calorie);

        String createWorkoutTable = "CREATE TABLE workoutTracker" +
                "(workout_date DATE," +
                "workout_current INT," +
                "workout_goal INT);";
        sqLiteDatabase.execSQL(createWorkoutTable);

        // WORKOUT TRACKER TABLE
        String populate_TestData_workout =  "INSERT INTO workoutTracker (workout_date,workout_current,workout_goal) " +
                                            "VALUES" +
                                            "('2022-03-01', 10, 25),"+
                                            "('2022-03-02', 36, 25),"+
                                            "('2022-03-03', 67, 25),"+
                                            "('2022-03-04', 15, 25),"+
                                            "('2022-03-05', 20, 25),"+
                                            "('2022-03-06', 20, 25),"+
                                            "('2022-03-07', 0, 25),"+
                                            "('2022-03-08', 0, 25),"+
                                            "('2022-03-09', 15, 25),"+
                                            "('2022-03-10', 30, 25),"+
                                            "('2022-03-11', 30, 25),"+
                                            "('2022-03-12', 25, 25),"+
                                            "('2022-03-13', 15, 25),"+
                                            "('2022-03-14', 10, 25),"+
                                            "('2022-03-15', 14, 25),"+
                                            "('2022-03-16', 13, 25),"+
                                            "('2022-03-17', 18, 25),"+
                                            "('2022-03-18', 5, 25),"+
                                            "('2022-03-19', 0, 25),"+
                                            "('2022-03-20', 10, 25),"+
                                            "('2022-03-21', 125, 25),"+
                                            "('2022-03-22', 100, 25),"+
                                            "('2022-03-23', 90, 25),"+
                                            "('2022-03-24', 25, 25),"+
                                            "('2022-03-25', 60, 25),"+
                                            "('2022-03-26', 30, 25),"+
                                            "('2022-03-27', 0, 25),"+
                                            "('2022-03-28', 0, 25),"+
                                            "('2022-03-29', 25, 25),"+
                                            "('2022-03-30', 15, 25),"+
                                            "('2022-03-31', 60, 25),"+
                                            "('2022-04-01', 60, 25),"+
                                            "('2022-04-02', 60, 25),"+
                                            "('2022-04-03', 30, 25),"+
                                            "('2022-04-04', 35, 25),"+
                                            "('2022-04-05', 45, 25),"+
                                            "('2022-04-06', 45, 25),"+
                                            "('2022-04-07', 10, 25),"+
                                            "('2022-04-08', 15, 25),"+
                                            "('2022-04-09', 17, 25),"+
                                            "('2022-04-10', 10, 25),"+
                                            "('2022-04-11', 15, 25),"+
                                            "('2022-04-12', 20, 25),"+
                                            "('2022-04-13', 20, 25),"+
                                            "('2022-04-14', 25, 25),"+
                                            "('2022-04-15', 25, 25),"+
                                            "('2022-04-16', 25, 25),"+
                                            "('2022-04-17', 10, 25),"+
                                            "('2022-04-18', 12, 25)," +
                                            "('2022-04-19', 20, 25)," +
                                            "('2022-04-21', 15, 25)," +
                                            "('2022-04-20', 0, 25)," +
                                            "('2022-04-22', 0, 25)," +
                                            "('2022-04-23', 22, 25)," +
                                            "('2022-04-24', 21, 25)," +
                                            "('2022-04-25', 0, 25)," +
                                            "('2022-04-26', 30, 25)," +
                                            "('2022-04-27', 10, 25)," +
                                            "('2022-04-28', 13, 25)," +
                                            "('2022-04-29', 14, 25)," +
                                            "('2022-04-30', 0, 25)," +
                                            "('2022-05-01', 0, 25)," +
                                            "('2022-05-02', 18, 25)," +
                                            "('2022-05-03', 17, 25)," +
                                            "('2022-05-04', 0, 25)," +
                                            "('2022-05-05', 15, 25)";

        sqLiteDatabase.execSQL(populate_TestData_workout);

        String createWeightTable =  "CREATE TABLE weightTracker" +
                "(weight_date DATE," +
                "weight_current DECIMAL," +
                "weight_goal DECIMAL);";
        sqLiteDatabase.execSQL(createWeightTable);

        // WEIGHT TRACKER TABLE
        String populate_TestData_weight =   "INSERT INTO weightTracker (weight_date,weight_current,weight_goal) " +
                                            "VALUES" +
                                            "('2022-03-01', 210.7, 180)," +
                                            "('2022-03-02', 210.7, 180)," +
                                            "('2022-03-03', 210.7, 180)," +
                                            "('2022-03-04', 210.5, 180)," +
                                            "('2022-03-05', 210.4, 180)," +
                                            "('2022-03-06', 210, 180)," +
                                            "('2022-03-07', 210.1, 180)," +
                                            "('2022-03-08', 209.9, 180)," +
                                            "('2022-03-09', 209.4, 180)," +
                                            "('2022-03-10', 209.6, 180)," +
                                            "('2022-03-11', 209.1, 180)," +
                                            "('2022-03-12', 208.7, 180)," +
                                            "('2022-03-13', 208.3, 180)," +
                                            "('2022-03-14', 207.7, 180)," +
                                            "('2022-03-15', 207.6, 180)," +
                                            "('2022-03-16', 207.6, 180)," +
                                            "('2022-03-17', 207.6, 180)," +
                                            "('2022-03-18', 207.5, 180)," +
                                            "('2022-03-19', 207.8, 180)," +
                                            "('2022-03-20', 207.1, 180)," +
                                            "('2022-03-21', 206.9, 180)," +
                                            "('2022-03-22', 206.3, 180)," +
                                            "('2022-03-23', 206.1, 180)," +
                                            "('2022-03-24', 205.8, 180)," +
                                            "('2022-03-25', 205.2, 180)," +
                                            "('2022-03-26', 204.8, 180)," +
                                            "('2022-03-27', 204.8, 180)," +
                                            "('2022-03-28', 204.8, 180)," +
                                            "('2022-03-29', 203.5, 180)," +
                                            "('2022-03-30', 201.5, 180)," +
                                            "('2022-03-31', 200.9, 180)," +
                                            "('2022-04-01', 200.3, 180)," +
                                            "('2022-04-02', 199.4, 180)," +
                                            "('2022-04-03', 199.2, 180)," +
                                            "('2022-04-04', 198.7, 180)," +
                                            "('2022-04-05', 198.4, 180)," +
                                            "('2022-04-06', 197.9, 180)," +
                                            "('2022-04-07', 197.5, 180)," +
                                            "('2022-04-08', 196.2, 180)," +
                                            "('2022-04-09', 195.3, 180)," +
                                            "('2022-04-10', 195.9, 180)," +
                                            "('2022-04-11', 194.8, 180)," +
                                            "('2022-04-12', 194.1, 180)," +
                                            "('2022-04-13', 193.4, 180)," +
                                            "('2022-04-14', 192.4, 180)," +
                                            "('2022-04-15', 192.9, 180)," +
                                            "('2022-04-16', 192.6, 180)," +
                                            "('2022-04-17', 191.9, 180)," +
                                            "('2022-04-18', 191.5, 180)," +
                                            "('2022-04-19', 191.4, 180)," +
                                            "('2022-04-20', 191.0, 180)," +
                                            "('2022-04-21', 190.3, 180)," +
                                            "('2022-04-22', 189.5, 180)," +
                                            "('2022-04-23', 189.2, 180)," +
                                            "('2022-04-24', 188.5, 180)," +
                                            "('2022-04-25', 188.0, 180)," +
                                            "('2022-04-26', 187.5, 180)," +
                                            "('2022-04-27', 187.0, 180)," +
                                            "('2022-04-28', 186.7, 180)," +
                                            "('2022-04-29', 186.3, 180)," +
                                            "('2022-04-30', 185.9, 180)," +
                                            "('2022-05-01', 186.0, 180)," +
                                            "('2022-05-02', 184.8, 180)," +
                                            "('2022-05-03', 184.7, 180)," +
                                            "('2022-05-04', 184.5, 180)," +
                                            "('2022-05-05', 184.2, 180)";
        sqLiteDatabase.execSQL(populate_TestData_weight);
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
        cv.put("u_weight_goal", userModel.getGoalWeight());
        cv.put("u_water_current", userModel.getCurrentWater());
        cv.put("u_water_goal", userModel.getGoalWater());
        cv.put("u_calorie_current", userModel.getCurrentCalorie());
        cv.put("u_calorie_goal", userModel.getGoalCalorie());
        cv.put("u_workout_current", userModel.getCurrentWorkout());
        cv.put("u_workout_goal", userModel.getGoalWorkout());

        long insert = db.insert("user", null, cv);
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

    // Get settings information -----------------------------------
    public String getWaterChartViewSetting() {
        String setting = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select waterChartView from settings;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            setting = cursor.getString(0);
        }
        cursor.close();

        return setting;
    }

    public String getCalorieChartViewSetting() {
        String setting = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select calorieChartView from settings;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            setting = cursor.getString(0);
        }
        cursor.close();

        return setting;
    }

    public String getWorkoutChartViewSetting() {
        String setting = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select workoutChartView from settings;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            setting = cursor.getString(0);
        }
        cursor.close();

        return setting;
    }

    public String getWeightChartViewSetting() {
        String setting = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select weightChartView from settings;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            setting = cursor.getString(0);
        }
        cursor.close();

        return setting;
    }

    // Set settings -----------------------------------
    public void setWaterChartViewSetting(String setting) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update settings set waterChartView = '" + setting + "';";
        db.execSQL(queryString);

    }

    public void setCalorieChartViewSetting(String setting) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update settings set calorieChartView = '" + setting + "';";
        db.execSQL(queryString);

    }

    public void setWorkoutChartViewSetting(String setting) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update settings set workoutChartView = '" + setting + "';";
        db.execSQL(queryString);

    }

    public void setWeightChartViewSetting(String setting) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update settings set weightChartView = '" + setting + "';";
        db.execSQL(queryString);

    }

    // Get personal information -----------------------------------
    public String getUsername() {
        String username = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_name FROM user LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            username = cursor.getString(0);
        }
//
        cursor.close();

        return username;
    }

    public String getUserAge() {
        String userage = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_age FROM user LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            userage = cursor.getString(0);
        }
//
        cursor.close();

        return userage;
    }

    public String getUserWeight() {
        String userweight = "";

        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT u_weight FROM user LIMIT 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            userweight = cursor.getString(0);
        }
//
        cursor.close();

        return userweight;
    }

    public double[] getUserHeight() {
        double[] height = new double [2];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_height_1, u_height_2 from user limit 1;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()){
            height[0] = cursor.getDouble(0);
            height[1] = cursor.getDouble(1);
        }
        cursor.close();

        return height;
    }

    // Replace current tracker data
    public void replaceWaterTrackerDateEntry(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        int current = getUserCurrentWater();
        int goal = getUserGoalWater();
        //waterTracker (water_date,water_current,water_goal)
        String deleteSring =    "DELETE FROM waterTracker WHERE water_date = '" + date + "';";
        db.execSQL(deleteSring);
        String populateWaterTracker =   "INSERT INTO waterTracker (water_date,water_current,water_goal) " +
                "VALUES" +
                "('" + date + "'," + current + "," + goal + ");";
        db2.execSQL(populateWaterTracker);
    }

    public void replaceCalorieTrackerDateEntry(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        double current = getUserCurrentCalorie();
        double goal = getUserGoalCalorie();
        //waterTracker (water_date,water_current,water_goal)
        String deleteSring =    "DELETE FROM calorieTracker WHERE calorie_date = '" + date + "';";
        db.execSQL(deleteSring);
        String populateCalorieTracker =   "INSERT INTO calorieTracker (calorie_date,calorie_current,calorie_goal) " +
                "VALUES" +
                "('" + date + "'," + current + "," + goal + ");";
        db2.execSQL(populateCalorieTracker);
    }

    public void replaceWorkoutTrackerDateEntry(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        SQLiteDatabase db2 = this.getReadableDatabase();
        double current = getUserCurrentWorkout();
        double goal = getUserGoalWorkout();
        //waterTracker (water_date,water_current,water_goal)
        String deleteSring =    "DELETE FROM workoutTracker WHERE workout_date = '" + date + "';";
        db.execSQL(deleteSring);
        String populateWorkoutTracker =   "INSERT INTO workoutTracker (workout_date,workout_current,workout_goal) " +
                "VALUES" +
                "('" + date + "'," + current + "," + goal + ");";
        db2.execSQL(populateWorkoutTracker);
    }

    // Current data -----------------------------------
    public int getUserCurrentWater() {
        int currentWater = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_water_current from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()) {
            currentWater = cursor.getInt(0);
        }
        cursor.close();

        return currentWater;
    }

    public double getUserCurrentCalorie() {
        int currentCalorie = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_calorie_current from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            currentCalorie = cursor.getInt(0);
        }
        cursor.close();

        return currentCalorie;
    }

    public int getUserCurrentWorkout() {
        int currentWorkout = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_workout_current from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()) {
            currentWorkout = cursor.getInt(0);
        }
        cursor.close();

        return currentWorkout;
    }

    // Get goal data -----------------------------------
    public double getUserGoalWeight() {
        double goalWeight = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_weight_goal from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            goalWeight = cursor.getDouble(0);
        }
        cursor.close();

        return goalWeight;
    }

    public int getUserGoalWater() {
        int goalWater = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_water_goal from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while(cursor.moveToNext()) {
            goalWater = cursor.getInt(0);
        }
        cursor.close();

        return goalWater;
    }

    public double getUserGoalCalorie() {
        double goalCalorie = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_calorie_goal from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            goalCalorie = cursor.getInt(0);
        }
        cursor.close();

        return goalCalorie;
    }

    public int getUserGoalWorkout() {
        int goalWorkout = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "select u_workout_goal from user;";
        Cursor cursor = db.rawQuery(queryString, null);

        while (cursor.moveToNext()) {
            goalWorkout = cursor.getInt(0);
        }
        cursor.close();

        return goalWorkout;
    }

    // Set goal data -----------------------------------
    public void setUserGoalWater(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_water_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserGoalCalorie(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_calorie_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserGoalWeight(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_weight_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserGoalWorkout(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_workout_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    // Set current data -----------------------------------
    public void setUserCurrentWater(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_water_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserCurrentCalorie(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_calorie_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserCurrentWeight(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_weight = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    public void setUserCurrentWorkout(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_workout_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);

    }

    // Get graph data
    public int[] getCurrentWaterData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select water_current from waterTracker " +
                "where water_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by water_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }

    public float[] getCurrentWaterDateDay(String beginDate, String endDate, int numVal) {
        float[] data = new float[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select strftime('%d', water_date) from waterTracker " +
                "where water_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by water_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getFloat(0);
            }
        }

        cursor.close();

        return data;
    }

    public int[] getCurrentWaterGoalData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select water_goal from waterTracker " +
                "where water_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by water_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }

    public int[] getCurrentCalorieData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select calorie_current from calorieTracker " +
                "where calorie_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by calorie_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }

    public float[] getCurrentCalorieDateDay(String beginDate, String endDate, int numVal) {
        float[] data = new float[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select strftime('%d', calorie_date) from calorieTracker " +
                "where calorie_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by calorie_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getFloat(0);
            }
        }

        cursor.close();

        return data;
    }

    public int[] getCurrentCalorieGoalData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select calorie_goal from calorieTracker " +
                "where calorie_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by calorie_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }

    public int[] getCurrentWorkoutData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select workout_current from workoutTracker " +
                "where workout_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by workout_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }

    public float[] getCurrentWorkoutDateDay(String beginDate, String endDate, int numVal) {
        float[] data = new float[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select strftime('%d', workout_date) from workoutTracker " +
                "where workout_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by workout_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getFloat(0);
            }
        }

        cursor.close();

        return data;
    }

    public int[] getCurrentWorkoutGoalData(String beginDate, String endDate, int numVal) {
        int[] data = new int[numVal];
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString =    "select workout_goal from workoutTracker " +
                "where workout_date between '" + beginDate + "' and '" + endDate + "' " +
                "order by workout_date asc;";
        Cursor cursor = db.rawQuery(queryString, null);

        for (int i=0; i < data.length; i++) {
            if (cursor.moveToNext()) {
                data[i] = cursor.getInt(0);
            }
        }

        cursor.close();

        return data;
    }
    // Not used or for reference -----------------------------------
    public List<UserModel> getEveryone() {
        List<UserModel> returnList = new ArrayList<>();

        // get data from the database
        String queryString = "SELECT * FROM user";
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


                // If using this object, update to match current arguments
                //UserModel newCustomer = new UserModel(userID, userName, userAge, userHeight1, userHeight2, userWeight, userCurrentWater, userDailyWater, userCurrentCalorie, userDailyCalorie);
                //returnList.add(newCustomer);


            } while (cursor.moveToNext());
        }
        else {
            /*
             * Failure. Do not add anything to the list.
             * */
        }

        // Close both the cursor and the db when done.
        cursor.close();


        return returnList;
    }
}
