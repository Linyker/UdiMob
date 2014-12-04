package pdsi2.udimob;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Linyker on 10/11/2014.
 *
 * Retorna a latitude e longitude através de um endereço fornecido
 */





public class GeoCode {

    private static double latitude,longitute;



    public static LatLng getLocationInfo(String address) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        LatLng location = null;
        try {
            address = address.replaceAll(" ","+");

            HttpPost httppost = new HttpPost("http://maps.google.com/maps/api/geocode/json?address=" + address + "&sensor=false");

            HttpClient client = new DefaultHttpClient();
            HttpResponse response;
            stringBuilder = new StringBuilder();

            response = client.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream stream = entity.getContent();
            int b;
            while ((b = stream.read()) != -1) {
                stringBuilder.append((char) b);
            }
        } catch (ClientProtocolException e) {
        } catch (IOException e) {
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = new JSONObject(stringBuilder.toString());
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        longitute = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lng");

        latitude = ((JSONArray)jsonObject.get("results")).getJSONObject(0)
                .getJSONObject("geometry").getJSONObject("location")
                .getDouble("lat");


        location = new LatLng(latitude,longitute);
        //location.setLatitude(latitude);
        //location.setLongitude(longitute);

        return location;
    }

    public static LatLng adicionarMarcador(Object[] objects){
        LatLng marcacao = null;
        ArrayList arrayList = (ArrayList) objects[0];

        try {
            marcacao = getLocationInfo(arrayList.get(0).toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return marcacao;
    }

    public static double distancia(Object[] objects){
        ArrayList arrayList = (ArrayList) objects[0];
        Double distancia = 0.0;
        //O endereço fica na posição 0 do arraylist
        //As posições latitude e longitude ficam respectivamente nas posições 1 e 2 do array list

        double minhaLatitude = Double.parseDouble(String.valueOf(arrayList.get(1)));
        double minhaLongitude = Double.parseDouble(String.valueOf(arrayList.get(2)));

        LatLng ondeEstou = new LatLng(minhaLatitude,minhaLongitude);
        //LatLng ondeEuVou = new LatLng(latitude,longitude);
        LatLng ondeEuVou;


        //pegando as coordenadas para calcular a distancia com a minha posição atual

        try {
            ondeEuVou = getLocationInfo(arrayList.get(0).toString());
            distancia = GeoCode.distance(ondeEuVou,ondeEstou);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        /*
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

            distancia = GeoCode.distance(ondeEuVou,ondeEstou);


        }catch(JSONException e){
            e.printStackTrace();
        }
        */

        return distancia;
    }

    public static double distance(LatLng StartP, LatLng EndP) {
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2-lat1);
        double dLon = Math.toRadians(lon2-lon1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6366000 * c;
    }

    // DECODE POLYLINE
    public static List<LatLng> decodePolyline(String encoded) {

        List<LatLng> listPoints = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)), (((double) lng / 1E5)));
            Log.i("Script", "POL: LAT: "+p.latitude+" | LNG: "+p.longitude);
            listPoints.add(p);
        }
        return listPoints;
    }

    // PARSER JSON
    public static List<LatLng> buildJSONRoute(String json) throws JSONException {
        JSONObject result = new JSONObject(json);
        JSONArray routes = result.getJSONArray("routes");

        long distance = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONObject("distance").getInt("value");

        JSONArray steps = routes.getJSONObject(0).getJSONArray("legs").getJSONObject(0).getJSONArray("steps");
        List<LatLng> lines = new ArrayList<LatLng>();

        for(int i=0; i < steps.length(); i++) {
            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("start_location").getDouble("lng"));


            String polyline = steps.getJSONObject(i).getJSONObject("polyline").getString("points");

            for(LatLng p : decodePolyline(polyline)) {
                lines.add(p);
            }

            Log.i("Script", "STEP: LAT: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lat")+" | LNG: "+steps.getJSONObject(i).getJSONObject("end_location").getDouble("lng"));
        }

        return(lines);
    }
}
