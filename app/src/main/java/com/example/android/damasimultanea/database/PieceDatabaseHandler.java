package com.example.android.damasimultanea.database;

import android.content.Context;
import android.util.Log;

public class PieceDatabaseHandler{
    AppDatabase mDb;

    PieceDatabaseHandler(Context context){
        mDb = AppDatabase.getInstance(context);
    }

    public void addPiece(){
        PieceEntry pieceEntry = new PieceEntry(3,0,1,2,4);
        Log.d("fredmudar","entry");

        mDb.taskDao().insertPiece(pieceEntry);

        Log.d("fredmudar","inserted");


    }






}
