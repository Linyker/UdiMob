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
import android.widget.Button;
import android.widget.EditText;
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
    private String [] email;
    private String [] telefone;
    private String[] nome;
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
        nome = new String[] {"João","Maria","José","Marta","Joaquim","Fulano","Ciclano","Beltrano","Pedro","Marcos" };


        email = new String[]{
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
                "linykerrm@gmail.com",
        };

        telefone = new String[]{
                "(34) 3212-1111",
                "(34) 3222-3332",
                "(34) 3432-2342",
                "(34) 3564-6876",
                "(34) 3142-4534",
                "(34) 3234-6876",
                "(34) 3223-6454",
                "(34) 3223-4556",
                "(34) 3215-4321",
                "(34) 3212-1234"
        };



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


        /*
            Para inserir os bairros, execute o código com o insert, depois execute de novo sem os inserts,
            se não tirar os inserts, a cada execução será criado um novo registro igual.

            sqLiteBairro.inserirBairro("Centro");
            sqLiteBairro.inserirBairro("Fundinho");
            sqLiteBairro.inserirBairro("Nossa Senhora Aparecida");
            sqLiteBairro.inserirBairro("Martins");
            sqLiteBairro.inserirBairro("Osvaldo Rezende");
            sqLiteBairro.inserirBairro("Bom Jesus");
            sqLiteBairro.inserirBairro("Brasil");
            sqLiteBairro.inserirBairro("Cazeca");
            sqLiteBairro.inserirBairro("Lídice");
            sqLiteBairro.inserirBairro("Daniel Fonseca");
            sqLiteBairro.inserirBairro("Tabajaras");
            sqLiteBairro.inserirBairro("Presidente Roosevelt");
            sqLiteBairro.inserirBairro("Jardim Brasília");
            sqLiteBairro.inserirBairro("São José");
            sqLiteBairro.inserirBairro("Marta Helena");
            sqLiteBairro.inserirBairro("Pacaembu");
            sqLiteBairro.inserirBairro("Santa Rosa");
            sqLiteBairro.inserirBairro("Residencial Gramado");
            sqLiteBairro.inserirBairro("Nossa Senhora das Graças");
            sqLiteBairro.inserirBairro("Minas Gerais");
            sqLiteBairro.inserirBairro("Cruzeiro do Sul");
            sqLiteBairro.inserirBairro("Jardim América");
            sqLiteBairro.inserirBairro("Jardim América II");
            sqLiteBairro.inserirBairro("Distrito Industrial Norte");
            sqLiteBairro.inserirBairro("Residencial Liberdade");
            sqLiteBairro.inserirBairro("Maravilha");
            sqLiteBairro.inserirBairro("Esperança");
            sqLiteBairro.inserirBairro("Jaraguá");
            sqLiteBairro.inserirBairro("Planalto");
            sqLiteBairro.inserirBairro("Chácaras Tubalina");
            sqLiteBairro.inserirBairro("Chácaras Panorama");
            sqLiteBairro.inserirBairro("Luizote de Freitas");
            sqLiteBairro.inserirBairro("Jardim das Palmeiras");
            sqLiteBairro.inserirBairro("Jardim Patrícia");
            sqLiteBairro.inserirBairro("Jardim Holanda");
            sqLiteBairro.inserirBairro("Jardim Europa");
            sqLiteBairro.inserirBairro("Jardim Canaã");
            sqLiteBairro.inserirBairro("Jardim Célia");
            sqLiteBairro.inserirBairro("Mansour");
            sqLiteBairro.inserirBairro("Dona Zulmira");
            sqLiteBairro.inserirBairro("Taiaman");
            sqLiteBairro.inserirBairro("Guarani");
            sqLiteBairro.inserirBairro("Tocantins2");
            sqLiteBairro.inserirBairro("Morada Nova");
            sqLiteBairro.inserirBairro("Morada do Sol");
            sqLiteBairro.inserirBairro("Parque Santo Antônio");
            sqLiteBairro.inserirBairro("Chácaras Rancho Alegre");
            sqLiteBairro.inserirBairro("Jardins");
            sqLiteBairro.inserirBairro("Tubalina");
            sqLiteBairro.inserirBairro("Cidade Jardim");
            sqLiteBairro.inserirBairro("Nova Uberlândia");
            sqLiteBairro.inserirBairro("Patrimônio");
            sqLiteBairro.inserirBairro("Morada da Colina");
            sqLiteBairro.inserirBairro("Vigilato Pereira");
            sqLiteBairro.inserirBairro("Saraiva");
            sqLiteBairro.inserirBairro("Lagoinha");
            sqLiteBairro.inserirBairro("Carajás");
            sqLiteBairro.inserirBairro("Pampulha");
            sqLiteBairro.inserirBairro("Jardim Karaíba");
            sqLiteBairro.inserirBairro("Jardim Inconfidência");
            sqLiteBairro.inserirBairro("Santa Luzia");
            sqLiteBairro.inserirBairro("Granada");
            sqLiteBairro.inserirBairro("São Jorge");
            sqLiteBairro.inserirBairro("Laranjeiras");
            sqLiteBairro.inserirBairro("Shopping Park");
            sqLiteBairro.inserirBairro("Gávea Sul");
            sqLiteBairro.inserirBairro("Santa Mônica");
            sqLiteBairro.inserirBairro("Tibery");
            sqLiteBairro.inserirBairro("Segismundo Pereira");
            sqLiteBairro.inserirBairro("Novo Mundo");
            sqLiteBairro.inserirBairro("Umuarama");
            sqLiteBairro.inserirBairro("Alto Umuarama");
            sqLiteBairro.inserirBairro("Custódio Pereira");
            sqLiteBairro.inserirBairro("Aclimação");
            sqLiteBairro.inserirBairro("Mansões Aeroporto");
            sqLiteBairro.inserirBairro("Dom Almir - Região do Grande Morumbi.");
            sqLiteBairro.inserirBairro("Alvorada");
            sqLiteBairro.inserirBairro("Morumbi - Região do Grande Morumbi.");
            sqLiteBairro.inserirBairro("Joana Darc - Região do Grande Morumbi.");
            sqLiteBairro.inserirBairro("Morada dos Pássaros");
            sqLiteBairro.inserirBairro("Quintas do Bosque");
            sqLiteBairro.inserirBairro("Bosque dos Buritis");
            sqLiteBairro.inserirBairro("Jardim Ipanema");
            sqLiteBairro.inserirBairro("Jardim Califórnia");
            sqLiteBairro.inserirBairro("Jardim Sucupira.");
            sqLiteBairro.inserirBairro("Vila Marielza");
         */


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
                        Imovel wp = new Imovel(nome[i],imovels.get(i).getIdImovel(),imovels.get(i).getTipoImovel(),imovels.get(i).getUsuario(),imovels.get(i).getLogradouro(),imovels.get(i).getNumero(),imovels.get(i).getBairro(),imovels.get(i).getDescricaoImovel(),imovels.get(i).getPreco(),email[i],telefone[i],"");
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

