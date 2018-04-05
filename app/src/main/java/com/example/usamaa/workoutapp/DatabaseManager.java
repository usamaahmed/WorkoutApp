package com.example.usamaa.workoutapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by usamaa on 1/14/18.
 */

public class DatabaseManager extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Exercise";
    public static final String FCT_EXERCISE = "exercise_data";
    public static final String DIM_USER = "dim_user";
    public static final String EXERCISE_NAME = "exercise_name";
    public static final String WEIGHT = "weight";
    public static final String SETS = "sets";
    public static final String REPS = "reps";
    public static final String DATE = "date";
    public static final String STATUS = "status";

    Calendar c = Calendar.getInstance();

    SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
    String formattedDate = df.format(c.getTime());

    public DatabaseManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String exercise_query = "CREATE TABLE IF NOT EXISTS " + FCT_EXERCISE + "("
                        + EXERCISE_NAME + " TEXT, "
                        + WEIGHT + " TEXT, "
                        + SETS + " TEXT, "
                        + REPS + " TEXT, "
                        + DATE + " TEXT);";

        db.execSQL(exercise_query);

        String user_query = "CREATE TABLE IF NOT EXISTS " + DIM_USER + "("
                + STATUS + " TEXT, " + DATE + " TEXT);";

        db.execSQL(user_query);

        Log.d("myTag", "Hey THERERERERRERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERERER");

        //String Query = "INSERT INTO " + DIM_USER + "(status) VALUES (\"Complete\");";
        db.execSQL("INSERT INTO " + DIM_USER+ "(" + STATUS + "," + DATE + ") VALUES ('Complete','" + formattedDate +"')");
        //ContentValues values = new ContentValues();
        //values.put(STATUS, "Complete");
        // Inserting Row
        //db.insert(DIM_USER, null, values);
        //db.close(); // Closing database connection
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + FCT_EXERCISE);
        //db.execSQL("DROP TABLE IF EXISTS " + DIM_USER);
        onCreate(db);
    }

    public void addRow(String exercise_name, String weight, String sets, String reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        String Query = "Select * from " + FCT_EXERCISE + " where " + EXERCISE_NAME + " =\"" + exercise_name
        + "\" AND "  + DATE + "=\"" + formattedDate + "\";";

        Cursor cursor = db.rawQuery(Query, null);
        if(cursor.getCount() <= 0) {
            ContentValues values = new ContentValues();
            values.put(EXERCISE_NAME, exercise_name);
            values.put(WEIGHT, weight);
            values.put(SETS, sets);
            values.put(REPS, reps);
            values.put(DATE, formattedDate);
            // Inserting Row
            db.insert(FCT_EXERCISE, null, values);
            db.close(); // Closing database connection
        }
    }

    public void updateExercise(String exercise_name, String weight, String sets, String reps) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + FCT_EXERCISE + " SET " + EXERCISE_NAME + "=\"" + exercise_name + "\", " +
                    WEIGHT +"=\"" + weight + "\", " + SETS + "=\"" + sets + "\", " +
                    REPS +"=\"" + reps + "\", " + DATE + "=\"" + formattedDate
                    + "\" WHERE " + EXERCISE_NAME + "=\"" + exercise_name + "\" AND " + DATE
                    + "=\"" + formattedDate + "\";");
    }


    public void updateStatus(String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + DIM_USER + " SET " + STATUS + "=\"" + status + "\", " + DATE + "='"
        + formattedDate + "';");
    }


    public String getStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT status FROM " + DIM_USER + ";";
        String value = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            value = cursor.getString(cursor.getColumnIndex("status"));
        }
        return value;
    }

    public String getLatestDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT date FROM " + DIM_USER + ";";
        String value = "";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst()){
            value = cursor.getString(cursor.getColumnIndex("date"));
        }
        return value;
    }

    public String[][] getLastWorkout() {
        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + FCT_EXERCISE + " WHERE " + DATE + "= \"" + formattedDate +"\";";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String[][] exercise_array= new String[cursor.getCount()][5];
        int cPos;
        if(cursor.getCount()>0){
           // exercise_array = new String[cursor.getCount()][5];
            cursor.moveToFirst();
            do { // always prefer do while loop while you deal with database
                cPos = cursor.getPosition();
                exercise_array[cPos][0] = cursor.getString(cursor.getColumnIndex(EXERCISE_NAME));
                exercise_array[cPos][1] = cursor.getString(cursor.getColumnIndex(WEIGHT));
                exercise_array[cPos][2] = cursor.getString(cursor.getColumnIndex(SETS));
                exercise_array[cPos][3] = cursor.getString(cursor.getColumnIndex(REPS));
                exercise_array[cPos][4] = cursor.getString(cursor.getColumnIndex(DATE));
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } else {
            Log.e("SQL Query Error", "Cursor has no data");
        }
        return exercise_array;
    }

    public void deleteRow(String exercise_name){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + FCT_EXERCISE + " WHERE " + EXERCISE_NAME + "=\"" + exercise_name + "\";");
    }

    public ArrayList<Cursor> getData(String Query){
        //get writable database
        SQLiteDatabase sqlDB = this.getWritableDatabase();
        String[] columns = new String[] { "message" };
        //an array list of cursor to save two cursors one has results from the query
        //other cursor stores error message if any errors are triggered
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        MatrixCursor Cursor2= new MatrixCursor(columns);
        alc.add(null);
        alc.add(null);

        try{
            String maxQuery = Query ;
            //execute the query results will be save in Cursor c
            Cursor c = sqlDB.rawQuery(maxQuery, null);

            //add value to cursor2
            Cursor2.addRow(new Object[] { "Success" });

            alc.set(1,Cursor2);
            if (null != c && c.getCount() > 0) {

                alc.set(0,c);
                c.moveToFirst();

                return alc ;
            }
            return alc;
        } catch(SQLException sqlEx){
            Log.d("printing exception", sqlEx.getMessage());
            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        } catch(Exception ex){
            Log.d("printing exception", ex.getMessage());

            //if any exceptions are triggered save the error message to cursor an return the arraylist
            Cursor2.addRow(new Object[] { ""+ex.getMessage() });
            alc.set(1,Cursor2);
            return alc;
        }
    }

}
