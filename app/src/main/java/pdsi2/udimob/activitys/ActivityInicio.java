package pdsi2.udimob.activitys;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import pdsi2.udimob.R;
import pdsi2.udimob.classes.DetectaConexao;

/**
 * Created by Linyker on 19/01/2015.
 */
public class ActivityInicio extends Activity {

    private static final int NOTIFY_ME_ID=9999;
    private Button botao_aluguel,botao_venda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_inicial);

        verificaCondicoesParaUso();

        botao_aluguel = (Button) findViewById(R.id.botao_aluguel);
        botao_venda = (Button) findViewById(R.id.botao_venda);

        botao_aluguel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityInicio.this,ActivityImoveis.class);
                i.putExtra("situacao_imovel","aluguel");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.animation3,R.anim.animation4).toBundle();
                startActivity(i,bundle);
            }
        });

        botao_venda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityInicio.this,ActivityImoveis.class);
                i.putExtra("situacao_imovel","venda");
                Bundle bundle = ActivityOptions.makeCustomAnimation(getApplicationContext(),R.anim.animation3,R.anim.animation4).toBundle();
                startActivity(i,bundle);
            }
        });

        //Criando a notificação
        final NotificationManager mgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note = new Notification(R.drawable.dica,"Udimob-Dicas!",System.currentTimeMillis());
        PendingIntent i = PendingIntent.getActivity(this,0,new Intent(this,ActivityNotification.class),0);
        note.setLatestEventInfo(this,"Manual do usuário ","Clique aqui para saber mais!",i);
        mgr.notify(NOTIFY_ME_ID,note);
    }

    public void verificaCondicoesParaUso(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        DetectaConexao detecta = new DetectaConexao(getApplicationContext());

        boolean gps = detecta.verificaGPS();
        boolean conectado = detecta.verificaConexao();

        if(gps == false){
            build = new AlertDialog.Builder(ActivityInicio.this);
            build.setTitle("GPS");
            build.setMessage("Você precisa estar com o GPS habilitado, deseja habilitar ?");
            build.setPositiveButton(
                    "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(intent);
                        }
                    }
            );

            build.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog alert = build.create();
            alert.show();
        }

        if(conectado == false) {
            build = new AlertDialog.Builder(ActivityInicio.this);
            build.setTitle("Conexão com a internet");
            build.setMessage("Você precisa estar conectado a internet, deseja conectar ?");
            build.setPositiveButton(
                    "Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                            startActivity(intent);
                        }
                    }
            );

            build.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            AlertDialog alert = build.create();
            alert.show();
        }

    }
}
