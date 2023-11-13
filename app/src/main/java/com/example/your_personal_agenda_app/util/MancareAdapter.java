package com.example.your_personal_agenda_app.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.your_personal_agenda_app.R;
import com.example.your_personal_agenda_app.database.model.Mancare;


public class MancareAdapter extends ArrayAdapter<Mancare> {

    private Context context;
    private List<Mancare> mancaruri;
    private LayoutInflater inflater;
    private int resource;

    public MancareAdapter(@NonNull Context context, int resource,
                             @NonNull List<Mancare> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.mancaruri = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Mancare mancare = mancaruri.get(position);
        if (mancare != null) {
            addDescriereMancare(view, mancare.getDescriereMancare());
            addFelMancare(view, mancare.getFelMancare());
            addGramajMancare(view, mancare.getGramajMancare());
            addProteineMancare(view, mancare.getProteineMancare());
            addCarbohidratiMancare(view, mancare.getCarbohidratiMancare());
            addGrasimiMancare(view, mancare.getGrasimiMancare());
            addCaloriiMancare(view, mancare.getCaloriiMancare());
        }
        return view;
    }

    private void addDescriereMancare(View view, String descriereMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_descriere);
        populateTextViewContent(textView, descriereMancare);
    }

    private void addFelMancare(View view, FelMancare felMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_fel);
        String fel = String.valueOf(felMancare);
        if (fel.equals("MIC_DEJUN"))
        {
            fel = "MIC-DEJUN";
        }
        populateTextViewContent(textView, fel);
    }

    private void addGramajMancare(View view, double gramajMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_gramaj);
        String gramaj = context.getString(R.string.tv_lv_mancaruri_view_macronutrienti_si_gramaj,gramajMancare);
        populateTextViewContent(textView, gramaj);
    }

    private void addProteineMancare(View view, double proteineMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_proteine);
        String proteine = context.getString(R.string.tv_lv_mancaruri_view_macronutrienti_si_gramaj,proteineMancare);
        populateTextViewContent(textView, proteine);
    }

    private void addCarbohidratiMancare(View view, double carbohidratiMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_carbohidrati);
        String carbohidrati = context.getString(R.string.tv_lv_mancaruri_view_macronutrienti_si_gramaj,carbohidratiMancare);
        populateTextViewContent(textView, carbohidrati);
    }

    private void addGrasimiMancare(View view, double grasimiMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_grasimi);
        String grasimi = context.getString(R.string.tv_lv_mancaruri_view_macronutrienti_si_gramaj,grasimiMancare);
        populateTextViewContent(textView, grasimi);
    }

    private void addCaloriiMancare(View view, double caloriiMancare) {
        TextView textView = view.findViewById(R.id.tv_lv_mancaruri_view_calorii);
        String calorii = String.valueOf(caloriiMancare);
        populateTextViewContent(textView, calorii);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.trim().isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_row_view_no_content);
        }
    }
}