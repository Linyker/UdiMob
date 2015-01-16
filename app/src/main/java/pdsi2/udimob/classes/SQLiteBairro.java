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

}
