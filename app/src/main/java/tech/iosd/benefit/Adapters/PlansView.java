package tech.iosd.benefit.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tech.iosd.benefit.DashboardFragments.BodyCompositionAnalysis;
import tech.iosd.benefit.R;

public class PlansView extends PagerAdapter
{
    private int[] layouts;
    private LayoutInflater layoutInflater;
    private FragmentManager fm;

    public PlansView(int[] layouts, Context context, FragmentManager fm)
    {
        this.layouts = layouts;
        this.fm = fm;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return layouts.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
    {
        return (view == object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position)
    {
        View view = layoutInflater.inflate(layouts[position], container, false);
        container.addView(view);

        switch (position)
        {
            case 3:
            {
                view.findViewById(R.id.nutrition_plan_call).setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        fm.beginTransaction().replace(R.id.dashboard_content, new BodyCompositionAnalysis()).addToBackStack(null).commit();
                    }
                });
                break;
            }
        }
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
    {
        View view = (View) object;
        container.removeView(view);

    }
}
