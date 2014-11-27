package pdsi2.udimob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity implements GoogleMap.OnMarkerClickListener {

    static GoogleMap googleMap;
    private LatLng frameworkSystemLocation;
    private Polyline polyline;
    private List<LatLng> list;
    private static ArrayList<Ponto> marcadores = new ArrayList<Ponto>();
    private Ponto ponto1 = new Ponto();
    private Ponto ponto2 = new Ponto();
    private DecimalFormat df = new DecimalFormat("#,##");
    private GeoCode geoCode = new GeoCode();
    private static LocationManager locationManager ;
    private static Criteria criteria = new Criteria(); // Creating a criteria object to retrieve provider
    private static String provider;
    private static Location location;
    private static Double lat,lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        ponto1.setLongitude(-48.2882804); ponto1.setLatitude(-18.8707074);
        ponto2.setLongitude(-48.2777712); ponto2.setLatitude(-18.957444);

        marcadores.add(ponto1);
        marcadores.add(ponto2);


        createMapView();

        minhaLocalizacao();
        movimentarCamera(-18.9220717, -48.2635649);
        //adicionarMarcadores();

        Button botao_distancia = (Button) findViewById(R.id.botao_distancia);
        Button botao_rota = (Button) findViewById(R.id.botao_rota);
        Button botao_imoveis = (Button) findViewById(R.id.botao_imoveis);

        botao_imoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MyActivity.this,Imoveis.class);
                startActivity(i);
            }
        });

        botao_distancia.setOnClickListener(new View.OnClickListener() {
            LatLng p1 = new LatLng(ponto1.getLatitude(),ponto1.getLongitude());

            LatLng p2 = new LatLng(ponto2.getLatitude(),ponto2.getLongitude());

            @Override
            public void onClick(View view) {
                Double distancia = geoCode.distance(p1,p2);
                //geo.getLatLong("Alameda César Augusto Faria - Residencial Gramado, MG");
                Toast.makeText(MyActivity.this, "Distancia(aproximada): "+df.format(distancia/1000)+" Km", Toast.LENGTH_LONG).show();
            }
        });


        botao_rota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Location minhaLocalizacao = googleMap.getMyLocation();
                LatLng origem = new LatLng(minhaLocalizacao.getLatitude(),minhaLocalizacao.getLongitude());
                LatLng destino = new LatLng(-18.4868648,-47.4030649);
                //Toast.makeText(getApplicationContext(),minhaLocalizacao.getLatitude()+" "+minhaLocalizacao.getLongitude(),Toast.LENGTH_LONG).show();

                getRoute(origem, destino);
            }
        });



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
       return true;
    }


    private void movimentarCamera(Double latitude, Double longitude) {

        frameworkSystemLocation = new LatLng(latitude,longitude);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(frameworkSystemLocation).zoom(12).bearing(0).tilt(90).build();
        CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);

        googleMap.animateCamera(update,3000, new GoogleMap.CancelableCallback() {
            @Override
            public void onFinish() {
                Log.i("Script","CancelableCallback.onFinish()");
            }

            @Override
            public void onCancel() {
                Log.i("Script","CancelableCallback.onCancel()");
            }
        });

    }

    public LatLng getLocation()
    {
        // Get the location manager

        criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        location = locationManager.getLastKnownLocation(provider);
        Double lat,lon;

        boolean isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        //Location location;

        if (isGpsEnabled) {
            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        }
        else {
            location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        }

        Log.i("Prov", "Provider is: "+provider);
        Log.i("Loc", "Location is: "+location);

        try {
            lat = location.getLatitude();
            lon = location.getLongitude ();
            return new LatLng(lat, lon);
        }
        catch (NullPointerException e){
            e.printStackTrace();
            Log.e("Nulo", "nulo");
            return null;
        }
    }

    private void minhaLocalizacao() {

        // Getting reference to the SupportMapFragment of activity_main.xml
        MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);


    }
    private void createMapView(){
        /**
         * Catch the null pointer exception that
         * may be thrown when initialising the map
         */
        try{
            if(googleMap == null){
                googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                /**
                 * If the map is still null after attempted initialisation,
                 * show an error to the user
                 */

                if(null == googleMap) {
                    Toast.makeText(getApplicationContext(),
                            "Error creating map", Toast.LENGTH_SHORT).show();
                }

            }
        }catch (NullPointerException e){
            Log.e("Erro no mapa", e.toString());
        }
    }

    //adicionando marcador no mapa
    public void tracarRota() {


        LatLng p1 = new LatLng(ponto1.getLatitude(),ponto1.getLongitude());
        LatLng p2 = new LatLng(ponto2.getLatitude(),ponto2.getLongitude());

        getRoute(p1, p2);

        googleMap.setOnMarkerClickListener((GoogleMap.OnMarkerClickListener) this);

        //Marca aonde o mapa vai ser inicializado

       // ponto1.setLatitude(minhaLatitude);
       // ponto1.setLongitude(minhaLongitude);

        //ponto2.setLatitude(-18.645914);
        //ponto2.setLongitude(-48.193838);




        /*
        googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(-18.645914, -48.193838))
                        .title("Chegada")
                        .draggable(true)

           */



        //marker0 = googleMap.addMarker(new MarkerOptions().position(p1).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        //marker1 = googleMap.addMarker(new MarkerOptions().position(p2).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));



    }



    public static void adicionarMarcadores(){
        for(int i=0; i< marcadores.size(); i++){
            LatLng latLng = new LatLng(marcadores.get(i).getLatitude(),marcadores.get(i).getLongitude());
            googleMap.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }



    /*----------------------------Traçar Rota---------------------------------------*/

    public void drawRoute(){
        PolylineOptions po;

        if(polyline == null){
            po = new PolylineOptions();

            for(int i = 0, tam = list.size(); i < tam; i++){
                po.add(list.get(i));
            }

            po.color(Color.BLUE).width(10);
            polyline = googleMap.addPolyline(po);
        }
        else{
            polyline.setPoints(list);
        }
    }

    // WEB CONNECTION
    //public void getRoute(final String origin, final String destination){
    public void getRoute(final LatLng origin, final LatLng destination){

        final ProgressDialog ringProgressDialog = ProgressDialog.show(MyActivity.this, "Aguarde", "Traçando a Rota", true);

        ringProgressDialog.setCancelable(true);

        new Thread(){
            public void run(){
						/*String url= "http://maps.googleapis.com/maps/api/directions/json?origin="
								+ origin+"&destination="
								+ destination+"&sensor=false";*/
                String url= "http://maps.googleapis.com/maps/api/directions/json?origin="
                        + origin.latitude+","+origin.longitude+"&destination="
                        + destination.latitude+","+destination.longitude+"&sensor=false";


                HttpResponse response;
                HttpGet request;
                AndroidHttpClient client = AndroidHttpClient.newInstance("route");

                request = new HttpGet(url);
                try {
                    response = client.execute(request);
                    final String answer = EntityUtils.toString(response.getEntity());

                    runOnUiThread(new Runnable(){
                        public void run(){
                            try {
                                //Log.i("Script", answer);
                                list = GeoCode.buildJSONRoute(answer);
                                drawRoute();
                            }
                            catch(JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
                catch(IOException e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }
        }.start();
    }

    public void minhasCoordenadas(){
        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) MyActivity.this.getSystemService(Context.LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                lat = location.getLatitude();
                lon = location.getLongitude();
                LatLng latLng = new LatLng(lat,lon);
                Toast.makeText(MyActivity.this, "Coordenadas: "+latLng, Toast.LENGTH_LONG).show();
                //Log.e("Localizacao : ","Your Location is:" + lat + " " + lon);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

}
