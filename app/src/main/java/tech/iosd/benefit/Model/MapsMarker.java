package tech.iosd.benefit.Model;

/**
 * Created by SAM33R on 24-06-2018.
 */

public class MapsMarker {
    private double latitude;
    private double longitude;
    private boolean typeStart;


    public MapsMarker(double latitude, double longitude, boolean typeStart) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.typeStart = typeStart;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isTypeStart() {
        return typeStart;
    }

    public void setTypeStart(boolean typeStart) {
        this.typeStart = typeStart;
    }
}
