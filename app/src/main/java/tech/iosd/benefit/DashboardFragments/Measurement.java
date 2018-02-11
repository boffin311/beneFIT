package tech.iosd.benefit.DashboardFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import tech.iosd.benefit.R;

public class Measurement extends Fragment
{
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_fragment_measurement, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        Random rand = new Random();
        LineChart bmi_chart = rootView.findViewById(R.id.dashboard_measurement_bmi_graph);
        LineChart basal_chart = rootView.findViewById(R.id.dashboard_measurement_basal_graph);
        LineChart fat_chart = rootView.findViewById(R.id.dashboard_measurement_fat_graph);

        List<Entry> entries = new ArrayList<>();
        for(int i=0; i<10; i++) entries.add((new Entry(i, rand.nextInt(30))));
        LineDataSet bmi_data = new LineDataSet(entries, "BMI Chart");
        bmi_data.setColor(Color.GRAY);
        bmi_data.setValueTextColor(Color.BLUE);
        bmi_chart.setData(new LineData(bmi_data));
        bmi_chart.invalidate();

        List<Entry> entries2 = new ArrayList<>();
        for(int i=0; i<10; i++) entries2.add((new Entry(i, rand.nextInt(30))));
        LineDataSet bmi_data2 = new LineDataSet(entries2, "Basal Chart");
        bmi_data2.setColor(Color.GRAY);
        bmi_data2.setValueTextColor(Color.BLUE);
        basal_chart.setData(new LineData(bmi_data2));
        basal_chart.invalidate();

        List<Entry> entries3 = new ArrayList<>();
        for(int i=0; i<10; i++) entries3.add((new Entry(i, rand.nextInt(30))));
        LineDataSet bmi_data3 = new LineDataSet(entries3, "Fat Chart");
        bmi_data3.setColor(Color.GRAY);
        bmi_data3.setValueTextColor(Color.BLUE);
        fat_chart.setData(new LineData(bmi_data3));
        fat_chart.invalidate();

        return rootView;
    }
}
