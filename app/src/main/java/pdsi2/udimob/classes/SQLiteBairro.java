package pdsi2.udimob.classes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

/**
 * Created by Linyker on 16/01/2015.
 */
public class SQLiteBairro extends SQLiteOpenHelper {

    private static final String DB_NAME = "bairros.db";
    private static final int DB_VERSION_NUMBER = 1;
    private static final String DB_TABLE_NAME = "bairros";
    private static final String DB_COLUMN_1_NAME = "nome";
    private static int QTD_BAIRROS;
    private static final String DB_CREATE_SCRIPT = "create table " + DB_TABLE_NAME +
            "(id integer primary key autoincrement, nome text not null);";

    private SQLiteDatabase sqLiteDatabase = null;

    public SQLiteBairro(Context context){
        super(context,DB_NAME,null,DB_VERSION_NUMBER);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(DB_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }

    public void openDB() throws SQLException{
        if(this.sqLiteDatabase == null){
            this.sqLiteDatabase = this.getWritableDatabase();

            QTD_BAIRROS = listarBairros().length;

            if(QTD_BAIRROS == 0){
                povoarBanco();
            }
        }
    }

    public void closeDB(){
        if(this.sqLiteDatabase != null){
            if(this.sqLiteDatabase.isOpen()){
                this.sqLiteDatabase.close();
            }
        }
    }

    public long inserirBairro(String bairro){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN_1_NAME,bairro);
        return this.sqLiteDatabase.insert(DB_TABLE_NAME,null,contentValues);
    }

    public boolean removerBairro(String bairro){
        int result = this.sqLiteDatabase.delete(DB_TABLE_NAME,"nome='"+bairro+"'",null);

        if(result > 0){
            return  true;
        }else{
            return false;
        }
    }


    public long atualizarBairro(String bairroAntigo, String bairroNovo)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DB_COLUMN_1_NAME, bairroNovo);
        return this.sqLiteDatabase.update(DB_TABLE_NAME, contentValues, "nome='" + bairroAntigo + "'", null);
    }


    public String[] listarBairros(){
        Cursor cursor = this.sqLiteDatabase.query(DB_TABLE_NAME,new String[] {DB_COLUMN_1_NAME},null,null,null,null,null);

        if(cursor.getCount() > 0){
            String[] str = new String[cursor.getCount()];
            int i = 0;

            while(cursor.moveToNext()){
                str[i] = cursor.getString(cursor.getColumnIndex(DB_COLUMN_1_NAME));
                i++;
            }
            return str;
        }else{
            return new String[]{};
        }

    }

    public void povoarBanco(){

        inserirBairro("Centro");
        inserirBairro("Fundinho");
        inserirBairro("Nossa Senhora Aparecida");
        inserirBairro("Martins");
        inserirBairro("Osvaldo Rezende");
        inserirBairro("Bom Jesus");
        inserirBairro("Brasil");
        inserirBairro("Cazeca");
        inserirBairro("Lídice");
        inserirBairro("Daniel Fonseca");
        inserirBairro("Tabajaras");
        inserirBairro("Presidente Roosevelt");
        inserirBairro("Jardim Brasília");
        inserirBairro("São José");
        inserirBairro("Marta Helena");
        inserirBairro("Pacaembu");
        inserirBairro("Santa Rosa");
        inserirBairro("Residencial Gramado");
        inserirBairro("Nossa Senhora das Graças");
        inserirBairro("Minas Gerais");
        inserirBairro("Cruzeiro do Sul");
        inserirBairro("Jardim América");
        inserirBairro("Jardim América II");
        inserirBairro("Distrito Industrial Norte");
        inserirBairro("Residencial Liberdade");
        inserirBairro("Maravilha");
        inserirBairro("Esperança");
        inserirBairro("Jaraguá");
        inserirBairro("Planalto");
        inserirBairro("Chácaras Tubalina");
        inserirBairro("Chácaras Panorama");
        inserirBairro("Luizote de Freitas");
        inserirBairro("Jardim das Palmeiras");
        inserirBairro("Jardim Patrícia");
        inserirBairro("Jardim Holanda");
        inserirBairro("Jardim Europa");
        inserirBairro("Jardim Canaã");
        inserirBairro("Jardim Célia");
        inserirBairro("Mansour");
        inserirBairro("Dona Zulmira");
        inserirBairro("Taiaman");
        inserirBairro("Guarani");
        inserirBairro("Tocantins2");
        inserirBairro("Morada Nova");
        inserirBairro("Morada do Sol");
        inserirBairro("Parque Santo Antônio");
        inserirBairro("Chácaras Rancho Alegre");
        inserirBairro("Jardins");
        inserirBairro("Tubalina");
        inserirBairro("Cidade Jardim");
        inserirBairro("Nova Uberlândia");
        inserirBairro("Patrimônio");
        inserirBairro("Morada da Colina");
        inserirBairro("Vigilato Pereira");
        inserirBairro("Saraiva");
        inserirBairro("Lagoinha");
        inserirBairro("Carajás");
        inserirBairro("Pampulha");
        inserirBairro("Jardim Karaíba");
        inserirBairro("Jardim Inconfidência");
        inserirBairro("Santa Luzia");
        inserirBairro("Granada");
        inserirBairro("São Jorge");
        inserirBairro("Laranjeiras");
        inserirBairro("Shopping Park");
        inserirBairro("Gávea Sul");
        inserirBairro("Santa Mônica");
        inserirBairro("Tibery");
        inserirBairro("Segismundo Pereira");
        inserirBairro("Novo Mundo");
        inserirBairro("Umuarama");
        inserirBairro("Alto Umuarama");
        inserirBairro("Custódio Pereira");
        inserirBairro("Aclimação");
        inserirBairro("Mansões Aeroporto");
        inserirBairro("Dom Almir - Região do Grande Morumbi.");
        inserirBairro("Alvorada");
        inserirBairro("Morumbi - Região do Grande Morumbi.");
        inserirBairro("Joana Darc - Região do Grande Morumbi.");
        inserirBairro("Morada dos Pássaros");
        inserirBairro("Quintas do Bosque");
        inserirBairro("Bosque dos Buritis");
        inserirBairro("Jardim Ipanema");
        inserirBairro("Jardim Califórnia");
        inserirBairro("Jardim Sucupira.");
        inserirBairro("Vila Marielza");

    }

}
