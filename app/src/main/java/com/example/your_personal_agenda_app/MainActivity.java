package com.example.your_personal_agenda_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.your_personal_agenda_app.AsyncTask.AsyncTaskRunner;
import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.Network.HTTPManager;
import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.database.service.ActivitateService;
import com.example.your_personal_agenda_app.database.service.MancareService;
import com.example.your_personal_agenda_app.database.service.PersoanaBDService;
import com.example.your_personal_agenda_app.fragments.FragmentActivitati;
import com.example.your_personal_agenda_app.fragments.FragmentMancaruri;
import com.example.your_personal_agenda_app.fragments.FragmentPersoane;
import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.model.Mancare;
import com.example.your_personal_agenda_app.util.Persoana;
import com.example.your_personal_agenda_app.util.PersoanaJSONParser;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


public class MainActivity extends AppCompatActivity {

    private static final int NEW_PERSOANA_REQUEST_CODE = 100;
    private static final int NEW_MANCARE_REQUEST_CODE = 200;
    private static final int NEW_ACTIVITATE_REQUEST_CODE = 300;
    private static final int UPDATE_PERSOANA_REQUEST_CODE = 400;
    private static final int UPDATE_ACTIVITATE_REQUEST_CODE = 500;
    private static final int UPDATE_MANCARURI_REQUEST_CODE = 600;

    public static final String PERSOANE_URL = "https://jsonkeeper.com/b/1DWQ";
    public static int selectedPersoanaIndex = -1;
    public static int selectedMancareIndex = -1;
    public static int selectedActivitateIndex = -1;

    DrawerLayout drawerLayout;
    private FloatingActionButton fab_add_persoane;
    private FloatingActionButton fab_add_mancaruri;
    private FloatingActionButton fab_add_activitati;
    private FloatingActionButton fab_update_persoane;
    private FloatingActionButton fab_update_mancaruri;
    private FloatingActionButton fab_update_activitati;
    private NavigationView navigationView;
    private Fragment currentFragment;

    private List<Persoana> persoaneHTTP = new ArrayList<>();
    private List<PersoanaBD> persoane = new ArrayList<>();
    private List<Mancare> mancaruri = new ArrayList<>();
    private List<Activitate> activitati = new ArrayList<>();
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    private PersoanaBDService persoanaBDService;
    private MancareService mancareService;
    private ActivitateService activitateService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preluareDinBazaDeDate();
        getPersoaneFromHttp(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        configNavigation();
    }

    private void preluareDinBazaDeDate() {
        persoanaBDService = new PersoanaBDService(getApplicationContext());
        persoanaBDService.getAll(getPersoaneFromDbCallback());
        mancareService = new MancareService(getApplicationContext());
        mancareService.getAll(getMancaruriFromDbCallback());
        activitateService = new ActivitateService(getApplicationContext());
        activitateService.getAll(getActivatiFromDbCallback());
    }

    private void getPersoaneFromHttp(Bundle savedInstanceState) {
        Callable<String> asyncOperation = new HTTPManager(PERSOANE_URL);
        Callback<String> mainThreadOperation = receivePersoaneFromHTTP(savedInstanceState);
        asyncTaskRunner.executeAsync(asyncOperation, mainThreadOperation);
    }

    private Callback<String> receivePersoaneFromHTTP(final Bundle savedInstanceState) {
        return new Callback<String>() {
            @Override
            public void runResultOnUiThread(String result) {
                persoaneHTTP.addAll(PersoanaJSONParser.fromJson(result));
                combinareBDandJSON();
                setareIDPersHTTP();
                adaugareMancaruriActivitatiDupaCombinare();
                openDefaultFragment(savedInstanceState);
            }
        };
    }

    private void configNavigation() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private void initComponents() {
        fab_add_persoane=findViewById(R.id.fab_main_activity_add_persoane);
        fab_add_mancaruri=findViewById(R.id.fab_main_activity_add_mancaruri);
        fab_add_activitati=findViewById(R.id.fab_main_activity_add_activitati);
        fab_update_persoane=findViewById(R.id.fab_main_activity_update_persoane);
        fab_update_mancaruri=findViewById(R.id.fab_main_activity_update_mancaruri);
        fab_update_activitati=findViewById(R.id.fab_main_activity_update_activitati);
        navigationView=findViewById(R.id.nav_view);
        fab_add_persoane.setOnClickListener(addNewPersoanaEventListener());
        fab_add_mancaruri.setOnClickListener(addNewMancareEventListener());
        fab_add_activitati.setOnClickListener(addNewActivitateEventListener());
        fab_update_persoane.setOnClickListener(updatePersoanaEventListener());
        fab_update_mancaruri.setOnClickListener(updateMancareEventListener());
        fab_update_activitati.setOnClickListener(updateActivitatiEventListener());
        navigationView.setNavigationItemSelectedListener(addNavigationMenuItemSelectedEvent());
    }

