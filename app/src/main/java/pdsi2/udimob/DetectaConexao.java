package pdsi2.udimob;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Linyker on 04/12/2014.
 */
public class DetectaConexao {
    private Context context;

    public DetectaConexao(Context context){
        this.context = context;
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
