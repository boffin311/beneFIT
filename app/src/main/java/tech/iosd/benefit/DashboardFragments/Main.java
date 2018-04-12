package tech.iosd.benefit.DashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tech.iosd.benefit.R;

public class Main extends Fragment implements View.OnTouchListener
{
    public static boolean introShown = false;

    Context ctx;
    Animation scaleInAnimation;
    Animation scaleOutAnimation;
    FragmentManager fm;
    FloatingActionButton contactBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_main, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        scaleInAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_in_src);
        scaleOutAnimation = AnimationUtils.loadAnimation(ctx, R.anim.scale_out_src);
        scaleInAnimation.setFillAfter(true);
        scaleOutAnimation.setFillAfter(true);

        contactBtn = getActivity().findViewById(R.id.dashboard_contact);
        if(!introShown)
        {
            contactBtn.setVisibility(View.INVISIBLE);
            fm.beginTransaction().replace(R.id.dashboard_content, new BMIIntro()).addToBackStack(null).commit();
            introShown = true;
        }
        else
        {
            contactBtn.setVisibility(View.VISIBLE);
            contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.bottom_up));
        }

        final ImageView workout = rootView.findViewById(R.id.dashboard_main_workout);
        final ImageView nutrition = rootView.findViewById(R.id.dashboard_main_nutrition);
        final ImageView track_and_log = rootView.findViewById(R.id.dashboard_main_track_and_log);
        final ImageView measurement = rootView.findViewById(R.id.dashboard_main_measurement);
        final ImageView challenges = rootView.findViewById(R.id.dashboard_main_challenges);

        workout.setOnTouchListener(this);
        nutrition.setOnTouchListener(this);
        track_and_log.setOnTouchListener(this);
        measurement.setOnTouchListener(this);
        challenges.setOnTouchListener(this);

        contactBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_message, null);
                TextView dialogMsg = mView.findViewById(R.id.dialog_message);
                Button dialogDone = mView.findViewById(R.id.dialog_done);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                dialogMsg.setText(R.string.alert_get_chat_plan);
                dialogDone.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                        fm.beginTransaction().replace(R.id.dashboard_content, new Chat()).addToBackStack(null).commit();
                        contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                        contactBtn.setVisibility(View.INVISIBLE);
                    }
                });
            }
        });
        return rootView;
    }

    @Override
    public boolean onTouch(final View view, MotionEvent motionEvent)
    {
        switch (motionEvent.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                view.startAnimation(scaleInAnimation);
                break;
            case MotionEvent.ACTION_UP:
            {
                view.startAnimation(scaleOutAnimation);
                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() { @Override public void run() { onClick(view); } }, 200);
                break;
            }
        }
        return true;
    }

    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dashboard_main_workout:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Workout()).addToBackStack(null).commit();
                contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                contactBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.dashboard_main_nutrition:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Nutrition()).addToBackStack(null).commit();
                contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                contactBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.dashboard_main_track_and_log:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new TrackAndLog()).addToBackStack(null).commit();
                contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                contactBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.dashboard_main_measurement:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new MeasurementData()).addToBackStack(null).commit();
                contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                contactBtn.setVisibility(View.INVISIBLE);
                break;
            }
            case R.id.dashboard_main_challenges:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Challenges()).addToBackStack(null).commit();
                contactBtn.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.top_down));
                contactBtn.setVisibility(View.INVISIBLE);
                break;
            }
        }
    }
}
