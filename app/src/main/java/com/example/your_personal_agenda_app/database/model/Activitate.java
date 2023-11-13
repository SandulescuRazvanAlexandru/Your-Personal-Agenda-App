package com.example.your_personal_agenda_app.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "activitati")
public class Activitate implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "tipActivitate")
    private String tipActivitate;

    @ColumnInfo(name = "oreActivitate")
    private int oreActivitate;

    @ColumnInfo(name = "minuteActivitate")
    private int minuteActivitate;

    @ColumnInfo(name = "caloriiArseActivitate")
    private double caloriiArseActivitate;

    @ColumnInfo(name = "idPersoanaActivitate")
    private int idPersoanaActivitate;

    public Activitate(long id, String tipActivitate, int oreActivitate, int minuteActivitate, double caloriiArseActivitate, int idPersoanaActivitate) {
        this.id = id;
        this.tipActivitate = tipActivitate;
        this.oreActivitate = oreActivitate;
        this.minuteActivitate = minuteActivitate;
        this.caloriiArseActivitate = caloriiArseActivitate;
        this.idPersoanaActivitate = idPersoanaActivitate;
    }

    @Ignore
    public Activitate(String tipActivitate, int oreActivitate, int minuteActivitate, double caloriiArseActivitate, int idPersoanaActivitate) {
        this.tipActivitate = tipActivitate;
        this.oreActivitate = oreActivitate;
        this.minuteActivitate = minuteActivitate;
        this.caloriiArseActivitate = caloriiArseActivitate;
        this.idPersoanaActivitate = idPersoanaActivitate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTipActivitate() {
        return tipActivitate;
    }

    public void setTipActivitate(String tipActivitate) {
        this.tipActivitate = tipActivitate;
    }

    public int getOreActivitate() {
        return oreActivitate;
    }

    public void setOreActivitate(int oreActivitate) {
        this.oreActivitate = oreActivitate;
    }

    public int getMinuteActivitate() {
        return minuteActivitate;
    }

    public void setMinuteActivitate(int minuteActivitate) {
        this.minuteActivitate = minuteActivitate;
    }

    public double getCaloriiArseActivitate() {
        return caloriiArseActivitate;
    }

    public void setCaloriiArseActivitate(double caloriiArseActivitate) {
        this.caloriiArseActivitate = caloriiArseActivitate;
    }

    public int getIdPersoanaActivitate() {
        return idPersoanaActivitate;
    }

    public void setIdPersoanaActivitate(int idPersoanaActivitate) {
        this.idPersoanaActivitate = idPersoanaActivitate;
    }

    @Override
    public String toString() {
        return "Activitate{" +
                "id=" + id +
                ", tipActivitate='" + tipActivitate + '\'' +
                ", oreActivitate=" + oreActivitate +
                ", minuteActivitate=" + minuteActivitate +
                ", caloriiArseActivitate=" + caloriiArseActivitate +
                ", idPersoanaActivitate=" + idPersoanaActivitate +
                '}';
    }
}