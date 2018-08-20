package com.example.android.damasimultanea;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoardDrawings {

    private PiecesPositions piecesPositions = new PiecesPositions();
    private MyRecyclerViewAdapter.ViewHolder[] allHolders = new MyRecyclerViewAdapter.ViewHolder[piecesPositions.getTableSize()];//TODO o holder pode ser null e nao tem protecao para isso neste codigo
    ArrayList<Integer> possibleMovements = new ArrayList<>();
    private int selectedPiece;
    private int NOT_SELECTED = -1;

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
        selectedPiece = NOT_SELECTED;
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
        //TurnHandler
        //CapturePiecesHandler -- ele anda no tabuleiro tod o e aponta quem captura quem.

        if(possibleMovements.contains(position))
            movePiece(position);
        else
            highlightMovements(position);
    }

    private void movePiece(int toPosition){
        piecesPositions.movePiece(selectedPiece, toPosition);
        drawPiece(toPosition);
        drawPiece(selectedPiece);
        clearHighlight();
    }

    private void highlightMovements(int position){
        PieceTypeEnum piece = piecesPositions.whichPiece(position);
        if(position == selectedPiece)
            clearHighlight();
        else if(isValidSelection(piece))
            highlightSelectedPiece(position);
    }

    private boolean isValidSelection(PieceTypeEnum piece){
        boolean isPiece = (piece != PieceTypeEnum.NOTPLAYABLE) && (piece != PieceTypeEnum.BLANK);
        boolean isAvailable = selectedPiece == NOT_SELECTED;
        return isPiece && isAvailable;
    }

    private void highlightSelectedPiece(int position){
        possibleMovements = piecesPositions.possibleMovements(position);
        if(possibleMovements.size() == 0)
            return;
        allHolders[position].myTextView.setBackgroundColor(highlightColor);
        selectedPiece = position;
        for (Integer iMoves : possibleMovements) {
            allHolders[iMoves].myTextView.setBackgroundColor(highlightColor);
        }
    }

    private void clearHighlight(){
        allHolders[selectedPiece].myTextView.setBackgroundColor(backGroundPlayableColor);
        selectedPiece = NOT_SELECTED;
        for (Integer iMoves : possibleMovements) {
            allHolders[iMoves].myTextView.setBackgroundColor(backGroundPlayableColor);
        }
        possibleMovements.clear();
    }


    private void drawBackground(int position){
        if (piecesPositions.whichPiece(position) != PieceTypeEnum.NOTPLAYABLE) {
            allHolders[position].myTextView.setBackgroundColor(backGroundPlayableColor);
        } else {
            allHolders[position].myTextView.setBackgroundColor(backGroundNotPlayableColor);
        }
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
