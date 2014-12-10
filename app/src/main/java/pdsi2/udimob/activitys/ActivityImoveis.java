package pdsi2.udimob.activitys;

/**
 * Created by Linyker on 17/11/2014.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import pdsi2.udimob.DetectaConexao;
import pdsi2.udimob.ListViewAdapter;
import pdsi2.udimob.R;
import pdsi2.udimob.conexao.ImovelRest;
import pdsi2.udimob.dto.Imovel;

public class ActivityImoveis extends Activity {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    String[] preco;
    String[] bairro;
    String[] usuario;
    String[] imagem_url;
    String [] descricaoImovel;
    String [] email;
    String [] telefone;
    String [] logradouro;
    String[] idImovel;
    String[] tipoImovel;
    String[] numero;
    String[] nome;

    ArrayList<Imovel> arraylist = new ArrayList<Imovel>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verificaConexao();
        setContentView(R.layout.imoveis);

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);
        nome = new String[] {"João","Maria","José","Marta","Joaquim","Fulano","Ciclano","Beltrano","Pedro","Marcos" };
        imagem_url = new String[] {
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg",
                "http://nrksuper.no/super/files/2014/05/android.jpeg" };

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

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = (EditText) findViewById(R.id.search);

        // Capture Text in EditText
        editsearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = editsearch.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub

            }
        });
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
                        Imovel wp = new Imovel(nome[i],imovels.get(i).getIdImovel(),imovels.get(i).getTipoImovel(),imovels.get(i).getUsuario(),imovels.get(0).getLogradouro(),imovels.get(i).getNumero(),imovels.get(i).getBairro(),imovels.get(i).getDescricaoImovel(),imovels.get(i).getPreco(),email[i],telefone[i],imagem_url[i]);
                        // Binds all strings into an array
                        arraylist.add(wp);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                ringProgressDialog.dismiss();
            }
        }.start();
    }
}

