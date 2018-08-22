package com.example.android.damasimultanea;


import android.content.Context;
import android.util.Log;

import com.example.android.damasimultanea.database.AppDatabase;
import com.example.android.damasimultanea.database.PieceEntry;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class PiecesPositions {
    final private int ROW_SIZE = 8;
    final private int COLUMN_SIZE = 4;
    private int[][] playablePositionsTable = new int[ROW_SIZE][COLUMN_SIZE];
    private PieceTypeEnum[][] pieceTypeTable = new PieceTypeEnum[ROW_SIZE][COLUMN_SIZE];
    private AppDatabase pieceDatabase;

    private MovementCalculations movementCalculations;

    public int getTableSize(){
        return ROW_SIZE * COLUMN_SIZE * 2;
    }

    public PieceTypeEnum whichPiece(int position){
        return movementCalculations.whichPiece(position);
    }

    public ArrayList<Integer> possibleMovements(int position) {
        return movementCalculations.possibleMovements(position);
    }

    public void captureAllPossiblePieces(){
        movementCalculations.captureAllPossiblePieces();
    }

    public void movePiece(int piecePosition, int newPiecePosition){
        movementCalculations.movePieceXToPositionY(piecePosition,newPiecePosition);
    }

    public void deletePiece(int position){
        movementCalculations.deletePiece(position);
    }

    public boolean isBothPiecesMovable(){
        return movementCalculations.isBothPiecesMovable();
    }

    public PieceTypeEnum avaliateWinningPlayer(){
        return movementCalculations.avaliateWinningPlayer();
    }

    PiecesPositions(Context context){
        pieceDatabase = AppDatabase.getInstance(context);
        resetDatabase();
        List<PieceEntry> allBoard = pieceDatabase.taskDao().loadAllPieces();

        playablePositionsTable[0][0] = 0;
        playablePositionsTable[0][1] = 2;
        playablePositionsTable[0][2] = 4;
        playablePositionsTable[0][3] = 6;

        playablePositionsTable[1][0] = 9;
        playablePositionsTable[1][1] = 11;
        playablePositionsTable[1][2] = 13;
        playablePositionsTable[1][3] = 15;

        playablePositionsTable[2][0] = 16;
        playablePositionsTable[2][1] = 18;
        playablePositionsTable[2][2] = 20;
        playablePositionsTable[2][3] = 22;

        playablePositionsTable[3][0] = 25;
        playablePositionsTable[3][1] = 27;
        playablePositionsTable[3][2] = 29;
        playablePositionsTable[3][3] = 31;

        playablePositionsTable[4][0] = 32;
        playablePositionsTable[4][1] = 34;
        playablePositionsTable[4][2] = 36;
        playablePositionsTable[4][3] = 38;

        playablePositionsTable[5][0] = 41;
        playablePositionsTable[5][1] = 43;
        playablePositionsTable[5][2] = 45;
        playablePositionsTable[5][3] = 47;

        playablePositionsTable[6][0] = 48;
        playablePositionsTable[6][1] = 50;
        playablePositionsTable[6][2] = 52;
        playablePositionsTable[6][3] = 54;

        playablePositionsTable[7][0] = 57;
        playablePositionsTable[7][1] = 59;
        playablePositionsTable[7][2] = 61;
        playablePositionsTable[7][3] = 63;

        pieceTypeTable[0][0] = PieceTypeEnum.pieceA;
        pieceTypeTable[0][1] = PieceTypeEnum.pieceA;
        pieceTypeTable[0][2] = PieceTypeEnum.pieceA;
        pieceTypeTable[0][3] = PieceTypeEnum.pieceA;

        pieceTypeTable[1][0] = PieceTypeEnum.pieceA;
        pieceTypeTable[1][1] = PieceTypeEnum.pieceA;
        pieceTypeTable[1][2] = PieceTypeEnum.pieceA;
        pieceTypeTable[1][3] = PieceTypeEnum.pieceA;

        pieceTypeTable[2][0] = PieceTypeEnum.pieceA;
        pieceTypeTable[2][1] = PieceTypeEnum.pieceA;
        pieceTypeTable[2][2] = PieceTypeEnum.pieceA;
        pieceTypeTable[2][3] = PieceTypeEnum.pieceA;

        pieceTypeTable[3][0] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][1] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][2] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][3] = PieceTypeEnum.BLANK;

        pieceTypeTable[4][0] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][1] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][2] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][3] = PieceTypeEnum.BLANK;

        pieceTypeTable[5][0] = PieceTypeEnum.pieceB;
        pieceTypeTable[5][1] = PieceTypeEnum.pieceB;
        pieceTypeTable[5][2] = PieceTypeEnum.pieceB;
        pieceTypeTable[5][3] = PieceTypeEnum.pieceB;

        pieceTypeTable[6][0] = PieceTypeEnum.pieceB;
        pieceTypeTable[6][1] = PieceTypeEnum.pieceB;
        pieceTypeTable[6][2] = PieceTypeEnum.pieceB;
        pieceTypeTable[6][3] = PieceTypeEnum.pieceB;

        pieceTypeTable[7][0] = PieceTypeEnum.pieceB;
        pieceTypeTable[7][1] = PieceTypeEnum.pieceB;
        pieceTypeTable[7][2] = PieceTypeEnum.pieceB;
        pieceTypeTable[7][3] = PieceTypeEnum.pieceB;

        movementCalculations = new MovementCalculations(playablePositionsTable, pieceTypeTable, allBoard);
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
