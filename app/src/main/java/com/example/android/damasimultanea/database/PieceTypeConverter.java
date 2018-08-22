package com.example.android.damasimultanea.database;

import android.arch.persistence.room.TypeConverter;

import com.example.android.damasimultanea.PieceTypeEnum;

public class PieceTypeConverter {
    @TypeConverter
    public static PieceTypeEnum toPieceType(int pieceType) {
        switch(pieceType){
            case 0:
                return PieceTypeEnum.BLANK;
            case 1:
                return PieceTypeEnum.pieceA;
            case 2:
                return PieceTypeEnum.pieceB;
            case 3:
                return PieceTypeEnum.NOTPLAYABLE;
            default:
                return PieceTypeEnum.NOTPLAYABLE; //TODO throw exception if enum change i have to come here
        }
    }

    @TypeConverter
    public static int toInt(PieceTypeEnum pieceType) {
        return pieceType.getValue();
    }
}

