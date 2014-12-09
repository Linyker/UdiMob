package pdsi2.udimob.activitys;

/**
 * Created by Linyker on 17/11/2014.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
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

        // Generate sample data
        preco = new String[] { "10000.00", "20000.00", "30000.00", "40000.00", "50000.00", "40000.00", "30000.00", "70000.00", "25000.00", "50000.00" };

        bairro = new String[] { "Brasil", "Centro", "Daniel Fonseca","Morada da Colina", "Centro", "Tibery", "Jardim das Palmeiras", "Brasil","Centro", "Cidade Jardim" };

        usuario = new String[] {"1","2","3","4","5","6","7","8","9","10" };

        nome = new String[] {"João","Maria","José","Marta","Joaquim","Fulano","Ciclano","Beltrano","Pedro","Marcos" };


        logradouro = new String[] {"Avenida Cesário Alvim, 1331","Rua Rodolfo Correia, 470","Av Marcos de Freitas Costa, 1855","Av Rondon Pacheco, 3223","Avenida Getúlio Vargas, 1040","Avenida Espanha, 880","Avenida Dos Pássaros, 234","Avenida Mato Grosso, 694","Avenida Getúlio Vargas, 1040","Avenida Uirapuru, 961" };

        numero = new String[] {"10","200","30","40","50","69","77","86","59","140"};

        idImovel = new String[] {"1","2","3","4","5","6","7","8","9","10"};

        tipoImovel = new String[] {"1","2","1","2","1","2","1","1","2","2"};

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

        descricaoImovel = new String[]{
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Duis eu rutrum ex, vel sodales justo"
        };

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

        // Locate the ListView in listview_main.xml
        list = (ListView) findViewById(R.id.listview);

        List<Imovel> imovels = new ArrayList<Imovel>();
        ImovelRest imovelRest = new ImovelRest();

        try {
            imovels = imovelRest.getListaImovel();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < bairro.length; i++)
        {
             Imovel wp = new Imovel(nome[i],Integer.parseInt(idImovel[i]),Integer.parseInt(tipoImovel[i]),Integer.parseInt(usuario[i]),logradouro[i],Integer.parseInt(numero[i]),bairro[i],descricaoImovel[i],Double.parseDouble(preco[i]),email[i],telefone[i],imagem_url[i]);
            // Binds all strings into an array
            arraylist.add(wp);
        }

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
}

