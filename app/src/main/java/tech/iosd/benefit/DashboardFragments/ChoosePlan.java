package tech.iosd.benefit.DashboardFragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

import tech.iosd.benefit.Adapters.PlansIntro;
import tech.iosd.benefit.R;

public class ChoosePlan extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    View rootView;
    ArrayList<ImageView> pagerIndicator;
    TextView introPagerTxt;
    int currentPage = 0;
    Timer timer;
    final long DELAY_MS = 500;
    final long PERIOD_MS = 2000;
    String[] introPagerTxts = {"Talk to your coach regularly through chat and call.",
                                "Train smarter with our coach. Get personalized plan that suits your lifestyle.",
                                "Personally meet with coaches. Note - terms and conditions apply.",
                                "Assured results"};
    ImageView icon;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_choose_a_plan, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        final ViewPager viewPager = rootView.findViewById(R.id.choose_a_plan_intro_pager);
        introPagerTxt = rootView.findViewById(R.id.choose_a_plan_intro_pager_txt);
        final ArrayList<Integer> plansImages = new ArrayList<>();
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
        final Handler handler = new Handler();
        final Runnable update = new Runnable()
        {
            @Override
            public void run()
            {
                if(currentPage == plansImages.size())
                    currentPage = 0;
                viewPager.setCurrentItem(currentPage++, true);
            }
        };

        timer = new Timer();
        timer .schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);

        rootView.findViewById(R.id.choose_a_plan_intro_plans).setOnClickListener(this);
        icon = getActivity().findViewById(R.id.navigation_dashboard_coach);

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
    @Override
    public void onStart() {
        super.onStart();
        if(icon!=null){
            icon.setColorFilter(getResources().getColor(R.color.colorSelectedIcon));
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(icon!=null){
            icon.setColorFilter(null);
        }
    }
}

