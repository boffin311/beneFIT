package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import agency.tango.materialintroscreen.SlideFragment;
import tech.iosd.benefit.R;

/**
 * Created by Anubhav on 26-12-2017.
 */

public class getStartedFragment extends SlideFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.onboarding_getstarted,container,false);
        return view;
    }

    @Override
    public int backgroundColor() {
        return super.backgroundColor();
    }

    @Override
    public int buttonsColor() {
        return super.buttonsColor();
    }

    @Override
    public boolean canMoveFurther() {
        return super.canMoveFurther();
    }

    @Override
    public String cantMoveFurtherErrorMessage() {
        return super.cantMoveFurtherErrorMessage();
    }
}
