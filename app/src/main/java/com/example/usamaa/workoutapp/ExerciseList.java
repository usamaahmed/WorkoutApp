package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExerciseList extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    String newExercise;
    List<String> exercise_list;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> selectedItems= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
        // Get reference of widgets from XML layout
        final ListView lv = (ListView) findViewById(R.id.exerciseList);

        // Initializing a new String Array
        String[] exercises = new String[]{
                "Squat",
                "Bench"
        };

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // Create a List from String Array elements
        exercise_list = new ArrayList<String>(Arrays.asList(exercises));

        // Create an ArrayAdapter from List
        arrayAdapter = new ArrayAdapter<String>
                (this, R.layout.checked_items, exercise_list);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedItem = ((TextView) view).getText().toString();
                if (selectedItems.contains(selectedItem)) {
                    selectedItems.remove(selectedItem);
                } else
                    selectedItems.add(selectedItem);

            }


        });

        // Add new Items to List
        exercise_list.add("Leg Press");
        /*
            notifyDataSetChanged ()
                Notifies the attached observers that the underlying
                data has been changed and any View reflecting the
                data set should refresh itself.
         */
        arrayAdapter.notifyDataSetChanged();

    }

    public void addExercise(View v) {
        newExercise = ((EditText)findViewById(R.id.enterExercise)).getText().toString();
        exercise_list.add(newExercise);
        arrayAdapter.notifyDataSetChanged();
    }

    public void startWorkout (View v){
        Intent i= new Intent (ExerciseList.this, StartWorkout.class);
        i.putExtra("selectedItems", selectedItems);

        startActivity(i);
       /* String items= "";
        for (String item:selectedItems){
            items += "-"+item+"\n";
        }

        Toast.makeText(this, "you have selected \n"+ items, Toast.LENGTH_LONG).show();*/
    }
}
