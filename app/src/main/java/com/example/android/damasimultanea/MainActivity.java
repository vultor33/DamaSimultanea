package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

// android colors https://material.io/design/color/the-color-system.html#tools-for-picking-colors
public class MainActivity
        extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerViewer;
    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheRecyclerViewer();

    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("fredmudar", "CLICKED POSITION: " + String.valueOf(position));
        adapter.playPiece(position);
    }

    void setTheRecyclerViewer(){
        Context context = this;
        mRecyclerViewer = (RecyclerView) findViewById(R.id.rvNumbers);
        int numberOfColumns = 8;
        mRecyclerViewer.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        mRecyclerViewer.setAdapter(adapter);
    }

}


