package com.example.android.damasimultanea;

import android.util.Log;

import java.util.ArrayList;

public class TurnHandler {

    private int NOT_SELECTED = -1;
    private int selectedPiecePosition = NOT_SELECTED;
    private PieceTypeEnum selectedPiece = PieceTypeEnum.NOTPLAYABLE;
    private ArrayList<Integer> possibleMovements = new ArrayList<>();
    private class PlayerData{
        int movedTryPosition = NOT_SELECTED;
        int position = NOT_SELECTED;
        PieceTypeEnum pieceType;

        public void clear(){
            movedTryPosition = NOT_SELECTED;
            position = NOT_SELECTED;
        }
    }
    private PlayerData player1 = new PlayerData();
    private PlayerData player2 = new PlayerData();

    TurnHandler(){
        player1.pieceType = PieceTypeEnum.pieceA;
        player2.pieceType = PieceTypeEnum.pieceB;
    }

    public boolean isTurnEnded(){
        return ((player1.position != NOT_SELECTED) && (player2.position != NOT_SELECTED));
    }

    public void clearAllPlayersData(){
        player1.clear();
        player2.clear();
    }

    public int getPlayer1Position(){
        return player1.position;
    }

    public int getPlayer2Position(){
        return player2.position;
    }

    public int getPlayer1Destination(){
        return player1.movedTryPosition;
    }

    public int getPlayer2Destination(){
        return player2.movedTryPosition;
    }


    //PIECE
    public void setSelectedPiece(int selectedPiecePosition_in, PieceTypeEnum piece_in){
        selectedPiecePosition = selectedPiecePosition_in;
        selectedPiece = piece_in;
    }

    public boolean isSelected(int position){
        return position == selectedPiecePosition;
    }

    public int getSelectedPiecePosition(){
        return selectedPiecePosition;
    }

    public void clearSelectedPiece(){
        selectedPiecePosition = NOT_SELECTED;
    }

    public boolean isSelectionPossible(int position, PieceTypeEnum piece){
        if((player1.position != NOT_SELECTED) && (player1.pieceType == piece))
            return  false;
        else if((player2.position != NOT_SELECTED) && (player2.pieceType == piece))
            return false;

        boolean player1Moved = player1.position == position;
        boolean player2Moved = player2.position == position;
        return !(player1Moved || player2Moved);
    }


    //MOVEMENTS
    public void setPossibleMovements(ArrayList<Integer> possibleMovements_in){
        possibleMovements = possibleMovements_in;
    }

    public boolean isMovementPossible(){
        return possibleMovements.size() > 0;
    }

    public ArrayList<Integer> getPossibleMovements() {
        return possibleMovements;
    }

    public void clearPossibleMovements(){
        possibleMovements.clear();
    }

    public boolean isPositionAPossibleMovement(int position){
        return possibleMovements.contains(position);
    }

    public void askingToMovePiece(int position){
        if(player1.pieceType == selectedPiece){
            player1.movedTryPosition = position;
            player1.position = selectedPiecePosition;
        }
        else if(player2.pieceType == selectedPiece){
            player2.movedTryPosition = position;
            player2.position = selectedPiecePosition;
        }
        selectedPiecePosition = NOT_SELECTED;
        selectedPiece = PieceTypeEnum.NOTPLAYABLE;
        possibleMovements.clear();
    }

    public boolean isValidSelection(PieceTypeEnum piece){
        boolean isPiece = (piece != PieceTypeEnum.NOTPLAYABLE) && (piece != PieceTypeEnum.BLANK);
        boolean isAvailable = selectedPiecePosition == NOT_SELECTED;
        return isPiece && isAvailable;
    }


}
