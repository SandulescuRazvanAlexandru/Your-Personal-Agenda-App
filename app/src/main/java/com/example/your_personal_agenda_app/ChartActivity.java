package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.util.ChartView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChartActivity extends AppCompatActivity {
    public static final String MANCARURI_KEY = "mancaruri_key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<Mancare> mancaruri = (ArrayList<Mancare>) getIntent().getSerializableExtra(MANCARURI_KEY);

        setContentView(new ChartView(getApplicationContext(), getSource(mancaruri)));
    }

    private Map<String, Double> getSource(List<Mancare> mancaruri) {
        if (mancaruri == null || mancaruri.isEmpty()) {
            return null;
        }
        Map<String, Double> source = new HashMap<>();
        int i = 0;
        for (Mancare mancare : mancaruri) {
            i++;
           source.put(String.valueOf(i) + ". " + mancare.getDescriereMancare(),mancare.getCaloriiMancare());
        }
        return source;
    }
}