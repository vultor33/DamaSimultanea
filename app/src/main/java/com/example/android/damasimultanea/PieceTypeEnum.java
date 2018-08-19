package com.example.android.damasimultanea;

public enum PieceTypeEnum {
    BLANK(0), A(1), B(2);

    private final int valor;
    PieceTypeEnum(int valorOpcao){
        valor = valorOpcao;
    }
}