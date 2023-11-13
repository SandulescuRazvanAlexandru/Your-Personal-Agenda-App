package com.example.your_personal_agenda_app.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.your_personal_agenda_app.database.model.PersoanaBD;

import java.util.List;

@Dao
public interface PersoanaBDDao {

    @Query("select * from persoane")
    List<PersoanaBD> getAll();

    @Insert
    long insert(PersoanaBD persoana);

    @Update
    int update(PersoanaBD persoana);

    @Delete
    int delete(PersoanaBD persoana);

}
