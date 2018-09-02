package com.example.android.damasimultanea.database;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.example.android.damasimultanea.BoardDrawings;
import com.example.android.damasimultanea.MyRecyclerViewAdapter;
import com.example.android.damasimultanea.PieceTypeEnum;
import com.example.android.damasimultanea.PiecesPositions;
import com.example.android.damasimultanea.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseHandler {

    private int BOARD_SIZE = 64;
    BoardDrawings boardDrawings;

    private PiecesPositions piecesPositions;

    final private DatabaseReference mPieceEntryReference;
    private ChildEventListener mChildEventListenerPieces;
    private ValueEventListener valueEventListener;

    private ArrayList<String> allKeys = new ArrayList<>();

    FirebaseDatabaseHandler(Context context, FirebaseDatabase mFirebaseDatabase_in)
    {
        String boardReference = context.getString(R.string.firebase_board);
        boardDrawings = new BoardDrawings(context);

        mPieceEntryReference = mFirebaseDatabase_in.getReference().child(boardReference);
        mChildEventListenerPieces = null;
        valueEventListener = null;
        piecesPositions = null;

        readAllDatabase();
        //createFireDatabase();// TODO -- BUTTON TO RESET DATABASE
    }

    public boolean isDatabaseLoaded(){
        return piecesPositions != null;
    }

    public void atachDatabaseReadListener(){
        if(isDatabaseLoaded()){
            if (mChildEventListenerPieces == null) {
                mChildEventListenerPieces = new ChildEventListenerPieces();
                mPieceEntryReference.addChildEventListener(mChildEventListenerPieces);
            }
        } else {
            if (valueEventListener == null) {
                readAllDatabase();
            }
        }
    }

    public void detachDatabaseReadListener() {
        if(valueEventListener != null){
            mPieceEntryReference.removeEventListener(valueEventListener);
            valueEventListener = null;
        }
        if(mChildEventListenerPieces != null){
            mPieceEntryReference.removeEventListener(mChildEventListenerPieces);
            mChildEventListenerPieces = null;
        }
    }

    public void readAllDatabase(){
        if(valueEventListener != null)
            return;

        valueEventListener = new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<PieceEntry> allPieces = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    allKeys.add(ds.getKey());
                    PieceEntry piece = ds.getValue(PieceEntry.class);
                    allPieces.add(piece);
                }
                piecesPositions = new PiecesPositions(allPieces);
                boardDrawings.setPiecesPositions(piecesPositions);
                drawAllBoard();
                atachDatabaseReadListener();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        };
        mPieceEntryReference.addListenerForSingleValueEvent(valueEventListener);
    }
    /////////////////////////////     BOARD DRAWING    ///////////////////////////////////////////////////

    public int getTableSize(){
        return BOARD_SIZE;
    }

    public void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        boardDrawings.addHolder(holder,position);
    }

    public void playPiece(int position, PieceTypeEnum playerPiece){
        boardDrawings.playPiece(position, playerPiece);
    }

    public void resolveAllMovements(){
        boardDrawings.resolveAllMovements();
        updateAllPieces(piecesPositions.getAllPieces());
    }

    public void gameEndConditions(){
        boardDrawings.gameEndConditions();
    }

    public void drawAllBoard(){
        boardDrawings.drawAllPieces();
        boardDrawings.drawAllBackground();
    }

    public void resetDatabase(){
        updateAllPieces(defaultFireDatabase());
    }

    public int getPlayerAPosition(){
        return boardDrawings.turnHandler.getPlayer1Position();
    }
    public int getPlayerADestination(){
        return boardDrawings.turnHandler.getPlayer1Destination();
    }
    public int getPlayerBPosition(){
        return boardDrawings.turnHandler.getPlayer2Position();
    }
    public int getPlayerBdestination(){
        return boardDrawings.turnHandler.getPlayer2Destination();
    }

    /////////////////////////////     PRIVATE    ///////////////////////////////////////////////////

    class ChildEventListenerPieces implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            PieceEntry piece = dataSnapshot.getValue(PieceEntry.class);
            savePieceChange(piece);
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

    private void savePieceChange(PieceEntry pieceEntry){
        piecesPositions.setPostionI(pieceEntry.getPosition(),pieceEntry);
        boardDrawings.drawPiece(pieceEntry.getPosition());//Todo colocar na posicao
    }

    private void insertEntry(int position, PieceEntry pieceEntry){
        mPieceEntryReference.push().setValue(pieceEntry);
    }

    private void updateAllPieces(ArrayList<PieceEntry> allPieces){
        for(PieceEntry piece : allPieces){
            updatePiecePosition(piece.getPosition(),piece);
        }
    }

    private void updatePiecePosition(int position, PieceEntry pieceEntry){
        mPieceEntryReference
                .child(allKeys.get(position))
                .setValue(pieceEntry);
    }


    private ArrayList<PieceEntry> defaultFireDatabase(){
        ArrayList<PieceEntry> allPieces = new ArrayList<>();
        allPieces.add(new PieceEntry(0,true,PieceTypeEnum.pieceA,0,0));
        allPieces.add(new PieceEntry(1,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(2,true,PieceTypeEnum.pieceA,0,1));
        allPieces.add(new PieceEntry(3,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(4,true,PieceTypeEnum.pieceA,0,2));
        allPieces.add(new PieceEntry(5,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(6,true,PieceTypeEnum.pieceA,0,3));
        allPieces.add(new PieceEntry(7,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));

        allPieces.add(new PieceEntry(8,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(9,true,PieceTypeEnum.pieceA,1,0));
        allPieces.add(new PieceEntry(10,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(11,true,PieceTypeEnum.pieceA,1,1));
        allPieces.add(new PieceEntry(12,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(13,true,PieceTypeEnum.pieceA,1,2));
        allPieces.add(new PieceEntry(14,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(15,true,PieceTypeEnum.pieceA,1,3));

        allPieces.add(new PieceEntry(16,true,PieceTypeEnum.pieceA,2,0));
        allPieces.add(new PieceEntry(17,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(18,true,PieceTypeEnum.pieceA,2,1));
        allPieces.add(new PieceEntry(19,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(20,true,PieceTypeEnum.pieceA,2,2));
        allPieces.add(new PieceEntry(21,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(22,true,PieceTypeEnum.pieceA,2,3));
        allPieces.add(new PieceEntry(23,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));

        allPieces.add(new PieceEntry(24,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(25,true,PieceTypeEnum.BLANK,3,0));
        allPieces.add(new PieceEntry(26,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(27,true,PieceTypeEnum.BLANK,3,1));
        allPieces.add(new PieceEntry(28,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(29,true,PieceTypeEnum.BLANK,3,2));
        allPieces.add(new PieceEntry(30,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(31,true,PieceTypeEnum.BLANK,3,3));

        allPieces.add(new PieceEntry(32,true,PieceTypeEnum.BLANK,4,0));
        allPieces.add(new PieceEntry(33,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(34,true,PieceTypeEnum.BLANK,4,1));
        allPieces.add(new PieceEntry(35,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(36,true,PieceTypeEnum.BLANK,4,2));
        allPieces.add(new PieceEntry(37,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(38,true,PieceTypeEnum.BLANK,4,3));
        allPieces.add(new PieceEntry(39,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));

        allPieces.add(new PieceEntry(40,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(41,true,PieceTypeEnum.pieceB,5,0));
        allPieces.add(new PieceEntry(42,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(43,true,PieceTypeEnum.pieceB,5,1));
        allPieces.add(new PieceEntry(44,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(45,true,PieceTypeEnum.pieceB,5,2));
        allPieces.add(new PieceEntry(46,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(47,true,PieceTypeEnum.pieceB,5,3));

        allPieces.add(new PieceEntry(48,true,PieceTypeEnum.pieceB,6,0));
        allPieces.add(new PieceEntry(49,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(50,true,PieceTypeEnum.pieceB,6,1));
        allPieces.add(new PieceEntry(51,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(52,true,PieceTypeEnum.pieceB,6,2));
        allPieces.add(new PieceEntry(53,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(54,true,PieceTypeEnum.pieceB,6,3));
        allPieces.add(new PieceEntry(55,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));

        allPieces.add(new PieceEntry(56,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(57,true,PieceTypeEnum.pieceB,7,0));
        allPieces.add(new PieceEntry(58,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(59,true,PieceTypeEnum.pieceB,7,1));
        allPieces.add(new PieceEntry(60,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(61,true,PieceTypeEnum.pieceB,7,2));
        allPieces.add(new PieceEntry(62,false,PieceTypeEnum.NOTPLAYABLE,-1,-1));
        allPieces.add(new PieceEntry(63,true,PieceTypeEnum.pieceB,7,3));
        return allPieces;
    }

}



