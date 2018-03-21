package tech.iosd.benefit.DashboardFragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tech.iosd.benefit.R;

public class Workout extends Fragment implements View.OnClickListener
{
    public boolean isMyWorkoutLocked = false;

    Context ctx;
    FragmentManager fm;
    TextView myWorkoutTxt;
    ImageView myWorkoutLock;
    ImageView myWorkoutProceed;
    CardView myWorkoutCard;
    CardView freeWorkoutCard;
    ImageView highIntensityTraining;
    ImageView functionallyFit;
    ImageView legedTube;
    ImageView cardioCrunch;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_workout, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        myWorkoutTxt = rootView.findViewById(R.id.dashboard_workout_my_txt);
        myWorkoutLock = rootView.findViewById(R.id.dashboard_workout_my_lock);
        myWorkoutProceed = rootView.findViewById(R.id.dashboard_workout_my_proceed);
        myWorkoutCard = rootView.findViewById(R.id.dashboard_workout_my_workouts);
        freeWorkoutCard = rootView.findViewById(R.id.dashboard_workout_free_workouts);
        highIntensityTraining = rootView.findViewById(R.id.dashboard_workout_high_intensity_interval_training);
        functionallyFit = rootView.findViewById(R.id.dashboard_workout_functionally_fit);
        legedTube = rootView.findViewById(R.id.dashboard_workout_legedtude);
        cardioCrunch = rootView.findViewById(R.id.dashboard_workout_cardio_crunch);

        setMyWorkoutLockCondition(isMyWorkoutLocked);

        myWorkoutCard.setOnClickListener(this);
        freeWorkoutCard.setOnClickListener(this);
        highIntensityTraining.setOnClickListener(this);
        functionallyFit.setOnClickListener(this);
        legedTube.setOnClickListener(this);
        cardioCrunch.setOnClickListener(this);

        return rootView;
    }

    void setMyWorkoutLockCondition(boolean isLocked)
    {
        if(!isLocked)
        {
            myWorkoutLock.setVisibility(View.INVISIBLE);
            myWorkoutProceed.setVisibility(View.VISIBLE);
            myWorkoutTxt.setTextColor(Color.BLACK);
        }
        else
        {
            myWorkoutLock.setVisibility(View.VISIBLE);
            myWorkoutProceed.setVisibility(View.INVISIBLE);
            myWorkoutTxt.setTextColor(Color.parseColor("9d9d9d"));
        }
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dashboard_workout_my_workouts:
            {
                if(isMyWorkoutLocked)
                    fm.beginTransaction().replace(R.id.dashboard_content, new MyWorkoutLocked()).addToBackStack("tag").commit();
                else
                    fm.beginTransaction().replace(R.id.dashboard_content, new MyWorkout()).addToBackStack("tag").commit();
                break;
            }
            case R.id.dashboard_workout_high_intensity_interval_training:
                fm.beginTransaction().replace(R.id.dashboard_content, new HighIntensityTraining()).addToBackStack("tag").commit();
                break;
            case R.id.dashboard_workout_functionally_fit:
                fm.beginTransaction().replace(R.id.dashboard_content, new FunctionallyFit()).addToBackStack("tag").commit();
                break;
            case R.id.dashboard_workout_legedtude:
                fm.beginTransaction().replace(R.id.dashboard_content, new LegedTube()).addToBackStack("tag").commit();
                break;
            case R.id.dashboard_workout_cardio_crunch:
                fm.beginTransaction().replace(R.id.dashboard_content, new CardioCrunch()).addToBackStack("tag").commit();
                break;
        }

    }
}
