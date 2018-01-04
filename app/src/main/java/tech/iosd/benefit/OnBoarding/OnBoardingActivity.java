package tech.iosd.benefit.OnBoarding;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import agency.tango.materialintroscreen.MaterialIntroActivity;

public class OnBoardingActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        addSlide(new GetStartedFragment());
        addSlide(new GetGoalFragment());
        addSlide(new BasicDetailFragment());
        addSlide(new LoginFragment());

        /*TODO: Add custom slides here for onBoarding Fragment*/
    }
}
