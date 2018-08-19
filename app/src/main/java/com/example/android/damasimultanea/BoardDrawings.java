package com.example.android.damasimultanea;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BoardDrawings {

    private PiecesPositions piecesPositions;
    private List<MyRecyclerViewAdapter.ViewHolder> allHolders = new ArrayList<>();
    ArrayList<Integer> possibleMovements = new ArrayList<>();
    private int selectedPiece;

    //Colors
    private int pieceSideAColor;
    private int pieceSideBColor;
    private int highlightColor;
    private int backGroundPlayableColor;
    private int backGroundNotPlayableColor;


    BoardDrawings(PiecesPositions pieces, @NotNull Context context){
        piecesPositions = pieces;
        pieceSideAColor = ContextCompat.getColor(context, R.color.pieceA);
        pieceSideBColor = ContextCompat.getColor(context, R.color.pieceB);
        highlightColor = ContextCompat.getColor(context, R.color.highLight);
        backGroundPlayableColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        backGroundNotPlayableColor = ContextCompat.getColor(context, R.color.colorAccent);
        selectedPiece = -1;
    }

    public void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        allHolders.add(position,holder);
        drawBackground(position);
        drawPiece(position);
    }

    public void playPiece(int position){
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
        if(piece == PieceTypeEnum.BLANK)
            return;
        else if(position == selectedPiece){ //turn off
            clearHighlight();
        }
        else if(selectedPiece != -1)
            return;
        else if(piece != PieceTypeEnum.NOTPLAYABLE){
            highlightSelectedColor(position);
        }
    }

    private void highlightSelectedColor(int position){
        possibleMovements = piecesPositions.possibleMovements(position);
        if(possibleMovements.size() == 0)
            return;
        allHolders.get(position).myTextView.setBackgroundColor(highlightColor);
        selectedPiece = position;
        for (Integer iMoves : possibleMovements) {
            allHolders.get(iMoves).myTextView.setBackgroundColor(highlightColor);
        }
    }

    private void clearHighlight(){
        allHolders.get(selectedPiece).myTextView.setBackgroundColor(backGroundPlayableColor);
        selectedPiece = -1;
        for (Integer iMoves : possibleMovements) {
            allHolders.get(iMoves).myTextView.setBackgroundColor(backGroundPlayableColor);
        }
        possibleMovements.clear();
    }


    private void drawBackground(int position){
        if (piecesPositions.whichPiece(position) != PieceTypeEnum.NOTPLAYABLE) {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundPlayableColor);
        } else {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundNotPlayableColor);
        }
    }

    private void drawPiece(int position){
        switch (piecesPositions.whichPiece(position)){
            case BLANK:
                allHolders.get(position).pieceImage.setVisibility(View.INVISIBLE);
                break;

            case pieceA:
                allHolders.get(position).pieceImage.setVisibility(View.VISIBLE);
                allHolders.get(position).pieceImage.setColorFilter(pieceSideAColor);
                break;

            case pieceB:
                allHolders.get(position).pieceImage.setVisibility(View.VISIBLE);
                allHolders.get(position).pieceImage.setColorFilter(pieceSideBColor);
                break;

            default:
                allHolders.get(position).pieceImage.setVisibility(View.INVISIBLE);
        }
    }
}
