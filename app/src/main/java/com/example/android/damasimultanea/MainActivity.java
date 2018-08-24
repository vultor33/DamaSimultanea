package com.example.android.damasimultanea;

import android.content.Context;
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
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private String mUsername;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTheRecyclerViewer();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mUsername = ANONYMUS;
        PieceEntry pieceEntry = new PieceEntry(4,true, PieceTypeEnum.pieceB,3,4);
        mMessagesDatabaseReference.push().setValue(pieceEntry);


        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    onSignedInInitialize(user.getDisplayName());
                    Toast.makeText(
                            MainActivity.this,
                            "Signer in, Welcome",
                            Toast.LENGTH_SHORT).show();

                }else{
                    onSignedOutCleanup();
                    List<AuthUI.IdpConfig> providers = Arrays.asList(
                            new AuthUI.IdpConfig.EmailBuilder().build());
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setAvailableProviders(providers)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }




    ////////////////////////////  FIREBASE  ///////////////////////////////////////////////////////////
    private void atachDatabaseReadListener(){
        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    PieceEntry pieceEntry = dataSnapshot.getValue(PieceEntry.class);
                    Log.d("fredmudar", "imprimindo:  " + pieceEntry.getPieceType());
                    if(pieceEntry.getPieceType() == PieceTypeEnum.pieceB)
                        Log.d("fredmudar","para a noooossaaaa alegriaaaaa");
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            };
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    private void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mAuthStateListener != null){
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
        detachDatabaseReadListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        atachDatabaseReadListener();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    private void onSignedInInitialize(String username){
        mUsername = username;
        atachDatabaseReadListener();
    }

    private void onSignedOutCleanup(){
        mUsername = ANONYMUS;
        detachDatabaseReadListener();
    }











    ////////////////////////////  FIREBASE  ///////////////////////////////////////////////////////////






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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        adapter.saveDatabase();//TODO as acoes nao estao funcionando quando a tela gira
        super.onStop();
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
        }

        return super.onOptionsItemSelected(item);
    }
}


