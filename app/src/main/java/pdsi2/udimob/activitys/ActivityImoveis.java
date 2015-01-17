package pdsi2.udimob.activitys;

/**
 * Created by Linyker on 17/11/2014.
 */
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import pdsi2.udimob.classes.DetectaConexao;
import pdsi2.udimob.classes.ListViewAdapter;
import pdsi2.udimob.R;
import pdsi2.udimob.classes.SQLiteBairro;
import pdsi2.udimob.conexao.ImovelRest;
import pdsi2.udimob.dto.Imovel;

public class ActivityImoveis extends Activity {

    // Declare Variables
    private ListView list;
    private ListViewAdapter adapter;
    private AutoCompleteTextView editsearch;
    private List<Imovel> original;
    private ArrayList<Imovel> arraylist = new ArrayList<Imovel>();
    private ArrayAdapter<String> adapter_bairros;
    private SQLiteBairro sqLiteBairro;
    private static final int NOTIFY_ME_ID=9999;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificaConexao();
        setContentView(R.layout.imoveis);
        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
        carregarImoveis();
        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);
        original = new ArrayList<Imovel>();
        original.addAll(arraylist);

         // Binds the Adapter to the ListView
        list.setAdapter(adapter);


        try {
            sqLiteBairro = new SQLiteBairro(ActivityImoveis.this);
            sqLiteBairro.openDB();
        } catch (SQLException e) {
            e.printStackTrace();
        }



        String[] bairros = sqLiteBairro.listarBairros();
        adapter_bairros = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,bairros);

        editsearch = (AutoCompleteTextView) findViewById(R.id.edit_buscar);

        editsearch.setAdapter(adapter_bairros);
        editsearch.setThreshold(1);


        editsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                new Thread(){
                    @Override
                    public void run() {
                        String texto = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                        adapter.filter(texto);

                    }
                }.start();

            }
        });


        //Criando a notificação
        final NotificationManager mgr = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification note = new Notification(R.drawable.dica,"Udimob-Dicas!",System.currentTimeMillis());

        PendingIntent i = PendingIntent.getActivity(this,0,new Intent(this,ActivityNotification.class),0);

        note.setLatestEventInfo(this,"Dicas para utilização ","Clique aqui para saber mais!",i);
        mgr.notify(NOTIFY_ME_ID,note);

    }





    public void carregarImoveis(){

        final ProgressDialog ringProgressDialog = ProgressDialog.show(ActivityImoveis.this, "Aguarde", "Carregando Imóveis", true);

        ringProgressDialog.setCancelable(true);

        new Thread(){
            @Override
            public void run() {
                try {
                    List<Imovel> imovels;
                    final ImovelRest imovelRest = new ImovelRest();

                    imovels = imovelRest.getListaImovel();

                    for (int i = 0; i < imovels.size(); i++)
                    {
                        Imovel wp = new Imovel(imovels.get(i).getUsuario(),imovels.get(i).getIdImovel(),imovels.get(i).getTipoImovel(),imovels.get(i).getUsuario(),imovels.get(i).getLogradouro(),imovels.get(i).getNumero(),imovels.get(i).getBairro(),imovels.get(i).getDescricaoImovel(),imovels.get(i).getPreco(),imovels.get(i).getEmail(),imovels.get(i).getTelefone(),"");
                        // Binds all strings into an array
                        arraylist.add(wp);
                    }

                    // Pass results to ListViewAdapter Class
                    adapter = new ListViewAdapter(ActivityImoveis.this, arraylist);

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }
        }.start();
    }


    public void verificaConexao(){
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        DetectaConexao detecta = new DetectaConexao(getApplicationContext());
        boolean conectado = detecta.verificaConexao();

        if(conectado == false) {
            build = new AlertDialog.Builder(ActivityImoveis.this);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqLiteBairro.close();
    }
}

