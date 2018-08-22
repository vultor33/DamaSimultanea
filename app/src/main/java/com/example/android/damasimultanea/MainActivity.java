package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.android.damasimultanea.database.AppDatabase;
import com.example.android.damasimultanea.database.PieceEntry;

import java.util.List;

// android colors https://material.io/design/color/the-color-system.html#tools-for-picking-colors
public class MainActivity
        extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerViewer;
    MyRecyclerViewAdapter adapter;

    private AppDatabase pieceDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheRecyclerViewer();

        pieceDatabase = AppDatabase.getInstance(getApplicationContext());

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dama_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dama_menu) {

            Log.d("fredmudar","criando");
            PieceEntry pieceEntry = new PieceEntry(7,0,1,2,4);
            Log.d("fredmudar","entry");

            //pieceDatabase.taskDao().insertTask(pieceEntry);

            Log.d("fredmudar", "inserted with sucess");

            List<PieceEntry> allPieces = pieceDatabase.taskDao().loadAllPieces();

            Log.d("fredmudar","inserted:  " + String.valueOf(allPieces.size()));
            for(int i = 0; i < allPieces.size(); i++){
                Log.d("fredmudar", "pos:  " + String.valueOf(allPieces.get(i).getPosition()));
                pieceDatabase.taskDao().deletePiece(allPieces.get(i));
            }

            adapter.endTurn();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


