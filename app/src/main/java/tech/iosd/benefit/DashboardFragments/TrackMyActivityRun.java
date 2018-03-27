package tech.iosd.benefit.DashboardFragments;

import android.Manifest;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import tech.iosd.benefit.R;

public class TrackMyActivityRun extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    MapView mMapView;

    private GoogleMap googleMap;
    private View startBtn;
    private View pauseBtn;
    private View stopBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_track_my_activity_run, container, false);
        ctx = rootView.getContext();
        fm = getFragmentManager();

        startBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_start);
        pauseBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_pause);
        stopBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_stop);

        rootView.findViewById(R.id.back_icon).setOnClickListener(this);
        rootView.findViewById(R.id.dashboard_track_my_activity_running_discard).setOnClickListener(this);
        startBtn.setOnClickListener(this);
        pauseBtn.setOnClickListener(this);

        mMapView = rootView.findViewById(R.id.track_running_map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();

        try
        {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback()
        {
            @Override
            public void onMapReady(GoogleMap mMap)
            {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                LatLng sydney = new LatLng(-34, 151);
                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.back_icon:
                fm.popBackStack();
                break;
            case R.id.dashboard_track_my_activity_running_start:
            {
                startBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.VISIBLE);
                stopBtn.setVisibility(View.GONE);
                break;
            }
            case R.id.dashboard_track_my_activity_running_pause:
            {
                startBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.dashboard_track_my_activity_running_discard:
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getActivity());
                View mView = getActivity().getLayoutInflater().inflate(R.layout.dialog_accept, null);
                TextView dialogMsg = mView.findViewById(R.id.dialog_message);
                Button dialogAccept = mView.findViewById(R.id.dialog_accept);
                Button dialogCancel = mView.findViewById(R.id.dialog_cancel);
                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                dialogMsg.setText(R.string.alert_discard_activity);
                dialogAccept.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
                dialogCancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });
                break;
            }
        }
    }
}
