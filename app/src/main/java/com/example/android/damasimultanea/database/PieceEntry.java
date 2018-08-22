package com.example.android.damasimultanea.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "piece")
public class PieceEntry {

    @PrimaryKey(autoGenerate = true)
    private int position;

    private int isPlayable;
    private int pieceType; //TODO - colocar o tipo estanho aqui e fazer a conversao depois - playable conversao para blue
    private int row;
    private int column;

    public PieceEntry(int position, int isPlayable, int pieceType, int row, int column) {
        this.position = position;
        this.isPlayable = isPlayable;
        this.pieceType = pieceType;
        this.row = row;
        this.column = column;
    }

    public int getPosition() {
        return position;
    }

    public int getIsPlayable() {
        return isPlayable;
    }

    public int getPieceType() {
        return pieceType;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setIsPlayable(int isPlayable) {
        this.isPlayable = isPlayable;
    }

    public void setPieceType(int pieceType) {
        this.pieceType = pieceType;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
