package com.example.your_personal_agenda_app.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.your_personal_agenda_app.database.model.Activitate;

import java.util.List;

@Dao
public interface ActivitateDao {

    @Query("select * from activitati")
    List<Activitate> getAll();

    @Insert
    long insert(Activitate activitate);

    @Update
    int update(Activitate activitate);

    @Delete
    int delete(Activitate activitate);

}
