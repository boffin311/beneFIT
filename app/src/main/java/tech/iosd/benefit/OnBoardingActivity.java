package tech.iosd.benefit;

import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import tech.iosd.benefit.OnBoardingFragments.GetStarted;

public class OnBoardingActivity extends AppCompatActivity
{
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.onboarding_content, new GetStarted()).commit();
    }
}
