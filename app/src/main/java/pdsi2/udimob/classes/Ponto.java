package pdsi2.udimob.classes;

/**
 * Created by Linyker on 06/11/2014.
 */
public class Ponto {

    double latitude;
    double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Ponto(double latitude,double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
