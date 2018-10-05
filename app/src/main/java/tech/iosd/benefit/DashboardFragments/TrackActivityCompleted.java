package tech.iosd.benefit.DashboardFragments;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import tech.iosd.benefit.R;

public class TrackActivityCompleted extends Fragment
{
    Context ctx;
    FragmentManager fm;
    String calorie,duration,distance,avgPaceText;
    TextView calorieBurnt,totalDuration,totalDistance,avgPace;
    ImageView backIcon;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            calorie = getArguments().getString("CALORIE","0");
            duration = getArguments().getString("DURATION","00:00");
            distance = getArguments().getString("DISTANCE","0.0");
            avgPaceText = getArguments().getString("AVG_PACE","0.0");
        } else {
            return;
        }
    }
    Button saveActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.layout_track_my_activity_done, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        calorieBurnt=rootView.findViewById(R.id.complete_calories_track_my_activity);
        totalDuration=rootView.findViewById(R.id.complete_track_activity_duration);
        totalDistance=rootView.findViewById(R.id.complete_dashboard_track_my_activity_distance_textview);
        avgPace=rootView.findViewById(R.id.complete_track_activity_avg_pace);
        backIcon=rootView.findViewById(R.id.completed_back_icon);
        saveActivity=rootView.findViewById(R.id.complete_save_activity);
        calorieBurnt.setText(calorie);
        totalDuration.setText(duration);
        totalDistance.setText(distance);
        avgPace.setText(avgPaceText);
        backIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.popBackStack();
            }
        });
        saveActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fm.popBackStack();
            }
        });
        return rootView;
    }
}
