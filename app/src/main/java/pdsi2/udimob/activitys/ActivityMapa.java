package pdsi2.udimob.activitys;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
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

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import pdsi2.udimob.classes.GeoCode;
import pdsi2.udimob.R;


public class ActivityMapa extends Activity implements GoogleMap.OnMarkerClickListener{

    static GoogleMap googleMap;
    private LatLng frameworkSystemLocation;
    private Polyline polyline;
    private List<LatLng> list;
    private DecimalFormat df = new DecimalFormat("0.00");
    private static Double lat,lon;
    private String endereco,bairro;
    private static Location minhaLocalizacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Intent i = getIntent();
        endereco = i.getStringExtra("endereco");
        bairro = i.getStringExtra("bairro");
        final ArrayList<Object> objetoEndereco = new ArrayList<Object>();
        objetoEndereco.add(endereco);

        createMapView();

        minhaLocalizacao();
        movimentarCamera(-18.9220717, -48.2635649);
        new AdicionarMarcador().execute(objetoEndereco);

        Button botao_distancia = (Button) findViewById(R.id.botao_distancia);
        Button botao_rota = (Button) findViewById(R.id.botao_rota);


        botao_distancia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               minhaLocalizacao = googleMap.getMyLocation();

               final ArrayList<Object> objeto = new ArrayList<Object>();
               objeto.add(endereco);

                if(minhaLocalizacao != null) {
                    objeto.add(minhaLocalizacao.getLatitude());
                    objeto.add(minhaLocalizacao.getLongitude());

                    new Distancia().execute(objeto);
                }else{
                   Toast.makeText(getApplicationContext(),"Aguarde até aparecer sua localização",Toast.LENGTH_LONG).show();
              }


            }
        });


        botao_rota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                minhaLocalizacao = googleMap.getMyLocation();

                final ArrayList<Object> objeto = new ArrayList<Object>();
                objeto.add(endereco);
                objeto.add(minhaLocalizacao.getLatitude());
                objeto.add(minhaLocalizacao.getLongitude());

                new TracarRota().execute(objeto);

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



    private class AdicionarMarcador extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            final LatLng posicao = GeoCode.retornaCoordenadas(objects);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    googleMap.addMarker(new MarkerOptions().position(posicao).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                }
            });

            return null;
        }
    }

    private class TracarRota extends  AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {
            final ArrayList<LatLng> dados = GeoCode.dadosParaTracarRota(objects);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    getRoute(dados.get(0),dados.get(1));
                }
            });

            return null;
        }
    }

    private class Distancia extends AsyncTask{
    @Override
        protected Object doInBackground(Object[] objects) {
            final Double result = GeoCode.distancia(objects);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                        Toast.makeText(ActivityMapa.this, "Distancia(aproximada): " + df.format(result / 1000) + " Km", Toast.LENGTH_LONG).show();

                    }
            });
           return null;

        }
    }



}
