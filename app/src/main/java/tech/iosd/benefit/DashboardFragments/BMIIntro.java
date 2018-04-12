package tech.iosd.benefit.DashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.iosd.benefit.R;

public class BMIIntro extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_bmi_intro, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        rootView.findViewById(R.id.dashboard_bmi_intro_want_expert).setOnClickListener(this);
        rootView.findViewById(R.id.dashboard_bmi_intro_self).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.dashboard_bmi_intro_want_expert:
            {
                fm.popBackStack();
                break;
            }
            case R.id.dashboard_bmi_intro_self:
            {
                fm.popBackStack();
                break;
            }
        }
    }
}
