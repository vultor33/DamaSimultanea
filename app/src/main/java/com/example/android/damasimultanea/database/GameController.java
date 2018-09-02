package com.example.android.damasimultanea.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

import com.example.android.damasimultanea.AuthenticationHandler;
import com.example.android.damasimultanea.MovementCalculations;
import com.example.android.damasimultanea.MyRecyclerViewAdapter;
import com.example.android.damasimultanea.PieceTypeEnum;
import com.example.android.damasimultanea.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class GameController {

    private String playerName = "";
    private boolean alreadyPlayed = false;
    final private String serverOwner;
    final private String gameKey = "-LLAb5CMF-eGkalyFFZS";
    final private String menuPlay;
    final private String menuWait;
    private MenuItem menuplayItem;

    private FirebaseDatabaseHandler mFirebaseDatabaseHandler;
    final private DatabaseReference mGameHandlerReference;
    private ChildEventListener mChildEventListenerGame;
    private GameHandler gameHandler;


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
    }

    ////////////////////////////   GAME STATUS CONTROLLING /////////////////////////////////////////

    public void setPlayerName(String playerName_in){
        this.playerName = playerName_in;
    }

    public void setMenuItem(MenuItem menuplayItem_in){
        menuplayItem = menuplayItem_in;
        setMenuPlayTitle();
    }

    public boolean isNotReady(){
        return !(isPlayable() && mFirebaseDatabaseHandler.isDatabaseLoaded());
    }

    public void atachDatabaseReadListener(){
        if (mChildEventListenerGame == null) {
            mChildEventListenerGame = new GameController.ChildEventListenerGame();
            mGameHandlerReference.addChildEventListener(mChildEventListenerGame);
        }
        if(mFirebaseDatabaseHandler.isDatabaseLoaded()){
            mFirebaseDatabaseHandler.atachDatabaseReadListener();
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

    private boolean isPlayable() {
        return gameHandler != null && !alreadyPlayed;
    }

    private void setMenuPlayTitle(){
        if(menuplayItem != null) {
            if (isPlayable())
                menuplayItem.setTitle(menuPlay);
            else
                menuplayItem.setTitle(menuWait);
        }
    }

    class ChildEventListenerGame implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            gameHandler = dataSnapshot.getValue(GameHandler.class);
            evaluatePlayableConditions();
            setMenuPlayTitle();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            gameHandler = dataSnapshot.getValue(GameHandler.class);
            evaluatePlayableConditions();
            setMenuPlayTitle();
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

    private void updateGameStatusPosition(PieceTypeEnum playerPiece, int position, int destination){
        if(playerPiece.equals(PieceTypeEnum.pieceA)) {
            mGameHandlerReference.child(gameKey).child("player1MovedPiecePosition").setValue(position);
            mGameHandlerReference.child(gameKey).child("player1MovedPieceDestination").setValue(destination);
        } else {
            mGameHandlerReference.child(gameKey).child("player2MovedPiecePosition").setValue(position);
            mGameHandlerReference.child(gameKey).child("player2MovedPieceDestination").setValue(destination);
        }
        alreadyPlayed = true;
    }

    private void evaluatePlayableConditions(){// TODO mexer nisso aqui
        boolean play1 = false;
        boolean play2 = false;
        if(playerName.equals(serverOwner)){
            if(gameHandler.getPlayer1MovedPieceDestination() != -1)
                alreadyPlayed = true;
        } else {
            if(gameHandler.getPlayer2MovedPiecePosition() != -1)
                alreadyPlayed = true;
        }
        if(gameHandler.getPlayer1MovedPieceDestination() != -1)
            play1 = true;
        if(gameHandler.getPlayer2MovedPieceDestination() != -1)
            play2 = true;
        if(play1 && play2){
            if(playerName.equals(serverOwner))
                mFirebaseDatabaseHandler.resolveAllMovements();
            mFirebaseDatabaseHandler.boardDrawings.drawAllBackground();
            mFirebaseDatabaseHandler.boardDrawings.turnHandler.clearAllPlayersData();
        }
        alreadyPlayed = false;
    }

    /////////////////////////////     BOARD DRAWING    ///////////////////////////////////////////////////

    public int getTableSize(){
        return mFirebaseDatabaseHandler.getTableSize();
    }

    public void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        mFirebaseDatabaseHandler.addHolder(holder,position);
    }

    public void playPiece(int position){
        if(isNotReady())
            return;
        if(playerName.equals(serverOwner))
            mFirebaseDatabaseHandler.playPiece(position, PieceTypeEnum.pieceA);
        else
            mFirebaseDatabaseHandler.playPiece(position, PieceTypeEnum.pieceB);
    }

    public void sendMove(){//refactor isso aqui para sendo move e depois colocar um privtt
        if(!mFirebaseDatabaseHandler.boardDrawings.turnHandler.isTurnEnded())
            return;
        if(playerName.equals(serverOwner)){
            int positionA = mFirebaseDatabaseHandler.boardDrawings.turnHandler.getPlayer1Position();
            int destinationA = mFirebaseDatabaseHandler.boardDrawings.turnHandler.getPlayer1Destination();
            updateGameStatusPosition(PieceTypeEnum.pieceA,positionA,destinationA);
        } else {
            int positionB = mFirebaseDatabaseHandler.boardDrawings.turnHandler.getPlayer2Position();
            int destinationB = mFirebaseDatabaseHandler.boardDrawings.turnHandler.getPlayer2Destination();
            updateGameStatusPosition(PieceTypeEnum.pieceA,positionB,destinationB);
        }
    }

    public void gameEndConditions(){
        mFirebaseDatabaseHandler.gameEndConditions();
    }

    public void drawAllBoard(){
        mFirebaseDatabaseHandler.drawAllBoard();
    }

    public void resetDatabase(){
        mFirebaseDatabaseHandler.resetDatabase();
    }



}
