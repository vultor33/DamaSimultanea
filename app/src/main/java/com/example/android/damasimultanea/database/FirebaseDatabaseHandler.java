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
                Log.d("fredmudar","terminou");
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

    public void playPiece(int position){
        boardDrawings.playPiece(position);
    }

    public void resolveAllMovements(){
        boardDrawings.resolveAllMovements();
        ArrayList<PieceEntry> allPieces = piecesPositions.getAllPieces();
        for(PieceEntry piece : allPieces){
            updatePiecePosition(piece.getPosition(),piece);
        }
    }

    public void gameEndConditions(){
        boardDrawings.gameEndConditions();
    }

    public void drawAllBoard(){
        boardDrawings.drawAllPieces();
        boardDrawings.drawAllBackground();
    }

    /////////////////////////////     PRIVATE    ///////////////////////////////////////////////////

    private void updatePiecePosition(int position, PieceEntry pieceEntry){
        mPieceEntryReference
                .child(allKeys.get(position))
                .setValue(pieceEntry);
    }

    class ChildEventListenerPieces implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            PieceEntry piece = dataSnapshot.getValue(PieceEntry.class);
            Log.d("fredmudar", "data changed:  " + String.valueOf(piece.getPosition()));
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
        boardDrawings.drawAllPieces();//Todo colocar na posicao
    }

    private void createFireDatabase(){
        insertEntry(0,true,PieceTypeEnum.pieceA,0,0);
        insertEntry(1,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(2,true,PieceTypeEnum.pieceA,0,1);
        insertEntry(3,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(4,true,PieceTypeEnum.pieceA,0,2);
        insertEntry(5,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(6,true,PieceTypeEnum.pieceA,0,3);
        insertEntry(7,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(8,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(9,true,PieceTypeEnum.pieceA,1,0);
        insertEntry(10,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(11,true,PieceTypeEnum.pieceA,1,1);
        insertEntry(12,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(13,true,PieceTypeEnum.pieceA,1,2);
        insertEntry(14,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(15,true,PieceTypeEnum.pieceA,1,3);

        insertEntry(16,true,PieceTypeEnum.pieceA,2,0);
        insertEntry(17,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(18,true,PieceTypeEnum.pieceA,2,1);
        insertEntry(19,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(20,true,PieceTypeEnum.pieceA,2,2);
        insertEntry(21,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(22,true,PieceTypeEnum.pieceA,2,3);
        insertEntry(23,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(24,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(25,true,PieceTypeEnum.BLANK,3,0);
        insertEntry(26,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(27,true,PieceTypeEnum.BLANK,3,1);
        insertEntry(28,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(29,true,PieceTypeEnum.BLANK,3,2);
        insertEntry(30,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(31,true,PieceTypeEnum.BLANK,3,3);

        insertEntry(32,true,PieceTypeEnum.BLANK,4,0);
        insertEntry(33,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(34,true,PieceTypeEnum.BLANK,4,1);
        insertEntry(35,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(36,true,PieceTypeEnum.BLANK,4,2);
        insertEntry(37,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(38,true,PieceTypeEnum.BLANK,4,3);
        insertEntry(39,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(40,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(41,true,PieceTypeEnum.pieceB,5,0);
        insertEntry(42,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(43,true,PieceTypeEnum.pieceB,5,1);
        insertEntry(44,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(45,true,PieceTypeEnum.pieceB,5,2);
        insertEntry(46,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(47,true,PieceTypeEnum.pieceB,5,3);

        insertEntry(48,true,PieceTypeEnum.pieceB,6,0);
        insertEntry(49,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(50,true,PieceTypeEnum.pieceB,6,1);
        insertEntry(51,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(52,true,PieceTypeEnum.pieceB,6,2);
        insertEntry(53,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(54,true,PieceTypeEnum.pieceB,6,3);
        insertEntry(55,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(56,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(57,true,PieceTypeEnum.pieceB,7,0);
        insertEntry(58,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(59,true,PieceTypeEnum.pieceB,7,1);
        insertEntry(60,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(61,true,PieceTypeEnum.pieceB,7,2);
        insertEntry(62,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(63,true,PieceTypeEnum.pieceB,7,3);

    }

    private void insertEntry(
                             int position,
                             boolean isPlayable,
                             PieceTypeEnum pieceType,
                             int row,
                             int column){
        PieceEntry pieceEntry = new PieceEntry(position, position,isPlayable, pieceType,row,column);
        mPieceEntryReference.push().setValue(pieceEntry);
    }



}