    private void combinareBDandJSON()
    {
        for(Persoana persoanaHTTP:persoaneHTTP)
        {
            boolean check = true;
            for(PersoanaBD persoanaBD:persoane)
            {
                if(persoanaBD.getNumePersoana().equals( persoanaHTTP.getNumePersoana())
                        && persoanaBD.getVarstaPersoana() ==  persoanaHTTP.getVarstaPersoana()
                        && persoanaBD.getGenPersoana() ==  persoanaHTTP.getGenPersoana()
                        && persoanaBD.getGreutatePersoana() ==  persoanaHTTP.getGreutatePersoana()
                        && persoanaBD.getInaltimePersoana() == persoanaHTTP.getInaltimePersoana()
                        && persoanaBD.getDataPersoana().compareTo(persoanaHTTP.getDataPersoana())==0)
                {
                    check = false;
                    break;
                }
            }
            if (check)
            {
                PersoanaBD persDeInserat = new PersoanaBD(persoanaHTTP.getNumePersoana(), persoanaHTTP.getVarstaPersoana(), persoanaHTTP.getGenPersoana(), persoanaHTTP.getGreutatePersoana(), persoanaHTTP.getInaltimePersoana(), persoanaHTTP.getDataPersoana());
                persoanaBDService.insert(insertPersoanaIntoDbCallback(), persDeInserat);
            }
        }
    }

    private void setareIDPersHTTP()
    {
        for(Persoana persoanaHTTP:persoaneHTTP)
        {
            for(PersoanaBD persoanaBD:persoane)
            {
                if(persoanaBD.getNumePersoana().equals( persoanaHTTP.getNumePersoana())
                        && persoanaBD.getVarstaPersoana() ==  persoanaHTTP.getVarstaPersoana()
                        && persoanaBD.getGenPersoana() ==  persoanaHTTP.getGenPersoana()
                        && persoanaBD.getGreutatePersoana() ==  persoanaHTTP.getGreutatePersoana()
                        && persoanaBD.getInaltimePersoana() == persoanaHTTP.getInaltimePersoana()
                        && persoanaBD.getDataPersoana().compareTo(persoanaHTTP.getDataPersoana())==0)
                {
                    persoanaHTTP.setId((int)persoanaBD.getId());
                    break;
                }
            }
        }
    }

    private void adaugareMancaruriActivitatiDupaCombinare()
    {
        for(Persoana persoanaHTTP:persoaneHTTP)
        {
            for(PersoanaBD persoanaBD:persoane)
            {
                if(persoanaBD.getNumePersoana().equals( persoanaHTTP.getNumePersoana())
                        && persoanaBD.getVarstaPersoana() ==  persoanaHTTP.getVarstaPersoana()
                        && persoanaBD.getGenPersoana() ==  persoanaHTTP.getGenPersoana()
                        && persoanaBD.getGreutatePersoana() ==  persoanaHTTP.getGreutatePersoana()
                        && persoanaBD.getInaltimePersoana() == persoanaHTTP.getInaltimePersoana()
                        && persoanaBD.getDataPersoana().compareTo(persoanaHTTP.getDataPersoana())==0)
                {
                    for (Mancare mancare:persoanaHTTP.getListaMancaruriPersoana())
                    {
                        mancare.setIdPersoanaMancare(persoanaHTTP.getId());
                        boolean exista = false;
                        for(Mancare mancareBD:mancaruri)
                        {

                            if (mancareBD.getDescriereMancare().equals(mancare.getDescriereMancare())
                                    && mancareBD.getFelMancare().toString().equals(mancare.getFelMancare().toString())
                                    && mancareBD.getGramajMancare() == mancare.getGramajMancare()
                                    && mancareBD.getProteineMancare() == mancare.getProteineMancare()
                                    && mancareBD.getCarbohidratiMancare() == mancare.getCarbohidratiMancare()
                                    && mancareBD.getGrasimiMancare() == mancare.getGrasimiMancare()
                                    && mancareBD.getCaloriiMancare() == mancare.getCaloriiMancare()
                                    && mancareBD.getIdPersoanaMancare() == mancare.getIdPersoanaMancare())
                            {
                                exista = true;
                                break;
                            }
                        }
                        if(!exista)
                        {
                            mancareService.insert(insertMancareIntoDbCallback(), mancare);
                        }
                    }
                    for (Activitate activitate:persoanaHTTP.getListaActivitatiPersoana())
                    {
                        activitate.setIdPersoanaActivitate(persoanaHTTP.getId());
                        boolean exista = false;
                        for(Activitate activitateBD:activitati)
                        {

                            if (activitateBD.getTipActivitate().equals(activitate.getTipActivitate())
                                    && activitateBD.getOreActivitate() == activitate.getOreActivitate()
                                    && activitateBD.getMinuteActivitate() == activitate.getMinuteActivitate()
                                    && activitateBD.getCaloriiArseActivitate() == activitate.getCaloriiArseActivitate()
                                    && activitateBD.getIdPersoanaActivitate() == activitate.getIdPersoanaActivitate())
                            {
                                exista = true;
                                break;
                            }
                        }
                        if(!exista)
                        {
                            activitateService.insert(insertActivitateIntoDbCallback(), activitate);
                        }
                    }
                    break;
                }
            }
        }
    }

