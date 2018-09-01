package com.example.android.damasimultanea.database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.android.damasimultanea.PieceTypeEnum;

@Entity(tableName = "piece")
public class PieceEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int position;
    private int row;
    private int column;

    private PieceTypeEnum pieceType; 
    private boolean isPlayable;


    public PieceEntry(int id, int position, boolean isPlayable, PieceTypeEnum pieceType, int row, int column) {
        this.id = id;
        this.position = position;
        this.isPlayable = isPlayable;
        this.pieceType = pieceType;
        this.row = row;
        this.column = column;
    }

    @Ignore
    public PieceEntry() {}

    @Ignore
    public PieceEntry(int position, boolean isPlayable, PieceTypeEnum pieceType, int row, int column) {
        this.position = position;
        this.isPlayable = isPlayable;
        this.pieceType = pieceType;
        this.row = row;
        this.column = column;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
      return id;
    }

    public int getPosition() {
        return position;
    }

    public boolean getIsPlayable() {
        return isPlayable;
    }

    public PieceTypeEnum getPieceType() {
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

    public void setIsPlayable(boolean isPlayable) {
        this.isPlayable = isPlayable;
    }

    public void setPieceType(PieceTypeEnum pieceType) {
        this.pieceType = pieceType;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }
}
