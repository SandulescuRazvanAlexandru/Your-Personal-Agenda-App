package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.util.DateConverter;

import java.util.ArrayList;
import java.util.List;

public class RapoarteActivity extends AppCompatActivity {

    public static final String RAPOARTE_PERSOANE_KEY = "rapoarte_persoane_key";
    public static final String RAPOARTE_MANCARURI_KEY = "rapoarte_mancaruri_key";
    public static final String RAPOARTE_ACTIVTTATI_KEY = "rapoarte_activitati_key";
    private Spinner spnPersoane;
    private Spinner spnDate;
    private Button btnAfiseaza;
    private SwitchCompat switchMancaruriActivitati;
    private List<PersoanaBD> persoanePrimite = new ArrayList<PersoanaBD>();
    private List<String> numeUnice = new ArrayList<String>();
    private List<String> date = new ArrayList<String>();
    private List<Mancare> mancaruriPrimite= new ArrayList<Mancare>();
    private List<Mancare> mancaruriTrimise= new ArrayList<Mancare>();
    private List<Activitate> activitatiPrimite =  new ArrayList<Activitate>();
    private List<Activitate> activitatiTrimise =  new ArrayList<Activitate>();
    private DateConverter converter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rapoarte);
        Intent intent = getIntent();
        persoanePrimite = (ArrayList<PersoanaBD>)intent.getSerializableExtra(RAPOARTE_PERSOANE_KEY);
        mancaruriPrimite = (ArrayList<Mancare>)intent.getSerializableExtra(RAPOARTE_MANCARURI_KEY);
        activitatiPrimite = (ArrayList<Activitate>)intent.getSerializableExtra(RAPOARTE_ACTIVTTATI_KEY);
        initComponents();
    }

    private void initComponents()
    {
        spnPersoane = findViewById(R.id.spn_rapoarte_activity_persoane);
        spnDate = findViewById(R.id.spn_rapoarte_activity_data);
        btnAfiseaza = findViewById(R.id.btn_rapoarte_activity_show);
        switchMancaruriActivitati = findViewById(R.id.sw_rapoarte_activity_picker);
        converter = new DateConverter();
        spnPersoane.setOnItemSelectedListener(alegerePersoanaEventListener());
        btnAfiseaza.setOnClickListener(btnAfisearaClickListener());
        createUniqueNames();
        populatePersoaneAdapter();
    }

    private View.OnClickListener btnAfisearaClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(switchMancaruriActivitati.isChecked()))
                {
                    modificareListaMancaruri();
                    Intent intent = new Intent(getApplicationContext(), ChartActivity.class);
                    intent.putExtra(ChartActivity.MANCARURI_KEY, (ArrayList<Mancare>) mancaruriTrimise);
                    startActivity(intent);
                }
                else
                {
                    Log.i("btncheck","check");
                    modificareListaActivitati();
                    Intent intent = new Intent(getApplicationContext(), PieChartActivity.class);
                    intent.putExtra(PieChartActivity.PIE_CHART_ACTIVITATI_KEY, (ArrayList<Activitate>) activitatiTrimise);
                    startActivity(intent);
                }
            }
        };
    }

    private AdapterView.OnItemSelectedListener alegerePersoanaEventListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nume = spnPersoane.getSelectedItem().toString();
                date.clear();
                for(PersoanaBD persoana:persoanePrimite)
                {
                    if(nume.equals(persoana.getNumePersoana()))
                    {
                        date.add(converter.toString(persoana.getDataPersoana()));
                    }
                }
                populateDateAdapter();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                String nume = persoanePrimite.get(0).getNumePersoana();
                date.clear();
                for(PersoanaBD persoana:persoanePrimite)
                {
                    if(nume.equals(persoana.getNumePersoana()))
                    {
                        date.add(converter.toString(persoana.getDataPersoana()));
                    }
                }
                populateDateAdapter();
            }
        };
    }

    private void modificareListaMancaruri()
    {
        mancaruriTrimise.clear();
        String name = spnPersoane.getSelectedItem().toString();
        String data = spnDate.getSelectedItem().toString();
        long id = 0 ;
        for(PersoanaBD persoana:persoanePrimite)
        {
            if(name.equals(persoana.getNumePersoana()) && data.equals(converter.toString(persoana.getDataPersoana())))
            {
                id=persoana.getId();
                break;
            }
        }
        for(Mancare mancare:mancaruriPrimite)
        {
            if(mancare.getIdPersoanaMancare() == id)
            {
                mancaruriTrimise.add(mancare);
            }
        }
    }

    private void modificareListaActivitati()
    {
        activitatiTrimise.clear();
        String name = spnPersoane.getSelectedItem().toString();
        String data = spnDate.getSelectedItem().toString();
        long id = 0 ;
        for(PersoanaBD persoana:persoanePrimite)
        {
            if(name.equals(persoana.getNumePersoana()) && data.equals(converter.toString(persoana.getDataPersoana())))
            {
                id=persoana.getId();
                break;
            }
        }
        for(Activitate activitate:activitatiPrimite)
        {
            if(activitate.getIdPersoanaActivitate() == id)
            {
               activitatiTrimise.add(activitate);
            }
        }
    }

    private void createUniqueNames()
    {
        for(PersoanaBD persoana:persoanePrimite)
        {
            boolean check = true;
            for(int i =0; i < numeUnice.size();++i)
            {
                if(numeUnice.get(i).equals(persoana.getNumePersoana()))
                {
                    check = false;
                }
            }
            if(check)
            {
                numeUnice.add(persoana.getNumePersoana());
            }
        }
    }

    private void populatePersoaneAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,numeUnice);
        spnPersoane.setAdapter(adapter);
    }

    private void populateDateAdapter() {
        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item,date);
        spnDate.setAdapter(adapter);
    }
}