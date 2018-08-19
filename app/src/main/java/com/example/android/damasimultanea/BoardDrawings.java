package com.example.android.damasimultanea;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
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

    private int highlightPiece;

    BoardDrawings(PiecesPositions pieces, @NotNull Context context){
        piecesPositions = pieces;
        pieceSideAColor = ContextCompat.getColor(context, R.color.pieceA);
        pieceSideBColor = ContextCompat.getColor(context, R.color.pieceB);
        highlightColor = ContextCompat.getColor(context, R.color.highLight);
        backGroundPlayableColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        backGroundNotPlayableColor = ContextCompat.getColor(context, R.color.colorAccent);
        highlightPiece = -1;
    }

    void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        allHolders.add(position,holder);
        drawBackground(position);
        drawPiece(position);
    }

    void drawBackground(int position){
        if (piecesPositions.whichPiece(position) != PieceTypeEnum.NOTPLAYABLE) {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundPlayableColor);
        } else {
            allHolders.get(position).myTextView.setBackgroundColor(backGroundNotPlayableColor);
        }
    }

    void drawPiece(int position){
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

    void highlightBackground(int position){
        if(position == highlightPiece){ //turn off
            allHolders.get(position).myTextView.setBackgroundColor(backGroundPlayableColor);
            highlightPiece = -1;


            //turn off possible plays

        }
        else if(highlightPiece != -1)
            return;
        else if(piecesPositions.whichPiece(position) != PieceTypeEnum.NOTPLAYABLE){
            allHolders.get(position).myTextView.setBackgroundColor(highlightColor);
            highlightPiece = position;
            ArrayList<Integer> possibleMoviments = piecesPositions.possibleMovements(position);
            for (Integer iMoves : possibleMoviments) {
                allHolders.get(iMoves).myTextView.setBackgroundColor(highlightColor);
            }


            // add possible plays here.

        }
    }

}
