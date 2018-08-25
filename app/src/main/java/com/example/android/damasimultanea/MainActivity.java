package com.example.android.damasimultanea;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.damasimultanea.database.FirebaseDatabaseHandler;
import com.example.android.damasimultanea.database.PieceEntry;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

// TODO remover android colors https://material.io/design/color/the-color-system.html#tools-for-picking-colors



public class MainActivity
        extends AppCompatActivity
        implements MyRecyclerViewAdapter.ItemClickListener{

    private final String ANONYMUS = "anonymus";
    private final int RC_SIGN_IN = 1;

    RecyclerView mRecyclerViewer;
    MyRecyclerViewAdapter adapter;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;

    FirebaseDatabaseHandler mFirebaseDatabaseHandler;
    AuthenticationHandler mAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheRecyclerViewer();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseDatabaseHandler = new FirebaseDatabaseHandler(mFirebaseDatabase);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mAuthentication = new AuthenticationHandler(this, mFirebaseAuth);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if((requestCode == mAuthentication.getAuthenticationRequestedCode()) && (resultCode == RESULT_CANCELED))
            finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mAuthentication.removeListener();
        mFirebaseDatabaseHandler.detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAuthentication.addListener();
        mFirebaseDatabaseHandler.atachDatabaseReadListener();
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.d("fredmudar", "CLICKED POSITION: " + String.valueOf(position));
        adapter.playPiece(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.dama_menu, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.saveDatabase();//TODO as acoes nao estao funcionando quando a tela gira
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.dama_menu) {
            adapter.endTurn();
            return true;
        } else if (id == R.id.menu_resetdb) {
            adapter.resetDatabase();
            Toast.makeText(
                    this,
                    getString(R.string.toast_reset),
                    Toast.LENGTH_LONG)
                    .show();

            return true;
        } else if (id == R.id.menu_signout){
            mAuthentication.logOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
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




}


