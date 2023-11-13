package com.example.your_personal_agenda_app.firebase;

import androidx.annotation.NonNull;

import com.example.your_personal_agenda_app.AsyncTask.Callback;
import com.example.your_personal_agenda_app.util.PersoanaFirebase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {
    public static final String PERSOANE_TABLE_NAME = "persoane";
    private DatabaseReference database;

    private static FirebaseService firebaseService;

    private FirebaseService() {
        database = FirebaseDatabase.getInstance().getReference(PERSOANE_TABLE_NAME);
    }

    public static FirebaseService getInstance() {
        if (firebaseService == null) {
            synchronized (FirebaseService.class) {
                if (firebaseService == null) {
                    firebaseService = new FirebaseService();
                }
            }
        }
        return firebaseService;
    }

    public void upsert(PersoanaFirebase persoanaFirebase) {
        if (persoanaFirebase == null) {
            return;
        }
        if (persoanaFirebase.getId().equals("0") || persoanaFirebase.getId().trim().isEmpty()) {
            String id = database.push().getKey();
            persoanaFirebase.setId(id);
        }
        database.child(persoanaFirebase.getId()).setValue(persoanaFirebase);
    }

    public void delete(final PersoanaFirebase persoanaFirebase) {
        if (persoanaFirebase == null || persoanaFirebase.getId() == null || persoanaFirebase.getId().trim().isEmpty()) {
            return;
        }
        database.child(persoanaFirebase.getId()).removeValue();
    }
    public void deleteAll()
    {
        database.removeValue();
    }

    public void attachDataChangeEventListener(final Callback<List<PersoanaFirebase>> callback) {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PersoanaFirebase> persoaneFirebase = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    PersoanaFirebase persoanaFirebase = data.getValue(PersoanaFirebase.class);
                    if (persoanaFirebase != null) {
                        persoaneFirebase.add(persoanaFirebase);
                    }
                }
                callback.runResultOnUiThread(persoaneFirebase);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
