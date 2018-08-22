package com.example.android.damasimultanea.database;

import android.arch.persistence.room.TypeConverter;

public class IsPlayableConverter {
    @TypeConverter
    public static boolean toBoolean(int isPlayable) {
        return isPlayable == 1;
    }

    @TypeConverter
    public static int toInt(boolean isPlayable) {
        if(isPlayable)
            return 1;
        else
            return 0;
    }
}
