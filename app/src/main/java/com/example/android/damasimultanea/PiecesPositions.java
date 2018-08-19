package com.example.android.damasimultanea;


import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PiecesPositions {
    final private int TABLE_SIZE = 64;
    final int ROW_SIZE = 8;
    final int COLUMN_SIZE = 4;
    private int[][] playablePositionsTable = new int[ROW_SIZE][COLUMN_SIZE];
    private PieceTypeEnum[][] pieceTypeTable = new PieceTypeEnum[ROW_SIZE][COLUMN_SIZE];

    public int getTableSize(){
        return TABLE_SIZE;
    }

    public PieceTypeEnum whichPiece(int position){
        for(int i=0; i<ROW_SIZE; i++){
            for(int j=0; j<COLUMN_SIZE; j++){
                if(position == playablePositionsTable[i][j])
                    return pieceTypeTable[i][j];
            }
        }
        return PieceTypeEnum.NOTPLAYABLE;
    }

    public ArrayList<Integer> possibleMovements(int position){
        ArrayList<Integer> movements = new ArrayList<>();

        for(int i=0; i<ROW_SIZE; i++){
            for(int j=0; j<COLUMN_SIZE; j++){
                if(position == playablePositionsTable[i][j]) {
                    if(pieceTypeTable[i][j] == PieceTypeEnum.pieceA){
                        movementsForPieceX(movements,i,j);
                        return movements;
                    }
                    else if(pieceTypeTable[i][j] == PieceTypeEnum.pieceB){
                        movementsForPieceX(movements,i,j);
                        return movements;
                    }
                }
            }
        }
        return movements;
    }

    private void movementsForPieceX(ArrayList<Integer> movements, int row, int column1) {
        int nextRow = nextPossibleRow(pieceTypeTable[row][column1], row);
        int INVALID_ROW = -1;
        if(nextRow != INVALID_ROW){
            addMovement(movements, nextRow, column1);
            int column2 = column1 + nextPossibleColumn(row);
            addMovement(movements, nextRow, column2);
        }
    }

    private void addMovement(ArrayList<Integer> movements, int row, int column){
        if(isColumnValid(column)){
            if(isTypeBlank(pieceTypeTable[row][column])){
                movements.add(playablePositionsTable[row][column]);
            }
        }
    }

    private boolean isRowValid(int index){
        return (index > -1) && (index < ROW_SIZE);
    }

    private boolean isColumnValid(int index){
        return (index > -1) && (index < COLUMN_SIZE);
    }

    private int nextPossibleColumn(int row){
        if(row % 2 == 0)
            return -1;
        else
            return +1;
    }

    private int nextPossibleRow(PieceTypeEnum pieceType, int row){
        if(pieceType == PieceTypeEnum.pieceA) {
            if(isRowValid(row + 1))
                return row + 1;
            else
                return -1;
        }
        else if(pieceType == PieceTypeEnum.pieceB) {
            if (isRowValid(row - 1))
                return row - 1;
            else
                return -1;
        }
        else
            return -1;
    }

    private boolean isTypeBlank(PieceTypeEnum pieceType){
        return pieceType == PieceTypeEnum.BLANK;
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

    }
}
