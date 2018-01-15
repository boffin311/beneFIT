package tech.iosd.benefit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

enum ModelObject
{
    FIRST(R.drawable.burn_fat),
    SECOND(R.drawable.stay_healthy),
    THIRD(R.drawable.build_muscle);

    private int mThumb;

    ModelObject(int thumb)
    {
        mThumb = thumb;
    }

    public int getThumb()
    {
        return mThumb;
    }
}


public class OnBoardingGoals extends PagerAdapter
{
    private Context mContext;

    OnBoardingGoals(Context context)
    {
        mContext = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup collection, int position)
    {
        ModelObject modelObject = ModelObject.values()[position];
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.onboarding_goals, collection, false);
        collection.addView(layout);
        ImageView img = layout.findViewById(R.id.get_started_goals_thumb);
        img.setImageResource(modelObject.getThumb());
        return layout;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view)
    {
        collection.removeView((View) view);
    }

    @Override
    public int getCount()
    {
        return ModelObject.values().length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object)
    {
        return view == object;
    }
}