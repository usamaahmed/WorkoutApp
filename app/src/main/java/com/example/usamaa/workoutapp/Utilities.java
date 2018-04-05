package com.example.usamaa.workoutapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by usamaa on 2/18/18.
 */

public class Utilities extends AppCompatActivity {

    static ArrayList<String> selectedItems= new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
    }

    public void setActionBar(android.support.v7.app.ActionBar ab, View customView) {
        ab.setDisplayOptions(R.layout.action_bar);
        ab.setCustomView(customView);
        Toolbar parent = (Toolbar) customView.getParent();
        parent.setContentInsetsAbsolute(0,0);
        final Toolbar.LayoutParams lp = new Toolbar.LayoutParams
                (Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.MATCH_PARENT);
        customView.setLayoutParams(lp);
    }

    public static void addSelectedItems(String item){
        selectedItems.add(item);
    }

    public static ArrayList<String> getSelectedItems(){
        return selectedItems;
    }

    //public static void clearSelectedItems()

    public static void removeSelectedItems(String item){
        selectedItems.remove(item);
    }
}

