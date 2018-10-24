package tech.iosd.benefit.DashboardFragments;

import android.app.AlertDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.R;
import tech.iosd.benefit.Model.DatabaseHandler;

public class TrackMyActivityCallisthenics extends Fragment
{
    Context ctx;
    FragmentManager fm;
    TextView duration,calories;
    private DatabaseHandler db;
    WheelPicker wheelPickerHour;
    WheelPicker wheelPickerMinute;
    WheelPicker wheelPickerSecond;
    int type_of_activity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_track_my_activity_callisthenics, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        db = new DatabaseHandler(ctx);
        duration=rootView.findViewById(R.id.duration_callisthenics);
        calories=rootView.findViewById(R.id.calories_callisthenics);
        Bundle bundle = this.getArguments();
        String activity_type ="AEROBICS";
        if (bundle != null) {
            activity_type = bundle.getString("ACTIVITY_TYPE", "AEROBICS");
        }
        if(activity_type.equals("SKIPPING"))
            type_of_activity=3;
        else if(activity_type.equals("SWIMMING"))
            type_of_activity=2;
        else
            type_of_activity=1;
        rootView.findViewById(R.id.dashboard_track_my_activity_callisthenics_edit_time).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_picker_time, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                wheelPickerHour = mView.findViewById(R.id.dialog_picker_hour);
                wheelPickerMinute = mView.findViewById(R.id.dialog_picker_minute);
                wheelPickerSecond = mView.findViewById(R.id.dialog_picker_seconds);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                List<String> data_hour = new ArrayList<>();
                for (int i = 1; i <= 24; i++)
                    data_hour.add( i > 9 ? Integer.toString(i) : "0" + i );
                wheelPickerHour.setData(data_hour);
                List<String> data_minute = new ArrayList<>();
                for (int i = 0; i <= 60; i++)
                    data_minute.add( i > 9 ? Integer.toString(i) : "0" + i );
                wheelPickerMinute.setData(data_minute);
                List<String> data_second = new ArrayList<>();
                for (int i = 0; i <= 60; i++)
                    data_second.add( i > 9 ? Integer.toString(i) : "0" + i );
                wheelPickerSecond.setData(data_second);

                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        if (duration!=null)
                        duration.setText(data_hour.get(wheelPickerHour.getCurrentItemPosition())+":"
                                    +data_minute.get(wheelPickerMinute.getCurrentItemPosition())+":"+
                                    data_second.get(wheelPickerSecond.getCurrentItemPosition()));
                        changeCalories();
                        dialog.dismiss();

                    }
                });
            }
        });

        return rootView;
    }

    private void changeCalories(){

        double METS=5.5;
        if(type_of_activity==2)
            METS= 5.8;
        else if(type_of_activity==3)
            METS= 8.5;

        int hours= wheelPickerHour.getCurrentItemPosition()+1;
        int mins= wheelPickerMinute.getCurrentItemPosition()+1;
        int secs = wheelPickerSecond.getCurrentItemPosition()+1;
        int total= hours+ mins/60 + secs/3600;
        int weight= db.getUserWeight();
        double calories_value= METS*hours*weight;
        DecimalFormat df = new DecimalFormat("#.##");
        String formatted = df.format(calories_value);
        calories.setText(formatted);
    }
}
