package com.example.your_personal_agenda_app.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.your_personal_agenda_app.database.dao.ActivitateDao;
import com.example.your_personal_agenda_app.database.dao.MancareDao;
import com.example.your_personal_agenda_app.database.dao.PersoanaBDDao;
import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.util.DateConverter;
import com.example.your_personal_agenda_app.util.FelMancareConverter;
import com.example.your_personal_agenda_app.util.GenPersoanaConverter;

@Database(entities = {PersoanaBD.class, Mancare.class, Activitate.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class, GenPersoanaConverter.class, FelMancareConverter.class})
public abstract class DatabaseManager extends RoomDatabase {
    private static final String THE_REDDIT_GUYS_PROIECT_DAM_DB = "the_reddit_guys_proiect_dam_db";

    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, THE_REDDIT_GUYS_PROIECT_DAM_DB)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return databaseManager;
    }
    public abstract PersoanaBDDao getPersoanaBDDao();
    public abstract MancareDao getMancareDao();
    public abstract ActivitateDao getActivitateDao();
}