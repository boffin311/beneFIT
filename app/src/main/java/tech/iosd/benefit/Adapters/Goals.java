package tech.iosd.benefit.Adapters;

import java.util.ArrayList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import tech.iosd.benefit.FragmentImageView;

public class Goals extends FragmentStatePagerAdapter
{
    private ArrayList<Integer> images;

    public Goals(FragmentManager fm, ArrayList<Integer> images)
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
