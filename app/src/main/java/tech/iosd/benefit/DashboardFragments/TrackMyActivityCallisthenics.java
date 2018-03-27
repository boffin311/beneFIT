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

import com.aigestudio.wheelpicker.WheelPicker;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.R;

public class TrackMyActivityCallisthenics extends Fragment
{
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_track_my_activity_callisthenics, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        rootView.findViewById(R.id.dashboard_track_my_activity_callisthenics_edit_time).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_time_picker, null);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                WheelPicker wheelPickerHour = mView.findViewById(R.id.dialog_picker_hour);
                WheelPicker wheelPickerMinute = mView.findViewById(R.id.dialog_picker_minute);
                WheelPicker wheelPickerSecond = mView.findViewById(R.id.dialog_picker_seconds);
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
                        dialog.dismiss();
                    }
                });
            }
        });

        return rootView;
    }
}
