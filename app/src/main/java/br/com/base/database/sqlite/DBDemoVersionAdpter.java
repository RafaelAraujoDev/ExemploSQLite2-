package br.com.base.database.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.base.beans.Record;

import static br.com.base.database.sqlite.DBDemoVersion.getDemoVersionTable;

public class DBDemoVersionAdpter {

    private static DBDemoVersion dbDemoVersion;
    private static SQLiteDatabase db;

    /**
     * Construtor do DBDemoVersionAdpter
     */
    public DBDemoVersionAdpter(Context context) {
        dbDemoVersion = new DBDemoVersion(context);
    }

    /**
     * Faz a conexão com o banco de dados
     */
    private static SQLiteDatabase getConnectionDemo() {

        if (db == null || !db.isOpen()) {
            db = dbDemoVersion.getWritableDatabase();
        }
        return db;
    }

    /**
     * Fecha a conexão com o banco de dados
     */
    private static void close() {
        if (dbDemoVersion != null) {
            dbDemoVersion.close();
        }
        if (db != null) {
            db.close();
        }
    }

    /**
     * Faz um insert novo no banco de dados cadastrando um registro
     */
    public void insertDemoVersion(String name, double price, double quantidade) {
        ContentValues contentValue = new ContentValues();
        contentValue.put("name", name);
        contentValue.put("price", price);
        contentValue.put("quantidade", quantidade);
        getConnectionDemo().insert(getDemoVersionTable(0), null, contentValue);
        close();
    }

    /**
     * Faz um select pegando todos os registros
     * @return retorna um ArrayList<Record>
     */
    public ArrayList<Record> getAllRecords() {
        ArrayList<Record> myarray = new ArrayList<>();
        Cursor cursor = getConnectionDemo().rawQuery("select * from " + getDemoVersionTable(0), null);

            while (cursor.moveToNext()) {
                Record record = new Record();
                record.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                record.setName(cursor.getString(cursor.getColumnIndex("name")));
                record.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
                record.setQuantidade(cursor.getDouble(cursor.getColumnIndex("quantidade")));
                myarray.add(record);
            }

            cursor.close();

        close();
        return myarray;
    }

    /**
     * Atualiza um registro passando os dados dele
     */
    public void updateRecord(long _id, String name, double price, double quantidade) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", _id);
        contentValues.put("name", name);
        contentValues.put("price", price);
        contentValues.put("quantidade", quantidade);
        getConnectionDemo().update(getDemoVersionTable(0), contentValues, " " + "_id" + " = ?", new String[]{String.valueOf(_id)});
        close();
    }

    /**
     * Apaga um registro passando o id dele
     */
    public void deleteRecord(long _id) {
        getConnectionDemo().delete(getDemoVersionTable(0), " " + "_id" + " = ?",
                new String[]{String.valueOf(_id)});
        getConnectionDemo();
    }
}
