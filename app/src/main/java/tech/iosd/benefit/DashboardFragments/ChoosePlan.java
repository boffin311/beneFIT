package tech.iosd.benefit.DashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import tech.iosd.benefit.Adapters.PlansIntro;
import tech.iosd.benefit.R;

public class ChoosePlan extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    View rootView;
    ArrayList<ImageView> pagerIndicator;
    TextView introPagerTxt;
    String[] introPagerTxts = {"Talk to your coach regularly through chat and call.",
                                "Train smarter with our coach. Get personalized plan that suits your lifestyle.",
                                "Personally meet with coaches. Note - terms and conditions apply.",
                                "Assured results"};

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_choose_a_plan, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        ViewPager viewPager = rootView.findViewById(R.id.choose_a_plan_intro_pager);
        introPagerTxt = rootView.findViewById(R.id.choose_a_plan_intro_pager_txt);
        ArrayList<Integer> plansImages = new ArrayList<>();
        plansImages.add(R.drawable.m1);
        plansImages.add(R.drawable.m2);
        plansImages.add(R.drawable.m3);
        plansImages.add(R.drawable.m4);
        pagerIndicator = new ArrayList<>();
        pagerIndicator.add((ImageView)rootView.findViewById(R.id.choose_a_plan_intro_pager_indicator1));
        pagerIndicator.add((ImageView)rootView.findViewById(R.id.choose_a_plan_intro_pager_indicator2));
        pagerIndicator.add((ImageView)rootView.findViewById(R.id.choose_a_plan_intro_pager_indicator3));
        pagerIndicator.add((ImageView)rootView.findViewById(R.id.choose_a_plan_intro_pager_indicator4));
        onPageSelected(0);

        viewPager.setAdapter(new PlansIntro(getChildFragmentManager(), plansImages));
        viewPager.addOnPageChangeListener(this);

        rootView.findViewById(R.id.choose_a_plan_intro_plans).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        for(int i=0; i<pagerIndicator.size(); i++)
            pagerIndicator.get(i).setColorFilter(getResources().getColor(R.color.pageIndicatorNotSelected));

        pagerIndicator.get(position).setColorFilter(getResources().getColor(R.color.pageIndicatorSelected));
        introPagerTxt.setText(introPagerTxts[position]);
    }

    @Override
    public void onPageSelected(int position) { }

    @Override
    public void onPageScrollStateChanged(int state) { }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.choose_a_plan_intro_plans:
            {
                fm.beginTransaction().replace(R.id.dashboard_content, new Plans())
                        .addToBackStack(null)
                        .commit();
                break;
            }
        }
    }
}

