package com.example.android.damasimultanea;

import android.content.Context;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class BoardDrawings {

    private PiecesPositions piecesPositions;
    private List<MyRecyclerViewAdapter.ViewHolder> allHolders = new ArrayList<>();
    private int pieceSideAColor;
    private int pieceSideBColor;
    private int highlightColor;
    private int backGroundPlayableColor;
    private int backGroundNotPlayableColor;


    BoardDrawings(PiecesPositions pieces, Context context){
        piecesPositions = pieces;
        pieceSideAColor = context.getResources().getColor(R.color.pieceA);
        pieceSideBColor = context.getResources().getColor(R.color.pieceB);
        highlightColor = context.getResources().getColor(R.color.highLight);
        backGroundPlayableColor = context.getResources().getColor(R.color.colorPrimaryDark);
        backGroundNotPlayableColor = context.getResources().getColor(R.color.colorAccent);
    }

    void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        allHolders.add(position,holder);
        drawBackground(position);
        drawPiece(position);
    }

    void drawBackground(int position){
        if (piecesPositions.isPlayablePosition(position)) {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundPlayableColor);
        } else {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundNotPlayableColor);
        }
    }

    void drawPiece(int position){
        switch (piecesPositions.whichPiece(position)){
            case 0:
                allHolders.get(position).pieceImage.setVisibility(View.INVISIBLE);
                break;

            case 1:
                allHolders.get(position).pieceImage.setVisibility(View.VISIBLE);
                allHolders.get(position).pieceImage.setColorFilter(pieceSideAColor);
                break;

            case 2:
                allHolders.get(position).pieceImage.setVisibility(View.VISIBLE);
                allHolders.get(position).pieceImage.setColorFilter(pieceSideBColor);
                break;

            default:
                allHolders.get(position).pieceImage.setVisibility(View.INVISIBLE);//fredmudar esses dois swirchs tambem nao tem muita condicao throw exception
        }
    }

    void highlightBackground(int position){
        if(piecesPositions.isPlayablePosition(position)){
            allHolders.get(position).myTextView.setBackgroundColor(highlightColor);
        }
    }


}
