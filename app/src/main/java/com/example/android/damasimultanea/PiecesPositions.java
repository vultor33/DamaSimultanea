package com.example.android.damasimultanea;


public class PiecesPositions {
    private int tableSize = 64;



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
        for(int i=0; i<8; i++){
            for(int j=0; j<4; j++){
                if(position == playablePositionsTable[i][j])
                    return true;
            }
        }
        return false;

/*
        for (int enabled : enabledPositions){
            if(position == enabled)
                return true;
        }
        return false;
        */
    }

    public PieceTypeEnum whichPiece(int position){
        for(int i=0; i<8; i++){
            for(int j=0; j<4; j++){
                if(position == playablePositionsTable[i][j])
                    return pieceTypeTable[i][j];
            }
        }
        return PieceTypeEnum.BLANK;
        /*throw exception
        for (int enabled : redPieces){
            if(position == enabled)
                return 1;
        }
        for (int enabled : bluePieces){
            if(position == enabled)
                return 2;
        }
        return 0;
        */
    }





















    private int[] enabledPositions = {
            0,2,4,6,
            9,11,13,15,
            16,18,20,22,
            25,27,29,31,
            32,34,36,38,
            41,43,45,47,
            48,50,52,54,
            57,59,61,63};



    private int[][] playablePositionsTable = new int[8][4];
    private PieceTypeEnum[][] pieceTypeTable = new PieceTypeEnum[8][4];

    PiecesPositions(){

        playablePositionsTable[0][0] = 0;
        playablePositionsTable[0][1] = 2;
        playablePositionsTable[0][2] = 4;
        playablePositionsTable[0][3] = 6;

        playablePositionsTable[1][0] = 9;
        playablePositionsTable[1][1] = 11;
        playablePositionsTable[1][2] = 13;
        playablePositionsTable[1][3] = 15;

        playablePositionsTable[2][0] = 16;
        playablePositionsTable[2][1] = 18;
        playablePositionsTable[2][2] = 20;
        playablePositionsTable[2][3] = 22;

        playablePositionsTable[3][0] = 25;
        playablePositionsTable[3][1] = 27;
        playablePositionsTable[3][2] = 29;
        playablePositionsTable[3][3] = 31;

        playablePositionsTable[4][0] = 32;
        playablePositionsTable[4][1] = 34;
        playablePositionsTable[4][2] = 36;
        playablePositionsTable[4][3] = 38;

        playablePositionsTable[5][0] = 41;
        playablePositionsTable[5][1] = 43;
        playablePositionsTable[5][2] = 45;
        playablePositionsTable[5][3] = 47;

        playablePositionsTable[6][0] = 48;
        playablePositionsTable[6][1] = 50;
        playablePositionsTable[6][2] = 52;
        playablePositionsTable[6][3] = 54;

        playablePositionsTable[7][0] = 57;
        playablePositionsTable[7][1] = 59;
        playablePositionsTable[7][2] = 61;
        playablePositionsTable[7][3] = 63;

        pieceTypeTable[0][0] = PieceTypeEnum.A;
        pieceTypeTable[0][1] = PieceTypeEnum.A;
        pieceTypeTable[0][2] = PieceTypeEnum.A;
        pieceTypeTable[0][3] = PieceTypeEnum.A;

        pieceTypeTable[1][0] = PieceTypeEnum.A;
        pieceTypeTable[1][1] = PieceTypeEnum.A;
        pieceTypeTable[1][2] = PieceTypeEnum.A;
        pieceTypeTable[1][3] = PieceTypeEnum.A;

        pieceTypeTable[2][0] = PieceTypeEnum.A;
        pieceTypeTable[2][1] = PieceTypeEnum.A;
        pieceTypeTable[2][2] = PieceTypeEnum.A;
        pieceTypeTable[2][3] = PieceTypeEnum.A;

        pieceTypeTable[3][0] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][1] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][2] = PieceTypeEnum.BLANK;
        pieceTypeTable[3][3] = PieceTypeEnum.BLANK;

        pieceTypeTable[4][0] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][1] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][2] = PieceTypeEnum.BLANK;
        pieceTypeTable[4][3] = PieceTypeEnum.BLANK;

        pieceTypeTable[5][0] = PieceTypeEnum.B;
        pieceTypeTable[5][1] = PieceTypeEnum.B;
        pieceTypeTable[5][2] = PieceTypeEnum.B;
        pieceTypeTable[5][3] = PieceTypeEnum.B;

        pieceTypeTable[6][0] = PieceTypeEnum.B;
        pieceTypeTable[6][1] = PieceTypeEnum.B;
        pieceTypeTable[6][2] = PieceTypeEnum.B;
        pieceTypeTable[6][3] = PieceTypeEnum.B;

        pieceTypeTable[7][0] = PieceTypeEnum.B;
        pieceTypeTable[7][1] = PieceTypeEnum.B;
        pieceTypeTable[7][2] = PieceTypeEnum.B;
        pieceTypeTable[7][3] = PieceTypeEnum.B;

    }
}
