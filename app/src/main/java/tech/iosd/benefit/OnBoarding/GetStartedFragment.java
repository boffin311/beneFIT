package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;


import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 28-12-2017.
 */

public class GetStartedFragment extends SlideFragment {
    public GetStartedFragment() {
    }

    public static GetStartedFragment newInstance() {
        return new GetStartedFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.onboarding_getstarted, container, false);
        //YoYo.with(Techniques.FadeIn).delay(1000).playOn(v.findViewById(R.id.logo));

        Button startBtn=v.findViewById(R.id.startBtn);
        Animation btm_up = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        startBtn.startAnimation(btm_up);
        //startBtn.animate().setStartDelay(2000).start();
        //YoYo.with(Techniques.BounceInUp).duration(1000).delay(2000).playOn(startBtn);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

            }
        });

        return v;
    }

    @Override
    public int backgroundColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int buttonsColor() {
        return R.color.white;
    }

    @Override
    public boolean canMoveFurther() {
        return true;
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
