package tech.iosd.benefit.OnBoardingFragments;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tech.iosd.benefit.R;

public class GetStarted extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.onboarding_get_started, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        ImageView motto_guy = rootView.findViewById(R.id.get_started_motto_logo);
        TextView motto = rootView.findViewById(R.id.get_started_motto);
        motto_guy.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.left_show));
        Animation motto_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        motto_fade.setStartOffset(1000);
        motto.startAnimation(motto_fade);

        final Button startBtn = rootView.findViewById(R.id.get_started_startBtn);
        Animation start_fade = AnimationUtils.loadAnimation(ctx, R.anim.fade_in);
        start_fade.setStartOffset(1200);
        startBtn.startAnimation(start_fade);
        startBtn.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.get_started_startBtn:
            {
                fm.beginTransaction().replace(R.id.onboarding_content, new ChooseAGoal())
                        .addToBackStack(null)
                        .commit();
                break;
            }
        }
    }
}
