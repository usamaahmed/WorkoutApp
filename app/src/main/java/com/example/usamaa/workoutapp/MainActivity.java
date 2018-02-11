package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button mainQuickStart;
    DatabaseManager db;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseManager(this, null, null, 12);


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        setDisplay(rl);
        setActionBar();
        addContinueButton(params, rl);

    }

    public void addContinueButton(RelativeLayout.LayoutParams params, RelativeLayout rl){
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

    public void setActionBar() {
        android.support.v7.app.ActionBar ab = getSupportActionBar();

        ab.setDisplayOptions(R.layout.action_bar);
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        ab.setCustomView(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        //parent.setPadding(0,0,0,0);//for tab otherwise give space in tab
        parent.setContentInsetsAbsolute(0,0);


        final Toolbar.LayoutParams lp = new Toolbar.LayoutParams
                (Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        customView.setLayoutParams(lp);

        TextView tv = (TextView) findViewById(R.id.action_bar_title);
        tv.setText("FlexiFit");
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setTextSize(22);
        tv.setGravity(Gravity.CENTER);
    }

    public void setDisplay(RelativeLayout rl) {
        int height = getScreenHeight();

        Toast.makeText(this, String.valueOf(height),  Toast.LENGTH_LONG).show();

        TextView intro = (TextView) findViewById(R.id.intro);
        Button continueBtn = (Button) findViewById(R.id.mainQuickStart);

        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) intro
                .getLayoutParams();

        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) continueBtn
                .getLayoutParams();

        params1.setMargins(0, height/5, 0, 0);
        params2.setMargins(0, height/20, 0, 0);

        intro.setLayoutParams(params1);
        continueBtn.setLayoutParams(params2);

    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
}
