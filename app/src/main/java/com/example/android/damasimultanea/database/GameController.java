package com.example.android.damasimultanea.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;

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

    public void updateGameStatus(GameHandler gameChanges){
        mGameHandlerReference.child(gameKey).setValue(gameChanges);
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
        return gameHandler != null && gameHandler.isPlayable();
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
            setMenuPlayTitle();
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            gameHandler = dataSnapshot.getValue(GameHandler.class);
            setMenuPlayTitle();
            Log.d("fredmudar", "data changed:  " + String.valueOf(gameHandler.getServer()));
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
        mFirebaseDatabaseHandler.playPiece(position);
    }

    public void resolveAllMovements(){
        mFirebaseDatabaseHandler.resolveAllMovements();
    }

    public void gameEndConditions(){
        mFirebaseDatabaseHandler.gameEndConditions();
    }

    public void drawAllBoard(){
        mFirebaseDatabaseHandler.drawAllBoard();
    }




    ////////////////////////////   DATABASE CONTROLLING /////////////////////////////////////////
/*

    public PieceTypeEnum whichPiece(int position){
        if(isNotReady())
            return PieceTypeEnum.NOTPLAYABLE;
        else
            return mFirebaseDatabaseHandler.piecesPositions.whichPiece(position);
    }

    public ArrayList<Integer> possibleMovements(int position) {
        if(isNotReady())
            return new ArrayList<>();
        else
            return mFirebaseDatabaseHandler.piecesPositions.possibleMovements(position);
    }

    public boolean isBothPiecesMovable(){
        if(isNotReady())
            return false;
        else
            return mFirebaseDatabaseHandler.piecesPositions.isBothPiecesMovable();
    }

    public PieceTypeEnum avaliateWinningPlayer(){
        if(isNotReady())
            return PieceTypeEnum.BLANK;
        else
            return mFirebaseDatabaseHandler.piecesPositions.avaliateWinningPlayer();
    }

    // ESSES CARAS INFLUENCIAM O BANCO DE DADOS - trabalhar com eles devagar
    public void captureAllPossiblePieces(){
        mFirebaseDatabaseHandler.piecesPositions.captureAllPossiblePieces();
    }
    public void movePiece(int piecePosition, int newPiecePosition){
        if(isNotReady())
            return;
        else
            mFirebaseDatabaseHandler.piecesPositions.movePiece(piecePosition,newPiecePosition);
    }

    public void deletePiece(int position){
        if(isNotReady())
            return;
        else
            mFirebaseDatabaseHandler.piecesPositions.deletePiece(position);
    }

*/

}
