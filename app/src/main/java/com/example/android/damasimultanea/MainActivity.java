package com.example.android.damasimultanea;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

// android colors https://material.io/design/color/the-color-system.html#tools-for-picking-colors
public class MainActivity
        extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener{


    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* data to populate the RecyclerView with
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
                "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
                "31", "32", "33", "34", "35", "36", "37", "38", "39", "40",
                "41", "42", "43", "44", "45", "46", "47", "48", "49", "50",
                "51", "52", "53", "54", "55", "56", "57", "58", "59", "60",
                "61", "62", "63", "64"};
                */
        String[] data = {
                "0", "1", "0", "1", "0", "1", "0", "1",
                "1","0", "1", "0", "1", "0", "1", "0",
                "0", "1", "0", "1", "0", "1", "0", "1",
                "1","0", "1", "0", "1", "0", "1", "0",
                "0", "1", "0", "1", "0", "1", "0", "1",
                "1","0", "1", "0", "1", "0", "1", "0",
                "0", "1", "0", "1", "0", "1", "0", "1",
                "1","0", "1", "0", "1", "0", "1", "0"};


                // set up the RecyclerView
        Context context = this;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rvNumbers);
        recyclerView.setHasFixedSize(true);

        int numberOfColumns = 8;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }

}


