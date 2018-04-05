package com.example.usamaa.workoutapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class StartWorkout extends AppCompatActivity {

    DatabaseManager db;
    TableLayout tl;
    boolean isDataComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);


        Log.d("test", "did it fail?");
        setActionBar();

        fillData();
    }

    public void setActionBar(){
        Utilities utils = new Utilities();
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        utils.setActionBar(getSupportActionBar(), customView);
        TextView Title= (TextView) findViewById(R.id.action_bar_title);
        Title.setText("Set Goals");
    }

    public void fillData(){
        isDataComplete = true;
        String source = getIntent().getStringExtra("Source");
        if(source.equals("fromMain")){
            db = new DatabaseManager(this, null, null, 12);

            String [][] exercise_array = db.getLastWorkout();

            tl = (TableLayout) findViewById(R.id.exerciseTable);
            for (int row = 0; row < exercise_array.length; row++) {
            /* Create a new row to be added. */
                TableRow tr = new TableRow(this);
                TableRow.LayoutParams rowLayout=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                rowLayout.setMargins(20,20,20,20);
                tr.setLayoutParams(rowLayout);
                /* Create a Button to be the row-content. */
                TextView exerciseName = new TextView(this);
                exerciseName.setText(exercise_array[row][0]);
                exerciseName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                EditText weight = new EditText(this);
                weight.setText(exercise_array[row][1]);
                weight.setGravity(Gravity.CENTER_HORIZONTAL);
                weight.setBackgroundResource(R.drawable.border);
                EditText sets = new EditText(this);
                sets.setText(exercise_array[row][2]);
                sets.setBackgroundResource(R.drawable.border);
                sets.setGravity(Gravity.CENTER_HORIZONTAL);
                EditText reps = new EditText(this);
                reps.setText(exercise_array[row][3]);
                reps.setGravity(Gravity.CENTER_HORIZONTAL);
                reps.setBackgroundResource(R.drawable.border);
                exerciseName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.regTextSize));
    /* Add Button to row. */
                //sets.setText("3");
                //reps.setText("10");
                tr.addView(exerciseName);
                tr.addView(weight);
                tr.addView(sets);
                tr.addView(reps);
                tl.addView(tr, rowLayout);
            }
        }
        else {

            ArrayList<String> selectedItems = Utilities.getSelectedItems();

            String items = "";
            for (String item : selectedItems) {
                items += "-" + item + "\n";
            }

            Calendar c = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            String formattedDate = df.format(c.getTime());

            //for (i=0;i++;)
            /* Find Tablelayout defined in main.xml */
            tl = (TableLayout) findViewById(R.id.exerciseTable);
            for (String item : selectedItems) {
            /* Create a new row to be added. */
                TableRow tr = new TableRow(this);
                //tr.setBackgroundResource(R.drawable.border);
                TableRow.LayoutParams rowLayout=new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT);
                rowLayout.setMargins(0,0,0,400);
                tr.setLayoutParams(rowLayout);
                TextView exerciseName = new TextView(this);
                exerciseName.setText(item);
                exerciseName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
                EditText weight = new EditText(this);
                weight.setBackgroundResource(R.drawable.border);
                weight.setGravity(Gravity.CENTER_HORIZONTAL);
                EditText sets = new EditText(this);
                sets.setBackgroundResource(R.drawable.border);
                sets.setGravity(Gravity.CENTER_HORIZONTAL);
                //sets.setLayoutParams(col);
                EditText reps = new EditText(this);
                reps.setBackgroundResource(R.drawable.border);
                reps.setGravity(Gravity.CENTER_HORIZONTAL);
                exerciseName.setTextSize(TypedValue.COMPLEX_UNIT_PX, getResources().getDimension(R.dimen.regTextSize));
                tr.addView(exerciseName);
                tr.addView(weight);
                tr.addView(sets);
                tr.addView(reps);
                tl.addView(tr, rowLayout);
            }
        }

    }
    public void completeWorkOut (View v){

        updateExercise(v);
        db = new DatabaseManager(this, null, null, 12);

        db.updateStatus("Complete");

        if (isDataComplete){
            Intent intent= new Intent (this, ExerciseActivity.class);
            startActivity(intent);
        }
        else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            //add message
            builder.setMessage("Incomplete data. Would you like to go ahead?")
                    .setTitle("Incomplete data");// Add the buttons
            builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent= new Intent (StartWorkout.this, MainActivity.class);
                    startActivity(intent);
                }
            });
            builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog

                }
            });
            // Set other dialog properties

            // Create the AlertDialog
            AlertDialog dialog = builder.create();


            dialog.show();
        }

    }

    public void saveData(View v){
        updateExercise(v);
        db = new DatabaseManager(this, null, null, 12);

        db.updateStatus("Continue");

    }

    public void updateExercise(View v) {
        db = new DatabaseManager(this, null, null, 12);
        int rows = tl.getChildCount();
        String exerciseName;
        String weight;
        String sets;
        String reps;

        for (int i = 0; i < rows; i++) {

            //String exercise = tl.getChildAt(i).getChildAt(1).getText().toString();
            TableRow row = (TableRow) tl.getChildAt(i);

            TextView exercise_view = (TextView) row.getChildAt(0);
            exerciseName = exercise_view.getText().toString();

            TextView weight_view = (TextView) row.getChildAt(1);
            weight = weight_view.getText().toString();

            TextView sets_view = (TextView) row.getChildAt(2);
            sets = sets_view.getText().toString();

            TextView reps_view = (TextView) row.getChildAt(3);
            reps = reps_view.getText().toString();
            if (TextUtils.isEmpty(weight) || TextUtils.isEmpty(sets) || TextUtils.isEmpty(reps)) {
                isDataComplete= false;
                Toast.makeText(this, "One of your input fields is empty", Toast.LENGTH_LONG).show();
                break;
            }

            else
                isDataComplete=true;

            db.updateExercise(exerciseName, weight, sets, reps);
            Toast.makeText(this, "Workout saved", Toast.LENGTH_LONG).show();
        }
    }


    public void viewDatabase(View v) {
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }
}
