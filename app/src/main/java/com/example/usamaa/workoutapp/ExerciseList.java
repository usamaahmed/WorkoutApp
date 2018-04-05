package com.example.usamaa.workoutapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.usamaa.workoutapp.Utilities.getSelectedItems;
import static com.example.usamaa.workoutapp.Utilities.selectedItems;

public class ExerciseList extends AppCompatActivity {

    ArrayList<String> listItems=new ArrayList<String>();
    DatabaseManager db;
    ListView lv;

    //DEFINING A STRING ADAPTER WHICH WILL HANDLE THE DATA OF THE LISTVIEW
    ArrayAdapter<String> adapter;
    String newExercise;
    ArrayList<String> exercise_list;
    MyCustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBar();

        Utilities.selectedItems.clear();
        setContentView(R.layout.activity_exercise_list);
        // Get reference of widgets from XML layout
        lv = (ListView) findViewById(R.id.exerciseList);

        // Initializing a new String Array
        String[] exercises = new String[]{
                "Squat",
                "Bench"
        };

        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // Create a List from String Array elements
        exercise_list = new ArrayList<String>(Arrays.asList(exercises));

        // Create an ArrayAdapter from List
        customAdapter = new MyCustomAdapter(exercise_list, this);

        // DataBind ListView with items from ArrayAdapter
        lv.setAdapter(customAdapter);

        TextView v= (TextView) findViewById(R.id.checkTextView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ExerciseList.this, "parent: " + parent + " View: " + view + " position: " + position + " id: " + id, Toast.LENGTH_LONG).show();
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
        customAdapter.notifyDataSetChanged();

    }

    public void setActionBar(){
        Utilities utils = new Utilities();
        View customView = getLayoutInflater().inflate(R.layout.action_bar, null);
        TextView tv = (TextView) findViewById(R.id.action_bar_title);
        utils.setActionBar(getSupportActionBar(), customView);
    }

    public void addExercise(View v) {
        newExercise = ((EditText)findViewById(R.id.enterExercise)).getText().toString();
        if (!TextUtils.isEmpty(newExercise)) {
             if (!containsCaseInsensitive(newExercise, exercise_list)) {
                 exercise_list.add(newExercise);
             }
             else {
                 Toast.makeText(this, "The exercise is already in the list", Toast.LENGTH_LONG).show();
             }
         }
        else {
             Toast.makeText(this, "You haven't entered an exercise", Toast.LENGTH_LONG).show();
         }
        customAdapter.notifyDataSetChanged();
        lv.smoothScrollToPosition(customAdapter.getCount());
    }

    public void startWorkout (View v){
        //ArrayList<String> selectedItems = utils.getSelectedItems();
        for (int i = 0; i< getSelectedItems().size(); i++){
            Log.d("tag", getSelectedItems().get(i));
        }
        if(Utilities.getSelectedItems().size() > 0) {
            Intent i = new Intent(ExerciseList.this, ExerciseActivity.class);
            i.putExtra("Source", "fromExerciseList");

            startActivity(i);

            db = new DatabaseManager(this, null, null, 12);
            db.updateStatus("Continue");
            int rows = selectedItems.size();
            String exerciseName;
            for (int j = 0; j < rows; j++) {
                exerciseName = selectedItems.get(j);
                db.addRow(exerciseName, "0", "0", "0");
            }
        }
        else {
            Toast.makeText(this, "Please select at least one exercise", Toast.LENGTH_LONG).show();
        }
    }

    public boolean containsCaseInsensitive(String s, List<String> l){
        for (String string : l){
            if (string.equalsIgnoreCase(s)){
                return true;
            }
        }
        return false;
    }

}
