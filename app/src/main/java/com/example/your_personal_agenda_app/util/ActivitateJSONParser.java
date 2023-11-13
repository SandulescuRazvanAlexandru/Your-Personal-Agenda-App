package com.example.your_personal_agenda_app.util;

import com.example.your_personal_agenda_app.database.model.Activitate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivitateJSONParser {

    public static final String TIP_ACTIVITATE = "tipActivitate";
    public static final String ORE_ACTIVITATE = "oreActivitate";
    public static final String MINUTE_ACTIVITATE = "minuteActivitate";
    public static final String CALORII_ARSE_ACTIVITATE = "caloriiArseActivitate";

    public static List<Activitate> readActivitati(JSONArray array) throws JSONException {
        List<Activitate> activitati = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Activitate activitate = readActivitate(array.getJSONObject(i));
            activitati.add(activitate);
        }
        return activitati;
    }

    private static Activitate readActivitate(JSONObject object) throws JSONException {
        String tipActivitate = object.getString(TIP_ACTIVITATE);
        int oreActivitate = 0;
        int minuteActivitate = 0;
        if(validateOreActivitate(object))
        {
            oreActivitate = object.getInt(ORE_ACTIVITATE);
        }
        if(validateMinuteActivitate(object))
        {
            minuteActivitate = object.getInt(MINUTE_ACTIVITATE);
        }
        double caloriiArseActivitate = object.getDouble(CALORII_ARSE_ACTIVITATE);
        return new Activitate(tipActivitate,oreActivitate,minuteActivitate,caloriiArseActivitate,0);
    }

    private static boolean validateOreActivitate(JSONObject object) throws JSONException
    {
        try{
            int oreActivitate = object.getInt(ORE_ACTIVITATE);
            return true;
        }
        catch(JSONException e)
        {
            return false;
        }
    }

    private static boolean validateMinuteActivitate(JSONObject object) throws JSONException
    {
        try{
            int minuteActivitate = object.getInt(MINUTE_ACTIVITATE);
            return true;
        }
        catch(JSONException e)
        {
            return false;
        }
    }
}