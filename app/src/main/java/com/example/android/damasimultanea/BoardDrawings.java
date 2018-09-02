package com.example.android.damasimultanea;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;


import com.example.android.damasimultanea.database.GameController;

import org.jetbrains.annotations.NotNull;

public class BoardDrawings {

    public TurnHandler turnHandler = new TurnHandler();
    private PiecesPositions piecesPositions;
    private int BOARD_SIZE = 64;
    private MyRecyclerViewAdapter.ViewHolder[] allHolders = new MyRecyclerViewAdapter.ViewHolder[BOARD_SIZE];

    private TextView winPlayer;

    //Colors
    private int pieceSideAColor;
    private int pieceSideBColor;
    private int highlightColor;
    private int highlighMovementColor;
    private int backGroundPlayableColor;
    private int backGroundNotPlayableColor;

    private String winBlue;
    private String winRed;
    private String winTie;
    private String winError;

    //TODO boardrawing esta muito mesclado com turnhandler - construir um game manager que assume essa parte e separa os dois.

    public BoardDrawings(@NotNull Context context){
        piecesPositions = null;

        pieceSideAColor = ContextCompat.getColor(context, R.color.pieceA);
        pieceSideBColor = ContextCompat.getColor(context, R.color.pieceB);
        highlightColor = ContextCompat.getColor(context, R.color.highLight);
        highlighMovementColor = ContextCompat.getColor(context,R.color.highLightMovement);
        backGroundPlayableColor = ContextCompat.getColor(context, R.color.colorPrimaryDark);
        backGroundNotPlayableColor = ContextCompat.getColor(context, R.color.colorAccent);
        winPlayer = ((Activity)context).findViewById(R.id.tv_win);

        winBlue = context.getString(R.string.win_blue);
        winRed = context.getString(R.string.win_red);
        winTie = context.getString(R.string.win_tie);
        winError = context.getString(R.string.win_error);
    }

    public void setPiecesPositions(PiecesPositions piecesPositions_in){
        this.piecesPositions = piecesPositions_in;
    }

    public int getTableSize(){
        return BOARD_SIZE;
    }

    public void addHolder(MyRecyclerViewAdapter.ViewHolder holder, int position){
        allHolders[position] = holder;
    }

    public void playPiece(int position, PieceTypeEnum playerPiece) {
        if (turnHandler.isPositionAPossibleMovement(position))
            setPieceMovement(position);
        else {
            if(playerPiece.equals(piecesPositions.whichPiece(position)))
                highlightMovements(position);
        }
    }

    public void resolveAllMovements(){
        if(!turnHandler.isTurnEnded())
            return;

        if(turnHandler.getPlayer1Destination() == turnHandler.getPlayer2Destination()) {
            piecesPositions.deletePiece(turnHandler.getPlayer1Position());
            piecesPositions.deletePiece(turnHandler.getPlayer2Position());
        }
        else {
            piecesPositions.movePiece(turnHandler.getPlayer1Position(),turnHandler.getPlayer1Destination());
            piecesPositions.movePiece(turnHandler.getPlayer2Position(),turnHandler.getPlayer2Destination());
        }
        piecesPositions.captureAllPossiblePieces();
        turnHandler.clearAllPlayersData();
        drawAllPieces();
        drawAllBackground();
    }

    public void gameEndConditions(){
        if(piecesPositions.isBothPiecesMovable())
            return;

        PieceTypeEnum winPiece = piecesPositions.avaliateWinningPlayer();
        switch (winPiece){
            case pieceA:
                winPlayer.setText(winBlue);
                break;

            case pieceB:
                winPlayer.setText(winRed);
                break;

            case BLANK:
                winPlayer.setText(winTie);
                break;

            default:
                winPlayer.setText(winError);
                break;
        }
        winPlayer.setVisibility(View.VISIBLE);
    }

    public void drawAllPieces(){
        for(int i = 0; i < getTableSize(); i++)
            drawPiece(i);
    }

    public void drawAllBackground(){
        for(int i = 0; i < getTableSize(); i++)
            drawBackground(i);
    }

    public void drawPiece(int position){
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


    /////////////////////////////     PRIVATE    ///////////////////////////////////////////////////


    private void setPieceMovement(int toPosition){
        allHolders[toPosition].myTextView.setBackgroundColor(highlighMovementColor);
        turnHandler.askingToMovePiece(toPosition);
    }

    private void highlightMovements(int position){
        PieceTypeEnum piece = piecesPositions.whichPiece(position);
        if(turnHandler.isSelected(position))
            clearHighlight();
        else if(turnHandler.isValidSelection(piece))
            highlightSelectedPiece(position);
    }

    private void highlightSelectedPiece(int position){
        PieceTypeEnum piece = piecesPositions.whichPiece(position);
        if(!turnHandler.isSelectionPossible(position, piece))
            return;
        turnHandler.setPossibleMovements(piecesPositions.possibleMovements(position));
        if(turnHandler.isMovementPossible()) {
            allHolders[position].myTextView.setBackgroundColor(highlightColor);
            turnHandler.setSelectedPiece(position, piece);
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
}