    private Callback<List<PersoanaBD>> getPersoaneFromDbCallback() {
        return new Callback<List<PersoanaBD>>() {
            @Override
            public void runResultOnUiThread(List<PersoanaBD> result) {
                if (result != null) {
                    persoane.clear();
                    persoane.addAll(result);
                }
            }
        };
    }

    private Callback<PersoanaBD> insertPersoanaIntoDbCallback() {
        return new Callback<PersoanaBD>() {
            @Override
            public void runResultOnUiThread(PersoanaBD result) {
                if (result != null) {
                    persoane.add(result);
                    currentFragment = FragmentPersoane.newInstance((ArrayList<PersoanaBD>) persoane,(ArrayList<Mancare>) mancaruri,(ArrayList<Activitate>) activitati);
                    openFragment();
                }
            }
        };
    }

    private Callback<PersoanaBD> updatePersoanaToDbCallback() {
        return new Callback<PersoanaBD>() {
            @Override
            public void runResultOnUiThread(PersoanaBD result) {
                if (result != null) {
                    for (PersoanaBD persoana:persoane) {
                        if (persoana.getId() == result.getId()) {
                            persoana.setNumePersoana(result.getNumePersoana());
                            persoana.setVarstaPersoana(result.getVarstaPersoana());
                            persoana.setGenPersoana(result.getGenPersoana());
                            persoana.setGreutatePersoana(result.getGreutatePersoana());
                            persoana.setInaltimePersoana(result.getInaltimePersoana());
                            persoana.setDataPersoana(result.getDataPersoana());
                            break;
                        }
                    }
                    currentFragment = FragmentPersoane.newInstance((ArrayList<PersoanaBD>) persoane,(ArrayList<Mancare>) mancaruri,(ArrayList<Activitate>) activitati);
                    openFragment();
                }
            }
        };
    }


    private Callback<List<Mancare>> getMancaruriFromDbCallback() {
        return new Callback<List<Mancare>>() {
            @Override
            public void runResultOnUiThread(List<Mancare> result) {
                if (result != null) {
                    mancaruri.clear();
                    mancaruri.addAll(result);
                }
            }
        };
    }

    private Callback<Mancare> insertMancareIntoDbCallback() {
        return new Callback<Mancare>() {
            @Override
            public void runResultOnUiThread(Mancare result) {
                if (result != null) {
                    mancaruri.add(result);
                    if(selectedPersoanaIndex != -1)
                    {
                        currentFragment = FragmentMancaruri.newInstance((ArrayList<Mancare>)mancaruri, persoane.get(selectedPersoanaIndex).getId());
                        openFragment();
                    }
                }
            }
        };
    }

