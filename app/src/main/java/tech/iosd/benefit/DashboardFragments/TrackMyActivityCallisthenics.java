package tech.iosd.benefit.DashboardFragments;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tech.iosd.benefit.R;

public class TrackMyActivityCallisthenics extends Fragment
{
    Context ctx;
    FragmentManager fm;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_fragment_track_my_activity_run, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        rootView.findViewById(R.id.dashboard_track_my_activity_callisthenics_edit_time).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

            }
        });

        return rootView;
    }
}
