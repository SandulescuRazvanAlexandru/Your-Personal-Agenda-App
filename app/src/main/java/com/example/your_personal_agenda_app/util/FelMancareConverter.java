package com.example.your_personal_agenda_app.util;

import androidx.room.TypeConverter;

public class FelMancareConverter {

    @TypeConverter
    public FelMancare fromString(String value) {
        try {
            return FelMancare.valueOf(value);
        } catch (IllegalArgumentException e ) {
            return null;
        }
    }

    @TypeConverter
    public String toString(FelMancare value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

}