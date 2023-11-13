package com.example.your_personal_agenda_app.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.MainActivity;
import com.example.your_personal_agenda_app.R;
import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.service.ActivitateService;
import com.example.your_personal_agenda_app.util.ActivitateAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentActivitati extends Fragment {

    private static String ACTIVITATI_KEY = "activitati_key";
    private static String ID_PERSOANA_KEY = "id_persoana_key";

    private ListView lvActivitati;
    private List<Activitate> listaActivitati = new ArrayList<>();
    private List<Activitate> listaActivitatiPersoana = new ArrayList<>();
    private long idPersoanaActivitate;
    private ActivitateService activitateService;

    public FragmentActivitati() {
        // Required empty public constructor
    }

    public static FragmentActivitati newInstance(ArrayList<Activitate> activitati, long id) {
        FragmentActivitati fragment = new FragmentActivitati();
        Bundle argumente = new Bundle();
        argumente.putSerializable(FragmentActivitati.ACTIVITATI_KEY,activitati);
        argumente.putLong(ID_PERSOANA_KEY,id);
        fragment.setArguments(argumente);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activitati, container, false);
        initComponentsFragmentMancaruri(view);
        return view;
    }

    private void initComponentsFragmentMancaruri(View view) {
        lvActivitati = view.findViewById(R.id.lv_fragment_activitati);
        lvActivitati.setOnItemClickListener(activitateSelectEventListener());
        lvActivitati.setOnItemLongClickListener(activitateDeleteEventListener());
        if (getArguments() != null) {
            listaActivitati = (List<Activitate>)getArguments().getSerializable(ACTIVITATI_KEY);
            idPersoanaActivitate = getArguments().getLong(ID_PERSOANA_KEY);
            for(Activitate activitate:listaActivitati)
            {
                if(idPersoanaActivitate == activitate.getIdPersoanaActivitate())
                {
                    listaActivitatiPersoana.add(activitate);
                }
            }
        }
        if (getContext() != null) {
            ActivitateAdapter adapter= new ActivitateAdapter(getContext().getApplicationContext(),
                    R.layout.lv_activitati_view, listaActivitatiPersoana, getLayoutInflater());
            activitateService = new ActivitateService(getContext());
            lvActivitati.setAdapter(adapter);
            notifyInternalAdapterActivitati();
        }
    }

    private AdapterView.OnItemLongClickListener activitateDeleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.selectedActivitateIndex = -1;
                for(Activitate activitate:listaActivitati)
                {
                    if(activitate.getId() == listaActivitatiPersoana.get(position).getId())
                    {
                        listaActivitati.remove(activitate);
                        break;
                    }
                }
                activitateService.delete(deleteToDbCallbackActivitate(position),listaActivitatiPersoana.get(position));
                return true;
            }
        };
    }

    private Callback<Integer> deleteToDbCallbackActivitate(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                listaActivitatiPersoana.remove(position);
                notifyInternalAdapterActivitati();
            }
        };
    }

    private AdapterView.OnItemClickListener activitateSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.selectedActivitateIndex = position;
            }
        };
    }


    public void notifyInternalAdapterActivitati() {
        ArrayAdapter adapter = (ArrayAdapter) lvActivitati.getAdapter();
        adapter.notifyDataSetChanged();
    }
}