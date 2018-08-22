package com.example.android.damasimultanea.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface PieceDAO {
    @Query("SELECT * FROM piece ORDER BY position") //devia trocar para is playable
    List<PieceEntry> loadAllPieces();

    @Insert
    void insertPiece(PieceEntry pieceEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updatePiece(PieceEntry pieceEntry);

    @Delete
    void deletePiece(PieceEntry pieceEntry);
}
