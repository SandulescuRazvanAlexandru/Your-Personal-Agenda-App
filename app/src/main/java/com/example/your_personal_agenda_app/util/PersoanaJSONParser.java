package com.example.your_personal_agenda_app.util;

import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.model.Mancare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PersoanaJSONParser {

    public static final String NUME_PERSOANA = "numePersoana";
    public static final String VARSTA_PERSOANA = "varstaPersoana";
    public static final String GEN_PERSOANA = "genPersoana";
    public static final String GREUTATE_PERSOANA = "greutatePersoana";
    public static final String INALTIME_PERSOANA = "inaltimePersoana";
    public static final String DATA_PERSOANA = "dataPersoana";
    public static final String MANCARURI_PERSOANA = "mancaruriPersoana";
    public static final String ACTIVITATI_PERSOANA = "activitatiPersoana";
    private static DateConverter dateConverter = new DateConverter();


    public static List<Persoana> fromJson(String json) {
        try {
            JSONArray array = new JSONArray(json);
            return readPersoane(array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private static List<Persoana> readPersoane(JSONArray array) throws JSONException {
        List<Persoana> persoane = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
           Persoana persoana = readPersoana(array.getJSONObject(i));
            persoane.add(persoana);
        }
        return persoane;
    }

    private static Persoana readPersoana(JSONObject object) throws JSONException {
        String numePersoana = object.getString(NUME_PERSOANA);
        int varstaPersoana = object.getInt(VARSTA_PERSOANA);
        GenPersoana genPersoana =  GenPersoana.valueOf(object.getString(GEN_PERSOANA).toUpperCase());
        double greutatePersoana = object.getDouble(GREUTATE_PERSOANA);
        double inaltimePersoana = object.getDouble(INALTIME_PERSOANA);
        Date dataPersoana = dateConverter.fromString(object.getString(DATA_PERSOANA));
        ArrayList<Mancare> listaMancaruriPersoana = (ArrayList<Mancare>) MancareJSONParser.readMancaruri(object.getJSONArray(MANCARURI_PERSOANA));
        ArrayList<Activitate> listaActivitatiPersoana = (ArrayList<Activitate>) ActivitateJSONParser.readActivitati(object.getJSONArray(ACTIVITATI_PERSOANA));
        return new Persoana(numePersoana, varstaPersoana, genPersoana, greutatePersoana, inaltimePersoana, dataPersoana, listaMancaruriPersoana, listaActivitatiPersoana);
    }
}