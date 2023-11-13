package com.example.your_personal_agenda_app.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.your_personal_agenda_app.R;

import java.util.List;

public class PersoanaFirebaseAdapter extends ArrayAdapter<PersoanaFirebase> {

    private Context context;
    private List<PersoanaFirebase> persoane;
    private LayoutInflater inflater;
    private int resource;

    public PersoanaFirebaseAdapter(@NonNull Context context, int resource,
                                   @NonNull List<PersoanaFirebase> objects, LayoutInflater inflater) {
        super(context, resource, objects);
        this.context = context;
        this.persoane = objects;
        this.inflater = inflater;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = inflater.inflate(resource, parent, false);
        PersoanaFirebase persoana = persoane.get(position);
        if (persoana != null) {
            addNumePersoana(view, persoana.getNumePersoana());
            addVarstaPersoana(view, persoana.getVarstaPersoana());
            addGreutatePersoana(view, persoana.getGreutatePersoana());
            addInaltimePersoana(view, persoana.getInaltimePersoana());
        }
        return view;
    }

    private void addNumePersoana(View view, String numePersoana) {
        TextView textView = view.findViewById(R.id.tv_lv_persoane_view_nume);
        populateTextViewContent(textView, numePersoana);
    }

    private void addVarstaPersoana(View view, int varstaPersoana) {
        TextView textView = view.findViewById(R.id.tv_lv_persoane_view_varsta);
        String varsta = "";
        if(varstaPersoana>1)
        {
            varsta = context.getString(R.string.tv_lv_persoane_view_varsta_diferit1,varstaPersoana);
        }
        else
        {
            varsta = context.getString(R.string.tv_lv_persoane_view_varsta_egal1,varstaPersoana);
        }
        populateTextViewContent(textView, varsta);
    }

    private void addGreutatePersoana(View view, double greutatePersoana) {
        TextView textView = view.findViewById(R.id.tv_lv_persoane_view_greutate);
        String greutate = context.getString(R.string.tv_lv_persoane_view_greutate_text,greutatePersoana);
        populateTextViewContent(textView, greutate);
    }

    private void addInaltimePersoana(View view, double inaltimePersoana) {
        TextView textView = view.findViewById(R.id.tv_lv_persoane_view_inaltime);
        String inaltime = context.getString(R.string.tv_lv_persoane_view_inaltime_text,inaltimePersoana);
        populateTextViewContent(textView, inaltime);
    }

    private void populateTextViewContent(TextView textView, String value) {
        if (value != null && !value.trim().isEmpty()) {
            textView.setText(value);
        } else {
            textView.setText(R.string.lv_row_view_no_content);
        }
    }
}
