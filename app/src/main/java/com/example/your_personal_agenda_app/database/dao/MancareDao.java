package com.example.your_personal_agenda_app.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.your_personal_agenda_app.database.model.Mancare;

import java.util.List;

@Dao
public interface MancareDao {

    @Query("select * from mancaruri")
    List<Mancare> getAll();

    @Insert
    long insert(Mancare mancare);

    @Update
    int update(Mancare mancare);

    @Delete
    int delete(Mancare mancare);

}
