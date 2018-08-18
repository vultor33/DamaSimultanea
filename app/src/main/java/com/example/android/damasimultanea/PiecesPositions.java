package com.example.android.damasimultanea;

import android.util.Log;

public class PiecesPositions {
    private int tableSize = 64;
    private int[] enabledPositions = {
            0,2,4,6,
            9,11,13,15,
            16,18,20,22,
            25,27,29,31,
            32,34,36,38,
            41,43,45,47,
            48,50,52,54,
            57,59,61,63};

    private int[] redPieces = {
            0,2,4,6,
            9,11,13,15,
            16,18,20,22};

    private int[] bluePieces = {
            41,43,45,47,
            48,50,52,54,
            57,59,61,63};

    public int getTableSize(){
        return tableSize;
    }

    public boolean isPlayablePosition(int position){
        for (int enabled : enabledPositions){
            if(position == enabled)
                return true;
        }
        return false;
    }

    public int whichPiece(int position){
        for (int enabled : redPieces){
            if(position == enabled)
                return 1;
        }
        for (int enabled : bluePieces){
            if(position == enabled)
                return 2;
        }
        return 0;
    }




}
