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
import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.database.service.MancareService;
import com.example.your_personal_agenda_app.util.MancareAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentMancaruri extends Fragment {

    private static String MANCARURI_KEY = "mancaruri_key";
    private static String ID_PERSOANA_KEY = "id_persoana_key";

    private ListView lvMancaruri;
    private List<Mancare> listaMancaruri = new ArrayList<>();
    private List<Mancare> listaMancaruriPersoana = new ArrayList<>();
    private long idPersoanaMancare;
    private MancareService mancareService;

    public FragmentMancaruri() {
        // Required empty public constructor
    }

    public static FragmentMancaruri newInstance(ArrayList<Mancare> mancaruri, long id) {
        FragmentMancaruri fragment = new FragmentMancaruri();
        Bundle argumente = new Bundle();
        argumente.putSerializable(FragmentMancaruri.MANCARURI_KEY,mancaruri);
        argumente.putLong(ID_PERSOANA_KEY,id);
        fragment.setArguments(argumente);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mancaruri, container, false);
        initComponentsFragmentMancaruri(view);
        return view;
    }

    private void initComponentsFragmentMancaruri(View view) {
        lvMancaruri = view.findViewById(R.id.lv_fragment_mancaruri);
        lvMancaruri.setOnItemClickListener(mancareSelectEventListener());
        lvMancaruri.setOnItemLongClickListener(mancareDeleteEventListener());
        if (getArguments() != null) {
            listaMancaruri = (List<Mancare>)getArguments().getSerializable(MANCARURI_KEY);
            idPersoanaMancare = getArguments().getLong(ID_PERSOANA_KEY);
            for(Mancare mancare:listaMancaruri)
            {
                if(idPersoanaMancare == mancare.getIdPersoanaMancare())
                {
                    listaMancaruriPersoana.add(mancare);
                }
            }
        }
        if (getContext() != null) {
           MancareAdapter adapter= new MancareAdapter(getContext().getApplicationContext(),
                    R.layout.lv_mancaruri_view, listaMancaruriPersoana, getLayoutInflater());
            mancareService = new MancareService(getContext());
            lvMancaruri.setAdapter(adapter);
            notifyInternalAdapterMancaruri();
        }
    }

    private AdapterView.OnItemLongClickListener mancareDeleteEventListener() {
        return new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.selectedMancareIndex = -1;
                for(Mancare mancare:listaMancaruri)
                {
                    if(mancare.getId() == listaMancaruriPersoana.get(position).getId())
                    {
                        listaMancaruri.remove(mancare);
                        break;
                    }
                }
                mancareService.delete(deleteToDbCallbackMancare(position),listaMancaruriPersoana.get(position));
                return true;
            }
        };
    }

    private AdapterView.OnItemClickListener mancareSelectEventListener() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.selectedMancareIndex = position;

            }
        };
    }

    private Callback<Integer> deleteToDbCallbackMancare(final int position) {
        return new Callback<Integer>() {
            @Override
            public void runResultOnUiThread(Integer result) {
                listaMancaruriPersoana.remove(position);
                notifyInternalAdapterMancaruri();
            }
        };
    }


    public void notifyInternalAdapterMancaruri() {
        ArrayAdapter adapter = (ArrayAdapter) lvMancaruri.getAdapter();
        adapter.notifyDataSetChanged();
    }
}