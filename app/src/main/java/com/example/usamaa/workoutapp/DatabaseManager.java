package com.example.usamaa.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by usamaa on 1/14/18.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Exercise";
    public static final String TABLE_NAME = "exercise_data";
    public static final String EXERCISE_NAME = "exercise_name";
    public static final String WEIGHT = "weight";
    public static final String SETS = "sets";
    public static final String REPS = "reps";
    public static final String DATE = "date";
    Calendar c = Calendar.getInstance();

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "Create Table" + TABLE_NAME + "("
                        + EXERCISE_NAME + " Text, "
                        + WEIGHT + " Text, "
                        + SETS + " Text, "
                        + REPS + " Text, "
                        + DATE + "Date);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addRow(String exercise_name, String weight, String sets, String reps) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(EXERCISE_NAME, exercise_name);
        values.put(WEIGHT, weight);
        values.put(SETS, sets);
        values.put(REPS, reps);
        values.put(DATE, formattedDate);
        // Inserting Row
        db.insert(TABLE_NAME, null, values);
        db.close(); // Closing database connection
    }

    public void updateGoal(String old_exercise_name, String exercise_name, String weight, String sets, String reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + EXERCISE_NAME + "=\"" + exercise_name + "\", " +
                    WEIGHT +"=\"" + weight + "\", " + SETS + "=\"" + sets + "\", " +
                    REPS +"=\"" + reps + "\", " + DATE + "=\"" + formattedDate
                    + "\" WHERE " + EXERCISE_NAME + "=\"" + old_exercise_name + "\";");
    }

    public void deleteRow(String exercise_name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + EXERCISE_NAME + "=\"" + exercise_name + "\";");
    }

}
