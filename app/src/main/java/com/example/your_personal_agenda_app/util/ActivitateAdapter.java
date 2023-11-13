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
import com.example.your_personal_agenda_app.database.model.Activitate;


public class ActivitateAdapter extends ArrayAdapter<Activitate> {

    private Context context;
    private List<Activitate> activitati;
    private LayoutInflater inflater;
    private int resource;

    public ActivitateAdapter(@NonNull Context context, int resource,
                              @NonNull List<Activitate> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.activitati = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        Activitate activitate = activitati.get(position);
        if (activitate != null) {
            addTipActivitate(view, activitate.getTipActivitate());
            addOreActivitate(view, activitate.getOreActivitate());
            addMinuteActivitate(view, activitate.getMinuteActivitate());
            addCaloriiArseActivitate(view, activitate.getCaloriiArseActivitate());
        }
        return view;
    }

    private void addTipActivitate(View view, String tipActivitate) {
        TextView textView = view.findViewById(R.id.tv_lv_activitati_view_tip);
        populateTextViewContent(textView, tipActivitate);
    }

    private void addOreActivitate(View view, int oreActivitate) {
        TextView textView = view.findViewById(R.id.tv_lv_activitati_view_durata_ore);
        String ore = context.getString(R.string.tv_lv_activitati_view_durata_ore_text,oreActivitate);
        populateTextViewContent(textView, ore);
    }

    private void addMinuteActivitate(View view, int minuteActivitate) {
        TextView textView = view.findViewById(R.id.tv_lv_activitati_view_durata_minute);
        String minute = "";
        if(minuteActivitate >= 20)
        {
            minute = context.getString(R.string.tv_lv_activitati_view_durata_minute_text_peste20,minuteActivitate);
        }
        else if ( (minuteActivitate >1 && minuteActivitate < 20) || minuteActivitate == 0)
        {
            minute = context.getString(R.string.tv_lv_activitati_view_durata_minute_text_sub20,minuteActivitate);
        }
        else if (minuteActivitate == 1)
        {
            minute = context.getString(R.string.tv_lv_activitati_view_durata_minute_text_egal1,minuteActivitate);
        }
        populateTextViewContent(textView, minute);
    }

    private void addCaloriiArseActivitate(View view, double caloriiArseActivitate) {
        TextView textView = view.findViewById(R.id.tv_lv_activitati_view_calorii_arse);
        String calorii = context.getString(R.string.lv_activitati_calorii_template,caloriiArseActivitate);
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