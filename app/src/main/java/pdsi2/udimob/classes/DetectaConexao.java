package pdsi2.udimob.classes;

import android.content.ContentResolver;
import android.content.Context;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.widget.Toast;

/**
 * Created by Linyker on 04/12/2014.
 */
public class DetectaConexao {
    private Context context;


    public DetectaConexao(Context context){
        this.context = context;
    }

    public boolean verificaGPS(){
        final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            return false;
        }else{
            return true;
        }
    }

    public boolean verificaConexao(){
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo netInfo = connectivity.getActiveNetworkInfo();
            //Se não existir conexão retorna false
            if(netInfo == null){
                return false;
            }

            int netType = netInfo.getType();

            // Verifica se a conexão é do tipo WiFi ou Mobile e
            // retorna true se estiver conectado ou false em
            // caso contrário

            if(netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_MOBILE){
                return netInfo.isConnected();
            }else{
                return false;
            }
        }else{
            return false;
        }

    }

}
