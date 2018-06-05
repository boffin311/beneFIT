package tech.iosd.benefit.Services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import com.google.android.gms.location.LocationListener;

import tech.iosd.benefit.Utils.Constants;

/**
 * Created by SAM33R on 31-05-2018.
 */
public class GPSTracker extends Service implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {
    private  Context mContext ;
    private ArrayList<LatLng> points;
    private IBinder mBinder = new MyBinder();
    private boolean isPaused=false;

    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private double currentLatitude;
    private double currentLongitude;
    private boolean locationAvailabe = false;

    double latitude;
    double longitude;


    @Override
    public void onCreate(){
        super.onCreate();
        points =  new ArrayList<>();


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        // Create the LocationRequest object
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10 * 1000)        // 10 seconds, in milliseconds
                .setFastestInterval(1 * 1000); // 1 second, in milliseconds


    }





    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onConnected(Bundle bundle) {
        try{
            Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            locationAvailabe =true;

            if (location == null) {
                LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

            } else {
                //If everything went fine lets get latitude and longitude
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();

                Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){
           // showSettingsAlert();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
         showSettingsAlert();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
              //  connectionResult.startResolutionForResult(mContext, CONNECTION_FAILURE_RESOLUTION_REQUEST);
                Toast.makeText(mContext,"Some error occured.\nRestarts App.",Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            Log.e("LocationError", "Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }
    @Override
    public void onLocationChanged(Location location) {
        currentLatitude = location.getLatitude();
        latitude = location.getLatitude();
        currentLongitude = location.getLongitude();
        longitude =  location.getLongitude();
        points.add(new LatLng(latitude,longitude));

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(Constants.GPS_UPDATE);
        sendBroadcast(broadcastIntent);

        Toast.makeText(this, currentLatitude + " WORKS " + currentLongitude + "", Toast.LENGTH_LONG).show();
    }


    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        alertDialog.show();

    }
    public ArrayList getPoints(){
        return points;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public boolean canGetLocation(){
        return locationAvailabe;
    }
    public double getLatitude(){
        return longitude;
    }
    public double getLongitude(){
        return longitude;
    }

    public class MyBinder extends Binder {
        public GPSTracker getService() {
            return GPSTracker.this;
        }
    }
}