    private Callback<Mancare> updateMancareToDbCallback() {
        return new Callback<Mancare>() {
            @Override
            public void runResultOnUiThread(Mancare result) {
                if (result != null) {
                    for (Mancare mancare:mancaruri) {
                        if (mancare.getId() == result.getId()) {
                            mancare.setDescriereMancare(result.getDescriereMancare());
                            mancare.setFelMancare(result.getFelMancare());
                            mancare.setGramajMancare(result.getGramajMancare());
                            mancare.setProteineMancare(result.getProteineMancare());
                            mancare.setCarbohidratiMancare(result.getCarbohidratiMancare());
                            mancare.setGrasimiMancare(result.getGrasimiMancare());
                            mancare.setCaloriiMancare(result.getCaloriiMancare());

                            break;
                        }
                    }
                    currentFragment = FragmentMancaruri.newInstance((ArrayList<Mancare>)mancaruri, persoane.get(selectedPersoanaIndex).getId());
                    openFragment();
                }
            }
        };
    }

    private Callback<List<Activitate>> getActivatiFromDbCallback() {
        return new Callback<List<Activitate>>() {
            @Override
            public void runResultOnUiThread(List<Activitate> result) {
                if (result != null) {
                    activitati.clear();
                    activitati.addAll(result);
                }
            }
        };
    }

    private Callback<Activitate> insertActivitateIntoDbCallback() {
        return new Callback<Activitate>() {
            @Override
            public void runResultOnUiThread(Activitate result) {
                if (result != null) {
                    activitati.add(result);
                    if (selectedPersoanaIndex != -1)
                    {
                    currentFragment = FragmentActivitati.newInstance((ArrayList<Activitate>)activitati, persoane.get(selectedPersoanaIndex).getId());
                    openFragment();
                    }
                }
            }
        };
    }

    private Callback<Activitate> updateActivitateToDbCallback() {
        return new Callback<Activitate>() {
            @Override
            public void runResultOnUiThread(Activitate result) {
                if (result != null) {
                    for (Activitate activitate:activitati) {
                        if (activitate.getId() == result.getId()) {
                            activitate.setTipActivitate(result.getTipActivitate());
                            activitate.setOreActivitate(result.getOreActivitate());
                            activitate.setMinuteActivitate(result.getMinuteActivitate());
                            activitate.setCaloriiArseActivitate(result.getCaloriiArseActivitate());
                            break;
                        }
                    }
                    currentFragment = FragmentActivitati.newInstance((ArrayList<Activitate>)activitati, persoane.get(selectedPersoanaIndex).getId());
                    openFragment();
                }
            }
        };
    }

