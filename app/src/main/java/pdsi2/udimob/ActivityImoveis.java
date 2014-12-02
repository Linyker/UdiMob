package pdsi2.udimob;

/**
 * Created by Linyker on 17/11/2014.
 */
import java.util.ArrayList;
import java.util.Locale;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.gms.internal.ci;

public class ActivityImoveis extends Activity {

    // Declare Variables
    ListView list;
    ListViewAdapter adapter;
    EditText editsearch;
    String[] cidade;
    String[] bairro;
    String[] proprietario;
    String[] imagem_url;
    String [] descricao;
    String [] email;
    String [] telefone;
    String [] endereco;
    ArrayList<ObjImoveis> arraylist = new ArrayList<ObjImoveis>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imoveis);

        // Generate sample data
        cidade = new String[] { "Uberlândia", "Uberaba", "Araguari", "Uberlândia", "Araguari", "Uberlândia", "Patrocinio", "Nova Ponte", "Uberaba", "Araguari" };

        bairro = new String[] { "Bairro 1", "Bairro 2", "Bairro 3","Bairro 4", "Bairro 5", "Bairro 6", "Bairro 7", "Bairro 8","Bairro 9", "Bairro 10" };

        proprietario = new String[] {"João","Maria","José","Marta","Joaquim","Fulano","Ciclano","Beltrano","Pedro","Marcos" };

        endereco = new String[] {"Rua 1","Rua 2","Rua 3","Rua 4","Rua 5","Rua 6","Rua 7","Rua 8","Rua 9","Rua 10" };


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

        descricao = new String[]{
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

        for (int i = 0; i < cidade.length; i++)
        {
            ObjImoveis wp = new ObjImoveis(bairro[i],cidade[i],imagem_url[i],descricao[i],proprietario[i],email[i],telefone[i],endereco[i]);
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
}

