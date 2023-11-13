package com.example.your_personal_agenda_app.database.service;

import android.content.Context;

import com.example.your_personal_agenda_app.AsyncTask.AsyncTaskRunner;
import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.database.DatabaseManager;
import com.example.your_personal_agenda_app.database.dao.MancareDao;
import com.example.your_personal_agenda_app.database.model.Mancare;

import java.util.List;
import java.util.concurrent.Callable;

public class MancareService {

    private final MancareDao mancareDao;
    private final AsyncTaskRunner taskRunner;

    public MancareService(Context context) {
        mancareDao = DatabaseManager.getInstance(context).getMancareDao();
        taskRunner = new AsyncTaskRunner();
    }

    public void getAll(Callback<List<Mancare>> callback) {
        Callable<List<Mancare>> callable = new Callable<List<Mancare>>() {
            @Override
            public List<Mancare> call() {
                return mancareDao.getAll();
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void insert(Callback<Mancare> callback, final Mancare mancare) {
        Callable<Mancare> callable = new Callable<Mancare>() {
            @Override
            public Mancare call() {
                if (mancare == null) {
                    return null;
                }
                long id = mancareDao.insert(mancare);
                if (id == -1) {
                    return null;
                }
                mancare.setId(id);
                return mancare;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void update(Callback<Mancare> callback, final Mancare mancare) {
        Callable<Mancare> callable = new Callable<Mancare>() {
            @Override
            public Mancare call() {
                if (mancare == null) {
                    return null;
                }
                int count = mancareDao.update(mancare);
                if (count < 1) {
                    return null;
                }
                return mancare;
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

    public void delete(Callback<Integer> callback, final Mancare mancare) {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() {
                if (mancare == null) {
                    return -1;
                }
                return mancareDao.delete(mancare);
            }
        };
        taskRunner.executeAsync(callable, callback);
    }

}
