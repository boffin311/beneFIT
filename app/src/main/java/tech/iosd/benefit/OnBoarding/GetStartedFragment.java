package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


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


        return v;

    }

    @Override
    public int backgroundColor() {
        return R.color.colorPrimaryDark;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimaryDark;
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
