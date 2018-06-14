package tech.iosd.benefit.DashboardFragments;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import tech.iosd.benefit.R;

public class Notification extends Fragment
{
    Context ctx;
    FragmentManager fm;
    ImageView icon;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_notifications, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();
        icon =  getActivity().findViewById(R.id.navigation_dashboard_notification);

        return rootView;
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
