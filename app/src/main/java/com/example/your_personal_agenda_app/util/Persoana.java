package com.example.your_personal_agenda_app.util;

import com.example.your_personal_agenda_app.database.model.Activitate;
import com.example.your_personal_agenda_app.database.model.Mancare;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Persoana implements Serializable {

        public int getId() {
                return id;
        }

        public void setId(int id) {
                this.id = id;
        }

        private int id;
        private String numePersoana;
        private int varstaPersoana;
        private GenPersoana genPersoana;
        private double greutatePersoana;
        private double inaltimePersoana;
        private Date dataPersoana;
        private ArrayList<Mancare> listaMancaruriPersoana = new ArrayList<>();
        private ArrayList<Activitate> listaActivitatiPersoana = new ArrayList<>();

        public Persoana(String numePersoana, int varstaPersoana, GenPersoana genPersoana, double greutatePersoana, double inaltimePersoana, Date dataPersoana, ArrayList<Mancare> listaMancaruriPersoana, ArrayList<Activitate> listaActivitatiPersoana) {
                this.numePersoana = numePersoana;
                this.varstaPersoana = varstaPersoana;
                this.genPersoana = genPersoana;
                this.greutatePersoana = greutatePersoana;
                this.inaltimePersoana = inaltimePersoana;
                this.dataPersoana = dataPersoana;
                this.listaMancaruriPersoana = listaMancaruriPersoana;
                this.listaActivitatiPersoana = listaActivitatiPersoana;
        }

        public Persoana(String numePersoana, int varstaPersoana, GenPersoana genPersoana, double greutatePersoana, double inaltimePersoana, Date dataPersoana) {
                this.numePersoana = numePersoana;
                this.varstaPersoana = varstaPersoana;
                this.genPersoana = genPersoana;
                this.greutatePersoana = greutatePersoana;
                this.inaltimePersoana = inaltimePersoana;
                this.dataPersoana = dataPersoana;
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

        public ArrayList<Mancare> getListaMancaruriPersoana() {
                return listaMancaruriPersoana;
        }

        public void setListaMancaruriPersoana(ArrayList<Mancare> listaMancaruriPersoana) {
                this.listaMancaruriPersoana = listaMancaruriPersoana;
        }

        public ArrayList<Activitate> getListaActivitatiPersoana() {
                return listaActivitatiPersoana;
        }

        public void setListaActivitatiPersoana(ArrayList<Activitate> listaActivitatiPersoana) {
                this.listaActivitatiPersoana = listaActivitatiPersoana;
        }

        @Override
        public String toString() {
                return "Persoana{" +
                        "id=" + id +
                        ", numePersoana='" + numePersoana + '\'' +
                        ", varstaPersoana=" + varstaPersoana +
                        ", genPersoana=" + genPersoana +
                        ", greutatePersoana=" + greutatePersoana +
                        ", inaltimePersoana=" + inaltimePersoana +
                        ", dataPersoana=" + dataPersoana +
                        ", listaMancaruriPersoana=" + listaMancaruriPersoana +
                        ", listaActivitatiPersoana=" + listaActivitatiPersoana +
                        '}';
        }
}
