package com.example.android.damasimultanea;


import android.content.Context;

import com.example.android.damasimultanea.database.AppDatabase;
import com.example.android.damasimultanea.database.FirebaseDatabaseHandler;
import com.example.android.damasimultanea.database.GameController;
import com.example.android.damasimultanea.database.PieceEntry;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class PiecesPositions {
    private AppDatabase pieceDatabase;
    private MovementCalculations movementCalculations;

    PiecesPositions(final Context context){
        pieceDatabase = AppDatabase.getInstance(context);
        movementCalculations = new MovementCalculations(loadAllDatabase());
    }

    public PiecesPositions(ArrayList<PieceEntry> allPieces){
        movementCalculations = new MovementCalculations(allPieces);

    }

    public void setAllBoard(ArrayList<PieceEntry> allPieces) {
        movementCalculations.setAllBoard(allPieces);
    }

    public void setPostionI(int postionI, PieceEntry pieceEntry){
        movementCalculations.setPieceI(postionI,pieceEntry);
    }

    public ArrayList<PieceEntry> getAllPieces(){
        return movementCalculations.getAllBoard();
    }



    private int getTableSize(){
        return movementCalculations.getTABLE_SIZE();
    }

    public PieceTypeEnum whichPiece(int position){
        return movementCalculations.whichPiece(position);
    }

    public ArrayList<Integer> possibleMovements(int position) {
        return movementCalculations.possibleMovements(position);
    }

    public boolean isBothPiecesMovable(){
        return movementCalculations.isBothPiecesMovable();
    }

    public PieceTypeEnum avaliateWinningPlayer(){
        return movementCalculations.avaliateWinningPlayer();
    }

    // ESSES CARAS INFLUENCIAM O BANCO DE DADOS - trabalhar com eles devagar
    public void captureAllPossiblePieces(){
        movementCalculations.captureAllPossiblePieces();
    }
    public void movePiece(int piecePosition, int newPiecePosition){
        movementCalculations.movePieceXToPositionY(piecePosition,newPiecePosition);
    }
    public void deletePiece(int position){
        movementCalculations.deletePiece(position);
    }



    public void safeResetDatabase(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Void> future = executorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                resetDatabase();
                return null;
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            // failed
        }
        executorService.shutdown();
        movementCalculations.setAllBoard(loadAllDatabase());
    }

    public void saveDatabase(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        Future<Void> future = executorService.submit(new Callable<Void>() {
            public Void call() throws Exception {
                ArrayList<PieceEntry> allBoard = movementCalculations.getAllBoard();
                for(int i = 0; i < getTableSize(); i++){
                    PieceEntry pieceEntry = allBoard.get(i);
                    pieceDatabase.taskDao().updatePiece(pieceEntry);
                }
                return null;
            }
        });
        try {
            future.get();
        } catch (Exception e) {
            // failed
        }
        executorService.shutdown();
    }

    private ArrayList<PieceEntry> loadAllDatabase(){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<ArrayList<PieceEntry>> result = executorService.submit(new Callable<ArrayList<PieceEntry>>() {
            public ArrayList<PieceEntry> call() throws Exception {
                List<PieceEntry> allPiecesDatabase = pieceDatabase.taskDao().loadAllPieces();
                ArrayList<PieceEntry> allPiecesReturn = new ArrayList<>(allPiecesDatabase);
                return allPiecesReturn;
            }
        });
        ArrayList<PieceEntry> allBoard;
        try {
            allBoard = result.get();
        } catch (Exception e) {
            allBoard = null;
        }
        executorService.shutdown();
        return allBoard;
    }



    private void resetDatabase(){
        cleanDatabase(pieceDatabase);

        insertEntry(pieceDatabase, 0,true,PieceTypeEnum.pieceA,0,0);
        insertEntry(pieceDatabase, 1,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 2,true,PieceTypeEnum.pieceA,0,1);
        insertEntry(pieceDatabase, 3,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 4,true,PieceTypeEnum.pieceA,0,2);
        insertEntry(pieceDatabase, 5,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 6,true,PieceTypeEnum.pieceA,0,3);
        insertEntry(pieceDatabase, 7,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(pieceDatabase, 8,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 9,true,PieceTypeEnum.pieceA,1,0);
        insertEntry(pieceDatabase, 10,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 11,true,PieceTypeEnum.pieceA,1,1);
        insertEntry(pieceDatabase, 12,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 13,true,PieceTypeEnum.pieceA,1,2);
        insertEntry(pieceDatabase, 14,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 15,true,PieceTypeEnum.pieceA,1,3);

        insertEntry(pieceDatabase, 16,true,PieceTypeEnum.pieceA,2,0);
        insertEntry(pieceDatabase, 17,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 18,true,PieceTypeEnum.pieceA,2,1);
        insertEntry(pieceDatabase, 19,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 20,true,PieceTypeEnum.pieceA,2,2);
        insertEntry(pieceDatabase, 21,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 22,true,PieceTypeEnum.pieceA,2,3);
        insertEntry(pieceDatabase, 23,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(pieceDatabase, 24,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 25,true,PieceTypeEnum.BLANK,3,0);
        insertEntry(pieceDatabase, 26,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 27,true,PieceTypeEnum.BLANK,3,1);
        insertEntry(pieceDatabase, 28,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 29,true,PieceTypeEnum.BLANK,3,2);
        insertEntry(pieceDatabase, 30,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 31,true,PieceTypeEnum.BLANK,3,3);

        insertEntry(pieceDatabase, 32,true,PieceTypeEnum.BLANK,4,0);
        insertEntry(pieceDatabase, 33,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 34,true,PieceTypeEnum.BLANK,4,1);
        insertEntry(pieceDatabase, 35,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 36,true,PieceTypeEnum.BLANK,4,2);
        insertEntry(pieceDatabase, 37,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 38,true,PieceTypeEnum.BLANK,4,3);
        insertEntry(pieceDatabase, 39,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(pieceDatabase, 40,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 41,true,PieceTypeEnum.pieceB,5,0);
        insertEntry(pieceDatabase, 42,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 43,true,PieceTypeEnum.pieceB,5,1);
        insertEntry(pieceDatabase, 44,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 45,true,PieceTypeEnum.pieceB,5,2);
        insertEntry(pieceDatabase, 46,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 47,true,PieceTypeEnum.pieceB,5,3);

        insertEntry(pieceDatabase, 48,true,PieceTypeEnum.pieceB,6,0);
        insertEntry(pieceDatabase, 49,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 50,true,PieceTypeEnum.pieceB,6,1);
        insertEntry(pieceDatabase, 51,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 52,true,PieceTypeEnum.pieceB,6,2);
        insertEntry(pieceDatabase, 53,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 54,true,PieceTypeEnum.pieceB,6,3);
        insertEntry(pieceDatabase, 55,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);

        insertEntry(pieceDatabase, 56,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 57,true,PieceTypeEnum.pieceB,7,0);
        insertEntry(pieceDatabase, 58,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 59,true,PieceTypeEnum.pieceB,7,1);
        insertEntry(pieceDatabase, 60,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 61,true,PieceTypeEnum.pieceB,7,2);
        insertEntry(pieceDatabase, 62,false,PieceTypeEnum.NOTPLAYABLE,-1,-1);
        insertEntry(pieceDatabase, 63,true,PieceTypeEnum.pieceB,7,3);

    }
    private void insertEntry(AppDatabase pieceDatabase,
                             int position,
                             boolean isPlayable,
                             PieceTypeEnum pieceType,
                             int row,
                             int column){
        PieceEntry pieceEntry = new PieceEntry(position,isPlayable, pieceType,row,column);
        pieceDatabase.taskDao().insertPiece(pieceEntry);
    }

    private void cleanDatabase(AppDatabase pieceDatabase){
        List<PieceEntry> allPieces = pieceDatabase.taskDao().loadAllPieces();
        for(int i = 0; i < allPieces.size(); i++){
            pieceDatabase.taskDao().deletePiece(allPieces.get(i));
        }
    }


}
