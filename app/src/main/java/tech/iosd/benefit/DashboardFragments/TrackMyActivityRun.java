package tech.iosd.benefit.DashboardFragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
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
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

import tech.iosd.benefit.R;
import tech.iosd.benefit.Services.GPSTracker;
import tech.iosd.benefit.Utils.Constants;

import static android.content.Context.LOCATION_SERVICE;

public class TrackMyActivityRun extends Fragment implements View.OnClickListener
{
    Context ctx;
    FragmentManager fm;
    MapView mMapView;

    private GoogleMap googleMap;
    private View startBtn;
    private View pauseBtn;
    private View stopBtn;

    private GPSTracker gpsTracker;
    private ArrayList<LatLng> points;
    private Polyline polyline;
    private LocationManager mgr;
    private boolean isgoogleMap = false;
    boolean mServiceBound = false;

    private TextView distance;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_track_my_activity_run, container, false);

        ctx = rootView.getContext();

        Thread t = new Thread(){
            public void run(){
                ctx.bindService(
                        new Intent(getActivity().getApplicationContext(), GPSTracker.class),
                        mServiceConnection,
                        Context.BIND_AUTO_CREATE
                );
            }
        };
        t.start();

        ctx.bindService(
                new Intent(getActivity().getApplicationContext(), GPSTracker.class),
                mServiceConnection,
                Context.BIND_AUTO_CREATE
        );

        fm = getFragmentManager();

        startBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_start);
        pauseBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_pause);
        stopBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_stop);
        distance = (TextView)rootView.findViewById(R.id.dashboard_track_my_activity_distance_textview);


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
                isgoogleMap=true;
                googleMap = mMap;


                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                {
                    googleMap.setMyLocationEnabled(true);

                }

                if(mServiceBound && gpsTracker.canGetLocation()){
                    LatLng myLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(myLocation);
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);
                }else if(mServiceBound){
                    gpsTracker.showSettingsAlert();

                }


            }
        });
        IntentFilter intentFilter = new IntentFilter(Constants.GPS_UPDATE);

         BroadcastReceiver mReceiver = new BroadcastReceiver() {

            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,"activity",Toast.LENGTH_LONG).show();

            /*

            Intent stopIntent = new Intent(MainActivity.this,
                        BroadcastService.class);
                stopService(stopIntent);
                */

                if (intent.getAction().equals(Constants.GPS_UPDATE)) {
                    //intent.getExtras();
                    redrawLine();
                    Toast.makeText(getActivity().getApplicationContext(),"activity",Toast.LENGTH_LONG).show();
                }
            }
        };
        getActivity().getApplicationContext().registerReceiver(mReceiver, intentFilter);

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
        gpsTracker.onDestroy();
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
                startRunning();
                break;
            }
            case R.id.dashboard_track_my_activity_running_pause:
            {
                startBtn.setVisibility(View.GONE);
                pauseBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.VISIBLE);
                //gpsTracker.setPaused(true);
                break;
            }
            case R.id.dashboard_track_my_activity_running_resume:
            {
                //gpsTracker.setPaused(false);
                Toast.makeText(getActivity().getApplicationContext()," "+ gpsTracker.canGetLocation(),Toast.LENGTH_LONG).show();

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

    private ServiceConnection mServiceConnection = new ServiceConnection() {


        @Override
        public void onServiceDisconnected(ComponentName name) {
            mServiceBound = false;
            Toast.makeText(getActivity().getApplicationContext(),"service disconnceted",Toast.LENGTH_LONG).show();

        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GPSTracker.MyBinder myBinder = (GPSTracker.MyBinder) service;
            gpsTracker = myBinder.getService();
            mServiceBound = true;
            //gpsTracker.setmContext(ctx);
            Toast.makeText(getActivity().getApplicationContext(),"service connceted",Toast.LENGTH_LONG).show();

            if(isgoogleMap){
                if(mServiceBound && gpsTracker.canGetLocation()){
                    LatLng myLocation = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(myLocation);
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);
                }else if(mServiceBound){
//                    gpsTracker.showSettingsAlert();

                }
            }




        }
    };


    private void startRunning() {

        if (gpsTracker.canGetLocation())
        {
            String stringLongitude = String.valueOf(gpsTracker.getLongitude());

            Toast.makeText(getActivity().getApplicationContext(),"Lat:"+stringLongitude+"\nLong"+stringLongitude,Toast.LENGTH_LONG).show();

            LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Starting Point").snippet("You started your running journey from this point."));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(30).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        }
        else
        {

            gpsTracker.showSettingsAlert();
        }

    }

    private void redrawLine(){

        points = gpsTracker.getPoints();

        googleMap.clear();

        //distance.setText(String.format("%.2f", gpsTracker.getDistance()));


        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }
        polyline = googleMap.addPolyline(options); //add Polyline
    }




}
