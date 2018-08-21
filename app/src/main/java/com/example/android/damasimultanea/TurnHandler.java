package com.example.android.damasimultanea;

import java.util.ArrayList;

public class TurnHandler {

    private int NOT_SELECTED = -1;
    private class PlayerData{
        private int selectedPiecePosition = NOT_SELECTED;
        ArrayList<Integer> possibleMovements = new ArrayList<>();
        boolean played;
        PieceTypeEnum pieceType;
    }

    private PlayerData player1 = new PlayerData();
    private PlayerData player2 = new PlayerData();

    public TurnHandler(){
        player1.pieceType = PieceTypeEnum.pieceA;
        player2.pieceType = PieceTypeEnum.pieceB;
    }

    //PIECE
    public void setSelectedPiecePosition(int selectedPiece_in){
        player1.selectedPiecePosition = selectedPiece_in;
    }

    public boolean isSelected(int position){
        return position == player1.selectedPiecePosition;
    }

    public int getSelectedPiecePosition(){
        return player1.selectedPiecePosition;
    }

    public void clearSelectedPiece(){
        player1.selectedPiecePosition = NOT_SELECTED;
    }


    //MOVEMENTS
    public void setPossibleMovements(ArrayList<Integer> possibleMovements_in){
        player1.possibleMovements = possibleMovements_in;
    }

    public boolean isMovementPossible(){
        return player1.possibleMovements.size() > 0;
    }

    public ArrayList<Integer> getPossibleMovements() {
        return player1.possibleMovements;
    }

    public void clearPossibleMovements(){
        player1.possibleMovements.clear();
    }

    public boolean isPositionAPossibleMovement(int position){
        return player1.possibleMovements.contains(position);
    }

    /*ArrayList<PieceTypeEnum> alreadyPlayed
    public boolean isPlayersTurn(PieceTypeEnum piece_in){
        return !alreadyPlayed.contains(piece_in);
    }

    public void addPlayed(PieceTypeEnum piece_in){
        alreadyPlayed.add(piece_in);
    }

    public void clearPlayedMoves(){
        alreadyPlayed.clear();
    }
    */




    public boolean isValidSelection(PieceTypeEnum piece){
        boolean isPiece = (piece != PieceTypeEnum.NOTPLAYABLE) && (piece != PieceTypeEnum.BLANK);
        boolean isAvailable = player1.selectedPiecePosition == NOT_SELECTED;
        return isPiece && isAvailable;
    }


}
