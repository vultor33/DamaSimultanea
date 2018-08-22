package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.os.Looper;
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
import java.util.logging.Handler;

// TODO remover android colors https://material.io/design/color/the-color-system.html#tools-for-picking-colors
public class MainActivity
        extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener{

    RecyclerView mRecyclerViewer;
    MyRecyclerViewAdapter adapter;

    AppDatabase pieceDatabase;
    List<PieceEntry> allBoardDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheRecyclerViewer();

        pieceDatabase = AppDatabase.getInstance(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                final List<PieceEntry> allBoard = pieceDatabase.taskDao().loadAllPieces();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //allBoardDb = allBoard;
                        adapter.setAllBoardDb(allBoard);//TODO remover essas coisas no main thread
                    }
                });
            }
        });



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
        adapter = new MyRecyclerViewAdapter(context);
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
            adapter.endTurn();

            Log.d("fredmudar", "uma pos:  " + String.valueOf(adapter.getBoardPos(4)));



            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


