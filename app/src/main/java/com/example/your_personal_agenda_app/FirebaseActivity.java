package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.firebase.FirebaseService;
import com.example.your_personal_agenda_app.util.DateConverter;
import com.example.your_personal_agenda_app.util.GenPersoanaConverter;
import com.example.your_personal_agenda_app.util.PersoanaFirebase;
import com.example.your_personal_agenda_app.util.PersoanaFirebaseAdapter;

import java.util.ArrayList;
import java.util.List;

public class FirebaseActivity extends AppCompatActivity {

    public static final String FIREBASE_KEY = "firebase_key";
    private List<PersoanaBD> persoanePrimite = new ArrayList<PersoanaBD>();
    private List<PersoanaFirebase> persoaneFirebase= new ArrayList<PersoanaFirebase>();
    private List<PersoanaFirebase> persoaneFirebaseV2= new ArrayList<PersoanaFirebase>();
    private GenPersoanaConverter converterGen;
    private DateConverter converterData;
    private Button btnSave;
    private Button btnRestaurare;
    private ListView lvPersoaneFirebase;
    private FirebaseService firebaseService;
    private boolean intrare = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase);
        Intent intent = getIntent();
        persoanePrimite = (ArrayList<PersoanaBD>)intent.getSerializableExtra(FIREBASE_KEY);
        primireLista();
        initComponents();
    }

    private void primireLista()
    {
        converterGen = new GenPersoanaConverter();
        converterData = new DateConverter();
        for(PersoanaBD persoana:persoanePrimite)
        {
            PersoanaFirebase pers = new PersoanaFirebase("0",persoana.getNumePersoana(),
                    persoana.getVarstaPersoana(),converterGen.toString(persoana.getGenPersoana())
                    ,persoana.getGreutatePersoana(),persoana.getInaltimePersoana(),converterData.toString(persoana.getDataPersoana()));
            persoaneFirebase.add(pers);
        }
        for(PersoanaBD persoana:persoanePrimite)
        {
            PersoanaFirebase pers = new PersoanaFirebase("0",persoana.getNumePersoana(),
                    persoana.getVarstaPersoana(),converterGen.toString(persoana.getGenPersoana())
                    ,persoana.getGreutatePersoana(),persoana.getInaltimePersoana(),converterData.toString(persoana.getDataPersoana()));
            persoaneFirebaseV2.add(pers);
        }
    }

    private void initComponents()
    {
        btnSave = findViewById(R.id.firebase_save);
        btnRestaurare = findViewById(R.id.firebase_restore);
        lvPersoaneFirebase = findViewById(R.id.lv_firebase_persoane);
        PersoanaFirebaseAdapter adapter = new PersoanaFirebaseAdapter(getApplicationContext(),
                R.layout.lv_persoane_view,persoaneFirebaseV2, getLayoutInflater());
        lvPersoaneFirebase.setAdapter(adapter);
        notifyInternalAdapterPersoaneFirebase();
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangeEventListener(restaurareCallback());
        btnSave.setOnClickListener(saveEventListener());
        btnRestaurare.setOnClickListener(restaurareEventListener());
    }

    private View.OnClickListener saveEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    firebaseService.deleteAll();
                    for(PersoanaFirebase persoana:persoaneFirebaseV2)
                    {
                        firebaseService.upsert(persoana);
                    }

            }
        };
    }

    private View.OnClickListener restaurareEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersoanaFirebase dummy = new PersoanaFirebase("0","test",2,"Feminin",2,2,"2");
                firebaseService.upsert(dummy);
                firebaseService.delete(dummy);
                persoaneFirebaseV2 = persoaneFirebase;
            }
        };
    }

    private Callback<List<PersoanaFirebase>> restaurareCallback() {
        return new Callback<List<PersoanaFirebase>>() {
            @Override
            public void runResultOnUiThread(List<PersoanaFirebase> result) {
                if (result != null) {
                    if(intrare == true)
                    {
                        PersoanaFirebaseAdapter adapter = new PersoanaFirebaseAdapter(getApplicationContext(),
                                R.layout.lv_persoane_view,persoaneFirebase, getLayoutInflater());
                        lvPersoaneFirebase.setAdapter(adapter);
                        intrare=false;
                    }
                    else {
                        persoaneFirebase.clear();
                        persoaneFirebase.addAll(result);
                        notifyInternalAdapterPersoaneFirebase();
                    }
                }
            }
        };
    }

    public void notifyInternalAdapterPersoaneFirebase() {
        ArrayAdapter adapter = (ArrayAdapter) lvPersoaneFirebase.getAdapter();
        adapter.notifyDataSetChanged();
    }
}