    private View.OnClickListener addNewPersoanaEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(currentFragment instanceof FragmentPersoane))
                {
                    Toast.makeText(getApplicationContext(), R.string.add_person_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), AdaugarePersoaneActivity.class);
                    startActivityForResult(intent,NEW_PERSOANA_REQUEST_CODE);
                }

            }
        };
    }

    private View.OnClickListener updatePersoanaEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedPersoanaIndex == -1)
                {
                    Toast.makeText(getApplicationContext(), R.string.update_persoana_not_selected_message,Toast.LENGTH_LONG).show();
                }
                else if(!(currentFragment instanceof FragmentPersoane))
                {
                    Toast.makeText(getApplicationContext(), R.string.update_persoana_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), AdaugarePersoaneActivity.class);
                    intent.putExtra(AdaugarePersoaneActivity.NEW_PERSOANA_KEY, persoane.get(selectedPersoanaIndex));
                    startActivityForResult(intent, UPDATE_PERSOANA_REQUEST_CODE);
                }
            }
        };
    }

    private View.OnClickListener addNewMancareEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPersoanaIndex == -1)
                {
                    Toast.makeText(getApplicationContext(), R.string.add_mancare_person_not_selected_message, Toast.LENGTH_LONG).show();
                }
                else if( !(currentFragment instanceof FragmentMancaruri))
                {
                    Toast.makeText(getApplicationContext(), R.string.add_mancare_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), AdaugareMancaruriActivity.class);
                    startActivityForResult(intent,NEW_MANCARE_REQUEST_CODE);
                }
            }
        };
    }

    private View.OnClickListener updateMancareEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedMancareIndex == -1)
                {
                    Toast.makeText(getApplicationContext(), R.string.update_mancare_not_selected_message,Toast.LENGTH_LONG).show();
                }
                else if (!(currentFragment instanceof FragmentMancaruri))
                {
                    Toast.makeText(getApplicationContext(), R.string.update_mancare_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    ArrayList<Mancare> listaMancaruri = new ArrayList<Mancare>();
                    for(PersoanaBD persoana:persoane)
                    {
                        if(persoana.getId()==persoane.get(selectedPersoanaIndex).getId())
                        {
                            for(Mancare mancare:mancaruri)
                            {
                                if(mancare.getIdPersoanaMancare() == persoana.getId())
                                {
                                    listaMancaruri.add(mancare);
                                }
                            }
                            break;
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), AdaugareMancaruriActivity.class);
                    intent.putExtra(AdaugareMancaruriActivity.NEW_MANCARE_KEY, listaMancaruri.get(selectedMancareIndex));
                    startActivityForResult(intent, UPDATE_MANCARURI_REQUEST_CODE);
                }
            }
        };
    }

    private View.OnClickListener addNewActivitateEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedPersoanaIndex == -1)
                {
                    Toast.makeText(getApplicationContext(), R.string.add_activitate_person_not_selected_message, Toast.LENGTH_LONG).show();
                }
                else if (!(currentFragment instanceof FragmentActivitati))
                {
                    Toast.makeText(getApplicationContext(), R.string.add_activitate_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    Intent intent = new Intent(getApplicationContext(), AdaugareActivitatiActivity.class);
                    startActivityForResult(intent,NEW_ACTIVITATE_REQUEST_CODE);
                }
            }
        };
    }

    private View.OnClickListener updateActivitatiEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedActivitateIndex == -1)
                {
                    Toast.makeText(getApplicationContext(), R.string.update_activitate_not_selected_message,Toast.LENGTH_LONG).show();
                }
                else if (!(currentFragment instanceof FragmentActivitati))
                {
                    Toast.makeText(getApplicationContext(), R.string.update_activitate_wrong_screen_message,Toast.LENGTH_LONG).show();
                }
                else
                {
                    ArrayList<Activitate> listaActivitati = new ArrayList<Activitate>();
                    for(PersoanaBD persoana:persoane)
                    {
                        if(persoana.getId()==persoane.get(selectedPersoanaIndex).getId())
                        {
                            for(Activitate activitate:activitati)
                            {
                                if(activitate.getIdPersoanaActivitate() == persoana.getId())
                                {
                                    listaActivitati.add(activitate);
                                }
                            }
                            break;
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), AdaugareActivitatiActivity.class);
                    intent.putExtra(AdaugareActivitatiActivity.NEW_ACTIVITATE_KEY, listaActivitati.get(selectedActivitateIndex));
                    startActivityForResult(intent, UPDATE_ACTIVITATE_REQUEST_CODE);
                }
            }
        };
    }

    private NavigationView.OnNavigationItemSelectedListener addNavigationMenuItemSelectedEvent() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.main_nav_persoane) {
                    currentFragment = FragmentPersoane.newInstance((ArrayList<PersoanaBD>) persoane,(ArrayList<Mancare>) mancaruri,(ArrayList<Activitate>) activitati);
                    Toast.makeText(getApplicationContext(), getString(R.string.main_activity_option,item.getTitle()), Toast.LENGTH_LONG).show();
                    openFragment();
                } else if (item.getItemId() == R.id.main_nav_mancaruri) {
                    if(selectedPersoanaIndex == -1)
                    {
                        Toast.makeText(getApplicationContext(), R.string.mancaruri_person_not_selected_message, Toast.LENGTH_LONG).show();
                        //openFragment();
                    }
                    else{
                    currentFragment = FragmentMancaruri.newInstance((ArrayList<Mancare>)mancaruri, persoane.get(selectedPersoanaIndex).getId());
                    Toast.makeText(getApplicationContext(), getString(R.string.main_activity_option,item.getTitle()), Toast.LENGTH_LONG).show();
                    openFragment();}
                } else if(item.getItemId() == R.id.main_nav_activitati)
                {
                    if(selectedPersoanaIndex == -1)
                    {
                        Toast.makeText(getApplicationContext(), R.string.activitati_person_not_selected_message, Toast.LENGTH_LONG).show();
                        //openFragment();
                    }
                    else{
                        currentFragment = FragmentActivitati.newInstance((ArrayList<Activitate>)activitati, persoane.get(selectedPersoanaIndex).getId());
                        Toast.makeText(getApplicationContext(), getString(R.string.main_activity_option,item.getTitle()), Toast.LENGTH_LONG).show();
                        openFragment();}
                } else if (item.getItemId() == R.id.main_nav_rapoarte) {
                    Intent intent = new Intent(getApplicationContext(), RapoarteActivity.class);
                    intent.putExtra(RapoarteActivity.RAPOARTE_PERSOANE_KEY,(ArrayList<PersoanaBD>)persoane);
                    intent.putExtra(RapoarteActivity.RAPOARTE_MANCARURI_KEY,(ArrayList<Mancare>)mancaruri);
                    intent.putExtra(RapoarteActivity.RAPOARTE_ACTIVTTATI_KEY,(ArrayList<Activitate>)activitati);
                    startActivity(intent);
                } else if(item.getItemId() == R.id.main_nav_tutorial)
                {
                    Intent intent = new Intent(getApplicationContext(), TutorialActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.main_nav_detalii) {
                    Intent intent = new Intent(getApplicationContext(), InfoActivity.class);
                    startActivity(intent);
                } else if (item.getItemId() == R.id.main_nav_firebase)
                {
                    Intent intent = new Intent(getApplicationContext(), FirebaseActivity.class);
                    intent.putExtra(FirebaseActivity.FIREBASE_KEY,(ArrayList<PersoanaBD>)persoane);
                    startActivity(intent);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PERSOANA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            PersoanaBD persoana = (PersoanaBD) data.getSerializableExtra(AdaugarePersoaneActivity.NEW_PERSOANA_KEY);
            persoanaBDService.insert(insertPersoanaIntoDbCallback(),persoana);
            Toast.makeText(getApplicationContext(), R.string.new_person_added_message, Toast.LENGTH_LONG).show();

        }
        else if(requestCode == UPDATE_PERSOANA_REQUEST_CODE && resultCode == RESULT_OK && data != null )
        {
            PersoanaBD persoana = (PersoanaBD) data.getSerializableExtra(AdaugarePersoaneActivity.NEW_PERSOANA_KEY);
            if(persoana != null)
            {
                persoanaBDService.update(updatePersoanaToDbCallback(),persoana);
                Toast.makeText(getApplicationContext(),getString(R.string.update_persoana_success_message,persoana.getNumePersoana()),Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == NEW_MANCARE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Mancare mancare = (Mancare) data.getSerializableExtra(AdaugareMancaruriActivity.NEW_MANCARE_KEY);
            mancare.setIdPersoanaMancare((int)persoane.get(selectedPersoanaIndex).getId());
            mancareService.insert(insertMancareIntoDbCallback(), mancare);
            Toast.makeText(getApplicationContext(), getString(R.string.add_mancare_persoana_message, persoane.get(selectedPersoanaIndex).getNumePersoana()),
                    Toast.LENGTH_LONG).show();

        }
        else if (requestCode == UPDATE_MANCARURI_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Mancare mancare = (Mancare) data.getSerializableExtra(AdaugareMancaruriActivity.NEW_MANCARE_KEY);
            if( mancare != null)
            {
                mancareService.update(updateMancareToDbCallback(),mancare);
                Toast.makeText(getApplicationContext(),getString(R.string.update_mancare_success_message,mancare.getDescriereMancare()),Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == NEW_ACTIVITATE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Activitate activitate = (Activitate) data.getSerializableExtra(AdaugareActivitatiActivity.NEW_ACTIVITATE_KEY);
            activitate.setIdPersoanaActivitate((int)persoane.get(selectedPersoanaIndex).getId());
            activitateService.insert(insertActivitateIntoDbCallback(), activitate);
            Toast.makeText(getApplicationContext(), getString(R.string.add_activitate_persoana_message, persoane.get(selectedPersoanaIndex).getNumePersoana()),
                    Toast.LENGTH_LONG).show();
        }
        else if (requestCode == UPDATE_ACTIVITATE_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            Activitate activitate = (Activitate) data.getSerializableExtra(AdaugareActivitatiActivity.NEW_ACTIVITATE_KEY);
            if( activitate != null)
            {
                activitateService.update(updateActivitateToDbCallback(),activitate);
                Toast.makeText(getApplicationContext(),getString(R.string.update_activitate_success_message,activitate.getTipActivitate()),Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openDefaultFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            currentFragment = FragmentPersoane.newInstance((ArrayList<PersoanaBD>) persoane,(ArrayList<Mancare>) mancaruri,(ArrayList<Activitate>) activitati);
            openFragment();
            navigationView.setCheckedItem(R.id.main_nav_persoane);
        }
    }

    public void openFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_main_activity_lista, currentFragment)
                .commit();
    }

}