package com.example.usamaa.workoutapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.usamaa.workoutapp.Utilities.getSelectedItems;

/**
 * Created by usamaa on 3/10/18.
 */

public class MyCustomAdapter extends BaseAdapter implements ListAdapter{
    private ArrayList<String> list = new ArrayList<String>();
    Utilities utils = new Utilities();
    private Context context;

    public MyCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.checked_items, null);
        }

        //Handle TextView and display string from your list
        final TextView tv= (TextView)view.findViewById(R.id.checkTextView);
        tv.setText(list.get(i));


        //Handle buttons and add onClickListeners
       Button delBtn= (Button)view.findViewById(R.id.deleteButton);

       delBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.remove(i);
                notifyDataSetChanged();
            }
        });

        CheckBox checkBtn= (CheckBox) view.findViewById(R.id.checkBox);
           checkBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

               // ArrayList<String> selectedItems = utils.getSelectedItems();
                String selectedItem = tv.getText().toString();

                if (Utilities.getSelectedItems().contains(selectedItem)) {
                    Utilities.removeSelectedItems(selectedItem);
                } else
                    Utilities.addSelectedItems(selectedItem);
                for (int i = 0; i< getSelectedItems().size(); i++){
                    Log.d("tag", getSelectedItems().get(i));
                }
                notifyDataSetChanged();

            }
        });

        return view;
    }

}

