package com.example.android.damasimultanea;

import android.util.Log;

import java.util.ArrayList;

public class MovementCalculations {
    private int ROW_SIZE;
    private int COLUMN_SIZE;
    private int[][] playablePositionsTable;
    private PieceTypeEnum[][] pieceTypeTable;

    boolean reversedMovement;
    boolean rawPossibleMovements;
    int columnBlock;

    private class PositionData{
        ArrayList<Integer> moves = new ArrayList<>();
        int rowOfSelected = -1;
        int columnOfSelected = -1;
    }

    MovementCalculations(int[][] playablePositionsTable_in,  PieceTypeEnum[][] pieceTypeTable_in){
        playablePositionsTable = playablePositionsTable_in;
        pieceTypeTable = pieceTypeTable_in;
        ROW_SIZE = playablePositionsTable.length;
        COLUMN_SIZE = playablePositionsTable[0].length;
    }

    public void piecesAdjacencies(int position) {
        Log.d("fredmudar", "comecou adjacencies");
        reversedMovement = false;
        rawPossibleMovements = true;
        columnBlock = -1;
        PositionData avaibleMoves = allPossibleMovements(position);

        Log.d("fredmudar", "passou do all");
        reversedMovement = true;
        rawPossibleMovements = false;
        columnBlock = avaibleMoves.columnOfSelected;
        ArrayList<Integer> possibleCaptures = new ArrayList<>();
        for (Integer iMoves : avaibleMoves.moves) {
            ArrayList<Integer> tempPossibleCaptures = allPossibleMovements(iMoves).moves;
            possibleCaptures.addAll(tempPossibleCaptures);
        }
        for (Integer iMoves : possibleCaptures) {
            Log.d("fredmudar", "captures:  " + String.valueOf(iMoves));
        }
    }

    public ArrayList<Integer> possibleMovements(int position) {
        reversedMovement = false;
        rawPossibleMovements = false;
        columnBlock = -1;
        return allPossibleMovements(position).moves;
    }

    private PositionData allPossibleMovements(int position){
        PositionData positionData = new PositionData();
        for(int row=0; row<ROW_SIZE; row++){
            for(int column=0; column<COLUMN_SIZE; column++){
                if(position == playablePositionsTable[row][column]) {
                    if((pieceTypeTable[row][column] == PieceTypeEnum.pieceA) ||(pieceTypeTable[row][column] == PieceTypeEnum.pieceB)){
                        positionData.rowOfSelected = row;
                        positionData.columnOfSelected = column;
                        movementsForPieceX(positionData.moves, row, column);
                        return positionData;
                    }
                }
            }
        }
        return positionData;
    }

    private void movementsForPieceX(ArrayList<Integer> movements, int row, int column1) {
        int nextRow;
        if(reversedMovement)
             nextRow = reversedNextPossibleRow(pieceTypeTable[row][column1], row);
        else
            nextRow = nextPossibleRow(pieceTypeTable[row][column1], row);

        int INVALID_ROW = -1;
        if(nextRow != INVALID_ROW){
            addMovement(movements, nextRow, column1);
            int column2 = column1 + nextPossibleColumn(row);
            addMovement(movements, nextRow, column2);
        }
    }

    private void addMovement(ArrayList<Integer> movements, int row, int column){
        if(isColumnValid(column)){
            if(columnBlock == column)
                return;
            if((isTypeBlank(pieceTypeTable[row][column])) || (rawPossibleMovements)){
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
        if(pieceType == PieceTypeEnum.pieceA)
            return nextPossibleRowDown(row);
        else if(pieceType == PieceTypeEnum.pieceB)
            return nextPossibleRowUp(row);
        else
            return -1;
    }

    private int reversedNextPossibleRow(PieceTypeEnum pieceType, int row){
        if(pieceType == PieceTypeEnum.pieceA)
            return nextPossibleRowUp(row);
        else if(pieceType == PieceTypeEnum.pieceB)
            return nextPossibleRowDown(row);
        else
            return -1;
    }

    private int nextPossibleRowDown(int row){
        if(isRowValid(row + 1))
            return row + 1;
        else
            return -1;
    }

    private int nextPossibleRowUp(int row){
        if (isRowValid(row - 1))
            return row - 1;
        else
            return -1;
    }

    private boolean isTypeBlank(PieceTypeEnum pieceType){
        return pieceType == PieceTypeEnum.BLANK;
    }




}
