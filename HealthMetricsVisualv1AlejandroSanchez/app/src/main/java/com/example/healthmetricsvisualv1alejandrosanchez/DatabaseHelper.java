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

        String createWaterTable =   "CREATE TABLE waterTracker" +
                                    "(water_date DATE," +
                                    "water_current INT," +
                                    "water_goal INT);";
        sqLiteDatabase.execSQL(createWaterTable);

        String createCalorieTable = "CREATE TABLE calorieTracker" +
                                    "(calorie_date DATE," +
                                    "calorie_current INT," +
                                    "calorie_goal INT);";
        sqLiteDatabase.execSQL(createCalorieTable);

        String createWorkoutTable = "CREATE TABLE workoutTracker" +
                                    "(workout_date DATE," +
                                    "workout_current INT," +
                                    "workout_goal INT);";
        sqLiteDatabase.execSQL(createWorkoutTable);

        String createWeightTable =  "CREATE TABLE weightTracker" +
                                    "(weight_date DATE," +
                                    "weight_current INT," +
                                    "weight_goal INT);";
        sqLiteDatabase.execSQL(createWeightTable);

        String populate_TestData_water =    "INSERT INTO waterTracker (water_date,water_current,water_goal)" +
                                            "VALUES" +
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
                                            "('2022-04-31', 8, 8)," +
                                            "('2022-05-01', 8, 8)," +
                                            "('2022-05-02', 5, 8)," +
                                            "('2022-05-03', 4, 8)," +
                                            "('2022-05-04', 8, 8)," +
                                            "('2022-05-05', 9, 8)," +
                                            "('2022-05-06', 9, 8)," +
                                            "('2022-05-07', 9, 8)," +
                                            "('2022-05-08', 9, 8)," +
                                            "('2022-05-09', 1, 8)," +
                                            "('2022-05-10', 8, 8)," +
                                            "('2022-05-11', 5, 8)," +
                                            "('2022-05-12', 3, 8)," +
                                            "('2022-05-13', 4, 8)," +
                                            "('2022-05-14', 8, 8)";
        sqLiteDatabase.execSQL(populate_TestData_water);

        String populate_TestData_calorie =  "INSERT INTO calorieTracker (calorie_date,calorie_current,calorie_goal)" +
                                            "VALUES" +
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
                                            "('2022-04-31', 1900, 2000)," +
                                            "('2022-05-01', 1800, 2000)," +
                                            "('2022-05-02', 2000, 2000)," +
                                            "('2022-05-03', 1900, 2000)," +
                                            "('2022-05-04', 2200, 2000)," +
                                            "('2022-05-05', 1600, 2000)," +
                                            "('2022-05-06', 1400, 2000)," +
                                            "('2022-05-07', 1700, 2000)," +
                                            "('2022-05-08', 1900, 2000)," +
                                            "('2022-05-09', 1700, 2000)," +
                                            "('2022-05-10', 2000, 2000)," +
                                            "('2022-05-11', 2100, 2000)," +
                                            "('2022-05-12', 2000, 2000)," +
                                            "('2022-05-13', 1600, 2000)," +
                                            "('2022-05-14', 1800, 2000)";
        sqLiteDatabase.execSQL(populate_TestData_calorie);
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

        db.close();
        return true;
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
        db.close();
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
        db.close();
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
        db.close();
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
        db.close();
        return height;
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
        db.close();
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
        db.close();
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
        db.close();
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
        db.close();
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
        db.close();
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
        db.close();
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
        db.close();
        return goalWorkout;
    }

    // Set goal data -----------------------------------
    public void setUserGoalWater(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_water_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserGoalCalorie(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_calorie_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserGoalWeight(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_weight_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserGoalWorkout(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_workout_goal = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    // Set current data -----------------------------------
    public void setUserCurrentWater(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_water_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserCurrentCalorie(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_calorie_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserCurrentWeight(double val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_weight = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    public void setUserCurrentWorkout(int val) {
        String userName = getUsername();
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "update user set u_workout_current = " + val + " where u_name = '" + userName + "';";
        db.execSQL(queryString);
        db.close();
    }

    // Get graph data
    public int[] getCurrentWaterData(String beginDate, String endDate) {
        int[] data = new int[7];
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
        db.close();
        return data;
    }

    public float[] getCurrentWaterDateDay(String beginDate, String endDate) {
        float[] data = new float[7];
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
        db.close();
        return data;
    }

    public int[] getCurrentCalorieData(String beginDate, String endDate) {
        int[] data = new int[7];
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
        db.close();
        return data;
    }

    public float[] getCurrentCalorieDateDay(String beginDate, String endDate) {
        float[] data = new float[7];
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
        db.close();
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
        db.close();

        return returnList;
    }
}
