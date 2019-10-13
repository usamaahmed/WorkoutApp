package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button mainQuickStart;
    DatabaseManager db;

    //@RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseManager(this, null, null, 12);


        RelativeLayout rl = (RelativeLayout) findViewById(R.id.mainLayout);
        RelativeLayout.LayoutParams params= new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        setDisplay(rl);
        Utilities utils = new Utilities();
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        utils.setActionBar(getSupportActionBar(), customView);
        addContinueButton(params, rl);

    }

    public void addContinueButton(RelativeLayout.LayoutParams params, RelativeLayout rl){
        params.addRule(RelativeLayout.BELOW, R.id.mainQuickStart);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);
        Button button = new Button(this);
        button.setText("Continue Workout");

        int padding_in_px = getPx(15);
        button.setPadding(padding_in_px, padding_in_px, padding_in_px, padding_in_px);
        button.setLayoutParams(params);
        button.setBackgroundResource(R.drawable.roundedbutton);
        button.setTextAppearance(this, R.style.standardButtonStyle);
        button.setGravity(Gravity.CENTER);

        int height = getScreenHeight();
        ViewGroup.MarginLayoutParams paramsBtn = (ViewGroup.MarginLayoutParams) button
                .getLayoutParams();

        paramsBtn.setMargins(0, height/20, 0, 0);

        button.setLayoutParams(paramsBtn);


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
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());

            String latestDate= db.getLatestDate();
            if (formattedDate.equals(latestDate))
                rl.addView(button);

        }
    }
    /*public void continueWorkout (){
        startActivity(new Intent(MainActivity.this, StartWorkout.class));
    }*/
    public void quickStart(View view) {
        startActivity(new Intent(MainActivity.this, ExerciseList.class));
    }

    public void setDisplay(RelativeLayout rl) {
        int height = getScreenHeight();

        Toast.makeText(this, String.valueOf(height),  Toast.LENGTH_LONG).show();

        TextView intro = (TextView) findViewById(R.id.intro);
        Button quickStartBtn = (Button) findViewById(R.id.mainQuickStart);

        ViewGroup.MarginLayoutParams params1 = (ViewGroup.MarginLayoutParams) intro
                .getLayoutParams();

        ViewGroup.MarginLayoutParams params2 = (ViewGroup.MarginLayoutParams) quickStartBtn
                .getLayoutParams();

        params1.setMargins(0, height/5, 0, 0);
        params2.setMargins(0, height/20, 0, 0);

        intro.setLayoutParams(params1);
        quickStartBtn.setLayoutParams(params2);

    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public int getPx(int padding_in_dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (padding_in_dp * scale + 0.5f);
    }
}
