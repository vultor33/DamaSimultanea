package com.example.android.damasimultanea;

public enum PieceTypeEnum {
    BLANK(0), pieceA(1), pieceB(2), NOTPLAYABLE(3);

    private final int value;
    PieceTypeEnum(int value_in){
        value = value_in;
    }

    public int getValue() {
        return value;
    }
}