package com.example.your_personal_agenda_app.AsyncTask;

public interface Callback <R> {

    void runResultOnUiThread(R result);
}