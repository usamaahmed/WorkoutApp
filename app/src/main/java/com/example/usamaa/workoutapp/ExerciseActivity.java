package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ExerciseActivity extends AppCompatActivity {

    DatabaseManager db;
    TableLayout tl;
    boolean isDataComplete;
    int i = 0;
    ArrayList<String> selectedItems = Utilities.getSelectedItems();
    TextView exercise;
    Button complete;
    SharedPreferences sharedPrefs;
    private static final String TAG = "ExerciseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();
        setContentView(R.layout.activity_exercise);
        exercise = (TextView) findViewById(R.id.exerciseName);
        exercise.setText(selectedItems.get(0));
        if(i==selectedItems.size()-1){
            complete = (Button) findViewById(R.id.activityNext);
            complete.setText("Finish Work Out");
        }
    }

    public void setActionBar(){
        Utilities utils = new Utilities();
        Log.d("tag", "util:" + utils);
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        Log.d("tag", "customeView:" + customView);
        TextView tv = (TextView) findViewById(R.id.action_bar_title);
        Log.d("tag", "Textview working");
        utils.setActionBar(getSupportActionBar(), customView);
        Log.d("tag", "setActionBar fuckedup");
    }

    public void nextExercise(View v) {
        int size = selectedItems.size()-i;
        i++;
        ArrayList<String> unfinishedExercises = new ArrayList<String>();


        for (int j=0;j< size-1;j++) {
            unfinishedExercises.add(selectedItems.get(i+j));
            Log.d("TAG", j + "  " + selectedItems.get(i+j));
        }


        storeExercises(unfinishedExercises);
        updateExercise();
        clearText();

        if(i==selectedItems.size()-1){
            complete = (Button) findViewById(R.id.activityNext);
            complete.setText("Finish Work Out");
            exercise.setText(selectedItems.get(i));
        }

        else if(i==selectedItems.size()){

            startActivity(new Intent(this, MainActivity.class));
        }

        else
            exercise.setText(selectedItems.get(i));
    }

    public void updateExercise() {

        db = new DatabaseManager(this, null, null, 12);
        String exerciseName;
        String weight;
        String sets;
        String reps;

        exerciseName= exercise.getText().toString();

        EditText wView= (EditText) findViewById(R.id.activityWeight);
        weight= wView.getText().toString();

        EditText sView= (EditText) findViewById(R.id.activitySets);
        sets= sView.getText().toString();

        EditText rView= (EditText) findViewById(R.id.activityReps);
        reps= rView.getText().toString();



        if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(sets) || TextUtils.isEmpty(reps)) {
            isDataComplete= false;
            Toast.makeText(this, "One of your input fields is empty", Toast.LENGTH_LONG).show();
        }

        else {
            isDataComplete = true;
            db.updateExercise(exerciseName, weight, sets, reps);
            Toast.makeText(this, "Workout saved", Toast.LENGTH_LONG).show();

        }

    }

    public void clearText() {

        EditText wView= (EditText) findViewById(R.id.activityWeight);
        wView.getText().clear();

        EditText sView= (EditText) findViewById(R.id.activitySets);
        sView.getText().clear();

        EditText rView= (EditText) findViewById(R.id.activityReps);
        rView.getText().clear();
    }

    public void storeExercises(ArrayList<String> selectedItems) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        Gson gson = new Gson();

        String json = gson.toJson(selectedItems);

        editor.putString(TAG, json);
        editor.commit();

        String json1 = sharedPrefs.getString(TAG, null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> arrayList = gson.fromJson(json1, type);
        for (int j=0;j< arrayList.size();j++) {
            Log.d("TAG", j + " proof:  " + arrayList.get(j));
        }
    }
}
