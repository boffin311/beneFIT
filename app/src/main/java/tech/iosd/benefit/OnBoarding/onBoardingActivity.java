package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;

import agency.tango.materialintroscreen.MaterialIntroActivity;

public class onBoardingActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        addSlide(new getGoalFragment());
        addSlide(new basicDetailFragment());

        /*TODO: Add custom slides here for onBoarding Fragment*/
    }
}
