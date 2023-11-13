package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.your_personal_agenda_app.database.model.PersoanaBD;
import com.example.your_personal_agenda_app.util.DateConverter;
import com.example.your_personal_agenda_app.util.GenPersoana;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class AdaugarePersoaneActivity extends AppCompatActivity {

    public static final String SHARED_PREF_PERSOANE_FILE_NAME = "persoanaSharedPref";
    public static final String NEW_PERSOANA_KEY = "new_persoana_key";

    public static final String CHKBX_STATE = "chkbxState";
    public static final String NUME_PERSOANA = "numePersoana";
    public static final String VARSTA_PERSOANA = "varstaPersoana";
    public static final String GEN_PERSOANA = "genPersoana";
    public static final String GREUTATE_PERSOANA = "greutatePersoana";
    public static final String INALTIME_PERSOANA = "inaltimePersoana";
    public static final String DATA_PERSOANA = "dataPersoana";

    private TextInputEditText tietNumePersoana;
    private TextInputEditText tietVarstaPersoana;
    private RadioGroup rgGenPersoana;
    private RadioButton rbGenFeminin;
    private TextInputEditText tietGreutatePersoana;
    private TextInputEditText tietInaltimePersoana;
    private Button btnDataPersoana;
    private TextView tvDataPersoana;
    private CheckBox chkbxPreferintePersoana;
    private Button btnSalvarePersoana;
    private DatePickerDialog.OnDateSetListener dataPersoanaListener;
    private static DateConverter converter = new DateConverter();
    private SharedPreferences preferences;

    private Intent intent;
    private PersoanaBD persoanaUPDATE = null;
    private boolean UPDATE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_persoane);
        initComponentsAddPersoana();
        intent = getIntent();
        if (intent.hasExtra(NEW_PERSOANA_KEY))
        {
            persoanaUPDATE = (PersoanaBD) intent.getSerializableExtra(NEW_PERSOANA_KEY);
            buildViewsFromPersoana(persoanaUPDATE);
            UPDATE = true;
        }
        else
        {
            getPersoanaDetailsFromSharedPreference();
        }
    }

    private void initComponentsAddPersoana() {
        tietNumePersoana = findViewById(R.id.tiet_add_persoane_nume);
        tietVarstaPersoana = findViewById(R.id.tiet_add_persoane_varsta);
        rgGenPersoana = findViewById(R.id.rg_add_persoane_gen);
        rbGenFeminin = findViewById(R.id.rb_add_persoane_feminin);
        tietGreutatePersoana = findViewById(R.id.tiet_add_persoane_greutate);
        tietInaltimePersoana = findViewById(R.id.tiet_add_persoane_inaltime);
        btnDataPersoana = findViewById(R.id.btn_add_persoane_data);
        tvDataPersoana = findViewById(R.id.tv_add_persoane_data);
        chkbxPreferintePersoana = findViewById(R.id.chkbx_add_persoane_preferinta);
        btnSalvarePersoana = findViewById(R.id.btn_add_persoane_salvare);
        btnSalvarePersoana.setOnClickListener(salvarePersoaneEventListener());
        btnDataPersoana.setOnClickListener(salvareDataPersoanaEventListener());
        dataPersoanaListener = getDataPersoanaListener();

        preferences = getSharedPreferences(SHARED_PREF_PERSOANE_FILE_NAME, MODE_PRIVATE);
    }

    private DatePickerDialog.OnDateSetListener getDataPersoanaListener() {
        return new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String dataPersoana = getString(R.string.add_persoane_data_pick_choice,dayOfMonth,month+1,year);
                tvDataPersoana.setText(dataPersoana);
            }
        };
    }

    private View.OnClickListener salvarePersoaneEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    PersoanaBD persoana = createPersoanaFromView();
                    if(UPDATE)
                    {
                        persoana.setId(persoanaUPDATE.getId());
                    }
                    intent.putExtra(NEW_PERSOANA_KEY,persoana);
                    setResult(RESULT_OK, intent);
                    if(chkbxPreferintePersoana.isChecked())
                    {
                        savePersoanaDetailsToSharedPreference();
                    }
                    else if(!UPDATE)
                    {
                        saveChkBxStateToSharedPreference();
                    }
                    finish();
                }
            }
        };
    }

    private PersoanaBD createPersoanaFromView() {
        String numePersoana = tietNumePersoana.getText().toString();
        int varstaPersoana = Integer.parseInt(tietVarstaPersoana.getText().toString());
        GenPersoana genPersoana = GenPersoana.MASCULIN;
        if (rgGenPersoana.getCheckedRadioButtonId() == R.id.rb_add_persoane_feminin) {
            genPersoana = GenPersoana.FEMININ;
        }
        double greutatePersoana = Double.parseDouble(tietGreutatePersoana.getText().toString());
        double inaltimePersoana = Double.parseDouble(tietInaltimePersoana.getText().toString());
        Date dataPersoana = converter.fromString(tvDataPersoana.getText().toString());
        return new PersoanaBD(numePersoana,varstaPersoana,genPersoana,greutatePersoana,inaltimePersoana,dataPersoana);
    }

    private void buildViewsFromPersoana(PersoanaBD persoana)
    {
        if(persoana == null)
        {
            return;
        }
        tietNumePersoana.setText(persoana.getNumePersoana());
        tietVarstaPersoana.setText(String.valueOf(persoana.getVarstaPersoana()));
        if(String.valueOf(persoana.getGenPersoana()).equals("FEMININ"))
        {
            rbGenFeminin.setChecked(true);
        }
        tietGreutatePersoana.setText(String.valueOf(persoana.getGreutatePersoana()));
        tietInaltimePersoana.setText(String.valueOf(persoana.getInaltimePersoana()));
        tvDataPersoana.setText(converter.toString(persoana.getDataPersoana()));
    }

    private void getPersoanaDetailsFromSharedPreference() {
        int chkbxState = preferences.getInt(CHKBX_STATE, 0);
        if(chkbxState != 0)
        {
        String numePers = preferences.getString(NUME_PERSOANA, "");
        int varstaPers = preferences.getInt(VARSTA_PERSOANA,1);
        int genPersoanaID = preferences.getInt(GEN_PERSOANA, R.id.rb_add_persoane_masculin);
        float greutatePers = preferences.getFloat(GREUTATE_PERSOANA, 0.1f);
        float inaltimePers = preferences.getFloat(INALTIME_PERSOANA, 0.1f);
        String dataPers = preferences.getString(DATA_PERSOANA,getString(R.string.tv_add_persoane_ziua_luna_anul));
        chkbxPreferintePersoana.setChecked(true);
        tietNumePersoana.setText(numePers);
        tietVarstaPersoana.setText(String.valueOf(varstaPers));
        rgGenPersoana.check(genPersoanaID);
        tietGreutatePersoana.setText(String.valueOf(greutatePers));
        tietInaltimePersoana.setText(String.valueOf(inaltimePers));
        tvDataPersoana.setText(dataPers);
        }
    }

    private void savePersoanaDetailsToSharedPreference() {
        int chkbxState = 1;
        String numePers = tietNumePersoana.getText().toString();
        int varstaPers = Integer.parseInt(tietVarstaPersoana.getText().toString());
        int genPersoanaID = rgGenPersoana.getCheckedRadioButtonId();
        float greutatePers = Float.parseFloat(tietGreutatePersoana.getText().toString());
        float inaltimePers = Float.parseFloat(tietInaltimePersoana.getText().toString());
        String dataPers = tvDataPersoana.getText().toString();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CHKBX_STATE,chkbxState);
        editor.putString(NUME_PERSOANA, numePers);
        editor.putInt(VARSTA_PERSOANA, varstaPers);
        editor.putInt(GEN_PERSOANA, genPersoanaID);
        editor.putFloat(GREUTATE_PERSOANA,greutatePers);
        editor.putFloat(INALTIME_PERSOANA, inaltimePers);
        editor.putString(DATA_PERSOANA, dataPers);
        editor.apply();
    }

    private void saveChkBxStateToSharedPreference(){
        int chkbxState = 0;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CHKBX_STATE,chkbxState);
        editor.apply();
    }

    private boolean validate() {
        if (tietNumePersoana.getText() == null || tietNumePersoana.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.add_persoane_invalid_name_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (tietVarstaPersoana.getText() == null || tietVarstaPersoana.getText().toString().trim().length() == 0 || tietVarstaPersoana.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                || tietVarstaPersoana.getText().toString().trim().equals(getString(R.string.test_dot_sign)) || tietVarstaPersoana.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                || Integer.parseInt(tietVarstaPersoana.getText().toString()) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.add_persoane_invalid_age_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (tietGreutatePersoana.getText() == null || tietGreutatePersoana.getText().toString().trim().length() == 0 || tietGreutatePersoana.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                || tietGreutatePersoana.getText().toString().trim().equals(getString(R.string.test_dot_sign)) || tietGreutatePersoana.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                || Double.parseDouble(tietGreutatePersoana.getText().toString().trim()) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.add_persoane_invalid_weight_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (tietInaltimePersoana.getText() == null || tietInaltimePersoana.getText().toString().trim().length() == 0 || tietInaltimePersoana.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                || tietInaltimePersoana.getText().toString().trim().equals(getString(R.string.test_dot_sign)) || tietInaltimePersoana.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                || Double.parseDouble(tietInaltimePersoana.getText().toString().trim()) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.add_persoane_invalid_height_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (tvDataPersoana.getText().toString().equals(getString(R.string.tv_add_persoane_ziua_luna_anul))) {
            Toast.makeText(getApplicationContext(), R.string.add_persoane_invalid_date_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private View.OnClickListener salvareDataPersoanaEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Calendar cal = Calendar.getInstance();
               int an = cal.get(Calendar.YEAR);
               int luna = cal.get(Calendar.MONTH);
               int zi = cal.get(Calendar.DAY_OF_MONTH);
               DatePickerDialog dialog = new DatePickerDialog(AdaugarePersoaneActivity.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,dataPersoanaListener,an,luna,zi);
               dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
               dialog.show();
            }
        };
    }
}