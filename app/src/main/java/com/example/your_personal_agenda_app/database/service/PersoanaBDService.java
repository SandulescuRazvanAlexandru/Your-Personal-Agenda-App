package com.example.your_personal_agenda_app.database.service;

import android.content.Context;

import com.example.your_personal_agenda_app.AsyncTask.AsyncTaskRunner;
import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.database.DatabaseManager;
import com.example.your_personal_agenda_app.database.dao.PersoanaBDDao;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;

import java.util.List;
import java.util.concurrent.Callable;

public class PersoanaBDService {

    private final PersoanaBDDao persoanaBDDao;
    private final AsyncTaskRunner taskRunner;

    public PersoanaBDService(Context context) {
        persoanaBDDao = DatabaseManager.getInstance(context).getPersoanaBDDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<PersoanaBD>> callback) {
        Callable<List<PersoanaBD>> callable = new Callable<List<PersoanaBD>>() {
            @Override
            public List<PersoanaBD> call() {
                return persoanaBDDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<PersoanaBD> callback, final PersoanaBD persoanaBD) {
        Callable<PersoanaBD> callable = new Callable<PersoanaBD>() {
            @Override
            public PersoanaBD call() {
                if (persoanaBD == null) {
                    return null;
                }
                long id = persoanaBDDao.insert(persoanaBD);
                if (id == -1) {
                    return null;
                }
                persoanaBD.setId(id);
                return persoanaBD;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<PersoanaBD> callback, final PersoanaBD persoanaBD) {
        Callable<PersoanaBD> callable = new Callable<PersoanaBD>() {
            @Override
            public PersoanaBD call() {
                if (persoanaBD == null) {
                    return null;
                }
                int count = persoanaBDDao.update(persoanaBD);
                if (count < 1) {
                    return null;
                }
                return persoanaBD;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final PersoanaBD persoanaBD) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (persoanaBD == null) {
                    return -1;
                }
                return persoanaBDDao.delete(persoanaBD);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

}
