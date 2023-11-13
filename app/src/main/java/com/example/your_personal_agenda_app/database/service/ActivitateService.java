package com.example.your_personal_agenda_app.database.service;

import android.content.Context;

import com.example.your_personal_agenda_app.AsyncTask.AsyncTaskRunner;
import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.database.DatabaseManager;
import com.example.your_personal_agenda_app.database.dao.ActivitateDao;
import com.example.your_personal_agenda_app.database.model.Activitate;

import java.util.List;
import java.util.concurrent.Callable;


public class ActivitateService {

    private final ActivitateDao activitateDao;
    private final AsyncTaskRunner taskRunner;

    public ActivitateService(Context context) {
        activitateDao = DatabaseManager.getInstance(context).getActivitateDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Activitate>> callback) {
        Callable<List<Activitate>> callable = new Callable<List<Activitate>>() {
            @Override
            public List<Activitate> call() {
                return activitateDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Activitate> callback, final Activitate activitate) {
        Callable<Activitate> callable = new Callable<Activitate>() {
            @Override
            public Activitate call() {
                if (activitate == null) {
                    return null;
                }
                long id = activitateDao.insert(activitate);
                if (id == -1) {
                    return null;
                }
                activitate.setId(id);
                return activitate;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Activitate> callback, final Activitate activitate) {
        Callable<Activitate> callable = new Callable<Activitate>() {
            @Override
            public Activitate call() {
                if (activitate == null) {
                    return null;
                }
                int count = activitateDao.update(activitate);
                if (count < 1) {
                    return null;
                }
                return activitate;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Activitate activitate) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (activitate == null) {
                    return -1;
                }
                return activitateDao.delete(activitate);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

}
