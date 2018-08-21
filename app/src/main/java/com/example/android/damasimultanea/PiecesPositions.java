package com.example.android.damasimultanea;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PiecesPositions {
    final private int ROW_SIZE = 8;
    final private int COLUMN_SIZE = 4;
    private int[][] playablePositionsTable = new int[ROW_SIZE][COLUMN_SIZE];
    private PieceTypeEnum[][] pieceTypeTable = new PieceTypeEnum[ROW_SIZE][COLUMN_SIZE];

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

    PiecesPositions(){

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

        movementCalculations = new MovementCalculations(playablePositionsTable, pieceTypeTable);
    }
}
