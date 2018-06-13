package tech.iosd.benefit.DashboardFragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.DialogInterface;
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
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;

import tech.iosd.benefit.R;
import tech.iosd.benefit.Services.GPSTracker;
import tech.iosd.benefit.Utils.Constants;

import static android.content.Context.BIND_AUTO_CREATE;
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
    private View discardBtn;


    private GPSTracker myService;
    private ArrayList<LatLng> points;
    private Polyline polyline;

    long startTime;
     boolean status = false;
    LocationManager locationManager;
    private boolean isgoogleMap = false;
    //boolean mServiceBound = false;

    private TextView distance;
    double distace_paused = 0;
    double distace_total= 0;
    double lastDistance =0;

    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            GPSTracker.LocalBinder binder = (GPSTracker.LocalBinder) service;
            myService = binder.getService();
            GPSTracker.LocalBinder myBinder = (GPSTracker.LocalBinder) service;
            myService = myBinder.getService();
            myService.setmContext(ctx);
            Toast.makeText(getActivity().getApplicationContext(),"service connceted",Toast.LENGTH_LONG).show();
            status = true;

            if(isgoogleMap){
                if(status ){
                    locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

                    LatLng myLocation = new LatLng(myService.getLatitude(), myService.getLongitude());

                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(myLocation);
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(30);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);
                    googleMap.setMyLocationEnabled(true);
                }else if(status){
//                    gpsTracker.showSettingsAlert();

                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            status = false;
        }
    };

    void bindService() {
        if (status == true)
            return;

        Thread t = new Thread(){
            public void run(){
                Intent i = new Intent(getContext(), GPSTracker.class);
                getContext().bindService(i, sc, BIND_AUTO_CREATE);
                startTime = System.currentTimeMillis();
            }
        };

        t.start();


    }

    void unbindService() {
        if (status == false)
            return;
        Intent i = new Intent(getContext(), GPSTracker.class);
        getContext().unbindService(sc);
        status = false;
    }
    //This method leads you to the alert dialog box.
    void checkGps() {
        locationManager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            showGPSDisabledAlertToUser();
        }
    }

    //This method configures the Alert Dialog box.
    private void showGPSDisabledAlertToUser() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("Enable GPS to use application")
                .setCancelable(false)
                .setPositiveButton("Enable GPS",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent callGPSSettingIntent = new Intent(
                                        android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                startActivity(callGPSSettingIntent);
                            }
                        });
        alertDialogBuilder.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alertDialogBuilder.create();
        alert.show();
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.dashboard_track_my_activity_run, container, false);

        ctx = rootView.getContext();
        distance = rootView.findViewById(R.id.dashboard_track_my_activity_distance_textview);

        bindService();

        /*Thread t = new Thread(){
            public void run(){
                ctx.bindService(
                        new Intent(getActivity().getApplicationContext(), GPSTracker.class),
                        sc,
                        BIND_AUTO_CREATE
                );
            }
        };
        t.start();*/



        fm = getFragmentManager();

        startBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_start);
        pauseBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_pause);
        stopBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_stop);
        discardBtn = rootView.findViewById(R.id.dashboard_track_my_activity_running_discard);
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

               /* if(status ){
                    LatLng myLocation = new LatLng(myService.getLatitude(), myService.getLongitude());

                    CameraUpdate center=
                            CameraUpdateFactory.newLatLng(myLocation);
                    CameraUpdate zoom=CameraUpdateFactory.zoomTo(15);

                    googleMap.moveCamera(center);
                    googleMap.animateCamera(zoom);
                }*/


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
        myService.unbindService(sc);
        myService.onDestroy();
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
                myService.setPaused(true);
                break;
            }
            case R.id.dashboard_track_my_activity_running_resume:
            {
                //gpsTracker.setPaused(false);
                Toast.makeText(getActivity().getApplicationContext(),"rrere",Toast.LENGTH_LONG).show();
                startBtn.setVisibility(View.VISIBLE);
                pauseBtn.setVisibility(View.GONE);
                stopBtn.setVisibility(View.GONE);
                discardBtn.setVisibility(View.GONE);
                myService.setPaused(false);

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
                        startBtn.setVisibility(View.VISIBLE);
                        pauseBtn.setVisibility(View.GONE);
                        stopBtn.setVisibility(View.GONE);
                        discardBtn.setVisibility(View.GONE);
                        myService.setPaused(true);
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




    private void startRunning() {
            String stringLongitude = String.valueOf(myService.getLongitude());
            myService.setPaused(false);

            Toast.makeText(getActivity().getApplicationContext(),"Lat:"+stringLongitude+"\nLong"+stringLongitude,Toast.LENGTH_LONG).show();

            LatLng sydney = new LatLng(myService.getLatitude(), myService.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Starting Point").snippet("You started your running journey from this point."));

            CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(30).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        CameraUpdate zoom=CameraUpdateFactory.zoomTo(30);
        googleMap.animateCamera(zoom);

       /* points.clear();
        myService.setPoints(points);*/

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





    }

    private void redrawLine(){

        points = myService.getPoints();

        googleMap.clear();
        CameraUpdate center=
                CameraUpdateFactory.newLatLng(new LatLng(myService.getLatitude(),myService.getLongitude()));

        googleMap.moveCamera(center);

        //distance.setText(String.format("%.2f", gpsTracker.getDistance()));


        PolylineOptions options = new PolylineOptions().width(8).color(Color.BLACK).geodesic(true);
        for (int i = 0; i < points.size(); i++) {
            LatLng point = points.get(i);
            options.add(point);
        }

        polyline = googleMap.addPolyline(options); //add Polyline
        double distance_number = SphericalUtil.computeLength(points);
        if(!myService.isPaused()){
            lastDistance = distance_number - lastDistance;
            distance_number = distance_number - distace_paused;
            distace_total = distance_number;

        }else {
            distace_paused = distace_paused + lastDistance;

        }
        distace_total = distance_number;
        distance.setText(String.valueOf(distance_number));

    }




}
