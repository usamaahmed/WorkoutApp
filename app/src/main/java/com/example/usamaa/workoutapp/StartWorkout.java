package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);


        ArrayList<String> selectedItems = (ArrayList<String>) getIntent().getSerializableExtra("selectedItems");

        String items= "";
        for (String item:selectedItems){
            items += "-"+item+"\n";
        }

        Calendar c = Calendar.getInstance();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c.getTime());


        Toast.makeText(this, "The date is" + formattedDate, Toast.LENGTH_LONG).show();

        Toast.makeText(this, "you have selected \n"+ items, Toast.LENGTH_LONG).show();
        //for (i=0;i++;)
        /* Find Tablelayout defined in main.xml */
        tl = (TableLayout) findViewById(R.id.exerciseTable);
        for (String item:selectedItems) {
        /* Create a new row to be added. */
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            /* Create a Button to be the row-content. */
            TextView exerciseName = new TextView(this);
            exerciseName.setText(item);
            exerciseName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT));
            EditText weight= new EditText(this);
            EditText sets= new EditText(this);
            EditText reps = new EditText(this);
/* Add Button to row. */
            //weight.setText("3");
            //sets.setText("3");
            //reps.setText("10");
            tr.addView(exerciseName);
            tr.addView(weight);
            tr.addView(sets);
            tr.addView(reps);
            tl.addView(tr);
        }


    }

    public void updateData(View v) {
        db = new DatabaseManager(this, null, null, 1);
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
                Toast.makeText(this, "One of your input fields is empty", Toast.LENGTH_LONG).show();
                continue;
            }

            else
                db.updateData(exerciseName, weight, sets, reps);
        }
    }

    public void viewDatabase(View v) {
        Intent dbmanager = new Intent(this,AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


}
