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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.LineChartView;
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
        LineChartView bmi_chart = rootView.findViewById(R.id.dashboard_measurement_bmi_graph);
        LineChartView basal_chart = rootView.findViewById(R.id.dashboard_measurement_basal_graph);
        LineChartView fat_chart = rootView.findViewById(R.id.dashboard_measurement_fat_graph);

        bmi_chart.setInteractive(false);
        List<PointValue> bmi_entries = new ArrayList<>();
        for(int i=0; i<10; i++) bmi_entries.add((new PointValue(i, rand.nextInt(30))));
        Line bmi_line = new Line(bmi_entries).setColor(Color.BLUE).setCubic(true);
        List<Line> bmi_lines = new ArrayList<>();
        bmi_lines.add(bmi_line);
        LineChartData bmi_data = new LineChartData();
        Axis bmi_axisX = new Axis().setHasLines(true).setName("Axis X");
        Axis bmi_axisY = new Axis().setHasLines(true).setName("Axis Y");
        bmi_data.setLines(bmi_lines);
        bmi_data.setAxisXBottom(bmi_axisX);
        bmi_data.setAxisYLeft(bmi_axisY);
        bmi_chart.setLineChartData(bmi_data);

        basal_chart.setInteractive(false);
        List<PointValue> basal_entries = new ArrayList<>();
        for(int i=0; i<10; i++) basal_entries.add((new PointValue(i, rand.nextInt(30))));
        Line basal_line = new Line(basal_entries).setColor(Color.BLUE).setCubic(true);
        List<Line> basal_lines = new ArrayList<>();
        basal_lines.add(basal_line);
        LineChartData basal_data = new LineChartData();
        Axis basal_axisX = new Axis().setHasLines(true).setName("Axis X");
        Axis basal_axisY = new Axis().setHasLines(true).setName("Axis Y");
        basal_data.setLines(basal_lines);
        basal_data.setAxisXBottom(basal_axisX);
        basal_data.setAxisYLeft(basal_axisY);
        basal_chart.setLineChartData(basal_data);

        fat_chart.setInteractive(false);
        List<PointValue> fat_entries = new ArrayList<>();
        for(int i=0; i<10; i++) fat_entries.add((new PointValue(i, rand.nextInt(30))));
        Line fat_line = new Line(fat_entries).setColor(Color.BLUE).setCubic(true);
        List<Line> fat_lines = new ArrayList<>();
        fat_lines.add(fat_line);
        LineChartData fat_data = new LineChartData();
        Axis fat_axisX = new Axis().setHasLines(true).setName("Axis X");
        Axis fat_axisY = new Axis().setHasLines(true).setName("Axis Y");
        fat_data.setLines(fat_lines);
        fat_data.setAxisXBottom(fat_axisX);
        fat_data.setAxisYLeft(fat_axisY);
        fat_chart.setLineChartData(fat_data);

        return rootView;
    }
}
