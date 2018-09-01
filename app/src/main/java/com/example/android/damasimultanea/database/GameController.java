package com.example.android.damasimultanea.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.damasimultanea.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class GameController {

    final private String serverOwner;
    final private String gameKey = "-LLAb5CMF-eGkalyFFZS";
    final private String menuPlay;
    final private String menuWait;
    private MenuItem menuplayItem;

    FirebaseDatabaseHandler mFirebaseDatabaseHandler;
    private String buttonWait;
    final private DatabaseReference mGameHandlerReference;
    private ChildEventListener mChildEventListenerGame;
    private ValueEventListener valueEventListener;

    private GameHandler gameHandler;
    private ArrayList<PieceEntry> allPieces = new ArrayList<>();
    private ArrayList<String> allKeys = new ArrayList<>();


    // TODO ver uma forma de gerenciar os turnos e atualizar no ponto certo.

    public GameController(Context context, FirebaseDatabase mFirebaseDatabase){
        this.mFirebaseDatabaseHandler = new FirebaseDatabaseHandler(context, mFirebaseDatabase);

        serverOwner = context.getString(R.string.firebase_server_owner);
        String gameReference = context.getString(R.string.firebase_game);
        menuPlay = context.getString(R.string.menu_dama_play);
        menuWait = context.getString(R.string.menu_dama_wait);

        mGameHandlerReference = mFirebaseDatabase.getReference().child(gameReference);
        mChildEventListenerGame = null;
        menuplayItem = null;

        mFirebaseDatabaseHandler.readAllDatabase();
    }

    public void setMenuItem(MenuItem menuplayItem_in){
        menuplayItem = menuplayItem_in;
        setMenuPlayTitle();
    }

    public boolean isReady(){
        return isPlayable() && mFirebaseDatabaseHandler.isDatabaseLoaded();
    }






    


    private boolean isPlayable(){
        return gameHandler.isPlayable();
    }

    private void setMenuPlayTitle(){
        if(menuplayItem != null) {
            if (isReady())
                menuplayItem.setTitle(menuPlay);
            else
                menuplayItem.setTitle(menuWait);
        }
    }


    public void atachDatabaseReadListener(){
        if(mFirebaseDatabaseHandler.isDatabaseLoaded()){
            mFirebaseDatabaseHandler.atachDatabaseReadListener();
            if (mChildEventListenerGame == null) {
                mChildEventListenerGame = new GameController.ChildEventListenerGame();
                mGameHandlerReference.addChildEventListener(mChildEventListenerGame);
            }

        } else {
            mFirebaseDatabaseHandler.readAllDatabase();
        }
    }

    public void detachDatabaseReadListener() {
        mFirebaseDatabaseHandler.detachDatabaseReadListener();
        if(mChildEventListenerGame != null){
            mGameHandlerReference.removeEventListener(mChildEventListenerGame);
            mChildEventListenerGame= null;
        }
    }



    class ChildEventListenerGame implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            GameHandler game = dataSnapshot.getValue(GameHandler.class);
            gameHandler = game;
            setMenuPlayTitle();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            GameHandler game = dataSnapshot.getValue(GameHandler.class);
            gameHandler = game;
            setMenuPlayTitle();
            Log.d("fredmudar", "data changed:  " + String.valueOf(game.getServer()));
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
    }




}
