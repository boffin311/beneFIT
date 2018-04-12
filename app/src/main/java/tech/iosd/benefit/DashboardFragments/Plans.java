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
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import tech.iosd.benefit.Adapters.PlansView;
import tech.iosd.benefit.R;

public class Plans extends Fragment implements ViewPager.OnPageChangeListener
{
    private int[] layouts = { R.layout.dashboard_plan_view_1, R.layout.dashboard_plan_view_2,
                              R.layout.dashboard_plan_view_3, R.layout.dashboard_plan_view_4};
    private int[] pagerIndicator = { R.id.dashboard_plan_views_pager_indicator_1,
                                    R.id.dashboard_plan_views_pager_indicator_2,
                                    R.id.dashboard_plan_views_pager_indicator_3,
                                    R.id.dashboard_plan_views_pager_indicator_4 };
    private List<ImageView> indicatorViews = new ArrayList<>();

    Context ctx;
    FragmentManager fm;
    View rootView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.dashboard_plan_views, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        for (int aPagerIndicator : pagerIndicator)
            indicatorViews.add((ImageView) rootView.findViewById(aPagerIndicator));

        ViewPager mPager = rootView.findViewById(R.id.dashboard_plan_views_pager);
        mPager.setAdapter(new PlansView(layouts, ctx, fm));
        mPager.addOnPageChangeListener(this);

        Button callBtn = rootView.findViewById(R.id.nutrition_plan_call);

        if(callBtn != null)
        {
            callBtn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    fm.beginTransaction().replace(R.id.dashboard_content, new BodyCompositionAnalysis()).addToBackStack(null).commit();
                }
            });
        }

        return rootView;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
    {
        for(int i=0; i<indicatorViews.size(); i++)
            indicatorViews.get(i).setAlpha(0.3f);

        indicatorViews.get(position).setAlpha(1.0f);
    }

    @Override
    public void onPageSelected(int position) { }

    @Override
    public void onPageScrollStateChanged(int state) { }
}
