package pdsi2.udimob;

/**
 * Created by Linyker on 17/11/2014.
 */
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

public class ListViewAdapter extends BaseAdapter {

    // Declare Variables
    Context mContext;
    LayoutInflater inflater;
    private List<ObjImoveis> imovel = null;
    private ArrayList<ObjImoveis> arraylist;
    Bitmap bitmap;

    public ListViewAdapter(Context context,List<ObjImoveis> imovel) {
        mContext = context;
        this.imovel = imovel;
        inflater = LayoutInflater.from(mContext);
        this.arraylist = new ArrayList<ObjImoveis>();
        this.arraylist.addAll(imovel);
    }

    public class ViewHolder {
        TextView bairro;
        TextView cidade;
        TextView proprietario;
        ImageView imagem_imovel;
    }

    @Override
    public int getCount() {
        return imovel.size();
    }

    @Override
    public ObjImoveis getItem(int position) {
        return imovel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.lista_imoveis, null);
            // Locate the TextViews in listview_item.xml
            holder.bairro = (TextView) view.findViewById(R.id.bairro);
            holder.cidade = (TextView) view.findViewById(R.id.cidade);
            holder.proprietario = (TextView) view.findViewById(R.id.proprietario);
            // Locate the ImageView in listview_item.xml
            holder.imagem_imovel = (ImageView) view.findViewById(R.id.imagem_imovel);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        // Set the results into TextViews
        holder.bairro.setText(" " + imovel.get(position).getBairro());
        holder.cidade.setText(" "+ imovel.get(position).getCidade());
        holder.proprietario.setText(" "+ imovel.get(position).getProprietario());
        // Set the results into ImageView

        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {

                try {
                    InputStream in = new URL(imovel.get(position).getImagem_url()).openStream();
                    bitmap = BitmapFactory.decodeStream(in);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onPostExecute(Void aVoid) {
                if(bitmap != null) {
                    holder.imagem_imovel.setImageBitmap(bitmap);
                }
            }
        }.execute();


        // Listen for ListView Item Click
        view.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Send single item click data to SingleItemView Class
                Intent intent = new Intent(mContext, ActivitySingleItemView.class);
                // Pass all data rank
                intent.putExtra("bairro",
                        (imovel.get(position).getBairro()));
                // Pass all data country
                intent.putExtra("cidade",
                        (imovel.get(position).getCidade()));
                // Pass all data population
                intent.putExtra("proprietario",
                        (imovel.get(position).getProprietario()));
                // Pass all data flag
                intent.putExtra("imagem_imovel",
                        (imovel.get(position).getImagem_url()));

                intent.putExtra("telefone",
                        (imovel.get(position).getTelefone()));

                intent.putExtra("descricao",
                        (imovel.get(position).getDescricao()));

                intent.putExtra("email",
                        (imovel.get(position).getEmail()));

                intent.putExtra("endereco",
                        (imovel.get(position).getEndereco()));

                // Start SingleItemView Class
                mContext.startActivity(intent);
            }
        });

        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        imovel.clear();
        if (charText.length() == 0) {
            imovel.addAll(arraylist);
        } else {
            for (ObjImoveis wp : arraylist) {
                if (wp.getCidade().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    imovel.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }

}

