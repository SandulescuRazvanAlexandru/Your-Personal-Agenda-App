package com.example.your_personal_agenda_app.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.your_personal_agenda_app.util.FelMancare;

import java.io.Serializable;

@Entity(tableName = "mancaruri")
public class Mancare implements Serializable {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        private long id;

        @ColumnInfo(name = "descriereMancare")
        private String descriereMancare;

        @ColumnInfo(name = "felMancare")
        private FelMancare felMancare;

        @ColumnInfo(name = "gramajMancare")
        private double gramajMancare;

        @ColumnInfo(name = "proteineMancare")
        private double proteineMancare;

        @ColumnInfo(name = "carbohidratiMancare")
        private double carbohidratiMancare;

        @ColumnInfo(name = "grasimiMancare")
        private double grasimiMancare;

        @ColumnInfo(name = "caloriiMancare")
        private double caloriiMancare;

        @ColumnInfo(name = "idPersoanaMancare")
        private int idPersoanaMancare;

        public Mancare(long id, String descriereMancare, FelMancare felMancare, double gramajMancare, double proteineMancare, double carbohidratiMancare, double grasimiMancare, double caloriiMancare, int idPersoanaMancare) {
                this.id = id;
                this.descriereMancare = descriereMancare;
                this.felMancare = felMancare;
                this.gramajMancare = gramajMancare;
                this.proteineMancare = proteineMancare;
                this.carbohidratiMancare = carbohidratiMancare;
                this.grasimiMancare = grasimiMancare;
                this.caloriiMancare = caloriiMancare;
                this.idPersoanaMancare = idPersoanaMancare;
        }

        @Ignore
        public Mancare(String descriereMancare, FelMancare felMancare, double gramajMancare, double proteineMancare, double carbohidratiMancare, double grasimiMancare, double caloriiMancare, int idPersoanaMancare) {
                this.descriereMancare = descriereMancare;
                this.felMancare = felMancare;
                this.gramajMancare = gramajMancare;
                this.proteineMancare = proteineMancare;
                this.carbohidratiMancare = carbohidratiMancare;
                this.grasimiMancare = grasimiMancare;
                this.caloriiMancare = caloriiMancare;
                this.idPersoanaMancare = idPersoanaMancare;
        }

        public long getId() {
                return id;
        }

        public void setId(long id) {
                this.id = id;
        }

        public String getDescriereMancare() {
                return descriereMancare;
        }

        public void setDescriereMancare(String descriereMancare) {
                this.descriereMancare = descriereMancare;
        }

        public FelMancare getFelMancare() {
                return felMancare;
        }

        public void setFelMancare(FelMancare felMancare) {
                this.felMancare = felMancare;
        }

        public double getGramajMancare() {
                return gramajMancare;
        }

        public void setGramajMancare(double gramajMancare) {
                this.gramajMancare = gramajMancare;
        }

        public double getProteineMancare() {
                return proteineMancare;
        }

        public void setProteineMancare(double proteineMancare) {
                this.proteineMancare = proteineMancare;
        }

        public double getCarbohidratiMancare() {
                return carbohidratiMancare;
        }

        public void setCarbohidratiMancare(double carbohidratiMancare) {
                this.carbohidratiMancare = carbohidratiMancare;
        }

        public double getGrasimiMancare() {
                return grasimiMancare;
        }

        public void setGrasimiMancare(double grasimiMancare) {
                this.grasimiMancare = grasimiMancare;
        }

        public double getCaloriiMancare() {
                return caloriiMancare;
        }

        public void setCaloriiMancare(double caloriiMancare) {
                this.caloriiMancare = caloriiMancare;
        }

        public int getIdPersoanaMancare() {
                return idPersoanaMancare;
        }

        public void setIdPersoanaMancare(int idPersoanaMancare) {
                this.idPersoanaMancare = idPersoanaMancare;
        }

        @Override
        public String toString() {
                return "Mancare{" +
                        "id=" + id +
                        ", descriereMancare='" + descriereMancare + '\'' +
                        ", felMancare=" + felMancare +
                        ", gramajMancare=" + gramajMancare +
                        ", proteineMancare=" + proteineMancare +
                        ", carbohidratiMancare=" + carbohidratiMancare +
                        ", grasimiMancare=" + grasimiMancare +
                        ", caloriiMancare=" + caloriiMancare +
                        ", idPersoanaMancare=" + idPersoanaMancare +
                        '}';
        }
}
