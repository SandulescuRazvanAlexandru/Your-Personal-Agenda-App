package com.example.your_personal_agenda_app.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.your_personal_agenda_app.util.GenPersoana;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "persoane")
public class PersoanaBD implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "numePersoana")
    private String numePersoana;

    @ColumnInfo(name = "varstaPersoana")
    private int varstaPersoana;

    @ColumnInfo(name = "genPersoana")
    private GenPersoana genPersoana;

    @ColumnInfo(name = "greutatePersoana")
    private double greutatePersoana;

    @ColumnInfo(name = "inaltimePersoana")
    private double inaltimePersoana;

    @ColumnInfo(name = "dataPersoana")
    private Date dataPersoana;

    public PersoanaBD(long id, String numePersoana, int varstaPersoana, GenPersoana genPersoana, double greutatePersoana, double inaltimePersoana, Date dataPersoana) {
        this.id = id;
        this.numePersoana = numePersoana;
        this.varstaPersoana = varstaPersoana;
        this.genPersoana = genPersoana;
        this.greutatePersoana = greutatePersoana;
        this.inaltimePersoana = inaltimePersoana;
        this.dataPersoana = dataPersoana;
    }

    @Ignore
    public PersoanaBD(String numePersoana, int varstaPersoana, GenPersoana genPersoana, double greutatePersoana, double inaltimePersoana, Date dataPersoana) {
        this.numePersoana = numePersoana;
        this.varstaPersoana = varstaPersoana;
        this.genPersoana = genPersoana;
        this.greutatePersoana = greutatePersoana;
        this.inaltimePersoana = inaltimePersoana;
        this.dataPersoana = dataPersoana;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumePersoana() {
        return numePersoana;
    }

    public void setNumePersoana(String numePersoana) {
        this.numePersoana = numePersoana;
    }

    public int getVarstaPersoana() {
        return varstaPersoana;
    }

    public void setVarstaPersoana(int varstaPersoana) {
        this.varstaPersoana = varstaPersoana;
    }

    public GenPersoana getGenPersoana() {
        return genPersoana;
    }

    public void setGenPersoana(GenPersoana genPersoana) {
        this.genPersoana = genPersoana;
    }

    public double getGreutatePersoana() {
        return greutatePersoana;
    }

    public void setGreutatePersoana(double greutatePersoana) {
        this.greutatePersoana = greutatePersoana;
    }

    public double getInaltimePersoana() {
        return inaltimePersoana;
    }

    public void setInaltimePersoana(double inaltimePersoana) {
        this.inaltimePersoana = inaltimePersoana;
    }

    public Date getDataPersoana() {
        return dataPersoana;
    }

    public void setDataPersoana(Date dataPersoana) {
        this.dataPersoana = dataPersoana;
    }

    @Override
    public String toString() {
        return "PersoanaBD{" +
                "id=" + id +
                ", numePersoana='" + numePersoana + '\'' +
                ", varstaPersoana=" + varstaPersoana +
                ", genPersoana=" + genPersoana +
                ", greutatePersoana=" + greutatePersoana +
                ", inaltimePersoana=" + inaltimePersoana +
                ", dataPersoana=" + dataPersoana +
                '}';
    }
}
