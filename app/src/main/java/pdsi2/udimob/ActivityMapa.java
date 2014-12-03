package pdsi2.udimob;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ActivityMapa extends Activity implements GoogleMap.OnMarkerClickListener{

    static GoogleMap googleMap;
    private LatLng frameworkSystemLocation;
    private Polyline polyline;
    private List<LatLng> list;
    private static ArrayList<Ponto> marcadores = new ArrayList<Ponto>();
    private DecimalFormat df = new DecimalFormat("#,##");
    private GeoCode geoCode = new GeoCode();
    private static Double lat,lon;
    private String endereco,bairro;
    private static Location minhaLocalizacao;
    private Location enderecoLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Intent i = getIntent();
        endereco = i.getStringExtra("endereco");
        bairro = i.getStringExtra("bairro");

        createMapView();

        minhaLocalizacao();
        movimentarCamera(-18.9220717, -48.2635649);
        adicionarMarcadores();

        Button botao_distancia = (Button) findViewById(R.id.botao_distancia);
        Button botao_rota = (Button) findViewById(R.id.botao_rota);
        Button botao_imoveis = (Button) findViewById(R.id.botao_imoveis);

        botao_imoveis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityMapa.this,ActivityImoveis.class);
                startActivity(i);
            }
        });

        botao_distancia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               minhaLocalizacao = googleMap.getMyLocation();

               ArrayList<Object> objeto = new ArrayList<Object>();
               objeto.add(endereco);
               objeto.add(minhaLocalizacao.getLatitude());
               objeto.add(minhaLocalizacao.getLongitude());

               new Distancia().execute(objeto);

                //enderecoLatLong = GeoCode.getLatLong(endereco);

                //Toast.makeText(getApplicationContext(),enderecoLatLong.getLatitude()+" "+ enderecoLatLong.getLongitude(),Toast.LENGTH_LONG).show();
                //Double distancia = geoCode.distance(p1,p2);
                //geo.getLatLong("Alameda César Augusto Faria - Residencial Gramado, MG");
                //Toast.makeText(ActivityMapa.this, "Distancia(aproximada): "+df.format(distancia/1000)+" Km", Toast.LENGTH_LONG).show();
            }
        });


        botao_rota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minhaLocalizacao = googleMap.getMyLocation();
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

        final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityMapa.this, "Aguarde", "Traçando a Rota", true);

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
        LocationManager locationManager = (LocationManager) ActivityMapa.this.getSystemService(Context.LOCATION_SERVICE);

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
                Toast.makeText(ActivityMapa.this, "Coordenadas: "+latLng, Toast.LENGTH_LONG).show();
                //Log.e("Localizacao : ","Your Location is:" + lat + " " + lon);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Register the listener with the Location Manager to receive location updates
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);

    }

    private class Distancia extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

           distancia(objects);

            return null;
        }
    }


    public static void distancia(Object[] objects){
        ArrayList arrayList = (ArrayList) objects[0];
        //O endereço fica na posição 0 do arraylist
        //As posições latitude e longitude ficam respectivamente nas posições 1 e 2 do array list

        double minhaLatitude = Double.parseDouble(String.valueOf(arrayList.get(1)));
        double minhaLongitude = Double.parseDouble(String.valueOf(arrayList.get(2)));

        //pegando as coordenadas para calcular a distancia com a minha posição atual
        String url = "http://maps.google.com/maps/api/geocode/json?address="+arrayList.get(0).toString()+"&sensor=false";
        url = url.replaceAll(" ","+");

        HttpGet httpGet = new HttpGet(url);
        HttpClient client = new DefaultHttpClient();
        HttpResponse response;
        StringBuilder stringBuilder = new StringBuilder();

        try{
            response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;

            while((b = stream.read()) != -1){
                stringBuilder.append((char) b);
            }
        }catch(ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e1){
            e1.printStackTrace();
        }

        JSONObject jsonObject;

        try{
            jsonObject = new JSONObject(stringBuilder.toString());

            double longitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lng");
            double latitude = ((JSONArray) jsonObject.get("results")).getJSONObject(0).getJSONObject("geometry").getJSONObject("location").getDouble("lat");
//
            LatLng ondeEstou = new LatLng(minhaLatitude,minhaLongitude);
            LatLng ondeEuVou = new LatLng(latitude,longitude);

            Double distancia = GeoCode.distance(ondeEuVou,ondeEstou);
            Log.e("DISTANCIA ENTRE OS PONTOS",distancia.toString());


        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
