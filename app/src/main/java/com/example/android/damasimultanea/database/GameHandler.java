package com.example.android.damasimultanea.database;

public class GameHandler {

    private String server; //nome da pessoa que vai alterar o BOARD
    private int player1MovedPiecePosition;
    private int player1MovedPieceDestination;
    private int player2MovedPiecePosition;
    private int player2MovedPieceDestination;
    private boolean isPlayable;

    public GameHandler(){}

    public GameHandler(
            String server,
            int player1MovedPiecePosition,
            int player1MovedPieceDestination,
            int player2MovedPiecePosition,
            int player2MovedPieceDestination,
            boolean isPlayable)
    {
        this.server = server;
        this.player1MovedPiecePosition = player1MovedPiecePosition;
        this.player1MovedPieceDestination = player1MovedPieceDestination;
        this.player2MovedPiecePosition = player2MovedPiecePosition;
        this.player2MovedPieceDestination = player2MovedPieceDestination;
        this.isPlayable = isPlayable;
    }

    public String getServer() {
        return server;
    }

    public int getPlayer1MovedPiecePosition() {
        return player1MovedPiecePosition;
    }

    public int getPlayer1MovedPieceDestination() {
        return player1MovedPieceDestination;
    }

    public int getPlayer2MovedPiecePosition() {
        return player2MovedPiecePosition;
    }

    public int getPlayer2MovedPieceDestination() {
        return player2MovedPieceDestination;
    }

    public boolean isPlayable() {
        return isPlayable;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setPlayer1MovedPiecePosition(int player1MovedPiecePosition) {
        this.player1MovedPiecePosition = player1MovedPiecePosition;
    }

    public void setPlayer1MovedPieceDestination(int player1MovedPieceDestination) {
        this.player1MovedPieceDestination = player1MovedPieceDestination;
    }

    public void setPlayer2MovedPiecePosition(int player2MovedPiecePosition) {
        this.player2MovedPiecePosition = player2MovedPiecePosition;
    }

    public void setPlayer2MovedPieceDestination(int player2MovedPieceDestination) {
        this.player2MovedPieceDestination = player2MovedPieceDestination;
    }

    public void setPlayable(boolean playable) {
        isPlayable = playable;
    }
}
