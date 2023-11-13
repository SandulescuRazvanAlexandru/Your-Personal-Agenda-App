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
import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.database.service.ActivitateService;
import com.example.your_personal_agenda_app.database.service.MancareService;
import com.example.your_personal_agenda_app.database.service.PersoanaBDService;
import com.example.your_personal_agenda_app.util.PersoanaBDAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentPersoane extends Fragment {

    private static String PERSOANE_KEY = "persoane_key";
    private static String MANCARURI_KEY = "mancaruri_key";
    private static String ACTIVITATI_KEY = "activitati_key";

    private ListView lvPersoane;
    private List<PersoanaBD> listaPersoane = new ArrayList<>();
    private List<Mancare> listaMancaruri = new ArrayList<>();
    private List<Activitate> listaActivitati = new ArrayList<>();
    private PersoanaBDService persoanaBDService;
    private MancareService mancareService;
    private ActivitateService activitateService;

    public FragmentPersoane() {
        // Required empty public constructor
    }

    public static FragmentPersoane newInstance(ArrayList<PersoanaBD> persoane, ArrayList<Mancare> mancaruri, ArrayList<Activitate> activitati) {
        FragmentPersoane fragment = new FragmentPersoane();
        Bundle argumente = new Bundle();
        argumente.putSerializable(FragmentPersoane.PERSOANE_KEY,persoane);
        argumente.putSerializable(FragmentPersoane.MANCARURI_KEY,mancaruri);
        argumente.putSerializable(FragmentPersoane.ACTIVITATI_KEY,activitati);
        fragment.setArguments(argumente);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_persoane, container, false);
        initComponentsFragmentPersoane(view);
        return view;
    }

    private void initComponentsFragmentPersoane(View view) {
        lvPersoane = view.findViewById(R.id.lv_fragment_persoane);
        lvPersoane.setOnItemClickListener(persoanaSelectEventListener());
        lvPersoane.setOnItemLongClickListener(persoanaDeleteEventListener());
        if (getArguments() != null) {
            listaPersoane = (List<PersoanaBD>)getArguments().getSerializable(PERSOANE_KEY);
            listaMancaruri = (List<Mancare>)getArguments().getSerializable(MANCARURI_KEY);
            listaActivitati = (List<Activitate>)getArguments().getSerializable(ACTIVITATI_KEY);
        }
        if (getContext() != null) {
            PersoanaBDAdapter adapter = new PersoanaBDAdapter(getContext().getApplicationContext(),
                    R.layout.lv_persoane_view, listaPersoane, getLayoutInflater());
            persoanaBDService = new PersoanaBDService(getContext());
            mancareService = new MancareService(getContext());
            activitateService = new ActivitateService(getContext());
            lvPersoane.setAdapter(adapter);
            notifyInternalAdapterPersoane();
        }
    }

    private AdapterView.OnItemClickListener persoanaSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               MainActivity.selectedPersoanaIndex = position;
            }
        };
    }

    private AdapterView.OnItemLongClickListener persoanaDeleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.selectedPersoanaIndex=-1;
                for(Mancare mancare:listaMancaruri)
                {
                    if(mancare.getIdPersoanaMancare() == listaPersoane.get(position).getId())
                    {
                        mancareService.delete(deleteToDbCallbackMancare(),mancare);
                    }
                }
                for(Activitate activitate:listaActivitati)
                {
                    if(activitate.getIdPersoanaActivitate() == listaPersoane.get(position).getId())
                    {
                        activitateService.delete(deleteToDbCallbackActivitate(),activitate);
                    }
                }
                persoanaBDService.delete(deleteToDbCallbackPersoane(position),listaPersoane.get(position));
                return true;
            }
        };
    }

    private Callback<Integer> deleteToDbCallbackPersoane(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                if (result != -1) {
                    listaPersoane.remove(position);
                    notifyInternalAdapterPersoane();
                }
            }
        };
    }

    private Callback<Integer> deleteToDbCallbackMancare() {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
            }
        };
    }

    private Callback<Integer> deleteToDbCallbackActivitate() {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {

            }
        };
    }


    public void notifyInternalAdapterPersoane() {
        ArrayAdapter adapter = (ArrayAdapter) lvPersoane.getAdapter();
        adapter.notifyDataSetChanged();
    }
}