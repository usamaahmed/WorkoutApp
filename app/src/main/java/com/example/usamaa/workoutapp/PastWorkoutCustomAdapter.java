package com.example.usamaa.workoutapp;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static android.content.ContentValues.TAG;

/**
 * Created by usamaa on 6/7/18.
 */

public class PastWorkoutCustomAdapter extends RecyclerView.Adapter<PastWorkoutCustomAdapter.MyViewHolder> {

    private String [][] dataSet;
    private int position=0;

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private final TextView nametextView;
        private final TextView weightTextView;
        private final TextView setsTextView;
        private final TextView repsTextView;


        public MyViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            this.nametextView = (TextView) v.findViewById(R.id.pastName);
            this.weightTextView = (TextView) v.findViewById(R.id.pastWeight);
            this.setsTextView = (TextView) v.findViewById(R.id.pastSets);
            this.repsTextView = (TextView) v.findViewById(R.id.pastReps);
            Log.d("TAG", "ViewHolder");
        }
    }

    public PastWorkoutCustomAdapter(String[][] data) {
        this.dataSet = data;
        Log.d("TAG", "PastWorkoutCustomAdapter");
        Log.d("TAG", data[0][0]);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        final CardView layout;
        final CardView.LayoutParams params;

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.past_workout_card, parent, false);


            MyViewHolder myViewHolder = new MyViewHolder(view);

        if (dataSet[position][1].equals("0") || dataSet[position][2].equals("0") || dataSet[position][3].equals("0")) {
            params = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.height = 0;
            view.setLayoutParams(params);
        }
            position++;
            return myViewHolder;

    }

    @Override
    public void onBindViewHolder(PastWorkoutCustomAdapter.MyViewHolder holder, int i) {
        if (!dataSet[i][1].equals("0") || !dataSet[i][2].equals("0") || !dataSet[i][3].equals("0")) {
            TextView textViewName = holder.nametextView;
            TextView textViewWeight = holder.weightTextView;
            TextView textViewSets = holder.setsTextView;
            TextView textViewReps = holder.repsTextView;

            textViewName.setText(dataSet[i][0]);
            textViewWeight.setText(dataSet[i][1]);
            textViewSets.setText(dataSet[i][2]);
            textViewReps.setText(dataSet[i][3]);
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
