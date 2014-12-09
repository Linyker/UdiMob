package pdsi2.udimob.activitys;

/**
 * Created by Linyker on 17/11/2014.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pdsi2.udimob.R;
import pdsi2.udimob.activity.ActivityEnviarEmail;
import pdsi2.udimob.activity.ActivityIndicarAmigo;
import pdsi2.udimob.activity.ActivityMapa;

public class ActivitySingleItemView extends Activity {
    // Declare Variables
    TextView txtbairro;
    TextView txtcidade;
    TextView txtproprietario;
    TextView txtEmail;
    TextView txtTelefone;
    TextView txtDescricao;
    TextView txtEndereco;
    ImageView imgflag;
    String bairro;
    String cidade;
    String proprietario;
    String imagem_imovel;
    String telefone;
    String email;
    String descricao;
    String endereco;
    Bitmap bitmap1;
    Button enviar_email,indicar_amigo,ver_mapa;

    String[] imageIDs = {
            "http://4.bp.blogspot.com/-GvQv8Ro99To/UuazzmEP8wI/AAAAAAAAFhI/WoCOm3QhPvE/s1600/Fachada+Sobrado+Esquina+(8).jpg",
            "http://4.bp.blogspot.com/-fIdnEg5hnrI/UuedmjDFG3I/AAAAAAAAFl0/Gl3gsPqSWgI/s1600/Fachada+Sobrado+Esquina+%25286%2529.jpg",
            "http://www.dicassobre.com.br/wp-content/uploads/2014/06/fachadas-de-casas-7.jpg"
    };

    Integer[] imageIDs1 = {
            R.drawable.casa1,
            R.drawable.casa2

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);

        // Get the intent from ListViewAdapter
        Intent i = getIntent();
        // Get the results of rank
        bairro = i.getStringExtra("bairro");
        // Get the results of country
        cidade = i.getStringExtra("cidade");
        // Get the results of population
        proprietario = i.getStringExtra("proprietario");
        // Get the results of flag
        imagem_imovel = i.getStringExtra("imagem_imovel");

        email = i.getStringExtra("email");

        descricao = i.getStringExtra("descricao");

        telefone = i.getStringExtra("telefone");

        endereco = i.getStringExtra("endereco");



        // Locate the TextViews in singleitemview.xml
        txtbairro = (TextView) findViewById(R.id.bairro);
        txtcidade = (TextView) findViewById(R.id.cidade);
        txtproprietario = (TextView) findViewById(R.id.proprietario);
        txtEmail = (TextView) findViewById(R.id.email);
        txtTelefone = (TextView) findViewById(R.id.telefone);
        txtDescricao = (TextView) findViewById(R.id.descricao);
        txtEndereco = (TextView) findViewById(R.id.endereco);

        enviar_email = (Button) findViewById(R.id.botao_enviar_email);
        indicar_amigo = (Button) findViewById(R.id.botao_indicar_amigo);
        ver_mapa = (Button) findViewById(R.id.botao_ver_mapa);

        // Locate the ImageView in singleitemview.xml
      //  imgflag = (ImageView) findViewById(R.id.flag);

        // Load the results into the TextViews
        txtbairro.setText(bairro);
        txtcidade.setText(cidade);
        txtproprietario.setText(proprietario);
        txtEmail.setText(email);
        txtTelefone.setText(telefone);
        txtDescricao.setText(descricao);
        txtEndereco.setText(endereco);

        // Load the image into the ImageView
       // imgflag.setImageResource(flag);

        //final ImageView imageView = (ImageView) findViewById(R.id.imagem);


        /*
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    InputStream in = new URL(imagem_imovel).openStream();
                    bitmap1 = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(bitmap1 != null){
                    imageView.setImageBitmap(bitmap1);
                }
            }
        }.execute();

        */

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));




        enviar_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitySingleItemView.this,ActivityEnviarEmail.class);
                i.putExtra("email_contato",email);
                startActivity(i);
            }
        });

        indicar_amigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivitySingleItemView.this,ActivityIndicarAmigo.class);
                i.putExtra("imobiliaria",proprietario);
                i.putExtra("endereco",endereco);
                i.putExtra("email",email);
                i.putExtra("telefone",telefone);
                i.putExtra("bairro",bairro);

                startActivity(i);
            }
        });

        ver_mapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ActivitySingleItemView.this,ActivityMapa.class);
                i.putExtra("endereco",endereco);
                i.putExtra("bairro",bairro);
                startActivity(i);
            }
        });

    }

    public class ImageAdapter extends BaseAdapter
    {
        private Context context;
        private int itemBackground;
        public ImageAdapter(Context c)
        {
            context = c;
            //---setting the style---
            TypedArray a = obtainStyledAttributes(R.styleable.Galeria);
            itemBackground = a.getResourceId(
                    R.styleable.Galeria_android_galleryItemBackground, 0);
            a.recycle();
        }
        //---returns the number of images---
        public int getCount()
        {
            return imageIDs.length;
        }
        //---returns the ID of an item---
        public Object getItem(int position)
        {
            return position;
        }
        //---returns the ID of an item---
        public long getItemId(int position)
        {
            return position;
        }
        //---returns an ImageView view---
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            final ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setBackgroundResource(itemBackground);

            new AsyncTask<Void,Void,Void>(){
                @Override
                protected Void doInBackground(Void... voids) {
                    try {
                        InputStream in = new URL(imageIDs[position]).openStream();
                        bitmap1 = BitmapFactory.decodeStream(in);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    if(bitmap1 != null){
                        imageView.setImageBitmap(bitmap1);
                    }
                }
            }.execute();

            //imageView.setImageResource(imageIDs1[position]);

            return imageView;
        }
    }
}


