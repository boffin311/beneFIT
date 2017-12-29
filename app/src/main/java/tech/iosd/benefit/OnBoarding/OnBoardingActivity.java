package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;

import agency.tango.materialintroscreen.MaterialIntroActivity;

public class OnBoardingActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        addSlide(new GetStartedFragment());
        addSlide(new GetGoalFragment());
        addSlide(new BasicDetailFragment());
        addSlide(new LoginFragment());

        /*TODO: Add custom slides here for onBoarding Fragment*/
    }
}
