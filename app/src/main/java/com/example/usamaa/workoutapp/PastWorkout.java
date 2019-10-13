package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class PastWorkout extends AppCompatActivity {

    DatabaseManager db = new DatabaseManager(this, null, null, 15);
    private static RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    String[][] data;
    private static RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_workout);

        ArrayList<String> exerciseNames = getExercises();

        String[][] data = getExerciseData(exerciseNames);
        setActionBar();

        recyclerView = (RecyclerView) findViewById(R.id.pastRecyclerView);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new PastWorkoutCustomAdapter(data);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();

    }

    public void setActionBar(){
        Utilities utils = new Utilities();
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView tv = (TextView) findViewById(R.id.action_bar_title);
        utils.setActionBar(getSupportActionBar(), customView);
    }


    public ArrayList<String> getExercises() {
        return db.getFullExerciseNames();
    }


    public String[][] getExerciseData(ArrayList<String> exerciseNames) {
        String[][] pastExerciseData= new String[exerciseNames.size()][5];
        for (int i=0; i<exerciseNames.size();i++){
            pastExerciseData[i] = db.getLastExercise(exerciseNames.get(i));
        }
        return pastExerciseData;
    }

    public void mainMenu(View view){
        startActivity(new Intent(this, MainActivity.class));
    }

}




// loop get latest data for each exercise