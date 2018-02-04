package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    Button mainQuickStart;
    DatabaseManager db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseManager(this, null, null,11);

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.BELOW, R.id.mainQuickStart);
        Button button = new Button(this);
        button.setText(" Continue Workout");
        button.setTextSize(20);
        button.setGravity(Gravity.CENTER);
        button.setLayoutParams(params);

        //button.setOnClickListener(continueWorkout());

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StartWorkout.class);
                intent.putExtra("Source", "fromMain");
                startActivity(intent);
            }
        });

        String status = db.getStatus();


        if(status.equals("Continue")) {
            //show button
            //go to start workout activity
            //populate table
            rl.addView(button);

        }



    }

    /*public void continueWorkout (){
        startActivity(new Intent(MainActivity.this, StartWorkout.class));
    }*/
    public void quickStart(View view) {
        startActivity(new Intent(MainActivity.this, ExerciseList.class));
    }
}
