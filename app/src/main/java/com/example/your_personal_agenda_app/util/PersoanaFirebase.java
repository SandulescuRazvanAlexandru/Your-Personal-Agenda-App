package com.example.your_personal_agenda_app.util;


public class PersoanaFirebase {
        private String id;
        private String numePersoana;
        private int varstaPersoana;
        private String genPersoana;
        private double greutatePersoana;
        private double inaltimePersoana;
        private String dataPersoana;

    public PersoanaFirebase() {
    }

    public PersoanaFirebase(String id, String numePersoana, int varstaPersoana, String genPersoana, double greutatePersoana, double inaltimePersoana, String dataPersoana) {
        this.id = id;
        this.numePersoana = numePersoana;
        this.varstaPersoana = varstaPersoana;
        this.genPersoana = genPersoana;
        this.greutatePersoana = greutatePersoana;
        this.inaltimePersoana = inaltimePersoana;
        this.dataPersoana = dataPersoana;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getGenPersoana() {
        return genPersoana;
    }

    public void setGenPersoana(String genPersoana) {
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

    public String getDataPersoana() {
        return dataPersoana;
    }

    public void setDataPersoana(String dataPersoana) {
        this.dataPersoana = dataPersoana;
    }

    @Override
    public String toString() {
        return "PersoanaFirebase{" +
                "id='" + id + '\'' +
                ", numePersoana='" + numePersoana + '\'' +
                ", varstaPersoana=" + varstaPersoana +
                ", genPersoana='" + genPersoana + '\'' +
                ", greutatePersoana=" + greutatePersoana +
                ", inaltimePersoana=" + inaltimePersoana +
                ", dataPersoana='" + dataPersoana + '\'' +
                '}';
    }
}


