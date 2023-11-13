package com.example.your_personal_agenda_app.util;

import androidx.room.TypeConverter;

public class GenPersoanaConverter {

    @TypeConverter
    public GenPersoana fromString(String value) {
        try {
            return GenPersoana.valueOf(value);
        } catch (IllegalArgumentException e ) {
            return null;
        }
    }

    @TypeConverter
    public String toString(GenPersoana value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }
}