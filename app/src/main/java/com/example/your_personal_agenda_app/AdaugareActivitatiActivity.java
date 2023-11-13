package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.your_personal_agenda_app.database.model.Activitate;
import com.google.android.material.textfield.TextInputEditText;

public class AdaugareActivitatiActivity extends AppCompatActivity {

    public static final String SHARED_PREF_ACTIVITATI_FILE_NAME = "activitatiSharedPref";
    public static final String NEW_ACTIVITATE_KEY = "new_activitate_key";

    public static final String CHKBX_STATE = "chkbxState";
    public static final String TIP_ACTITATE = "tipActivitate";
    public static final String ORE_ACTIVITATE = "oreActivitate";
    public static final String MINUTE_ACTIVITATE = "minuteActivitate";
    public static final String CALORII_ARSE_ACTIVITATE = "caloriiArseActivitate";

    private TextInputEditText tietTipActivitate;
    private EditText etOreActivitate;
    private EditText etMinuteActivitate;
    private TextInputEditText tietCaloriiArseActivitate;
    private CheckBox chkbxPreferinteActivitati;
    private Button btnSalvareActivitate;
    private SharedPreferences preferences;

    private Intent intent;
    private Activitate activitateUPDATE = null;
    private boolean UPDATE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_activitati);
        initComponentsAddActivitate();
        intent = getIntent();
        if (intent.hasExtra(NEW_ACTIVITATE_KEY))
        {
            activitateUPDATE = (Activitate) intent.getSerializableExtra(NEW_ACTIVITATE_KEY);
            buildViewsFromActivitate(activitateUPDATE);
            UPDATE = true;
        }
        else
        {
            getActivitateDetailsFromSharedPreference();
        }
    }

    private void initComponentsAddActivitate() {
        tietTipActivitate = findViewById(R.id.tiet_add_activitati_tip);
        etOreActivitate = findViewById(R.id.et_add_activitati_durata_ore);
        etMinuteActivitate = findViewById(R.id.et_add_activitati_durata_minute);
        tietCaloriiArseActivitate = findViewById(R.id.tiet_add_activitati_calorii_arse);
        chkbxPreferinteActivitati = findViewById(R.id.chkbx_add_activitati_preferinta);
        btnSalvareActivitate = findViewById(R.id.btn_add_activitati_salvare);
        btnSalvareActivitate.setOnClickListener(salvareActivitateEventListener());

        preferences = getSharedPreferences(SHARED_PREF_ACTIVITATI_FILE_NAME, MODE_PRIVATE);
    }

    private View.OnClickListener salvareActivitateEventListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()) {
                    Activitate activitate = createActivitateFromView();
                    if (UPDATE)
                    {
                        activitate.setId(activitateUPDATE.getId());
                        activitate.setIdPersoanaActivitate(activitateUPDATE.getIdPersoanaActivitate());
                    }
                    intent.putExtra(NEW_ACTIVITATE_KEY,activitate);
                    setResult(RESULT_OK, intent);
                    if(chkbxPreferinteActivitati.isChecked())
                    {
                        saveActivitateDetailsToSharedPreference();
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

    private Activitate createActivitateFromView() {
        String tipActivitate = tietTipActivitate.getText().toString();
        int oreActivitate = 0;
        int minuteActivitate = 0;
        if(etOreActivitate.getText() != null && etOreActivitate.getText().toString().trim().length() != 0
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign)))
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign)))
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign)))
                && (etOreActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etOreActivitate.getText().toString().trim()) > 0))
        {
            oreActivitate = Integer.parseInt(etOreActivitate.getText().toString());
        }
        if(etMinuteActivitate.getText() != null && etMinuteActivitate.getText().toString().trim().length() != 0
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign)))
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign)))
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign)))
                && (etMinuteActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etMinuteActivitate.getText().toString().trim()) > 0))
        {
            minuteActivitate = Integer.parseInt(etMinuteActivitate.getText().toString());
        }
        double caloriiArseActivitate = Double.parseDouble(tietCaloriiArseActivitate.getText().toString());
        return new Activitate(tipActivitate,oreActivitate,minuteActivitate,caloriiArseActivitate,MainActivity.selectedPersoanaIndex);
    }

    private void buildViewsFromActivitate(Activitate activitate)
    {
        tietTipActivitate.setText(activitate.getTipActivitate());
        etOreActivitate.setText(String.valueOf(activitate.getOreActivitate()));
        etMinuteActivitate.setText(String.valueOf(activitate.getMinuteActivitate()));
        tietCaloriiArseActivitate.setText(String.valueOf(activitate.getCaloriiArseActivitate()));
    }

    private void getActivitateDetailsFromSharedPreference() {
        int chkbxState = preferences.getInt(CHKBX_STATE, 0);
        if(chkbxState != 0)
        {
            String tipActivitate = preferences.getString(TIP_ACTITATE, "");
            int oreActivitate = preferences.getInt(ORE_ACTIVITATE,0);
            int minuteActivitate = preferences.getInt(MINUTE_ACTIVITATE,0);
            float caloriiArseActivitate = preferences.getFloat(CALORII_ARSE_ACTIVITATE, 0.1f);
            chkbxPreferinteActivitati.setChecked(true);
            tietTipActivitate.setText(tipActivitate);
            etOreActivitate.setText(String.valueOf(oreActivitate));
            etMinuteActivitate.setText(String.valueOf(minuteActivitate));
            tietCaloriiArseActivitate.setText(String.valueOf(caloriiArseActivitate));
        }
    }

    private void saveActivitateDetailsToSharedPreference() {
        int chkbxState = 1;
        String tipActivitate = tietTipActivitate.getText().toString();
        int oreActivitate = 0;
        int minuteActivitate = 0;
        if(etOreActivitate.getText() != null && etOreActivitate.getText().toString().trim().length() != 0
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign)))
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign)))
                && !(etOreActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign)))
                && (etOreActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etOreActivitate.getText().toString().trim()) > 0))
        {
            oreActivitate = Integer.parseInt(etOreActivitate.getText().toString());
        }
        if(etMinuteActivitate.getText() != null && etMinuteActivitate.getText().toString().trim().length() != 0
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign)))
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign)))
                && !(etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign)))
                && (etMinuteActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etMinuteActivitate.getText().toString().trim()) > 0))
        {
            minuteActivitate = Integer.parseInt(etMinuteActivitate.getText().toString());
        }
        float caloriiArseActivitate = Float.parseFloat(tietCaloriiArseActivitate.getText().toString());
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CHKBX_STATE,chkbxState);
        editor.putString(TIP_ACTITATE, tipActivitate);
        editor.putInt(ORE_ACTIVITATE,oreActivitate);
        editor.putInt(MINUTE_ACTIVITATE,minuteActivitate);
        editor.putFloat(CALORII_ARSE_ACTIVITATE, caloriiArseActivitate);
        editor.apply();
    }

    private void saveChkBxStateToSharedPreference(){
        int chkbxState = 0;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(CHKBX_STATE,chkbxState);
        editor.apply();
    }

    private boolean validate() {
        if (tietTipActivitate.getText() == null || tietTipActivitate.getText().toString().trim().length() == 0) {
            Toast.makeText(getApplicationContext(), R.string.add_activitate_invalid_type_error, Toast.LENGTH_LONG).show();
            return false;
        }
        if (
            //===ore===///
                (etOreActivitate.getText() == null || etOreActivitate.getText().toString().trim().length() == 0
                        || etOreActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                        || etOreActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign))
                        || etOreActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                        || (etOreActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etOreActivitate.getText().toString().trim()) <= 0))

                        && //===minute===//
                        (etMinuteActivitate.getText() == null || etMinuteActivitate.getText().toString().trim().length() == 0
                                || etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                                || etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign))
                                || etMinuteActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                                || (etMinuteActivitate.getText().toString().trim().length() != 0 && Integer.parseInt(etMinuteActivitate.getText().toString().trim()) <= 0))
        )
        {
            Toast.makeText(getApplicationContext(), R.string.add_activitate_invalid_duration_error, Toast.LENGTH_LONG).show();
            return false;
        }

        if (tietCaloriiArseActivitate.getText() == null || tietCaloriiArseActivitate.getText().toString().trim().length() == 0 || tietCaloriiArseActivitate.getText().toString().trim().equals(getString(R.string.test_minus_sign))
                || tietCaloriiArseActivitate.getText().toString().trim().equals(getString(R.string.test_dot_sign)) || tietCaloriiArseActivitate.getText().toString().trim().equals(getString(R.string.test_comma_sign))
                || Double.parseDouble(tietCaloriiArseActivitate.getText().toString().trim()) <= 0) {
            Toast.makeText(getApplicationContext(), R.string.add_activitate_invalid_burned_calories_error, Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}