package tech.iosd.benefit.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import tech.iosd.benefit.FragmentImageView;

public class PlansIntro extends FragmentStatePagerAdapter
{
    private ArrayList<Integer> images;

    public PlansIntro(FragmentManager fm, ArrayList<Integer> images)
    {
        super(fm);
        this.images = images;
    }

    @Override
    public Fragment getItem(int position)
    {
        FragmentImageView f = FragmentImageView.newInstance();
        f.setImageList(images.get(position));
        return f;
    }

    @Override
    public int getCount()
    {
        return images.size();
    }
}
