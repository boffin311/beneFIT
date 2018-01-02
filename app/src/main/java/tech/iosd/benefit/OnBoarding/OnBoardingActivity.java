package tech.iosd.benefit.OnBoarding;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import agency.tango.materialintroscreen.MaterialIntroActivity;

public class OnBoardingActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(new GetStartedFragment());
        addSlide(new GetGoalFragment());
        addSlide(new BasicDetailFragment());
        addSlide(new LoginFragment());

        hideBackButton();
        ImageButton nextButton = findViewById(agency.tango.materialintroscreen.R.id.button_next);
        nextButton.setVisibility(View.INVISIBLE);
    }
}
