package br.com.base.database.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBDemoVersion extends SQLiteOpenHelper {

    //Nome do banco de dados
    private static final String DATABASE_NAME = "demo.db";

    //Versão do banco de dados
    private static final int DATABASE_VERSION = 1;

    // Tabelas
    private static final String Table0 = "CREATE TABLE IF NOT EXISTS " + getDemoVersionTable(0) + " ("
            + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + " name varchar(2) NOT NULL, "
            + " price DOUBLE NOT NULL, "
            + " quantidade DOUBLE NOT NULL);";
    private static final String Table1 = "CREATE TABLE IF NOT EXISTS " + getDemoVersionTable(1) + " ("
            + "_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
            + " name varchar(2) NOT NULL, "
            + " price varchar(2) NOT NULL);";

    /**
     * Construtor do DBDemoVersion
     */
    public DBDemoVersion(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * retorna o nome da tabela passando a posição que ela está no array
     */
    static String getDemoVersionTable(int position) {
        final String[] DATABASE_TABLE = {"test", "teste2"};
        return DATABASE_TABLE[position];
    }

    /**
     * essa função é executada quando o banco de dados não existir
     * criando assim o banco de dados e suas tabelas
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table0);
        db.execSQL(Table1);
        onUpgrade(db, 0, DATABASE_VERSION);
    }

    /**
     * Faz a atualização do banco de dados
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion >= newVersion) return;

        // loop through incremental upgrades
        switch (oldVersion) {
            case 0:

            case 1:
                // upgrade from version 1 to 2
                //db.execSql("ALTER TABLE ...");
                //db.execSql("ALTER TABLE ...");

                break;

            default:
                throw new IllegalStateException("No upgrade specified for " + oldVersion + " -> " + newVersion);
        }

    }
}
