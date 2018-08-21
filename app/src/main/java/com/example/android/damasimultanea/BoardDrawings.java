package com.example.android.damasimultanea;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.jetbrains.annotations.NotNull;

public class BoardDrawings {

    private TurnHandler turnHandler = new TurnHandler();
    private PiecesPositions piecesPositions = new PiecesPositions();
    private MyRecyclerViewAdapter.ViewHolder[] allHolders = new MyRecyclerViewAdapter.ViewHolder[piecesPositions.getTableSize()];//TODO o holder pode ser null e nao tem protecao para isso neste codigo

    //Colors
    private int pieceSideAColor;
    private int pieceSideBColor;
    private int highlightColor;
    private int backGroundPlayableColor;
    private int backGroundNotPlayableColor;

    BoardDrawings(@NotNull Context context){
        pieceSideAColor = ContextCompat.getColor(context, R.color.pieceA);
        pieceSideBColor = ContextCompat.getColor(context, R.color.pieceB);
        highlightColor = ContextCompat.getColor(context, R.color.highLight);
        backGroundPlayableColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        backGroundNotPlayableColor = ContextCompat.getColor(context, R.color.colorAccent);
    }

    public int getTableSize(){
        return piecesPositions.getTableSize();
    }

    public void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        allHolders[position] = holder;
        drawBackground(position);
        drawPiece(position);
    }

    public void playPiece(int position){
        if(turnHandler.isPositionAPossibleMovement(position)) {
            //movePiece(position); //highlight green
            setPieceMovement(position);
            piecesPositions.captureAllPossiblePieces();
            drawAllPieces();
        }
        else
            highlightMovements(position);

    }

    private void movePiece(int toPosition){
        piecesPositions.movePiece(turnHandler.getSelectedPiecePosition(), toPosition);
        drawPiece(toPosition);
        drawPiece(turnHandler.getSelectedPiecePosition());
        clearHighlight();
    }

    private void setPieceMovement(int toPosition){
        allHolders[toPosition].myTextView.setBackgroundColor(Color.GREEN);
    }

    private void highlightMovements(int position){
        PieceTypeEnum piece = piecesPositions.whichPiece(position);
        if(turnHandler.isSelected(position))
            clearHighlight();
        else if(turnHandler.isValidSelection(piece))
            highlightSelectedPiece(position);
    }

    private void highlightSelectedPiece(int position){
        turnHandler.setPossibleMovements(piecesPositions.possibleMovements(position));
        if(turnHandler.isMovementPossible()) {
            allHolders[position].myTextView.setBackgroundColor(highlightColor);
            turnHandler.setSelectedPiecePosition(position);
            for (Integer iMoves : turnHandler.getPossibleMovements()) {
                allHolders[iMoves].myTextView.setBackgroundColor(highlightColor);
            }
        }
    }

    private void clearHighlight(){
        allHolders[turnHandler.getSelectedPiecePosition()].myTextView.setBackgroundColor(backGroundPlayableColor);
        turnHandler.clearSelectedPiece();
        for (Integer iMoves : turnHandler.getPossibleMovements()) {
            allHolders[iMoves].myTextView.setBackgroundColor(backGroundPlayableColor);
        }
        turnHandler.clearPossibleMovements();
    }







    private void drawBackground(int position){
        if (piecesPositions.whichPiece(position) != PieceTypeEnum.NOTPLAYABLE) {
            allHolders[position].myTextView.setBackgroundColor(backGroundPlayableColor);
        } else {
            allHolders[position].myTextView.setBackgroundColor(backGroundNotPlayableColor);
        }
    }

    private void drawAllPieces(){
        for(int i = 0; i < getTableSize(); i++)
            drawPiece(i);
    }

    private void drawPiece(int position){
        switch (piecesPositions.whichPiece(position)){
            case BLANK:
                allHolders[position].pieceImage.setVisibility(View.INVISIBLE);
                break;

            case pieceA:
                allHolders[position].pieceImage.setVisibility(View.VISIBLE);
                allHolders[position].pieceImage.setColorFilter(pieceSideAColor);
                break;

            case pieceB:
                allHolders[position].pieceImage.setVisibility(View.VISIBLE);
                allHolders[position].pieceImage.setColorFilter(pieceSideBColor);
                break;

            default:
                allHolders[position].pieceImage.setVisibility(View.INVISIBLE);
        }
    }
}
