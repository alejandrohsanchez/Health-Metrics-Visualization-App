package com.example.healthmetricsvisualv1alejandrosanchez;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "metrics.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "User Info";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_AGE = "age";
    private static final String COLUMN_HEIGHTFEET = "heightFeet";
    private static final String COLUMN_HEIGHTINCHES = "heightInches";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_WATER = "water";
    private static final String COLUMN_CALORIES = "calories";
    private static final String COLUMN_WORKOUT = "workout";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTableStatement = "CREATE TABLE " + TABLE_NAME +
                        " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + "TEXT, " +
                        COLUMN_AGE + " INTEGER, " +
                        COLUMN_HEIGHTFEET + " INTEGER, " +
                        COLUMN_HEIGHTINCHES + " INTEGER, " +
                        COLUMN_WEIGHT + " DOUBLE, " +
                        COLUMN_WATER + " INTEGER, " +
                        COLUMN_CALORIES + " INTEGER, " +
                        COLUMN_WORKOUT + " DOUBLE);" ;

        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addOne(UserModel userModel) {
        // Access database
        SQLiteDatabase db = this.getWritableDatabase();

        // Acts like a hashmap
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_ID, userModel.getId());
        cv.put(COLUMN_NAME, userModel.getName());
        cv.put(COLUMN_AGE, userModel.getAge());
        cv.put(COLUMN_HEIGHTFEET, userModel.getHeight1());
        cv.put(COLUMN_HEIGHTINCHES, userModel.getHeight2());
        cv.put(COLUMN_WEIGHT, userModel.getWeight());
        cv.put(COLUMN_WATER, userModel.getWater());
        cv.put(COLUMN_CALORIES, userModel.getCalories());
        cv.put(COLUMN_WORKOUT, userModel.getWorkout());

        long result = db.insert(TABLE_NAME, null, cv);

        if(result == 1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }

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
        Cursor cursor = db.rawQuery(queryString, null);

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

                UserModel newCustomer = new UserModel(userID, userName, userAge,
                        userHeight1, userHeight2, userWeight, userWater, userCalories, userWorkout);
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

    public void insertProfileData(String toString, int id, int id1, int id2, int id3, int id4, int id5, int id6) {
    }



/*
    @Override
    public void onUpdate(SQLiteDatabase db, int i, int i1){
        db.execSQL("DROP TABLE IF EXISTS" + );
    }
*/



}
























