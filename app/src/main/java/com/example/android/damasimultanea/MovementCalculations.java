package com.example.android.damasimultanea;

import android.util.Log;

import java.util.ArrayList;

public class MovementCalculations {
    private int INVALID_NUMBER = -1;
    private int ROW_SIZE;
    private int COLUMN_SIZE;
    private int[][] playablePositionsTable;
    private PieceTypeEnum[][] pieceTypeTable;

    boolean reversedMovement;
    boolean rawPossibleMovements;
    int columnBlock;

    private class PositionData{
        ArrayList<Integer> moves = new ArrayList<>();
        int rowOfSelected = INVALID_NUMBER;
        int columnOfSelected = INVALID_NUMBER;
        int position = INVALID_NUMBER;
        PieceTypeEnum piece = PieceTypeEnum.NOTPLAYABLE;
    }

    MovementCalculations(int[][] playablePositionsTable_in,  PieceTypeEnum[][] pieceTypeTable_in){
        playablePositionsTable = playablePositionsTable_in;
        pieceTypeTable = pieceTypeTable_in;
        ROW_SIZE = playablePositionsTable.length;
        COLUMN_SIZE = playablePositionsTable[0].length;
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

    public void piecesAdjacencies(int position) {
        reversedMovement = false;
        rawPossibleMovements = true;
        columnBlock = INVALID_NUMBER;
        PositionData selectedPiece = allPossibleMovements(position);

        reversedMovement = true;
        rawPossibleMovements = false;
        columnBlock = selectedPiece.columnOfSelected;
        ArrayList<Integer> possibleCapturesPieces = new ArrayList<>();
        for (Integer selectedMoves : selectedPiece.moves) {
            PositionData adjacentPiece = allPossibleMovements(selectedMoves);
            if(adjacentPiece.piece == selectedPiece.piece)
                continue;
            if((adjacentPiece.position != INVALID_NUMBER) && (adjacentPiece.moves.size() > 0))
                possibleCapturesPieces.add(adjacentPiece.position);
        }
        for (Integer iMoves : possibleCapturesPieces) {
            Log.d("fredmudar", "captures:  " + String.valueOf(iMoves));
        }
    }

    public ArrayList<Integer> possibleMovements(int position) {
        reversedMovement = false;
        rawPossibleMovements = false;
        columnBlock = INVALID_NUMBER;
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
                        positionData.position = position;
                        positionData.piece = pieceTypeTable[row][column];
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

        if(nextRow != INVALID_NUMBER){
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
            return INVALID_NUMBER;
    }

    private int reversedNextPossibleRow(PieceTypeEnum pieceType, int row){
        if(pieceType == PieceTypeEnum.pieceA)
            return nextPossibleRowUp(row);
        else if(pieceType == PieceTypeEnum.pieceB)
            return nextPossibleRowDown(row);
        else
            return INVALID_NUMBER;
    }

    private int nextPossibleRowDown(int row){
        if(isRowValid(row + 1))
            return row + 1;
        else
            return INVALID_NUMBER;
    }

    private int nextPossibleRowUp(int row){
        if (isRowValid(row - 1))
            return row - 1;
        else
            return INVALID_NUMBER;
    }

    private boolean isTypeBlank(PieceTypeEnum pieceType){
        return pieceType == PieceTypeEnum.BLANK;
    }




}
