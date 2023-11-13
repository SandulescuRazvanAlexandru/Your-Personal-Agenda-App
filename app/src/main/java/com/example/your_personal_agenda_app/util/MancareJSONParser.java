package com.example.your_personal_agenda_app.util;

import com.example.your_personal_agenda_app.database.model.Mancare;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MancareJSONParser {

    public static final String DESCRIERE_MANCARE = "descriereMancare";
    public static final String FEL_MANCARE = "felMancare";
    public static final String GRAMAJ_MANCARE = "gramajMancare";
    public static final String MACRONUTRIENTI_MANCARE = "macronutrientiMancare";
    public static final String PROTEINE_MANCARE = "proteineMancare";
    public static final String CARBOHIDRATI_MANCARE = "carbohidratiMancare";
    public static final String GRASIMI_MANCARE = "grasimiMancare";
    public static final String CALORII_MANCARE = "caloriiMancare";


    public static List<Mancare> readMancaruri(JSONArray array) throws JSONException {
        List<Mancare> mancaruri = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Mancare mancare = readMancare(array.getJSONObject(i));
            mancaruri.add(mancare);
        }
        return mancaruri;
    }

    private static Mancare readMancare(JSONObject object) throws JSONException {
        String descriereMancare = object.getString(DESCRIERE_MANCARE);
        FelMancare felMancare = FelMancare.valueOf("ALTELE");
        if(object.getString(FEL_MANCARE).toUpperCase().equals("MIC-DEJUN"))
        {
            felMancare= FelMancare.valueOf("MIC_DEJUN");
        }
        else
        {
            FelMancare.valueOf(object.getString(FEL_MANCARE).toUpperCase());
        }
        double gramajMancare = object.getDouble(GRAMAJ_MANCARE);
        JSONObject objectMacronutrienti = object.getJSONObject(MACRONUTRIENTI_MANCARE);
        double proteineMancare = 0;
        double carbohidratiMancare = 0;
        double grasimiMancare = 0;
        //if(validateProteineMancare(object))
        //{
            proteineMancare = objectMacronutrienti.getDouble(PROTEINE_MANCARE);
        //}
        //if(validateCarbohidratiMancare(object))
        //{
           // Log.i("carbohidratiTest3","check2");
            carbohidratiMancare = objectMacronutrienti.getDouble(CARBOHIDRATI_MANCARE);
        //}
        //Log.i("carbohidratiTest4","picat2");
       // if(validateGrasimiMancare(object))
       // {
            grasimiMancare = objectMacronutrienti.getDouble(GRASIMI_MANCARE);
       // }
        double caloriiMancare = object.getDouble(CALORII_MANCARE);
        return new Mancare(descriereMancare, felMancare, gramajMancare, proteineMancare, carbohidratiMancare, grasimiMancare, caloriiMancare, 0);
    }

//    private static boolean validateProteineMancare(JSONObject object) throws JSONException
//    {
//        try{
//            double proteineMancare = object.getDouble(PROTEINE_MANCARE);
//
//            return true;
//        }
//        catch(JSONException e)
//        {
//            return false;
//        }
//    }
//
//    private static boolean validateCarbohidratiMancare(JSONObject object) throws JSONException
//    {
//        try{
//            Log.i("carbohidratiTest0","checking");
//            double carbohidratiMancare = object.getDouble(CARBOHIDRATI_MANCARE);
//            Log.i("carbohidratiTest","check");
//            return true;
//        }
//        catch(JSONException e)
//        {
//            //Log.i("carbohidratiTest2","picat");
//            return false;
//        }
//    }
//
//    private static boolean validateGrasimiMancare(JSONObject object) throws JSONException
//    {
//        try{
//            double grasimiMancare = object.getDouble(GRASIMI_MANCARE);
//            return true;
//        }
//        catch(JSONException e)
//        {
//            return false;
//        }
//    }
}