package com.example.healthmetricsvisualv1alejandrosanchez;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class SQLiteManager extends SQLiteOpenHelper {

    private static SQLiteManager sqLiteManager;
    private static final String database_name = "NoteDB";
    private static final int database_version = 1;
    private static final String table_name = "Profile";
    private static final String Database_name = "NoteDB";
    private static final String counter = "Counter";

    private static final String userID = "id";
    private static final String userName = "name";
    private static final String userAge = "age";
    private static final String userHeightFeet = "heightFeet";
    private static final String userHeightInches = "heightInches";
    private static final String userWeight = "weight";
    private static final String userWater = "water";
    private static final String userCalories = "calories";
    private static final String userWorkout = "workout";

    private static final DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH::mm::ss");


    public SQLiteManager(Context context) {
        super(context, database_name, null, database_version);
    }

    public static SQLiteManager instanceOfDatabase(Context context){
        if(sqLiteManager == null)
            sqLiteManager = new SQLiteManager(context);

        return sqLiteManager;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE")
                .append(table_name)
                .append("(")
                .append(counter)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(userID)
                .append(" INT, ")
                .append(userName)
                .append(" TEXT, ")
                .append(userAge)
                .append(" INT, ")
                .append(userHeightFeet)
                .append(" INT, ")
                .append(userHeightInches)
                .append(" INT, ")
                .append(userWeight)
                .append(" DOUBLE, ")
                .append(userWater)
                .append(" INT, ")
                .append(userCalories)
                .append(" INT, ")
                .append(userWorkout)
                .append(" DOUBLE)");

        sqLiteDatabase.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }






}
















