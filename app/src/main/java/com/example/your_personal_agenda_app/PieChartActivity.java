package com.example.your_personal_agenda_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.your_personal_agenda_app.database.model.Activitate;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class PieChartActivity extends AppCompatActivity {

    public static final String  PIE_CHART_ACTIVITATI_KEY="pie_chart_activitati_key";

    private List<Activitate> activitatiPrimite = new ArrayList<Activitate>();

    private int[] yData;
    private String[] xData;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie_chart);
        Intent intent = getIntent();
        activitatiPrimite = (ArrayList<Activitate>) intent.getSerializableExtra(PIE_CHART_ACTIVITATI_KEY);
        populateData();
        pieChart = findViewById(R.id.pie_chart_view);
        Description desc = new Description();
        desc.setText(getString(R.string.descriere_text_pie_chart));
        desc.setTextSize(20);
        pieChart.setDescription(desc);
        pieChart.setRotationEnabled(true);
        pieChart.setHoleRadius(25f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.setCenterText(getString(R.string.centru_text_pie_chart));
        pieChart.setCenterTextSize(15);
        pieChart.setOnChartValueSelectedListener(ChartValueSelectedListener());
        addDataSet();
    }

    private OnChartValueSelectedListener ChartValueSelectedListener() {
        return new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Toast.makeText(getApplicationContext(),getString(R.string.pie_chart_select_text,xData[(int)h.getX()]),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected() {

            }
        };
    }

    private void populateData()
    {
        yData = new int[activitatiPrimite.size()];
        xData = new String[activitatiPrimite.size()];
        int i = 0;
        for(Activitate activitate:activitatiPrimite)
        {
            yData[i] = activitate.getOreActivitate()*60 + activitate.getMinuteActivitate();
            xData[i] = activitate.getTipActivitate();
            i++;
        }
    }

    private void addDataSet() {

        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Tip activitati");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(20);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        Random rand = new Random();
        for(int i = 0; i<yData.length; ++i)
        {
            colors.add(Color.rgb(50+rand.nextInt(205),50+rand.nextInt(205),50+rand.nextInt(205)));
        }

        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }
}