package com.example.android.damasimultanea.database;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.damasimultanea.PieceTypeEnum;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirebaseDatabaseHandler {

    final private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;

    private ArrayList<PieceEntry> allPieces = new ArrayList<>();

    public FirebaseDatabaseHandler(
            FirebaseDatabase mFirebaseDatabase_in)
    {
        mMessagesDatabaseReference = mFirebaseDatabase_in.getReference().child("BOARD"); //read and write on BOARD flag
        mChildEventListener = null;

        PieceEntry pieceEntry = new PieceEntry(1,4,true, PieceTypeEnum.pieceB,3,4);
        mMessagesDatabaseReference.push().setValue(pieceEntry);

    }

    public int getPiecesSize(){
        return allPieces.size();
    }

    public void atachDatabaseReadListener(){
        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListenerPieces();
            mMessagesDatabaseReference.addChildEventListener(mChildEventListener);
        }
    }

    public void detachDatabaseReadListener() {
        if(mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }




    private class ChildEventListenerPieces implements ChildEventListener {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            PieceEntry pieceEntry = dataSnapshot.getValue(PieceEntry.class);
            Log.d("fredmudar", "imprimindo:  " + pieceEntry.getPieceType());
            if(pieceEntry.getPieceType() == PieceTypeEnum.pieceB)
                Log.d("fredmudar","para a noooossaaaa alegriaaaaa");

            allPieces.add(pieceEntry);
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
    }

}
