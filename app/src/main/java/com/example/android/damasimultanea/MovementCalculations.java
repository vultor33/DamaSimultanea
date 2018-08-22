package com.example.android.damasimultanea;

import com.example.android.damasimultanea.database.PieceEntry;

import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class MovementCalculations {
    private int INVALID_NUMBER = -1;
    private List<PieceEntry> allBoard;

    private int ROW_SIZE;
    private int COLUMN_SIZE;
    private int TABLE_SIZE;
    private boolean reversedMovement;
    private boolean rawPossibleMovements;
    private int columnBlock;
    private Random rand = new Random();

    private class PositionData{
        ArrayList<Integer> moves = new ArrayList<>();
        int rowOfSelected = INVALID_NUMBER;
        int columnOfSelected = INVALID_NUMBER;
        int position = INVALID_NUMBER;
        PieceTypeEnum piece = PieceTypeEnum.NOTPLAYABLE;
    }

    MovementCalculations(List<PieceEntry> allBoard_in){
        allBoard = allBoard_in;
        ROW_SIZE = 8;
        COLUMN_SIZE = 4;
        TABLE_SIZE = (ROW_SIZE * COLUMN_SIZE * 2);
    }

    public PieceTypeEnum whichPiece(int position){
        return allBoard.get(position).getPieceType();
    }

    public int getTABLE_SIZE() {
        return TABLE_SIZE;
    }

    public void captureAllPossiblePieces(){
        while(true){
            ArrayList< ArrayList<PositionData> > capturesPiecesMatrix = new ArrayList<>();
            ArrayList<Integer> capturesOriginPieces = new ArrayList<>();
            allPossibleCaptures(capturesPiecesMatrix,capturesOriginPieces);
            if(capturesOriginPieces.size() == 0)
                break;

            int chosenCapture = rand.nextInt(capturesOriginPieces.size());
            int pieceToCapturePosition = capturesOriginPieces.get(chosenCapture);
            ArrayList<PositionData> possibleCapturePieces = capturesPiecesMatrix.get(chosenCapture);
            int chosenPiece = rand.nextInt(possibleCapturePieces.size());
            PositionData capturedPosData = possibleCapturePieces.get(chosenPiece);

            deletePiece(capturedPosData.rowOfSelected, capturedPosData.columnOfSelected);
            movePieceXToPositionY(pieceToCapturePosition,capturedPosData.moves.get(0));
        }

    }

    public void movePieceXToPositionY(int startPosition, int finalPosition){
        PieceTypeEnum piece = whichPiece(startPosition);
        deletePiece(startPosition);
        createPieceAtPosition(finalPosition,piece);
    }

    private void allPossibleCaptures(
            ArrayList< ArrayList<PositionData> > capturesPiecesMatrix,
            ArrayList<Integer> capturesOriginPieces){

        for(int i = 0; i < TABLE_SIZE; i++){
            ArrayList<PositionData> capturedPieces = piecesAdjacencies(i);
            if(capturedPieces.size() > 0){
                capturesPiecesMatrix.add(capturedPieces);
                capturesOriginPieces.add(i);
            }
        }
    }

    private ArrayList<PositionData> piecesAdjacencies(int position) {
        ArrayList<PositionData> allCapturesPieces = new ArrayList<>();
        reversedMovement = false;
        rawPossibleMovements = true;
        columnBlock = INVALID_NUMBER;
        PositionData selectedPiece = allPossibleMovements(position);

        reversedMovement = true;
        rawPossibleMovements = false;
        columnBlock = selectedPiece.columnOfSelected;
        for (Integer selectedMoves : selectedPiece.moves) {
            PositionData adjacentPiece = allPossibleMovements(selectedMoves);
            if(adjacentPiece.piece == selectedPiece.piece)
                continue;
            if((adjacentPiece.position != INVALID_NUMBER) && (adjacentPiece.moves.size() > 0))
                allCapturesPieces.add(adjacentPiece);
        }
        return allCapturesPieces;
    }

    public boolean isBothPiecesMovable(){
        boolean pieceAHaveMovement = false;
        boolean pieceBHaveMoevement = false;
        for(int i = 0; i < TABLE_SIZE; i++){
            ArrayList<Integer> moves = possibleMovements(i);
            PieceTypeEnum piece = whichPiece(i);
            if(moves.size() > 0) {
                if (piece == PieceTypeEnum.pieceA)
                    pieceAHaveMovement = true;
                else if(piece == PieceTypeEnum.pieceB)
                    pieceBHaveMoevement = true;
            }
        }
        return pieceAHaveMovement && pieceBHaveMoevement;
    }

    public PieceTypeEnum avaliateWinningPlayer(){
        int pieceAPoints = 0;
        int pieceBPoints = 0;
        for(int j = 0; j < COLUMN_SIZE; j++)
        {
            int positionFinalA = positionFromRowAndColumn(0,j);
            int positionFinalB = positionFromRowAndColumn(ROW_SIZE-1,j);

            if(whichPiece(positionFinalA) == PieceTypeEnum.pieceB)
                pieceBPoints += 1;
            if(whichPiece(positionFinalB) == PieceTypeEnum.pieceA)
                pieceAPoints += 1;
        }
        if(pieceAPoints > pieceBPoints)
            return PieceTypeEnum.pieceA;
        else if(pieceAPoints < pieceBPoints)
            return PieceTypeEnum.pieceB;
        else
            return PieceTypeEnum.BLANK;
    }


    public ArrayList<Integer> possibleMovements(int position) {
        reversedMovement = false;
        rawPossibleMovements = false;
        columnBlock = INVALID_NUMBER;
        return allPossibleMovements(position).moves;
    }

    private void deletePiece(int row, int column){ // fredmudar procurar formas de deletar esse cara
        int position = positionFromRowAndColumn(row,column);
        deletePiece(position);
    }

    private void createPiece(PieceTypeEnum piece, int row, int column){// fredmudar procurar formas de deletar esse cara
        int position = positionFromRowAndColumn(row,column);
        createPieceAtPosition(position,piece);
    }

    private PositionData allPossibleMovements(int position){
        PositionData positionData = new PositionData();
        PieceEntry pieceEntry = allBoard.get(position);
        positionData.rowOfSelected = pieceEntry.getRow();
        positionData.columnOfSelected = pieceEntry.getColumn();
        positionData.position = position;
        positionData.piece = whichPiece(position);

        movementsForPieceX(positionData.moves, pieceEntry.getRow(), pieceEntry.getColumn());
        return positionData;
    }

    private void movementsForPieceX(ArrayList<Integer> movements, int row, int column1) {
        int nextRow;
        int position = positionFromRowAndColumn(row,column1);
        if(reversedMovement)
            nextRow = reversedNextPossibleRow(whichPiece(position), row);
        else
            nextRow = nextPossibleRow(whichPiece(position), row);

        if(nextRow != INVALID_NUMBER){
            addMovement(movements, nextRow, column1);
            int column2 = column1 + nextPossibleColumn(row);
            addMovement(movements, nextRow, column2);
        }
    }

    private void addMovement(ArrayList<Integer> movements, int row, int column){
        int position = positionFromRowAndColumn(row,column);
        if(isColumnValid(column)){
            if(columnBlock == column)
                return;
            if((isTypeBlank(whichPiece(position))) || (rawPossibleMovements)){
                movements.add(position);
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






    public void deletePiece(int position){
        allBoard.get(position).setPieceType(PieceTypeEnum.BLANK);
    }

    private void createPieceAtPosition(int position, PieceTypeEnum piece){
        allBoard.get(position).setPieceType(piece);
    }

    private int positionFromRowAndColumn(int row, int column){
        for(int pos = 0; pos < TABLE_SIZE; pos++){
            PieceEntry pieceEntry = allBoard.get(pos);
            if((pieceEntry.getRow() == row) && (pieceEntry.getColumn() == column))
                return pos;
        }
        return INVALID_NUMBER;
    }



}
