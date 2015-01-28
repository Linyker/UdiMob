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
import android.net.Uri;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import pdsi2.udimob.R;

public class ActivitySingleItemView extends Activity {
    // Declare Variables
    private TextView txtbairro,txtpreco,txtproprietario,txtEmail,txtTelefone,txtDescricao,txtEndereco;
    private String bairro,proprietario,imagem_imovel,telefone,email,descricao,endereco,preco;

    TelephonyManager gerenciadorTelefone;
    Bitmap bitmap1;

    private static final int MAPA = 0;
    private static final int EMAIL = 1;
    private static final int LIGAR = 2;

    private static final int VER_MAPA = Menu.FIRST;
    private static final int ENVIAR_EMAIL = VER_MAPA + 1;
    private static final int INDICAR_AMIGO = ENVIAR_EMAIL + 1;
    private static final int LIGAR_PROPRIETARIO = INDICAR_AMIGO + 1;


    String[] imageIDs = {
            "http://4.bp.blogspot.com/-GvQv8Ro99To/UuazzmEP8wI/AAAAAAAAFhI/WoCOm3QhPvE/s1600/Fachada+Sobrado+Esquina+(8).jpg",
            "http://4.bp.blogspot.com/-fIdnEg5hnrI/UuedmjDFG3I/AAAAAAAAFl0/Gl3gsPqSWgI/s1600/Fachada+Sobrado+Esquina+%25286%2529.jpg",
            "http://www.dicassobre.com.br/wp-content/uploads/2014/06/fachadas-de-casas-7.jpg"
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        SubMenu mapa = menu.addSubMenu("Mapa");
        SubMenu email = menu.addSubMenu("Email");
        SubMenu ligar = menu.addSubMenu("Ligar");

        mapa.add(MAPA, VER_MAPA, 0, "Ver imóvel no mapa");
        email.add(EMAIL, ENVIAR_EMAIL, 1, "Enviar email para o proprietário");
        email.add(EMAIL, INDICAR_AMIGO, 0, "Indicar imóvel para um amigo");
        ligar.add(LIGAR,LIGAR_PROPRIETARIO,0,"Ligar para proprietário");

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case LIGAR_PROPRIETARIO:
                Intent phoneCall = new Intent(Intent.ACTION_CALL);
                phoneCall.setData(Uri.parse("tel:"+telefone));
                startActivity(phoneCall);

                break;

            case VER_MAPA:

                Intent i = new Intent(ActivitySingleItemView.this,ActivityMapa.class);
                i.putExtra("endereco",endereco);
                i.putExtra("bairro",bairro);
                startActivity(i);

                break;
            case ENVIAR_EMAIL:

                Intent i2 = new Intent(ActivitySingleItemView.this,ActivityEnviarEmail.class);
                i2.putExtra("email_contato",email);
                startActivity(i2);

                break;
            case INDICAR_AMIGO:

                Intent i3 = new Intent(ActivitySingleItemView.this,ActivityIndicarAmigo.class);
                i3.putExtra("imobiliaria",proprietario);
                i3.putExtra("endereco",endereco);
                i3.putExtra("email",email);
                i3.putExtra("telefone",telefone);
                i3.putExtra("bairro",bairro);

                startActivity(i3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showMsg(String message) {
        Toast msg = Toast.makeText(ActivitySingleItemView.this, message, Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, msg.getXOffset() / 2,
                msg.getYOffset() / 2);
        msg.show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleitemview);

        PhoneCallListener phoneCallListener = new PhoneCallListener();
        gerenciadorTelefone = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);

        gerenciadorTelefone.listen(phoneCallListener,PhoneStateListener.LISTEN_CALL_STATE);



        Intent i = getIntent();
        bairro = i.getStringExtra("bairro");
        proprietario = i.getStringExtra("proprietario");
        imagem_imovel = i.getStringExtra("imagem_imovel");
        email = i.getStringExtra("email");
        descricao = i.getStringExtra("descricao");
        telefone = i.getStringExtra("telefone");
        endereco = i.getStringExtra("endereco");
        preco = i.getStringExtra("preco");

        // Locate the TextViews in singleitemview.xml
        txtbairro = (TextView) findViewById(R.id.bairro);
        txtpreco = (TextView) findViewById(R.id.preco);
        txtproprietario = (TextView) findViewById(R.id.proprietario);
        txtEmail = (TextView) findViewById(R.id.email);
        txtTelefone = (TextView) findViewById(R.id.telefone);
        txtDescricao = (TextView) findViewById(R.id.descricao);
        txtEndereco = (TextView) findViewById(R.id.endereco);

        // Load the results into the TextViews
        txtbairro.setText(bairro);
        txtpreco.setText("R$"+preco);
        txtproprietario.setText(proprietario);
        txtEmail.setText(email);
        txtTelefone.setText(telefone);
        txtDescricao.setText(descricao);
        txtEndereco.setText(endereco);

        Gallery gallery = (Gallery) findViewById(R.id.gallery1);
        gallery.setAdapter(new ImageAdapter(this));

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


    private class PhoneCallListener extends PhoneStateListener{

        String TAG = "Iniciando ligação";
        private boolean phoneCalling = false;

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if(TelephonyManager.CALL_STATE_RINGING == state){
                Log.i(TAG,"Chamando numero "+ incomingNumber);
            }

            if(TelephonyManager.CALL_STATE_OFFHOOK == state){
                Log.i(TAG,"Ocupado");
                phoneCalling = true;
            }

            if(TelephonyManager.CALL_STATE_IDLE == state){
                Log.e(TAG,"Inativo");

                if(phoneCalling){
                    Log.i(TAG,"Restart aplicação");

                    Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);

                    phoneCalling = false;


                }
            }



        }
    }

}


